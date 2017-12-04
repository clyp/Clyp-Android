package info.gfruit.paperclyp.Activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

import info.gfruit.paperclyp.API.ClypApi;
import info.gfruit.paperclyp.Flags;
import info.gfruit.paperclyp.R;
import info.gfruit.paperclyp.Ui.Fragment.QueueListDialogFragment;
import info.gfruit.paperclyp.Ui.Visualizer;
import info.gfruit.paperclyp.Util.CachedIconloader;
import info.gfruit.paperclyp.Util.GraphicOperation;

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

    private static MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SurfaceView surface = (SurfaceView) findViewById(R.id.visualizer);
        Visualizer viz = new Visualizer(getApplicationContext());

        setContentView(R.layout.activity_player);
        Bundle bundle = getIntent().getExtras();

        final Context context = getApplicationContext();

        TextView artist = (TextView) findViewById(R.id.player_author);
        TextView title = (TextView) findViewById(R.id.player_title);

        artist.setText(bundle.getString(Flags.INTENT_TRACK_ARTIST, ""));
        title.setText(bundle.getString(Flags.INTENT_TRACK_TITLE, ""));

        CachedIconloader.setIcon(
                bundle.getString(Flags.INTENT_TRACK_DISCOGRAPHY),
                findViewById(R.id.player_ui),
                R.id.player_discography,
                new CachedIconloader.CILPostFetchCallback() {
                    @Override
                    public Bitmap onComplete(Bitmap bmp) {
                        return new GraphicOperation(bmp)
                                .scale(5f)
                                .fastBlur(0.5f, 1)
                                .sharpen(context, GraphicOperation.MEDIUM_SHARPEN)
                                .fastBlur(0.80f, 1)
                                .roundCorners(5)
                                .result();
                    }
                }
        );

        CachedIconloader.setIcon(
                bundle.getString(Flags.INTENT_TRACK_DISCOGRAPHY),
                findViewById(R.id.player),
                R.id.player_background,
                new CachedIconloader.CILPostFetchCallback() {
                    @Override
                    public Bitmap onComplete(Bitmap bmp) {
                        return new GraphicOperation(bmp)
                                .fastBlur(0.70f, 5)
                                .tint(Color.argb(255, 225, 225, 225))
                                .result();
                    }
                }
        );

        try {
            if(mediaPlayer != null) {
                mediaPlayer.stop();
            }

            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(bundle.getString(Flags.INTENT_TRACK_OGG));
            mediaPlayer.prepare();
            mediaPlayer.start();
            final ImageView play = (ImageView) findViewById(R.id.player_play);
            play.setImageResource(R.drawable.ic_pause_circle_filled_black_24dp);
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    play.setImageResource(R.drawable.ic_play_circle_filled_black_24dp);
                }
            });
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
