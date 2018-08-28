package com.maciejgontar.zoogs;

import com.maciejgontar.zoogs.model.GifData;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GiphyService {

    @GET("/v1/gifs/search")
    Single<GifData> getGifData(@Query("api_key") String apiKey,
                               @Query("q") String q,
                               @Query("limit") int limit);
}