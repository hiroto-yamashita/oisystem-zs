package com.oisix.oisystemzs.ejb;

import com.oisix.oisystemfr.ejb.ServiceLocator;
import com.oisix.oisystemfr.Debug;
import com.oisix.oisystemfr.ejb.KeyGeneratorLocal;
import com.oisix.oisystemfr.ejb.KeyGeneratorLocalHome;
import com.oisix.oisystemzs.Names;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.text.SimpleDateFormat;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.CreateException;
import javax.ejb.FinderException;
import javax.naming.NamingException;
import javax.naming.InitialContext;

/**
 * The Entity bean represents Hacchuu
 *
 * @author Ryuya Matsunaga
 * @version $Revision: 1.0 $
 *
 * @ejb:bean name="Hacchuu"
 *           display-name="Hacchuu"
 *           type="CMP"
 *           jndi-name="ejb/Hacchuu"
 *           local-jndi-name="ejb/HacchuuLocal"
 *           view-type="both"
 *
 * @ejb:ejb-ref ejb-name="KeyGenerator"
 *              view-type="local"
 * @ejb:ejb-ref ejb-name="Nyuukayoteimeisai"
 *              view-type="local"
 * @ejb:finder signature="HacchuuLocal findByHacchuu_bg(java.lang.String hacchuu_bg)" query="SELECT OBJECT(h) from Hacchuu h WHERE h.hacchuu_bg = ?1" result-type-mapping="Local"
 *
 * @ejb.persistence table-name="zt_hacchuu"
 *
 * @jboss:persistence pk-constraint="true"
 *
 **/
public abstract class HacchuuBean implements EntityBean {
    private EntityContext entityContext;

    /**
    * @ejb:persistent-field
    * @ejb:pk-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract int getHacchuu_id();
    public abstract void setHacchuu_id(int hacchuu_id);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract String getHacchuu_bg();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setHacchuu_bg(String hacchuu_bg);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract String getShiiresaki_id();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setShiiresaki_id(String shiiresaki_id);
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
    public abstract java.util.Date getHacchuu_date();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setHacchuu_date(java.util.Date hacchuu_date);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract int getFormat();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setFormat(int format);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract String getBikou();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setBikou(String bikou);
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
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract int getSakujo_flg();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setSakujo_flg(int updatedby);
    /**
    * @ejb:create-method view-type="both"
    **/
    public HacchuuPK ejbCreate(String shiiresaki_id, String nouhinsaki_id,
        java.util.Date hacchuu_date, int format, String bikou, int user_id,
        int sakujo_flg)
      throws CreateException {
        int hacchuu_id = getNextHacchuu_id();
        String hacchuu_bg = getNewHacchuu_bg(hacchuu_id);
        setHacchuu_id(hacchuu_id);
        setHacchuu_bg(hacchuu_bg);
        setShiiresaki_id(shiiresaki_id);
        setNouhinsaki_id(nouhinsaki_id);
        setHacchuu_date(hacchuu_date);
        setFormat(format);
        setBikou(bikou);
        setCreated(new java.util.Date());
        setCreatedby(user_id);
        setUpdated(new java.util.Date());
        setUpdatedby(user_id);
        setSakujo_flg(sakujo_flg);
        return null;
    }
    public void ejbPostCreate(String shiiresaki_id, String nouhinsaki_id,
        java.util.Date hacchuu_date, int format, String bikou, int user_id,
        int sakujo_flg) {}
    /**
    * @ejb:create-method view-type="both"
    **/
    public HacchuuPK ejbCreate(String shiiresaki_id, String nouhinsaki_id,
        java.util.Date hacchuu_date, int format, String bikou, int user_id)
      throws CreateException {
        int hacchuu_id = getNextHacchuu_id();
        String hacchuu_bg = getNewHacchuu_bg(hacchuu_id);
        setHacchuu_id(hacchuu_id);
        setHacchuu_bg(hacchuu_bg);
        setShiiresaki_id(shiiresaki_id);
        setNouhinsaki_id(nouhinsaki_id);
        setHacchuu_date(hacchuu_date);
        setFormat(format);
        setBikou(bikou);
        setCreated(new java.util.Date());
        setCreatedby(user_id);
        setUpdated(new java.util.Date());
        setUpdatedby(user_id);
        setSakujo_flg(Names.OFF);
        return null;
    }
    public void ejbPostCreate(String shiiresaki_id, String nouhinsaki_id,
        java.util.Date hacchuu_date, int format, String bikou, int user_id) {}
    public void setEntityContext(EntityContext ctx) {}
    public void unsetEntityContext() {}
    public void ejbRemove() throws javax.ejb.RemoveException {}
    public void ejbActivate() {}
    public void ejbPassivate() {}
    public void ejbLoad() {}
    public void ejbStore() {}

    private int getNextHacchuu_id() {
        int id = 0;
        try {
            InitialContext ic = new InitialContext();
            Object objref = ic.lookup("java:comp/env/ejb/KeyGeneratorLocal");
            KeyGeneratorLocalHome kgh = (KeyGeneratorLocalHome)objref;
            KeyGeneratorLocal kg = kgh.create();
            id = kg.getNext("ZS_HACCHUU");
        } catch (NamingException ex) {
            Debug.println(ex);
        } catch (CreateException ex) {
            Debug.println(ex);
        }
        return id;
    }

    private String getNewHacchuu_bg(int hacchuu_id) {
        String bg = "HC-";
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
        bg += sdf.format(new java.util.Date()) + "-";
        String strHacchuu_id = "0000" + String.valueOf(hacchuu_id);
        int start = strHacchuu_id.length() - 4;
        bg += strHacchuu_id.substring(start);
        return bg;
    }

    /**
    * @ejb:interface-method view-type="local"
    **/
    public Collection getNyuukayoteimeisai()
      throws NamingException, FinderException {
        NyuukayoteimeisaiLocalHome nh = (NyuukayoteimeisaiLocalHome)
          ServiceLocator.getLocalHome(
          "java:comp/env/ejb/NyuukayoteimeisaiLocal");
        return nh.findByHacchuu_bg(getHacchuu_bg());
    }

    /**
    * @ejb:interface-method view-type="both"
    * î≠íçÇçÌèúÇ∑ÇÈÅBä÷òAÇ∑ÇÈì¸â◊ó\íËñæç◊ÇÕì¸â◊çœÇ›Ç∆Ç»ÇÈÅB
    **/
    public void sakujo(int user_id) throws NamingException, FinderException {
        setSakujo_flg(Names.ON);
        setUpdatedby(user_id);
        setUpdated(new java.util.Date());
        Collection meisais = getNyuukayoteimeisai();
        Iterator miter = meisais.iterator();
        while (miter.hasNext()) {
            NyuukayoteimeisaiLocal meisai =
              (NyuukayoteimeisaiLocal)miter.next();
            meisai.setNyuukajoukyou(NyuukayoteimeisaiBean.NYUUKAZUMI);
        }
    }

    /**
    * @ejb:interface-method view-type="both"
    **/
    public HacchuuData getHacchuuData() {
        HacchuuData data = new HacchuuData(
            getHacchuu_id(),
            getHacchuu_bg(),
            getShiiresaki_id(),
            getNouhinsaki_id(),
            getHacchuu_date(),
            getFormat(),
            getBikou(),
            getCreated(),
            getCreatedby(),
            getUpdated(),
            getUpdatedby(),
            getSakujo_flg()
        );
        return data;
    }
}
