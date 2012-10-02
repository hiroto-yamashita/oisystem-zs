package com.oisix.oisystemzs.eventhandler;

import com.oisix.oisystemfr.Debug;
import com.oisix.oisystemfr.TransactionEvent;
import com.oisix.oisystemfr.ejb.ServiceLocator;
import com.oisix.oisystemzs.ejb.ShukkayoteiPK;
import com.oisix.oisystemzs.ejb.ShukkayoteiLocal;
import com.oisix.oisystemzs.ejb.ShukkayoteiLocalHome;
import com.oisix.oisystemzs.ejb.ShukkayoteiData;
import com.oisix.oisystemzs.ejb.ShukkayoteimeisaiLocal;
import com.oisix.oisystemzs.ejb.ShukkayoteimeisaiData;
import com.oisix.oisystemzs.ejb.ShouhinLocal;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import javax.servlet.http.HttpServletRequest;
import javax.naming.NamingException;
import javax.ejb.FinderException;

public class ShukkayoteishousaiEvent extends TransactionEvent {

    private String idstr;
    private ShukkayoteiData shukkayotei;
    private Collection shukkayoteimeisai;
    private java.util.Date shukkayotei_date;
    private java.util.Date nouhinyotei_date;

    public ShukkayoteiData getShukkayotei() { return shukkayotei; }
    public Collection getShukkayoteimeisai() {
        return shukkayoteimeisai;
    }

    public void init(HttpServletRequest request) {
        idstr = request.getParameter("id");
        if (idstr == null) {
            idstr = (String)session.getAttribute("KOUSHINSHUKKAYOTEI_ID");
            session.setAttribute("KOUSHINSHUKKAYOTEI_ID", null);
        }
    }

    public void handleEvent(HashMap attr) {
        int id;
        try {
            id = Integer.parseInt(idstr);
        } catch (NumberFormatException nfe) {
            errorlist.add("システムエラー。idが数値に変換できません");
            result = RC_INPUTERROR;
            return;
        }
        try {
            ShukkayoteiPK pk = new ShukkayoteiPK(id);
            ShukkayoteiLocalHome hclh = (ShukkayoteiLocalHome)
              ServiceLocator.getLocalHome(
              "java:comp/env/ejb/ShukkayoteiLocal");
            ShukkayoteiLocal syl = hclh.findByPrimaryKey(pk);
            shukkayotei = syl.getShukkayoteiData();
            shukkayotei_date = shukkayotei.getShukkayotei_date();
            nouhinyotei_date = shukkayotei.getNouhinyotei_date();

            Collection meisai = syl.getShukkayoteimeisai();
            Iterator meisaiiter = meisai.iterator();
            shukkayoteimeisai = new LinkedList();
            while (meisaiiter.hasNext()) {
                ShukkayoteimeisaiLocal sym =
                  (ShukkayoteimeisaiLocal)meisaiiter.next();
                ShukkayoteimeisaiData syd = sym.getShukkayoteimeisaiData();
                ShouhinLocal sh = sym.getShouhin();

                HashMap meisailine = new HashMap();
                meisailine.put("SHUKKAYOTEIMEISAI", syd);
                meisailine.put("SHOUHIN", sh);
                shukkayoteimeisai.add(meisailine);
            }
        } catch (NamingException ne) {
            ne.printStackTrace();
            errorlist.add("システムエラーです。");
            result = RC_INPUTERROR;
            return;
        } catch (FinderException fe) {
            fe.printStackTrace();
            errorlist.add("システムエラーです。");
            result = RC_INPUTERROR;
            return;
        }
    }

    public void postHandle(HttpServletRequest request) {
        request.setAttribute("shukkayotei", shukkayotei_date);
        request.setAttribute("nouhinyotei", nouhinyotei_date);
    }
}
