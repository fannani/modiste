package com.circleline.modiste.models;

import com.orm.SugarRecord;

/**
 * Created by rahad on 07/01/2018.
 */

public class Measurement extends SugarRecord {

    String nama;
    String lingkarBadan;
    String lingkarPinggang;
    String lingkarPanggul;
    String lebarMuka;
    String panjangMuka;
    String lebarPunggung;
    String panjangPunggung;
    String lebarBahu;
    String panjangSisi;
    String panjangLengan;
    String lingkarKerungLengan;
    String lingkarPergelanganLengan;
    String panjangBaju;
    String lingkarKerungLeher;
    String panjangRok;
    String tinggiPanggul;
    String lingkarPesak;
    String tinggiDuduk;
    String lingkarPaha;
    String lingkarLutut;
    String lingkarKakiCelana;
    String idCustomer;
    String catatan;


    public Measurement() {
    }

    public Measurement(String nama, String lingkarBadan, String lingkarPinggang, String lingkarPanggul, String lebarMuka, String panjangMuka, String lebarPunggung, String panjangPunggung, String lebarBahu, String panjangSisi, String panjangLengan, String lingkarKerungLengan, String lingkarPergelanganLengan, String panjangBaju, String lingkarKerungLeher, String idCustomer, String catatan) {
        this.nama = nama;
        this.lingkarBadan = lingkarBadan;
        this.lingkarPinggang = lingkarPinggang;
        this.lingkarPanggul = lingkarPanggul;
        this.lebarMuka = lebarMuka;
        this.panjangMuka = panjangMuka;
        this.lebarPunggung = lebarPunggung;
        this.panjangPunggung = panjangPunggung;
        this.lebarBahu = lebarBahu;
        this.panjangSisi = panjangSisi;
        this.panjangLengan = panjangLengan;
        this.lingkarKerungLengan = lingkarKerungLengan;
        this.lingkarPergelanganLengan = lingkarPergelanganLengan;
        this.panjangBaju = panjangBaju;
        this.lingkarKerungLeher = lingkarKerungLeher;
        this.idCustomer = idCustomer;
        this.catatan = catatan;
    }

    public String getPanjangRok() {
        return panjangRok;
    }

    public void setPanjangRok(String panjangRok) {
        this.panjangRok = panjangRok;
    }

    public String getTinggiPanggul() {
        return tinggiPanggul;
    }

    public void setTinggiPanggul(String tinggiPanggul) {
        this.tinggiPanggul = tinggiPanggul;
    }

    public String getLingkarPesak() {
        return lingkarPesak;
    }

    public void setLingkarPesak(String lingkarPesak) {
        this.lingkarPesak = lingkarPesak;
    }

    public String getTinggiDuduk() {
        return tinggiDuduk;
    }

    public void setTinggiDuduk(String tinggiDuduk) {
        this.tinggiDuduk = tinggiDuduk;
    }

    public String getLingkarPaha() {
        return lingkarPaha;
    }

    public void setLingkarPaha(String lingkarPaha) {
        this.lingkarPaha = lingkarPaha;
    }

    public String getLingkarLutut() {
        return lingkarLutut;
    }

    public void setLingkarLutut(String lingkarLutut) {
        this.lingkarLutut = lingkarLutut;
    }

    public String getLingkarKakiCelana() {
        return lingkarKakiCelana;
    }

    public void setLingkarKakiCelana(String lingkarKakiCelana) {
        this.lingkarKakiCelana = lingkarKakiCelana;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(String idCustomer) {
        this.idCustomer = idCustomer;
    }

    public String getLingkarBadan() {
        return lingkarBadan;
    }

    public void setLingkarBadan(String lingkarBadan) {
        this.lingkarBadan = lingkarBadan;
    }

    public String getLingkarPinggang() {
        return lingkarPinggang;
    }

    public void setLingkarPinggang(String lingkarPinggang) {
        this.lingkarPinggang = lingkarPinggang;
    }

    public String getLingkarPanggul() {
        return lingkarPanggul;
    }

    public void setLingkarPanggul(String lingkarPanggul) {
        this.lingkarPanggul = lingkarPanggul;
    }

    public String getLebarMuka() {
        return lebarMuka;
    }

    public void setLebarMuka(String lebarMuka) {
        this.lebarMuka = lebarMuka;
    }

    public String getPanjangMuka() {
        return panjangMuka;
    }

    public void setPanjangMuka(String panjangMuka) {
        this.panjangMuka = panjangMuka;
    }

    public String getLebarPunggung() {
        return lebarPunggung;
    }

    public void setLebarPunggung(String lebarPunggung) {
        this.lebarPunggung = lebarPunggung;
    }

    public String getPanjangPunggung() {
        return panjangPunggung;
    }

    public void setPanjangPunggung(String panjangPunggung) {
        this.panjangPunggung = panjangPunggung;
    }

    public String getLebarBahu() {
        return lebarBahu;
    }

    public void setLebarBahu(String lebarBahu) {
        this.lebarBahu = lebarBahu;
    }

    public String getPanjangSisi() {
        return panjangSisi;
    }

    public void setPanjangSisi(String panjangSisi) {
        this.panjangSisi = panjangSisi;
    }

    public String getPanjangLengan() {
        return panjangLengan;
    }

    public void setPanjangLengan(String panjangLengan) {
        this.panjangLengan = panjangLengan;
    }

    public String getLingkarKerungLengan() {
        return lingkarKerungLengan;
    }

    public void setLingkarKerungLengan(String lingkarKerungLengan) {
        this.lingkarKerungLengan = lingkarKerungLengan;
    }

    public String getLingkarPergelanganLengan() {
        return lingkarPergelanganLengan;
    }

    public void setLingkarPergelanganLengan(String lingkarPergelanganLengan) {
        this.lingkarPergelanganLengan = lingkarPergelanganLengan;
    }

    public String getPanjangBaju() {
        return panjangBaju;
    }

    public void setPanjangBaju(String panjangBaju) {
        this.panjangBaju = panjangBaju;
    }

    public String getLingkarKerungLeher() {
        return lingkarKerungLeher;
    }

    public void setLingkarKerungLeher(String lingkarKerungLeher) {
        this.lingkarKerungLeher = lingkarKerungLeher;
    }

    public String getCatatan() {
        return catatan;
    }

    public void setCatatan(String catatan) {
        this.catatan = catatan;
    }
}
