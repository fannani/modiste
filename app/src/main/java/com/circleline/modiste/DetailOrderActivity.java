package com.circleline.modiste;

import android.content.Intent;
import android.icu.util.Measure;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.circleline.modiste.models.Customer;
import com.circleline.modiste.models.Measurement;
import com.circleline.modiste.models.OrderDB;
import com.circleline.modiste.util.Constant;

import org.w3c.dom.Text;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailOrderActivity extends AppCompatActivity {

    @BindView(R.id.txvw_customer)
    TextView txvw_customer;

    @BindView(R.id.txvw_tanggal_masuk)
    TextView txvw_tanggal_masuk;

    @BindView(R.id.txvw_tanggal_selesai)
    TextView txvw_tanggal_selesai;

    @BindView(R.id.txvw_cutting)
    TextView txvw_cutting;

    @BindView(R.id.txvw_harga)
    TextView txvw_harga;

    @BindView(R.id.txvw_lingkar_badan)
    TextView txvw_lingkar_badan;

    @BindView(R.id.txvw_lingkar_pinggang)
    TextView txvw_lingkar_pinggang;

    @BindView(R.id.txvw_lingkar_panggul)
    TextView txvw_lingkar_panggul;

    @BindView(R.id.txvw_lebar_muka)
    TextView txvw_lebar_muka;

    @BindView(R.id.txvw_panjang_muka)
    TextView txvw_panjang_muka;

    @BindView(R.id.txvw_lebar_punggung)
    TextView txvw_lebar_punggung;

    @BindView(R.id.txvw_panjang_punggung)
    TextView txvw_panjang_punggung;

    @BindView(R.id.txvw_lebar_bahu)
    TextView txvw_lebar_bahu;

    @BindView(R.id.txvw_panjang_sisi)
    TextView txvw_panjang_sisi;

    @BindView(R.id.txvw_panjang_lengan)
    TextView txvw_panjang_lengan;

    @BindView(R.id.txvw_lingkar_kerung_lengan)
    TextView txvw_lingkar_kerung_lengan;

    @BindView(R.id.txvw_pergelangan_lengan)
    TextView txvw_pergelangan_lengan;

    @BindView(R.id.txvw_panjang_baju)
    TextView txvw_panjang_baju;

    @BindView(R.id.txvw_lingkar_kerung_leher)
    TextView txvw_lingkar_kerung_leher;

    @BindView(R.id.btn_selesai)
    Button btn_selesai;

    private OrderDB order;
    private Customer customer;
    private Measurement measurement;
    private Long orderID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_order);
        ButterKnife.bind(this);

        orderID = getIntent().getLongExtra("orderID",-1);
        updateView(orderID);
    }

    void updateView(long id){
        order = OrderDB.find(OrderDB.class,"id = ? ",String.valueOf(id)).get(0);
        customer = Customer.find(Customer.class,"id = ?",String.valueOf(order.getIdCustomer())).get(0);
        measurement = Measurement.find(Measurement.class,"id = ?",String.valueOf(order.getIdMeasurement())).get(0);

        txvw_customer.setText(customer.getNama());
        txvw_tanggal_masuk.setText(order.getTglMasuk());
        txvw_tanggal_selesai.setText(order.getTglSelesai());
        txvw_cutting.setText(order.getCutting());
        txvw_harga.setText(order.getHarga());
        txvw_lingkar_badan.setText(measurement.getLingkarBadan());
        txvw_lingkar_pinggang.setText(measurement.getLingkarPinggang());
        txvw_lingkar_panggul.setText(measurement.getLingkarPanggul());
        txvw_lebar_muka.setText(measurement.getLebarMuka());
        txvw_panjang_muka.setText(measurement.getPanjangMuka());
        txvw_lebar_punggung.setText(measurement.getLebarPunggung());
        txvw_panjang_punggung.setText(measurement.getPanjangPunggung());
        txvw_lebar_bahu.setText(measurement.getLebarBahu());
        txvw_panjang_sisi.setText(measurement.getPanjangSisi());
        txvw_panjang_lengan.setText(measurement.getPanjangLengan());
        txvw_lingkar_kerung_lengan.setText(measurement.getLingkarKerungLeher());
        txvw_pergelangan_lengan.setText(measurement.getLingkarPergelanganLengan());
        txvw_panjang_baju.setText(measurement.getPanjangBaju());
        txvw_lingkar_kerung_leher.setText(measurement.getLingkarKerungLeher());

        if(order.getStatus().equals("1")){
            btn_selesai.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.btn_selesai)
    void onSelesai(){
        order.setStatus("1");
        order.save();
        setResult(RESULT_OK,getIntent());
        finish();
    }

    @OnClick(R.id.btn_batalkan)
    void onBatalkan(){
        order.delete();
        setResult(RESULT_OK,getIntent());
        finish();
    }

    @OnClick(R.id.btn_edit)
    void onEdit(){
        Intent intent = new Intent(DetailOrderActivity.this,OrderFormActivity.class);
        intent.putExtra("orderID",order.getId());
        startActivityForResult(intent, Constant.EDIT_ORDER_REQUEST);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == Constant.EDIT_ORDER_REQUEST){
            if(resultCode == RESULT_OK){
                updateView(orderID);
            }
        }
    }
}
