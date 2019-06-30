package com.opensource.paperclyp.Util.GraphicOperation;

import android.content.Context;
import android.graphics.Bitmap;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicConvolve3x3;
import android.util.Log;

import com.squareup.picasso.Transformation;

/**
 * Based off https://stackoverflow.com/questions/34175032/color-matrix-for-image-sharpening-in-android
 **/
public class Sharpen implements Transformation {
    public static final int LOW_SHARPEN = 0;
    public static final int MEDIUM_SHARPEN = 1;
    public static final int HIGH_SHARPEN = 2;

    private static final float[][] radius = new float[][]{
        {-0.15f, -0.15f, -0.15f, -0.15f, 2.2f, -0.15f, -0.15f, -0.15f, -0.15f},
        {0.0f, -1.0f, 0.0f, -1.0f, 5.0f, -1.0f, 0.0f, -1.0f, 0.0f},
        {-0.60f, -0.60f, -0.60f, -0.60f, 5.81f, -0.60f, -0.60f, -0.60f, -0.60f}
    };

    private final int degree;

    private final Context context;

    public Sharpen(Context context, int degree) {
        super();
        this.degree = degree;
        this.context = context;
    };

    @Override public String key() { return "sharpen()"; }

    @Override public Bitmap transform(Bitmap source) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
            Bitmap result = Bitmap.createBitmap(
                    source.getWidth(),
                    source.getHeight(),
                    source.getConfig()
            );

            RenderScript rs = RenderScript.create(context);

            Allocation allocIn = Allocation.createFromBitmap(rs, source);
            Allocation allocresult = Allocation.createFromBitmap(rs, result);

            ScriptIntrinsicConvolve3x3 convolution = ScriptIntrinsicConvolve3x3.create(rs, Element.U8_4(rs));
            convolution.setInput(allocIn);
            convolution.setCoefficients(radius[degree]);
            convolution.forEach(allocresult);

            allocresult.copyTo(result);
            rs.destroy();

            source.recycle();
            return result;
        }
        
        return source;
    }
}
