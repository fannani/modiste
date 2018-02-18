package com.circleline.modiste.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.circleline.modiste.models.Customer;

import java.util.List;

/**
 * Created by rahad on 07/01/2018.
 */

public class CustomerSpinnerAdapter extends ArrayAdapter<Customer> {

    private List<Customer> customerList;
    private Context context;
    public CustomerSpinnerAdapter(@NonNull Context context, int resource, List<Customer> customerList) {
        super(context, resource);

        this.customerList = customerList;
        this.context = context;
    }

    @Override
    public boolean isEnabled(int position) {
        if (position == 0) {
            // Disable the first item from Spinner
            // First item will be use for hint
            return false;
        } else {
            return true;
        }
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

        TextView label = new TextView(context);
        label.setTextColor(Color.BLACK);
        label.setText(customerList.get(position).getNama());
        return label;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView label = new TextView(context);
        if (position == 0) {
            // Set the hint text color gray
            label.setTextColor(Color.GRAY);
        } else {
            label.setTextColor(Color.BLACK);
        }
        label.setText(customerList.get(position).getNama());
        return label;
    }



}
