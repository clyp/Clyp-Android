package info.gfruit.paperclyp.Ui.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.LinkedList;

import info.gfruit.paperclyp.API.Callback.StandardCallback;
import info.gfruit.paperclyp.API.Response.NotificationResponse;
import info.gfruit.paperclyp.API.Structure.Notification;
import info.gfruit.paperclyp.Activity.HomeActivity;
import info.gfruit.paperclyp.R;
import info.gfruit.paperclyp.Ui.ListEntity.NotificationListAdapter;


public class FeedFragment extends Fragment {

    public FeedFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_feed, container, false);
        ListView notificationView = (ListView) rootView.findViewById(R.id.notification_list);
        final LinkedList<Notification> notifications = new LinkedList<>();
        final NotificationListAdapter notificationAdapter = new NotificationListAdapter(rootView.getContext(), R.layout.track_item, notifications);
        notificationView.setAdapter(notificationAdapter);
        notificationView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO detect what the notification is for and handle appropriately
            }
        });

        HomeActivity.api.getNotifications(new StandardCallback() {
            @Override
            public void onComplete(String error, Object data) {
                if(error != null) {
                    // TODO handle error
                } else {
                    NotificationResponse resp =(NotificationResponse) data;
                    // TODO check if all notifications have been checked already
                    notifications.addAll(resp.getData());
                }
            }
        });
        return rootView;
    }
}
