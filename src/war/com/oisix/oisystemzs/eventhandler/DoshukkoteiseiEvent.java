package com.oisix.oisystemzs.eventhandler;

import com.oisix.oisystemfr.Debug;
import com.oisix.oisystemfr.TransactionEvent;
import com.oisix.oisystemfr.ejb.ServiceLocator;
import com.oisix.oisystemzs.ejb.ShukkoPK;
import com.oisix.oisystemzs.ejb.ShukkoLocal;
import com.oisix.oisystemzs.ejb.ShukkoLocalHome;
import com.oisix.oisystemzs.ejb.ShukkoData;
import com.oisix.oisystemzs.ejb.UserLocal;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.naming.NamingException;
import javax.ejb.FinderException;
import javax.ejb.CreateException;
import com.oisix.oisystemzs.ejb.exception.HaraidashiException;
import com.oisix.oisystemzs.ejb.exception.ShukkoModifyException;

public class DoshukkoteiseiEvent extends TransactionEvent {

    private String strShukko_id;
    private int motoshukko_id;

    private String motoshukko_bg;
    private String teiseishukko_bg;

    private boolean isAutoCalcTanka;
    private int user_id;
    private float suuryou;
    private float tanka;

    public void init(HttpServletRequest request) {
        // �o�ɔԍ��̎擾
        strShukko_id = getInput("#shukko_id");
        try {
            motoshukko_id = Integer.parseInt(strShukko_id);
        } catch (NumberFormatException nfe) {
            Debug.println(nfe);
            errorlist.add("SHUKKO_ID���s���ł��B");
            result = RC_INPUTERROR;
            return;
        }
        // ���ʂ̎擾
        String strSuuryou = getInput("#suuryou");
        try {
            suuryou = Float.parseFloat(strSuuryou);
        } catch (NumberFormatException nfe) {
            Debug.println(nfe);
            errorlist.add("���ʂ��s���ł��B");
            result = RC_INPUTERROR;
        }
        // ���[�U�̎擾
        UserLocal user = (UserLocal)session.getAttribute("USER");
        user_id = user.getUser_id();
    }

    public void handleEvent(HashMap attr) {
        if (result == RC_INPUTERROR) { return; }
        try {
            // ���o�Ƀf�[�^�̎擾
            ShukkoLocalHome sklh = (ShukkoLocalHome)
              ServiceLocator.getLocalHome("java:comp/env/ejb/ShukkoLocal");
            ShukkoPK pk = new ShukkoPK(motoshukko_id);
            // �����������o�ɂ̎擾
            ShukkoLocal motoshukko = sklh.findByPrimaryKey(pk);
            motoshukko_bg = motoshukko.getShukko_bg();
            // �o�ɒ���
            ShukkoLocal teiseishukko = motoshukko.modify(suuryou, user_id);
            teiseishukko_bg = teiseishukko.getShukko_bg();
        } catch (NamingException ne) {
            Debug.println(ne);
            result = RC_INPUTERROR;
            errorlist.add("�V�X�e���G���[�ENamingException");
            setRollbackOnly();
            return;
        } catch (FinderException fe) {
            Debug.println(fe);
            result = RC_INPUTERROR;
            errorlist.add("�V�X�e���G���[�EFinderException");
            setRollbackOnly();
            return;
        } catch (ShukkoModifyException sme) {
            Debug.println(sme);
            result = RC_INPUTERROR;
            errorlist.add("�V�X�e���G���[�EShukkoModifyException");
            setRollbackOnly();
            return;
        } catch (HaraidashiException he) {
            Debug.println(he);
            result = RC_INPUTERROR;
            errorlist.add("�V�X�e���G���[�EHaraidashiException");
            setRollbackOnly();
            return;
        }
    }

    public void postHandle(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("MOTOSHUKKO_BG", motoshukko_bg);
        session.setAttribute("TEISEIHUKKO_BG", teiseishukko_bg);
        session.setAttribute("TEISEISHUKKO_ID", strShukko_id);
    }
}
