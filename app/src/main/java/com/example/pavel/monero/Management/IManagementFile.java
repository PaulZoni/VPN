package com.example.pavel.monero.Management;

import io.reactivex.Observable;

public interface IManagementFile {
    Observable<Boolean> saveSelect(Integer type, String FILENAME);
    Observable<Boolean> saveSelect(String type, String FILENAME);
    Observable<String> readSelect(String FILENAME, int type);
}
