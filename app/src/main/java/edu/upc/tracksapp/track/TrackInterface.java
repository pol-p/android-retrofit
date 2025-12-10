package edu.upc.tracksapp.track;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface TrackInterface {
    @GET("/dsaApp/tracks")
    Call<List<Track>> getAll();
    @POST("/dsaApp/tracks")
    Call<Track> createTrack(@Body Track track);

    @PUT("/dsaApp/tracks")
    Call<Void> updateTrack(@Body Track track);

    @DELETE("/dsaApp/tracks/{id}")
    Call<Void> deleteTrack(@Path("id") String id);
}
