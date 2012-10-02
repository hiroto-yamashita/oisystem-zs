package com.oisix.oisystemzs.ejb;

import com.oisix.oisystemfr.ejb.ServiceLocator;
import com.oisix.oisystemfr.Debug;
import com.oisix.oisystemfr.ejb.KeyGeneratorLocal;
import com.oisix.oisystemfr.ejb.KeyGeneratorLocalHome;
import com.oisix.oisystemzs.ejb.exception.ZaikomeisaiException;
import java.util.HashMap;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.CreateException;
import javax.ejb.FinderException;
import javax.naming.NamingException;
import javax.naming.InitialContext;

/**
 * The Entity bean represents Zaikomeisai
 *
 * @author Ryuya Matsunaga
 * @version $Revision: 1.0 $
 *
 * @ejb:bean name="Zaikomeisai"
 *           display-name="Zaikomeisai"
 *           type="CMP"
 *           jndi-name="ejb/Zaikomeisai"
 *           local-jndi-name="ejb/ZaikomeisaiLocal"
 *           view-type="both"
 *
 * @ejb:ejb-ref ejb-name="KeyGenerator"
 *              view-type="local"
 * @ejb:ejb-ref ejb-name="Zaiko"
 *              view-type="local"
 * @ejb:ejb-ref ejb-name="Shouhin"
 *              view-type="local"
 *
 * @ejb:finder signature="Collection findByZaiko_id(int zaiko_id)" query="SELECT OBJECT(z) from Zaikomeisai z WHERE z.zaiko_id = ?1" result-type-mapping="Local"
 *
 * @ejb:finder signature="Collection findByShoumi(int zaiko_id, java.util.Date shoumi)" query="SELECT OBJECT(z) from Zaikomeisai z WHERE z.zaiko_id = ?1 and z.shoumikigen = ?2" result-type-mapping="Local"
 *
 * @ejb.persistence table-name="zt_zaikomeisai"
 *
 * @jboss:persistence pk-constraint="true"
 *
 **/
public abstract class ZaikomeisaiBean implements EntityBean {
    private EntityContext entityContext;

    /**
    * @ejb:persistent-field
    * @ejb:pk-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract int getZaikomeisai_id();
    public abstract void setZaikomeisai_id(int zaikomeisai_id);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract int getZaiko_id();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setZaiko_id(int zaiko_id);
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
    public abstract String getLocation_id();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setLocation_id(String location_id);
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
    public abstract float getSuuryou();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setSuuryou(float suuryou);
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
    public ZaikomeisaiPK ejbCreate(int zaiko_id, String souko_id,
        String shouhin_id, String location_id, java.util.Date shoumikigen,
        float suuryou, java.util.Date shukkakigen, int user_id)
      throws CreateException {
        int zaikomeisai_id = getNextZaikomeisai_id();
        setZaikomeisai_id(zaikomeisai_id);
        setZaiko_id(zaiko_id);
        setSouko_id(souko_id);
        setShouhin_id(shouhin_id);
        setLocation_id(location_id);
        setShoumikigen(shoumikigen);
        setSuuryou(suuryou);
        setShukkakigen(shukkakigen);
        setCreated(new java.util.Date());
        setCreatedby(user_id);
        setUpdated(new java.util.Date());
        setUpdatedby(user_id);
        return null;
    }
    public void ejbPostCreate(int zaiko_id, String souko_id,
        String shouhin_id, String location_id, java.util.Date shoumikigen,
        float suuryou, java.util.Date shukkakigen, int user_id) {}
    /**
    * @ejb:create-method view-type="both"
    **/
    public ZaikomeisaiPK ejbCreate(ZaikomeisaiData zd, int zaiko_id,
      int user_id) throws CreateException {
        int zaikomeisai_id = getNextZaikomeisai_id();
        setZaikomeisai_id(zaikomeisai_id);
        setZaiko_id(zaiko_id);
        setSouko_id(zd.getSouko_id());
        setShouhin_id(zd.getShouhin_id());
        setLocation_id(zd.getLocation_id());
        setShoumikigen(zd.getShoumikigen());
        setSuuryou(zd.getSuuryou());
        setShukkakigen(zd.getShukkakigen());
        setCreated(new java.util.Date());
        setCreatedby(user_id);
        setUpdated(new java.util.Date());
        setUpdatedby(user_id);
        return null;
    }
    public void ejbPostCreate(ZaikomeisaiData zd, int zaiko_id, int user_id) {
    }
    /**
    * @ejb:create-method view-type="local"
    **/
    public ZaikomeisaiPK ejbCreate(ZaikomeisaiLocal zm, int zaiko_id,
      int user_id) throws CreateException {
        int zaikomeisai_id = getNextZaikomeisai_id();
        setZaikomeisai_id(zaikomeisai_id);
        setZaiko_id(zm.getZaiko_id());
        setSouko_id(zm.getSouko_id());
        setShouhin_id(zm.getShouhin_id());
        setLocation_id(zm.getLocation_id());
        setShoumikigen(zm.getShoumikigen());
        setSuuryou(zm.getSuuryou());
        setShukkakigen(zm.getShukkakigen());
        setCreated(new java.util.Date());
        setCreatedby(user_id);
        setUpdated(new java.util.Date());
        setUpdatedby(user_id);
        return null;
    }
    public void ejbPostCreate(ZaikomeisaiLocal zm, int zaiko_id, int user_id) {
    }

    public void setEntityContext(EntityContext ctx) {}
    public void unsetEntityContext() {}
    public void ejbRemove() throws javax.ejb.RemoveException {}
    public void ejbActivate() {}
    public void ejbPassivate() {}
    public void ejbLoad() {}
    public void ejbStore() {}

    private int getNextZaikomeisai_id() {
        int id = 0;
        try {
            InitialContext ic = new InitialContext();
            Object objref = ic.lookup("java:comp/env/ejb/KeyGeneratorLocal");
            KeyGeneratorLocalHome kgh = (KeyGeneratorLocalHome)objref;
            KeyGeneratorLocal kg = kgh.create();
            id = kg.getNext("ZS_ZAIKOMEISAI");
        } catch (NamingException ex) {
            Debug.println(ex);
        } catch (CreateException ex) {
            Debug.println(ex);
        }
        return id;
    }

    /**
    * @ejb:interface-method view-type="both"
    **/
    public void addSuuryou(float inc, int user_id) {
        setSuuryou(getSuuryou() + inc);
        setUpdated(new java.util.Date());
        setUpdatedby(user_id);
    }

    /**
    * @ejb:interface-method view-type="both"
    **/
    public void subSuuryou(float dec, int user_id) throws ZaikomeisaiException{
        float suuryou = getSuuryou();
        float orgsuuryou = suuryou;
        suuryou -= dec;
        if ((suuryou < 0) && (suuryou > -0.001f)) {
            //ä€ÇﬂåÎç∑ëŒçÙ
            suuryou = 0;
        }
        if (suuryou < 0) {
            throw new ZaikomeisaiException("suuryou less than zero:zaiko_id=" + getZaiko_id() + " zaikomeisai_id=" + getZaikomeisai_id() + " org=" + orgsuuryou + " dec="+ dec + " suuryou=" + suuryou);
        }
        setSuuryou(suuryou);
        setUpdated(new java.util.Date());
        setUpdatedby(user_id);
    }

    /**
    * @ejb:interface-method view-type="both"
    **/
    public ZaikomeisaiData getZaikomeisaiData() {
        ZaikomeisaiData zaikomeisaiData = new ZaikomeisaiData(
            this.getZaikomeisai_id(),
            this.getZaiko_id(),
            this.getSouko_id(),
            this.getShouhin_id(),
            this.getLocation_id(),
            this.getShoumikigen(),
            this.getSuuryou(),
            this.getShukkakigen(),
            this.getCreated(),
            this.getCreatedby(),
            this.getUpdated(),
            this.getUpdatedby()
        );
        return zaikomeisaiData;
    }

    /**
    * @ejb:interface-method view-type="local"
    **/
    public ZaikoLocal getZaiko() throws NamingException, FinderException {
        ZaikoLocalHome zh = (ZaikoLocalHome)ServiceLocator.getLocalHome(
          "java:comp/env/ejb/ZaikoLocal");
        ZaikoPK pk = new ZaikoPK(getZaiko_id());
        return zh.findByPrimaryKey(pk);
    }

    /**
    * @ejb:interface-method view-type="local"
    **/
    public ShouhinLocal getShouhin() throws NamingException, FinderException {
        ShouhinLocalHome sh = (ShouhinLocalHome)ServiceLocator.getLocalHome(
          "java:comp/env/ejb/ShouhinLocal");
        return sh.findByPrimaryKey(getShouhin_id());
    }

}
