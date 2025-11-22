package edu.upc.tracksapp.track;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TrackInterface {
    @GET("/dsaApp/tracks")
    Call<List<Track>> getAll();
}
