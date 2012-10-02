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
 * The Entity bean represents Shiiresaki
 *
 * @author Ryuya Matsunaga
 * @version $Revision: 1.0 $
 *
 * @ejb:bean name="Shiiresaki"
 *           display-name="Shiiresaki"
 *           type="CMP"
 *           primkey-field="shiiresaki_id"
 *           jndi-name="ejb/Shiiresaki"
 *           local-jndi-name="ejb/ShiiresakiLocal"
 *           view-type="both"
 *
 * @ejb:pk class="java.lang.String"
 *
 * @ejb:finder signature="Collection findAll()" query="SELECT OBJECT(s) from Shiiresaki s" result-type-mapping="Local"
 *
 * @ejb.persistence table-name="zm_shiiresaki"
 *
 * @jboss:persistence pk-constraint="true"
 *
 **/
public abstract class ShiiresakiBean implements EntityBean {
    private EntityContext entityContext;

    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract String getShiiresaki_id();
    public abstract void setShiiresaki_id(String shiiresaki_id);
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
    public abstract String getFurigana1();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setFurigana1(String furigana1);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract String getFurigana2();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setFurigana2(String furigana2);
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
    public abstract String getTantoushaname1();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setTantoushaname1(String tantoushaname1);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract String getTantoushaname2();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setTantoushaname2(String tantoushaname2);
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
    public String ejbCreate(String shiiresaki_id, String name,
        String furigana, String furigana1, String furigana2,
        String yuubin, String addr, String tel, String fax,
        String tantoushaname1, String tantoushaname2, int user_id)
      throws CreateException {
        setShiiresaki_id(shiiresaki_id);
        setName(name);
        setFurigana(furigana);
        setFurigana1(furigana1);
        setFurigana2(furigana2);
        setYuubin(yuubin);
        setAddr(addr);
        setTel(tel);
        setFax(fax);
        setTantoushaname1(tantoushaname1);
        setTantoushaname2(tantoushaname2);
        setCreated(new java.util.Date());
        setCreatedby(user_id);
        setUpdated(new java.util.Date());
        setUpdatedby(user_id);
        return null;
    }
    public void ejbPostCreate(String shiiresaki_id, String name,
        String furigana, String furigana1, String furigana2,
        String yuubin, String addr, String tel, String fax,
        String tantoushaname1, String tantoushaname2, int user_id) {}

    /**
    * @ejb:create-method view-type="both"
    **/
    public String ejbCreate(ShiiresakiData data)
      throws CreateException {
        setShiiresaki_id(data.getShiiresaki_id());
        setName(data.getName());
        setFurigana(data.getFurigana());
        setFurigana1(data.getFurigana1());
        setFurigana2(data.getFurigana2());
        setYuubin(data.getYuubin());
        setAddr(data.getAddr());
        setTel(data.getTel());
        setFax(data.getFax());
        setTantoushaname1(data.getTantoushaname1());
        setTantoushaname2(data.getTantoushaname2());
        setCreated(data.getCreated());
        setCreatedby(data.getCreatedby());
        setUpdated(data.getUpdated());
        setUpdatedby(data.getUpdatedby());
        return null;
    }
    public void ejbPostCreate(ShiiresakiData data) {}

    public void setEntityContext(EntityContext ctx) {
        entityContext = ctx;
    }
    public void unsetEntityContext() {}
    public void ejbRemove() throws javax.ejb.RemoveException {}
    public void ejbActivate() {}
    public void ejbPassivate() {}
    public void ejbLoad() {}
    public void ejbStore() {}

    /**
    * @ejb:interface-method view-type="both"
    **/
    public ShiiresakiData getShiiresakiData() {
        ShiiresakiData data = new ShiiresakiData(
          this.getShiiresaki_id(),
          this.getName(),
          this.getFurigana(),
          this.getFurigana1(),
          this.getFurigana2(),
          this.getYuubin(),
          this.getAddr(),
          this.getTel(),
          this.getFax(),
          this.getTantoushaname1(),
          this.getTantoushaname2(),
          this.getCreated(),
          this.getCreatedby(),
          this.getUpdated(),
          this.getUpdatedby()
        );
        return data;
    }

}
