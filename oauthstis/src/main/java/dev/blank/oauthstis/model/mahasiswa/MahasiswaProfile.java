package dev.blank.oauthstis.model.mahasiswa;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import dev.blank.oauthstis.model.Kelas;
import dev.blank.oauthstis.model.UserProfile;

public class MahasiswaProfile extends UserProfile {
    @SerializedName("nim")
    private String nim;

    @SerializedName("kelas")
    private List<Kelas> kelasList;

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public List<Kelas> getKelasList() {
        return kelasList;
    }

    public void setKelasList(List<Kelas> kelasList) {
        this.kelasList = kelasList;
    }
}
