package com.oisix.oisystemzs.ejb;

import com.oisix.oisystemfr.ejb.ServiceLocator;
import com.oisix.oisystemfr.Debug;
import com.oisix.oisystemfr.ejb.KeyGeneratorLocal;
import com.oisix.oisystemfr.ejb.KeyGeneratorLocalHome;
import java.util.Collection;
import java.util.HashMap;
import java.text.SimpleDateFormat;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.CreateException;
import javax.ejb.FinderException;
import javax.naming.NamingException;
import javax.naming.InitialContext;

/**
 * The Entity bean represents Shukkayotei
 *
 * @author Ryuya Matsunaga
 * @version $Revision: 1.0 $
 *
 * @ejb:bean name="Shukkayotei"
 *           display-name="Shukkayotei"
 *           type="CMP"
 *           jndi-name="ejb/Shukkayotei"
 *           local-jndi-name="ejb/ShukkayoteiLocal"
 *           view-type="both"
 *
 * @ejb:ejb-ref ejb-name="KeyGenerator"
 *              view-type="local"
 * @ejb:ejb-ref ejb-name="Shukkayoteimeisai"
 *              view-type="local"
 * @ejb:finder signature="Collection findByShubetsuAndDate(int shubetsu, java.lang.String shukkamoto, java.util.Date shukkadate)" query="SELECT OBJECT(s) from Shukkayotei s WHERE s.shukkayoteishubetsu = ?1 AND s.shukkamoto_id = ?2 AND s.shukkayotei_date = ?3" result-type-mapping="Local"
 *
 * @ejb.persistence table-name="zt_shukkayotei"
 *
 * @jboss:persistence pk-constraint="true"
 *
 **/
public abstract class ShukkayoteiBean implements EntityBean {
    private EntityContext entityContext;

    /**
    * @ejb:persistent-field
    * @ejb:pk-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract int getShukkayotei_id();
    public abstract void setShukkayotei_id(int shukkayotei_id);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract String getShukkayotei_bg();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setShukkayotei_bg(String shukkayotei_bg);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract int getShukkayoteishubetsu();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setShukkayoteishubetsu(int shukkayoteishubetsu);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract int getShukkokubun();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setShukkokubun(int shukkokubun);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract java.util.Date getShukkayotei_date();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setShukkayotei_date(java.util.Date shukkayotei_date);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract java.util.Date getNouhinyotei_date();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setNouhinyotei_date(java.util.Date nouhinyotei_date);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract String getShukkamoto_id();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setShukkamoto_id(String shukkamoto_id);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract String getNouhinsaki_id();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setNouhinsaki_id(String nouhinsaki_id);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract String getNouhinsakiname();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setNouhinsakiname(String nouhinsakiname);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract String getNouhinsakizip();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setNouhinsakizip(String nouhinsakizip);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract String getNouhinsakiaddr();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setNouhinsakiaddr(String nouhinsakiaddr);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract String getNouhinsakitel();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setNouhinsakitel(String nouhinsakitel);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract String getNouhinsakifax();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setNouhinsakifax(String nouhinsakifax);
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
    * @ejb:create-method view-type="both"
    **/
    public ShukkayoteiPK ejbCreate(int shukkayoteishubetsu, int shukkokubun,
        java.util.Date shukkayotei_date, java.util.Date nouhinyotei_date,
        String shukkamoto_id, String nouhinsaki_id, String nouhinsakiname,
        String nouhinsakizip, String nouhinsakiaddr, String nouhinsakitel,
        String nouhinsakifax, String bikou, int user_id)
      throws CreateException {
        int shukkayotei_id = getNextShukkayotei_id();
        String shukkayotei_bg = getNewShukkayotei_bg(shukkayotei_id);
        setShukkayotei_id(shukkayotei_id);
        setShukkayotei_bg(shukkayotei_bg);
        setShukkayoteishubetsu(shukkayoteishubetsu);
        setShukkokubun(shukkokubun);
        setShukkayotei_date(shukkayotei_date);
        setNouhinyotei_date(nouhinyotei_date);
        setShukkamoto_id(shukkamoto_id);
        setNouhinsaki_id(nouhinsaki_id);
        setNouhinsakiname(nouhinsakiname);
        setNouhinsakizip(nouhinsakizip);
        setNouhinsakiaddr(nouhinsakiaddr);
        setNouhinsakitel(nouhinsakitel);
        setNouhinsakifax(nouhinsakifax);
        setBikou(bikou);
        setCreated(new java.util.Date());
        setCreatedby(user_id);
        setUpdated(new java.util.Date());
        setUpdatedby(user_id);
        return null;
    }
    public void ejbPostCreate(int shukkayoteishubetsu, int shukkokubun,
        java.util.Date shukkayotei_date, java.util.Date nouhinyotei_date,
        String shukkamoto_id, String nouhinsaki_id, String nouhinsakiname,
        String nouhinsakizip, String nouhinsakiaddr, String nouhinsakitel,
        String nouhinsakifax, String bikou, int user_id) {}
    public void setEntityContext(EntityContext ctx) {}
    public void unsetEntityContext() {}
    public void ejbRemove() throws javax.ejb.RemoveException {}
    public void ejbActivate() {}
    public void ejbPassivate() {}
    public void ejbLoad() {}
    public void ejbStore() {}

    private int getNextShukkayotei_id() {
        int id = 0;
        try {
            InitialContext ic = new InitialContext();
            Object objref = ic.lookup("java:comp/env/ejb/KeyGeneratorLocal");
            KeyGeneratorLocalHome kgh = (KeyGeneratorLocalHome)objref;
            KeyGeneratorLocal kg = kgh.create();
            id = kg.getNext("ZS_SHUKKAYOTEI");
        } catch (NamingException ex) {
            Debug.println(ex);
        } catch (CreateException ex) {
            Debug.println(ex);
        }
        return id;
    }

    private String getNewShukkayotei_bg(int shukkayotei_id) {
        String bg = "SY-";
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
        bg += sdf.format(new java.util.Date()) + "-";
        String strShukkayotei_id = "0000" + String.valueOf(shukkayotei_id);
        int start = strShukkayotei_id.length() - 4;
        bg += strShukkayotei_id.substring(start);
        return bg;
    }

    /**
    * @ejb:interface-method view-type="both"
    **/
    public ShukkayoteiData getShukkayoteiData() {
        ShukkayoteiData data = new ShukkayoteiData(
          this.getShukkayotei_id(),
          this.getShukkayotei_bg(),
          this.getShukkayoteishubetsu(),
          this.getShukkokubun(),
          this.getShukkayotei_date(),
          this.getNouhinyotei_date(),
          this.getShukkamoto_id(),
          this.getNouhinsaki_id(),
          this.getNouhinsakiname(),
          this.getNouhinsakizip(),
          this.getNouhinsakiaddr(),
          this.getNouhinsakitel(),
          this.getNouhinsakifax(),
          this.getBikou(),
          this.getCreated(),
          this.getCreatedby(),
          this.getUpdated(),
          this.getUpdatedby()
        );
        return data;
    }

    /**
    * @ejb:interface-method view-type="both"
    **/
    public Collection getShukkayoteimeisai()
      throws NamingException, FinderException {
        ShukkayoteimeisaiLocalHome nh = (ShukkayoteimeisaiLocalHome)
          ServiceLocator.getLocalHome(
          "java:comp/env/ejb/ShukkayoteimeisaiLocal");
        return nh.findByShukkayotei_bg(getShukkayotei_bg());
    }

}
