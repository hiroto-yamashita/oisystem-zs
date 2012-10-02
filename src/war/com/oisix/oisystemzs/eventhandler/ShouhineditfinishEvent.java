package com.oisix.oisystemzs.eventhandler;

import com.oisix.oisystemfr.Debug;
import com.oisix.oisystemfr.TransactionEvent;
import com.oisix.oisystemfr.ejb.ServiceLocator;
import com.oisix.oisystemzs.Names;
import com.oisix.oisystemzs.ejb.ShouhinLocal;
import com.oisix.oisystemzs.ejb.ShouhinLocalHome;
import com.oisix.oisystemzs.ejb.UserLocal;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.naming.NamingException;
import javax.ejb.FinderException;
import javax.ejb.EJBException;

public class ShouhineditfinishEvent extends TransactionEvent {

    private java.lang.String shouhin_id;
    private java.lang.String shouhin;
    private java.lang.String shouhinfurigana;
    private java.lang.String shiiresaki_id;
    private java.lang.String hacchuushouhin1;
    private java.lang.String hacchuushouhin2;
    private java.lang.String hacchuushouhin3;
    private java.lang.String kikaku;
    private int tani;
    private java.lang.String hacchuukikaku;
    private int hacchuuten;
    private int hacchuutani;
    //private int irisuu;
    private float irisuu;
    private int hacchuutanisuu;
    private int saiteihacchuusuu;
    private float tanka;
    private float hyoujuntanka;
    private int shiireleadtime;
    private int ondotai;
    private int shoumikigen_flg;
    private int shoumikigennissuu;
    private int shukkakigennissuu;
    private int kobetsuhacchuu_flg;
    private int youchuuihin_flg;
    private int youraberu_flg;
    private java.lang.String daibunrui;
    private java.lang.String pcode;
    private int zaikohyoukahouhou;
    private java.lang.String jancode;
    private java.lang.String kataban;
    private java.lang.String location_id1;
    private java.lang.String location_id2;
    private java.lang.String location_id3;
    private int shuubai_flg;
    private int user_id;
    private long updated;
    private float hyoujunbaika;
    private String nisugata;
    private String hacchuucomment;
    private int hanbaikubun;
    private String shubetsu;
    private int mochikoshi_flg;
    private int shinyanouhin_flg;

    public void init(HttpServletRequest request) {
        shouhin_id = request.getParameter("shouhin_id");
        if ((shouhin_id == null) || (shouhin_id.length() ==0)){
            errorlist.add("���i�R�[�h�����͂���Ă��܂���");
            result = RC_INPUTERROR;
        }
        shouhin = request.getParameter("shouhin");
        if ((shouhin == null) || (shouhin.length() ==0)){
            errorlist.add("���i�������͂���Ă��܂���");
            result = RC_INPUTERROR;
        }
        shouhinfurigana = request.getParameter("shouhinfurigana");
        if ((shouhinfurigana == null) || (shouhinfurigana.length() ==0)){
            errorlist.add("�t���K�i�����͂���Ă��܂���");
            result = RC_INPUTERROR;
        }
        shiiresaki_id = request.getParameter("#shiiresaki_id_se");
        if ((shiiresaki_id == null) || (shiiresaki_id.length() ==0)){
            errorlist.add("�d����R�[�h�����͂���Ă��܂���");
            result = RC_INPUTERROR;
        }
        hacchuushouhin1 = request.getParameter("hacchuushouhin1");
        if ((hacchuushouhin1 == null) || (hacchuushouhin1.length() ==0)){
            errorlist.add("�������i��1�����͂���Ă��܂���");
            result = RC_INPUTERROR;
        }
        hacchuushouhin2 = request.getParameter("hacchuushouhin2");
        hacchuushouhin3 = request.getParameter("hacchuushouhin3");
        kikaku = request.getParameter("kikaku");
        String tanistr = request.getParameter("tani");
        if ((tanistr == null) || (tanistr.length() == 0)) {
            errorlist.add("�P�ʂ�I�����Ă�������");
            result = RC_INPUTERROR;
        } else {
            try {
                tani = Integer.parseInt(tanistr);
            } catch (NumberFormatException nfe) {
                Debug.println(nfe);
                errorlist.add("�P�ʂ��s���ł�");
                result = RC_INPUTERROR;
            }
        }
        hacchuukikaku = request.getParameter("hacchuukikaku");
        if ((hacchuukikaku == null) || (hacchuukikaku.length() ==0)){
            errorlist.add("�����K�i�����͂���Ă��܂���");
            result = RC_INPUTERROR;
        }
        String hacchuutenstr = request.getParameter("hacchuuten");
        if ((hacchuutenstr == null) || (hacchuutenstr.length() == 0)) {
            errorlist.add("�����_����͂��Ă�������");
            result = RC_INPUTERROR;
        } else {
            try {
                hacchuuten = Integer.parseInt(hacchuutenstr);
            } catch (NumberFormatException nfe) {
                Debug.println(nfe);
                errorlist.add("�����_���s���ł�");
                result = RC_INPUTERROR;
            }
        }
        String hacchuutanistr = request.getParameter("hacchuutani");
        if ((hacchuutanistr == null) || (hacchuutanistr.length() == 0)) {
            errorlist.add("�����P�ʂ�I�����Ă�������");
            result = RC_INPUTERROR;
        } else {
            try {
                hacchuutani = Integer.parseInt(hacchuutanistr);
            } catch (NumberFormatException nfe) {
                Debug.println(nfe);
                errorlist.add("�����P�ʂ��s���ł�");
                result = RC_INPUTERROR;
            }
        }
        String irisuustr = request.getParameter("irisuu");
        if ((irisuustr == null) || (irisuustr.length() == 0)) {
            errorlist.add("���萔����͂��Ă�������");
            result = RC_INPUTERROR;
        } else {
            try {
                //irisuu = Integer.parseInt(irisuustr);
                irisuu = Float.parseFloat(irisuustr);
            } catch (NumberFormatException nfe) {
                Debug.println(nfe);
                errorlist.add("���萔���s���ł�");
                result = RC_INPUTERROR;
            }
        }
        String hacchuutanisuustr = request.getParameter("hacchuutanisuu");
        if ((hacchuutanisuustr == null) || (hacchuutanisuustr.length() == 0)) {
            errorlist.add("�����P�ʐ�����͂��Ă�������");
            result = RC_INPUTERROR;
        } else {
            try {
                hacchuutanisuu = Integer.parseInt(hacchuutanisuustr);
            } catch (NumberFormatException nfe) {
                Debug.println(nfe);
                errorlist.add("�����P�ʐ����s���ł�");
                result = RC_INPUTERROR;
            }
        }
        String saiteihacchuusuustr = request.getParameter("saiteihacchuusuu");
        if ((saiteihacchuusuustr == null) ||
          (saiteihacchuusuustr.length() == 0)) {
            errorlist.add("�Œᔭ��������͂��Ă�������");
            result = RC_INPUTERROR;
        } else {
            try {
                saiteihacchuusuu = Integer.parseInt(saiteihacchuusuustr);
            } catch (NumberFormatException nfe) {
                Debug.println(nfe);
                errorlist.add("�Œᔭ�������s���ł�");
                result = RC_INPUTERROR;
            }
        }
        String tankastr = request.getParameter("tanka");
        if ((tankastr == null) || (tankastr.length() == 0)) {
            errorlist.add("�P������͂��Ă�������");
            result = RC_INPUTERROR;
        } else {
            try {
                tanka = Float.parseFloat(tankastr);
            } catch (NumberFormatException nfe) {
                Debug.println(nfe);
                errorlist.add("�P�����s���ł�");
                result = RC_INPUTERROR;
            }
        }
        String hyoujuntankastr = request.getParameter("hyoujuntanka");
        if (hyoujuntankastr != null) {
            try {
                hyoujuntanka = Float.parseFloat(hyoujuntankastr);
            } catch (NumberFormatException nfe) {
                Debug.println(nfe);
                errorlist.add("�W���P�����s���ł�");
                result = RC_INPUTERROR;
            }
        }
        String shiireleadtimestr = request.getParameter("shiireleadtime");
        if ((shiireleadtimestr == null) || (shiireleadtimestr.length() == 0)) {
            errorlist.add("�d�����[�h�^�C������͂��Ă�������");
            result = RC_INPUTERROR;
        } else {
            try {
                shiireleadtime = Integer.parseInt(shiireleadtimestr);
            } catch (NumberFormatException nfe) {
                Debug.println(nfe);
                errorlist.add("�d�����[�h�^�C�����s���ł�");
                result = RC_INPUTERROR;
            }
        }
        String ondotaistr = request.getParameter("ondotai");
        if ((ondotaistr == null) || (ondotaistr.length() == 0)) {
            errorlist.add("���x�т�I�����Ă�������");
            result = RC_INPUTERROR;
        } else {
            try {
                ondotai = Integer.parseInt(ondotaistr);
            } catch (NumberFormatException nfe) {
                Debug.println(nfe);
                errorlist.add("���x�т��s���ł�");
                result = RC_INPUTERROR;
            }
        }
        String shoumikigen_flgstr = request.getParameter("shoumikigen_flg");
        if (shoumikigen_flgstr != null) {
            try {
                shoumikigen_flg = Integer.parseInt(shoumikigen_flgstr);
            } catch (NumberFormatException nfe) {
                Debug.println(nfe);
                errorlist.add("�ܖ������t���O���s���ł�");
                result = RC_INPUTERROR;
            }
        }
        if (shoumikigen_flg == Names.ON) {
            String shoumikigennissuustr =
               request.getParameter("shoumikigennissuu");
            if ((shoumikigennissuustr == null) ||
              (shoumikigennissuustr.length() == 0)) {
                errorlist.add("�ܖ�������������͂��Ă�������");
                result = RC_INPUTERROR;
            } else {
                try {
                    shoumikigennissuu = Integer.parseInt(shoumikigennissuustr);
                } catch (NumberFormatException nfe) {
                    Debug.println(nfe);
                    errorlist.add("�ܖ������������s���ł�");
                    result = RC_INPUTERROR;
                }
            }
            String shukkakigennissuustr =
               request.getParameter("shukkakigennissuu");
            try {
                shukkakigennissuu = Integer.parseInt(shukkakigennissuustr);
            } catch (NumberFormatException nfe) {
                Debug.println(nfe);
                errorlist.add("�o�׊����������s���ł�");
                result = RC_INPUTERROR;
            }
        }
        String kobetsuhacchuu_flgstr =
          request.getParameter("kobetsuhacchuu_flg");
        if ((kobetsuhacchuu_flgstr != null) &&
          (kobetsuhacchuu_flgstr.length() > 0)) {
            try {
                kobetsuhacchuu_flg = Integer.parseInt(kobetsuhacchuu_flgstr);
            } catch (NumberFormatException nfe) {
                Debug.println(nfe);
                errorlist.add("�ʔ����t���O���s���ł�");
                result = RC_INPUTERROR;
            }
        }
        String youchuuihin_flgstr = request.getParameter("youchuuihin_flg");
        if ((youchuuihin_flgstr != null) && (youchuuihin_flgstr.length() > 0))
          {
            try {
                youchuuihin_flg = Integer.parseInt(youchuuihin_flgstr);
            } catch (NumberFormatException nfe) {
                Debug.println(nfe);
                errorlist.add("�v���ӕi�t���O���s���ł�");
                result = RC_INPUTERROR;
            }
        }
        String youraberu_flgstr = request.getParameter("youraberu_flg");
        if ((youraberu_flgstr != null) && (youraberu_flgstr.length() > 0)) {
            try {
                youraberu_flg = Integer.parseInt(youraberu_flgstr);
            } catch (NumberFormatException nfe) {
                Debug.println(nfe);
                errorlist.add("�v���x���t���O���s���ł�");
                result = RC_INPUTERROR;
            }
        }
        daibunrui = request.getParameter("daibunrui");
        pcode = request.getParameter("pcode");
        String zaikohyoukahouhoustr =
          request.getParameter("zaikohyoukahouhou");
        if ((zaikohyoukahouhoustr == null) ||
          (zaikohyoukahouhoustr.length() == 0)) {
            errorlist.add("�݌ɕ]�����@��I�����Ă�������");
            result = RC_INPUTERROR;
        } else {
            try {
                zaikohyoukahouhou = Integer.parseInt(zaikohyoukahouhoustr);
            } catch (NumberFormatException nfe) {
                Debug.println(nfe);
                errorlist.add("�݌ɕ]�����@���s���ł�");
                result = RC_INPUTERROR;
            }
        }
        jancode = request.getParameter("jancode");
        kataban = request.getParameter("kataban");
        location_id1 = request.getParameter("location_id1");
        location_id2 = request.getParameter("location_id2");
        location_id3 = request.getParameter("location_id3");
        String shuubai_flgstr = request.getParameter("shuubai_flg");
        if ((shuubai_flgstr != null) && (shuubai_flgstr.length() > 0)) {
            try {
                shuubai_flg = Integer.parseInt(shuubai_flgstr);
            } catch (NumberFormatException nfe) {
                Debug.println(nfe);
                errorlist.add("�I���t���O���s���ł�");
                result = RC_INPUTERROR;
            }
        }

        //added 2003/07/24
        String hyoujunbaikastr = request.getParameter("hyoujunbaika");
        if (hyoujunbaikastr != null) {
            try {
                hyoujunbaika = Float.parseFloat(hyoujunbaikastr);
            } catch (NumberFormatException nfe) {
                Debug.println(nfe);
                errorlist.add("�W���������s���ł�");
                result = RC_INPUTERROR;
            }
        }
        nisugata = request.getParameter("nisugata");
        hacchuucomment = request.getParameter("hacchuucomment");
        String hanbaikubunstr = request.getParameter("hanbaikubun");
        if (hanbaikubunstr != null) {
            try {
                hanbaikubun = Integer.parseInt(hanbaikubunstr);
            } catch (NumberFormatException nfe) {
                Debug.println(nfe);
                errorlist.add("�̔��敪���s���ł�");
                result = RC_INPUTERROR;
            }
        }
        shubetsu = request.getParameter("shubetsu");
        String mochikoshi_flgstr = request.getParameter("mochikoshi_flg");
        if ((mochikoshi_flgstr != null) && (!mochikoshi_flgstr.equals(""))) {
            try {
                mochikoshi_flg = Integer.parseInt(mochikoshi_flgstr);
            } catch (NumberFormatException nfe) {
                Debug.println(nfe);
                errorlist.add("�����z���t���O���s���ł�");
                result = RC_INPUTERROR;
            }
        }
        String shinyanouhin_flgstr = request.getParameter("shinyanouhin_flg");
        if ((shinyanouhin_flgstr != null) &&
            (!shinyanouhin_flgstr.equals(""))) {
            try {
                shinyanouhin_flg = Integer.parseInt(shinyanouhin_flgstr);
            } catch (NumberFormatException nfe) {
                Debug.println(nfe);
                errorlist.add("�[��[�i�t���O���s���ł�");
                result = RC_INPUTERROR;
            }
        }

        UserLocal user = (UserLocal)session.getAttribute("USER");
        user_id = user.getUser_id();

        String updatedstr = request.getParameter("updated");
        if (updatedstr != null) {
            updated = Long.parseLong(updatedstr);
        }
    }

    public void handleEvent(HashMap attr) {
        if (result == RC_INPUTERROR) { return; }
        ShouhinLocalHome shome = null;
        ShouhinLocal shouhinl = null;
        try {
            shome = (ShouhinLocalHome)
              ServiceLocator.getLocalHome("java:comp/env/ejb/ShouhinLocal");
            shouhinl = shome.findByPrimaryKey(shouhin_id);
            long objupdated = shouhinl.getUpdated().getTime();
            if (objupdated != updated) {
                result = RC_INPUTERROR;
                errorlist.add("���i����ҏW���Ƀf�[�^���X�V����Ă��܂��B<br>�ēx���i�f�[�^���������Ă���ҏW���Ă��������B");
                setRollbackOnly();
                return;
            }
            shouhinl.setShouhin(shouhin);
            shouhinl.setShouhinfurigana(shouhinfurigana);
            shouhinl.setShiiresaki_id(shiiresaki_id);
            shouhinl.setHacchuushouhin1(hacchuushouhin1);
            shouhinl.setHacchuushouhin2(hacchuushouhin2);
            shouhinl.setHacchuushouhin3(hacchuushouhin3);
            shouhinl.setKikaku(kikaku);
            shouhinl.setTani(tani);
            shouhinl.setHacchuukikaku(hacchuukikaku);
            shouhinl.setHacchuuten(hacchuuten);
            shouhinl.setHacchuutani(hacchuutani);
            shouhinl.setIrisuu(irisuu);
            shouhinl.setHacchuutanisuu(hacchuutanisuu);
            shouhinl.setSaiteihacchuusuu(saiteihacchuusuu);
            shouhinl.setTanka(tanka);
            shouhinl.setHyoujuntanka(hyoujuntanka);
            shouhinl.setShiireleadtime(shiireleadtime);
            shouhinl.setOndotai(ondotai);
            //shouhinl.setShoumikigen_flg(shoumikigen_flg);
            shouhinl.setShoumikigennissuu(shoumikigennissuu);
            shouhinl.setShukkakigennissuu(shukkakigennissuu);
            shouhinl.setKobetsuhacchuu_flg(kobetsuhacchuu_flg);
            shouhinl.setYouchuuihin_flg(youchuuihin_flg);
            shouhinl.setYouraberu_flg(youraberu_flg);
            shouhinl.setDaibunrui(daibunrui);
            shouhinl.setPcode(pcode);
            shouhinl.setZaikohyoukahouhou(zaikohyoukahouhou);
            shouhinl.setJancode(jancode);
            shouhinl.setKataban(kataban);
            shouhinl.setLocation_id1(location_id1);
            shouhinl.setLocation_id2(location_id2);
            shouhinl.setLocation_id3(location_id3);
            shouhinl.setShuubai_flg(shuubai_flg);
            shouhinl.setUpdatedby(user_id);
            shouhinl.setUpdated(new java.util.Date());
            shouhinl.setHyoujunbaika(hyoujunbaika);
            shouhinl.setNisugata(nisugata);
            shouhinl.setHacchuucomment(hacchuucomment);
            shouhinl.setHanbaikubun(hanbaikubun);
            shouhinl.setShubetsu(shubetsu);
            shouhinl.setMochikoshi_flg(mochikoshi_flg);
            shouhinl.setShinyanouhin_flg(shinyanouhin_flg);
        } catch (NamingException ne) {
            Debug.println(ne);
            result = RC_INPUTERROR;
            errorlist.add("�V�X�e���G���[�ENamingException");
            setRollbackOnly();
            return;
        } catch (FinderException fe) {
            Debug.println(fe);
            result = RC_INPUTERROR;
            errorlist.add("���i�R�[�h������܂���");
            setRollbackOnly();
            return;
        } catch (EJBException ee) {
            ee.printStackTrace();
            result = RC_INPUTERROR;
            errorlist.add("�V�X�e���G���[�EEJBException<BR>���ڂ̓��͓��e����������\��������܂��B�����łȂ��ꍇ��IT�`�[���܂ł��A�����������B");
            setRollbackOnly();
        }
    }

    public void postHandle(HttpServletRequest request) {
    }
}
