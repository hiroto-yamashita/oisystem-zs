package com.oisix.oisystemzs.ejb;

import com.oisix.oisystemfr.ejb.ServiceLocator;
import com.oisix.oisystemfr.Debug;
import com.oisix.oisystemfr.ejb.KeyGeneratorLocal;
import com.oisix.oisystemfr.ejb.KeyGeneratorLocalHome;
import java.util.Collection;
import java.util.HashMap;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.CreateException;
import javax.ejb.FinderException;
import javax.naming.NamingException;
import javax.naming.InitialContext;

/**
 * The Entity bean represents Souko
 *
 * @author Ryuya Matsunaga
 * @version $Revision: 1.0 $
 *
 * @ejb:bean name="Souko"
 *           display-name="Souko"
 *           type="CMP"
 *           primkey-field="souko_id"
 *           jndi-name="ejb/Souko"
 *           local-jndi-name="ejb/SoukoLocal"
 *           view-type="both"
 *
 * @ejb:pk class="java.lang.String"
 *
 * @ejb:ejb-ref ejb-name="Nouhinsaki"
 *              view-type="local"
 * @ejb:ejb-ref ejb-name="Location"
 *              view-type="local"
 * @ejb:finder signature="Collection findAll()" query="SELECT OBJECT(s) from Souko s" result-type-mapping="Local"
 *
 * @ejb.persistence table-name="zm_souko"
 *
 * @jboss:persistence pk-constraint="true"
 *
 **/
public abstract class SoukoBean implements EntityBean {
    private EntityContext entityContext;

    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract String getSouko_id();
    public abstract void setSouko_id(String souko_id);
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
    public abstract String getNouhinsaki_id();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setNouhinsaki_id(String nouhinsaki_id);
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
    public String ejbCreate(String souko_id, String name,
        String furigana, String yuubin, String addr, String tel,
        String fax, String nouhinsaki_id, int user_id)
      throws CreateException {
        setSouko_id(souko_id);
        setName(name);
        setFurigana(furigana);
        setYuubin(yuubin);
        setAddr(addr);
        setTel(tel);
        setFax(fax);
        setNouhinsaki_id(nouhinsaki_id);
        setCreated(new java.util.Date());
//        setCreated(DateUtil.getDate());
        setCreatedby(user_id);
        setUpdated(new java.util.Date());
//        setUpdated(DateUtil.getDate());
        setUpdatedby(user_id);
        return null;
    }
    public void ejbPostCreate(String souko_id, String name,
        String furigana, String yuubin, String addr, String tel,
        String fax, String nouhinsaki_id, int user_id) {}

    /**
    * @ejb:create-method view-type="both"
    **/
    public String ejbCreate(SoukoData data)
      throws CreateException {
        setSouko_id(data.getSouko_id());
        setName(data.getName());
        setFurigana(data.getFurigana());
        setYuubin(data.getYuubin());
        setAddr(data.getAddr());
        setTel(data.getTel());
        setFax(data.getFax());
        setNouhinsaki_id(data.getNouhinsaki_id());
        setCreated(data.getCreated());
        setCreatedby(data.getCreatedby());
        setUpdated(data.getUpdated());
        setUpdatedby(data.getUpdatedby());
        return null;
    }
    public void ejbPostCreate(SoukoData data) {}

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
    public SoukoData getSoukoData() {
        SoukoData data = new SoukoData(
          this.getSouko_id(),
          this.getName(),
          this.getFurigana(),
          this.getYuubin(),
          this.getAddr(),
          this.getTel(),
          this.getFax(),
          this.getNouhinsaki_id(),
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
    public NouhinsakiLocal getNouhinsaki()
      throws NamingException, FinderException {
        NouhinsakiLocalHome nlh =
          (NouhinsakiLocalHome)ServiceLocator.getLocalHome(
          "java:comp/env/ejb/NouhinsakiLocal");
        return nlh.findByPrimaryKey(getNouhinsaki_id());
    }

    /**
    * @ejb:interface-method view-type="both"
    **/
    public Collection getLocation()
      throws NamingException, FinderException {
        LocationLocalHome llh = (LocationLocalHome)
          ServiceLocator.getLocalHome(
          "java:comp/env/ejb/LocationLocal");
        return llh.findBySouko_id(getSouko_id());
    }
}
