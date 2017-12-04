package info.gfruit.paperclyp.Util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicConvolve3x3;
import android.util.Log;

/**
 * Created by lite20 on 7/21/2017.
 */

public class GraphicOperation {
    private Bitmap bitmap;

    public static final int LOW_SHARPEN = 0;
    public static final int MEDIUM_SHARPEN = 1;
    public static final int HIGH_SHARPEN = 2;

    public GraphicOperation(Bitmap bmp) {
        this.bitmap = bmp.copy(bmp.getConfig(), true);
    }

    public GraphicOperation fastBlur(float scale, int radius) {
        int width = Math.round(bitmap.getWidth() * scale);
        int height = Math.round(bitmap.getHeight() * scale);
        bitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
        if (radius < 1) {
           // return (null);
        }

        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        int[] pix = new int[w * h];
        bitmap.getPixels(pix, 0, w, 0, 0, w, h);

        int wm = w - 1;
        int hm = h - 1;
        int wh = w * h;
        int div = radius + radius + 1;

        int r[] = new int[wh];
        int g[] = new int[wh];
        int b[] = new int[wh];
        int rsum, gsum, bsum, x, y, i, p, yp, yi, yw;
        int vmin[] = new int[Math.max(w, h)];

        int divsum = (div + 1) >> 1;
        divsum *= divsum;
        int dv[] = new int[256 * divsum];
        for (i = 0; i < 256 * divsum; i++) {
            dv[i] = (i / divsum);
        }

        yw = yi = 0;

        int[][] stack = new int[div][3];
        int stackpointer;
        int stackstart;
        int[] sir;
        int rbs;
        int r1 = radius + 1;
        int routsum, goutsum, boutsum;
        int rinsum, ginsum, binsum;

        for (y = 0; y < h; y++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            for (i = -radius; i <= radius; i++) {
                p = pix[yi + Math.min(wm, Math.max(i, 0))];
                sir = stack[i + radius];
                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);
                rbs = r1 - Math.abs(i);
                rsum += sir[0] * rbs;
                gsum += sir[1] * rbs;
                bsum += sir[2] * rbs;
                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }
            }

            stackpointer = radius;
            for (x = 0; x < w; x++) {
                r[yi] = dv[rsum];
                g[yi] = dv[gsum];
                b[yi] = dv[bsum];

                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;

                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];

                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];

                if (y == 0) {
                    vmin[x] = Math.min(x + radius + 1, wm);
                }

                p = pix[yw + vmin[x]];

                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);

                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];

                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;

                stackpointer = (stackpointer + 1) % div;
                sir = stack[(stackpointer) % div];

                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];

                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];

                yi++;
            }

            yw += w;
        }

        for (x = 0; x < w; x++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            yp = -radius * w;
            for (i = -radius; i <= radius; i++) {
                yi = Math.max(0, yp) + x;

                sir = stack[i + radius];

                sir[0] = r[yi];
                sir[1] = g[yi];
                sir[2] = b[yi];

                rbs = r1 - Math.abs(i);

                rsum += r[yi] * rbs;
                gsum += g[yi] * rbs;
                bsum += b[yi] * rbs;

                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }

                if (i < hm) {
                    yp += w;
                }
            }

            yi = x;
            stackpointer = radius;
            for (y = 0; y < h; y++) {
                // Preserve alpha channel: ( 0xff000000 & pix[yi] )
                pix[yi] = ( 0xff000000 & pix[yi] ) | ( dv[rsum] << 16 ) | ( dv[gsum] << 8 ) | dv[bsum];

                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;

                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];

                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];

                if (x == 0) {
                    vmin[y] = Math.min(y + r1, hm) * w;
                }

                p = x + vmin[y];

                sir[0] = r[p];
                sir[1] = g[p];
                sir[2] = b[p];

                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];

                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;

                stackpointer = (stackpointer + 1) % div;
                sir = stack[stackpointer];

                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];

                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];

                yi += w;
            }
        }

        bitmap.setPixels(pix, 0, w, 0, 0, w, h);
        return this;
    }

    /**
     * Taken from https://stackoverflow.com/questions/2459916/how-to-make-an-imageview-with-rounded-corners
     * @param pixels
     * @return
     */
    public GraphicOperation roundCorners(int pixels) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        bitmap = output.copy(bitmap.getConfig(), true);
        return this;
    }

    /**
     * This function tints the Bitmap with a certain color
     * @param tint The color int to tint with. Should have 255 alpha, and RGB between 0 to 255 (higher = less impact)
     * @return
     */
    public GraphicOperation tint(int tint) {
        Canvas canvas = new Canvas(bitmap);
        Paint p = new Paint(Color.RED);
        ColorFilter filter = new LightingColorFilter(tint, 0x00000000);
        p.setColorFilter(filter);
        canvas.drawBitmap(bitmap, new Matrix(), p);
        return this;
    }

    public GraphicOperation scale(float scale) {
        bitmap = Bitmap.createScaledBitmap(
                bitmap,
                (int)(bitmap.getWidth() * scale),
                (int)(bitmap.getHeight() * scale),
                false
        );

        return this;
    }

    /**
     * Based off https://stackoverflow.com/questions/34175032/color-matrix-for-image-sharpening-in-android
     * @param context
     * @param radius
     * @return
     */
    public GraphicOperation sharpen(Context context, float[] radius) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
            Bitmap out = Bitmap.createBitmap(
                    bitmap.getWidth(),
                    bitmap.getHeight(),
                    bitmap.getConfig()
            );

            RenderScript rs = RenderScript.create(context);

            Allocation allocIn = Allocation.createFromBitmap(rs, bitmap);
            Allocation allocOut = Allocation.createFromBitmap(rs, out);

            ScriptIntrinsicConvolve3x3 convolution = ScriptIntrinsicConvolve3x3.create(rs, Element.U8_4(rs));
            convolution.setInput(allocIn);
            convolution.setCoefficients(radius);
            convolution.forEach(allocOut);

            allocOut.copyTo(bitmap);
            rs.destroy();
        } else {
            Log.d("ClypGO", "API level too low to perform image sharpening.");
        }

        return this;
    }

    public GraphicOperation sharpen(Context context, int degree) {
        switch(degree) {
            case MEDIUM_SHARPEN:
                sharpen(
                        context,
                        new float[]{0.0f, -1.0f, 0.0f, -1.0f, 5.0f, -1.0f, 0.0f, -1.0f, 0.0f}
                );

                break;
            case HIGH_SHARPEN:
                sharpen(
                        context,
                        new float[]{-0.60f, -0.60f, -0.60f, -0.60f, 5.81f, -0.60f, -0.60f, -0.60f, -0.60f}
                );

                break;
            default:
                sharpen(
                        context,
                        new float[]{ -0.15f, -0.15f, -0.15f, -0.15f, 2.2f, -0.15f, -0.15f, -0.15f, -0.15f}
                );

                break;
        }

        return this;
    }

    public Bitmap result() {
        return this.bitmap;
    }
}
