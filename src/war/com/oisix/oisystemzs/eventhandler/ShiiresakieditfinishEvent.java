package com.oisix.oisystemzs.eventhandler;

import com.oisix.oisystemfr.Debug;
import com.oisix.oisystemfr.TransactionEvent;
import com.oisix.oisystemfr.ejb.ServiceLocator;
import com.oisix.oisystemzs.Names;
import com.oisix.oisystemzs.ejb.ShiiresakiLocal;
import com.oisix.oisystemzs.ejb.ShiiresakiLocalHome;
import com.oisix.oisystemzs.ejb.UserLocal;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.naming.NamingException;
import javax.ejb.FinderException;
import javax.ejb.EJBException;

public class ShiiresakieditfinishEvent extends TransactionEvent {

    private java.lang.String shiiresaki_id;
    private java.lang.String name;
    private java.lang.String furigana;
    private java.lang.String furigana1;
    private java.lang.String furigana2;
    private java.lang.String yuubin;
    private java.lang.String addr;
    private java.lang.String tel;
    private java.lang.String fax;
    private java.lang.String tantoushaname1;
    private java.lang.String tantoushaname2;
    private int user_id;
    private long updated;

    public void init(HttpServletRequest request) {
        shiiresaki_id = request.getParameter("shiiresaki_id");
        if ((shiiresaki_id == null) || (shiiresaki_id.length() ==0)){
            errorlist.add("仕入先コードが入力されていません");
            result = RC_INPUTERROR;
        }
        name = request.getParameter("name");
        if ((name == null) || (name.length() ==0)){
            errorlist.add("仕入先名が入力されていません");
            result = RC_INPUTERROR;
        }
        furigana = request.getParameter("furigana");
        if ((furigana == null) || (furigana.length() ==0)){
            errorlist.add("フリガナが入力されていません");
            result = RC_INPUTERROR;
        }
        furigana1 = request.getParameter("furigana1");
        furigana2 = request.getParameter("furigana2");
        yuubin = request.getParameter("yuubin");
        if ((yuubin == null) || (yuubin.length() ==0)){
            errorlist.add("郵便番号が入力されていません");
            result = RC_INPUTERROR;
        }
        if ((yuubin != null) && (yuubin.length() > 8)) {
            errorlist.add("郵便番号が長すぎます。");
            result = RC_INPUTERROR;
        }
        addr = request.getParameter("addr");
        if ((addr == null) || (addr.length() ==0)){
            errorlist.add("住所が入力されていません");
            result = RC_INPUTERROR;
        }
        tel = request.getParameter("tel");
        if ((tel == null) || (tel.length() ==0)){
            errorlist.add("電話番号が入力されていません");
            result = RC_INPUTERROR;
        }
        fax = request.getParameter("fax");
        tantoushaname1 = request.getParameter("tantoushaname1");
        tantoushaname2 = request.getParameter("tantoushaname2");

        UserLocal user = (UserLocal)session.getAttribute("USER");
        user_id = user.getUser_id();

        String updatedstr = request.getParameter("updated");
        if (updatedstr != null) {
            updated = Long.parseLong(updatedstr);
        }
    }

    public void handleEvent(HashMap attr) {
        if (result == RC_INPUTERROR) { return; }
        ShiiresakiLocalHome shome = null;
        ShiiresakiLocal shiirel = null;
        try {
            shome = (ShiiresakiLocalHome)
              ServiceLocator.getLocalHome("java:comp/env/ejb/ShiiresakiLocal");
            shiirel = shome.findByPrimaryKey(shiiresaki_id);
            long objupdated = shiirel.getUpdated().getTime();
            if (objupdated != updated) {
                result = RC_INPUTERROR;
                errorlist.add("仕入先情報を編集中にデータが更新されています。<br>再度商品データを検索してから編集してください。");
                setRollbackOnly();
                return;
            }
            shiirel.setName(name);
            shiirel.setFurigana(furigana);
            shiirel.setFurigana1(furigana1);
            shiirel.setFurigana2(furigana2);
            shiirel.setYuubin(yuubin);
            shiirel.setAddr(addr);
            shiirel.setTel(tel);
            shiirel.setFax(fax);
            shiirel.setTantoushaname1(tantoushaname1);
            shiirel.setTantoushaname2(tantoushaname2);
            shiirel.setUpdatedby(user_id);
            shiirel.setUpdated(new java.util.Date());
        } catch (NamingException ne) {
            Debug.println(ne);
            result = RC_INPUTERROR;
            errorlist.add("システムエラー・NamingException");
            setRollbackOnly();
            return;
        } catch (FinderException fe) {
            Debug.println(fe);
            result = RC_INPUTERROR;
            errorlist.add("商品コードがありません");
            setRollbackOnly();
            return;
        } catch (EJBException ee) {
            ee.printStackTrace();
            result = RC_INPUTERROR;
            errorlist.add("システムエラー・EJBException<BR>項目の入力内容が長すぎる可能性があります。そうでない場合はITチームまでご連絡ください。");
            setRollbackOnly();
        }
    }

    public void postHandle(HttpServletRequest request) {
    }
}
