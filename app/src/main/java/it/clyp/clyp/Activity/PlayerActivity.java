package it.clyp.clyp.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

import it.clyp.clyp.API.ClypApi;
import it.clyp.clyp.Ui.Fragment.QueueListDialogFragment;
import it.clyp.clyp.Ui.Prompt.AuthFragment;
import it.clyp.clyp.R;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        new AuthFragment().show(getSupportFragmentManager(), "Sign in");
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


        btnSearch = (ImageButton) findViewById(R.id.search);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });

        btnInfo = (ImageButton) findViewById(R.id.info);
        btnInfo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });

        btnRepeat = (ImageButton) findViewById(R.id.repeat);
        btnRepeat.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });

        btnPrevious = (ImageButton) findViewById(R.id.previous);
        btnPrevious.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });

        btnPlay = (ImageButton) findViewById(R.id.play);
        btnPlay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });

        btnNext = (ImageButton) findViewById(R.id.next);
        btnNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });

        btnToggleQueue = (ImageButton) findViewById(R.id.queue);
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
