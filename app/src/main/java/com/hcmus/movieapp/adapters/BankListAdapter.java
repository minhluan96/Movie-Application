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
import com.hcmus.movieapp.models.Bank;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BankListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Bank> bankList;
    private Context context;
    private BankListener bankListener;

    public BankListAdapter(List<Bank> bankList, Context context) {
        this.bankList = bankList;
        this.context = context;
    }

    public void setBankListener(BankListener bankListener) {
        this.bankListener = bankListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.bank_item_layout, viewGroup, false);
        return new BankViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        BankViewHolder bankViewHolder = (BankViewHolder) viewHolder;
        Bank bank = bankList.get(i);
        bankViewHolder.txtName.setText(bank.getName());
        Picasso.get().load(bank.getImgResource()).error(R.drawable.credit_card).into(bankViewHolder.imgThumb);
        bankViewHolder.itemView.setOnClickListener(v -> {
            bankListener.onBankItemSelected(i);
        });
    }

    @Override
    public int getItemCount() {
        return bankList.size();
    }

    public interface BankListener {
        void onBankItemSelected(int pos);
    }

    class BankViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgThumb;
        private TextView txtName;

        public BankViewHolder(@NonNull View itemView) {
            super(itemView);
            imgThumb = itemView.findViewById(R.id.imgBank);
            txtName = itemView.findViewById(R.id.txtName);
        }
    }
}
