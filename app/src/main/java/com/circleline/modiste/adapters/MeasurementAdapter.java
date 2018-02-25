package com.circleline.modiste.adapters;

import android.content.Context;
import android.icu.util.Measure;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.circleline.modiste.R;
import com.circleline.modiste.models.Customer;
import com.circleline.modiste.models.Measurement;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rahad on 25/02/2018.
 */

public class MeasurementAdapter  extends ArrayAdapter<Measurement> {


    Context context;
    List<Measurement> measurementList;
    public MeasurementAdapter(@NonNull Context context, int resource, List<Measurement> measurementList) {
        super(context, resource);
        this.measurementList = measurementList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return measurementList.size();
    }

    @Nullable
    @Override
    public Measurement getItem(int position) {
        return measurementList.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Measurement measurement = getItem(position);
        MeasurementAdapter.ViewHolder holder;
        if (convertView != null) {
            holder = (MeasurementAdapter.ViewHolder) convertView.getTag();
        } else {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_measurement, parent, false);
            holder = new MeasurementAdapter.ViewHolder(convertView);
            convertView.setTag(holder);
        }
        holder.txvw_nama.setText(measurement.getNama());


        return convertView;

    }

    public void setList(List<Measurement> measurementList) {
        this.measurementList = measurementList;
        notifyDataSetChanged();
    }

    static class ViewHolder {
        @BindView(R.id.txvw_nama)
        TextView txvw_nama;
        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
