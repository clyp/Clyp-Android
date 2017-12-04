package info.gfruit.paperclyp.Util;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;

import java.util.HashMap;

import info.gfruit.paperclyp.Activity.HomeActivity;

/**
 * Created by lite20 on 7/19/2017.
 */

public class CachedIconloader {

    private static HashMap<String, Bitmap> bitmapCache = new HashMap<String, Bitmap>();

    public static void fetchIcon(String url, final CILPostFetchCallback cb) {
        if (bitmapCache.containsKey(url)) {
            cb.onComplete(bitmapCache.get(url));
            return;
        } else {
            // if we haven't loaded the image before, we grab it
            final String finalUrl = url;
            final ImageRequest request = new ImageRequest(url,
                    new Response.Listener<Bitmap>() {
                        @Override
                        public void onResponse(Bitmap bitmap) {
                            // place the image in cache
                            // TODO use filesystem based cache
                            bitmapCache.put(finalUrl, bitmap);
                            cb.onComplete(bitmap);
                        }
                    }, 0, 0, null,
                    new Response.ErrorListener() {
                        public void onErrorResponse(VolleyError error) {
                            // TODO show failed load icon and warning to user
                            // TODO check if internet is still available
                        }
                    }
            );

            HomeActivity.mRequestQueue.add(request);
        }
    }

    public static void setIcon(String url, final View parentView, final int id, final CILPostFetchCallback cb) {
        // check if the image has been loaded before and use a cached image if available
        if (bitmapCache.containsKey(url)) {
            ImageView img = (ImageView) parentView.findViewById(id);
            if(cb != null) {
                img.setImageBitmap(cb.onComplete(bitmapCache.get(url)));
            } else {
                img.setImageBitmap(bitmapCache.get(url));
            }
        } else {
            // if we haven't loaded the image before, we grab it
            final String finalUrl = url;
            final ImageRequest request = new ImageRequest(url,
                    new Response.Listener<Bitmap>() {
                        @Override
                        public void onResponse(Bitmap bitmap) {
                            // place the image in cache
                            // TODO use filesystem based cache
                            bitmapCache.put(finalUrl, bitmap);
                            // grab a reference to the discography UI element
                            ImageView img = (ImageView) parentView.findViewById(id);
                            // set the UI elements bitmap
                            if(cb != null) {
                                img.setImageBitmap(cb.onComplete(bitmap));
                            } else {
                                img.setImageBitmap(bitmap);
                            }
                        }
                    }, 0, 0, null,
                    new Response.ErrorListener() {
                        public void onErrorResponse(VolleyError error) {
                            // TODO show failed load icon and warning to user
                            // TODO check if internet is still available
                        }
                    }
            );

            HomeActivity.mRequestQueue.add(request);
        }
    }

    public interface CILPostFetchCallback {
        Bitmap onComplete(Bitmap bmp);
    }
}
