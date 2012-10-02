package com.oisix.oisystemzs.objectmap;

import com.oisix.oisystemfr.Debug;
import com.oisix.oisystemzs.ejb.OfficeLocalHome;
import com.oisix.oisystemzs.ejb.OfficeLocal;
import com.oisix.oisystemzs.ejb.OfficeData;
import java.util.HashMap;
import java.util.Collection;
import java.util.Iterator;
import javax.naming.NamingException;
import javax.naming.InitialContext;
import javax.ejb.FinderException;

public class OfficeMap {
    private static HashMap officeMap = null;

    static {
        newOfficeMap();
    }

    public static HashMap newOfficeMap() {
        officeMap = new HashMap();
        try {
            InitialContext ctx = new InitialContext();
            Object objref = ctx.lookup("java:comp/env/ejb/OfficeLocal");
            OfficeLocalHome oh = (OfficeLocalHome)objref;
            Collection offices = oh.findAll();
            if ((offices == null) || (offices.isEmpty())) {
                return officeMap;
            }
            Iterator officeiter = offices.iterator();
            while (officeiter.hasNext()) {
                OfficeLocal ol = (OfficeLocal)officeiter.next();
                OfficeData office = ol.getOfficeData();
                officeMap.put(office.getOffice_id(), office);
            }
        } catch (NamingException ex) {
            Debug.println(ex);
            return officeMap;
        } catch (FinderException ex) {
            Debug.println(ex);
            return officeMap;
        }
        return officeMap;
    }

    public static void setOfficeMap() {
        if (officeMap == null || officeMap.isEmpty()) {
            newOfficeMap();
        }
    }

    public static HashMap getOfficeMap() {
        setOfficeMap();
        return officeMap;
    }

    public static void clearOfficeMap() {
        officeMap = null;
    }

    public static OfficeData getOffice(String office_id) {
        setOfficeMap();
        return (OfficeData)officeMap.get(office_id);
    }
}
