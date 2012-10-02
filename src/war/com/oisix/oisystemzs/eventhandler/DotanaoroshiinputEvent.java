package com.oisix.oisystemzs.eventhandler;

import com.oisix.oisystemfr.Debug;
import com.oisix.oisystemfr.TransactionEvent;
import com.oisix.oisystemfr.ejb.ServiceLocator;
import com.oisix.oisystemzs.Names;
import com.oisix.oisystemzs.ejb.NyuukoLocal;
import com.oisix.oisystemzs.ejb.NyuukoLocalHome;
import com.oisix.oisystemzs.ejb.ShukkoLocal;
import com.oisix.oisystemzs.ejb.ShukkoLocalHome;
import com.oisix.oisystemzs.ejb.UserLocal;
import com.oisix.oisystemzs.ejb.SoukoData;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.LinkedList;
import java.util.Iterator;
import java.text.DateFormat;
import java.text.ParseException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.naming.NamingException;
import javax.ejb.FinderException;
import javax.ejb.CreateException;
import com.oisix.oisystemzs.ejb.exception.HaraidashiException;
import com.oisix.oisystemzs.ejb.exception.UkeireException;

public class DotanaoroshiinputEvent extends TransactionEvent {

    private int shukkokubun = 0;
    private java.util.Date tanaoroshidate;
    private TreeMap alltanaoroshilist = new TreeMap();
    private int page_id;
    private int next_page_id;
    private String shorikubun;

//    private final int onePageColumn = 20;
    private final int onePageColumn = 10;
    public int countTanaoroshi() {
        return alltanaoroshilist.size();
    }
    private int firstIndex() {
        return onePageColumn * page_id + 1;
    }
    private int lastIndex() {
        int last = onePageColumn * (page_id + 1);
        if (last > countTanaoroshi()) {
            last = countTanaoroshi();
        }
        return last;
    }

    private int user_id;
    private String souko_id;

    public void init(HttpServletRequest request) {
        // �o�ח\�薾�׃��X�g�̎擾
        alltanaoroshilist =
          (TreeMap)session.getAttribute("#TANAOROSHILIST");
        if (alltanaoroshilist == null) {
            errorlist.add("SystemError/TANAOROSHILIST���擾�ł��܂���B");
            result = RC_INPUTERROR;
            return;
        }

        // �y�[�W�ԍ��̎擾
        Integer page_idint = (Integer)session.getAttribute("#TANAOROSHIPAGE");
        if (page_idint == null) {
            page_idint = new Integer(0);
        }
        try {
            page_id = page_idint.intValue();
        } catch (NumberFormatException nfe) {
            result = RC_INPUTERROR;
            errorlist.add("SystemError/�y�[�W�ԍ����擾�ł��܂���B");
            return;
        }

        // �I�����̎擾
        tanaoroshidate =
          (java.util.Date)session.getAttribute("#TANAOROSHIDATE");
        if (tanaoroshidate == null) {
            result = RC_INPUTERROR;
            errorlist.add("SystemError/�I�������擾�ł��܂���");
            return;
        }

        // �����敪
        shorikubun = getInput("#shorikubun");
        if (shorikubun == null || shorikubun.equals("")) {
            errorlist.add("SystemError/SHORIKUBUN���擾�ł��܂���B");
            result = RC_INPUTERROR;
            return;
        }

        TreeMap newtanaoroshilist = new TreeMap();
        // �I���Ɋւ�����͏����擾
        for (int i = firstIndex(); i <= lastIndex(); i++) {
            String strInd = String.valueOf(i);
            Integer intInd = new Integer(i);
            HashMap tanaoroshi = (HashMap)alltanaoroshilist.get(intInd);
            if (tanaoroshi == null) {
                errorlist.add("SystemError/" + strInd + "�Ԗڂ�" +
                  "�I���f�[�^���擾�ł��܂���B");
                result = RC_INPUTERROR;
                continue;
            }

            // �ܖ������̎擾
            java.util.Date shoumikigen = null;
            String shoumikigenstr = getInput("#shoumikigen" + strInd);
            if (shoumikigenstr == null || shoumikigenstr.equals("")) {
                tanaoroshi.put("SHOUMIKIGEN", null);
            } else {
                try {
                    DateFormat df = DateFormat.getDateInstance();
                    shoumikigen = df.parse(shoumikigenstr);
                    tanaoroshi.put("SHOUMIKIGEN", shoumikigen);
                } catch (ParseException pe) {
                    Debug.println(pe);
                    errorlist.add("SystemError/" + strInd + "�Ԗڂ�" +
                      "SHOUMIKIGEN���s���ł��B");
                    result = RC_INPUTERROR;
                    continue;
                }
            }

            // �o�׊����̎擾
            java.util.Date shukkakigen = null;
            String shukkakigenstr = getInput("#shukkakigen" + strInd);
            if (shukkakigenstr == null || shukkakigenstr.equals("")) {
                tanaoroshi.put("SHUKKAKIGEN", null);
            } else {
                try {
                    DateFormat df = DateFormat.getDateInstance();
                    shukkakigen = df.parse(shukkakigenstr);
                    tanaoroshi.put("SHUKKAKIGEN", shukkakigen);
                } catch (ParseException pe) {
                    Debug.println(pe);
                    errorlist.add("SystemError/" + strInd + "�Ԗڂ�" +
                      "SHUKKAKIGEN���s���ł��B");
                    result = RC_INPUTERROR;
                    continue;
                }
            }

            // ���ʂ̎擾
            String suuryoustr = getInput("#suuryou" + strInd);
            Float suuryou = null;
            if (suuryoustr == null || suuryoustr.equals("")) {
                errorlist.add(strInd + "�Ԗڂ̐��ʂ������͂ł��B");
                result = RC_INPUTERROR;
                tanaoroshi.put("SUURYOU", "");
            } else {
                try {
                    suuryou = new Float(suuryoustr);
                    if (suuryou.floatValue() < 0) {
                        errorlist.add(strInd + "�Ԗڂ̐��ʂ����ł��B");
                        result = RC_INPUTERROR;
                    }
                    tanaoroshi.put("SUURYOU", suuryou);
                } catch (NumberFormatException nfe) {
                    Debug.println(nfe);
                    errorlist.add(strInd + "�Ԗڂ̐��ʂ��s���ł��B");
                    result = RC_INPUTERROR;
                    tanaoroshi.put("SUURYOU", suuryoustr);
                }
            }
            newtanaoroshilist.put(intInd, tanaoroshi);
        }

        if (result == RC_INPUTERROR) {
            next_page_id = page_id;
            return;
        }
        // ���͘R���~�X�͂Ȃ�
        alltanaoroshilist.putAll(newtanaoroshilist);
        if (shorikubun.equals("PREV")) {
            next_page_id = page_id - 1;
        }
        else if (shorikubun.equals("NEXT")) {
            next_page_id = page_id + 1;
        }
        if (!shorikubun.equals("INPUT")) {
            result = RC_INPUTERROR;
        } else {
            // ���[�U�̎擾
            UserLocal user = (UserLocal)session.getAttribute("USER");
            user_id = user.getUser_id();
            SoukoData souko = (SoukoData)session.getAttribute("SOUKO");
            souko_id = souko.getSouko_id();
        }
    }

    public void handleEvent(HashMap attr) {
        if (result == RC_INPUTERROR) { return; }
        // �ȉ��A�o�Ƀf�[�^�쐬
        int i = 0;
        try {
            NyuukoLocalHome nklh = (NyuukoLocalHome)
              ServiceLocator.getLocalHome("java:comp/env/ejb/NyuukoLocal");
            ShukkoLocalHome sklh = (ShukkoLocalHome)
              ServiceLocator.getLocalHome("java:comp/env/ejb/ShukkoLocal");
            for (i = 1; i <= alltanaoroshilist.size(); i++) {
                Integer intInd = new Integer(i);
                HashMap tanaoroshi = (HashMap)alltanaoroshilist.get(intInd);
                float suuryou =
                  ((Float)tanaoroshi.get("SUURYOU")).floatValue();
                float motosuuryou =
                  ((Float)tanaoroshi.get("MOTOSUURYOU")).floatValue();
                if (suuryou > motosuuryou) {
                    // �I�������̓���
                    float newsuuryou = suuryou - motosuuryou;
                    NyuukoLocal newnyuuko = nklh.create(
                      tanaoroshidate,
                      souko_id,
                      (String)tanaoroshi.get("SHOUHIN_ID"),
                      newsuuryou,
                      (String)tanaoroshi.get("TANI"),
                      ((Float)tanaoroshi.get("TANKA")).floatValue(),
                      newsuuryou,
                      (String)tanaoroshi.get("TANI"),
                      ((Float)tanaoroshi.get("TANKA")).floatValue(),
                      (java.util.Date)tanaoroshi.get("SHOUMIKIGEN"),
                      (java.util.Date)tanaoroshi.get("SHUKKAKIGEN"),
                      4, // NYUUKOKUBUN
                      null,  // SHIIRESAKI_ID
                      null,  // NOUHINSAKI_ID
                      Names.NONE_ID,  // ���ח\��R�[�h
                      Names.OFF,      // �����t���O
                      Names.NONE_ID,  // �����o�ɃR�[�h
                      user_id
                    );
                } else if (suuryou < motosuuryou) {
                    // �I�����Ղ̏o��
                    float newsuuryou = motosuuryou - suuryou;
                    float tanka =
                      ((Float)tanaoroshi.get("TANKA")).floatValue();
                    float kingaku = newsuuryou * tanka;
                    ShukkoLocal newshukko = sklh.create(
                      18, // SHUKKOKUBUN
                      null,  // NOUHINSAKI_ID
                      souko_id,
                      (String)tanaoroshi.get("SHOUHIN_ID"),
                      Names.DUMMY_LOCATION_ID,
                      tanaoroshidate,
                      (java.util.Date)tanaoroshi.get("SHOUMIKIGEN"),
                      newsuuryou,
                      (String)tanaoroshi.get("TANI"),
                      0, // �P���͎����v�Z
                      tanka,
                      kingaku,
                      Names.NONE_ID,  // �o�ח\�薾�׃R�[�h
                      Names.OFF,      // �����t���O
                      Names.NONE_ID,  // �����o�ɃR�[�h
                      user_id,   // �W������
                      0
                    );
                }
            }
        } catch (NamingException ne) {
            Debug.println(ne);
            result = RC_INPUTERROR;
            errorlist.add("�V�X�e���G���[�ENamingException");
            setRollbackOnly();
            next_page_id = (int)((i - 1) / onePageColumn);
            return;
        } catch (CreateException ce) {
            Debug.println(ce);
            result = RC_INPUTERROR;
            errorlist.add("�V�X�e���G���[�ECreateException");
            setRollbackOnly();
            next_page_id = (int)((i - 1) / onePageColumn);
            return;
        } catch (UkeireException ue) {
            Debug.println(ue);
            result = RC_INPUTERROR;
            errorlist.add("�V�X�e���G���[�EUkeireException");
            setRollbackOnly();
            next_page_id = (int)((i - 1) / onePageColumn);
            return;
        } catch (HaraidashiException he) {
            Debug.println(he);
            result = RC_INPUTERROR;
            errorlist.add("�V�X�e���G���[�EHaraidashiException");
            setRollbackOnly();
            next_page_id = (int)((i - 1) / onePageColumn);
            return;
        }
    }

    public void postHandle(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (result == RC_INPUTERROR) {
            session.setAttribute("#TANAOROSHILIST", alltanaoroshilist);
            session.setAttribute("#TANAOROSHIPAGE", new Integer(next_page_id));
        }
    }
}
