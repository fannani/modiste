package com.circleline.modiste.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.circleline.modiste.R;
import com.circleline.modiste.models.Customer;
import com.circleline.modiste.models.Measurement;
import com.circleline.modiste.models.OrderDB;
import com.circleline.modiste.util.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailCustomerActivity extends AppCompatActivity {

    @BindView(R.id.txvw_nama)
    TextView txvw_nama;

    @BindView(R.id.txvw_alamat)
    TextView txvw_alamat;

    @BindView(R.id.txvw_notelp)
    TextView txvw_notelp;


    private long customerID;
    private Customer customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_customer);
        setTitle("Detail Customer");
        ButterKnife.bind(this);
        customerID = getIntent().getLongExtra("customerID",-1);
        setData();

    }

    void setData(){
        customer = Customer.find(Customer.class,"id = ?",String.valueOf(customerID)).get(0);
        txvw_nama.setText("Nama : "+customer.getNama());
        txvw_alamat.setText("Alamat : "+customer.getAlamat());
        txvw_notelp.setText("No Telp : "+customer.getNoTelp());
    }

    @OnClick(R.id.btn_edit)
    void onEdit(){
        Intent intent = new Intent(DetailCustomerActivity.this,CustomerFormActivity.class);
        intent.putExtra("customerID",customerID);
        startActivityForResult(intent, Constant.EDIT_CUSTOMER_REQUEST);
    }

    @OnClick(R.id.btn_hapus)
    void onHapus(){
        OrderDB.deleteAll(OrderDB.class,"id_customer = ?",String.valueOf(customer.getId()));
        Measurement.deleteAll(Measurement.class,"id_customer = ?",String.valueOf(customer.getId()));
        customer.delete();
        setResult(RESULT_OK,getIntent());
        finish();
    }

    @OnClick(R.id.btn_measurement)
    void onMeasurementList(){
        Intent intent = new Intent(DetailCustomerActivity.this,MeasurementListActivity.class);
        intent.putExtra("customerID",customerID);
        startActivity(intent);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constant.EDIT_CUSTOMER_REQUEST ) {
            if (resultCode == RESULT_OK) {
                setData();
            }
        }
    }
}
