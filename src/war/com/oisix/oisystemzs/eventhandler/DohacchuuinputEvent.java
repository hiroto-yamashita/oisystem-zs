package com.oisix.oisystemzs.eventhandler;

import com.oisix.oisystemfr.Debug;
import com.oisix.oisystemfr.TransactionEvent;
import com.oisix.oisystemfr.ejb.ServiceLocator;
import com.oisix.oisystemzs.Names;
import com.oisix.oisystemzs.ejb.HacchuuLocal;
import com.oisix.oisystemzs.ejb.HacchuuLocalHome;
import com.oisix.oisystemzs.ejb.NyuukayoteimeisaiLocal;
import com.oisix.oisystemzs.ejb.NyuukayoteimeisaiLocalHome;
import com.oisix.oisystemzs.ejb.ShouhinLocal;
import com.oisix.oisystemzs.ejb.ShouhinLocalHome;
import com.oisix.oisystemzs.ejb.SoukoData;
import com.oisix.oisystemzs.ejb.UserLocal;
import com.oisix.oisystemzs.objectmap.TaniMap;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.naming.NamingException;
import javax.ejb.CreateException;
import javax.ejb.FinderException;

public class DohacchuuinputEvent extends TransactionEvent {

    private java.util.Date hacchuu_date;
    private String shiiresaki_id;
    private int hacchuusho;
    private String nouhinsaki_id;
    private int user_id;
    private boolean inputshiiresaki = false;
    private String souko_id;

    private LinkedList meisaiList = new LinkedList();

    private String hacchuu_bg;
    private int hacchuu_id;

    public String getHacchuu_bg() { return hacchuu_bg; }
    public int getHacchuu_id() { return hacchuu_id; }

    public void init(HttpServletRequest request) {
        // �������̎擾
        int year = 0;
        int month = 0;
        int day = 0;
        String yearstr = getInput("#year");
        String monthstr = getInput("#month");
        String datestr = getInput("#date");
        try {
            year = Integer.parseInt(yearstr);
            month = Integer.parseInt(monthstr);
            day = Integer.parseInt(datestr);
            Calendar cal = Calendar.getInstance();
            cal.clear();
            cal.set(year, month - 1, day);
            hacchuu_date = cal.getTime();
        } catch (NumberFormatException nfe) {
            Debug.println(nfe);
            errorlist.add("���������s���ł��B");
            result = RC_INPUTERROR;
        }

        // �d����R�[�h�̎擾
        shiiresaki_id = getInput("#shiiresaki_id");
        if ((shiiresaki_id != null) && (shiiresaki_id.equals(""))) {
            shiiresaki_id = null;
        }
        if (shiiresaki_id != null) {
            inputshiiresaki = true;
        }
        //if (shiiresaki_id == null || shiiresaki_id.equals("")) {
        //    errorlist.add("�d����R�[�h�������͂ł��B");
        //    result = RC_INPUTERROR;
        //}

        // �[�i��R�[�h�̎擾
        nouhinsaki_id = getInput("#nouhinsaki_id");
        if (nouhinsaki_id == null || nouhinsaki_id.equals("")) {
            errorlist.add("�[�i��R�[�h�������͂ł��B");
            result = RC_INPUTERROR;
        }

        // �������t�H�[�}�b�g�̎擾
        String hacchuushostr = getInput("#hacchuusho");
        if (hacchuushostr == null || hacchuushostr.equals("")) {
            errorlist.add("�������t�H�[�}�b�g�����I���ł��B");
            result = RC_INPUTERROR;
        } else {
            try {
                hacchuusho = Integer.parseInt(hacchuushostr);
            } catch (NumberFormatException nfe) {
                Debug.println(nfe);
                errorlist.add("�������t�H�[�}�b�g���s���ł��B");
                result = RC_INPUTERROR;
            }
        }

        int ind = 0;
        while (getInput("#hacchuukubun" + ind) != null) {
            String shouhin_id = getInput("#shouhin_id" + ind);
            // ���iID���擾�ł�����̂ݏ���
            if (shouhin_id != null && !shouhin_id.equals("")) {
                HashMap meisaiMap = new HashMap();
                meisaiMap.put("shouhin_id", shouhin_id);

                String suuryoustr = getInput("#suuryou" + ind);
                Float suuryou = null;
                try {
                    if (suuryoustr == null) { suuryoustr = "0"; }
                    suuryou = new Float(suuryoustr);
                } catch (NumberFormatException nfe) {
                    Debug.println(nfe);
                    errorlist.add(shouhin_id + "�̐��ʂ��s���ł��B");
                    result = RC_INPUTERROR;
                    ind++;
                    continue;
                }
                meisaiMap.put("suuryou", suuryou);

                String hacchuutanistr = getInput("#hacchuutani" + ind);
                String hacchuutani = null;
                if ((hacchuutanistr != null) && (!hacchuutanistr.equals(""))) {
                    try {
                        int tani_id = Integer.parseInt(hacchuutanistr);
                        hacchuutani = TaniMap.getTani(tani_id).getTani();
                    } catch (NumberFormatException nfe) {
                        Debug.println(nfe);
                        errorlist.add(shouhin_id + "�̒P�ʂ��s���ł��B");
                        result = RC_INPUTERROR;
                        ind++;
                        continue;
                    }
                }
                meisaiMap.put("hacchuutani", hacchuutani);

                String tankastr = getInput("#tanka" + ind);
                Float tanka = null;
                try {
                    tanka = new Float(tankastr);
                } catch (NumberFormatException nfe) {
                    Debug.println(nfe);
                    errorlist.add(shouhin_id + "�̒P�����s���ł��B");
                    result = RC_INPUTERROR;
                    ind++;
                    continue;
                } catch (NullPointerException npe) {
                    Debug.println(npe);
                    errorlist.add(shouhin_id + "�̒P�����s���ł��B");
                    result = RC_INPUTERROR;
                    ind++;
                    continue;
                }
                meisaiMap.put("tanka", tanka);

                String nyuukasuuryoustr = getInput("#nyuukasuuryou" + ind);
                Float nyuukasuuryou = null;
                if ((nyuukasuuryoustr != null) &&
                  (!nyuukasuuryoustr.equals(""))) {
                    try {
                        nyuukasuuryou = new Float(nyuukasuuryoustr);
                    } catch (NumberFormatException nfe) {
                        Debug.println(nfe);
                        errorlist.add(shouhin_id + "�̓��א��ʂ��s���ł��B");
                        result = RC_INPUTERROR;
                        ind++;
                        continue;
                    }
                }
                meisaiMap.put("nyuukasuuryou", nyuukasuuryou);

                String nyuukatanistr = getInput("#nyuukatani" + ind);
                String nyuukatani = null;
                if ((nyuukatanistr != null) && (!nyuukatanistr.equals(""))) {
                    try {
                        int tani_id = Integer.parseInt(nyuukatanistr);
                        nyuukatani = TaniMap.getTani(tani_id).getTani();
                    } catch (NumberFormatException nfe) {
                        Debug.println(nfe);
                        errorlist.add(shouhin_id + "�̓��גP�ʂ��s���ł��B");
                        result = RC_INPUTERROR;
                        ind++;
                        continue;
                    }
                }
                meisaiMap.put("nyuukatani", nyuukatani);

                yearstr = getInput("#year" + ind);
                monthstr = getInput("#month" + ind);
                datestr = getInput("#date" + ind);
                java.util.Date nouhin_date = null;
                try {
                    year = Integer.parseInt(yearstr);
                    month = Integer.parseInt(monthstr);
                    day = Integer.parseInt(datestr);
                    Calendar cal = Calendar.getInstance();
                    cal.clear();
                    cal.set(year, month - 1, day);
                    nouhin_date = cal.getTime();
                } catch (NumberFormatException nfe) {
                    Debug.println(nfe);
                    errorlist.add(shouhin_id + "�̔[�i�����s���ł��B");
                    result = RC_INPUTERROR;
                    ind++;
                    continue;
                }
                meisaiMap.put("nouhin_date", nouhin_date);

                String touchakustr = getInput("#touchaku" + ind);
                Integer touchaku;
                try {
                    touchaku = new Integer(touchakustr);
                } catch (NumberFormatException nfe) {
                    Debug.println(nfe);
                    errorlist.add(shouhin_id + "�̓������Ԃ��s���ł��B");
                    result = RC_INPUTERROR;
                    ind++;
                    continue;
                }
                meisaiMap.put("touchaku", touchaku);

                String hacchuukubunstr = getInput("#hacchuukubun" + ind);
                Integer hacchuukubun;
                try {
                    hacchuukubun = new Integer(hacchuukubunstr);
                } catch (NumberFormatException nfe) {
                    Debug.println(nfe);
                    errorlist.add(shouhin_id + "�̔����敪���s���ł��B");
                    result = RC_INPUTERROR;
                    ind++;
                    continue;
                }
                meisaiMap.put("hacchuukubun", hacchuukubun);

                meisaiList.add(meisaiMap);
            }
            ind++;
        }

        if (meisaiList.size() == 0) {
            errorlist.add("���i���P���I������Ă��܂���B");
            result = RC_INPUTERROR;
        }

        UserLocal user = (UserLocal)session.getAttribute("USER");
        user_id = user.getUser_id();
        SoukoData souko = (SoukoData)session.getAttribute("SOUKO");
        souko_id = souko.getSouko_id();
    }

    public void handleEvent(HashMap attr) {
        if (result == RC_INPUTERROR) { return; }
        java.util.Date now = new java.util.Date();
        try {
            HacchuuLocalHome hhome = (HacchuuLocalHome)
              ServiceLocator.getLocalHome("java:comp/env/ejb/HacchuuLocal");
            HacchuuLocal hacchuu = hhome.create(
              shiiresaki_id,
              nouhinsaki_id,
              hacchuu_date,
              hacchuusho,
              null,
              user_id
            );
            hacchuu_bg = hacchuu.getHacchuu_bg();
            hacchuu_id = hacchuu.getHacchuu_id();
            ShouhinLocalHome shome = (ShouhinLocalHome)
              ServiceLocator.getLocalHome("java:comp/env/ejb/ShouhinLocal");
            NyuukayoteimeisaiLocalHome nyhome = (NyuukayoteimeisaiLocalHome)
              ServiceLocator.getLocalHome(
              "java:comp/env/ejb/NyuukayoteimeisaiLocal");

            Iterator iter = meisaiList.iterator();
            while (iter.hasNext()) {
                HashMap meisaiMap = (HashMap)iter.next();
                String shouhin_id = (String)meisaiMap.get("shouhin_id");
                ShouhinLocal shouhin = shome.findByPrimaryKey(shouhin_id);
                if (!inputshiiresaki) {
                    if (shiiresaki_id == null) {
                        shiiresaki_id = shouhin.getShiiresaki_id();
                    } else if (!shiiresaki_id.equals(
                      shouhin.getShiiresaki_id())) {
                        result = RC_INPUTERROR;
                        errorlist.add("���i�̎d���悪��v���܂���B�����d����̏��i�����ɂ��邩�A�d����R�[�h���w�肵�Ă��������B");
                        setRollbackOnly();
                        return;
                    }
                }
                Float fSuuryou = (Float)meisaiMap.get("suuryou");
                float suuryou = fSuuryou.floatValue();
                String hacchuutani = (String)meisaiMap.get("hacchuutani");
                if (hacchuutani == null) {
                    hacchuutani = TaniMap.getTani(
                      shouhin.getHacchuutani()).getTani();
                }
                Float fTanka = (Float)meisaiMap.get("tanka");
                float tanka = fTanka.floatValue();
                Float fNyuukasuuryou = (Float)meisaiMap.get("nyuukasuuryou");
                float nyuukasuuryou = 0;
                if (fNyuukasuuryou == null) {
                    //���א��ʖ����͂̏ꍇ �������ʁ~���萔
                    //nyuukasuuryou = suuryou * shouhin.getHacchuutanisuu();
                    nyuukasuuryou = suuryou * shouhin.getIrisuu();
                } else {
                    nyuukasuuryou = fNyuukasuuryou.floatValue();
                }
                String nyuukatani = (String)meisaiMap.get("nyuukatani");
                if (nyuukatani == null) {
                    nyuukatani = TaniMap.getTani(shouhin.getTani()).getTani();
                }
                java.util.Date nouhin_date = (java.util.Date)
                  meisaiMap.get("nouhin_date");
                Integer iTouchaku = (Integer)meisaiMap.get("touchaku");
                int touchaku = iTouchaku.intValue();
                Integer iHacchuukubun = (Integer)meisaiMap.get("hacchuukubun");
                int hacchuukubun = iHacchuukubun.intValue();
                java.util.Date shoumikigen = null;
                java.util.Date shukkakigen = null;
                if (shouhin.getShoumikigen_flg() == Names.ON) {
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(nouhin_date);
                    //cal.setTime(hacchuu_date);
                    cal.add(Calendar.DATE, shouhin.getShoumikigennissuu());
                    shoumikigen = cal.getTime();
                    cal.setTime(nouhin_date);
                    //cal.setTime(hacchuu_date);
                    cal.add(Calendar.DATE, shouhin.getShukkakigennissuu());
                    shukkakigen = cal.getTime();
                }

                NyuukayoteimeisaiLocal nyuukayoteimeisai = nyhome.create(
                  hacchuu_bg,
                  //nouhinsaki_id,
                  souko_id,
                  shouhin_id,
                  suuryou,
                  hacchuutani,
                  tanka,
                  nyuukasuuryou,
                  nyuukatani,
                  nouhin_date,
                  touchaku,
                  shouhin.getOndotai(),
                  hacchuukubun,
                  shoumikigen,
                  shukkakigen,
                  1, //���ɋ敪=�d��
                  null, //���l
                  0.0f, //�����א�
                  1, //���׏�=������
                  user_id
                );
            }
            if (!inputshiiresaki) {
                hacchuu.setShiiresaki_id(shiiresaki_id);
            }
        } catch (NamingException ne) {
            Debug.println(ne);
            result = RC_INPUTERROR;
            errorlist.add("�V�X�e���G���[�ENamingException");
            setRollbackOnly();
            return;
        } catch (CreateException ce) {
            Debug.println(ce);
            result = RC_INPUTERROR;
            errorlist.add("�V�X�e���G���[�ECreateException");
            setRollbackOnly();
            return;
        } catch (FinderException fe) {
            Debug.println(fe);
            result = RC_INPUTERROR;
            errorlist.add("���i�R�[�h���Ԉ���Ă��܂��B");
            setRollbackOnly();
            return;
        }
    }

    public void postHandle(HttpServletRequest request) {
    }
}
