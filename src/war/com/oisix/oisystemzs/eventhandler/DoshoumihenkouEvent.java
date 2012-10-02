package com.oisix.oisystemzs.eventhandler;

import com.oisix.oisystemfr.Debug;
import com.oisix.oisystemfr.DateUtil;
import com.oisix.oisystemfr.TransactionEvent;
import com.oisix.oisystemfr.ejb.ServiceLocator;
import com.oisix.oisystemzs.Names;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.oisix.oisystemzs.objectmap.TaniMap;
import com.oisix.oisystemzs.ejb.ShukkoLocal;
import com.oisix.oisystemzs.ejb.ShukkoLocalHome;
import com.oisix.oisystemzs.ejb.NyuukoLocal;
import com.oisix.oisystemzs.ejb.NyuukoLocalHome;
import com.oisix.oisystemzs.ejb.ShouhinLocal;
import com.oisix.oisystemzs.ejb.ShouhinLocalHome;
import com.oisix.oisystemzs.ejb.UserLocal;
import com.oisix.oisystemzs.ejb.SoukoData;
import com.oisix.oisystemzs.ejb.exception.HaraidashiException;
import com.oisix.oisystemzs.ejb.exception.UkeireException;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Collection;
import java.util.Iterator;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.naming.NamingException;
import javax.ejb.FinderException;
import javax.ejb.CreateException;

public class DoshoumihenkouEvent extends TransactionEvent {

    private int user_id;
    private String souko_id;

    private HashMap henkou = new HashMap();

    public HashMap getHenkou() { return henkou; }

    public void init(HttpServletRequest request) {
        // ���[�U�̎擾
        UserLocal user = (UserLocal)session.getAttribute("USER");
        user_id = user.getUser_id();
        SoukoData souko = (SoukoData)session.getAttribute("SOUKO");
        souko_id = souko.getSouko_id();

        int index = 0;
        boolean finish = false;
        while (!finish) {
            index++;
            String shouhin_id = getInput("#shouhin_id_" + index);
            if (shouhin_id == null) {
                finish = true;
                break;
            }
            String suffix = shouhin_id + "_" + index;
            String year = getInput("#year_" + suffix);
            String month = getInput("#month_" + suffix);
            String date = getInput("#date_" + suffix);
            String orgyear = getInput("#orgyear_" + suffix);
            String orgmonth = getInput("#orgmonth_" + suffix);
            String orgdatestr = getInput("#orgdate_" + suffix);
            if (year.equals(orgyear) && month.equals(orgmonth) &&
              date.equals(orgdatestr)) {
                continue;
            }
            java.util.Date orgdate = DateUtil.getDate(
              orgyear, orgmonth, orgdatestr);
            java.util.Date newdate = DateUtil.getDate(year, month, date);
            String shoumistr = getInput("#shoumi_" + suffix);
            Integer shoumi = null;
            try {
                shoumi = Integer.valueOf(shoumistr);
            } catch (NumberFormatException nfe) {
                result = RC_INPUTERROR;
                errorlist.add("�ܖ������������s���ł�");
                return;
            }
            String shukkastr = getInput("#shukka_" + suffix);
            Integer shukka = null;
            try {
                shukka = Integer.valueOf(shukkastr);
            } catch (NumberFormatException nfe) {
                result = RC_INPUTERROR;
                errorlist.add("�o�Ɋ����������s���ł�");
                return;
            }
            String suuryoustr = getInput("#suuryou_" + suffix);
            Float suuryou = null;
            try {
                suuryou = Float.valueOf(suuryoustr);
            } catch (NumberFormatException nfe) {
                result = RC_INPUTERROR;
                errorlist.add("���ʂ��s���ł�");
                return;
            }
            HashMap henkouline = new HashMap();
            henkouline.put("orgdate", orgdate);
            henkouline.put("newdate", newdate);
            henkouline.put("shoumi", shoumi);
            henkouline.put("shukka", shukka);
            henkouline.put("suuryou", suuryou);
            henkou.put(shouhin_id, henkouline);
        }
    }

    public void handleEvent(HashMap attr) {
        if (result == RC_INPUTERROR) { return; }
        java.util.Date now = DateUtil.getDate();
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        now = cal.getTime();
        try {
            ShukkoLocalHome sklh = (ShukkoLocalHome)
              ServiceLocator.getLocalHome("java:comp/env/ejb/ShukkoLocal");
            NyuukoLocalHome nklh = (NyuukoLocalHome)
              ServiceLocator.getLocalHome("java:comp/env/ejb/NyuukoLocal");
            ShouhinLocalHome shlh = (ShouhinLocalHome)
              ServiceLocator.getLocalHome("java:comp/env/ejb/ShouhinLocal");
            Iterator henkouiter = henkou.keySet().iterator();
            while (henkouiter.hasNext()) {
                String shouhin_id = (String)henkouiter.next();
                HashMap henkouline = (HashMap)henkou.get(shouhin_id);
                java.util.Date orgdate =
                  (java.util.Date)henkouline.get("orgdate");
                java.util.Date newdate =
                  (java.util.Date)henkouline.get("newdate");
                int shoumi = ((Integer)henkouline.get("shoumi")).intValue();
                int shukka = ((Integer)henkouline.get("shukka")).intValue();
                cal.setTime(newdate);
                cal.add(Calendar.DATE, -1 * (shoumi - shukka));
                java.util.Date newshukka = cal.getTime();
                float suuryou =
                  ((Float)henkouline.get("suuryou")).floatValue();
                ShouhinLocal shouhin = shlh.findByPrimaryKey(shouhin_id);
                String tani = TaniMap.getTani(shouhin.getTani()).getTani();
                henkouline.put("shouhin", shouhin.getShouhin());
                //��ɏo�ɂ���ƍ݌ɂ�0�ɂȂ�ꍇ�ɋ��z��0�ɂȂ��Ă��܂�
                //�̂œ��ɂ����ɍs���B
                NyuukoLocal newnyuuko = nklh.create(
                  now,
                  souko_id,
                  shouhin_id,
                  suuryou,
                  tani,
                  0, // �P��
                  suuryou,
                  tani,
                  0, // �P��
                  newdate,
                  newshukka,
                  3, //�U�֓���
                  null,  //�d����R�[�h
                  null,  //�[�i��R�[�h
                  Names.NONE_ID, // ���ח\��ԍ�
                  Names.OFF, // �����t���O
                  Names.NONE_ID, // �������ɔԍ�
                  user_id);
                ShukkoLocal newshukko = sklh.create(
                  17, //�U�֏o��
                  null,  //�[�i��
                  souko_id,
                  shouhin_id,
                  Names.DUMMY_LOCATION_ID,
                  now, //�o�ɓ�
                  orgdate,
                  suuryou,
                  tani,
                  1,  //�P���敪 �W���P��=�X�V���Ȃ�
                  0,  //�P��
                  0,  //���z
                  Names.NONE_ID,  //�o�ח\�薾�הԍ�
                  Names.OFF,  //�����t���O
                  Names.NONE_ID,  //�����o�ɔԍ�
                  user_id,
                  0);   //�W������
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
        } catch (HaraidashiException he) {
            Debug.println(he);
            result = RC_INPUTERROR;
            errorlist.add("�o�ɏ������ɃG���[���������܂����B");
            setRollbackOnly();
            return;
        } catch (UkeireException he) {
            Debug.println(he);
            result = RC_INPUTERROR;
            errorlist.add("���ɏ������ɃG���[���������܂����B");
            setRollbackOnly();
            return;
        }
    }

    public void postHandle(HttpServletRequest request) {
    }

}
