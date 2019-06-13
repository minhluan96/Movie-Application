package com.hcmus.movieapp.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hcmus.movieapp.R;
import com.hcmus.movieapp.models.PaymentMethod;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PaymentMethodAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<PaymentMethod> paymentMethods;
    private Context context;
    private PaymentMethodListener listener;

    public PaymentMethodAdapter(List<PaymentMethod> paymentMethods, Context context) {
        this.paymentMethods = paymentMethods;
        this.context = context;
    }

    public void setListener(PaymentMethodListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.payment_method_item, viewGroup, false);
        return new PaymentMethodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        PaymentMethodViewHolder paymentMethodViewHolder = (PaymentMethodViewHolder) viewHolder;
        PaymentMethod paymentMethod = paymentMethods.get(i);
        if (paymentMethod.getImgUrl().isEmpty()) {
            Picasso.get().load(R.drawable.credit_card).into(paymentMethodViewHolder.imgCreditCard);
        } else {
            Picasso.get().load(paymentMethod.getImgUrl()).error(R.drawable.credit_card).into(paymentMethodViewHolder.imgCreditCard);
        }
        paymentMethodViewHolder.txtCreditCard.setText(paymentMethod.getName());
        paymentMethodViewHolder.itemView.setOnClickListener(v -> {
            listener.onMethodSelected(i);
        });
    }

    public interface PaymentMethodListener {
        void onMethodSelected(int pos);
    }

    @Override
    public int getItemCount() {
        return paymentMethods.size();
    }

    class PaymentMethodViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgCreditCard;
        private TextView txtCreditCard;

        public PaymentMethodViewHolder(@NonNull View itemView) {
            super(itemView);

            imgCreditCard = itemView.findViewById(R.id.imgCreditCard);
            txtCreditCard = itemView.findViewById(R.id.txtCreditCard);
        }
    }


}
