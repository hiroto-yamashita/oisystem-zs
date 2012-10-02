package com.oisix.oisystemzs.ejb;

import com.oisix.oisystemfr.ejb.ServiceLocator;
import com.oisix.oisystemfr.Debug;
import com.oisix.oisystemfr.ejb.KeyGeneratorLocal;
import com.oisix.oisystemfr.ejb.KeyGeneratorLocalHome;
import java.util.HashMap;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.CreateException;
import javax.ejb.FinderException;
import javax.naming.NamingException;
import javax.naming.InitialContext;

/**
 * The Entity bean represents Shukkayoteimeisai
 *
 * @author Ryuya Matsunaga
 * @version $Revision: 1.0 $
 *
 * @ejb:bean name="Shukkayoteimeisai"
 *           display-name="Shukkayoteimeisai"
 *           type="CMP"
 *           jndi-name="ejb/Shukkayoteimeisai"
 *           local-jndi-name="ejb/ShukkayoteimeisaiLocal"
 *           view-type="both"
 *
 * @ejb:ejb-ref ejb-name="KeyGenerator"
 *              view-type="local"
 * @ejb:ejb-ref ejb-name="Shouhin"
 *              view-type="local"
 * @ejb:finder signature="Collection findByShukkayotei_bg(java.lang.String shukkayotei_bg)" query="SELECT OBJECT(s) from Shukkayoteimeisai s WHERE s.shukkayotei_bg = ?1" result-type-mapping="Local"
 * @ejb:finder signature="Collection findByShukkayotei_bgAndShouhin_id(java.lang.String shukkayotei_bg, java.lang.String shouhin_id)" query="SELECT OBJECT(s) from Shukkayoteimeisai s WHERE s.shukkayotei_bg = ?1 AND s.shouhin_id = ?2" result-type-mapping="Local"
 *
 * @ejb.persistence table-name="zt_shukkayoteimeisai"
 *
 * @jboss:persistence pk-constraint="true"
 *
 **/
public abstract class ShukkayoteimeisaiBean implements EntityBean {
    public static final int MISHUKKA = 1;
    public static final int ICHIBUSHUKKA = 2;
    public static final int SHUKKAZUMI = 3;

    private EntityContext entityContext;

    /**
    * @ejb:persistent-field
    * @ejb:pk-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract int getShukkayoteimeisai_id();
    public abstract void setShukkayoteimeisai_id(int shukkayoteimeisai_id);
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
    public abstract float getShukkayoteisuuryou();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setShukkayoteisuuryou(float shukkayoteisuuryou);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract String getTani();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setTani(String tani);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract float getJitsushukkasuuryou();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setJitsushukkasuuryou(float jitsushukkasuuryou);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract int getShukkajoukyou();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setShukkajoukyou(int shukkajoukyou);
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
    public abstract float getHyoujunbaika();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setHyoujunbaika(float hyoujunbaika);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract String getMeyasu();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setMeyasu(String meyasu);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract String getPreshouhin_id();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setPreshouhin_id(String preshouhin_id);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract String getPreshouhin();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setPreshouhin(String preshouhin);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract String getPrekikaku();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setPrekikaku(String prekikaku);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract float getPresuuryou();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setPresuuryou(float presuuryou);

    /**
    * @ejb:create-method view-type="both"
    **/
    public ShukkayoteimeisaiPK ejbCreate(
      String shukkayotei_bg,
      String souko_id,
      String shouhin_id,
      float shukkayoteisuuryou,
      String tani,
      float jitsushukkasuuryou,
      int shukkajoukyou,
      int user_id,
      float hyoujunbaika,
      String meyasu,
      String preshouhin_id,
      String preshouhin,
      String prekikaku,
      float presuuryou
      )
      throws CreateException {
        int shukkayoteimeisai_id = getNextShukkayoteimeisai_id();
        setShukkayoteimeisai_id(shukkayoteimeisai_id);
        setShukkayotei_bg(shukkayotei_bg);
        setSouko_id(souko_id);
        setShouhin_id(shouhin_id);
        setShukkayoteisuuryou(shukkayoteisuuryou);
        setTani(tani);
        setJitsushukkasuuryou(jitsushukkasuuryou);
        setShukkajoukyou(shukkajoukyou);
        setCreated(new java.util.Date());
        setCreatedby(user_id);
        setUpdated(new java.util.Date());
        setUpdatedby(user_id);
        setHyoujunbaika(hyoujunbaika);
        setMeyasu(meyasu);
        setPreshouhin_id(preshouhin_id);
        setPreshouhin(preshouhin);
        setPrekikaku(prekikaku);
        setPresuuryou(presuuryou);
        return null;
    }
    public void ejbPostCreate(
      String shukkayotei_bg,
      String souko_id,
      String shouhin_id,
      float shukkayoteisuuryou,
      String tani,
      float jitsushukkasuuryou,
      int shukkajoukyou,
      int user_id,
      float hyoujunbaika,
      String meyasu,
      String preshouhin_id,
      String preshouhin,
      String prekikaku,
      float presuuryou
      ) {}
    public void setEntityContext(EntityContext ctx) {}
    public void unsetEntityContext() {}
    public void ejbRemove() throws javax.ejb.RemoveException {}
    public void ejbActivate() {}
    public void ejbPassivate() {}
    public void ejbLoad() {}
    public void ejbStore() {}

    private int getNextShukkayoteimeisai_id() {
        int id = 0;
        try {
            InitialContext ic = new InitialContext();
            Object objref = ic.lookup("java:comp/env/ejb/KeyGeneratorLocal");
            KeyGeneratorLocalHome kgh = (KeyGeneratorLocalHome)objref;
            KeyGeneratorLocal kg = kgh.create();
            id = kg.getNext("ZS_SHUKKAYOTEIMEISAI");
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
    public ShukkayoteimeisaiData getShukkayoteimeisaiData() {
        ShukkayoteimeisaiData data = new ShukkayoteimeisaiData(
          this.getShukkayoteimeisai_id(),
          this.getShukkayotei_bg(),
          this.getSouko_id(),
          this.getShouhin_id(),
          this.getShukkayoteisuuryou(),
          this.getTani(),
          this.getJitsushukkasuuryou(),
          this.getShukkajoukyou(),
          this.getCreated(),
          this.getCreatedby(),
          this.getUpdated(),
          this.getUpdatedby(),
          this.getHyoujunbaika(),
          this.getMeyasu(),
          this.getPreshouhin_id(),
          this.getPreshouhin(),
          this.getPrekikaku(),
          this.getPresuuryou()
        );
        return data;
    }

    /**
    * @ejb:interface-method view-type="both"
    **/
    public void addJitsushukkasuuryou(float suuryou, int user_id) {
        setJitsushukkasuuryou(getJitsushukkasuuryou() + suuryou);
        updateShukkajoukyou();
        setUpdated(new java.util.Date());
        setUpdatedby(user_id);
    }

    private void updateShukkajoukyou() {
        float shukkayoteisuuryou = getShukkayoteisuuryou();
        float jitsushukkasuuryou = getJitsushukkasuuryou();
        if (jitsushukkasuuryou <= 0) {
            setShukkajoukyou(MISHUKKA);
        } else if (shukkayoteisuuryou <= jitsushukkasuuryou) {
            setShukkajoukyou(SHUKKAZUMI);
        } else {
            setShukkajoukyou(ICHIBUSHUKKA);
        }
    }
}
