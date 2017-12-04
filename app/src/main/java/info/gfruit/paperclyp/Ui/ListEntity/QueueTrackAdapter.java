package info.gfruit.paperclyp.Ui.ListEntity;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import info.gfruit.paperclyp.R;

/**
 * Created by lite20 on 7/17/2017.
 */

public class QueueTrackAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] web;
    private final Integer[] imageId;
    public QueueTrackAdapter(Activity context, String[] web, Integer[] imageId) {
        super(context, R.layout.track_item, web);
        this.context = context;
        this.web = web;
        this.imageId = imageId;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.track_item, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.track_title);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.track_discography);
        txtTitle.setText(web[position]);

        imageView.setImageResource(imageId[position]);
        return rowView;
    }
}