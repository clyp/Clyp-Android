package it.clyp.clyp.API;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.util.List;

import it.clyp.clyp.API.Callback.AuthCallback;
import it.clyp.clyp.API.Callback.FeatureCallback;
import it.clyp.clyp.API.Callback.StandardCallback;
import it.clyp.clyp.API.Response.AuthResponse;
import it.clyp.clyp.API.Structure.Track;
import it.clyp.clyp.Flags;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lite20 on 7/16/2017.
 */

public class ClypApi {
    private final Context context;

    public ClypApiInterface service;

    public ClypSoundwaveApiInterface soundwaveService;

    private String token;

    private int expiry;


    public ClypApi(Context context, String base) {
        this.context = context;
        Retrofit soundwaveRetrofit = new Retrofit.Builder()
                .baseUrl("https://soundwave.clyp.it")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        soundwaveService = soundwaveRetrofit.create(ClypSoundwaveApiInterface.class);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(base)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(ClypApiInterface.class);
    }

    public ClypApi(Context context, String base, String token, int expiry) {
        this.context = context;
        Retrofit soundwaveRetrofit = new Retrofit.Builder()
                .baseUrl("https://soundwave.clyp.it")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        soundwaveService = soundwaveRetrofit.create(ClypSoundwaveApiInterface.class);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(base)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(ClypApiInterface.class);
        this.token = token;
        this.expiry = expiry;
    }

    public void getSoundwave(String songId, final StandardCallback cb) {
        Call<List<Integer>> call = soundwaveService.getSongSoundwave(
                songId
        );

        call.enqueue(new Callback<List<Integer>>() {
            @Override
            public void onResponse(Call<List<Integer>> call, Response<List<Integer>> response) {
                if (response.isSuccessful()) {
                    cb.onComplete(null, response.body());
                } else {
                    // error response, no access to resource?
                    try {
                        Log.d("ClypAPI", "ERROR: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    try {
                        cb.onComplete(response.errorBody().string(), null);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Integer>> call, Throwable t) {
                // something went completely south (like no internet connection)
                Log.d("ClypAPI", "FAILURE: " + t.getMessage());
                cb.onComplete(t.getMessage(), null);
            }
        });
    }

    public void getFeaturedLists(final FeatureCallback cb) {
        Call<List<Track>> featuredCall = service.getFeaturedTracks();
        Call<List<Track>> trendingCall = service.getPopularTracks();
        Call<List<Track>> recentCall = service.getRecentTracks();

        featuredCall.enqueue(new Callback<List<Track>>() {
            @Override
            public void onResponse(Call<List<Track>> call, Response<List<Track>> response) {
                if (response.isSuccessful()) {
                    cb.onComplete(null, response.body(), Flags.CALLBACK_FEATURE_FEATURE);
                } else {
                    // error response, no access to resource?
                    try {
                        Log.d("ClypAPI", "ERROR: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    try {
                        cb.onComplete(response.errorBody().string(), null, 0);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Track>> call, Throwable t) {
                // something went completely south (like no internet connection)
                Log.d("ClypAPI", "FAILURE: " + t.getMessage());
                cb.onComplete(t.getMessage(), null, 0);
            }
        });

        trendingCall.enqueue(new Callback<List<Track>>() {
            @Override
            public void onResponse(Call<List<Track>> call, Response<List<Track>> response) {
                if (response.isSuccessful()) {
                    cb.onComplete(null, response.body(), Flags.CALLBACK_FEATURE_TRENDING);
                } else {
                    // error response, no access to resource?
                    try {
                        Log.d("ClypAPI", "ERROR: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    try {
                        cb.onComplete(response.errorBody().string(), null, 0);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Track>> call, Throwable t) {
                // something went completely south (like no internet connection)
                Log.d("ClypAPI", "FAILURE: " + t.getMessage());
                cb.onComplete(t.getMessage(), null, 0);
            }
        });

        recentCall.enqueue(new Callback<List<Track>>() {
            @Override
            public void onResponse(Call<List<Track>> call, Response<List<Track>> response) {
                if (response.isSuccessful()) {
                    cb.onComplete(null, response.body(), Flags.CALLBACK_FEATURE_RECENT);
                } else {
                    // error response, no access to resource?
                    try {
                        Log.d("ClypAPI", "ERROR: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    try {
                        cb.onComplete(response.errorBody().string(), null, 0);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Track>> call, Throwable t) {
                // something went completely south (like no internet connection)
                Log.d("ClypAPI", "FAILURE: " + t.getMessage());
                cb.onComplete(t.getMessage(), null, 0);
            }
        });
    }

    public void getCategories(String category, final StandardCallback cb) {
        Call<List<Track>> call = service.getCategoryTrackList(
                category
        );

        call.enqueue(new Callback<List<Track>>() {
            @Override
            public void onResponse(Call<List<Track>> call, Response<List<Track>> response) {
                if (response.isSuccessful()) {
                    cb.onComplete(null, response.body());
                } else {
                    // error response, no access to resource?
                    try {
                        Log.d("ClypAPI", "ERROR: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    try {
                        cb.onComplete(response.errorBody().string(), null);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Track>> call, Throwable t) {
                // something went completely south (like no internet connection)
                Log.d("ClypAPI", "FAILURE: " + t.getMessage());
                cb.onComplete(t.getMessage(), null);
            }
        });
    }

    public void getCategoryTracks(String category, final StandardCallback cb) {
        Call<List<Track>> call = service.getCategoryTrackList(
                category
        );

        call.enqueue(new Callback<List<Track>>() {
            @Override
            public void onResponse(Call<List<Track>> call, Response<List<Track>> response) {
                if (response.isSuccessful()) {
                    cb.onComplete(null, response.body());
                } else {
                    // error response, no access to resource?
                    try {
                        Log.d("ClypAPI", "ERROR: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    try {
                        cb.onComplete(response.errorBody().string(), null);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Track>> call, Throwable t) {
                // something went completely south (like no internet connection)
                Log.d("ClypAPI", "FAILURE: " + t.getMessage());
                cb.onComplete(t.getMessage(), null);
            }
        });
    }

    public void authenticate(String username, String password, final AuthCallback authCallback) throws IOException {
        final ProgressDialog progress = new ProgressDialog(context);
        progress.setTitle("Loading");
        progress.setMessage("Signing you in...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();

        Call<AuthResponse> call = service.getAuthToken(
                "password",
                username,
                password
        );

        call.enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                if (response.isSuccessful()) {
                    token = response.body().getAccess_token();
                    expiry = response.body().getExpires_in();
                    progress.dismiss();
                    authCallback.onAuthComplete(null, true);
                } else {
                    // error response, no access to resource?
                    try {
                        Log.d("ClypAPI", "ERROR: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    try {
                        authCallback.onAuthComplete(response.errorBody().string(),false);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    progress.dismiss();
                }
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                // something went completely south (like no internet connection)
                Log.d("ClypAPI", "FAILURE: " + t.getMessage());
                authCallback.onAuthComplete(t.getMessage(), true);
                progress.dismiss();
            }
        });
    }

    public String getToken() {
        return token;
    }

    public int getExpiry() {
        return expiry;
    }
}
