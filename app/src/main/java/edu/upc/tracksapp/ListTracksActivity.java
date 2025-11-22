package edu.upc.tracksapp;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import retrofit2.Callback;

import edu.upc.tracksapp.track.API;
import edu.upc.tracksapp.track.Track;
import edu.upc.tracksapp.track.TrackInterface;
import retrofit2.Call;
import retrofit2.Response;

public class ListTracksActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_tracks);

        TrackInterface api = API.getTracks();
        Call<List<Track>> call = api.getAll();
        call.enqueue(new Callback<List<Track>>() {
            @Override
            public void onResponse(Call<List<Track>> call, Response<List<Track>> response) {
                if (response.isSuccessful()) {
                    List<Track> tracks = response.body();
                    Log.i("TracksAppGood", "Número de pistas recibidas: " + tracks.size());
                    // actualizar UI
                }
            }
            @Override
            public void onFailure(Call<List<Track>> call, Throwable t) {
                // manejar error
                Log.i("TracksAppFailure", "Número de pistas recibidas: ");

            }
        });
    }
}

