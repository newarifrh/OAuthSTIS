package dev.blank.oauthstis.model.dosen;

import com.google.gson.annotations.SerializedName;

public class Dosen {
    @SerializedName("profile")
    private DosenProfile dosenProfile;

    public DosenProfile getDosenProfile() {
        return dosenProfile;
    }

    public void setDosenProfile(DosenProfile dosenProfile) {
        this.dosenProfile = dosenProfile;
    }
}
