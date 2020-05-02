package dev.blank.oauthstis.model;

import com.google.gson.annotations.SerializedName;

public class Kelas {
    @SerializedName("kode_kelas")
    private String kode_kelas;

    @SerializedName("tahun_akademik")
    private String tahun_akademik;

    public String getKode_kelas() {
        return kode_kelas;
    }

    public void setKode_kelas(String kode_kelas) {
        this.kode_kelas = kode_kelas;
    }

    public String getTahun_akademik() {
        return tahun_akademik;
    }

    public void setTahun_akademik(String tahun_akademik) {
        this.tahun_akademik = tahun_akademik;
    }
}
