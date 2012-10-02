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
 * The Entity bean represents Touchaku
 *
 * @author Ryuya Matsunaga
 * @version $Revision: 1.0 $
 *
 * @ejb:bean name="Touchaku"
 *           display-name="Touchaku"
 *           type="CMP"
 *           jndi-name="ejb/Touchaku"
 *           local-jndi-name="ejb/TouchakuLocal"
 *           view-type="both"
 *
 * @ejb:finder signature="Collection findAll()" query="SELECT OBJECT(t) from Touchaku t" result-type-mapping="Local"
 *
 * @ejb.persistence table-name="zm_touchaku"
 *
 * @jboss:persistence pk-constraint="true"
 *
 **/
public abstract class TouchakuBean implements EntityBean {
    private EntityContext entityContext;

    /**
    * @ejb:persistent-field
    * @ejb:pk-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract int getTouchaku_id();
    public abstract void setTouchaku_id(int touchaku_id);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract String getTouchaku();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setTouchaku(String touchaku);
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
    public TouchakuPK ejbCreate(int touchaku_id, String touchaku, int user_id)
      throws CreateException {
        setTouchaku_id(touchaku_id);
        setTouchaku(touchaku);
        setCreated(new java.util.Date());
        setCreatedby(user_id);
        setUpdated(new java.util.Date());
        setUpdatedby(user_id);
        return null;
    }
    public void ejbPostCreate(int touchaku_id, String touchaku, int user_id) {}

    /**
    * @ejb:create-method view-type="both"
    **/
    public TouchakuPK ejbCreate(TouchakuData data)
      throws CreateException {
        setTouchaku_id(data.getTouchaku_id());
        setTouchaku(data.getTouchaku());
        setCreated(data.getCreated());
        setCreatedby(data.getCreatedby());
        setUpdated(data.getUpdated());
        setUpdatedby(data.getUpdatedby());
        return null;
    }
    public void ejbPostCreate(TouchakuData data) {}

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
    public TouchakuData getTouchakuData() {
        TouchakuData data = new TouchakuData(
          this.getTouchaku_id(),
          this.getTouchaku(),
          this.getCreated(),
          this.getCreatedby(),
          this.getUpdated(),
          this.getUpdatedby()
        );
        return data;
    }
}
