package it.clyp.clyp.Ui.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.LinkedList;
import java.util.List;

import it.clyp.clyp.API.Callback.FeatureCallback;
import it.clyp.clyp.API.Structure.Track;
import it.clyp.clyp.Activity.HomeActivity;
import it.clyp.clyp.Activity.PlayerActivity;
import it.clyp.clyp.Flags;
import it.clyp.clyp.R;
import it.clyp.clyp.Ui.ListEntity.HomeListAdapter;
import it.clyp.clyp.Util.PeriodicLabelSwitcher;

public class HomeFragment extends Fragment {
    public HomeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        // featured list
        ListView featureView = (ListView) rootView.findViewById(R.id.featured_list);
        final LinkedList<Track> featureTracks = new LinkedList<>();
        final HomeListAdapter featureAdapter = new HomeListAdapter(rootView.getContext(), R.layout.track_item, featureTracks);
        featureView.setAdapter(featureAdapter);
        featureView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(rootView.getContext(), PlayerActivity.class);
                intent.putExtra(Flags.INTENT_TRACK_ARTIST, featureTracks.get(position).getName());
                intent.putExtra(Flags.INTENT_TRACK_TITLE, featureTracks.get(position).getTitle());
                intent.putExtra(Flags.INTENT_TRACK_DISCOGRAPHY, featureTracks.get(position).getDiscographyUrl());
                intent.putExtra(Flags.INTENT_TRACK_OGG, featureTracks.get(position).getSecureOggUrl());
                startActivity(intent);
            }
        });

        // saved list
        ListView savedView = (ListView) rootView.findViewById(R.id.saved_list);
        final LinkedList<Track> savedTracks = new LinkedList<>();
        final HomeListAdapter savedAdapter = new HomeListAdapter(rootView.getContext(), R.layout.track_item, savedTracks);
        savedView.setAdapter(savedAdapter);
        savedView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(rootView.getContext(), PlayerActivity.class);
                intent.putExtra(Flags.INTENT_TRACK_ARTIST, savedTracks.get(position).getName());
                intent.putExtra(Flags.INTENT_TRACK_TITLE, savedTracks.get(position).getTitle());
                intent.putExtra(Flags.INTENT_TRACK_DISCOGRAPHY, savedTracks.get(position).getDiscographyUrl());
                intent.putExtra(Flags.INTENT_TRACK_OGG, savedTracks.get(position).getSecureOggUrl());
                startActivity(intent);
            }
        });

        // trending list
        ListView trendingView = (ListView) rootView.findViewById(R.id.trending_list);
        final LinkedList<Track> trendingTracks = new LinkedList<>();
        final HomeListAdapter trendingAdapter = new HomeListAdapter(rootView.getContext(), R.layout.track_item, trendingTracks);
        trendingView.setAdapter(trendingAdapter);
        trendingView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(rootView.getContext(), PlayerActivity.class);
                intent.putExtra(Flags.INTENT_TRACK_ARTIST, trendingTracks.get(position).getName());
                intent.putExtra(Flags.INTENT_TRACK_TITLE, trendingTracks.get(position).getTitle());
                intent.putExtra(Flags.INTENT_TRACK_DISCOGRAPHY, trendingTracks.get(position).getDiscographyUrl());
                intent.putExtra(Flags.INTENT_TRACK_OGG, trendingTracks.get(position).getSecureOggUrl());
                startActivity(intent);
            }
        });

        // recent list
        ListView recentView = (ListView) rootView.findViewById(R.id.recent_list);
        final LinkedList<Track> recentTracks = new LinkedList<>();
        final HomeListAdapter recentAdapter = new HomeListAdapter(rootView.getContext(), R.layout.track_item, recentTracks);
        recentView.setAdapter(recentAdapter);
        recentView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(rootView.getContext(), PlayerActivity.class);
                intent.putExtra(Flags.INTENT_TRACK_ARTIST, recentTracks.get(position).getName());
                intent.putExtra(Flags.INTENT_TRACK_TITLE, recentTracks.get(position).getTitle());
                intent.putExtra(Flags.INTENT_TRACK_DISCOGRAPHY, recentTracks.get(position).getDiscographyUrl());
                intent.putExtra(Flags.INTENT_TRACK_OGG, recentTracks.get(position).getSecureOggUrl());
                startActivity(intent);
            }
        });

        HomeActivity.api.getFeaturedLists(new FeatureCallback() {
            @Override
            public void onComplete(String error, List<Track> body, int item) {
                switch(item) {
                    case Flags.CALLBACK_FEATURE_FEATURE:
                        featureTracks.addAll(body);
                        featureAdapter.notifyDataSetChanged();
                        final PeriodicLabelSwitcher ftSwitcher = new PeriodicLabelSwitcher(
                                R.id.featured_showcase_discography,
                                rootView.findViewById(R.id.featured_showcase),
                                body,
                                3000
                        );

                        ftSwitcher.start();
                        ImageView featuredShowcase = (ImageView) rootView.findViewById(R.id.featured_showcase_discography);
                        featuredShowcase.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(rootView.getContext(), PlayerActivity.class);
                                intent.putExtra(Flags.INTENT_TRACK_ARTIST, ftSwitcher.getCurrentTrack().getName());
                                intent.putExtra(Flags.INTENT_TRACK_TITLE, ftSwitcher.getCurrentTrack().getTitle());
                                intent.putExtra(Flags.INTENT_TRACK_DISCOGRAPHY, ftSwitcher.getCurrentTrack().getDiscographyUrl());
                                intent.putExtra(Flags.INTENT_TRACK_OGG, ftSwitcher.getCurrentTrack().getSecureOggUrl());
                                startActivity(intent);
                            }
                        });

                        break;

                    case Flags.CALLBACK_FEATURE_TRENDING:
                        trendingTracks.addAll(body);
                        trendingAdapter.notifyDataSetChanged();
                        final PeriodicLabelSwitcher trndSwitcher = new PeriodicLabelSwitcher(
                                R.id.trending_showcase_discography,
                                rootView.findViewById(R.id.trending_showcase),
                                body,
                                3000
                        );

                        trndSwitcher.start();
                        ImageView trndShowcase = (ImageView) rootView.findViewById(R.id.trending_showcase_discography);
                        trndShowcase.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(rootView.getContext(), PlayerActivity.class);
                                intent.putExtra(Flags.INTENT_TRACK_ARTIST, trndSwitcher.getCurrentTrack().getName());
                                intent.putExtra(Flags.INTENT_TRACK_TITLE, trndSwitcher.getCurrentTrack().getTitle());
                                intent.putExtra(Flags.INTENT_TRACK_DISCOGRAPHY, trndSwitcher.getCurrentTrack().getDiscographyUrl());
                                intent.putExtra(Flags.INTENT_TRACK_OGG, trndSwitcher.getCurrentTrack().getSecureOggUrl());
                                startActivity(intent);
                            }
                        });

                        break;

                    case Flags.CALLBACK_FEATURE_RECENT:
                        recentTracks.addAll(body);
                        recentAdapter.notifyDataSetChanged();
                        final PeriodicLabelSwitcher rcntSwitcher = new PeriodicLabelSwitcher(
                                R.id.recent_showcase_discography,
                                rootView.findViewById(R.id.recent_showcase),
                                body,
                                3000
                        );

                        rcntSwitcher.start();
                        ImageView recentShowcase = (ImageView) rootView.findViewById(R.id.recent_showcase_discography);
                        recentShowcase.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(rootView.getContext(), PlayerActivity.class);
                                intent.putExtra(Flags.INTENT_TRACK_ARTIST, rcntSwitcher.getCurrentTrack().getName());
                                intent.putExtra(Flags.INTENT_TRACK_TITLE, rcntSwitcher.getCurrentTrack().getTitle());
                                intent.putExtra(Flags.INTENT_TRACK_DISCOGRAPHY, rcntSwitcher.getCurrentTrack().getDiscographyUrl());
                                intent.putExtra(Flags.INTENT_TRACK_OGG, rcntSwitcher.getCurrentTrack().getSecureOggUrl());
                                startActivity(intent);
                            }
                        });

                        break;
                }

            }
        });

        return rootView;
    }
}
