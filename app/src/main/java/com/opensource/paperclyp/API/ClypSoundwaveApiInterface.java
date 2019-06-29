package com.opensource.paperclyp.API;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by lite20 on 7/16/2017.
 */

public interface ClypSoundwaveApiInterface {
    @GET("{id}")
    Call<List<Integer>> getSongSoundwave(@Path("id") String songId);
}
