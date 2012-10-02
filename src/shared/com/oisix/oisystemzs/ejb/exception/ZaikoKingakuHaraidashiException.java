package com.oisix.oisystemzs.ejb.exception;

import com.oisix.oisystemzs.ejb.ZaikoData;
import com.oisix.oisystemzs.ejb.ShukkoData;

public class ZaikoKingakuHaraidashiException
  extends ZaikoHaraidashiException {

    public ZaikoKingakuHaraidashiException(
      ZaikoData zaiko, ShukkoData shukko) {
        super("kingaku less than zero", zaiko, shukko);
    }

    public ZaikoKingakuHaraidashiException(String message,
      ZaikoData zaiko, ShukkoData shukko) {
        super(message, zaiko, shukko);
    }

    public ZaikoKingakuHaraidashiException(Throwable cause,
      ZaikoData zaiko, ShukkoData shukko) {
        super(cause, zaiko, shukko);
    }
}
