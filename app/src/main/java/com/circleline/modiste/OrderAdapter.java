package com.circleline.modiste;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.circleline.modiste.models.Customer;
import com.circleline.modiste.models.Measurement;
import com.circleline.modiste.models.OrderDB;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rahad on 23/01/2018.
 */

public class OrderAdapter extends ArrayAdapter<OrderDB> {
    Context context;
    List<OrderDB> orderList;
    public OrderAdapter(@NonNull Context context, int resource, List<OrderDB> orderList) {
        super(context, resource);
        this.orderList = orderList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return orderList.size();
    }

    @Nullable
    @Override
    public OrderDB getItem(int position) {
        return orderList.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        OrderDB order = getItem(position);
        List<Customer> customerList = Customer.find(Customer.class,"id = ?",order.getIdCustomer().toString());
        ViewHolder holder;
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_order, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        holder.txvw_pemesan.setText((customerList.size() > 0) ? customerList.get(0).getNama() : "-");
        holder.txvw_masuk.setText(order.getTglMasuk());
        holder.txvw_selesai.setText(order.getTglSelesai());
        if(order.getStatus().equals("1")){
            holder.lnly_order.setBackgroundColor(context.getResources().getColor(android.R.color.darker_gray));
        } else {
            holder.lnly_order.setBackgroundColor(context.getResources().getColor(android.R.color.white));

        }

        return convertView;

    }

    public void setList(List<OrderDB> orderList) {
        this.orderList = orderList;
        notifyDataSetChanged();
    }

    static class ViewHolder {
        @BindView(R.id.txvw_pemesan)
        TextView txvw_pemesan;
        @BindView(R.id.txvw_masuk)
        TextView txvw_masuk;
        @BindView(R.id.txvw_selesai)
        TextView txvw_selesai;
        @BindView(R.id.lnly_order)
        LinearLayout lnly_order;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }


}
