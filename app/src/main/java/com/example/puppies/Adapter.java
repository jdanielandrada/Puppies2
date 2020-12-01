package com.example.puppies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private Context mCtx;
    private List<Perros> perroList;

    public Adapter(Context mCtx, List<Perros> perroList) {
        this.mCtx = mCtx;
        this.perroList = perroList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.list_layout, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Perros perro = perroList.get(position);

        Glide.with(mCtx)
                .load(perro.getFoto())
                .into(holder.imageView);

        holder.textNombres.setText(perro.getNombre());
        holder.textViewDesc.setText(String.valueOf(perro.getDesc()));

    }

    @Override
    public int getItemCount() {
        return perroList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textNombres, textViewDesc;
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textNombres = itemView.findViewById(R.id.textNombres);
            textViewDesc = itemView.findViewById(R.id.textViewDesc);
            imageView = itemView.findViewById(R.id.imageView);

        }
    }
}
