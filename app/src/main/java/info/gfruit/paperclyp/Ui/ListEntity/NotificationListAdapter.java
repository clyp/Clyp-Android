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

import java.util.List;

import info.gfruit.paperclyp.API.Structure.Notification;
import info.gfruit.paperclyp.R;
import info.gfruit.paperclyp.Util.CachedIconloader;
import info.gfruit.paperclyp.Util.GraphicOperation;

/**
 * Created by lite20 on 7/26/2017.
 */

public class NotificationListAdapter extends ArrayAdapter<Notification> {
    private final Context context;

    private final List<Notification> notificationList;

    public NotificationListAdapter(Context context, int resourceId, List<Notification> items) {
        super(context, resourceId, items);
        this.context = context;
        this.notificationList = items;
    }

    /* private view holder class */
    private class ViewHolder {
        ImageView notificationImage;
        TextView notificationText;
        TextView notificationTime;
    }

    /**
     *
     * @param listPosition
     * @param listViewElement This is one singular l
     * @param parent
     * @return
     */
    public View getView(int listPosition, View listViewElement, ViewGroup parent) {
        NotificationListAdapter.ViewHolder holder;
        Notification notification = getItem(listPosition);
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (listViewElement == null) {
            listViewElement = mInflater.inflate(R.layout.track_item, null);
            holder = new NotificationListAdapter.ViewHolder();
            // fetch references to all elements
            holder.notificationText = (TextView) listViewElement.findViewById(R.id.notification_text);
            holder.notificationTime = (TextView) listViewElement.findViewById(R.id.notification_time);
            holder.notificationImage = (ImageView) listViewElement.findViewById(R.id.notification_image);
            listViewElement.setTag(holder);
        } else {
            holder = (NotificationListAdapter.ViewHolder) listViewElement.getTag();
        }

        /* set notification text */
        holder.notificationText.setText(notification.getText());

        /* set notification time */
        holder.notificationTime.setText(notification.getDateCreated());

        /* set notification image */
        // make a final fork of the convert view since we need this specific copy for getting the ui image element
        final View finalConvertView = listViewElement;

        // set a place holder image until loaded
        holder.notificationImage.setImageBitmap(
                BitmapFactory.decodeResource(context.getResources(), R.drawable.blank)
        );
        // load the image in the background and set it once it's available
        CachedIconloader.setIcon(
                notification.getImageUrl(),
                listViewElement,
                R.id.notification_image,
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
