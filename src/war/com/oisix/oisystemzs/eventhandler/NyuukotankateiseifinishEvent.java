package com.oisix.oisystemzs.eventhandler;

import com.oisix.oisystemfr.Debug;
import com.oisix.oisystemfr.TransactionEvent;
import com.oisix.oisystemfr.ejb.ServiceLocator;
import com.oisix.oisystemzs.ejb.NyuukoPK;
import com.oisix.oisystemzs.ejb.NyuukoLocal;
import com.oisix.oisystemzs.ejb.NyuukoLocalHome;
import com.oisix.oisystemzs.ejb.NyuukoData;
import com.oisix.oisystemzs.ejb.UserLocal;
import com.oisix.oisystemzs.ejb.exception.UkeireException;
import com.oisix.oisystemzs.ejb.exception.NyuukoModifyException;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.naming.NamingException;
import javax.ejb.FinderException;
import javax.ejb.CreateException;

public class NyuukotankateiseifinishEvent extends TransactionEvent {

    private int nyuuko_id;
    private float newtanka;
    private int user_id;

    public void init(HttpServletRequest request) {
        // ���ɔԍ��̎擾
        String nyuuko_idstr = request.getParameter("nyuuko_id");
        try {
            nyuuko_id = Integer.parseInt(nyuuko_idstr);
        } catch (NumberFormatException nfe) {
            Debug.println(nfe);
            errorlist.add("NYUUKO_ID���s���ł��B");
            result = RC_INPUTERROR;
            return;
        }
        String newtankastr = request.getParameter("newtanka");
        try {
            newtanka = Float.parseFloat(newtankastr);
        } catch (NumberFormatException nfe) {
            errorlist.add("�P�������l�ł͂���܂���");
            result = RC_INPUTERROR;
            return;
        }
        // ���[�U�̎擾
        UserLocal user = (UserLocal)session.getAttribute("USER");
        user_id = user.getUser_id();
    }

    public void handleEvent(HashMap attr) {
        if (result == RC_INPUTERROR) { return; }
        try {
            // ���Ƀf�[�^�̎擾
            NyuukoLocalHome nklh = (NyuukoLocalHome)
              ServiceLocator.getLocalHome("java:comp/env/ejb/NyuukoLocal");
            NyuukoPK pk = new NyuukoPK(nyuuko_id);
            NyuukoLocal nyuuko = nklh.findByPrimaryKey(pk);
            nyuuko.modifyTanka(newtanka, user_id);
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
        } catch (NyuukoModifyException nme){
            Debug.println(nme);
            result = RC_INPUTERROR;
            errorlist.add("�݌Ƀf�[�^�̍X�V���ɃG���[���������܂����B�G���[�̌�����IT�`�[���ɂ��₢���킹���������B");
            setRollbackOnly();
            return;
        }
    }

}
