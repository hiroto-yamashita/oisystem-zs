package com.oisix.oisystemzs.ejb;

import com.oisix.oisystemfr.Debug;
import com.oisix.oisystemfr.ejb.KeyGeneratorLocal;
import com.oisix.oisystemfr.ejb.KeyGeneratorLocalHome;
import com.oisix.oisystemfr.ejb.ServiceLocator;
import com.oisix.oisystemzs.Names;
import com.oisix.oisystemzs.ejb.exception.ZaikoException;
import com.oisix.oisystemzs.ejb.exception.ZaikomeisaiException;
import com.oisix.oisystemzs.ejb.exception.HaraidashiException;
import com.oisix.oisystemzs.ejb.exception.ZaikoSuuryouHaraidashiException;
import com.oisix.oisystemzs.ejb.exception.ZaikoTankaHaraidashiException;
import com.oisix.oisystemzs.ejb.exception.ZaikoKingakuHaraidashiException;
import com.oisix.oisystemzs.ejb.exception.ZmeisaiSuuryouHaraidashiException;
import com.oisix.oisystemzs.ejb.exception.ZmeisaiShoumiHaraidashiException;
import com.oisix.oisystemzs.ejb.exception.UkeireException;
import java.util.HashMap;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.CreateException;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;
import javax.naming.NamingException;
import javax.naming.InitialContext;

/**
 * The Entity bean represents Zaiko
 *
 * @author Ryuya Matsunaga
 * @version $Revision: 1.0 $
 *
 * @ejb:bean name="Zaiko"
 *           display-name="Zaiko"
 *           type="CMP"
 *           jndi-name="ejb/Zaiko"
 *           local-jndi-name="ejb/ZaikoLocal"
 *           view-type="both"
 *
 * @ejb:ejb-ref ejb-name="KeyGenerator"
 *              view-type="local"
 * @ejb:ejb-ref ejb-name="Zaiko"
 *              view-type="local"
 * @ejb:ejb-ref ejb-name="Zaikomeisai"
 *              view-type="local"
 * @ejb:ejb-ref ejb-name="Nyuuko"
 *              view-type="local"
 * @ejb:ejb-ref ejb-name="Shukko"
 *              view-type="local"
 * @ejb:ejb-ref ejb-name="Shouhin"
 *              view-type="local"
 *
 * @ejb:finder signature="Collection findByZaikodate(java.util.Date start, java.util.Date end, java.lang.String souko_id, java.lang.String shouhin_id)" query="SELECT OBJECT(z) from Zaiko z WHERE (z.zaikodate = ?1 or z.zaikodate > ?1) and z.zaikodate < ?2 and z.souko_id = ?3 and z.shouhin_id = ?4" result-type-mapping="Local"
 *
 * @ejb:finder signature="ZaikoLocal findByNext_zaiko_id(java.lang.String souko_id, java.lang.String shouhin_id, int next_zaiko_date)" query="SELECT OBJECT(z) from Zaiko z WHERE z.souko_id = ?1 and z.shouhin_id = ?2 and z.next_zaiko_id = ?3" result-type-mapping="Local"
 *
 * @ejb:finder signature="ZaikoLocal findByShukko_id(int shukko_id)" query="SELECT OBJECT(z) from Zaiko z WHERE z.shukko_id = ?1" result-type-mapping="Local"
 *
 * @ejb:finder signature="ZaikoLocal findByNyuuko_id(int nyuuko_id)" query="SELECT OBJECT(z) from Zaiko z WHERE z.nyuuko_id = ?1" result-type-mapping="Local"
 *
 * @ejb.persistence table-name="zt_zaiko"
 *
 * @jboss:persistence pk-constraint="true"
 *
 **/
public abstract class ZaikoBean implements EntityBean {
    private EntityContext entityContext;

    /**
    * @ejb:persistent-field
    * @ejb:pk-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract int getZaiko_id();
    public abstract void setZaiko_id(int zaiko_id);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract java.util.Date getZaikodate();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setZaikodate(java.util.Date zaikodate);
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
    public abstract float getSuuryou();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setSuuryou(float suuryou);
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
    public abstract int getNyuuko_id();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setNyuuko_id(int nyuuko_id);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract int getShukko_id();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setShukko_id(int shukko_id);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract int getNext_zaiko_id();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setNext_zaiko_id(int next_zaiko_id);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract int getZorder();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setZorder(int zorder);
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
    public ZaikoPK ejbCreate(java.util.Date zaikodate,
        String souko_id, String shouhin_id, float suuryou, float tanka,
        float kingaku, int nyuuko_id, int shukko_id, int zorder, int user_id)
      throws CreateException {
        int zaiko_id = getNextZaiko_id();
        setZaiko_id(zaiko_id);
        setZaikodate(zaikodate);
        setSouko_id(souko_id);
        setShouhin_id(shouhin_id);
        setSuuryou(suuryou);
        setTanka(tanka);
        setKingaku(kingaku);
        setNyuuko_id(nyuuko_id);
        setShukko_id(shukko_id);
        setNext_zaiko_id(0);
        setZorder(zorder);
        setCreated(new java.util.Date());
        setCreatedby(user_id);
        setUpdated(new java.util.Date());
        setUpdatedby(user_id);
        return null;
    }
    public void ejbPostCreate(java.util.Date zaikodate,
        String souko_id, String shouhin_id, float suuryou, float tanka,
        float kingaku, int nyuuko_id, int shukko_id, int zorder, int user_id) {}
    public void setEntityContext(EntityContext ctx) {
        entityContext = ctx;
    }
    public void unsetEntityContext() {}
    public void ejbRemove() throws javax.ejb.RemoveException {}
    public void ejbActivate() {}
    public void ejbPassivate() {}
    public void ejbLoad() {}
    public void ejbStore() {}

    private int getNextZaiko_id() {
        int id = 0;
        try {
            InitialContext ic = new InitialContext();
            Object objref = ic.lookup("java:comp/env/ejb/KeyGeneratorLocal");
            KeyGeneratorLocalHome kgh = (KeyGeneratorLocalHome)objref;
            KeyGeneratorLocal kg = kgh.create();
            id = kg.getNext("ZS_ZAIKO");
        } catch (NamingException ex) {
            Debug.println(ex);
        } catch (CreateException ex) {
            Debug.println(ex);
        }
        return id;
    }

    /**
    * @ejb:interface-method view-type="local"
    **/
    public void update(ZaikoData prezaiko, Collection premeisais, int user_id)
//      throws ShukkoException, ZaikoException, ZaikomeisaiException {
      throws HaraidashiException, ZaikoException {
        float suuryou = prezaiko.getSuuryou();
        float tanka = prezaiko.getTanka();
        float kingaku = prezaiko.getKingaku();
        try {
            deleteMeisai();
        } catch (RemoveException re) {
            entityContext.setRollbackOnly();
            throw new ZaikoException(re);
        } catch (NamingException ne) {
            entityContext.setRollbackOnly();
            throw new ZaikoException(ne);
        }
        LinkedList meisais = null;
        ZaikomeisaiLocalHome zmhome = null;
        try {
            zmhome = (ZaikomeisaiLocalHome)ServiceLocator.getLocalHome(
              "java:comp/env/ejb/ZaikomeisaiLocal");
        } catch (NamingException ne) {
            entityContext.setRollbackOnly();
            throw new ZaikoException(ne);
        }
        if (premeisais != null) {
            Iterator premeisaiiter = premeisais.iterator();
            meisais = new LinkedList();
            while (premeisaiiter.hasNext()) {
                ZaikomeisaiData prezm = (ZaikomeisaiData)premeisaiiter.next();
                ZaikomeisaiLocal zm = null;
                try {
                     zm = zmhome.create(prezm, getZaiko_id(), user_id);
                } catch (CreateException ce) {
                    entityContext.setRollbackOnly();
                    throw new ZaikoException(ce);
                }
                meisais.add(zm);
            }
        }
        //入庫
        NyuukoLocal nyuuko = null;
        try {
            nyuuko = getNyuuko();
        } catch (NamingException ne) {
            entityContext.setRollbackOnly();
            throw new ZaikoException(ne);
        }
        if (nyuuko != null) {
            suuryou += nyuuko.getNyuukosuuryou();
            kingaku += nyuuko.getNyuukosuuryou() * nyuuko.getNyuukotanka();
            tanka = 0;
            if (suuryou != 0) {
                tanka = kingaku / suuryou;
            }
            ShouhinLocal sh = null;
            try {
                sh = getShouhin();
            } catch (NamingException ne) {
                entityContext.setRollbackOnly();
                throw new ZaikoException(ne);
            } catch (FinderException fe) {
                entityContext.setRollbackOnly();
                throw new ZaikoException(fe);
            }
            //if (meisais != null) {
            if (sh.getShoumikigen_flg() == Names.ON) {
                java.util.Date shoumi = nyuuko.getShoumikigen();
                boolean match = false;
                if (meisais != null) {
                    Iterator meisaiiter = meisais.iterator();
                    while (meisaiiter.hasNext()) {
                        ZaikomeisaiLocal zm =
                          (ZaikomeisaiLocal)meisaiiter.next();
                        //ロケーション対応にはここを変更
                        if (shoumi == null ||
                          zm.getShoumikigen().equals(shoumi)) {
                            zm.addSuuryou(nyuuko.getNyuukosuuryou(), user_id);
                            match = true;
                            break;
                        }
                    }
                } else {
                    meisais = new LinkedList();
                }
                if (match == false) {
                    //在庫にない賞味期限が入庫されたので新規に明細作成
                    ZaikomeisaiLocal zm = null;
                    try {
                        zm = zmhome.create(
                          getZaiko_id(),
                          getSouko_id(),
                          getShouhin_id(),
                          "000001", //ダミーロケーションコード
                          shoumi,
                          nyuuko.getNyuukosuuryou(),
                          nyuuko.getShukkakigen(),
                          user_id);
                    } catch (CreateException ce) {
                        entityContext.setRollbackOnly();
                        throw new ZaikoException(ce);
                    }
                    meisais.add(zm);
                }
            }
        }
        //出庫
        ShukkoLocal shukko = null;
        try {
            shukko = getShukko();
        } catch (NamingException ne) {
            entityContext.setRollbackOnly();
            throw new ZaikoException(ne);
        }
        if (shukko != null) {
            if (shukko.getTankakubun() == 0) {
                shukko.setTanka(tanka);
                shukko.setKingaku(shukko.getSuuryou() * tanka);
                shukko.setUpdated(new java.util.Date());
                shukko.setUpdatedby(user_id);
            }
            suuryou -= shukko.getSuuryou();
            kingaku -= shukko.getKingaku();
            tanka = 0;
            if (suuryou != 0) {
                tanka = kingaku / suuryou;
            }
            if (meisais != null) {
                java.util.Date shoumi = shukko.getShoumikigen();
                String location_id = shukko.getLocation_id();
                Iterator meisaiiter = meisais.iterator();
                boolean match = false;
                while (meisaiiter.hasNext()) {
                    ZaikomeisaiLocal zm = (ZaikomeisaiLocal)meisaiiter.next();
                    if ((shoumi == null ||
                        zm.getShoumikigen().equals(shoumi)) &&
                      (zm.getLocation_id().equals(location_id))) {
                        // for Exception
                        ZaikomeisaiData preZaikomeisai = zm.getZaikomeisaiData();
                        try {
                            zm.subSuuryou(shukko.getSuuryou(), user_id);
                        } catch (ZaikomeisaiException zme) {
                            ShukkoData errorsd = shukko.getShukkoData();
                            entityContext.setRollbackOnly();
                            throw new ZmeisaiSuuryouHaraidashiException(
                              preZaikomeisai, errorsd);
                        }
                        if (zm.getSuuryou() == 0) {
                            try {
                                zm.remove();
                            } catch (RemoveException re) {
                                entityContext.setRollbackOnly();
                                throw new ZaikoException(re);
                            }
                        }
                        match = true;
                        break;
                    }
                }
                if (match == false) {
                    ShukkoData errorsd = shukko.getShukkoData();
                    //在庫にない賞味期限が出庫された
                    entityContext.setRollbackOnly();
                    //ロールバックしてからEJBにアクセスすると
                    //RuntimeExceptionになる
                    //throw new ZmeisaiShoumiHaraidashiException(
                    //  shukko.getShukkoData());
                    throw new ZmeisaiShoumiHaraidashiException(errorsd);
                }
            }
        }
        // for Exception
        ShukkoData shukkoData = null;
        if (shukko != null) {
            shukkoData = shukko.getShukkoData();
        }
        if (suuryou < 0) {
            entityContext.setRollbackOnly();
            throw new ZaikoSuuryouHaraidashiException(
              prezaiko, shukkoData);
        }
        if (tanka < 0) {
            entityContext.setRollbackOnly();
            throw new ZaikoTankaHaraidashiException(
              prezaiko, shukkoData);
        }
        if (kingaku < 0) {
            entityContext.setRollbackOnly();
            throw new ZaikoKingakuHaraidashiException(
              prezaiko, shukkoData);
        }
        setSuuryou(suuryou);
        setTanka(tanka);
        setKingaku(kingaku);
        setZorder(prezaiko.getZorder() + 1);
        setUpdated(new java.util.Date());
        setUpdatedby(user_id);
        if (getNext_zaiko_id() != 0) {
            ZaikoLocal nextzaiko = null;
            ZaikoData thiszaiko = getZaikoData();
            Collection thismeisais = null;
            try {
                thismeisais = getZaikomeisaiData();
                nextzaiko = getNextZaiko();
            } catch (NamingException ne) {
                entityContext.setRollbackOnly();
                throw new ZaikoException(ne);
            }
            nextzaiko.update(thiszaiko, thismeisais, user_id);
        }
    }

    /**
    * @ejb:interface-method view-type="both"
    **/
    public void deleteMeisai() throws NamingException, RemoveException {
        Collection meisais = getZaikomeisai();
        if (meisais != null) {
            Iterator meisaiiter = meisais.iterator();
            while (meisaiiter.hasNext()) {
                ZaikomeisaiLocal zm = (ZaikomeisaiLocal)meisaiiter.next();
                zm.remove();
            }
        }
    }

    /**
    * @ejb:interface-method view-type="local"
    * 在庫明細がない場合はnullを返す。
    **/
    public Collection getZaikomeisai() throws NamingException {
        ZaikomeisaiLocalHome zmhome = (ZaikomeisaiLocalHome)
          ServiceLocator.getLocalHome("java:comp/env/ejb/ZaikomeisaiLocal");
        Collection meisais = null;
        try {
            meisais = zmhome.findByZaiko_id(getZaiko_id());
        } catch (FinderException fe) {
            //無視
        }
        if (meisais.isEmpty()) { return null; }
        return meisais;
    }

    /**
    * @ejb:interface-method view-type="local"
    * 次の在庫が存在しない場合はnullを返す
    **/
    public ZaikoLocal getNextZaiko() throws NamingException {
        ZaikoPK pk = new ZaikoPK(getNext_zaiko_id());
        ZaikoLocalHome zhome = (ZaikoLocalHome)ServiceLocator.getLocalHome(
          "java:comp/env/ejb/ZaikoLocal");
        ZaikoLocal next = null;
        try {
            next = zhome.findByPrimaryKey(pk);
        } catch (FinderException fe) {
            //無視
        }
        return next;
    }

    /**
    * @ejb:interface-method view-type="local"
    * 前の在庫が存在しない場合はnullを返す
    **/
    public ZaikoLocal getPreviousZaiko() throws NamingException {
        ZaikoLocalHome zhome = (ZaikoLocalHome)ServiceLocator.getLocalHome(
          "java:comp/env/ejb/ZaikoLocal");
        ZaikoLocal prev = null;
        try {
            prev = zhome.findByNext_zaiko_id(
              getSouko_id(), getShouhin_id(), getZaiko_id());
        } catch (FinderException fe) {
            //無視
        }
        return prev;
    }

    /**
    * @ejb:interface-method view-type="local"
    * 入庫が存在しない場合はnullを返す
    **/
    public NyuukoLocal getNyuuko() throws NamingException {
        NyuukoLocalHome nhome = (NyuukoLocalHome)
          ServiceLocator.getLocalHome("java:comp/env/ejb/NyuukoLocal");
        NyuukoPK pk = new NyuukoPK(getNyuuko_id());
        NyuukoLocal nk = null;
        try {
            nk = nhome.findByPrimaryKey(pk);
        } catch (FinderException fe) {
            //無視
        }
        return nk;
    }

    /**
    * @ejb:interface-method view-type="local"
    * 出庫が存在しない場合はnullを返す
    **/
    public ShukkoLocal getShukko() throws NamingException {
        ShukkoLocalHome shome = (ShukkoLocalHome)
          ServiceLocator.getLocalHome("java:comp/env/ejb/ShukkoLocal");
        ShukkoPK pk = new ShukkoPK(getShukko_id());
        ShukkoLocal sk = null;
        try {
            sk = shome.findByPrimaryKey(pk);
        } catch (FinderException fe) {
            //無視
        }
        return sk;
    }

    /**
    * @ejb:interface-method view-type="local"
    * 再帰呼び出しのためのテストメソッド。
    * 自分自身を別のインスタンスに渡して、その渡し先から自分自身のメソッドを
    * callするとlock状態になりtimeoutする。そのため、自分自身のDataを
    * 渡すことで回避する。
    **/
    /*
    public void recursiveTest(ZaikoData prezaiko, int flg) throws Exception {
        int id = prezaiko.getZaiko_id();
        System.out.println("id:" + id);
        if (flg == 0) { return; }
        System.out.println("recursivetest retrieving next zaiko");
        ZaikoLocal nextzaiko = getNextZaiko();
        ZaikoData thiszaiko = getZaikoData();
        nextzaiko.recursiveTest(thiszaiko, 0);
    }
    */

    /**
    * @ejb:interface-method view-type="both"
    **/
    public ZaikoData getZaikoData() {
        ZaikoData zaikoData = new ZaikoData(
          this.getZaiko_id(),
          this.getZaikodate(),
          this.getSouko_id(),
          this.getShouhin_id(),
          this.getSuuryou(),
          this.getTanka(),
          this.getKingaku(),
          this.getNyuuko_id(),
          this.getShukko_id(),
          this.getNext_zaiko_id(),
          this.getZorder(),
          this.getCreated(),
          this.getCreatedby(),
          this.getUpdated(),
          this.getUpdatedby()
        );
        return zaikoData;
    }

    /**
    * @ejb:interface-method view-type="local"
    **/
    public Collection getZaikomeisaiData() throws NamingException {
        Collection meisailocal = getZaikomeisai();
        if (meisailocal == null) { return null; }
        LinkedList meisaidata = new LinkedList();
        Iterator mliter = meisailocal.iterator();
        while (mliter.hasNext()) {
            ZaikomeisaiLocal zm = (ZaikomeisaiLocal)mliter.next();
            ZaikomeisaiData zdata = zm.getZaikomeisaiData();
            meisaidata.add(zdata);
        }
        return meisaidata;
    }

    /**
    * @ejb:interface-method view-type="local"
    **/
    public void haraidashi(ShukkoData shukko) throws HaraidashiException {
        Collection meisais = null;
        try {
            meisais = getZaikomeisaiData();
        } catch (NamingException ne) {
            throw new HaraidashiException(ne);
        }
        if (meisais != null) {
            //明細があれば数量変更
            Iterator meisaiiter = meisais.iterator();
            boolean match = false;
            while (meisaiiter.hasNext()) {
                ZaikomeisaiData meisai = (ZaikomeisaiData)meisaiiter.next();
                if ((shukko.getShoumikigen() == null ||
                  meisai.getShoumikigen().equals(shukko.getShoumikigen()))
                  && (meisai.getLocation_id().equals(shukko.getLocation_id())))
                  {
                    // for Exception
                    ZaikomeisaiData preMeisai = new ZaikomeisaiData(meisai);
                    meisai.setSuuryou(
                      meisai.getSuuryou() - shukko.getSuuryou());
                    if (meisai.getSuuryou() < 0) {
                        // 新規なので出庫番号を空にする
                        shukko.setShukko_bg("");
                        throw new ZmeisaiSuuryouHaraidashiException(
                          preMeisai, shukko);
                    }
                    match = true;
                }
            }
            if (match == false) {
                // 新規なので出庫番号を空にする
                shukko.setShukko_bg("");
                throw new ZmeisaiShoumiHaraidashiException(shukko);
            }
        }
        float suuryou = getSuuryou();
        suuryou -= shukko.getSuuryou();
        float kingaku = getKingaku();
        kingaku -= shukko.getKingaku();
        float tanka = 0;
        if (suuryou != 0) {
            tanka = kingaku / suuryou;
        }
        if (suuryou < 0) {
            entityContext.setRollbackOnly();
            // 新規なので出庫番号を空にする
            shukko.setShukko_bg("");
            throw new ZaikoSuuryouHaraidashiException(
              getZaikoData(), shukko);
        }
        if (tanka < 0) {
            entityContext.setRollbackOnly();
            // 新規なので出庫番号を空にする
            shukko.setShukko_bg("");
            throw new ZaikoTankaHaraidashiException(
              getZaikoData(), shukko);
        }
        ZaikoLocalHome zhome = null;
        ZaikoLocal zaiko = null;
        try {
            zhome = (ZaikoLocalHome)ServiceLocator.getLocalHome(
              "java:comp/env/ejb/ZaikoLocal");
            zaiko = zhome.create(
              shukko.getShukko_date(),
              getSouko_id(),
              getShouhin_id(),
              suuryou,
              tanka,
              kingaku,
              0, //nyuuko_id
              shukko.getShukko_id(),
              getZorder() + 1,
              shukko.getUpdatedby()
            );
        } catch (NamingException ne) {
            throw new HaraidashiException(ne);
        } catch (CreateException ce) {
            throw new HaraidashiException(ce);
        }
        try {
            zaiko.createMeisai(meisais, shukko.getUpdatedby());
            meisais = zaiko.getZaikomeisaiData();
        } catch (NamingException ne) {
            entityContext.setRollbackOnly();
            throw new HaraidashiException(ne);
        } catch (CreateException ce) {
            entityContext.setRollbackOnly();
            throw new HaraidashiException(ce);
        }
        if (getNext_zaiko_id() != 0) {
            zaiko.setNext_zaiko_id(getNext_zaiko_id());
            try {
                ZaikoLocal nextzaiko = getNextZaiko();
                ZaikoData prezaiko = zaiko.getZaikoData();
                nextzaiko.update(prezaiko, meisais, shukko.getUpdatedby());
            } catch (NamingException ne) {
                entityContext.setRollbackOnly();
                throw new HaraidashiException(ne);
            } catch (HaraidashiException he) {
                entityContext.setRollbackOnly();
                throw he;
//            } catch (ShukkoException se) {
//                entityContext.setRollbackOnly();
//                throw new HaraidashiException(se);
            } catch (ZaikoException ze) {
                entityContext.setRollbackOnly();
                throw new HaraidashiException(ze);
//            } catch (ZaikomeisaiException ze) {
//                entityContext.setRollbackOnly();
//                throw new HaraidashiException(ze);
            }
        }
        setNext_zaiko_id(zaiko.getZaiko_id());
    }

    /**
    * @ejb:interface-method view-type="both"
    **/
    public void createMeisai(Collection meisais, int user_id)
      throws NamingException, CreateException {
        if (meisais != null) {
            ZaikomeisaiLocalHome zmhome = (ZaikomeisaiLocalHome)
              ServiceLocator.getLocalHome(
              "java:comp/env/ejb/ZaikomeisaiLocal");
            Iterator meisaiiter = meisais.iterator();
            while (meisaiiter.hasNext()) {
                ZaikomeisaiData zm = (ZaikomeisaiData)meisaiiter.next();
                if (zm.getSuuryou() != 0) {
                    zmhome.create(zm, getZaiko_id(), user_id);
                }
            }
        }
    }

    /**
    * @ejb:interface-method view-type="local"
    **/
    public void ukeire(NyuukoData nyuuko, int shoumikigen_flg)
      throws UkeireException {
        Collection meisais = null;
        try {
            meisais = getZaikomeisaiData();
        } catch (NamingException ne) {
            throw new UkeireException(ne);
        }
        //if (meisais != null) {
        if (shoumikigen_flg == Names.ON) {
            //賞味期限管理対象商品なら在庫明細も更新
            boolean match = false;
            if (meisais != null) {
                Iterator meisaiiter = meisais.iterator();
                while (meisaiiter.hasNext()) {
                    ZaikomeisaiData meisai =
                      (ZaikomeisaiData)meisaiiter.next();
                    //if ((meisai.getShoumikigen().equals(nyuuko.getShoumikigen()))
                    //&& (meisai.getLocation_id().equals(nyuuko.getLocation_id())))
                    if (nyuuko.getShoumikigen() == null ||
                      meisai.getShoumikigen().equals(nyuuko.getShoumikigen())) {
                        // とりあえずロケーション無視
                        meisai.setSuuryou(
                          meisai.getSuuryou() + nyuuko.getNyuukosuuryou());
                        match = true;
                    }
                }
            } else {
                meisais = new LinkedList();
            }
            if (match == false) {
                ZaikomeisaiData meisai = new ZaikomeisaiData(
                  0, //zaikomeisai_id
                  0, //zaiko_id
                  getSouko_id(),
                  getShouhin_id(),
                  "000001", //ダミーロケーションコード
                  nyuuko.getShoumikigen(),
                  nyuuko.getNyuukosuuryou(),
                  nyuuko.getShukkakigen(),
                  new java.util.Date(),
                  getCreatedby(),
                  new java.util.Date(),
                  getCreatedby()
                );
                meisais.add(meisai);
            }
        }
        float suuryou = getSuuryou();
        suuryou += nyuuko.getNyuukosuuryou();
        float kingaku = getKingaku();
        float ntanka = nyuuko.getNyuukotanka();
        kingaku += nyuuko.getNyuukosuuryou() * ntanka;
        float tanka = 0;
        if (suuryou != 0) {
            tanka = kingaku / suuryou;
        }
        ZaikoLocalHome zhome = null;
        ZaikoLocal zaiko = null;
        try {
            zhome = (ZaikoLocalHome)ServiceLocator.getLocalHome(
              "java:comp/env/ejb/ZaikoLocal");
            zaiko = zhome.create(
              nyuuko.getNyuuko_date(),
              getSouko_id(),
              getShouhin_id(),
              suuryou,
              tanka,
              kingaku,
              nyuuko.getNyuuko_id(),
              0, //shukko_id
              getZorder() + 1,
              nyuuko.getUpdatedby()
            );
        } catch (NamingException ne) {
            throw new UkeireException(ne);
        } catch (CreateException ce) {
            throw new UkeireException(ce);
        }
        try {
            zaiko.createMeisai(meisais, nyuuko.getUpdatedby());
            meisais = zaiko.getZaikomeisaiData();
        } catch (NamingException ne) {
            entityContext.setRollbackOnly();
            throw new UkeireException(ne);
        } catch (CreateException ce) {
            entityContext.setRollbackOnly();
            throw new UkeireException(ce);
        }
        if (getNext_zaiko_id() != 0) {
            zaiko.setNext_zaiko_id(getNext_zaiko_id());
            try {
                ZaikoLocal nextzaiko = getNextZaiko();
                ZaikoData prezaiko = zaiko.getZaikoData();
                nextzaiko.update(prezaiko, meisais, nyuuko.getUpdatedby());
            } catch (NamingException ne) {
                entityContext.setRollbackOnly();
                throw new UkeireException(ne);
            } catch (HaraidashiException he) {
                entityContext.setRollbackOnly();
                throw new UkeireException(he);
//            } catch (ShukkoException se) {
//                entityContext.setRollbackOnly();
//                throw new UkeireException(se);
            } catch (ZaikoException ze) {
                entityContext.setRollbackOnly();
                throw new UkeireException(ze);
//            } catch (ZaikomeisaiException ze) {
//                entityContext.setRollbackOnly();
//                throw new UkeireException(ze);
            }
        }
        setNext_zaiko_id(zaiko.getZaiko_id());
    }

    /**
    * @ejb:interface-method view-type="local"
    * 出庫が存在しない場合はnullを返す
    **/
    public ShouhinLocal getShouhin() throws NamingException, FinderException {
        ShouhinLocalHome shome = (ShouhinLocalHome)
          ServiceLocator.getLocalHome("java:comp/env/ejb/ShouhinLocal");
        ShouhinLocal s = shome.findByPrimaryKey(getShouhin_id());
        return s;
    }

}
