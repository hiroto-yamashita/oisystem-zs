package com.oisix.oisystemfr;

import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;

public interface EventHandler {

    public void preInit(HttpServletRequest request);

    public void init(HttpServletRequest request);

    public void postInit();

    public void handleEvent(HashMap attr);

    public void postHandle(HttpServletRequest request);
}
