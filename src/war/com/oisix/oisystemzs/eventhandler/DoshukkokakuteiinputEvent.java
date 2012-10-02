package com.oisix.oisystemzs.eventhandler;

import com.oisix.oisystemfr.Debug;
import com.oisix.oisystemfr.TransactionEvent;
import com.oisix.oisystemfr.ejb.ServiceLocator;
import com.oisix.oisystemzs.Names;
import com.oisix.oisystemzs.ejb.ShukkoPK;
import com.oisix.oisystemzs.ejb.ShukkoLocal;
import com.oisix.oisystemzs.ejb.ShukkoLocalHome;
import com.oisix.oisystemzs.ejb.ShukkoData;
import com.oisix.oisystemzs.ejb.UserLocal;
import com.oisix.oisystemzs.ejb.SoukoData;
import com.oisix.oisystemzs.objectmap.ShukkokubunMap;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.Calendar;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.naming.NamingException;
import javax.ejb.FinderException;
import javax.ejb.CreateException;
import com.oisix.oisystemzs.ejb.exception.HaraidashiException;
import com.oisix.oisystemzs.ejb.exception.ZaikoSuuryouHaraidashiException;
import com.oisix.oisystemzs.ejb.exception.ZaikoTankaHaraidashiException;
import com.oisix.oisystemzs.ejb.exception.ZaikoKingakuHaraidashiException;
import com.oisix.oisystemzs.ejb.exception.ZmeisaiSuuryouHaraidashiException;
import com.oisix.oisystemzs.ejb.exception.ZmeisaiShoumiHaraidashiException;

public class DoshukkokakuteiinputEvent extends TransactionEvent {

    private LinkedList shukkoList = new LinkedList();
    private LinkedList resultShukkoList = new LinkedList();
    private java.util.Date shukko_date;
    private int shukkokubun;

    private int user_id;
    private String souko_id;

    public void init(HttpServletRequest request) {
        // �o�ɓ��̎擾
        int year = 0;
        int month = 0;
        int day = 0;
        String shukko_yearstr = getInput("#shukko_year");
        String shukko_monthstr = getInput("#shukko_month");
        String shukko_datestr = getInput("#shukko_date");
        try {
            year = Integer.parseInt(shukko_yearstr);
            month = Integer.parseInt(shukko_monthstr);
            day = Integer.parseInt(shukko_datestr);
            Calendar cal = Calendar.getInstance();
            cal.clear();
            cal.set(year, month - 1, day);
            shukko_date = cal.getTime();
        } catch (NumberFormatException nfe) {
            Debug.println(nfe);
            errorlist.add("�o�ɓ����s���ł��B");
            result = RC_INPUTERROR;
        }
        // �o�ɋ敪�̎擾
        String shukkokubunstr = getInput("#shukkokubun");
        if (shukkokubunstr == null || shukkokubunstr.equals("")) {
            errorlist.add("�o�ɋ敪���I������Ă��܂���B");
            result = RC_INPUTERROR;
            return;
        }
        try {
            shukkokubun = Integer.parseInt(shukkokubunstr);
        } catch (NumberFormatException nfe) {
            Debug.println(nfe);
            errorlist.add("�o�ɋ敪���I������Ă��܂���B");
            result = RC_INPUTERROR;
            return;
        }

        String meisaisuustr = getInput("#meisaisuu");
        int meisaisuu = 0;
        if (meisaisuustr == null || meisaisuustr.equals("")) {
            errorlist.add("MEISAISUU���擾�ł��܂���B");
            result = RC_INPUTERROR;
            return;
        } else {
            try {
                meisaisuu = new Integer(meisaisuustr).intValue();
            } catch (NumberFormatException nfe) {
                Debug.println(nfe);
                errorlist.add("MEISAISUU���s���ł��B");
                result = RC_INPUTERROR;
                return;
            }
        }

        for (int i = 0; i < meisaisuu; i++) {
            String strInd = String.valueOf(i);
            String meisai_idstr = getInput("#shukkayoteimeisai_id"+strInd);
            int meisai_id = 0;
            if (meisai_idstr == null || meisai_idstr.equals("")) {
                errorlist.add("SHUKKAYOTEIMEISAI_ID���擾�ł��܂���B");
                result = RC_INPUTERROR;
                return;
            }
            try {
                meisai_id = new Integer(meisai_idstr).intValue();
            } catch (NumberFormatException nfe) {
                Debug.println(nfe);
                errorlist.add("SHUKKAYOTEIMEISAI_ID���s���ł��B");
                result = RC_INPUTERROR;
                return;
            }
            String shouhin_id = getInput("#shouhin_id"+strInd);
            // ���iID���擾�ł�����̂ݏ���
            if (shouhin_id == null || shouhin_id.equals("")) {
                errorlist.add("SHOUHIN_ID���擾�ł��܂���B");
                result = RC_INPUTERROR;
                return;
            }
            String zaikoindexstr = getInput("#zaikoindex"+strInd);
            int zaikoindex = 0;
            if (zaikoindexstr == null || zaikoindexstr.equals("")) {
                errorlist.add("ZAIKOINDEX���擾�ł��܂���B");
                result = RC_INPUTERROR;
                return;
            }
            try {
                zaikoindex = new Integer(zaikoindexstr).intValue();
            } catch (NumberFormatException nfe) {
                Debug.println(nfe);
                errorlist.add("ZAIKOINDEX���s���ł��B");
                result = RC_INPUTERROR;
                return;
            }

            for (int j = 0; j < zaikoindex; j++) {
                String strZInd = strInd + "_" + String.valueOf(j);

                HashMap shukkoMap = new HashMap();
                shukkoMap.put("meisai_id", new Integer(meisai_id));
                shukkoMap.put("shouhin_id", shouhin_id);

                // �ܖ������̎擾
                String shoumikigenstr = getInput("#shoumikigen" + strZInd);
                java.util.Date shoumikigen = null;
                if (shoumikigenstr != null && !shoumikigenstr.equals("")) {
                    try {
                        DateFormat df = DateFormat.getDateInstance();
                        shoumikigen = df.parse(shoumikigenstr);
                    } catch (ParseException pe) {
                        Debug.println(pe);
                        errorlist.add("�ܖ��������s���ł��B");
                        result = RC_INPUTERROR;
                        return;
                    }
                }
                shukkoMap.put("shoumikigen", shoumikigen);
                // ���ʂ̎擾
                String suuryoustr = getInput("#suuryou" + strZInd);
                Float suuryou = null;
                if (suuryoustr == null || suuryoustr.equals("")) {
                    errorlist.add("���ʂ����͂���Ă��܂���B");
                    result = RC_INPUTERROR;
                    return;
                }
                try {
                    suuryou = new Float(suuryoustr);
                    shukkoMap.put("suuryou", suuryou);
                } catch (NumberFormatException nfe) {
                    Debug.println(nfe);
                    errorlist.add("���ʂ��s���ł��B");
                    result = RC_INPUTERROR;
                    return;
                }
                // ���ʂ�0�̏ꍇ�͏o�Ƀf�[�^���쐬���Ȃ�
                if (suuryou.floatValue() == 0.0f) {
                    continue;
                }
                // �P�ʂ̎擾
                String tani = getInput("#tani" + strZInd);
                if (tani == null || tani.equals("")) {
                    errorlist.add("�P�ʂ����I���ł��B");
                    result = RC_INPUTERROR;
                    return;
                } else {
                    shukkoMap.put("tani", tani);
                }
                // �P���̎擾
                String tankastr = getInput("#tanka" + strZInd);
                Float tanka = null;
                if (tankastr == null || tankastr.equals("")) {
                    errorlist.add("�P�������͂���Ă��܂���B");
                    result = RC_INPUTERROR;
                    return;
                }
                try {
                    tanka = new Float(tankastr);
                    shukkoMap.put("tanka", tanka);
                } catch (NumberFormatException nfe) {
                    Debug.println(nfe);
                    errorlist.add("�P�����s���ł��B");
                    result = RC_INPUTERROR;
                    return;
                }
                // ���z�̎擾
                String kingakustr = getInput("#kingaku"+strZInd);
                Float kingaku = null;
                if (kingakustr == null || kingakustr.equals("")) {
                    errorlist.add("���z�����͂���Ă��܂���B");
                    result = RC_INPUTERROR;
                    return;
                }
                try {
                    kingaku = new Float(kingakustr);
                    shukkoMap.put("kingaku", kingaku);
                } catch (NumberFormatException nfe) {
                    Debug.println(nfe);
                    errorlist.add("���z���s���ł��B");
                    result = RC_INPUTERROR;
                    return;
                }
                // �W�������̎擾
                String hyoujunbaikastr = getInput("#hyoujunbaika"+strZInd);
                Float hyoujunbaika = null;
                if (hyoujunbaikastr == null || hyoujunbaikastr.equals("")) {
                    errorlist.add("�W�����������͂���Ă��܂���B");
                    result = RC_INPUTERROR;
                    return;
                }
                try {
                    hyoujunbaika = new Float(hyoujunbaikastr);
                    shukkoMap.put("hyoujunbaika", hyoujunbaika);
                } catch (NumberFormatException nfe) {
                    Debug.println(nfe);
                    errorlist.add("�W���������s���ł��B");
                    result = RC_INPUTERROR;
                    return;
                }
                shukkoList.add(shukkoMap);
            }
        }

        if (shukkoList.size() == 0) {
            errorlist.add("�o�ɂ��P���쐬����܂���B");
            result = RC_INPUTERROR;
        }

        // ���[�U�̎擾
        UserLocal user = (UserLocal)session.getAttribute("USER");
        user_id = user.getUser_id();
        SoukoData souko = (SoukoData)session.getAttribute("SOUKO");
        souko_id = souko.getSouko_id();
    }

    public void handleEvent(HashMap attr) {
        if (result == RC_INPUTERROR) { return; }
        java.util.Date now = new java.util.Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/M/d");
        try {
            ShukkoLocalHome sklh = (ShukkoLocalHome)
              ServiceLocator.getLocalHome("java:comp/env/ejb/ShukkoLocal");
            Iterator iter = shukkoList.iterator();
            while (iter.hasNext()) {
                HashMap shukkoMap = (HashMap)iter.next();
                ShukkoLocal newshukko = sklh.create(
                  //((Integer)shukkoMap.get("shukkokubun")).intValue(),
                  shukkokubun,
                  null,
                  souko_id,
                  (String)shukkoMap.get("shouhin_id"),
                  Names.DUMMY_LOCATION_ID,
                  shukko_date,
                  (java.util.Date)shukkoMap.get("shoumikigen"),
                  ((Float)shukkoMap.get("suuryou")).floatValue(),
                  (String)shukkoMap.get("tani"),
                  0, // �P���͎����v�Z
                  ((Float)shukkoMap.get("tanka")).floatValue(),
                  ((Float)shukkoMap.get("kingaku")).floatValue(),
                  ((Integer)shukkoMap.get("meisai_id")).intValue(),
                  Names.OFF,
                  Names.NONE_ID,
                  user_id,
                  ((Float)shukkoMap.get("hyoujunbaika")).floatValue()
                );
                // ���ʕ\���p�̃f�[�^�쐬
                HashMap item = new HashMap();
                item.put("shukko_id", new Integer(newshukko.getShukko_id()));
                item.put("shukko_bg", newshukko.getShukko_bg());
                item.put("shouhin_id", newshukko.getShouhin_id());
                item.put("shoumikigen", newshukko.getShoumikigen());
                item.put("shouhinmei", newshukko.getShouhin().getShouhin());
                item.put("suuryou", new Float(newshukko.getSuuryou()));
                resultShukkoList.add(item);
            }
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
        } catch (CreateException ce) {
            Debug.println(ce);
            result = RC_INPUTERROR;
            errorlist.add("�V�X�e���G���[�ECreateException");
            setRollbackOnly();
            return;
        } catch (ZaikoSuuryouHaraidashiException zhe) {
            Debug.println(zhe);
            result = RC_INPUTERROR;
            String errorcomment = "<dl>\n" +
              "<dt>�݌ɐ��ʂ�0�������܂����B</dt>\n" +
              "<dd>�o�ɔԍ�:" + zhe.getShukko().getShukko_bg() + "</dd>\n" +
              "<dd>�o�ɓ�:" + sdf.format(
                zhe.getShukko().getShukko_date()) + "</dd>\n" +
              "<dd>�o�ɋ敪:" + ShukkokubunMap.getShukkokubun(
                zhe.getShukko().getShukkokubun()).getShukkokubun() +
                "</dd>\n" +
              "<dd>���i�R�[�h:" +
                zhe.getZaiko().getShouhin_id() + "</dd>\n" +
              "<dd>�݌ɐ���:" + zhe.getZaiko().getSuuryou() + "</dd>\n" +
              "<dd>�o�א���:" + zhe.getShukko().getSuuryou() + "</dd>\n" +
              "</dl>";
            errorlist.add(errorcomment);
            setRollbackOnly();
            return;
        } catch (ZaikoTankaHaraidashiException zhe) {
            Debug.println(zhe);
            result = RC_INPUTERROR;
            String errorcomment = "<dl>\n" +
              "<dt>�݌ɒP����0�~�������܂����B</dt>\n" +
              "<dd>�o�ɔԍ�:" + zhe.getShukko().getShukko_bg() + "</dd>\n" +
              "<dd>�o�ɓ�:" + sdf.format(
                zhe.getShukko().getShukko_date()) + "</dd>\n" +
              "<dd>�o�ɋ敪:" + ShukkokubunMap.getShukkokubun(
                zhe.getShukko().getShukkokubun()).getShukkokubun() +
                "</dd>\n" +
              "<dd>���i�R�[�h:" +
                zhe.getZaiko().getShouhin_id() + "</dd>\n" +
              "<dd>�݌ɐ���:" + zhe.getZaiko().getSuuryou() + "</dd>\n" +
              "<dd>�o�א���:" + zhe.getShukko().getSuuryou() + "</dd>\n" +
              "</dl>";
            errorlist.add(errorcomment);
            setRollbackOnly();
            return;
        } catch (ZaikoKingakuHaraidashiException zhe) {
            Debug.println(zhe);
            result = RC_INPUTERROR;
            String errorcomment = "<dl>\n" +
              "<dt>�݌ɋ��z��0�~�������܂����B</dt>\n" +
              "<dd>�o�ɔԍ�:" + zhe.getShukko().getShukko_bg() + "</dd>\n" +
              "<dd>�o�ɓ�:" + sdf.format(
                zhe.getShukko().getShukko_date()) + "</dd>\n" +
              "<dd>�o�ɋ敪:" + ShukkokubunMap.getShukkokubun(
                zhe.getShukko().getShukkokubun()).getShukkokubun() +
                "</dd>\n" +
              "<dd>���i�R�[�h:" +
                zhe.getZaiko().getShouhin_id() + "</dd>\n" +
              "<dd>�݌ɐ���:" + zhe.getZaiko().getSuuryou() + "</dd>\n" +
              "<dd>�o�א���:" + zhe.getShukko().getSuuryou() + "</dd>\n" +
              "</dl>";
            errorlist.add(errorcomment);
            setRollbackOnly();
            return;
        } catch (ZmeisaiSuuryouHaraidashiException zmhe) {
            Debug.println(zmhe);
            result = RC_INPUTERROR;
            String errorcomment = "<dl>\n" +
              "<dt>�݌ɖ��ׂ̐��ʂ�0�������܂����B</dt>\n" +
              "<dd>�o�ɔԍ�:" + zmhe.getShukko().getShukko_bg() + "</dd>\n" +
              "<dd>�o�ɓ�:" + sdf.format(
                zmhe.getShukko().getShukko_date()) + "</dd>\n" +
              "<dd>�o�ɋ敪:" + ShukkokubunMap.getShukkokubun(
                zmhe.getShukko().getShukkokubun()).getShukkokubun() +
                "</dd>\n" +
              "<dd>���i�R�[�h:" +
                zmhe.getShukko().getShouhin_id() + "</dd>\n";
            if (zmhe.getShukko().getShoumikigen() != null) {
                errorcomment += "<dd>�ܖ�����:" +
                  sdf.format(zmhe.getShukko().getShoumikigen()) + "</dd>\n";
            }
            if (zmhe.getZaikomeisai() != null) {
                errorcomment += "<dd>�݌ɐ���:" +
                  zmhe.getZaikomeisai().getSuuryou() + "</dd>\n";
            }
            errorcomment +=
              "<dd>�o�א���:" + zmhe.getShukko().getSuuryou() + "</dd>\n" +
              "</dl>";
            errorlist.add(errorcomment);
            setRollbackOnly();
            return;
        } catch (ZmeisaiShoumiHaraidashiException zmhe) {
            Debug.println(zmhe);
            result = RC_INPUTERROR;
            String errorcomment = "<dl>\n" +
              "<dt>�o�ɂŎw�肳�ꂽ�݌ɖ��ׂ����݂��܂���B</dt>\n" +
              "<dd>�o�ɔԍ�:" + zmhe.getShukko().getShukko_bg() + "</dd>\n" +
              "<dd>�o�ɓ�:" + sdf.format(
                zmhe.getShukko().getShukko_date()) + "</dd>\n" +
              "<dd>�o�ɋ敪:" + ShukkokubunMap.getShukkokubun(
                zmhe.getShukko().getShukkokubun()).getShukkokubun() +
                "</dd>\n" +
              "<dd>���i�R�[�h:" +
                zmhe.getShukko().getShouhin_id() + "</dd>\n";
            if (zmhe.getShukko().getShoumikigen() != null) {
                errorcomment += "<dd>�ܖ�����:" +
                  sdf.format(zmhe.getShukko().getShoumikigen()) + "</dd>\n";
            }
            if (zmhe.getZaikomeisai() != null) {
                errorcomment += "<dd>�݌ɐ���:" +
                  zmhe.getZaikomeisai().getSuuryou() + "</dd>\n";
            }
            errorcomment +=
              "<dd>�o�א���:" + zmhe.getShukko().getSuuryou() + "</dd>\n" +
              "</dl>";
            errorlist.add(errorcomment);
            setRollbackOnly();
            return;
        } catch (HaraidashiException he) {
            Debug.println(he);
            result = RC_INPUTERROR;
            String errorcomment =
              "�o�ɏ������ɃG���[���������܂����B" +
              "(" + he.getMessage() + ")";
            errorlist.add(errorcomment);
            setRollbackOnly();
            return;
        }
    }

    public void postHandle(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (result == RC_INPUTERROR) {
            session.setAttribute("SHUKKO_DATE", shukko_date);
        } else {
            session.setAttribute("SHUKKO_DATE", shukko_date);
            session.setAttribute("INPUTRESULTSHUKKOLIST", resultShukkoList);
        }
    }
}
