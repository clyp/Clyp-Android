package com.opensource.paperclyp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import com.opensource.paperclyp.API.Callback.AuthCallback;
import com.opensource.paperclyp.API.ClypApi;
import com.opensource.paperclyp.Flags;
import com.opensource.paperclyp.R;

public class AuthActivity extends AppCompatActivity {

    private ClypApi api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        final AuthActivity context = this;

        api = new ClypApi(this, "https://api.clyp.it");

        final TextView email = (TextView) findViewById(R.id.auth_email);
        final TextView password = (TextView) findViewById(R.id.auth_password);

        Button auth = (Button) findViewById(R.id.auth);

        auth.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    api.authenticate(
                            email.getText().toString(),
                            password.getText().toString(),
                            new AuthCallback() {
                                @Override
                                public void onAuthComplete(String error, boolean success) {
                                    if(success) {
                                        Intent intent = new Intent(context, MainActivity.class);
                                        intent.putExtra(Flags.INTENT_IS_AUTHED, true);
                                        intent.putExtra(Flags.INTENT_AUTH_TOKEN, api.getToken());
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(context, error, Toast.LENGTH_SHORT);
                                    }
                                }
                            }
                    );
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        Button skip_auth = (Button) findViewById(R.id.skip_auth);
        skip_auth.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra(Flags.INTENT_IS_AUTHED, false);
                startActivity(intent);
            }
        });
    }
}