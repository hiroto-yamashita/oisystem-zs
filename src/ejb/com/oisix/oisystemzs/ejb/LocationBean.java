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
 * The Entity bean represents Location
 *
 * @author Ryuya Matsunaga
 * @version $Revision: 1.0 $
 *
 * @ejb:bean name="Location"
 *           display-name="Location"
 *           type="CMP"
 *           primkey-field="location_id"
 *           jndi-name="ejb/Location"
 *           local-jndi-name="ejb/LocationLocal"
 *           view-type="both"
 *
 * @ejb:pk class="java.lang.String"
 *
 * @ejb:ejb-ref ejb-name="Souko"
 *              view-type="local"
 * @ejb:finder signature="Collection findAll()" query="SELECT OBJECT(l) from Location l" result-type-mapping="Local"
 * @ejb:finder signature="Collection findBySouko_id(java.lang.String souko_id)" query="SELECT OBJECT(l) from Location l WHERE l.souko_id = ?1" result-type-mapping="Local"
 *
 * @ejb.persistence table-name="zm_location"
 *
 * @jboss:persistence pk-constraint="true"
 *
 **/
public abstract class LocationBean implements EntityBean {
    private EntityContext entityContext;

    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract String getLocation_id();
    public abstract void setLocation_id(String location_id);
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
    public abstract int getYuusenjuni();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setYuusenjuni(int yuusenjuni);
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
    public String ejbCreate(String location_id, String souko_id,
        int yuusenjuni, int user_id)
      throws CreateException {
        setLocation_id(location_id);
        setSouko_id(souko_id);
        setYuusenjuni(yuusenjuni);
        setCreated(new java.util.Date());
        setCreatedby(user_id);
        setUpdated(new java.util.Date());
        setUpdatedby(user_id);
        return null;
    }
    public void ejbPostCreate(String location_id, String souko_id,
        int yuusenjuni, int user_id) {}

    /**
    * @ejb:create-method view-type="both"
    **/
    public String ejbCreate(LocationData data)
      throws CreateException {
        setLocation_id(getLocation_id());
        setSouko_id(getSouko_id());
        setYuusenjuni(getYuusenjuni());
        setCreated(data.getCreated());
        setCreatedby(data.getCreatedby());
        setUpdated(data.getUpdated());
        setUpdatedby(data.getUpdatedby());
        return null;
    }
    public void ejbPostCreate(LocationData data) {}

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
    public LocationData getLocationData() {
        LocationData data = new LocationData(
          this.getLocation_id(),
          this.getSouko_id(),
          this.getYuusenjuni(),
          this.getCreated(),
          this.getCreatedby(),
          this.getUpdated(),
          this.getUpdatedby()
        );
        return data;
    }

    /**
    * @ejb:interface-method view-type="local"
    **/
    public SoukoLocal getSouko()
      throws NamingException, FinderException {
        SoukoLocalHome slh = (SoukoLocalHome)ServiceLocator.getLocalHome(
          "java:comp/env/ejb/SoukoLocal");
        return slh.findByPrimaryKey(getSouko_id());
    }

}
