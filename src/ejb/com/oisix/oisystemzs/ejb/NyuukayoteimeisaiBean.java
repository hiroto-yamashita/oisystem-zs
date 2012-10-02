package com.oisix.oisystemzs.ejb;

import com.oisix.oisystemfr.ejb.ServiceLocator;
import com.oisix.oisystemfr.Debug;
import com.oisix.oisystemfr.ejb.KeyGeneratorLocal;
import com.oisix.oisystemfr.ejb.KeyGeneratorLocalHome;
import java.util.Calendar;
import java.util.HashMap;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.CreateException;
import javax.ejb.FinderException;
import javax.naming.NamingException;
import javax.naming.InitialContext;

/**
 * The Entity bean represents Nyuukayoteimeisai
 *
 * @author Ryuya Matsunaga
 * @version $Revision: 1.0 $
 *
 * @ejb:bean name="Nyuukayoteimeisai"
 *           display-name="Nyuukayoteimeisai"
 *           type="CMP"
 *           jndi-name="ejb/Nyuukayoteimeisai"
 *           local-jndi-name="ejb/NyuukayoteimeisaiLocal"
 *           view-type="both"
 *
 * @ejb:ejb-ref ejb-name="KeyGenerator"
 *              view-type="local"
 * @ejb:ejb-ref ejb-name="Shouhin"
 *              view-type="local"
 * @ejb:ejb-ref ejb-name="Hacchuu"
 *              view-type="local"
 * @ejb:finder signature="Collection findByHacchuu_bg(java.lang.String hacchuu_bg)" query="SELECT OBJECT(n) from Nyuukayoteimeisai n WHERE n.hacchuu_bg = ?1" result-type-mapping="Local"
 *
 * @ejb:finder signature="Collection findByHacchuu_bgAndShouhin_id(java.lang.String hacchuu_bg, java.lang.String shouhin_id)" query="SELECT OBJECT(n) from Nyuukayoteimeisai n WHERE n.hacchuu_bg = ?1 AND n.shouhin_id = ?2" result-type-mapping="Local"
 *
 * @ejb:finder signature="Collection findByNyuukayotei_date(java.lang.String souko_id, java.lang.String shouhin_id, java.util.Date nyuukayotei_date)" query="SELECT OBJECT(n) from Nyuukayoteimeisai n WHERE n.souko_id = ?1 AND n.shouhin_id = ?2 and (n.nyuukayotei_date = ?3 or n.nyuukayotei_date > ?3)" result-type-mapping="Local"
 *
 * @ejb.persistence table-name="zt_nyuukayoteimeisai"
 *
 * @jboss:persistence pk-constraint="true"
 *
 **/
public abstract class NyuukayoteimeisaiBean implements EntityBean {

    public static final int MINYUUKA = 1;
    public static final int ICHIBUNYUUKA = 2;
    public static final int NYUUKAZUMI = 3;

    public static final int MISHUTSURYOKU = 10;
    public static final int SHUTSURYOKUZUMI = 20;
    public static final int FAXZUMI = 30;
    public static final int FAXBKZUMI = 40;

    private EntityContext entityContext;

    /**
    * @ejb:persistent-field
    * @ejb:pk-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract int getNyuukayotei_id();
    public abstract void setNyuukayotei_id(int nyuukayotei_id);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract String getHacchuu_bg();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setHacchuu_bg(String hacchuu_bg);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract String getSouko_id();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setSouko_id(String souko_id);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract String getShouhin_id();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setShouhin_id(String shouhin_id);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract float getHacchuusuuryou();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setHacchuusuuryou(float hacchuusuuryou);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract String getHacchuutani();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setHacchuutani(String hacchuutani);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract float getHacchuutanka();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setHacchuutanka(float hacchuutanka);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract float getNyuukasuuryou();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setNyuukasuuryou(float nyuukasuuryou);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract String getNyuukatani();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setNyuukatani(String nyuukatani);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract java.util.Date getNyuukayotei_date();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setNyuukayotei_date(java.util.Date nyuukayotei_date);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract int getTouchakujikan();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setTouchakujikan(int touchakujikan);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract int getOndotai();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setOndotai(int ondotai);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract int getHacchuukubun();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setHacchuukubun(int hacchuukubun);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract java.util.Date getShoumikigen();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setShoumikigen(java.util.Date shoumikigen);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract java.util.Date getShukkakigen();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setShukkakigen(java.util.Date shukkakigen);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract int getNyuukokubun();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setNyuukokubun(int nyuukokubun);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract String getBikou();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setBikou(String bikou);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract float getJitsunyuukasuu();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setJitsunyuukasuu(float jitsunyuukasuu);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract int getNyuukajoukyou();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setNyuukajoukyou(int nyuukajoukyou);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract java.util.Date getCreated();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setCreated(java.util.Date created);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract int getCreatedby();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setCreatedby(int createdby);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract java.util.Date getUpdated();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setUpdated(java.util.Date updated);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract int getUpdatedby();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setUpdatedby(int updatedby);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract int getStatus();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setStatus(int status);
    /**
    * @ejb:create-method view-type="both"
    **/
    public NyuukayoteimeisaiPK ejbCreate(
        String hacchuu_bg,
        String souko_id,
        String shouhin_id,
        float hacchuusuuryou,
        String hacchuutani,
        float hacchuutanka,
        float nyuukasuuryou,
        String nyuukatani,
        java.util.Date nyuukayotei_date,
        int touchakujikan, int ondotai,
        int hacchuukubun,
        java.util.Date shoumikigen,
        java.util.Date shukkakigen,
        int nyuukokubun, String bikou,
        float jitsunyuukasuu,
        int nyuukajoukyou,
        int user_id)
      throws CreateException {
        int nyuukayotei_id = getNextNyuukayotei_id();
        setNyuukayotei_id(nyuukayotei_id);
        setHacchuu_bg(hacchuu_bg);
        setSouko_id(souko_id);
        setShouhin_id(shouhin_id);
        setHacchuusuuryou(hacchuusuuryou);
        setHacchuutani(hacchuutani);
        setHacchuutanka(hacchuutanka);
        setNyuukasuuryou(nyuukasuuryou);
        setNyuukatani(nyuukatani);
        setNyuukayotei_date(nyuukayotei_date);
        setTouchakujikan(touchakujikan);
        setOndotai(ondotai);
        setHacchuukubun(hacchuukubun);
        setShoumikigen(shoumikigen);
        setShukkakigen(shukkakigen);
        setNyuukokubun(nyuukokubun);
        setBikou(bikou);
        setJitsunyuukasuu(jitsunyuukasuu);
        setNyuukajoukyou(nyuukajoukyou);
        setCreated(new java.util.Date());
        setCreatedby(user_id);
        setUpdated(new java.util.Date());
        setUpdatedby(user_id);
        setStatus(MISHUTSURYOKU);
        return null;
    }
    public void ejbPostCreate(
        String hacchuu_bg,
        String souko_id,
        String shouhin_id,
        float hacchuusuuryou,
        String hacchuutani,
        float hacchuutanka,
        float nyuukasuuryou,
        String nyuukatani,
        java.util.Date nyuukayotei_date,
        int touchakujikan,
        int ondotai,
        int hacchuukubun,
        java.util.Date shoumikigen,
        java.util.Date shukkakigen,
        int nyuukokubun,
        String bikou,
        float jitsunyuukasuu,
        int nyuukajoukyou,
        int user_id) {}
    /**
    * @ejb:create-method view-type="both"
    **/
    public NyuukayoteimeisaiPK ejbCreate(
        String hacchuu_bg,
        String souko_id,
        String shouhin_id,
        float hacchuusuuryou,
        String hacchuutani,
        float hacchuutanka,
        float nyuukasuuryou,
        String nyuukatani,
        java.util.Date nyuukayotei_date,
        int touchakujikan, int ondotai,
        int hacchuukubun,
        java.util.Date shoumikigen,
        java.util.Date shukkakigen,
        int nyuukokubun, String bikou,
        float jitsunyuukasuu,
        int nyuukajoukyou,
        int user_id,
        int status)
      throws CreateException {
        int nyuukayotei_id = getNextNyuukayotei_id();
        setNyuukayotei_id(nyuukayotei_id);
        setHacchuu_bg(hacchuu_bg);
        setSouko_id(souko_id);
        setShouhin_id(shouhin_id);
        setHacchuusuuryou(hacchuusuuryou);
        setHacchuutani(hacchuutani);
        setHacchuutanka(hacchuutanka);
        setNyuukasuuryou(nyuukasuuryou);
        setNyuukatani(nyuukatani);
        setNyuukayotei_date(nyuukayotei_date);
        setTouchakujikan(touchakujikan);
        setOndotai(ondotai);
        setHacchuukubun(hacchuukubun);
        setShoumikigen(shoumikigen);
        setShukkakigen(shukkakigen);
        setNyuukokubun(nyuukokubun);
        setBikou(bikou);
        setJitsunyuukasuu(jitsunyuukasuu);
        setNyuukajoukyou(nyuukajoukyou);
        setCreated(new java.util.Date());
        setCreatedby(user_id);
        setUpdated(new java.util.Date());
        setUpdatedby(user_id);
        setStatus(status);
        return null;
    }
    public void ejbPostCreate(
        String hacchuu_bg,
        String souko_id,
        String shouhin_id,
        float hacchuusuuryou,
        String hacchuutani,
        float hacchuutanka,
        float nyuukasuuryou,
        String nyuukatani,
        java.util.Date nyuukayotei_date,
        int touchakujikan,
        int ondotai,
        int hacchuukubun,
        java.util.Date shoumikigen,
        java.util.Date shukkakigen,
        int nyuukokubun,
        String bikou,
        float jitsunyuukasuu,
        int nyuukajoukyou,
        int user_id,
        int status) {}
    public void setEntityContext(EntityContext ctx) {}
    public void unsetEntityContext() {}
    public void ejbRemove() throws javax.ejb.RemoveException {}
    public void ejbActivate() {}
    public void ejbPassivate() {}
    public void ejbLoad() {}
    public void ejbStore() {}

    private int getNextNyuukayotei_id() {
        int id = 0;
        try {
            InitialContext ic = new InitialContext();
            Object objref = ic.lookup("java:comp/env/ejb/KeyGeneratorLocal");
            KeyGeneratorLocalHome kgh = (KeyGeneratorLocalHome)objref;
            KeyGeneratorLocal kg = kgh.create();
            id = kg.getNext("ZS_NYUUKAYOTEIMEISAI");
        } catch (NamingException ex) {
            Debug.println(ex);
        } catch (CreateException ex) {
            Debug.println(ex);
        }
        return id;
    }

    /**
    * @ejb:interface-method view-type="local"
    **/
    public ShouhinLocal getShouhin() throws NamingException, FinderException {
        ShouhinLocalHome sh = (ShouhinLocalHome)ServiceLocator.getLocalHome(
          "java:comp/env/ejb/ShouhinLocal");
        return sh.findByPrimaryKey(getShouhin_id());
    }

    /**
    * @ejb:interface-method view-type="both"
    **/
    public NyuukayoteimeisaiData getNyuukayoteimeisaiData() {
        NyuukayoteimeisaiData data = new NyuukayoteimeisaiData(
          this.getNyuukayotei_id(),
          this.getHacchuu_bg(),
          this.getSouko_id(),
          this.getShouhin_id(),
          this.getHacchuusuuryou(),
          this.getHacchuutani(),
          this.getHacchuutanka(),
          this.getNyuukasuuryou(),
          this.getNyuukatani(),
          this.getNyuukayotei_date(),
          this.getTouchakujikan(),
          this.getOndotai(),
          this.getHacchuukubun(),
          this.getShoumikigen(),
          this.getShukkakigen(),
          this.getNyuukokubun(),
          this.getBikou(),
          this.getJitsunyuukasuu(),
          this.getNyuukajoukyou(),
          this.getCreated(),
          this.getCreatedby(),
          this.getUpdated(),
          this.getUpdatedby(),
          this.getStatus()
        );
        return data;
    }

    /**
    * @ejb:interface-method view-type="both"
    **/
    public void changeNyuukayotei_date(java.util.Date todate, int user_id)
      throws NamingException, FinderException {
        setNyuukayotei_date(todate);
        ShouhinLocal shouhin = getShouhin();
        if (shouhin.getShoumikigen_flg() == 1) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(todate);
            cal.add(Calendar.DATE, shouhin.getShoumikigennissuu());
            setShoumikigen(cal.getTime());
            cal.setTime(todate);
            cal.add(Calendar.DATE, shouhin.getShukkakigennissuu());
            setShukkakigen(cal.getTime());
        }
        setUpdated(new java.util.Date());
        setUpdatedby(user_id);
   }

    /**
    * @ejb:interface-method view-type="both"
    **/
    public void addJitsunyuukasuu(float suuryou, int user_id) {
        setJitsunyuukasuu(getJitsunyuukasuu() + suuryou);
        updateNyuukajoukyou();
        setUpdated(new java.util.Date());
        setUpdatedby(user_id);
    }

    private void updateNyuukajoukyou() {
        float nyuukasuuryou = getNyuukasuuryou();
        float jitsunyuukasuu = getJitsunyuukasuu();
        if (jitsunyuukasuu <= 0) {
            setNyuukajoukyou(MINYUUKA);
        } else if (nyuukasuuryou <= jitsunyuukasuu) {
            setNyuukajoukyou(NYUUKAZUMI);
        } else {
            setNyuukajoukyou(ICHIBUNYUUKA);
        }
    }

    /**
    * @ejb:interface-method view-type="local"
    **/
    public HacchuuLocal getHacchuu() throws NamingException, FinderException {
        HacchuuLocalHome hh = (HacchuuLocalHome)ServiceLocator.getLocalHome(
          "java:comp/env/ejb/HacchuuLocal");
        return hh.findByHacchuu_bg(getHacchuu_bg());
    }

}
