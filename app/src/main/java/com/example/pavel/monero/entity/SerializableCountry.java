package com.example.pavel.monero.entity;

import java.io.Serializable;

public class SerializableCountry implements Serializable {

    private String country;
    private int servers;

    public SerializableCountry(String country, int servers) {
        this.country = country;
        this.servers = servers;
    }

    public String getCountry() {
        return country;
    }

    public int getServers() {
        return servers;
    }
}
