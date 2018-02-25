package com.circleline.modiste.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.circleline.modiste.R;
import com.circleline.modiste.activities.DetailOrderActivity;
import com.circleline.modiste.activities.OrderFormActivity;
import com.circleline.modiste.adapters.OrderAdapter;
import com.circleline.modiste.models.OrderDB;
import com.circleline.modiste.util.Constant;
import com.orm.query.Select;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;


public class OrderListFragment extends Fragment {



    @BindView(R.id.lsvw_order)
    ListView lsvw_order;

    private List<OrderDB> orderList;
    private OrderAdapter orderAdapter;


    public OrderListFragment() {
        // Required empty public constructor
    }


    public static OrderListFragment newInstance() {
        OrderListFragment fragment = new OrderListFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_list, container, false);
        ButterKnife.bind(this,view);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),OrderFormActivity.class);
                startActivityForResult(intent, Constant.CREATE_ORDER_REQUEST);
            }
        });
        orderList = Select.from(OrderDB.class).orderBy("status").list();
        orderAdapter = new OrderAdapter(getActivity(),android.R.layout.simple_list_item_1,orderList);
        lsvw_order.setAdapter(orderAdapter);
        lsvw_order.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(),DetailOrderActivity.class);
                intent.putExtra("orderID",orderAdapter.getItem(i).getId());
                startActivityForResult(intent,Constant.DETAIL_ORDER_REQUEST);
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constant.CREATE_ORDER_REQUEST ) {
            if (resultCode == RESULT_OK) {
                orderList =  Select.from(OrderDB.class).orderBy("status").list();
                orderAdapter.setList(orderList);
            }
        } else if(requestCode == Constant.DETAIL_ORDER_REQUEST){
            if(resultCode == RESULT_OK){
                orderList =  Select.from(OrderDB.class).orderBy("status").list();
                orderAdapter.setList(orderList);
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


}
