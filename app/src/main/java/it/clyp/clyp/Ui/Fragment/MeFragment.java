package it.clyp.clyp.Ui.Fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import it.clyp.clyp.API.Callback.StandardCallback;
import it.clyp.clyp.API.Structure.User;
import it.clyp.clyp.Activity.HomeActivity;
import it.clyp.clyp.R;
import it.clyp.clyp.Util.CachedIconloader;
import it.clyp.clyp.Util.GraphicOperation;

public class MeFragment extends Fragment {

    public MeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_me, container, false);

        HomeActivity.api.getSelf(new StandardCallback() {
            @Override
            public void onComplete(String error, Object data) {
                User usr = (User)data;
                ImageView pp = (ImageView) rootView.findViewById(R.id.profile_picture);
                CachedIconloader.setIcon(usr.getProfilePictureUrl(), rootView, R.id.profile_picture, new CachedIconloader.CILPostFetchCallback() {
                    @Override
                    public Bitmap onComplete(Bitmap bmp) {
                        return new GraphicOperation(bmp)
                                .roundCorners(5)
                                .result();
                    }
                });
            }
        });

        return rootView;
    }
}
