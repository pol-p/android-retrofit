package edu.upc.tracksapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager; // IMPORTANTE
import androidx.recyclerview.widget.RecyclerView; // IMPORTANTE

import java.util.List;

import edu.upc.tracksapp.track.API;
import edu.upc.tracksapp.track.Track;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListTracksActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_tracks);

        // 1. Inicializar vistas
        recyclerView = findViewById(R.id.recyclerViewTracks);
        progressBar = findViewById(R.id.progressBar);

        // 2. Configurar el RecyclerView (¡Obligatorio decirle cómo se organizan!)
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Llamada a la API
        API.getTracks().getAll().enqueue(new Callback<List<Track>>() {
            @Override
            public void onResponse(Call<List<Track>> call, Response<List<Track>> response) {
                progressBar.setVisibility(View.GONE); // Ocultar carga

                if (response.isSuccessful()) {
                    List<Track> tracks = response.body();

                    // 3. Crear y asignar el adaptador con los datos recibidos
                    TracksAdapter adapter = new TracksAdapter(tracks);
                    recyclerView.setAdapter(adapter);

                    Log.i("TracksApp", "Cargadas " + tracks.size() + " canciones");
                } else {
                    Toast.makeText(getApplicationContext(), "Error Code: " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Track>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Error de red: " + t.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("TracksApp", "Error: " + t.getMessage());
            }
        });
    }
}