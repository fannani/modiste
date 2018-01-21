package com.circleline.modiste;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.circleline.modiste.models.Customer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderFormActivity extends AppCompatActivity {

    @BindView(R.id.spnr_pemesan) Spinner spnr_pemesan;

    List<Customer> customerList = new ArrayList<Customer>();
    private CustomerSpinnerAdapter customerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_form);
        ButterKnife.bind(this);

        List<Customer> list = Customer.listAll(Customer.class);
        generateCustomerList(list);

        customerAdapter = new CustomerSpinnerAdapter(OrderFormActivity.this,android.R.layout.simple_spinner_item,customerList);
        spnr_pemesan.setAdapter(customerAdapter);
        spnr_pemesan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

               // String selectedItemText = (String) adapterView.getItemAtPosition(i);
                // If user change the default selection
                // First item is disable and it is used for hint
                if (i > 0) {
                    // Notify the selected item text
                   // Toast.makeText(getApplicationContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT).show();
                    Customer customer = customerAdapter.getItem(i);
                    Toast.makeText(OrderFormActivity.this,customer.getNama().toString(),Toast.LENGTH_LONG).show();



                }


            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @OnClick(R.id.btn_tambahpemesan) void onTambahPemesan() {
        Intent intent = new Intent(OrderFormActivity.this, CustomerFormActivity.class);
        startActivityForResult(intent, Constant.CREATE_CUSTOMER_REQUEST);
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
        }
    }

    private void generateCustomerList(List<Customer> list){
        customerList.clear();
        Customer hint = new Customer("Pilih Pemesan","-1","-1");
        customerList.add(hint);
        customerList.addAll(list);
    }
}
