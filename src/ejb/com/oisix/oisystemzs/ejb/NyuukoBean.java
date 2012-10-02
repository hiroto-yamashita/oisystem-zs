package com.oisix.oisystemzs.ejb;

import com.oisix.oisystemfr.Debug;
import com.oisix.oisystemfr.ejb.ServiceLocator;
import com.oisix.oisystemfr.ejb.KeyGeneratorLocal;
import com.oisix.oisystemfr.ejb.KeyGeneratorLocalHome;
import com.oisix.oisystemzs.ejb.exception.UkeireException;
import com.oisix.oisystemzs.ejb.exception.NyuukoModifyException;
import com.oisix.oisystemzs.ejb.exception.ZaikoException;
import com.oisix.oisystemzs.ejb.exception.ZmeisaiHaraidashiException;
import com.oisix.oisystemzs.ejb.exception.HaraidashiException;
import com.oisix.oisystemzs.Names;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Iterator;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.CreateException;
import javax.ejb.FinderException;
import javax.ejb.EJBException;
import javax.naming.NamingException;
import javax.naming.InitialContext;

/**
 * The Entity bean represents Nyuuko
 *
 * @author Ryuya Matsunaga
 * @version $Revision: 1.0 $
 *
 * @ejb:bean name="Nyuuko"
 *           display-name="Nyuuko"
 *           type="CMP"
 *           jndi-name="ejb/Nyuuko"
 *           local-jndi-name="ejb/NyuukoLocal"
 *           view-type="both"
 *
 * @ejb:ejb-ref ejb-name="KeyGenerator"
 *              view-type="local" 
 * @ejb:ejb-ref ejb-name="Shouhin"
 *              view-type="local"
 * @ejb:ejb-ref ejb-name="Zaiko"
 *              view-type="local"
 * @ejb:ejb-ref ejb-name="Nyuukayoteimeisai"
 *              view-type="local"
 * @ejb:ejb-ref ejb-name="Nyuuko"
 *              view-type="local"
 * @ejb:resource-ref res-name="${res.name}"
 *                   res-type="javax.sql.DataSource"
 *                   res-auth="Container"
 * @jboss:resource-ref res-ref-name="${res.name}"
 *                     jndi-name="${datasource.name}"
 *
 * @ejb:finder signature="Collection findByNyuuko_date(java.lang.String souko_id, java.lang.String shouhin_id, java.util.Date start)" query="SELECT OBJECT(n) from Nyuuko n WHERE (n.nyuuko_date = ?3 or n.nyuuko_date > ?3) and n.souko_id = ?1 and n.shouhin_id = ?2" result-type-mapping="Local"
 *
 * @ejb.persistence table-name="zt_nyuuko"
 *
 * @jboss:persistence pk-constraint="true"
 *
 **/
public abstract class NyuukoBean implements EntityBean {
    private EntityContext entityContext;

    /**
    * @ejb:persistent-field
    * @ejb:pk-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract int getNyuuko_id();
    public abstract void setNyuuko_id(int nyuuko_id);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract String getNyuuko_bg();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setNyuuko_bg(String nyuuko_bg);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract java.util.Date getNyuuko_date();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setNyuuko_date(java.util.Date nyuuko_date);
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
    public abstract float getShiiresuuryou();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setShiiresuuryou(float shiiresuuryou);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract String getShiiretani();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setShiiretani(String shiiretani);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract float getShiiretanka();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setShiiretanka(float shiiretanka);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract float getNyuukosuuryou();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setNyuukosuuryou(float nyuukosuuryou);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract String getNyuukotani();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setNyuukotani(String nyuukotani);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract float getNyuukotanka();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setNyuukotanka(float nyuukotanka);
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
    public abstract java.util.Date getShukkakigen();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setShukkakigen(java.util.Date shukkakigen);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract int getNyuukokubun();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setNyuukokubun(int nyuukokubun);
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
    public abstract int getNyuukayotei_id();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setNyuukayotei_id(int nyuukayotei_id);
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
    public abstract int getTeiseinyuuko_id();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setTeiseinyuuko_id(int teiseinyuuko_id);
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
    public NyuukoPK ejbCreate(java.util.Date nyuuko_date, String souko_id,
        String shouhin_id, float shiiresuuryou, String shiiretani,
        float shiiretanka, float nyuukosuuryou, String nyuukotani,
        float nyuukotanka, java.util.Date shoumikigen,
        java.util.Date shukkakigen, int nyuukokubun,
        String shiiresaki_id, String nouhinsaki_id, int nyuukayotei_id,
        int teisei_flg, int teiseinyuuko_id, int user_id)
      throws CreateException, UkeireException {
        if (nyuukosuuryou == 0) {
            throw new UkeireException("nyuukosuuryou must not be zero");
        }
        int nyuuko_id = getNextNyuuko_id();
        String nyuuko_bg = getNewNyuuko_bg(nyuuko_id);
        setNyuuko_id(nyuuko_id);
        setNyuuko_bg(nyuuko_bg);
        setNyuuko_date(nyuuko_date);
        setSouko_id(souko_id);
        setShouhin_id(shouhin_id);
        setShiiresuuryou(shiiresuuryou);
        setShiiretani(shiiretani);
        setShiiretanka(shiiretanka);
        setNyuukosuuryou(nyuukosuuryou);
        setNyuukotani(nyuukotani);
        setNyuukotanka(nyuukotanka);
        setShoumikigen(shoumikigen);
        setShukkakigen(shukkakigen);
        setNyuukokubun(nyuukokubun);
        setShiiresaki_id(shiiresaki_id);
        setNouhinsaki_id(nouhinsaki_id);
        setNyuukayotei_id(nyuukayotei_id);
        setTeisei_flg(teisei_flg);
        setTeiseinyuuko_id(teiseinyuuko_id);
        setCreated(new java.util.Date());
        setCreatedby(user_id);
        setUpdated(new java.util.Date());
        setUpdatedby(user_id);
        try {
            updateZaiko();
        } catch (UkeireException ue) {
            entityContext.setRollbackOnly();
            throw ue;
        }
        //if (nyuukayotei_id != 0) {
        if ((nyuukayotei_id != Names.NONE_ID) && (teisei_flg == Names.OFF)) {
            try {
                updateNyuukayotei();
            } catch (NamingException ne) {
                entityContext.setRollbackOnly();
                throw new CreateException(
                  "NamingException in updateNyuukayotei");
            } catch (FinderException fe) {
                entityContext.setRollbackOnly();
                throw new CreateException(
                  "FinderException in updateNyuukayotei");
            }
        }
        return null;
    }
    public void ejbPostCreate(java.util.Date nyuuko_date, String souko_id,
      String shouhin_id, float shiiresuuryou, String shiiretani,
      float shiiretanka, float nyuukosuuryou, String nyuukotani,
      float nyuukotanka, java.util.Date shoumikigen,
      java.util.Date shukkakigen, int nyuukokubun,
      String shiiresaki_id, String nouhinsaki_id, int nyuukayotei_id,
      int teisei_flg, int teiseinyuuko_id, int user_id) {}
    public void setEntityContext(EntityContext ctx) {
        entityContext = ctx;
    }
    public void unsetEntityContext() {}
    public void ejbRemove() throws javax.ejb.RemoveException {}
    public void ejbActivate() {}
    public void ejbPassivate() {}
    public void ejbLoad() {}
    public void ejbStore() {}

    private int getNextNyuuko_id() {
        int id = 0;
        try {
            InitialContext ic = new InitialContext();
            Object objref = ic.lookup("java:comp/env/ejb/KeyGeneratorLocal");
            KeyGeneratorLocalHome kgh = (KeyGeneratorLocalHome)objref;
            KeyGeneratorLocal kg = kgh.create();
            id = kg.getNext("ZS_NYUUKO");
        } catch (NamingException ex) {
            Debug.println(ex);
        } catch (CreateException ex) {
            Debug.println(ex);
        }
        return id;
    }

    private String getNewNyuuko_bg(int nyuuko_id) {
        String bg = "NK-";
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
        bg += sdf.format(new java.util.Date()) + "-";
        String strNyuuko_id = "0000" + String.valueOf(nyuuko_id);
        int start = strNyuuko_id.length() - 4;
        bg += strNyuuko_id.substring(start);
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
    public NyuukoData getNyuukoData() {
        NyuukoData nyuukoData = new NyuukoData(
          this.getNyuuko_id(),
          this.getNyuuko_bg(),
          this.getNyuuko_date(),
          this.getSouko_id(),
          this.getShouhin_id(),
          this.getShiiresuuryou(),
          this.getShiiretani(),
          this.getShiiretanka(),
          this.getNyuukosuuryou(),
          this.getNyuukotani(),
          this.getNyuukotanka(),
          this.getShoumikigen(),
          this.getShukkakigen(),
          this.getNyuukokubun(),
          this.getShiiresaki_id(),
          this.getNouhinsaki_id(),
          this.getNyuukayotei_id(),
          this.getTeisei_flg(),
          this.getTeiseinyuuko_id(),
          this.getCreated(),
          this.getCreatedby(),
          this.getUpdated(),
          this.getUpdatedby()
        );
        return nyuukoData;
    }

    /**
    * ejbcreateの際に必ずCallする。入庫に応じて在庫をアップデートする。
    */
    private void updateZaiko() throws UkeireException {
        ZaikoLocalHome zhome = null;
        try {
            zhome = (ZaikoLocalHome)ServiceLocator.getLocalHome(
              "java:comp/env/ejb/ZaikoLocal");
        } catch(NamingException ne) {
            throw new UkeireException(ne);
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(getNyuuko_date());
        //cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        java.util.Date start = cal.getTime();
        cal.add(Calendar.DATE, 1);
        java.util.Date end = cal.getTime();
        ZaikoLocal targetzaiko = null;
        if (getTeisei_flg() == 1) {
            //訂正在庫の場合、元の入庫に対応する在庫に入庫
            try {
                //EJBのLock機構のため以下のソースでは動かない
                //NyuukoLocal moto = getTeiseimotoNyuuko();
                //targetzaiko = moto.getZaiko();
                targetzaiko = zhome.findByNyuuko_id(getTeiseinyuuko_id());
            } catch (FinderException fe) {
                throw new UkeireException(fe);
            }
        }
        if (targetzaiko == null) {
            //入庫日以前の最新の在庫に対し入庫
            try {
                ZaikoDAO zd = new ZaikoDAO();
                int zaiko_id = zd.findLatestZaiko_id(
                  getNyuuko_date(), getSouko_id(), getShouhin_id());
                if (zaiko_id != 0) {
                    ZaikoPK pk = new ZaikoPK(zaiko_id);
                    targetzaiko = zhome.findByPrimaryKey(pk);
                }
            } catch (FinderException fe) {
                throw new UkeireException(fe);
            } catch (SQLException se) {
                throw new UkeireException(se);
            }
        }
        ShouhinLocal sh = null;
        try {
            sh = getShouhin();
        } catch (FinderException fe) {
            throw new UkeireException(fe);
        } catch (NamingException ne) {
            throw new UkeireException(ne);
        }
        if (targetzaiko != null) {
            targetzaiko.ukeire(getNyuukoData(), sh.getShoumikigen_flg());
        } else {
            //新規入庫
            //または一番先頭の入庫
            try {
                //データを作る前に在庫データがあるか調べておく
                ZaikoDAO zd = new ZaikoDAO();
                int zaiko_id = 0;
                try {
                    zaiko_id = zd.findOldestZaiko_id(
                      getSouko_id(), getShouhin_id());
                } catch (SQLException se) {
                    throw new UkeireException(se);
                }

                ZaikoLocal newzaiko = zhome.create(
                  getNyuuko_date(),
                  getSouko_id(),
                  getShouhin_id(),
                  getNyuukosuuryou(),
                  getNyuukotanka(),
                  getNyuukosuuryou() * getNyuukotanka(),
                  getNyuuko_id(),
                  0,
                  1,
                  getCreatedby()
                );
                LinkedList meisais = null;
                if (sh.getShoumikigen_flg() == 1) {
                    //賞味期限管理対象商品の場合明細も作成
                    ZaikomeisaiData zmdata = new ZaikomeisaiData(
                      0, //zaikomeisai_id
                      newzaiko.getZaiko_id(),
                      getSouko_id(),
                      getShouhin_id(),
                      "000001", //ダミーロケーションコード
                      getShoumikigen(),
                      getNyuukosuuryou(),
                      getShukkakigen(),
                      new java.util.Date(),
                      getCreatedby(),
                      new java.util.Date(),
                      getCreatedby()
                    );
                    meisais = new LinkedList();
                    meisais.add(zmdata);
                    newzaiko.createMeisai(meisais, getCreatedby());
                }
                if (zaiko_id != 0) {
                    ZaikoPK pk = new ZaikoPK(zaiko_id);
                    try {
                        ZaikoLocal zaiko = zhome.findByPrimaryKey(pk);
                        newzaiko.setNext_zaiko_id(zaiko.getZaiko_id());
                        zaiko.update(newzaiko.getZaikoData(), meisais,
                          getCreatedby());
                    } catch (FinderException fe) {
                        throw new UkeireException(fe);
                    }
                }
            } catch (NamingException ne) {
                throw new UkeireException(ne);
            } catch (CreateException ce) {
                throw new UkeireException(ce);
//            } catch (ShukkoException se) {
//                throw new UkeireException(se);
            } catch (ZaikoException ze) {
                throw new UkeireException(ze);
//            } catch (ZaikomeisaiException zme) {
//                throw new UkeireException(zme);
            } catch (HaraidashiException he) {
                throw new UkeireException(he);
            }
        }
    }

    /**
    * @ejb:interface-method view-type="local"
    * nyuukosuuryouは減らす入庫数量(0以下)
    * shiiresuuryouは減らす仕入数量(0以下)
    * NyuukoModifiExceptionは、以下の場合に出ます。
    * ・訂正入庫にmodifyをかけたとき
    * ・nyuukosuuryouが0を超えるとき
    * ・shiiresuuryouが0を超えるとき
    * ・nyuukosuuryou,shiiresuuryouが元の入庫の数量を超えるとき
    * ・システムエラー(メソッド内部のNamingException,CreateException)
    * UkeireExceptionはZaikoBeanのupdateZaikoメソッドに準じます。
    **/
    public NyuukoLocal modify(float nyuukosuuryou, float shiiresuuryou,
      int user_id) throws NyuukoModifyException, UkeireException {
        if (getTeisei_flg() == Names.ON) {
            throw new NyuukoModifyException("already modified");
        }
        if (nyuukosuuryou > 0) {
            throw new NyuukoModifyException("nyuukosuuryou greater than zero");
        }
        if (shiiresuuryou > 0) {
            throw new NyuukoModifyException("shiiresuuryou greater than zero");
        }
        if (getNyuukosuuryou() + nyuukosuuryou < 0) {
            throw new NyuukoModifyException("modify nyuukosuuryou greater than original nyuukosuuryou");
        }
        if (getShiiresuuryou() + shiiresuuryou < 0) {
            throw new NyuukoModifyException("modify shiiresuuryou greater than original shiiresuuryou");
        }
        NyuukoLocalHome nhome = null;
        try {
            nhome = (NyuukoLocalHome)ServiceLocator.getLocalHome(
              "java:comp/env/ejb/NyuukoLocal");
        } catch (NamingException ne) {
            throw new NyuukoModifyException(ne);
        }
        NyuukoLocal nyuuko = null;
        float nyuukotanka = shiiresuuryou * getShiiretanka() / nyuukosuuryou;
        try {
            nyuuko = nhome.create(
              getNyuuko_date(),
              getSouko_id(),
              getShouhin_id(),
              shiiresuuryou,
              getShiiretani(),
              getShiiretanka(),
              nyuukosuuryou,
              getNyuukotani(),
              nyuukotanka,
              getShoumikigen(),
              getShukkakigen(),
              getNyuukokubun(),
              getShiiresaki_id(),
              getNouhinsaki_id(),
              getNyuukayotei_id(),
              1, //teisei_flg
              getNyuuko_id(), //teiseinyuuko_id
              user_id);
        } catch (CreateException ce) {
            throw new NyuukoModifyException(ce);
        }
        return nyuuko;
    }

    /**
    * @ejb:interface-method view-type="local"
    **/
    public NyuukoLocal getTeiseimotoNyuuko()
      throws NamingException, FinderException {
        if (getTeisei_flg() != 1) {
            return null;
        }
        NyuukoLocalHome nhome = (NyuukoLocalHome)ServiceLocator.getLocalHome(
          "java:comp/env/ejb/NyuukoLocal");
        NyuukoPK pk = new NyuukoPK(getTeiseinyuuko_id());
        NyuukoLocal nyuuko = nhome.findByPrimaryKey(pk);
        return nyuuko;
    }

    /**
    * @ejb:interface-method view-type="local"
    **/
    public ZaikoLocal getZaiko() throws NamingException, FinderException {
        ZaikoLocalHome zhome = (ZaikoLocalHome)ServiceLocator.getLocalHome(
          "java:comp/env/ejb/ZaikoLocal");
        ZaikoLocal zaiko = zhome.findByNyuuko_id(getNyuuko_id());
        return zaiko;
    }

    private void updateNyuukayotei() throws NamingException, FinderException {
        NyuukayoteimeisaiPK pk = new NyuukayoteimeisaiPK(getNyuukayotei_id());
        NyuukayoteimeisaiLocalHome nyhome = (NyuukayoteimeisaiLocalHome)
          ServiceLocator.getLocalHome(
          "java:comp/env/ejb/NyuukayoteimeisaiLocal");
        NyuukayoteimeisaiLocal ny = nyhome.findByPrimaryKey(pk);
        ny.addJitsunyuukasuu(getNyuukosuuryou(), getUpdatedby());
    }

    /**
    * @ejb:interface-method view-type="local"
    * NyuukoModifiExceptionは、以下の場合に出ます。
    * ・訂正入庫にmodifyをかけたとき
    * ・システムエラー(メソッド内部のNamingException,CreateException)
    * UkeireExceptionはZaikoBeanのupdateZaikoメソッドに準じます。
    **/
    public void modifyTanka(float newtanka, int user_id)
      throws NyuukoModifyException {
        if (getTeisei_flg() == Names.ON) {
            throw new NyuukoModifyException("already modified");
        }
        if (newtanka < 0) {
            throw new NyuukoModifyException("newtanka less than 0");
        }
        float origkingaku = getNyuukotanka() * getNyuukosuuryou();
        setShiiretanka(newtanka);
        float newkingaku = newtanka * getShiiresuuryou();
        float nyuukotanka = newkingaku / getNyuukosuuryou();
        setNyuukotanka(nyuukotanka);
        setUpdated(new java.util.Date());
        setUpdatedby(user_id);
        ZaikoLocal zaiko = null;
        try {
            zaiko = getZaiko();
        } catch (NamingException ne) {
            entityContext.setRollbackOnly();
            throw new NyuukoModifyException(ne);
        } catch (FinderException fe) {
            entityContext.setRollbackOnly();
            throw new NyuukoModifyException(fe);
        }
        float zaikokingaku = zaiko.getKingaku();
        zaikokingaku = zaikokingaku - origkingaku + newkingaku;
        zaiko.setTanka(zaikokingaku / zaiko.getSuuryou());
        zaiko.setKingaku(zaikokingaku);
        if (zaiko.getNext_zaiko_id() != Names.NONE_ID) {
            ZaikoData zaikodata = zaiko.getZaikoData();
            Collection meisaidata = null;
            ZaikoLocal nextzaiko= null;
            try {
                meisaidata = zaiko.getZaikomeisaiData();
                nextzaiko = zaiko.getNextZaiko();
                nextzaiko.update(zaikodata, meisaidata, user_id);
            } catch (NamingException ne) {
                entityContext.setRollbackOnly();
                throw new NyuukoModifyException(ne);
            } catch (HaraidashiException he) {
                entityContext.setRollbackOnly();
                if (he instanceof ZmeisaiHaraidashiException) {
                    ZmeisaiHaraidashiException zhe =
                      (ZmeisaiHaraidashiException)he;
                    ShukkoData sd = zhe.getShukko();
                    System.out.println("shukko_id:" + sd.getShukko_id());
                    System.out.println("shouhin_id:" + sd.getShouhin_id());
                    System.out.println("shukkodate:" + sd.getShukko_date());
                    System.out.println("shoumikigen:" + sd.getShoumikigen());
                    System.out.println("suuryou:" + sd.getSuuryou());
                }
                throw new NyuukoModifyException(he);
            } catch (ZaikoException ze) {
                entityContext.setRollbackOnly();
                throw new NyuukoModifyException(ze);
            }
        }
    }
}
