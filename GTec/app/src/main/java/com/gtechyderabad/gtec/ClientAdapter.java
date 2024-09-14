package com.gtechyderabad.gtec;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;
import android.widget.TextView;

import java.util.List;

public class ClientAdapter extends RecyclerView.Adapter<ClientAdapter.ClientViewHolder> {

    private final List<Client> clientList;

    public ClientAdapter(List<Client> clientList) {
        this.clientList = clientList;
    }

    @NonNull
    @Override
    public ClientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.client_card, parent, false);
        return new ClientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClientViewHolder holder, int position) {
        Client client = clientList.get(position);
        holder.profileImage.setImageResource(client.getImageResourceId());
        holder.quoteTextView.setText(client.getQuote());
        holder.nameTextView.setText(client.getName());
    }

    @Override
    public int getItemCount() {
        return clientList.size();
    }

    static class ClientViewHolder extends RecyclerView.ViewHolder {
        CircleImageView profileImage;
        TextView quoteTextView;
        TextView nameTextView;

        public ClientViewHolder(@NonNull View itemView) {
            super(itemView);
            profileImage = itemView.findViewById(R.id.profileImage);
            quoteTextView = itemView.findViewById(R.id.quoteTextView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
        }
    }
}
