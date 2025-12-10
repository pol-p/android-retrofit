package edu.upc.tracksapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import edu.upc.tracksapp.track.Track;

public class TracksAdapter extends RecyclerView.Adapter<TracksAdapter.ViewHolder> {

    private List<Track> tracks;
    private OnTrackClickListener listener;

    public interface OnTrackClickListener {
        void onTrackClick(Track track);   // Para editar
        void onTrackDelete(String id);    // Para borrar
    }

    public TracksAdapter(List<Track> tracks, OnTrackClickListener listener) {
        this.tracks = tracks;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_track, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Track track = tracks.get(position);
        holder.titleTextView.setText(track.getTitle());
        holder.singerTextView.setText(track.getSinger());
        holder.idTextView.setText("ID: " + track.getId());

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onTrackClick(track);
        });

        holder.btnDelete.setOnClickListener(v -> {
            if (listener != null) listener.onTrackDelete(track.getId());
        });
    }

    @Override
    public int getItemCount() {
        return tracks.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView, singerTextView, idTextView;
        public ImageButton btnDelete;

        public ViewHolder(View view) {
            super(view);
            titleTextView = view.findViewById(R.id.textViewTitle);
            singerTextView = view.findViewById(R.id.textViewSinger);
            idTextView = view.findViewById(R.id.textViewId);
            btnDelete = view.findViewById(R.id.btnDelete);
        }
    }
}