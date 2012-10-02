package com.oisix.oisystemzs.ejb;

import com.oisix.oisystemfr.Debug;
import com.oisix.oisystemfr.ejb.KeyGeneratorLocal;
import com.oisix.oisystemfr.ejb.KeyGeneratorLocalHome;
import java.util.HashMap;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.CreateException;
import javax.naming.NamingException;
import javax.naming.InitialContext;

/**
 * The Entity bean represents Shukkokubun
 *
 * @author Ryuya Matsunaga
 * @version $Revision: 1.0 $
 *
 * @ejb:bean name="Shukkokubun"
 *           display-name="Shukkokubun"
 *           type="CMP"
 *           jndi-name="ejb/Shukkokubun"
 *           local-jndi-name="ejb/ShukkokubunLocal"
 *           view-type="both"
 *
 * @ejb:finder signature="Collection findAll()" query="SELECT OBJECT(s) from Shukkokubun s" result-type-mapping="Local"
 *
 * @ejb.persistence table-name="zm_shukkokubun"
 *
 * @jboss:persistence pk-constraint="true"
 *
 **/
public abstract class ShukkokubunBean implements EntityBean {
    private EntityContext entityContext;

    /**
    * @ejb:persistent-field
    * @ejb:pk-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract int getShukkokubun_id();
    public abstract void setShukkokubun_id(int shukkokubun_id);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract String getShukkokubun();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setShukkokubun(String shukkokubun);
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
    public ShukkokubunPK ejbCreate(int shukkokubun_id,
      String shukkokubun, int user_id)
      throws CreateException {
        setShukkokubun_id(shukkokubun_id);
        setShukkokubun(shukkokubun);
        setCreated(new java.util.Date());
        setCreatedby(user_id);
        setUpdated(new java.util.Date());
        setUpdatedby(user_id);
        return null;
    }
    public void ejbPostCreate(int shukkokubun_id,
      String shukkokubun, int user_id) {}

    /**
    * @ejb:create-method view-type="both"
    **/
    public ShukkokubunPK ejbCreate(ShukkokubunData data)
      throws CreateException {
        setShukkokubun_id(data.getShukkokubun_id());
        setShukkokubun(data.getShukkokubun());
        setCreated(data.getCreated());
        setCreatedby(data.getCreatedby());
        setUpdated(data.getUpdated());
        setUpdatedby(data.getUpdatedby());
        return null;
    }
    public void ejbPostCreate(ShukkokubunData data) {}

    public void setEntityContext(EntityContext ctx) {}
    public void unsetEntityContext() {}
    public void ejbRemove() throws javax.ejb.RemoveException {}
    public void ejbActivate() {}
    public void ejbPassivate() {}
    public void ejbLoad() {}
    public void ejbStore() {}

    /**
    * @ejb:interface-method view-type="both"
    **/
    public ShukkokubunData getShukkokubunData() {
        ShukkokubunData data = new ShukkokubunData(
          this.getShukkokubun_id(),
          this.getShukkokubun(),
          this.getCreated(),
          this.getCreatedby(),
          this.getUpdated(),
          this.getUpdatedby()
        );
        return data;
    }
}
