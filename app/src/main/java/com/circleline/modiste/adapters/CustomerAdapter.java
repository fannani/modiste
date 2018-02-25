package com.circleline.modiste.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.circleline.modiste.R;
import com.circleline.modiste.models.Customer;
import com.circleline.modiste.models.OrderDB;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rahad on 18/02/2018.
 */

public class CustomerAdapter  extends ArrayAdapter<Customer> {


    Context context;
    List<Customer> customerList;
    public CustomerAdapter(@NonNull Context context, int resource, List<Customer> customerList) {
        super(context, resource);
        this.customerList = customerList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return customerList.size();
    }

    @Nullable
    @Override
    public Customer getItem(int position) {
        return customerList.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Customer customer = getItem(position);
        CustomerAdapter.ViewHolder holder;
        if (convertView != null) {
            holder = (CustomerAdapter.ViewHolder) convertView.getTag();
        } else {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_customer, parent, false);
            holder = new CustomerAdapter.ViewHolder(convertView);
            convertView.setTag(holder);
        }
        holder.txvw_nama.setText(customer.getNama());
        holder.txvw_alamat.setText(customer.getAlamat());
        holder.txvw_notelp.setText(customer.getNoTelp());

        return convertView;

    }

    public void setList(List<Customer> customerList) {
        this.customerList = customerList;
        notifyDataSetChanged();
    }

    static class ViewHolder {
        @BindView(R.id.txvw_nama)
        TextView txvw_nama;
        @BindView(R.id.txvw_alamat)
        TextView txvw_alamat;
        @BindView(R.id.txvw_notelp)
        TextView txvw_notelp;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
