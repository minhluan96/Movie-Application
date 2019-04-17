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
import android.widget.Toast;

import com.example.movieapp.R;
import com.example.movieapp.models.Ticket;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TicketTypeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Ticket> tickets;
    private Context context;
    private TicketChangeListener ticketChangeListener;

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

        int currentQuantity = getCurrentQuantityOfTicket(ticketTypeViewHolder);
        if (currentQuantity == 0) {
            ticketTypeViewHolder.imgSubtract.setVisibility(View.INVISIBLE);
            ticketTypeViewHolder.imgAdd.setImageResource(R.drawable.add_round_btn);
        } else {
            ticketTypeViewHolder.imgSubtract.setVisibility(View.VISIBLE);
            ticketTypeViewHolder.imgAdd.setImageResource(R.drawable.plus);
        }

        if (currentQuantity == 10) {
            ticketTypeViewHolder.imgAdd.setImageResource(R.drawable.plus_grey);
        } else {
            ticketTypeViewHolder.imgAdd.setImageResource(R.drawable.plus);
        }

        ticketTypeViewHolder.imgAdd.setOnClickListener(v -> {
            int quantity = getCurrentQuantityOfTicket(ticketTypeViewHolder);
            quantity++;
            if (quantity > 10) return;
            ticketTypeViewHolder.txtQuantity.setText(String.valueOf(quantity));
            ticketChangeListener.onAddButtonChanged(quantity, ticket.getPrice());
        });

        ticketTypeViewHolder.imgSubtract.setOnClickListener(v -> {
            int quantity = getCurrentQuantityOfTicket(ticketTypeViewHolder);
            ticketChangeListener.onSubtractButtonChanged(quantity, ticket.getPrice());
            quantity--;
            ticketTypeViewHolder.txtQuantity.setText(String.valueOf(quantity));
            if (quantity == 0) {
                ticketTypeViewHolder.imgSubtract.setVisibility(View.INVISIBLE);
                return;
            }

        });
    }

    private int getCurrentQuantityOfTicket(TicketTypeViewHolder ticketTypeViewHolder) {
        String quantityStr = ticketTypeViewHolder.txtQuantity.getText().toString();
        int quantity = Integer.parseInt(quantityStr);
        return quantity;
    }

    public interface TicketChangeListener {
        void onAddButtonChanged(int totalQuantity, double pricePerTicket);
        void onSubtractButtonChanged(int totalQuantity, double pricePerTicket);
    }

    public void setTicketChangeListener(TicketChangeListener ticketChangeListener) {
        this.ticketChangeListener = ticketChangeListener;
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
