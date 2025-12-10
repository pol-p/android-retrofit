package edu.upc.tracksapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import edu.upc.tracksapp.track.API;
import edu.upc.tracksapp.track.Track;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddTrackActivity extends AppCompatActivity {
    private EditText etTitle;
    private EditText etSinger;
    private Button btnSave;
    private String trackId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_track);

        etTitle = findViewById(R.id.etTitle);
        etSinger = findViewById(R.id.etSinger);
        btnSave = findViewById(R.id.btnSave);

        if (getIntent().hasExtra("ID")) {
            trackId = getIntent().getStringExtra("ID");
            etTitle.setText(getIntent().getStringExtra("TITLE"));
            etSinger.setText(getIntent().getStringExtra("SINGER"));
            btnSave.setText("Actualizar");
        }

        btnSave.setOnClickListener(v -> saveTrack());
    }

    private void saveTrack() {
        String title = etTitle.getText().toString();
        String singer = etSinger.getText().toString();

        if (title.isEmpty() || singer.isEmpty()) {
            Toast.makeText(this, "Rellena todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        Track track = new Track(trackId, title, singer);

        if (trackId == null) {
            createNewTrack(track);
        } else {
            updateExistingTrack(track);
        }
    }

    private void createNewTrack(Track track) {
        API.getTracks().createTrack(track).enqueue(new Callback<Track>() {
            @Override
            public void onResponse(Call<Track> call, Response<Track> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(AddTrackActivity.this, "Creado correctamente", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(AddTrackActivity.this, "Error: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Track> call, Throwable t) {
                Toast.makeText(AddTrackActivity.this, "Fallo de red", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateExistingTrack(Track track) {
        API.getTracks().updateTrack(track).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(AddTrackActivity.this, "Actualizado", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(AddTrackActivity.this, "Error al actualizar", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(AddTrackActivity.this, "Fallo de red", Toast.LENGTH_SHORT).show();
            }
        });
    }
}