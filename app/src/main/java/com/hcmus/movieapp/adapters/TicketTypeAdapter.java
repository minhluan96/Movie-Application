package com.hcmus.movieapp.adapters;

import android.content.Context;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hcmus.movieapp.R;
import com.hcmus.movieapp.models.Ticket;
import com.hcmus.movieapp.utils.Constant;

import java.util.List;

public class TicketTypeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Ticket> tickets;
    private Context context;
    private TicketChangeListener ticketChangeListener;
    private boolean isSetup = true;

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
            increaseNumberOfTicket(ticketTypeViewHolder, ticket);
        });

        if (i == Constant.SeatType.VIP - 1 && isSetup) { // position in list items
            ticketTypeViewHolder.txtQuantity.setText("1"); // set default value
            ticketTypeViewHolder.imgSubtract.setVisibility(View.VISIBLE);
            isSetup = false;
        }

        ticketTypeViewHolder.imgSubtract.setOnClickListener(v -> {
            int quantity = getCurrentQuantityOfTicket(ticketTypeViewHolder);
            ticketChangeListener.onSubtractButtonChanged(quantity, ticket.getPrice(), ticket);
            quantity--;
            ticketTypeViewHolder.txtQuantity.setText(String.valueOf(quantity));
            if (quantity == 0) {
                ticketTypeViewHolder.imgSubtract.setVisibility(View.INVISIBLE);
                return;
            }

        });
    }

    public void increaseNumberOfTicket(TicketTypeViewHolder ticketTypeViewHolder, Ticket ticket) {
        int quantity = getCurrentQuantityOfTicket(ticketTypeViewHolder);
        quantity++;
        if (quantity > 10) return;
        ticketTypeViewHolder.txtQuantity.setText(String.valueOf(quantity));
        ticketChangeListener.onAddButtonChanged(quantity, ticket.getPrice(), ticket);
    }

    private int getCurrentQuantityOfTicket(TicketTypeViewHolder ticketTypeViewHolder) {
        String quantityStr = ticketTypeViewHolder.txtQuantity.getText().toString();
        int quantity = Integer.parseInt(quantityStr);
        return quantity;
    }

    public interface TicketChangeListener {
        void onAddButtonChanged(int totalQuantity, double pricePerTicket, Ticket ticket);
        void onSubtractButtonChanged(int totalQuantity, double pricePerTicket, Ticket ticket);
    }

    public void setTicketChangeListener(TicketChangeListener ticketChangeListener) {
        this.ticketChangeListener = ticketChangeListener;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    @Override
    public int getItemCount() {
        return tickets.size();
    }

    public class TicketTypeViewHolder extends RecyclerView.ViewHolder {

        private TextView txtNameTicketType, txtPrice, txtQuantity;
        private ImageView imgAdd, imgSubtract;

        public TicketTypeViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setTag(this);
            txtNameTicketType = itemView.findViewById(R.id.txtNameTicket);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            txtQuantity = itemView.findViewById(R.id.txtQuantity);
            imgAdd = itemView.findViewById(R.id.imgAdd);
            imgSubtract = itemView.findViewById(R.id.imgSubtract);
        }
    }
}
