package com.circleline.modiste.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.circleline.modiste.R;
import com.circleline.modiste.models.Customer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CustomerFormActivity extends AppCompatActivity {

    @BindView(R.id.edtx_nama) EditText edtx_nama;
    @BindView(R.id.edtx_alamat) EditText edtx_alamat;
    @BindView(R.id.edtx_notelp) EditText edtx_notelp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_form);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_simpan) void onSubmit(){
        Customer customer = new Customer(edtx_nama.getText().toString(),edtx_alamat.getText().toString(),edtx_notelp.getText().toString());
        customer.save();
        setResult(RESULT_OK);
        finish();
    }

}
