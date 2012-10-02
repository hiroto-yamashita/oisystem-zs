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
 * The Entity bean represents Shukkayoteishubetsu
 *
 * @author Ryuya Matsunaga
 * @version $Revision: 1.0 $
 *
 * @ejb:bean name="Shukkayoteishubetsu"
 *           display-name="Shukkayoteishubetsu"
 *           type="CMP"
 *           jndi-name="ejb/Shukkayoteishubetsu"
 *           local-jndi-name="ejb/ShukkayoteishubetsuLocal"
 *           view-type="both"
 *
 * @ejb:finder signature="Collection findAll()" query="SELECT OBJECT(s) from Shukkayoteishubetsu s" result-type-mapping="Local"
 *
 * @ejb.persistence table-name="zm_shukkayoteishubetsu"
 *
 * @jboss:persistence pk-constraint="true"
 *
 **/
public abstract class ShukkayoteishubetsuBean implements EntityBean {
    private EntityContext entityContext;

    /**
    * @ejb:persistent-field
    * @ejb:pk-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract int getShukkayoteishubetsu_id();
    public abstract void setShukkayoteishubetsu_id(int shukkayoteishubetsu_id);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract String getShukkayoteishubetsu();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setShukkayoteishubetsu(String shukkayoteishubetsu);
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
    public ShukkayoteishubetsuPK ejbCreate(int shukkayoteishubetsu_id,
      String shukkayoteishubetsu, int user_id)
      throws CreateException {
        setShukkayoteishubetsu_id(shukkayoteishubetsu_id);
        setShukkayoteishubetsu(shukkayoteishubetsu);
        setCreated(new java.util.Date());
        setCreatedby(user_id);
        setUpdated(new java.util.Date());
        setUpdatedby(user_id);
        return null;
    }
    public void ejbPostCreate(int shukkayoteishubetsu_id,
      String shukkayoteishubetsu, int user_id) {}

    /**
    * @ejb:create-method view-type="both"
    **/
    public ShukkayoteishubetsuPK ejbCreate(ShukkayoteishubetsuData data)
      throws CreateException {
        setShukkayoteishubetsu_id(data.getShukkayoteishubetsu_id());
        setShukkayoteishubetsu(data.getShukkayoteishubetsu());
        setCreated(data.getCreated());
        setCreatedby(data.getCreatedby());
        setUpdated(data.getUpdated());
        setUpdatedby(data.getUpdatedby());
        return null;
    }
    public void ejbPostCreate(ShukkayoteishubetsuData data) {}

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
    public ShukkayoteishubetsuData getShukkayoteishubetsuData() {
        ShukkayoteishubetsuData data = new ShukkayoteishubetsuData(
          this.getShukkayoteishubetsu_id(),
          this.getShukkayoteishubetsu(),
          this.getCreated(),
          this.getCreatedby(),
          this.getUpdated(),
          this.getUpdatedby()
        );
        return data;
    }
}
