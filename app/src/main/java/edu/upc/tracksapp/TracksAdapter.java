package edu.upc.tracksapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import edu.upc.tracksapp.track.Track;

public class TracksAdapter extends RecyclerView.Adapter<TracksAdapter.ViewHolder> {

    private List<Track> tracks;

    // Constructor: Aquí pasamos la lista de datos
    public TracksAdapter(List<Track> tracks) {
        this.tracks = tracks;
    }

    // 1. Inflar el layout (crear la vista visual de la fila)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_track, parent, false);
        return new ViewHolder(view);
    }

    // 2. Asignar datos: coge la canción de la posición 'position' y la pinta
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Track track = tracks.get(position);
        holder.titleTextView.setText(track.getTitle());
        holder.singerTextView.setText(track.getSinger());
        holder.idTextView.setText("ID: " + track.getId());
    }

    // 3. Cantidad total de elementos
    @Override
    public int getItemCount() {
        return tracks.size();
    }

    // Clase interna ViewHolder: Mantiene las referencias a los elementos visuales
    // para no tener que buscarlos con findViewById cada vez (mejora rendimiento)
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView;
        public TextView singerTextView;
        public TextView idTextView;

        public ViewHolder(View view) {
            super(view);
            titleTextView = view.findViewById(R.id.textViewTitle);
            singerTextView = view.findViewById(R.id.textViewSinger);
            idTextView = view.findViewById(R.id.textViewId);
        }
    }
}