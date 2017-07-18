package it.clyp.clyp.Ui.ListEntity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;

import java.util.List;

import it.clyp.clyp.API.Structure.Track;
import it.clyp.clyp.Activity.HomeActivity;
import it.clyp.clyp.R;

/**
 * Created by lite20 on 7/17/2017.
 */

public class HomeListAdapter extends ArrayAdapter<Track> {
    private List<Track> tracks;

    private Context context;

    public HomeListAdapter(Context context, int resourceId, List<Track> items) {
        super(context, resourceId, items);
        this.context = context;
        this.tracks = items;
    }

    /*private view holder class*/
    private class ViewHolder {
        ImageView imageView;
        TextView txtTitle;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        Track track = getItem(position);
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.track_item, null);
            holder = new ViewHolder();
            holder.txtTitle = (TextView) convertView.findViewById(R.id.txt);
            holder.imageView = (ImageView) convertView.findViewById(R.id.img);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtTitle.setText(track.getTitle());
        final View finalConvertView = convertView;
        ImageRequest request = new ImageRequest(track.getArtworkPictureUrl(),
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        ImageView img = (ImageView)finalConvertView.findViewById(R.id.img);
                        img.setImageBitmap(bitmap);
                    }
                }, 0, 0, null,
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        // mImageView.setImageResource(R.drawable.image_load_error);
                    }
                }
        );

        HomeActivity.mRequestQueue.add(request);
        return convertView;
    }
}