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
 * The Entity bean represents Tani
 *
 * @author Ryuya Matsunaga
 * @version $Revision: 1.0 $
 *
 * @ejb:bean name="Tani"
 *           display-name="Tani"
 *           type="CMP"
 *           jndi-name="ejb/Tani"
 *           local-jndi-name="ejb/TaniLocal"
 *           view-type="both"
 *
 * @ejb:finder signature="Collection findAll()" query="SELECT OBJECT(t) from Tani t" result-type-mapping="Local"
 *
 * @ejb.persistence table-name="zm_tani"
 *
 * @jboss:persistence pk-constraint="true"
 *
 **/
public abstract class TaniBean implements EntityBean {
    private EntityContext entityContext;

    /**
    * @ejb:persistent-field
    * @ejb:pk-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract int getTani_id();
    public abstract void setTani_id(int tani_id);
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
    public TaniPK ejbCreate(int tani_id, String tani, int user_id)
      throws CreateException {
        setTani_id(tani_id);
        setTani(tani);
        setCreated(new java.util.Date());
        setCreatedby(user_id);
        setUpdated(new java.util.Date());
        setUpdatedby(user_id);
        return null;
    }
    public void ejbPostCreate(int tani_id, String tani, int user_id) {}

    /**
    * @ejb:create-method view-type="both"
    **/
    public TaniPK ejbCreate(TaniData data)
      throws CreateException {
        setTani_id(data.getTani_id());
        setTani(data.getTani());
        setCreated(data.getCreated());
        setCreatedby(data.getCreatedby());
        setUpdated(data.getUpdated());
        setUpdatedby(data.getUpdatedby());
        return null;
    }
    public void ejbPostCreate(TaniData data) {}

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
    public TaniData getTaniData() {
        TaniData data = new TaniData(
          this.getTani_id(),
          this.getTani(),
          this.getCreated(),
          this.getCreatedby(),
          this.getUpdated(),
          this.getUpdatedby()
        );
        return data;
    }

}
