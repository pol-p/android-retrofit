package edu.upc.tracksapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button addBtn = findViewById(R.id.buttonAddNewTrack);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickOnAddTrack(v);
            }
        });

        Button listBtn = findViewById(R.id.buttonListAllTracks);
        listBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickOnListAllTracks(v);
            }
        });
    }

    private void clickOnListAllTracks(View v) {
        Intent intent = new Intent(this, ListTracksActivity.class);
        startActivity(intent);
    }

    private void clickOnAddTrack(View v){
        Intent intent = new Intent(this, AddTrackActivity.class);
        startActivity(intent);
    }
}