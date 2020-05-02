package dev.blank.oauthstis.model.mahasiswa;

import com.google.gson.annotations.SerializedName;

public class Mahasiswa {
    @SerializedName("profile")
    private MahasiswaProfile mahasiswaProfile;

    public MahasiswaProfile getMahasiswaProfile() {
        return mahasiswaProfile;
    }

    public void setMahasiswaProfile(MahasiswaProfile mahasiswaProfile) {
        this.mahasiswaProfile = mahasiswaProfile;
    }
}
