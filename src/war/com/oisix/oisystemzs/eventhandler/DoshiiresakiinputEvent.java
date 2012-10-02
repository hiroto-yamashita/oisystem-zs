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
            errorlist.add("�d����R�[�h�����͂���Ă��܂���");
            result = RC_INPUTERROR;
        }
        name = getInput("#name");
        if ((name == null) || (name.length() ==0)){
            errorlist.add("�d���於�����͂���Ă��܂���");
            result = RC_INPUTERROR;
        }
        furigana = getInput("#furigana");
        if ((furigana == null) || (furigana.length() ==0)){
            errorlist.add("�t���K�i�����͂���Ă��܂���");
            result = RC_INPUTERROR;
        }
        furigana1 = getInput("#furigana1");
        furigana2 = getInput("#furigana2");
        yuubin = getInput("#yuubin");
        if ((yuubin == null) || (yuubin.length() ==0)){
            errorlist.add("�X�֔ԍ������͂���Ă��܂���");
            result = RC_INPUTERROR;
        }
        if ((yuubin != null) && (yuubin.length() > 8)) {
            errorlist.add("�X�֔ԍ����������܂��B");
            result = RC_INPUTERROR;
        }
        addr = getInput("#addr");
        if ((addr == null) || (addr.length() ==0)){
            errorlist.add("�Z�������͂���Ă��܂���");
            result = RC_INPUTERROR;
        }
        tel = getInput("#tel");
        if ((tel == null) || (tel.length() ==0)){
            errorlist.add("�d�b�ԍ������͂���Ă��܂���");
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
            errorlist.add("�V�X�e���G���[�ENamingException");
            setRollbackOnly();
            return;
        } catch (DuplicateKeyException de) {
            Debug.println(de);
            result = RC_INPUTERROR;
            errorlist.add("�d����R�[�h���d�����Ă��܂�");
            setRollbackOnly();
            return;
        } catch (CreateException ce) {
            String message = ce.getMessage();
            System.out.println("message:"+message);
            //Oracle�ˑ��ȃG���[����
            if (message.indexOf("ORA-01401") > 0) {
                errorlist.add("���͒l���������܂��B");
            } else {
                ce.printStackTrace();
                errorlist.add("�V�X�e���G���[�ECreateException");
            }
            result = RC_INPUTERROR;
            setRollbackOnly();
            return;
        }
    }

    public void postHandle(HttpServletRequest request) {
    }
}
