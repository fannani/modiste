package com.circleline.modiste.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.circleline.modiste.R;
import com.circleline.modiste.activities.DetailCustomerActivity;
import com.circleline.modiste.activities.DetailOrderActivity;
import com.circleline.modiste.activities.OrderFormActivity;
import com.circleline.modiste.adapters.CustomerAdapter;
import com.circleline.modiste.models.Customer;
import com.circleline.modiste.models.OrderDB;
import com.circleline.modiste.util.Constant;
import com.orm.query.Select;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;


public class CustomerListFragment extends Fragment {

    @BindView(R.id.lsvw_customer)
    ListView lsvw_customer;

    private List<Customer> customerList;
    private CustomerAdapter customerAdapter;


    public CustomerListFragment() {
        // Required empty public constructor
    }


    public static CustomerListFragment newInstance() {
        CustomerListFragment fragment = new CustomerListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer_list, container, false);
        ButterKnife.bind(this,view);
        customerList = Customer.listAll(Customer.class);
        customerAdapter = new CustomerAdapter(getActivity(),android.R.layout.simple_list_item_1,customerList);
        lsvw_customer.setAdapter(customerAdapter);
        lsvw_customer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(),DetailCustomerActivity.class);
                intent.putExtra("customerID",customerAdapter.getItem(i).getId());
                startActivityForResult(intent,Constant.DETAIL_CUSTOMER_REQUEST);
            }
        });

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
         if(requestCode == Constant.DETAIL_CUSTOMER_REQUEST){
            if(resultCode == RESULT_OK){
                customerList =  Customer.listAll(Customer.class);
                customerAdapter.setList(customerList);
            }
        }
    }
}
