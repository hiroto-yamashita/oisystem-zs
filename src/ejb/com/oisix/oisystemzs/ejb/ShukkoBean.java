package com.oisix.oisystemzs.ejb;

import com.oisix.oisystemfr.ejb.ServiceLocator;
import com.oisix.oisystemfr.Debug;
import com.oisix.oisystemfr.ejb.KeyGeneratorLocal;
import com.oisix.oisystemfr.ejb.KeyGeneratorLocalHome;
import com.oisix.oisystemzs.ejb.exception.HaraidashiException;
import com.oisix.oisystemzs.ejb.exception.ShukkoModifyException;
import com.oisix.oisystemzs.ejb.exception.ZaikoException;
import com.oisix.oisystemzs.Names;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Collection;
import java.util.Iterator;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.EJBException;
import javax.ejb.CreateException;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;
import javax.naming.NamingException;
import javax.naming.InitialContext;

/**
 * The Entity bean represents Shukko
 *
 * @author Ryuya Matsunaga
 * @version $Revision: 1.0 $
 *
 * @ejb:bean name="Shukko"
 *           display-name="Shukko"
 *           type="CMP"
 *           jndi-name="ejb/Shukko"
 *           local-jndi-name="ejb/ShukkoLocal"
 *           view-type="both"
 *
 * @ejb:ejb-ref ejb-name="KeyGenerator"
 *              view-type="local"
 * @ejb:ejb-ref ejb-name="Shouhin"
 *              view-type="local"
 * @ejb:ejb-ref ejb-name="Zaiko"
 *              view-type="local"
 * @ejb:ejb-ref ejb-name="Zaikomeisai"
 *              view-type="local"
 * @ejb:ejb-ref ejb-name="Shukkayoteimeisai"
 *              view-type="local"
 * @ejb:ejb-ref ejb-name="Shukko"
 *              view-type="local"
 * @ejb:resource-ref res-name="${res.name}"
 *                   res-type="javax.sql.DataSource"
 *                   res-auth="Container"
 * @jboss:resource-ref res-ref-name="${res.name}"
 *                     jndi-name="${datasource.name}"
 *
 * @ejb:finder signature="Collection findByShukko_date(java.lang.String souko_id, java.lang.String shouhin_id, java.util.Date start)" query="SELECT OBJECT(s) from Shukko s WHERE (s.shukko_date = ?3 or s.shukko_date > ?3) and s.souko_id = ?1 and s.shouhin_id = ?2" result-type-mapping="Local"
 *
 * @ejb.persistence table-name="zt_shukko"
 *
 * @jboss:persistence pk-constraint="true"
 *
 **/
public abstract class ShukkoBean implements EntityBean {
    private EntityContext entityContext;

    /**
    * @ejb:persistent-field
    * @ejb:pk-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract int getShukko_id();
    public abstract void setShukko_id(int shukko_id);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract String getShukko_bg();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setShukko_bg(String shukko_bg);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract int getShukkokubun();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setShukkokubun(int shukkokubun);
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
    public abstract String getSouko_id();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setSouko_id(String souko_id);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract String getShouhin_id();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setShouhin_id(String shouhin_id);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract String getLocation_id();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setLocation_id(String location_id);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract java.util.Date getShukko_date();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setShukko_date(java.util.Date shukko_date);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract java.util.Date getShoumikigen();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setShoumikigen(java.util.Date shoumikigen);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract float getSuuryou();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setSuuryou(float suuryou);
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
    public abstract int getTankakubun();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setTankakubun(int tankakubun);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract float getTanka();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setTanka(float tanka);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract float getKingaku();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setKingaku(float kingaku);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract int getShukkayoteimeisai_id();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setShukkayoteimeisai_id(int shukkayoteimeisai_id);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract int getTeisei_flg();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setTeisei_flg(int teisei_flg);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract int getTeiseishukko_id();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setTeiseishukko_id(int teiseishukko_id);
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
    public abstract float getHyoujunbaika();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setHyoujunbaika(float hyoujunbaika);


    /**
    * @ejb:create-method view-type="both"
    **/
    public ShukkoPK ejbCreate(
      int shukkokubun,
      String nouhinsaki_id,
      String souko_id,
      String shouhin_id,
      String location_id,
      java.util.Date shukko_date,
      java.util.Date shoumikigen,
      float suuryou,
      String tani,
      int tankakubun,
      float tanka,
      float kingaku,
      int shukkayoteimeisai_id,
      int teisei_flg,
      int teiseishukko_id,
      int user_id,
      float hyoujunbaika
      )
      throws CreateException, HaraidashiException {
        if ((teisei_flg != Names.ON) && (suuryou < 0)) {
            System.out.println(
              "exception in ShukkoBean#create():shouhin_id="+shouhin_id+
              " shukko_date="+shukko_date+" suuryou="+suuryou);
            throw new CreateException("suuryou less than zero");
        }
        int shukko_id = getNextShukko_id();
        String shukko_bg = getNewShukko_bg(shukko_id);
        setShukko_id(shukko_id);
        setShukko_bg(shukko_bg);
        setShukkokubun(shukkokubun);
        setNouhinsaki_id(nouhinsaki_id);
        setSouko_id(souko_id);
        setShouhin_id(shouhin_id);
        setLocation_id(location_id);
        setShukko_date(shukko_date);
        setShoumikigen(shoumikigen);
        setSuuryou(suuryou);
        setTani(tani);
        setTankakubun(tankakubun);
        setTanka(tanka);
        setKingaku(kingaku);
        setShukkayoteimeisai_id(shukkayoteimeisai_id);
        setTeisei_flg(teisei_flg);
        setTeiseishukko_id(teiseishukko_id);
        setCreated(new java.util.Date());
        setCreatedby(user_id);
        setUpdated(new java.util.Date());
        setUpdatedby(user_id);
        setHyoujunbaika(hyoujunbaika);
        try {
            if (teisei_flg != Names.ON) {
                updateZaiko();
            }
        } catch (HaraidashiException he) {
            entityContext.setRollbackOnly();
            throw he;
        }
        //if (shukkayoteimeisai_id != Names.NONE_ID) {
        if ((shukkayoteimeisai_id != Names.NONE_ID) &&
          (teisei_flg == Names.OFF)) {
            try {
                updateShukkayoteimeisai();
            } catch (NamingException ne) {
                entityContext.setRollbackOnly();
                throw new CreateException(
                  "NamingException in updateShukkayoteimeisai");
            } catch (FinderException fe) {
                entityContext.setRollbackOnly();
                throw new CreateException(
                  "FinderException in updateShukkayoteimeisai");
            }
        }
        return null;
    }
    public void ejbPostCreate(int shukkokubun, String nouhinsaki_id,
        String souko_id, String shouhin_id, String location_id,
        java.util.Date shukko_date, java.util.Date shoumikigen,
        float suuryou, String tani, int tankakubun, float tanka, float kingaku,
        int shukkayoteimeisai_id, int teisei_flg, int teiseishukko_id,
        int user_id, float hyoujunbaika) {}
    public void setEntityContext(EntityContext ctx) {
        entityContext = ctx;
    }
    public void unsetEntityContext() {}
    public void ejbRemove() throws javax.ejb.RemoveException {}
    public void ejbActivate() {}
    public void ejbPassivate() {}
    public void ejbLoad() {}
    public void ejbStore() {}

    private int getNextShukko_id() {
        int id = 0;
        try {
            InitialContext ic = new InitialContext();
            Object objref = ic.lookup("java:comp/env/ejb/KeyGeneratorLocal");
            KeyGeneratorLocalHome kgh = (KeyGeneratorLocalHome)objref;
            KeyGeneratorLocal kg = kgh.create();
            id = kg.getNext("ZS_SHUKKO");
        } catch (NamingException ex) {
            Debug.println(ex);
        } catch (CreateException ex) {
            Debug.println(ex);
        }
        return id;
    }

    private String getNewShukko_bg(int shukko_id) {
        String bg = "SK-";
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
        bg += sdf.format(new java.util.Date()) + "-";
        String strShukko_id = "0000" + String.valueOf(shukko_id);
        int start = strShukko_id.length() - 4;
        bg += strShukko_id.substring(start);
        return bg;
    }

    /**
    * @ejb:interface-method view-type="local"
    **/
    public ShouhinLocal getShouhin()
      throws NamingException, FinderException {
        ShouhinLocalHome sh = (ShouhinLocalHome)
          ServiceLocator.getLocalHome("java:comp/env/ejb/ShouhinLocal");
        return sh.findByPrimaryKey(getShouhin_id());
    }

    /**
    * @ejb:interface-method view-type="both"
    **/
    public ShukkoData getShukkoData() {
        ShukkoData shukkoData = new ShukkoData(
          this.getShukko_id(),
          this.getShukko_bg(),
          this.getShukkokubun(),
          this.getNouhinsaki_id(),
          this.getSouko_id(),
          this.getShouhin_id(),
          this.getLocation_id(),
          this.getShukko_date(),
          this.getShoumikigen(),
          this.getSuuryou(),
          this.getTani(),
          this.getTankakubun(),
          this.getTanka(),
          this.getKingaku(),
          this.getShukkayoteimeisai_id(),
          this.getTeisei_flg(),
          this.getTeiseishukko_id(),
          this.getCreated(),
          this.getCreatedby(),
          this.getUpdated(),
          this.getUpdatedby(),
          this.getHyoujunbaika()
        );
        return shukkoData;
    }

    /**
    * ejbcreateの際に必ずCallする。出庫に応じて在庫をアップデートする。
    */
    private void updateZaiko() throws HaraidashiException {
        ZaikoLocalHome zhome = null;
        try {
            zhome = (ZaikoLocalHome)ServiceLocator.getLocalHome(
              "java:comp/env/ejb/ZaikoLocal");
        } catch(NamingException ne) {
            throw new HaraidashiException(ne);
        }
        ZaikoLocal targetzaiko = null;
        //if (getTeisei_flg() == 1) {
        //    // 訂正出庫の場合、元の出庫に対応する在庫に対して出庫
        //    // 修正：元の出庫の１つ手前の在庫に対して出庫
        //    try {
        //        //targetzaiko = zhome.findByShukko_id(getTeiseishukko_id());
        //        ZaikoLocal zaiko = zhome.findByShukko_id(getTeiseishukko_id());
        //        targetzaiko = zaiko.getPreviousZaiko();
        //    } catch (FinderException fe) {
        //        throw new HaraidashiException(fe);
        //    } catch (NamingException ne) {
        //        throw new HaraidashiException(ne);
        //    }
        //}
        if (targetzaiko == null) {
            // 入庫日以前の最新の在庫に対し出庫
            try {
                ZaikoDAO zd = new ZaikoDAO();
                Debug.println("shukkodate:"+getShukko_date(), this);
                int zaiko_id = zd.findLatestZaiko_id(
                  getShukko_date(), getSouko_id(), getShouhin_id());
                Debug.println("zaiko_id:"+zaiko_id, this);
                if (zaiko_id == 0) {
                    throw new HaraidashiException("zaiko not found");
                }
                ZaikoPK pk = new ZaikoPK(zaiko_id);
                targetzaiko = zhome.findByPrimaryKey(pk);
            } catch (FinderException fe) {
                throw new HaraidashiException(fe);
            } catch (SQLException se) {
                throw new HaraidashiException(se);
            }
        }
        //出庫して数量が0になる場合は在庫金額も0になるようにする
        float suuryou = getSuuryou();
        if (suuryou == targetzaiko.getSuuryou()) {
            float kingaku = targetzaiko.getKingaku();
            setKingaku(kingaku);
            if (suuryou != 0) {
                setTanka(kingaku / suuryou);
            } else {
                setTanka(0);
            }
        }
        targetzaiko.haraidashi(getShukkoData());
    }

    /**
    * @ejb:interface-method view-type="local"
    **/
    public ShukkoLocal modify(float suuryou, int user_id)
      throws ShukkoModifyException, HaraidashiException {
        if (getTeisei_flg() == Names.ON) {
            throw new ShukkoModifyException("already modified");
        }
        if (suuryou > 0) {
            throw new ShukkoModifyException("suuryou greater than zero");
        }
        if (getSuuryou() + suuryou < 0) {
            throw new ShukkoModifyException(
              "modify suuryou greater than original suuryou");
        }
        ShukkoLocalHome sklhome = null;
        try {
            sklhome = (ShukkoLocalHome)ServiceLocator.getLocalHome(
              "java:comp/env/ejb/ShukkoLocal");
        } catch (NamingException ne) {
            throw new ShukkoModifyException(ne);
        }
        ShukkoLocal shukko = null;
        float kingaku = suuryou * getTanka();
        try {
            shukko = sklhome.create(
              getShukkokubun(),
              getNouhinsaki_id(),
              getSouko_id(),
              getShouhin_id(),
              getLocation_id(),
              getShukko_date(),
              getShoumikigen(),
              suuryou,
              getTani(),
              getTankakubun(),
              getTanka(),
              kingaku,
              getShukkayoteimeisai_id(),
              Names.ON, //teisei_flg
              getShukko_id(), //teiseishukko_id
              user_id,
              getHyoujunbaika());
            ZaikoLocalHome zhome = (ZaikoLocalHome)ServiceLocator.getLocalHome(
              "java:comp/env/ejb/ZaikoLocal");
            ZaikoLocal zaiko = zhome.findByShukko_id(getShukko_id());
            ZaikoLocal prezaiko = zaiko.getPreviousZaiko();
            float msuuryou = prezaiko.getSuuryou() - suuryou;
            float mkingaku = prezaiko.getKingaku() - kingaku;
            float mtanka = 0;
            if (msuuryou != 0) {
                mtanka = mkingaku / msuuryou;
            }
            ZaikoLocal modifyzaiko = zhome.create(
              getShukko_date(),
              getSouko_id(),
              getShouhin_id(),
              msuuryou,
              mtanka,
              mkingaku,
              0, //nyuuko_id
              shukko.getShukko_id(),
              prezaiko.getZorder() + 1,
              user_id);
            modifyzaiko.setNext_zaiko_id(zaiko.getZaiko_id());
            Collection meisais = prezaiko.getZaikomeisaiData();
            if (meisais != null) {
                Iterator meisaiiter = meisais.iterator();
                boolean match = false;
                while (meisaiiter.hasNext()) {
                    ZaikomeisaiData meisai = (ZaikomeisaiData)meisaiiter.next();
                    if ((shukko.getShoumikigen() == null ||
                      meisai.getShoumikigen().equals(shukko.getShoumikigen()))
                      && (meisai.getLocation_id().equals(shukko.getLocation_id())))
                      {
                        meisai.setSuuryou(
                          meisai.getSuuryou() - shukko.getSuuryou());
                        if (meisai.getSuuryou() < 0) {
                            throw new HaraidashiException(
                              "meisai suurou less than zero");
                        }
                        match = true;
                    }
                }
                if (match == false) {
                    throw new HaraidashiException("meisai shoumi unmatch");
                }
            }
            modifyzaiko.createMeisai(meisais, user_id);
            prezaiko.setNext_zaiko_id(modifyzaiko.getZaiko_id());
            float zsuuryou = msuuryou - getSuuryou();
            float zkingaku = mkingaku - getKingaku();
            float ztanka = 0;
            if (zsuuryou != 0) {
                ztanka = zkingaku / zsuuryou;
            }
            zaiko.setSuuryou(zsuuryou);
            zaiko.setKingaku(zkingaku);
            zaiko.setTanka(ztanka);
            zaiko.setZorder(modifyzaiko.getZorder() + 1);
            zaiko.deleteMeisai();
            if (meisais != null) {
                Iterator meisaiiter = meisais.iterator();
                boolean match = false;
                while (meisaiiter.hasNext()) {
                    ZaikomeisaiData meisai = (ZaikomeisaiData)meisaiiter.next();
                    if ((getShoumikigen() == null ||
                      meisai.getShoumikigen().equals(getShoumikigen()))
                      && (meisai.getLocation_id().equals(getLocation_id())))
                      {
                        meisai.setSuuryou(
                          meisai.getSuuryou() - getSuuryou());
                        if (meisai.getSuuryou() < 0) {
                            throw new HaraidashiException(
                              "meisai suurou less than zero");
                        }
                        match = true;
                    }
                }
                if (match == false) {
                    throw new HaraidashiException("meisai shoumi unmatch");
                }
            }
            zaiko.createMeisai(meisais, user_id);
            if (zaiko.getNext_zaiko_id() != 0) {
                ZaikoLocal nextzaiko = zaiko.getNextZaiko();
                nextzaiko.update(zaiko.getZaikoData(), meisais, user_id);
            }
        } catch (CreateException ce) {
            throw new ShukkoModifyException(ce);
        } catch(NamingException ne) {
            throw new HaraidashiException(ne);
        } catch (RemoveException re) {
            throw new HaraidashiException(re);
        } catch (FinderException fe) {
            throw new HaraidashiException(fe);
        } catch (ZaikoException ze) {
            throw new HaraidashiException(ze);
        } catch (HaraidashiException he) {
            throw new HaraidashiException(he);
        }
        return shukko;
    }

    private void updateShukkayoteimeisai()
      throws NamingException, FinderException {
        ShukkayoteimeisaiPK pk =
          new ShukkayoteimeisaiPK(getShukkayoteimeisai_id());
        ShukkayoteimeisaiLocalHome symhome = (ShukkayoteimeisaiLocalHome)
          ServiceLocator.getLocalHome(
          "java:comp/env/ejb/ShukkayoteimeisaiLocal");
        ShukkayoteimeisaiLocal sym = symhome.findByPrimaryKey(pk);
        sym.addJitsushukkasuuryou(getSuuryou(), getUpdatedby());
    }
}
