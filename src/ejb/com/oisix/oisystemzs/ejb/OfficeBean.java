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
 * The Entity bean represents Office
 *
 * @author Ryuya Matsunaga
 * @version $Revision: 1.0 $
 *
 * @ejb:bean name="Office"
 *           display-name="Office"
 *           type="CMP"
 *           primkey-field="office_id"
 *           jndi-name="ejb/Office"
 *           local-jndi-name="ejb/OfficeLocal"
 *           view-type="both"
 *
 * @ejb:pk class="java.lang.String"
 *
 * @ejb:finder signature="Collection findAll()" query="SELECT OBJECT(o) from Office o" result-type-mapping="Local"
 *
 * @ejb.persistence table-name="zm_office"
 *
 * @jboss:persistence pk-constraint="true"
 *
 **/
public abstract class OfficeBean implements EntityBean {
    private EntityContext entityContext;

    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract String getOffice_id();
    public abstract void setOffice_id(String office_id);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract String getName();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setName(String name);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract String getFurigana();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setFurigana(String furigana);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract String getYuubin();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setYuubin(String yuubin);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract String getAddr();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setAddr(String addr);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract String getTel();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setTel(String tel);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract String getFax();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setFax(String fax);
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
    public String ejbCreate(String office_id, String name, String furigana,
        String yuubin, String addr, String tel, String fax, int user_id)
      throws CreateException {
        setOffice_id(office_id);
        setName(name);
        setFurigana(furigana);
        setYuubin(yuubin);
        setAddr(addr);
        setTel(tel);
        setFax(fax);
        setCreated(new java.util.Date());
        setCreatedby(user_id);
        setUpdated(new java.util.Date());
        setUpdatedby(user_id);
        return null;
    }
    public void ejbPostCreate(String office_id, String name, String furigana,
        String yuubin, String addr, String tel, String fax, int user_id) {}

    /**
    * @ejb:create-method view-type="both"
    **/
    public String ejbCreate(OfficeData data)
      throws CreateException {
        setOffice_id(data.getOffice_id());
        setName(data.getName());
        setFurigana(data.getFurigana());
        setYuubin(data.getYuubin());
        setAddr(data.getAddr());
        setTel(data.getTel());
        setFax(data.getFax());
        setCreated(data.getCreated());
        setCreatedby(data.getCreatedby());
        setUpdated(data.getUpdated());
        setUpdatedby(data.getUpdatedby());
        return null;
    }
    public void ejbPostCreate(OfficeData data) {}

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
    public OfficeData getOfficeData() {
        OfficeData data = new OfficeData(
          this.getOffice_id(),
          this.getName(),
          this.getFurigana(),
          this.getYuubin(),
          this.getAddr(),
          this.getTel(),
          this.getFax(),
          this.getCreated(),
          this.getCreatedby(),
          this.getUpdated(),
          this.getUpdatedby()
        );
        return data;
    }
}
