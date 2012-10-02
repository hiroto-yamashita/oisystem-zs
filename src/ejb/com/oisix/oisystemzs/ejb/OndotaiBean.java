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
 * The Entity bean represents Ondotai
 *
 * @author Ryuya Matsunaga
 * @version $Revision: 1.0 $
 *
 * @ejb:bean name="Ondotai"
 *           display-name="Ondotai"
 *           type="CMP"
 *           jndi-name="ejb/Ondotai"
 *           local-jndi-name="ejb/OndotaiLocal"
 *           view-type="both"
 *
 * @ejb:finder signature="Collection findAll()" query="SELECT OBJECT(o) from Ondotai o" result-type-mapping="Local"
 *
 * @ejb.persistence table-name="zm_ondotai"
 *
 * @jboss:persistence pk-constraint="true"
 *
 **/
public abstract class OndotaiBean implements EntityBean {
    private EntityContext entityContext;

    /**
    * @ejb:persistent-field
    * @ejb:pk-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract int getOndotai_id();
    public abstract void setOndotai_id(int ondotai_id);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract String getOndotai();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setOndotai(String ondotai);
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
    public OndotaiPK ejbCreate(int ondotai_id, String ondotai, int user_id)
      throws CreateException {
        setOndotai_id(ondotai_id);
        setOndotai(ondotai);
        setCreated(new java.util.Date());
        setCreatedby(user_id);
        setUpdated(new java.util.Date());
        setUpdatedby(user_id);
        return null;
    }
    public void ejbPostCreate(int ondotai_id, String ondotai, int user_id) {}

    /**
    * @ejb:create-method view-type="both"
    **/
    public OndotaiPK ejbCreate(OndotaiData data)
      throws CreateException {
        setOndotai_id(data.getOndotai_id());
        setOndotai(data.getOndotai());
        setCreated(data.getCreated());
        setCreatedby(data.getCreatedby());
        setUpdated(data.getUpdated());
        setUpdatedby(data.getUpdatedby());
        return null;
    }
    public void ejbPostCreate(OndotaiData data) {}

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
    public OndotaiData getOndotaiData() {
        OndotaiData data = new OndotaiData(
          this.getOndotai_id(),
          this.getOndotai(),
          this.getCreated(),
          this.getCreatedby(),
          this.getUpdated(),
          this.getUpdatedby()
        );
        return data;
    }

}
