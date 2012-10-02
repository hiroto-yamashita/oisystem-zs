package com.oisix.oisystemzs.eventhandler;

import com.oisix.oisystemfr.Debug;
import com.oisix.oisystemfr.TransactionEvent;
import com.oisix.oisystemfr.ejb.ServiceLocator;
import com.oisix.oisystemzs.ejb.ZaikomeisaiPK;
import com.oisix.oisystemzs.ejb.ZaikomeisaiLocal;
import com.oisix.oisystemzs.ejb.ZaikomeisaiLocalHome;
import com.oisix.oisystemzs.ejb.ZaikoLocal;
import com.oisix.oisystemzs.ejb.ShouhinLocal;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.naming.NamingException;
import javax.ejb.FinderException;

public class ShukkakigenhenkouEvent extends TransactionEvent {

    private int zaikomeisai_id;
    public int getZaikomeisai_id() { return zaikomeisai_id; }

    private java.util.Date shukkakigen;

    private HashMap zaikomeisaidata = new HashMap();
    public HashMap getData() { return zaikomeisaidata; }

    public void init(HttpServletRequest request) {
        // 在庫明細コードの取得
        String zaikomeisai_idstr = request.getParameter("id");
        if (zaikomeisai_idstr == null) {
            zaikomeisai_idstr =
              (String)session.getAttribute("ZAIKOMEISAI_ID");
            session.removeAttribute("ZAIKOMEISAI_ID");
        }
        try {
            zaikomeisai_id = Integer.parseInt(zaikomeisai_idstr);
        } catch (NumberFormatException nfe) {
            errorlist.add("システムエラー。idを数値に変換できません");
            result = RC_INPUTERROR;
            return;
        }
    }

    public void handleEvent(HashMap attr) {
        try {
            ZaikomeisaiPK pk = new ZaikomeisaiPK(zaikomeisai_id);
            ZaikomeisaiLocalHome zmlh = (ZaikomeisaiLocalHome)
              ServiceLocator.getLocalHome(
              "java:comp/env/ejb/ZaikomeisaiLocal");
            ZaikomeisaiLocal zml = zmlh.findByPrimaryKey(pk);
            ZaikoLocal zl = zml.getZaiko();
            ShouhinLocal sl = zml.getShouhin();
            zaikomeisaidata.put("SHOUHIN_ID", zml.getShouhin_id());
            zaikomeisaidata.put("SHOUHINMEI", sl.getShouhin());
            zaikomeisaidata.put("KIKAKU", sl.getKikaku());
            shukkakigen = zml.getShukkakigen();
            zaikomeisaidata.put("SHOUMIKIGEN", zml.getShoumikigen());
            zaikomeisaidata.put("ZAIKODATE", zl.getZaikodate());
            zaikomeisaidata.put("SUURYOU", new Float(zml.getSuuryou()));
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
        request.setAttribute("shukkakigen", shukkakigen);
    }
}
