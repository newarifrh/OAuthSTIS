package dev.blank.oauthstis.model.dosen;

import com.google.gson.annotations.SerializedName;

import dev.blank.oauthstis.model.UserProfile;

public class DosenProfile extends UserProfile {
    @SerializedName("username")
    private String username;

    @SerializedName("gelar_depan ")
    private String gelar_depan;

    @SerializedName("gelar_belakang")
    private String gelar_belakang;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGelar_depan() {
        return gelar_depan;
    }

    public void setGelar_depan(String gelar_depan) {
        this.gelar_depan = gelar_depan;
    }

    public String getGelar_belakang() {
        return gelar_belakang;
    }

    public void setGelar_belakang(String gelar_belakang) {
        this.gelar_belakang = gelar_belakang;
    }
}
