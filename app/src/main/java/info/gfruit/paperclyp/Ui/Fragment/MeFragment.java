package info.gfruit.paperclyp.Ui.Fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import info.gfruit.paperclyp.API.Callback.StandardCallback;
import info.gfruit.paperclyp.API.Structure.User;
import info.gfruit.paperclyp.Activity.HomeActivity;
import info.gfruit.paperclyp.R;
import info.gfruit.paperclyp.Util.CachedIconloader;
import info.gfruit.paperclyp.Util.GraphicOperation;

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
