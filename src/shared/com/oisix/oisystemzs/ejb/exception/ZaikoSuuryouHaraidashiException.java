package com.oisix.oisystemzs.ejb.exception;

import com.oisix.oisystemzs.ejb.ZaikoData;
import com.oisix.oisystemzs.ejb.ShukkoData;

public class ZaikoSuuryouHaraidashiException
  extends ZaikoHaraidashiException {

    public ZaikoSuuryouHaraidashiException(
      ZaikoData zaiko, ShukkoData shukko) {
        super("suuryou less than zero", zaiko, shukko);
    }

    public ZaikoSuuryouHaraidashiException(String message,
      ZaikoData zaiko, ShukkoData shukko) {
        super(message, zaiko, shukko);
    }

    public ZaikoSuuryouHaraidashiException(Throwable cause,
      ZaikoData zaiko, ShukkoData shukko) {
        super(cause, zaiko, shukko);
    }
}
