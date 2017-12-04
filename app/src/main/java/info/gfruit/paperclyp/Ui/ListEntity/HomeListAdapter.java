package info.gfruit.paperclyp.Ui.ListEntity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import info.gfruit.paperclyp.API.Structure.Track;
import info.gfruit.paperclyp.R;
import info.gfruit.paperclyp.Util.CachedIconloader;
import info.gfruit.paperclyp.Util.GraphicOperation;

/**
 * Created by lite20 on 7/17/2017.
 */

public class HomeListAdapter extends ArrayAdapter<Track> {
    private List<Track> tracks;

    private HashMap<String, Integer> urlToIdMap = new HashMap<String, Integer>();

    private Context context;

    public HomeListAdapter(Context context, int resourceId, List<Track> items) {
        super(context, resourceId, items);
        this.context = context;
        this.tracks = items;
    }

    /* private view holder class */
    private class ViewHolder {
        ImageView trackDiscography;
        TextView trackTitle;
        TextView trackArtist;
    }

    /**
     * 
     * @param listPosition
     * @param listViewElement This is one singular l
     * @param parent
     * @return
     */
    public View getView(int listPosition, View listViewElement, ViewGroup parent) {
        ViewHolder holder;
        Track track = getItem(listPosition);
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (listViewElement == null) {
            listViewElement = mInflater.inflate(R.layout.track_item, null);
            holder = new ViewHolder();
            // fetch references to all elements
            holder.trackTitle = (TextView) listViewElement.findViewById(R.id.track_title);
            holder.trackArtist = (TextView) listViewElement.findViewById(R.id.track_artist);
            holder.trackDiscography = (ImageView) listViewElement.findViewById(R.id.track_discography);
            listViewElement.setTag(holder);
        } else {
            holder = (ViewHolder) listViewElement.getTag();
        }

        /* set title to track's title */
        holder.trackTitle.setText(track.getTitle());

        /* set track artist */
        // not all tracks are uploaded with an account so we only set the author if a user is tied to the track
        if(track.getUser() != null) {
            holder.trackArtist.setText(track.getName());
        }

        /* set track discography (artwork) */
        // make a final fork of the convert view since we need this specific copy for getting the ui image element
        final View finalConvertView = listViewElement;

        // set a place holder image until loaded
        holder.trackDiscography.setImageBitmap(
                BitmapFactory.decodeResource(context.getResources(), R.drawable.blank)
        );
        // load the icon in the background and set it once it's available
        CachedIconloader.setIcon(
                track.getDiscographyUrl(),
                listViewElement, R.id.track_discography,
                new CachedIconloader.CILPostFetchCallback() {
                    @Override
                    public Bitmap onComplete(Bitmap bmp) {
                        return new GraphicOperation(bmp)
                                .roundCorners(5)
                                .result();
                    }
                }
        );

        return listViewElement;
    }
}