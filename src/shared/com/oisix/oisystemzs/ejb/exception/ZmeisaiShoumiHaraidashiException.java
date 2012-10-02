package com.oisix.oisystemzs.ejb.exception;

import com.oisix.oisystemzs.ejb.ZaikomeisaiData;
import com.oisix.oisystemzs.ejb.ShukkoData;

public class ZmeisaiShoumiHaraidashiException
  extends ZmeisaiHaraidashiException {

    public ZmeisaiShoumiHaraidashiException(ShukkoData shukko) {
        super("shoumikigen is not found", null, shukko);
    }

    public ZmeisaiShoumiHaraidashiException(
      String message, ShukkoData shukko) {
        super(message, null, shukko);
    }

    public ZmeisaiShoumiHaraidashiException(
      Throwable cause, ShukkoData shukko) {
        super(cause, null, shukko);
    }
}
