package com.oisix.oisystemzs.eventhandler;

import com.oisix.oisystemfr.Debug;
import com.oisix.oisystemzs.pdf.Labelsakuseipdf;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import com.oisix.oisystemfr.TransactionEvent;
import com.oisix.oisystemfr.ejb.ServiceLocator;
import com.oisix.oisystemzs.ejb.UserLocal;
import com.oisix.oisystemzs.ejb.NyuukayoteimeisaiLocal;
import com.oisix.oisystemzs.ejb.NyuukayoteimeisaiLocalHome;
import com.oisix.oisystemzs.ejb.NyuukayoteimeisaiData;
import com.oisix.oisystemzs.ejb.NyuukayoteimeisaiPK;
import javax.naming.NamingException;
import javax.ejb.FinderException;
import com.oisix.oisystemzs.Names;

public class MakelabelpdfEvent extends TransactionEvent {

    private LinkedList labelsakuseiList = new LinkedList();
    HashMap buranku = new HashMap();
    private int user_id;
    private String filename;
    
    public String getFileName() { return filename; }

    public void init(HttpServletRequest request) {
    //���x�����̎擾
        int labelsuu = 0;
        String strlabelsuu = getInput("#labelsuu");
        try{
            labelsuu = Integer.parseInt(strlabelsuu);
        } catch (NumberFormatException nfe) {
            Debug.println(nfe);
            errorlist.add("���x�������s���ł��B");
            result = RC_INPUTERROR;
        }
    //�u�����N�����̎擾
        String strburankumaisuu = getInput("#burankumaisuu");
        buranku.put("BURANKUMAISUU",strburankumaisuu);
        for (int i=0; i<labelsuu; i++){
            HashMap labelMap = new HashMap();
            String strInd = String.valueOf(i);
            //���i�̎擾
            String shouhin = getInput("#shouhin" + strInd);
            labelMap.put("shouhin",shouhin);
            //���i�R�[�h�̎擾
            String strshouhin_id = getInput("#shouhin_id" + strInd);
            labelMap.put("shouhin_id",strshouhin_id);
            //�����̎擾
            String strmaisuu = getInput("#maisuu" + strInd);
            try{
                Float maisuu = new Float(strmaisuu);
                labelMap.put("maisuu",maisuu);
            }catch (NumberFormatException nfe) {
                Debug.println(nfe);
                errorlist.add("�������s���ł��B");
                result = RC_INPUTERROR;
            }
            //���ח\��R�[�h�̎擾
            String strnyuukayotei_id = getInput("#nyuukayotei_id" + strInd);
            try{
                Integer nyuukayotei_id = new Integer(strnyuukayotei_id);
                labelMap.put("nyuukayotei_id",nyuukayotei_id);
            }catch (NumberFormatException nfe) {
                Debug.println(nfe);
                errorlist.add("���ח\��R�]�h���s���ł��B");
                result = RC_INPUTERROR;
            }
            // �ܖ������̎擾
            int shoumiyear = 0;
            int shoumimonth = 0;
            int shoumiday = 0;
            java.util.Date shoumikigen = null;
            String shoumiyearstr =
            getInput("#shoumiyear" + strInd);
            if(shoumiyearstr.equals("0")){
                labelMap.put("shoumikigen", shoumikigen);
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
                    labelMap.put("shoumikigen", shoumikigen);
                } catch (NumberFormatException nfe) {
                    Debug.println(nfe);
                    errorlist.add("�ܖ��������s���ł��B");
                    result = RC_INPUTERROR;
                    continue;
                }
            }
            // �o�׊����̎擾
            int shukkayear = 0;
            int shukkamonth = 0;
            int shukkaday = 0;
            java.util.Date shukkakigen = null;
            String shukkayearstr =
              getInput("#shukkayear" + strInd);
            if(shukkayearstr.equals("0")){
                labelMap.put("shukkakigen", shukkakigen);
            }else{
                String shukkamonthstr =
                  getInput("#shukkamonth" + strInd);
                String shukkadatestr =
                  getInput("#shukkadate" + strInd);
                try {
                    shukkayear = Integer.parseInt(shukkayearstr);
                    shukkamonth = Integer.parseInt(shukkamonthstr);
                    shukkaday = Integer.parseInt(shukkadatestr);
                    Calendar shukkacal = Calendar.getInstance();
                    shukkacal.clear();
                    shukkacal.set(shukkayear, shukkamonth - 1, shukkaday);
                    shukkakigen = shukkacal.getTime();
                    labelMap.put("shukkakigen", shukkakigen);
                } catch (NumberFormatException nfe) {
                    Debug.println(nfe);
                    errorlist.add("�ܖ��������s���ł��B");
                    result = RC_INPUTERROR;
                    continue;
                }
            }
            labelsakuseiList.add(labelMap);
        }
        // ���[�U�̎擾
        UserLocal user = (UserLocal)session.getAttribute("USER");
        user_id = user.getUser_id();
    }

     public void handleEvent(HashMap attr) {
        if(result == RC_INPUTERROR) { return; }
        
        Labelsakuseipdf lpdf = new Labelsakuseipdf();
        java.util.Date now = new java.util.Date();
        try{
            NyuukayoteimeisaiLocalHome nymlh =
              (NyuukayoteimeisaiLocalHome)ServiceLocator.getLocalHome(
              "java:comp/env/ejb/NyuukayoteimeisaiLocal");
            Iterator iter = labelsakuseiList.iterator();
            while(iter.hasNext()){
                HashMap labelsakuseiMap = (HashMap)iter.next();
                Integer nyuukayotei = (Integer)labelsakuseiMap.get("nyuukayotei_id");
                if(nyuukayotei != null && !nyuukayotei.equals("")){
                    int intnyuukayotei_id = nyuukayotei.intValue();
                    NyuukayoteimeisaiPK pk = new NyuukayoteimeisaiPK(intnyuukayotei_id);
                    NyuukayoteimeisaiLocal nyml = nymlh.findByPrimaryKey(pk);
                    lpdf.setNyuukayoteimeisaiData(nyml.getNyuukayoteimeisaiData());
                    //�ܖ������Əo�׊������X�V
                    nyml.setShoumikigen((java.util.Date)labelsakuseiMap.get("shoumikigen"));
                    nyml.setShukkakigen((java.util.Date)labelsakuseiMap.get("shukkakigen"));
                    nyml.setUpdated(now);
                    nyml.setUpdatedby(user_id);
                }
                lpdf.setBuranku(buranku);
                lpdf.setLabelsakuseiList(labelsakuseiList);
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
        } 
        try{
            lpdf.makeDocument();
        }catch(Exception e){
             throw new RuntimeException(e);
        }
        filename = lpdf.getFileName();
    }
    
    public void postHandle(HttpServletRequest request) {
        if (result == RC_INPUTERROR) { return; }
        HttpSession session = request.getSession();
        session.setAttribute("FILENAME", filename);
    }
}