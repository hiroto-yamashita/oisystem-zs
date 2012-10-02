package com.oisix.oisystemzs.ejb.exception;

import com.oisix.oisystemzs.ejb.ZaikomeisaiData;
import com.oisix.oisystemzs.ejb.ShukkoData;

public class ZmeisaiSuuryouHaraidashiException
  extends ZmeisaiHaraidashiException {

    public ZmeisaiSuuryouHaraidashiException(
      ZaikomeisaiData zaikomeisai, ShukkoData shukko) {
        super("suuryou less than zero", zaikomeisai, shukko);
    }

    public ZmeisaiSuuryouHaraidashiException(String message,
      ZaikomeisaiData zaikomeisai, ShukkoData shukko) {
        super(message, zaikomeisai, shukko);
    }

    public ZmeisaiSuuryouHaraidashiException(Throwable cause,
      ZaikomeisaiData zaikomeisai, ShukkoData shukko) {
        super(cause, zaikomeisai, shukko);
    }
}
