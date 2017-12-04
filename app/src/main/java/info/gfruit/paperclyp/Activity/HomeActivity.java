package info.gfruit.paperclyp.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.crashlytics.android.Crashlytics;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.io.File;

import io.fabric.sdk.android.Fabric;
import info.gfruit.paperclyp.API.ClypApi;
import info.gfruit.paperclyp.Flags;
import info.gfruit.paperclyp.R;
import info.gfruit.paperclyp.Ui.Fragment.FeedFragment;
import info.gfruit.paperclyp.Ui.Fragment.HomeFragment;
import info.gfruit.paperclyp.Ui.Fragment.MeFragment;

public class HomeActivity extends AppCompatActivity {

    private FirebaseAnalytics mFirebaseAnalytics;

    public static RequestQueue mRequestQueue;

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    SharedPreferences settings;

    public static ClypApi api;

    private File cache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_home);
        settings = getSharedPreferences(Flags.PREFS_SETTINGS, 0);
        if(!settings.getBoolean("firstInstall", false)) {
            settings.edit()
                    .putBoolean("firstInstall", true)
                    .commit();

            Intent intent = new Intent(this, AuthActivity.class);
            startActivity(intent);
        }

        Intent intent = getIntent();
        if(settings.contains("auth_token") && settings.contains("expiry_time")) {
            String token = settings.getString("auth_token", null);
            String refresh_token = settings.getString("refresh_token", null);
            int expiry = settings.getInt("expiry_time", 0);
            api = new ClypApi(this, "https://api.clyp.it", token, refresh_token, expiry);

        } else if(intent.getBooleanExtra(Flags.INTENT_IS_AUTHED, false)) {
            String token = intent.getStringExtra(Flags.INTENT_AUTH_TOKEN);
            String refresh_token = settings.getString("refresh_token", null);
            int expiry = intent.getIntExtra(Flags.INTENT_AUTH_EXPIRY, 0);
            api = new ClypApi(this, "https://api.clyp.it", token, refresh_token, expiry);
            settings.edit()
                    .putString("auth_token", token)
                    .putInt("expiry_time", expiry)
                    .commit();
        } else {
            api = new ClypApi(this, "https://api.clyp.it");
        }

        Cache cache = new DiskBasedCache(getCacheDir(), 10 * 1024 * 1024); // 10MB cap
        // Set up the network to use HttpURLConnection as the HTTP client.
        Network network = new BasicNetwork(new HurlStack());
        mRequestQueue = new RequestQueue(cache, network);
        mRequestQueue.start();

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        // init firebase
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new HomeFragment();
                case 1:
                    return new FeedFragment();
                case 2:
                    return new MeFragment();
            }

            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Home";
                case 1:
                    return "Feed";
                case 2:
                    return "Me";
            }

            return null;
        }
    }
}
