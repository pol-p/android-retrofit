package edu.upc.tracksapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import edu.upc.tracksapp.track.API;
import edu.upc.tracksapp.track.Track;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListTracksActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TracksAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_tracks);

        recyclerView = findViewById(R.id.recyclerViewTracks);
        progressBar = findViewById(R.id.progressBar);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAllTracks(); // Cargar siempre al mostrar la pantalla
    }

    private void getAllTracks() {
        progressBar.setVisibility(View.VISIBLE);
        API.getTracks().getAll().enqueue(new Callback<List<Track>>() {
            @Override
            public void onResponse(Call<List<Track>> call, Response<List<Track>> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    List<Track> tracks = response.body();

                    // Configuramos el adaptador con el listener
                    adapter = new TracksAdapter(tracks, new TracksAdapter.OnTrackClickListener() {
                        @Override
                        public void onTrackClick(Track track) {
                            // Al tocar la fila: IR A EDITAR
                            Intent intent = new Intent(ListTracksActivity.this, AddTrackActivity.class);
                            intent.putExtra("ID", track.getId());
                            intent.putExtra("TITLE", track.getTitle());
                            intent.putExtra("SINGER", track.getSinger());
                            startActivity(intent);
                        }

                        @Override
                        public void onTrackDelete(String id) {
                            // Al tocar borrar: LLAMAR A LA API
                            deleteTrack(id);
                        }
                    });
                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(ListTracksActivity.this, "Error: " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Track>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(ListTracksActivity.this, "Error de red: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteTrack(String id) {
        API.getTracks().deleteTrack(id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ListTracksActivity.this, "Borrado correctamente", Toast.LENGTH_SHORT).show();
                    getAllTracks(); // Recargar la lista
                } else {
                    Toast.makeText(ListTracksActivity.this, "No se pudo borrar", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(ListTracksActivity.this, "Error al conectar", Toast.LENGTH_SHORT).show();
            }
        });
    }
}