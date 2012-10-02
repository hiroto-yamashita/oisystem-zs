package com.oisix.oisystemzs.eventhandler;

import com.oisix.oisystemfr.Debug;
import com.oisix.oisystemfr.EventHandlerSupport;
import com.oisix.oisystemzs.pdf.PickinglistPdf;
import com.oisix.oisystemfr.ejb.ServiceLocator;
import com.oisix.oisystemzs.ejb.ShukkayoteiPK;
import com.oisix.oisystemzs.ejb.ShukkayoteiLocal;
import com.oisix.oisystemzs.ejb.ShukkayoteiLocalHome;
import com.oisix.oisystemzs.ejb.ShukkayoteiData;
import com.oisix.oisystemzs.ejb.ShukkayoteimeisaiLocal;
import com.oisix.oisystemzs.ejb.ShukkayoteimeisaiData;
import com.oisix.oisystemzs.ejb.NouhinsakiLocalHome;
import com.oisix.oisystemzs.ejb.NouhinsakiLocal;
import com.oisix.oisystemzs.ejb.NouhinsakiData;
import com.oisix.oisystemzs.ejb.ShouhinLocal;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import javax.servlet.http.HttpServletRequest;
import javax.naming.NamingException;
import javax.ejb.FinderException;

public class MakepickinglistEvent extends EventHandlerSupport {

    private boolean isError = false;

    private int shukkayotei_id;
    private String filename;

    public boolean getIsError() { return isError; }
    public String getFileName() { return filename; }

    public void init(HttpServletRequest request) {
        String idstr = request.getParameter("id");
        try {
            shukkayotei_id = Integer.parseInt(idstr);
        } catch (NumberFormatException nfe) {
            Debug.println(nfe);
            isError = true;
            return;
        }
    }

    public void handleEvent(HashMap attr) {
        if (isError) { return; }

        PickinglistPdf ppdf = new PickinglistPdf();
        try {
            ShukkayoteiPK pk = new ShukkayoteiPK(shukkayotei_id);
            ShukkayoteiLocalHome hclh = (ShukkayoteiLocalHome)
              ServiceLocator.getLocalHome(
              "java:comp/env/ejb/ShukkayoteiLocal");
            ShukkayoteiLocal syl = hclh.findByPrimaryKey(pk);
            ShukkayoteiData syd = syl.getShukkayoteiData();
            ppdf.setShukkayoteiData(syd);

            String nouhinsaki_id = syd.getNouhinsaki_id();
            NouhinsakiLocalHome nslh = (NouhinsakiLocalHome)
              ServiceLocator.getLocalHome("java:comp/env/ejb/NouhinsakiLocal");
            NouhinsakiData nsd = null;
            if (nouhinsaki_id != null) {
                try {
                    NouhinsakiLocal nsl = nslh.findByPrimaryKey(nouhinsaki_id);
                    nsd = nsl.getNouhinsakiData();
                } catch (FinderException fe) {
                    //‚È‚­‚Ä‚à–³Ž‹
                }
            }
            ppdf.setNouhinsakiData(nsd);

            Collection meisai = syl.getShukkayoteimeisai();
            Iterator meisaiiter = meisai.iterator();
            LinkedList pickinglist = new LinkedList();
            while (meisaiiter.hasNext()) {
                ShukkayoteimeisaiLocal sym =
                  (ShukkayoteimeisaiLocal)meisaiiter.next();
                ShouhinLocal sh = sym.getShouhin();

                HashMap pickingdata = new HashMap();
                pickingdata.put("SHUKKAYOTEIMEISAI",
                  sym.getShukkayoteimeisaiData());
                pickingdata.put("SHOUHIN", sh.getShouhinData());
                pickinglist.add(pickingdata);
            }
            ppdf.setPickinglist(pickinglist);
        } catch (NamingException ne) {
            Debug.println(ne);
            isError = true;
            return;
        } catch (FinderException fe) {
            Debug.println(fe);
            isError = true;
            return;
        }

        try {
            ppdf.makeDocument();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        filename = ppdf.getFileName();
    }
}
