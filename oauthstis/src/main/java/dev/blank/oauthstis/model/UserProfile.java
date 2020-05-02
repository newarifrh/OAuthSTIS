package dev.blank.oauthstis.model;

import com.google.gson.annotations.SerializedName;

public class UserProfile {
    @SerializedName("nama")
    private String nama;

    @SerializedName("jenis_kelamin")
    private String jenis_kelamin;

    @SerializedName("role")
    private String role;


    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getJenis_kelamin() {
        return jenis_kelamin;
    }

    public void setJenis_kelamin(String jenis_kelamin) {
        this.jenis_kelamin = jenis_kelamin;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
