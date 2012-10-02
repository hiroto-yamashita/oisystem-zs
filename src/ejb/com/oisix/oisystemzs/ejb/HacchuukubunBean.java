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
 * The Entity bean represents Hacchuukubun
 *
 * @author Ryuya Matsunaga
 * @version $Revision: 1.0 $
 *
 * @ejb:bean name="Hacchuukubun"
 *           display-name="Hacchuukubun"
 *           type="CMP"
 *           jndi-name="ejb/Hacchuukubun"
 *           local-jndi-name="ejb/HacchuukubunLocal"
 *           view-type="both"
 *
 * @ejb:finder signature="Collection findAll()" query="SELECT OBJECT(h) from Hacchuukubun h" result-type-mapping="Local"
 *
 * @ejb.persistence table-name="zm_hacchuukubun"
 *
 * @jboss:persistence pk-constraint="true"
 *
 **/
public abstract class HacchuukubunBean implements EntityBean {
    private EntityContext entityContext;

    /**
    * @ejb:persistent-field
    * @ejb:pk-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract int getHacchuukubun_id();
    public abstract void setHacchuukubun_id(int hacchuukubun_id);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract String getHacchuukubun();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setHacchuukubun(String hacchuukubun);
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
    public HacchuukubunPK ejbCreate(int hacchuukubun_id,
        String hacchuukubun, int user_id)
      throws CreateException {
        setHacchuukubun_id(hacchuukubun_id);
        setHacchuukubun(hacchuukubun);
        setCreated(new java.util.Date());
        setCreatedby(user_id);
        setUpdated(new java.util.Date());
        setUpdatedby(user_id);
        return null;
    }
    public void ejbPostCreate(int hacchuukubun_id,
        String hacchuukubun, int user_id) {}

    /**
    * @ejb:create-method view-type="both"
    **/
    public HacchuukubunPK ejbCreate(HacchuukubunData data)
      throws CreateException {
        setHacchuukubun_id(data.getHacchuukubun_id());
        setHacchuukubun(data.getHacchuukubun());
        setCreated(data.getCreated());
        setCreatedby(data.getCreatedby());
        setUpdated(data.getUpdated());
        setUpdatedby(data.getUpdatedby());
        return null;
    }
    public void ejbPostCreate(HacchuukubunData data) {}

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
    public HacchuukubunData getHacchuukubunData() {
        HacchuukubunData data = new HacchuukubunData(
          getHacchuukubun_id(),
          getHacchuukubun(),
          getCreated(),
          getCreatedby(),
          getUpdated(),
          getUpdatedby()
        );
        return data;
    }
}
