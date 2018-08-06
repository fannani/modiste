package com.circleline.modiste.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.circleline.modiste.R;
import com.circleline.modiste.adapters.CustomerAdapter;
import com.circleline.modiste.adapters.MeasurementAdapter;
import com.circleline.modiste.models.Customer;
import com.circleline.modiste.models.Measurement;
import com.circleline.modiste.util.Constant;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MeasurementListActivity extends AppCompatActivity {

    @BindView(R.id.lsvw_measurement)
    ListView lsvw_measurement;

    private long customerid;
    private List<Measurement> measurementList;
    private MeasurementAdapter measurementAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measurement_list);
        setTitle("Daftar Ukuran");
        ButterKnife.bind(this);
        customerid = getIntent().getLongExtra("customerID",-1);
        measurementList = Measurement.find(Measurement.class,"id_customer = ?",String.valueOf(customerid));
        measurementAdapter = new MeasurementAdapter(MeasurementListActivity.this,android.R.layout.simple_list_item_1,measurementList);
        lsvw_measurement.setAdapter(measurementAdapter);
        lsvw_measurement.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MeasurementListActivity.this,DetailMeasurementActivity.class);
                intent.putExtra("measurementID",measurementAdapter.getItem(i).getId());
                startActivityForResult(intent, Constant.DETAIL_MEASUREMENT_REQUEST);
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == Constant.DETAIL_MEASUREMENT_REQUEST){
            if(resultCode == RESULT_OK){
                measurementList =  Measurement.listAll(Measurement.class);
                measurementAdapter.setList(measurementList);
            }
        }
    }
}

