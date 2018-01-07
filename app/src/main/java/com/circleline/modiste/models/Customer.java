package com.circleline.modiste.models;

import com.orm.SugarRecord;

/**
 * Created by rahad on 07/01/2018.
 */

public class Customer extends SugarRecord {

    String nama;
    String alamat;
    String noTelp;

    public Customer() {
    }

    public Customer(String nama, String alamat, String noTelp) {
        this.nama = nama;
        this.alamat = alamat;
        this.noTelp = noTelp;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNoTelp() {
        return noTelp;
    }

    public void setNoTelp(String noTelp) {
        this.noTelp = noTelp;
    }
}
