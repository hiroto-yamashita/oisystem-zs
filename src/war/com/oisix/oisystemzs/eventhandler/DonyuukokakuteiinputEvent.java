package com.oisix.oisystemzs.eventhandler;

import com.oisix.oisystemfr.Debug;
import com.oisix.oisystemfr.DateUtil;
import com.oisix.oisystemfr.TransactionEvent;
import com.oisix.oisystemfr.ejb.ServiceLocator;
import com.oisix.oisystemzs.ejb.NyuukoLocal;
import com.oisix.oisystemzs.ejb.NyuukoLocalHome;
import com.oisix.oisystemzs.ejb.NyuukayoteimeisaiPK;
import com.oisix.oisystemzs.ejb.NyuukayoteimeisaiLocal;
import com.oisix.oisystemzs.ejb.NyuukayoteimeisaiLocalHome;
import com.oisix.oisystemzs.ejb.NyuukayoteimeisaiData;
import com.oisix.oisystemzs.ejb.UserLocal;
import com.oisix.oisystemzs.ejb.SoukoData;
import com.oisix.oisystemzs.ejb.ShouhinLocal;
import com.oisix.oisystemzs.ejb.HacchuuLocal;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Collection;
import java.util.Iterator;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.naming.NamingException;
import javax.ejb.FinderException;
import javax.ejb.CreateException;
import com.oisix.oisystemzs.ejb.exception.UkeireException;
import com.oisix.oisystemzs.Names;

public class DonyuukokakuteiinputEvent extends TransactionEvent {

    private LinkedList nyuukokakuteiList = new LinkedList();
    private LinkedList resultNyuukokakuteiList = new LinkedList();
    private java.util.Date nyuuko_date;

    private int user_id;
    private String souko_id;
    private NyuukayoteimeisaiLocal nyuukayotei;
    
    public void init(HttpServletRequest request) {
        // ���ɓ��̎擾
        int nyuukoyear = 0;
        int nyuukomonth = 0;
        int nyuukoday = 0;
        String nyuukoyearstr = getInput("#nyuukoyear");
        String nyuukomonthstr = getInput("#nyuukomonth");
        String nyuukodatestr = getInput("#nyuukodate");
        try {
            nyuukoyear = Integer.parseInt(nyuukoyearstr);
            nyuukomonth = Integer.parseInt(nyuukomonthstr);
            nyuukoday = Integer.parseInt(nyuukodatestr);
            Calendar cal = Calendar.getInstance();
            cal.clear();
            cal.set(nyuukoyear, nyuukomonth - 1, nyuukoday);
            nyuuko_date = cal.getTime();
        } catch (NumberFormatException nfe) {
            Debug.println(nfe);
            errorlist.add("���ɓ����s���ł��B");
            result = RC_INPUTERROR;
        }
        //���Ɋm�萔�̎擾
        int kakuteisuu = 0;
        String strkakuteisuu = getInput("#kakuteisuu");
        try{
            kakuteisuu = Integer.parseInt(strkakuteisuu);
        }catch (NumberFormatException nfe) {
            Debug.println(nfe);
            errorlist.add("���Ɋm�萔���s���ł��B");
            result = RC_INPUTERROR;
        }
        for (int i = 0; i < kakuteisuu; i++) {
            HashMap nyuukoMap = new HashMap();
            String strInd = String.valueOf(i);
            //���ח\��R�[�h�̎擾
            String strnyuukayotei_id = getInput("#id" + strInd);
            try{
                Integer nyuukayotei_id = new Integer(strnyuukayotei_id);
                nyuukoMap.put("nyuukayotei_id",nyuukayotei_id);
            }catch (NumberFormatException nfe) {
                Debug.println(nfe);
                errorlist.add("���ח\��R�[�h���s���ł��B");
                result = RC_INPUTERROR;
            }
            // �ܖ������̎擾
            int shoumiyear = 0;
            int shoumimonth = 0;
            int shoumiday = 0;
            java.util.Date shoumikigen = null;
            String shoumiyearstr = getInput("#shoumiyear" + strInd);
            if(shoumiyearstr.equals("0")){
                nyuukoMap.put("shoumikigen", shoumikigen);
            }else{
                String shoumimonthstr =
                getInput("#shoumimonth" + strInd);
                String shoumidatestr =
                getInput("#shoumidate" + strInd);
                try {
                    shoumiyear = Integer.parseInt(shoumiyearstr);
                    shoumimonth = Integer.parseInt(shoumimonthstr);
                    shoumiday = Integer.parseInt(shoumidatestr);
                    Calendar shoumical = Calendar.getInstance();
                    shoumical.clear();
                    shoumical.set(shoumiyear, shoumimonth - 1, shoumiday);
                    shoumikigen = shoumical.getTime();
                    nyuukoMap.put("shoumikigen", shoumikigen);
                } catch (NumberFormatException nfe) {
                    Debug.println(nfe);
                    errorlist.add("�ܖ��������s���ł��B");
                    result = RC_INPUTERROR;
                    continue;
                }
            }
            
            // ���ɐ��ʂ̎擾
            String nyuukosuuryoustr = getInput("#nyuukosuuryou" + strInd);
            String shouhin = getInput("#shouhin" + strInd);
            Float nyuukosuuryou = null;
            try {
                nyuukosuuryou = new Float(nyuukosuuryoustr);
                Float zero = new Float(0);
                if(nyuukosuuryou.compareTo(zero)==0){
                    errorlist.add("���ɐ��ʂ��O�ł��B���i��"+shouhin);
                    result = RC_INPUTERROR;
                    continue;
                }else{
                    nyuukoMap.put("nyuukosuuryou", nyuukosuuryou);
                }
            } catch (NullPointerException npe) {
                errorlist.add("���ɐ��ʂ���͂��Ă��������B");
                result = RC_INPUTERROR;
                continue;
            } catch (NumberFormatException nfe) {
                Debug.println(nfe);
                errorlist.add("���ɐ��ʂ��s���ł��B");
                result = RC_INPUTERROR;
                continue;
            } 
            // ���ɒP�ʂ̎擾
            String nyuukotani = getInput("#nyuukotani" + strInd);
            if (nyuukotani == null || nyuukotani.equals("")) {
                errorlist.add("���ɒP�ʂ����I���ł��B");
                result = RC_INPUTERROR;
                continue;
            } else {
                nyuukoMap.put("nyuukotani", nyuukotani);
            }
            
            // �d�����ʂ̎擾
            String shiiresuuryoustr = getInput("#shiiresuuryou" + strInd);
            Float shiiresuuryou = null;
            try {
                shiiresuuryou = new Float(shiiresuuryoustr);
                nyuukoMap.put("shiiresuuryou", shiiresuuryou);
            } catch (NullPointerException npe) {
                errorlist.add("�d�����ʂ���͂��Ă��������B");
                result = RC_INPUTERROR;
                continue;
            } catch (NumberFormatException nfe) {
                Debug.println(nfe);
                errorlist.add("�d�����ʂ��s���ł��B");
                result = RC_INPUTERROR;
                continue;
            }
            // �d���P�ʂ̎擾
            String shiiretani = getInput("#shiiretani" + strInd);
            if (shiiretani == null || shiiretani.equals("")) {
                errorlist.add("�d���P�ʂ����I���ł��B");
                result = RC_INPUTERROR;
                continue;
            } else {
                nyuukoMap.put("shiiretani", shiiretani);
            }
            // �d���P���̎擾
            String tankastr = getInput("#shiiretanka" + strInd);
            Float shiiretanka = null;
            try {
                shiiretanka = new Float(tankastr);
                nyuukoMap.put("shiiretanka", shiiretanka);
            } catch (NullPointerException npe) {
                errorlist.add("�P������͂��Ă��������B");
                result = RC_INPUTERROR;
                continue;
            } catch (NumberFormatException nfe) {
                Debug.println(nfe);
                errorlist.add("�P�����s���ł��B");
                result = RC_INPUTERROR;
                continue;
            }
            // ���ɋ敪�̎擾
            String nyuukokubunstr = getInput("#nyuukokubun"+strInd);
            Integer nyuukokubun = null;
            try {
                nyuukokubun = new Integer(nyuukokubunstr);
                nyuukoMap.put("nyuukokubun", nyuukokubun);
            } catch (NullPointerException npe) {
                errorlist.add("���ɋ敪��I�����Ă��������B");
                result = RC_INPUTERROR;
                continue;
            } catch (NumberFormatException nfe) {
                Debug.println(nfe);
                errorlist.add("���ɋ敪���s���ł��B");
                result = RC_INPUTERROR;
                continue;
            }
            //���ɒP���̌v�Z
            Float nyuukotanka = null;
            try{
                float flshiiretanka = shiiretanka.floatValue();
                float flshiiresuuryou = shiiresuuryou.floatValue();
                float flnyuukosuuryou = nyuukosuuryou.floatValue();
                float flnyuukotanka =
                  flshiiresuuryou * flshiiretanka / flnyuukosuuryou ;
                nyuukotanka = new Float(flnyuukotanka);
                nyuukoMap.put("nyuukotanka",nyuukotanka);
            }catch (NullPointerException npe) {
                errorlist.add("�d���P���A�d�����ʁA���ɐ��ʂ�I�����Ă��������B");
                result = RC_INPUTERROR;
                continue;
            }catch (NumberFormatException nfe) {
                Debug.println(nfe);
                errorlist.add("�d���P���A�d�����ʁA���ɐ��ʂ��s���ł��B");
                result = RC_INPUTERROR;
                continue;
            }catch(Exception e){
                errorlist.add("���ɒP���v�Z���s���ł��B");
                result = RC_INPUTERROR;
                continue;
            }
            nyuukokakuteiList.add(nyuukoMap);
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
        try {
            NyuukayoteimeisaiLocalHome nkmlh = (NyuukayoteimeisaiLocalHome)
              ServiceLocator.getLocalHome("java:comp/env/ejb/NyuukayoteimeisaiLocal");
            Iterator iter = nyuukokakuteiList.iterator();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            while (iter.hasNext()) {
                HashMap nyuukayoteiMap = (HashMap)iter.next();
                int nyuukayotei_id = ((Integer)nyuukayoteiMap.get("nyuukayotei_id")).intValue();
                NyuukayoteimeisaiPK pk = new NyuukayoteimeisaiPK(nyuukayotei_id);
                nyuukayotei = nkmlh.findByPrimaryKey(pk);
                ShouhinLocal sl = nyuukayotei.getShouhin();
                HacchuuLocal hl = nyuukayotei.getHacchuu();
                NyuukoLocalHome nklh = (NyuukoLocalHome)
                  ServiceLocator.getLocalHome("java:comp/env/ejb/NyuukoLocal");

                java.util.Date shoumikigen = (java.util.Date)
                  nyuukayoteiMap.get("shoumikigen");
                java.util.Date shukkakigen = null;
                if (shoumikigen != null) {
                    Calendar cal = DateUtil.getCalendar();
                    cal.setTime(shoumikigen);
                    cal.add(Calendar.DATE,
                      sl.getShukkakigennissuu() - sl.getShoumikigennissuu());
                    shukkakigen = cal.getTime();
                }

                NyuukoLocal newnyuuko = nklh.create(
                  nyuuko_date,
                  souko_id,
                  nyuukayotei.getShouhin_id(),
                  ((Float)nyuukayoteiMap.get("shiiresuuryou")).floatValue(),
                  (String)nyuukayoteiMap.get("shiiretani"),
                  ((Float)nyuukayoteiMap.get("shiiretanka")).floatValue(),
                  ((Float)nyuukayoteiMap.get("nyuukosuuryou")).floatValue(),
                  (String)nyuukayoteiMap.get("nyuukotani"),
                  ((Float)nyuukayoteiMap.get("nyuukotanka")).floatValue(),
                  //(java.util.Date)nyuukayoteiMap.get("shoumikigen"),
                  //(java.util.Date)nyuukayoteiMap.get("shukkakigen"),
                  shoumikigen,
                  shukkakigen,
                  ((Integer)nyuukayoteiMap.get("nyuukokubun")).intValue(),
                  //sl.getShiiresaki_id(),
                  hl.getShiiresaki_id(),
                  hl.getNouhinsaki_id(),
                  nyuukayotei.getNyuukayotei_id(),
                  Names.OFF,
                  Names.NONE_ID,
                  user_id
                );
                // ���ʕ\���p�̃f�[�^�쐬
                HashMap item = new HashMap();
                item.put("nyuuko_id", new Integer(newnyuuko.getNyuuko_id()));
                item.put("nyuuko_bg", newnyuuko.getNyuuko_bg());
                item.put("nyuuko_date",newnyuuko.getNyuuko_date());
                item.put("shouhin_id", newnyuuko.getShouhin_id());
                item.put("shouhinmei", newnyuuko.getShouhin().getShouhin());
                item.put("nyuukosuuryou", new Float(newnyuuko.getNyuukosuuryou()));
                if (shoumikigen != null) {
                    item.put("shoumikigen", sdf.format(shoumikigen));
                }
                if (shukkakigen != null) {
                    item.put("shukkakigen", sdf.format(shukkakigen));
                }
                resultNyuukokakuteiList.add(item);
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
        } catch (UkeireException he) {
            Debug.println(he);
            result = RC_INPUTERROR;
            errorlist.add("�V�X�e���G���[�EUkeireException");
            setRollbackOnly();
            return;
        }
    }
    public void postHandle(HttpServletRequest request) {
        if (result == RC_INPUTERROR) { return; }
        HttpSession session = request.getSession();
        session.setAttribute("INPUTRESULTNYUUKOKAKUTEILIST", resultNyuukokakuteiList);
        session.setAttribute("INPUTRESULTNYUUKODATE", nyuuko_date);
    }
}
