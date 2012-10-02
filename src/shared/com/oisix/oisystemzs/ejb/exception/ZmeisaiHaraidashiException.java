package com.oisix.oisystemzs.ejb.exception;

import com.oisix.oisystemzs.ejb.ZaikomeisaiData;
import com.oisix.oisystemzs.ejb.ShukkoData;

public class ZmeisaiHaraidashiException extends HaraidashiException {
    protected ZaikomeisaiData zaikomeisaiData;
    protected ShukkoData shukkoData;

    public ZaikomeisaiData getZaikomeisai() { return zaikomeisaiData; }
    public ShukkoData getShukko() { return shukkoData; }

    public ZmeisaiHaraidashiException(
      ZaikomeisaiData zaikomeisai, ShukkoData shukko) {
        super();
        zaikomeisaiData = zaikomeisai;
        shukkoData = shukko;
    }

    public ZmeisaiHaraidashiException(String message,
      ZaikomeisaiData zaikomeisai, ShukkoData shukko) {
        super(message);
        zaikomeisaiData = zaikomeisai;
        shukkoData = shukko;
    }

    public ZmeisaiHaraidashiException(Throwable cause,
      ZaikomeisaiData zaikomeisai, ShukkoData shukko) {
        super(cause);
        zaikomeisaiData = zaikomeisai;
        shukkoData = shukko;
    }
}
