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
import javax.ejb.CreateException;
import javax.ejb.DuplicateKeyException;

public class DoshiiresakiinputEvent extends TransactionEvent {

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

    public void init(HttpServletRequest request) {
        shiiresaki_id = getInput("#shiiresaki_id");
        if ((shiiresaki_id == null) || (shiiresaki_id.length() ==0)){
            errorlist.add("仕入先コードが入力されていません");
            result = RC_INPUTERROR;
        }
        name = getInput("#name");
        if ((name == null) || (name.length() ==0)){
            errorlist.add("仕入先名が入力されていません");
            result = RC_INPUTERROR;
        }
        furigana = getInput("#furigana");
        if ((furigana == null) || (furigana.length() ==0)){
            errorlist.add("フリガナが入力されていません");
            result = RC_INPUTERROR;
        }
        furigana1 = getInput("#furigana1");
        furigana2 = getInput("#furigana2");
        yuubin = getInput("#yuubin");
        if ((yuubin == null) || (yuubin.length() ==0)){
            errorlist.add("郵便番号が入力されていません");
            result = RC_INPUTERROR;
        }
        if ((yuubin != null) && (yuubin.length() > 8)) {
            errorlist.add("郵便番号が長すぎます。");
            result = RC_INPUTERROR;
        }
        addr = getInput("#addr");
        if ((addr == null) || (addr.length() ==0)){
            errorlist.add("住所が入力されていません");
            result = RC_INPUTERROR;
        }
        tel = getInput("#tel");
        if ((tel == null) || (tel.length() ==0)){
            errorlist.add("電話番号が入力されていません");
            result = RC_INPUTERROR;
        }
        fax = getInput("#fax");
        tantoushaname1 = getInput("#tantoushaname1");
        tantoushaname2 = getInput("#tantoushaname2");

        UserLocal user = (UserLocal)session.getAttribute("USER");
        user_id = user.getUser_id();
    }

    public void handleEvent(HashMap attr) {
        if (result == RC_INPUTERROR) { return; }
        java.util.Date now = new java.util.Date();
        try {
            ShiiresakiLocalHome shome = (ShiiresakiLocalHome)
              ServiceLocator.getLocalHome("java:comp/env/ejb/ShiiresakiLocal");
            ShiiresakiLocal shiiresakil = shome.create(
              shiiresaki_id,
              name,
              furigana,
              furigana1,
              furigana2,
              yuubin,
              addr,
              tel,
              fax,
              tantoushaname1,
              tantoushaname2,
              user_id
            );
        } catch (NamingException ne) {
            Debug.println(ne);
            result = RC_INPUTERROR;
            errorlist.add("システムエラー・NamingException");
            setRollbackOnly();
            return;
        } catch (DuplicateKeyException de) {
            Debug.println(de);
            result = RC_INPUTERROR;
            errorlist.add("仕入先コードが重複しています");
            setRollbackOnly();
            return;
        } catch (CreateException ce) {
            String message = ce.getMessage();
            System.out.println("message:"+message);
            //Oracle依存なエラー制御
            if (message.indexOf("ORA-01401") > 0) {
                errorlist.add("入力値が長すぎます。");
            } else {
                ce.printStackTrace();
                errorlist.add("システムエラー・CreateException");
            }
            result = RC_INPUTERROR;
            setRollbackOnly();
            return;
        }
    }

    public void postHandle(HttpServletRequest request) {
    }
}
