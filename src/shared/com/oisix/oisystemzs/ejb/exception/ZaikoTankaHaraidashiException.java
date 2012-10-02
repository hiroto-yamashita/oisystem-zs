package com.oisix.oisystemzs.ejb.exception;

import com.oisix.oisystemzs.ejb.ZaikoData;
import com.oisix.oisystemzs.ejb.ShukkoData;

public class ZaikoTankaHaraidashiException
  extends ZaikoHaraidashiException {

    public ZaikoTankaHaraidashiException(
      ZaikoData zaiko, ShukkoData shukko) {
        super("tanka less than zero", zaiko, shukko);
    }

    public ZaikoTankaHaraidashiException(String message,
      ZaikoData zaiko, ShukkoData shukko) {
        super(message, zaiko, shukko);
    }

    public ZaikoTankaHaraidashiException(Throwable cause,
      ZaikoData zaiko, ShukkoData shukko) {
        super(cause, zaiko, shukko);
    }
}
