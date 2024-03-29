package com.circleline.modiste.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;

import com.circleline.modiste.R;
import com.circleline.modiste.models.Measurement;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MeasurementFormActivity extends AppCompatActivity {
    @BindView(R.id.edtx_nama)
    EditText edtx_nama;

    @BindView(R.id.edtx_lingkar_badan)
    EditText edtx_lingkar_badan;

    @BindView(R.id.edtx_lingkar_pinggang)
    EditText edtx_lingkar_pinggang;

    @BindView(R.id.edtx_lingkar_panggul)
    EditText edtx_lingkar_panggul;

    @BindView(R.id.edtx_lebar_muka)
    EditText edtx_lebar_muka;

    @BindView(R.id.edtx_panjang_muka)
    EditText edtx_panjang_muka;

    @BindView(R.id.edtx_lebar_punggung)
    EditText edtx_lebar_punggung;

    @BindView(R.id.edtx_panjang_punggung)
    EditText edtx_panjang_punggung;

    @BindView(R.id.edtx_lebar_bahu)
    EditText edtx_lebar_bahu;

    @BindView(R.id.edtx_panjang_sisi)
    EditText edtx_panjang_sisi;

    @BindView(R.id.edtx_panjang_lengan)
    EditText edtx_panjang_lengan;

    @BindView(R.id.edtx_lingkar_kerung_lengan)
    EditText edtx_lingkar_kerung_lengan;

    @BindView(R.id.edtx_lingkar_pergelangan_lengan)
    EditText edtx_lingkar_pergelangan_lengan;

    @BindView(R.id.edtx_panjang_baju)
    EditText edtx_panjang_baju;

    @BindView(R.id.edtx_lingkar_kerung_leher)
    EditText edtx_lingkar_kerung_leher;

    @BindView(R.id.edtx_panjang_rok)
    EditText edtx_panjang_rok;

    @BindView(R.id.edtx_tinggi_panggul)
    EditText edtx_tinggi_panggul;

    @BindView(R.id.edtx_lingkar_pesak)
    EditText edtx_lingkar_pesak;

    @BindView(R.id.edtx_tinggi_duduk)
    EditText edtx_tinggi_duduk;

    @BindView(R.id.edtx_lingkar_paha)
    EditText edtx_lingkar_paha;

    @BindView(R.id.edtx_lingkar_lutut)
    EditText edtx_lingkar_lutut;

    @BindView(R.id.edtx_lingkar_kaki_celana)
    EditText edtx_lingkar_kaki_celana;

    @BindView(R.id.edtx_catatan)
    EditText edtx_catatan;

    private Long idCustomer;
    private Long idMeasurement;
    private Measurement measurement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measurement_form);
        ButterKnife.bind(this);
        if(getIntent().hasExtra("measurementID")){
            idMeasurement = getIntent().getLongExtra("measurementID",-1);
            measurement = Measurement.find(Measurement.class,"id = ?",String.valueOf(idMeasurement)).get(0);
            edtx_nama.setText(measurement.getNama());
            edtx_lingkar_badan.setText(measurement.getLingkarBadan());
            edtx_lingkar_pinggang.setText(measurement.getLingkarPinggang());
            edtx_lingkar_panggul.setText(measurement.getLingkarPanggul());
            edtx_lebar_muka.setText(measurement.getLebarMuka());
            edtx_panjang_muka.setText(measurement.getPanjangMuka());
            edtx_lebar_punggung.setText(measurement.getLebarPunggung());
            edtx_panjang_punggung.setText(measurement.getPanjangPunggung());
            edtx_lebar_bahu.setText(measurement.getLebarBahu());
            edtx_panjang_sisi.setText(measurement.getPanjangSisi());
            edtx_panjang_lengan.setText(measurement.getPanjangLengan());
            edtx_lingkar_kerung_lengan.setText(measurement.getLingkarKerungLeher());
            edtx_lingkar_pergelangan_lengan.setText(measurement.getLingkarPergelanganLengan());
            edtx_panjang_baju.setText(measurement.getPanjangBaju());
            edtx_lingkar_kerung_leher.setText(measurement.getLingkarKerungLeher());
            edtx_panjang_rok.setText(measurement.getPanjangRok());
            edtx_tinggi_panggul.setText(measurement.getTinggiPanggul());
            edtx_lingkar_pesak.setText(measurement.getLingkarPesak());
            edtx_tinggi_duduk.setText(measurement.getTinggiDuduk());
            edtx_lingkar_paha.setText(measurement.getLingkarPaha());
            edtx_lingkar_lutut.setText(measurement.getLingkarLutut());
            edtx_lingkar_kaki_celana.setText(measurement.getLingkarKakiCelana());
            edtx_catatan.setText(measurement.getCatatan());
        } else {
            idCustomer = getIntent().getLongExtra("idCustomer",-1);
        }
    }

    @OnClick(R.id.btn_simpan)
    void onSimpanMeasurement(){
        if(getIntent().hasExtra("measurementID")){
            measurement.setNama(edtx_nama.getText().toString());
            measurement.setLingkarBadan(edtx_lingkar_badan.getText().toString());
            measurement.setLingkarPinggang(edtx_lingkar_pinggang.getText().toString());
            measurement.setLingkarPanggul(edtx_lingkar_panggul.getText().toString());
            measurement.setLebarMuka(edtx_lebar_muka.getText().toString());
            measurement.setPanjangMuka(edtx_panjang_muka.getText().toString());
            measurement.setLebarPunggung(edtx_lebar_punggung.getText().toString());
            measurement.setPanjangPunggung(edtx_panjang_punggung.getText().toString());
            measurement.setLebarBahu(edtx_lebar_bahu.getText().toString());
            measurement.setPanjangSisi(edtx_panjang_sisi.getText().toString());
            measurement.setPanjangLengan(edtx_panjang_lengan.getText().toString());
            measurement.setLingkarKerungLengan(edtx_lingkar_kerung_lengan.getText().toString());
            measurement.setLingkarPergelanganLengan(edtx_lingkar_pergelangan_lengan.getText().toString());
            measurement.setPanjangBaju(edtx_panjang_baju.getText().toString());
            measurement.setLingkarKerungLeher(edtx_lingkar_kerung_leher.getText().toString());
            measurement.setPanjangRok(edtx_panjang_rok.getText().toString());
            measurement.setTinggiPanggul(edtx_tinggi_panggul.getText().toString());
            measurement.setLingkarPesak(edtx_lingkar_pesak.getText().toString());
            measurement.setTinggiDuduk(edtx_tinggi_duduk.getText().toString());
            measurement.setLingkarPaha(edtx_lingkar_paha.getText().toString());
            measurement.setLingkarLutut(edtx_lingkar_lutut.getText().toString());
            measurement.setLingkarKakiCelana(edtx_lingkar_kaki_celana.getText().toString());
            measurement.setCatatan(edtx_catatan.getText().toString());
            measurement.save();
        } else {
            Measurement data = new Measurement();
            data.setNama(edtx_nama.getText().toString());
            data.setIdCustomer(idCustomer.toString());
            data.setLingkarBadan(edtx_lingkar_badan.getText().toString());
            data.setLingkarPinggang(edtx_lingkar_pinggang.getText().toString());
            data.setLingkarPanggul(edtx_lingkar_panggul.getText().toString());
            data.setLebarMuka(edtx_lebar_muka.getText().toString());
            data.setPanjangMuka(edtx_panjang_muka.getText().toString());
            data.setLebarPunggung(edtx_lebar_punggung.getText().toString());
            data.setPanjangPunggung(edtx_panjang_punggung.getText().toString());
            data.setLebarBahu(edtx_lebar_bahu.getText().toString());
            data.setPanjangSisi(edtx_panjang_sisi.getText().toString());
            data.setPanjangLengan(edtx_panjang_lengan.getText().toString());
            data.setLingkarKerungLengan(edtx_lingkar_kerung_lengan.getText().toString());
            data.setLingkarPergelanganLengan(edtx_lingkar_pergelangan_lengan.getText().toString());
            data.setPanjangBaju(edtx_panjang_baju.getText().toString());
            data.setLingkarKerungLeher(edtx_lingkar_kerung_leher.getText().toString());
            data.setPanjangRok(edtx_panjang_rok.getText().toString());
            data.setTinggiPanggul(edtx_tinggi_panggul.getText().toString());
            data.setLingkarPesak(edtx_lingkar_pesak.getText().toString());
            data.setTinggiDuduk(edtx_tinggi_duduk.getText().toString());
            data.setLingkarPaha(edtx_lingkar_paha.getText().toString());
            data.setLingkarLutut(edtx_lingkar_lutut.getText().toString());
            data.setLingkarKakiCelana(edtx_lingkar_kaki_celana.getText().toString());
            data.setCatatan(edtx_catatan.getText().toString());
            data.save();
        }
        setResult(RESULT_OK);
        finish();
    }
}
