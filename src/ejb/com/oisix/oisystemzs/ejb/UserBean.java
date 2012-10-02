package com.oisix.oisystemzs.ejb;

import com.oisix.oisystemfr.ejb.ServiceLocator;
import com.oisix.oisystemfr.Debug;
import com.oisix.oisystemfr.ejb.KeyGeneratorLocal;
import com.oisix.oisystemfr.ejb.KeyGeneratorLocalHome;
import java.util.HashMap;
import java.text.SimpleDateFormat;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.CreateException;
import javax.ejb.FinderException;
import javax.naming.NamingException;
import javax.naming.InitialContext;

/**
 * The Entity bean represents User
 *
 * @author Ryuya Matsunaga
 * @version $Revision: 1.0 $
 *
 * @ejb:bean name="User"
 *           display-name="User"
 *           type="CMP"
 *           jndi-name="ejb/User"
 *           local-jndi-name="ejb/UserLocal"
 *           view-type="both"
 *
 * @ejb:ejb-ref ejb-name="KeyGenerator"
 *              view-type="local"
 *
 * @ejb:ejb-ref ejb-name="Souko"
 *              view-type="local"
 * @ejb:ejb-ref ejb-name="Office"
 *              view-type="local"
 * @ejb:finder signature="Collection findByPassword(java.lang.String password)" query="SELECT OBJECT(u) from User u WHERE u.password = ?1" result-type-mapping="Local"
 *
 * @ejb.persistence table-name="zm_user"
 *
 * @jboss:persistence pk-constraint="true"
 *
 **/
public abstract class UserBean implements EntityBean {
    private EntityContext entityContext;

    /**
    * @ejb:persistent-field
    * @ejb:pk-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract int getUser_id();
    public abstract void setUser_id(int user_id);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract String getPassword();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setPassword(String password);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract String getName1();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setName1(String name1);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract String getName2();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setName2(String name2);
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
    public abstract String getOffice_id();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setOffice_id(String office_id);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract int getPriv();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setPriv(int priv);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract String getBusho();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setBusho(String busho);
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
    public UserPK ejbCreate(String password, String name1,
        String name2, String souko_id, String office_id,
        int priv, String busho, int user_id)
      throws CreateException {
        int next_user_id = getNextUser_id();
        setUser_id(next_user_id);
        setPassword(password);
        setName1(name1);
        setName2(name2);
        setSouko_id(souko_id);
        setOffice_id(office_id);
        setPriv(priv);
        setBusho(busho);
        setCreated(new java.util.Date());
        setCreatedby(user_id);
        setUpdated(new java.util.Date());
        setUpdatedby(user_id);
        return null;
    }
    public void ejbPostCreate(String password, String name1,
        String name2, String souko_id, String office_id,
        int priv, String busho, int user_id) {}

    /**
    * @ejb:create-method view-type="both"
    **/
    public UserPK ejbCreate(UserData data)
      throws CreateException {
        setUser_id(data.getUser_id());
        setPassword(data.getPassword());
        setName1(data.getName1());
        setName2(data.getName2());
        setSouko_id(data.getSouko_id());
        setOffice_id(data.getOffice_id());
        setPriv(data.getPriv());
        setBusho(data.getBusho());
        setCreated(data.getCreated());
        setCreatedby(data.getCreatedby());
        setUpdated(data.getUpdated());
        setUpdatedby(data.getUpdatedby());
        return null;
    }
    public void ejbPostCreate(UserData data) {}

    public void setEntityContext(EntityContext ctx) {}
    public void unsetEntityContext() {}
    public void ejbRemove() throws javax.ejb.RemoveException {}
    public void ejbActivate() {}
    public void ejbPassivate() {}
    public void ejbLoad() {}
    public void ejbStore() {}

    private int getNextUser_id() {
        int id = 0;
        try {
            InitialContext ic = new InitialContext();
            Object objref = ic.lookup("java:comp/env/ejb/KeyGeneratorLocal");
            KeyGeneratorLocalHome kgh = (KeyGeneratorLocalHome)objref;
            KeyGeneratorLocal kg = kgh.create();
            id = kg.getNext("ZS_USER");
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
    public UserData getUserData() {
        UserData data = new UserData(
          this.getUser_id(),
          this.getPassword(),
          this.getName1(),
          this.getName2(),
          this.getSouko_id(),
          this.getOffice_id(),
          this.getPriv(),
          this.getBusho(),
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

    /**
    * @ejb:interface-method view-type="local"
    **/
    public OfficeLocal getOffice()
      throws NamingException, FinderException {
        OfficeLocalHome olh = (OfficeLocalHome)ServiceLocator.getLocalHome(
          "java:comp/env/ejb/OfficeLocal");
        return olh.findByPrimaryKey(getOffice_id());
    }

}
