package com.circleline.modiste.models;

import com.orm.SugarRecord;
import com.orm.dsl.Unique;

/**
 * Created by rahad on 07/01/2018.
 */

public class Order extends SugarRecord {


    String namaPemesan;
    String alamat;
    String noHp;
    String tglMasuk;
    String tglSelesai;
    String cutting;
    String harga;

    public Order() {
    }

    public Order(String namaPemesan, String alamat, String noHp, String tglMasuk, String tglSelesai, String cutting, String harga) {
        this.namaPemesan = namaPemesan;
        this.alamat = alamat;
        this.noHp = noHp;
        this.tglMasuk = tglMasuk;
        this.tglSelesai = tglSelesai;
        this.cutting = cutting;
        this.harga = harga;
    }
}
