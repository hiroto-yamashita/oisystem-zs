package com.oisix.oisystemfr;

import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class JNDIUtil {
    public static Context getContext() throws NamingException {
        return new InitialContext();
    }
}
