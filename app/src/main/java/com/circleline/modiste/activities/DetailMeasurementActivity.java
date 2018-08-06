package com.circleline.modiste.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.circleline.modiste.R;
import com.circleline.modiste.models.Measurement;
import com.circleline.modiste.models.OrderDB;
import com.circleline.modiste.util.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailMeasurementActivity extends AppCompatActivity {


    @BindView(R.id.txvw_nama)
    TextView txvw_nama;

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



    private Measurement measurement;
    private long measurementID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_measurement);
        ButterKnife.bind(this);
        measurementID = getIntent().getLongExtra("measurementID",-1);
        setData();

    }

    void setData(){

        measurement = Measurement.find(Measurement.class,"id = ?",String.valueOf(measurementID)).get(0);
        txvw_nama.setText("Nama : "+measurement.getNama());
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
    }

    @OnClick(R.id.btn_edit)
    void onEdit(){
        Intent intent = new Intent(DetailMeasurementActivity.this,MeasurementFormActivity.class);
        intent.putExtra("measurementID",measurementID);
        startActivityForResult(intent, Constant.EDIT_MEASUREMENT_REQUEST);
    }

    @OnClick(R.id.btn_hapus)
    void onHapus(){
        measurement.delete();
        setResult(RESULT_OK,getIntent());
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constant.EDIT_MEASUREMENT_REQUEST ) {
            if (resultCode == RESULT_OK) {
                setData();
            }
        }
    }
}
