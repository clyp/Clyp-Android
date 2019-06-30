package com.opensource.paperclyp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import android.util.Log;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.opensource.paperclyp.Util.GraphicOperation.Blur;
import com.opensource.paperclyp.Util.GraphicOperation.Sharpen;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import java.net.URL;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


        // check if opened from link
        Intent intent = getIntent();
        Uri uri = this.getIntent().getData();
        try {
            // get ID
            URL url = new URL(uri.getScheme(), uri.getHost(), uri.getPath());
            String url_text = url.toString();
            String id = url_text.split("/")[3];

            // set main artwork
            final ImageView discography = (ImageView) findViewById(R.id.playback_discography);
            ViewTreeObserver vto = discography.getViewTreeObserver();
            vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                public boolean onPreDraw() {
                    discography.getViewTreeObserver().removeOnPreDrawListener(this);
                    int width = discography.getMeasuredWidth();
                    Log.i("PaperClyp", "Width: " + width);
                    Picasso.get()
                        .load("https://static.clyp.it/user-content/audio-file-artwork/42cfe010e83e478286218b74d3cea78b.jpg")
                        .resize(width, width)
                        .into(discography);

                    return true;
                }
            });

            // load background artwork
            Target backgroundTarget = new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    ConstraintLayout playbackPanel = (ConstraintLayout) findViewById(R.id.background_panel);
                    playbackPanel.setBackground(new BitmapDrawable(getResources(), bitmap));
                }

                @Override
                public void onBitmapFailed(Exception e, Drawable errorDrawable) {}

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {}
            };

            Picasso.get()
                    .load("https://static.clyp.it/user-content/audio-file-artwork/42cfe010e83e478286218b74d3cea78b.jpg")
                    .transform(new Blur(20))
                    .into(backgroundTarget);

            // expand playback panel
            SlidingUpPanelLayout slidingPanel = (SlidingUpPanelLayout) findViewById(R.id.playback_panel);
            slidingPanel.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);

            Log.i("PaperClyp", "Loaded song from URL ID: " + id);
        } catch (Exception e) { /* do nothing */ }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
