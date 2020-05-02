package dev.blank.oauthstis.utils;

import dev.blank.oauthstis.model.dosen.Dosen;
import dev.blank.oauthstis.model.mahasiswa.Mahasiswa;

public interface LoginListener {
    void onError(String error);

    void onFinish(Mahasiswa mahasiswa);

    void onFinish(Dosen dosen);
}
