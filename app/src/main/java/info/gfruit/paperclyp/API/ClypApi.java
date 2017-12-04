package info.gfruit.paperclyp.API;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import info.gfruit.paperclyp.API.Callback.AuthCallback;
import info.gfruit.paperclyp.API.Callback.FeatureCallback;
import info.gfruit.paperclyp.API.Callback.StandardCallback;
import info.gfruit.paperclyp.API.Response.AuthResponse;
import info.gfruit.paperclyp.API.Response.NotificationResponse;
import info.gfruit.paperclyp.API.Structure.Track;
import info.gfruit.paperclyp.API.Structure.User;
import info.gfruit.paperclyp.Flags;
import info.gfruit.paperclyp.Util.CallbackBuilder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lite20 on 7/16/2017.
 */

public class ClypApi implements Runnable, Callback<AuthResponse> {
    private final Context context;

    private String refresh_token;

    public ClypApiInterface service;

    public ClypSoundwaveApiInterface soundwaveService;

    private String token;

    private List<StandardCallback> pendingCalls = new ArrayList<>();

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

    public ClypApi(Context context, String base, String token, String refresh_token, int expiry) {
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
        this.refresh_token = refresh_token;
        new Handler().postDelayed(this, expiry * 1000);
    }

    public void refreshToken() {
        Call<AuthResponse> call = service.refreshToken(
                "refresh_token",
                refresh_token
        );

        call.enqueue(this);
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
                // TODO handle errors and display graphics to inform users
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

    public void getNotifications(final StandardCallback cb) {
        Call<NotificationResponse> call = service.getNotifications(
                "Bearer " + token
        );

        Callback<NotificationResponse> callback = new Callback<NotificationResponse>() {

            public boolean tried = false;

            @Override
            public void onResponse(Call<NotificationResponse> call, Response<NotificationResponse> response) {
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
                        if(response.message().contains("Unauthorized") && !tried) {
                            tried = true;
                            pendingCalls.add(CallbackBuilder.construct(call, this));
                            refreshToken();
                        } else {
                            cb.onComplete(response.errorBody().string(), null);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<NotificationResponse> call, Throwable t) {
                // something went completely south (like no internet connection)
                Log.d("ClypAPI", "FAILURE: " + t.getMessage());
                cb.onComplete(t.getMessage(), null);
            }
        };
    }

    public void getUploads(final StandardCallback cb) {
        Call<User> call = service.getSelf(
                "Bearer " + token
        );

        Callback<User> callback = new Callback<User>() {
            boolean tried = false;
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
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
                        if(response.message().contains("Unauthorized") && !tried) {
                            tried = true;
                            pendingCalls.add(CallbackBuilder.construct(call, this));
                            refreshToken();
                        } else {
                            cb.onComplete(response.errorBody().string(), null);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                // something went completely south (like no internet connection)
                Log.d("ClypAPI", "FAILURE: " + t.getMessage());
                cb.onComplete(t.getMessage(), null);
            }
        };

        call.enqueue(callback);
    }

    public void getSelf(final StandardCallback cb) {
        Call<User> call = service.getSelf(
                "Bearer " + token
        );

        Callback<User> callback = new Callback<User>() {
            boolean tried = false;
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
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
                        if(response.message().contains("Unauthorized") && !tried) {
                            tried = true;
                            pendingCalls.add(CallbackBuilder.construct(call, this));
                            refreshToken();
                        } else {
                            cb.onComplete(response.errorBody().string(), null);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                // something went completely south (like no internet connection)
                Log.d("ClypAPI", "FAILURE: " + t.getMessage());
                cb.onComplete(t.getMessage(), null);
            }
        };

        call.enqueue(callback);
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
                    refresh_token = response.body().getRefresh_token();
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
                        authCallback.onAuthComplete(response.errorBody().string(), false);
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

    /**
     * We extend Runnable to simplify setting a timer to refresh tokens
     */
    @Override
    public void run() {
        this.refreshToken();
    }

    /**
     * We extend the Call class to simplify refreshing tokens
     * @param call
     * @param response
     */
    @Override
    public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
        if (response.isSuccessful()) {
            token = response.body().getAccess_token();
            refresh_token = response.body().getRefresh_token();
            new Handler().postDelayed(this, response.body().getExpires_in() * 1000);
            // issue all the calls that were pending on the refresh
            for(int i = 0; i < pendingCalls.size(); i++) {
                pendingCalls.get(i).onComplete(null, null);
            }
        } else {
            // error response, no access to resource?
            try {
                Log.d("ClypAPI", "ERROR: " + response.errorBody().string());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onFailure(Call<AuthResponse> call, Throwable t) {}
}
