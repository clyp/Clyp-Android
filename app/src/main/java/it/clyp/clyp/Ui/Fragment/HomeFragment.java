package it.clyp.clyp.Ui.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.LinkedList;
import java.util.List;

import it.clyp.clyp.API.Callback.FeatureCallback;
import it.clyp.clyp.API.ClypApi;
import it.clyp.clyp.API.Structure.Track;
import it.clyp.clyp.Flags;
import it.clyp.clyp.R;
import it.clyp.clyp.Ui.ListEntity.HomeListAdapter;

public class HomeFragment extends Fragment {
    private ClypApi api;

    public HomeFragment() {
        Bundle bundle = getArguments();
        if(bundle != null && bundle.containsKey("token")) {
            api = new ClypApi(
                    getContext(),
                    "https://api.clyp.it",
                    bundle.getString("token", null),
                    bundle.getInt("expiry", 0)
            );
        } else {
            api = new ClypApi(
                    getContext(),
                    "https://api.clyp.it"
            );
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        api.getFeaturedLists(new FeatureCallback() {
            @Override
            public void onComplete(String err, List<Track> body, int item) {
                if(err == null) {
                    switch(item) {
                        case 1:
                            for(int i = 0; i < body.size(); i++) {

                            }

                            break;
                        case 2:
                            break;
                        case 3:
                            break;
                        default:
                            // TODO report unreported error?!
                            // this case should never ensue
                            break;
                    }
                } else {
                    // TODO handle error
                }
            }
        });

        // featured list
        ListView listView = (ListView) rootView.findViewById(R.id.featured_list);
        final LinkedList<Track> featureTracks = new LinkedList<Track>();
        final HomeListAdapter featureAdapter = new HomeListAdapter(rootView.getContext(), R.layout.track_item, featureTracks);
        listView.setAdapter(featureAdapter);

        listView = (ListView) rootView.findViewById(R.id.trending_list);
        final LinkedList<Track> trendingTracks = new LinkedList<Track>();
        final HomeListAdapter trendingAdapter = new HomeListAdapter(rootView.getContext(), R.layout.track_item, trendingTracks);
        listView.setAdapter(trendingAdapter);

        listView = (ListView) rootView.findViewById(R.id.recent_list);
        final LinkedList<Track> recentTracks = new LinkedList<Track>();
        final HomeListAdapter recentAdapter = new HomeListAdapter(rootView.getContext(), R.layout.track_item, recentTracks);
        listView.setAdapter(recentAdapter);

        api.getFeaturedLists(new FeatureCallback() {
            @Override
            public void onComplete(String error, List<Track> body, int item) {
                Log.d("Home", "Hanling item type " + item);
                switch(item) {
                    case Flags.CALLBACK_FEATURE_FEATURE:
                        featureTracks.addAll(body);
                        featureAdapter.notifyDataSetChanged();
                        break;

                    case Flags.CALLBACK_FEATURE_TRENDING:
                        trendingTracks.addAll(body);
                        trendingAdapter.notifyDataSetChanged();
                        break;

                    case Flags.CALLBACK_FEATURE_RECENT:
                        recentTracks.addAll(body);
                        recentAdapter.notifyDataSetChanged();
                        break;
                }
            }
        });

        return rootView;
    }
}
