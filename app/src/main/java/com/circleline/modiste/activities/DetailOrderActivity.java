package com.circleline.modiste.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.circleline.modiste.R;
import com.circleline.modiste.models.Customer;
import com.circleline.modiste.models.Measurement;
import com.circleline.modiste.models.OrderDB;
import com.circleline.modiste.util.Constant;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

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

    @BindView(R.id.txvw_panjang_rok)
    TextView txvw_panjang_rok;

    @BindView(R.id.txvw_tinggi_panggul)
    TextView txvw_tinggi_panggul;

    @BindView(R.id.txvw_lingkar_pesak)
    TextView txvw_lingkar_pesak;

    @BindView(R.id.txvw_tinggi_duduk)
    TextView txvw_tinggi_duduk;

    @BindView(R.id.txvw_lingkar_paha)
    TextView txvw_lingkar_paha;

    @BindView(R.id.txvw_lingkar_lutut)
    TextView txvw_lingkar_lutut;

    @BindView(R.id.txvw_lingkar_kaki_celana)
    TextView txvw_lingkar_kaki_celana;

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
        loadImageFromStorage(String.valueOf(orderID));
    }

    private void loadImageFromStorage(String filename)
    {

        try {
            ContextWrapper cw = new ContextWrapper(getApplicationContext());
            // path to /data/data/yourapp/app_data/imageDir
            File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
            // Create imageDir
            File mypath=new File(directory,filename);
            final Bitmap b = BitmapFactory.decodeStream(new FileInputStream(mypath));
            ImageView img=(ImageView)findViewById(R.id.imvw_foto);
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(DetailOrderActivity.this);
                    LayoutInflater inflater = DetailOrderActivity.this.getLayoutInflater();
                    View dialogView = inflater.inflate(R.layout.dialog_image_order,null);
                    ImageView imvw_foto = dialogView.findViewById(R.id.imvw_foto);
                    imvw_foto.setImageBitmap(b);
                    builder.setView(dialogView);

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });
            img.setImageBitmap(b);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

    }

    void updateView(long id){
        order = OrderDB.find(OrderDB.class,"id = ? ",String.valueOf(id)).get(0);
        customer = Customer.find(Customer.class,"id = ?",String.valueOf(order.getIdCustomer())).get(0);
        measurement = Measurement.find(Measurement.class,"id = ?",String.valueOf(order.getIdMeasurement())).get(0);

        txvw_customer.setText("Nama : "+customer.getNama());
        txvw_tanggal_masuk.setText("Tanggal Masuk : "+order.getTglMasuk());
        txvw_tanggal_selesai.setText("Tanggal Selesai : "+order.getTglSelesai());
        txvw_cutting.setText("Tanggal Cutting : "+order.getCutting());
        txvw_harga.setText("Harga : "+order.getHarga());
        txvw_lingkar_badan.setText("Lingkar Badan : "+measurement.getLingkarBadan());
        txvw_lingkar_pinggang.setText("Lingkar Pinggang : "+measurement.getLingkarPinggang());
        txvw_lingkar_panggul.setText("Lingkar Pinggul : "+measurement.getLingkarPanggul());
        txvw_lebar_muka.setText("Lebar Muka : "+measurement.getLebarMuka());
        txvw_panjang_muka.setText("Panjang Muka : "+measurement.getPanjangMuka());
        txvw_lebar_punggung.setText("Lebar Punggung : "+measurement.getLebarPunggung());
        txvw_panjang_punggung.setText("Panjang Punggung : "+measurement.getPanjangPunggung());
        txvw_lebar_bahu.setText("Lebar Bahu : "+measurement.getLebarBahu());
        txvw_panjang_sisi.setText("Panjang Sisi : "+measurement.getPanjangSisi());
        txvw_panjang_lengan.setText("Panjang Lengan : "+measurement.getPanjangLengan());
        txvw_lingkar_kerung_lengan.setText("Lingkar Kerung Lengan : "+measurement.getLingkarKerungLeher());
        txvw_pergelangan_lengan.setText("Pergelangan Lengan : "+measurement.getLingkarPergelanganLengan());
        txvw_panjang_baju.setText("Panjang Baju : "+measurement.getPanjangBaju());
        txvw_lingkar_kerung_leher.setText("Lingkar Kerung Leher : "+measurement.getLingkarKerungLeher());
        txvw_panjang_rok.setText("Panjang Rok : "+measurement.getPanjangRok());
        txvw_tinggi_panggul.setText("Tinggi Panggul : "+measurement.getTinggiPanggul());
        txvw_lingkar_pesak.setText("Lingkar Pesak : "+measurement.getLingkarPesak());
        txvw_tinggi_duduk.setText("Tinggi Duduk : "+measurement.getTinggiDuduk());
        txvw_lingkar_paha.setText("Lingkar Paha : "+measurement.getLingkarPaha());
        txvw_lingkar_lutut.setText("Lingkar Lutut : "+measurement.getLingkarLutut());
        txvw_lingkar_kaki_celana.setText("Lingkar Kaki Celana : "+measurement.getLingkarKakiCelana());

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
