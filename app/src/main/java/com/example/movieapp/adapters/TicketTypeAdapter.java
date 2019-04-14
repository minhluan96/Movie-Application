package com.example.movieapp.adapters;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.movieapp.R;
import com.example.movieapp.models.Ticket;

import java.util.List;

public class TicketTypeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Ticket> tickets;
    private Context context;

    public TicketTypeAdapter(List<Ticket> tickets, Context context) {
        this.tickets = tickets;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_ticket_type_layout, viewGroup, false);
        return new TicketTypeViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        TicketTypeViewHolder ticketTypeViewHolder = (TicketTypeViewHolder) viewHolder;
        Ticket ticket = tickets.get(i);
        ticketTypeViewHolder.txtNameTicketType.setText(ticket.getName());
        ticketTypeViewHolder.txtPrice.setText(ticket.getPriceString());
    }

    @Override
    public int getItemCount() {
        return tickets.size();
    }

    class TicketTypeViewHolder extends RecyclerView.ViewHolder {

        private TextView txtNameTicketType, txtPrice, txtQuantity;
        private ImageView imgAdd, imgSubtract;

        public TicketTypeViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNameTicketType = itemView.findViewById(R.id.txtNameTicket);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            txtQuantity = itemView.findViewById(R.id.txtQuantity);
            imgAdd = itemView.findViewById(R.id.imgAdd);
            imgSubtract = itemView.findViewById(R.id.imgSubtract);
        }
    }
}
