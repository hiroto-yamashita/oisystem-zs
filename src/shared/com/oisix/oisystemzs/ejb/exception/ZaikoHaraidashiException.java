package com.oisix.oisystemzs.ejb.exception;

import com.oisix.oisystemzs.ejb.ZaikoData;
import com.oisix.oisystemzs.ejb.ShukkoData;

public class ZaikoHaraidashiException extends HaraidashiException {
    protected ZaikoData zaikoData;
    protected ShukkoData shukkoData;

    public ZaikoData getZaiko() { return zaikoData; }
    public ShukkoData getShukko() { return shukkoData; }

    public ZaikoHaraidashiException(
      ZaikoData zaiko, ShukkoData shukko) {
        super();
        zaikoData = zaiko;
        shukkoData = shukko;
    }

    public ZaikoHaraidashiException(String message,
      ZaikoData zaiko, ShukkoData shukko) {
        super(message);
        zaikoData = zaiko;
        shukkoData = shukko;
    }

    public ZaikoHaraidashiException(Throwable cause,
      ZaikoData zaiko, ShukkoData shukko) {
        super(cause);
        zaikoData = zaiko;
        shukkoData = shukko;
    }
}
