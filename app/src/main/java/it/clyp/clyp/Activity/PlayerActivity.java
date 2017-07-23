package it.clyp.clyp.Activity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

import it.clyp.clyp.API.ClypApi;
import it.clyp.clyp.Flags;
import it.clyp.clyp.R;
import it.clyp.clyp.Ui.Fragment.QueueListDialogFragment;
import it.clyp.clyp.Util.CachedIconloader;
import it.clyp.clyp.Util.GraphicOperation;

public class PlayerActivity extends AppCompatActivity implements QueueListDialogFragment.Listener {

    private Toolbar toolbar;

    private ImageButton btnSearch;
    private ImageButton btnInfo;
    private ImageButton btnRepeat;
    private ImageButton btnPrevious;
    private ImageButton btnPlay;
    private ImageButton btnNext;
    private ImageButton btnToggleQueue;

    private ClypApi api;

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        Bundle bundle = getIntent().getExtras();

        TextView artist = (TextView) findViewById(R.id.player_author);
        TextView title = (TextView) findViewById(R.id.player_title);

        artist.setText(bundle.getString(Flags.INTENT_TRACK_ARTIST, ""));
        title.setText(bundle.getString(Flags.INTENT_TRACK_TITLE, ""));

        CachedIconloader.setIcon(
                bundle.getString(Flags.INTENT_TRACK_DISCOGRAPHY),
                findViewById(R.id.player_ui),
                R.id.player_discography,
                null
        );

        CachedIconloader.setIcon(
                bundle.getString(Flags.INTENT_TRACK_DISCOGRAPHY),
                findViewById(R.id.player),
                R.id.player_background,
                new CachedIconloader.CILPostFetchCallback() {
                    @Override
                    public Bitmap onComplete(Bitmap bmp) {
                        return new GraphicOperation(bmp)
                                .fastblur(0.75f, 2)
                                .tint(Color.argb(100, 0, 0, 0))
                                .result();
                    }
                }
        );

        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(bundle.getString(Flags.INTENT_TRACK_OGG));
            mediaPlayer.prepare();
            mediaPlayer.start();
            ImageView play = (ImageView) findViewById(R.id.player_play);
            play.setImageResource(R.drawable.ic_pause_circle_filled_black_24dp);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // new AuthFragment().show(getSupportFragmentManager(), "Sign in");
        /*
        auth.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    api.authenticate(
                            email.getText().toString(),
                            password.getText().toString(),
                            new AuthCallback() {

                                @Override
                                public void onAuthComplete(boolean success, String error) {
                                    if(success) {
                                        Intent intent = new Intent(context, PlayerActivity.class);
                                        intent.putExtra(Flags.INTENT_IS_AUTHED, true);
                                        intent.putExtra(Flags.INTENT_AUTH_EXPIRY, api.getExpiry());
                                        intent.putExtra(Flags.INTENT_AUTH_TOKEN, api.getToken());
                                        startActivity(intent);
                                    } else {

                                    }
                                }
                            }
                    );
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
         */
        // create new api reference using authentication if provided

        btnSearch = (ImageButton) findViewById(R.id.player_search);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });

        btnInfo = (ImageButton) findViewById(R.id.player_info);
        btnInfo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });

        btnRepeat = (ImageButton) findViewById(R.id.player_repeat);
        btnRepeat.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(mediaPlayer != null) {
                    mediaPlayer.setLooping(!mediaPlayer.isLooping());
                }
            }
        });

        btnPrevious = (ImageButton) findViewById(R.id.player_previous);
        btnPrevious.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(mediaPlayer != null) {
                    if(mediaPlayer.getCurrentPosition() > 500) {
                        mediaPlayer.seekTo(0);
                    } else {
                        // TODO previous track in queue or close player
                    }
                }
            }
        });

        btnPlay = (ImageButton) findViewById(R.id.player_play);
        btnPlay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(mediaPlayer != null) {
                    ImageView play = (ImageView) findViewById(R.id.player_play);
                    if(mediaPlayer.isPlaying() == false) {
                        mediaPlayer.start();
                        play.setImageResource(R.drawable.ic_pause_circle_filled_black_24dp);
                    } else {
                        mediaPlayer.pause();
                        play.setImageResource(R.drawable.ic_play_circle_filled_black_24dp);
                    }
                }
            }
        });

        btnNext = (ImageButton) findViewById(R.id.player_next);
        btnNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(mediaPlayer != null) {
                    mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() + 10000);
                }
            }
        });

        btnToggleQueue = (ImageButton) findViewById(R.id.player_queue);
        btnToggleQueue.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                QueueListDialogFragment.newInstance(30).show(getSupportFragmentManager(), "dialog");
            }
        });
    }

    @Override
    public void onQueueClicked(int position) {

    }
}
