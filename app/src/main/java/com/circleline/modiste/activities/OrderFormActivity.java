package com.circleline.modiste.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.circleline.modiste.R;
import com.circleline.modiste.adapters.CustomerSpinnerAdapter;
import com.circleline.modiste.adapters.MeasurementSpinnerAdapter;
import com.circleline.modiste.models.Customer;
import com.circleline.modiste.models.Measurement;
import com.circleline.modiste.models.OrderDB;
import com.circleline.modiste.util.Constant;
import com.circleline.modiste.util.DateUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderFormActivity extends AppCompatActivity {

    @BindView(R.id.spnr_pemesan)
    Spinner spnr_pemesan;

    @BindView(R.id.spnr_measurement)
    Spinner spnr_measurement;

    @BindView(R.id.edtx_tglselesai)
    EditText edtx_tglselesai;


    @BindView(R.id.edtx_cutting)
    EditText edtx_cutting;

    @BindView(R.id.edtx_harga)
    EditText edtx_harga;




    List<Customer> customerList = new ArrayList<Customer>();
    private CustomerSpinnerAdapter customerAdapter;

    List<Measurement> measurementList = new ArrayList<Measurement>();
    private MeasurementSpinnerAdapter measurementAdapter;

    private Customer selectedCustomer;
    private Measurement selectedMeasurement;
    private long orderID = -1;
    private OrderDB order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_form);
        ButterKnife.bind(this);

        List<Customer> list = Customer.listAll(Customer.class);
        generateCustomerList(list);

        List<Measurement> tmpList = new ArrayList<Measurement>();
        generateMeasurementList(tmpList);



        measurementAdapter = new MeasurementSpinnerAdapter(OrderFormActivity.this,android.R.layout.simple_spinner_item,measurementList);
        spnr_measurement.setAdapter(measurementAdapter);
        spnr_measurement.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    selectedMeasurement = measurementAdapter.getItem(i);
                    Toast.makeText(OrderFormActivity.this,selectedMeasurement.getNama().toString(),Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        customerAdapter = new CustomerSpinnerAdapter(OrderFormActivity.this,android.R.layout.simple_spinner_item,customerList);
        spnr_pemesan.setAdapter(customerAdapter);
        spnr_pemesan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    selectedCustomer = customerAdapter.getItem(i);
                    Toast.makeText(OrderFormActivity.this,selectedCustomer.getNama().toString(),Toast.LENGTH_LONG).show();
                    List<Measurement> measurements = Measurement.find(Measurement.class,"id_customer = ?",selectedCustomer.getId().toString());
                    generateMeasurementList(measurements);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        if(getIntent().hasExtra("orderID")){
            orderID = getIntent().getLongExtra("orderID",-1);
            order = OrderDB.find(OrderDB.class,"id = ?",String.valueOf(orderID)).get(0);
            edtx_tglselesai.setText(order.getTglSelesai());
            edtx_cutting.setText(order.getCutting());
            edtx_harga.setText(order.getHarga());
            selectedCustomer = Customer.find(Customer.class,"id = ?",String.valueOf(order.getIdCustomer())).get(0);
            int size = customerList.size();
            int pemesan = -1;
            for(int i = 0;i< size;i++){
                if(customerList.get(i).getId() != null) {
                    if (customerList.get(i).getId().equals(selectedCustomer.getId())) {
                        pemesan = i;
                    }
                }
            }
            spnr_pemesan.setSelection(pemesan);

            selectedMeasurement = Measurement.find(Measurement.class,"id = ?",String.valueOf(order.getIdMeasurement())).get(0);
            List<Measurement> measurements = Measurement.find(Measurement.class,"id_customer = ?",selectedCustomer.getId().toString());
            generateMeasurementList(measurements);

            size = measurementList.size();
            int measurement = -1;
            for(int i = 0;i< size;i++){
                if(measurementList.get(i).getId() != null) {
                    if (measurementList.get(i).getId().equals(selectedMeasurement.getId())) {
                        measurement = i;
                    }
                }
            }

            spnr_measurement.setSelection(measurement);
        }
    }

    @OnClick(R.id.btn_tambahpemesan)
    void onTambahPemesan() {
        Intent intent = new Intent(OrderFormActivity.this, CustomerFormActivity.class);
        startActivityForResult(intent, Constant.CREATE_CUSTOMER_REQUEST);
    }

    @OnClick(R.id.btn_tambahmeasurement)
    void onTambahMeasurement(){
        Intent intent = new Intent(OrderFormActivity.this, MeasurementFormActivity.class);
        intent.putExtra("idCustomer",selectedCustomer.getId());
        startActivityForResult(intent, Constant.CREATE_MEASUREMENT_REQUEST);
    }

    @OnClick(R.id.btn_simpan)
    void onSimpan(){
        if(getIntent().hasExtra("orderID")){
            long id = getIntent().getLongExtra("orderID",-1);
            OrderDB updatedOrder = OrderDB.find(OrderDB.class,"id = ?",String.valueOf(id)).get(0);
            updatedOrder.setTglSelesai(edtx_tglselesai.getText().toString());
            updatedOrder.setCutting(edtx_cutting.getText().toString());
            updatedOrder.setHarga(edtx_harga.getText().toString());
            updatedOrder.setIdCustomer(selectedCustomer.getId().toString());
            updatedOrder.setIdMeasurement(selectedMeasurement.getId().toString());
            updatedOrder.save();
        } else {
            OrderDB neworder = new OrderDB(selectedCustomer.getId().toString()
                    ,selectedMeasurement.getId().toString()
                    , DateUtil.getDate()
                    ,edtx_tglselesai.getText().toString()
                    ,edtx_cutting.getText().toString()
                    ,edtx_harga.getText().toString()
            );
            neworder.save();
        }
        setResult(RESULT_OK);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constant.CREATE_CUSTOMER_REQUEST ) {
            if (resultCode == RESULT_OK) {
                List<Customer> list = Customer.listAll(Customer.class);
                generateCustomerList(list);
                customerAdapter.notifyDataSetChanged();
                spnr_pemesan.setSelection(list.size()+1);
            }
        } else if(requestCode == Constant.CREATE_MEASUREMENT_REQUEST){
            if(resultCode == RESULT_OK){
                List<Measurement> measurements = Measurement.find(Measurement.class,"id_customer = ?",selectedCustomer.getId().toString());
                generateMeasurementList(measurements);
                measurementAdapter.notifyDataSetChanged();
                spnr_measurement.setSelection(measurements.size()+1);
            }
        }
    }

    private void generateCustomerList(List<Customer> list){
        customerList.clear();
        Customer hint = new Customer("Pilih Pemesan","-1","-1");
        customerList.add(hint);
        customerList.addAll(list);
    }
    private void generateMeasurementList(List<Measurement> list){
        measurementList.clear();
        Measurement hint = new Measurement();
        hint.setNama("Pilih Ukuran");
        measurementList.add(hint);
        measurementList.addAll(list);
    }
}
