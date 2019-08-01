package com.circleline.modiste.models;

import com.orm.SugarRecord;
import com.orm.dsl.Unique;

/**
 * Created by rahad on 07/01/2018.
 */

public class OrderDB extends SugarRecord {


    String idCustomer;

    public String getKetBahan() {
        return ketBahan;
    }

    public void setKetBahan(String ketBahan) {
        this.ketBahan = ketBahan;
    }

    String idMeasurement;
    String tglMasuk;
    String tglSelesai;
    String cutting;
    String harga;
    String status;
    String ketBahan;

    public OrderDB() {
    }

    public OrderDB(String idCustomer, String idMeasurement, String tglMasuk, String tglSelesai, String cutting, String harga, String ketBahan) {
        this.idCustomer = idCustomer;
        this.idMeasurement = idMeasurement;
        this.tglMasuk = tglMasuk;
        this.tglSelesai = tglSelesai;
        this.cutting = cutting;
        this.harga = harga;
        this.status = "0";
        this.ketBahan = ketBahan;
    }

    public String getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(String idCustomer) {
        this.idCustomer = idCustomer;
    }

    public String getIdMeasurement() {
        return idMeasurement;
    }

    public void setIdMeasurement(String idMeasurement) {
        this.idMeasurement = idMeasurement;
    }

    public String getTglMasuk() {
        return tglMasuk;
    }

    public void setTglMasuk(String tglMasuk) {
        this.tglMasuk = tglMasuk;
    }

    public String getTglSelesai() {
        return tglSelesai;
    }

    public void setTglSelesai(String tglSelesai) {
        this.tglSelesai = tglSelesai;
    }

    public String getCutting() {
        return cutting;
    }

    public void setCutting(String cutting) {
        this.cutting = cutting;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
