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
 * The Entity bean represents Shouhin
 *
 * @author Ryuya Matsunaga
 * @version $Revision: 1.0 $
 *
 * @ejb:bean name="Shouhin"
 *           display-name="Shouhin"
 *           type="CMP"
 *           primkey-field="shouhin_id"
 *           jndi-name="ejb/Shouhin"
 *           local-jndi-name="ejb/ShouhinLocal"
 *           view-type="both"
 *
 * @ejb:pk class="java.lang.String"
 *
 * @ejb:ejb-ref ejb-name="Shiiresaki"
 *              view-type="local"
 * @ejb:ejb-ref ejb-name="Tani"
 *              view-type="local"
 * @ejb:ejb-ref ejb-name="Ondotai"
 *              view-type="local"
 *
 * @ejb:finder signature="Collection findByYouchuuihin_flg(int youchuuihin_flg)" query="SELECT OBJECT(s) from Shouhin s WHERE s.youchuuihin_flg = ?1" result-type-mapping="Local"
 *
 * @ejb.persistence table-name="zm_shouhin"
 *
 * @jboss:persistence pk-constraint="true"
 *
 **/
public abstract class ShouhinBean implements EntityBean {
    private EntityContext entityContext;

    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract String getShouhin_id();
    public abstract void setShouhin_id(String shouhin_id);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract String getShouhin();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setShouhin(String shouhin);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract String getShouhinfurigana();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setShouhinfurigana(String shouhinfurigana);
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
    public abstract String getHacchuushouhin1();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setHacchuushouhin1(String hacchuushouhin1);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract String getHacchuushouhin2();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setHacchuushouhin2(String hacchuushouhin2);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract String getHacchuushouhin3();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setHacchuushouhin3(String hacchuushouhin3);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract String getKikaku();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setKikaku(String kikaku);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract int getTani();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setTani(int tani);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract String getHacchuukikaku();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setHacchuukikaku(String hacchuukikaku);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract int getHacchuuten();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setHacchuuten(int hacchuuten);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract int getHacchuutani();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setHacchuutani(int hacchuutani);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract float getIrisuu();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setIrisuu(float irisuu);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract int getHacchuutanisuu();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setHacchuutanisuu(int hacchuutanisuu);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract int getSaiteihacchuusuu();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setSaiteihacchuusuu(int saiteihacchuusuu);
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
    public abstract float getHyoujuntanka();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setHyoujuntanka(float hyoujuntanka);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract int getShiireleadtime();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setShiireleadtime(int shiireleadtime);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract int getOndotai();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setOndotai(int ondotai);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract int getShoumikigen_flg();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setShoumikigen_flg(int shoumikigen_flg);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract int getShoumikigennissuu();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setShoumikigennissuu(int shoumikigennissuu);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract int getShukkakigennissuu();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setShukkakigennissuu(int shukkakigennissuu);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract int getKobetsuhacchuu_flg();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setKobetsuhacchuu_flg(int kobetsuhacchuu_flg);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract int getYouchuuihin_flg();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setYouchuuihin_flg(int youchuuihin_flg);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract int getYouraberu_flg();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setYouraberu_flg(int youraberu_flg);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract String getDaibunrui();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setDaibunrui(String daibunrui);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract String getPcode();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setPcode(String pcode);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract int getZaikohyoukahouhou();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setZaikohyoukahouhou(int zaikohyoukahouhou);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract String getJancode();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setJancode(String jancode);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract String getKataban();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setKataban(String kataban);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract String getLocation_id1();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setLocation_id1(String location_id1);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract String getLocation_id2();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setLocation_id2(String location_id2);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract String getLocation_id3();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setLocation_id3(String location_id3);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract int getShuubai_flg();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setShuubai_flg(int shuubai_flg);
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
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract String getNisugata();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setNisugata(String nisugata);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract String getHacchuucomment();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setHacchuucomment(String hacchuucomment);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract float getIrisuurireki1();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setIrisuurireki1(float irisuurireki1);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract int getHanbaikubun();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setHanbaikubun(int hanbaikubun);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract String getShubetsu();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setShubetsu(String shubetsu);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract int getMochikoshi_flg();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setMochikoshi_flg(int mochikoshi_flg);
    /**
    * @ejb:persistent-field
    * @ejb:interface-method view-type="both"
    **/
    public abstract int getShinyanouhin_flg();
    /**
    * @ejb:interface-method view-type="both"
    **/
    public abstract void setShinyanouhin_flg(int shinyanouhin_flg);
    /**
    * @ejb:create-method view-type="both"
    **/
    public String ejbCreate(
      String shouhin_id,
      String shouhin,
      String shouhinfurigana,
      String shiiresaki_id,
      String hacchuushouhin1,
      String hacchuushouhin2,
      String hacchuushouhin3,
      String kikaku,
      int tani,
      String hacchuukikaku,
      int hacchuuten, int hacchuutani,
      float irisuu,
      int hacchuutanisuu,
      int saiteihacchuusuu,
      float tanka,
      float hyoujuntanka,
      int shiireleadtime,
      int ondotai,
      int shoumikigen_flg,
      int shoumikigennissuu,
      int shukkakigennissuu,
      int kobetsuhacchuu_flg,
      int youchuuihin_flg,
      int youraberu_flg,
      String daibunrui,
      String pcode,
      int zaikohyoukahouhou,
      String jancode,
      String kataban,
      String location_id1,
      String location_id2,
      String location_id3,
      int shuubai_flg,
      int user_id,
      float hyoujunbaika,
      String nisugata,
      String hacchuucomment,
      float irisuurireki1,
      int hanbaikubun,
      String shubetsu,
      int mochikoshi_flg,
      int shinyanouhin_flg
      )
      throws CreateException {
        setShouhin_id(shouhin_id);
        setShouhin(shouhin);
        setShouhinfurigana(shouhinfurigana);
        setShiiresaki_id(shiiresaki_id);
        setHacchuushouhin1(hacchuushouhin1);
        setHacchuushouhin2(hacchuushouhin2);
        setHacchuushouhin3(hacchuushouhin3);
        setKikaku(kikaku);
        setTani(tani);
        setHacchuukikaku(hacchuukikaku);
        setHacchuuten(hacchuuten);
        setHacchuutani(hacchuutani);
        setIrisuu(irisuu);
        setHacchuutanisuu(hacchuutanisuu);
        setSaiteihacchuusuu(saiteihacchuusuu);
        setTanka(tanka);
        setHyoujuntanka(hyoujuntanka);
        setShiireleadtime(shiireleadtime);
        setOndotai(ondotai);
        setShoumikigen_flg(shoumikigen_flg);
        setShoumikigennissuu(shoumikigennissuu);
        setShukkakigennissuu(shukkakigennissuu);
        setKobetsuhacchuu_flg(kobetsuhacchuu_flg);
        setYouchuuihin_flg(youchuuihin_flg);
        setYouraberu_flg(youraberu_flg);
        setDaibunrui(daibunrui);
        setPcode(pcode);
        setZaikohyoukahouhou(zaikohyoukahouhou);
        setJancode(jancode);
        setKataban(kataban);
        setLocation_id1(location_id1);
        setLocation_id2(location_id2);
        setLocation_id3(location_id3);
        setShuubai_flg(shuubai_flg);
        setCreated(new java.util.Date());
        setCreatedby(user_id);
        setUpdated(new java.util.Date());
        setUpdatedby(user_id);
        setHyoujunbaika(hyoujunbaika);
        setNisugata(nisugata);
        setHacchuucomment(hacchuucomment);
        setIrisuurireki1(irisuurireki1);
        setHanbaikubun(hanbaikubun);
        setShubetsu(shubetsu);
        setMochikoshi_flg(mochikoshi_flg);
        setShinyanouhin_flg(shinyanouhin_flg);
        return null;
    }
    public void ejbPostCreate(
      String shouhin_id,
      String shouhin,
      String shouhinfurigana,
      String shiiresaki_id,
      String hacchuushouhin1,
      String hacchuushouhin2,
      String hacchuushouhin3,
      String kikaku,
      int tani,
      String hacchuukikaku,
      int hacchuuten,
      int hacchuutani,
      float irisuu,
      int hacchuutanisuu,
      int saiteihacchuusuu,
      float tanka,
      float hyoujuntanka,
      int shiireleadtime,
      int ondotai,
      int shoumikigen_flg,
      int shoumikigennissuu,
      int shukkakigennissuu,
      int kobetsuhacchuu_flg,
      int youchuuihin_flg,
      int youraberu_flg,
      String daibunrui,
      String pcode,
      int zaikohyoukahouhou,
      String jancode,
      String kataban,
      String location_id1,
      String location_id2,
      String location_id3,
      int shuubai_flg,
      int user_id,
      float hyoujunbaika,
      String nisugata,
      String hacchuucomment,
      float irisuurireki1,
      int hanbaikubun,
      String shubetsu,
      int mochikoshi_flg,
      int shinyanouhin_flg
      ) {}
    /**
    * @ejb:create-method view-type="both"
    **/
    public String ejbCreate(
      String shouhin_id,
      String shouhin,
      String shouhinfurigana,
      String shiiresaki_id,
      String hacchuushouhin1,
      String hacchuushouhin2,
      String hacchuushouhin3,
      String kikaku,
      int tani,
      String hacchuukikaku,
      int hacchuuten, int hacchuutani,
      float irisuu,
      int hacchuutanisuu,
      int saiteihacchuusuu,
      float tanka,
      float hyoujuntanka,
      int shiireleadtime,
      int ondotai,
      int shoumikigen_flg,
      int shoumikigennissuu,
      int shukkakigennissuu,
      int kobetsuhacchuu_flg,
      int youchuuihin_flg,
      int youraberu_flg,
      String daibunrui,
      String pcode,
      int zaikohyoukahouhou,
      String jancode,
      String kataban,
      String location_id1,
      String location_id2,
      String location_id3,
      int shuubai_flg,
      int user_id,
      float hyoujunbaika,
      String nisugata,
      String hacchuucomment,
      float irisuurireki1,
      int hanbaikubun,
      String shubetsu,
      int mochikoshi_flg
      )
      throws CreateException {
        setShouhin_id(shouhin_id);
        setShouhin(shouhin);
        setShouhinfurigana(shouhinfurigana);
        setShiiresaki_id(shiiresaki_id);
        setHacchuushouhin1(hacchuushouhin1);
        setHacchuushouhin2(hacchuushouhin2);
        setHacchuushouhin3(hacchuushouhin3);
        setKikaku(kikaku);
        setTani(tani);
        setHacchuukikaku(hacchuukikaku);
        setHacchuuten(hacchuuten);
        setHacchuutani(hacchuutani);
        setIrisuu(irisuu);
        setHacchuutanisuu(hacchuutanisuu);
        setSaiteihacchuusuu(saiteihacchuusuu);
        setTanka(tanka);
        setHyoujuntanka(hyoujuntanka);
        setShiireleadtime(shiireleadtime);
        setOndotai(ondotai);
        setShoumikigen_flg(shoumikigen_flg);
        setShoumikigennissuu(shoumikigennissuu);
        setShukkakigennissuu(shukkakigennissuu);
        setKobetsuhacchuu_flg(kobetsuhacchuu_flg);
        setYouchuuihin_flg(youchuuihin_flg);
        setYouraberu_flg(youraberu_flg);
        setDaibunrui(daibunrui);
        setPcode(pcode);
        setZaikohyoukahouhou(zaikohyoukahouhou);
        setJancode(jancode);
        setKataban(kataban);
        setLocation_id1(location_id1);
        setLocation_id2(location_id2);
        setLocation_id3(location_id3);
        setShuubai_flg(shuubai_flg);
        setCreated(new java.util.Date());
        setCreatedby(user_id);
        setUpdated(new java.util.Date());
        setUpdatedby(user_id);
        setHyoujunbaika(hyoujunbaika);
        setNisugata(nisugata);
        setHacchuucomment(hacchuucomment);
        setIrisuurireki1(irisuurireki1);
        setHanbaikubun(hanbaikubun);
        setShubetsu(shubetsu);
        setMochikoshi_flg(mochikoshi_flg);
        setShinyanouhin_flg(0);
        return null;
    }
    public void ejbPostCreate(
      String shouhin_id,
      String shouhin,
      String shouhinfurigana,
      String shiiresaki_id,
      String hacchuushouhin1,
      String hacchuushouhin2,
      String hacchuushouhin3,
      String kikaku,
      int tani,
      String hacchuukikaku,
      int hacchuuten,
      int hacchuutani,
      float irisuu,
      int hacchuutanisuu,
      int saiteihacchuusuu,
      float tanka,
      float hyoujuntanka,
      int shiireleadtime,
      int ondotai,
      int shoumikigen_flg,
      int shoumikigennissuu,
      int shukkakigennissuu,
      int kobetsuhacchuu_flg,
      int youchuuihin_flg,
      int youraberu_flg,
      String daibunrui,
      String pcode,
      int zaikohyoukahouhou,
      String jancode,
      String kataban,
      String location_id1,
      String location_id2,
      String location_id3,
      int shuubai_flg,
      int user_id,
      float hyoujunbaika,
      String nisugata,
      String hacchuucomment,
      float irisuurireki1,
      int hanbaikubun,
      String shubetsu,
      int mochikoshi_flg
      ) {}

    public String ejbCreate(ShouhinData data)
      throws CreateException {
        setShouhin_id(data.getShouhin_id());
        setShouhin(data.getShouhin());
        setShouhinfurigana(data.getShouhinfurigana());
        setShiiresaki_id(data.getShiiresaki_id());
        setHacchuushouhin1(data.getHacchuushouhin1());
        setHacchuushouhin2(data.getHacchuushouhin2());
        setHacchuushouhin3(data.getHacchuushouhin3());
        setKikaku(data.getKikaku());
        setTani(data.getTani());
        setHacchuukikaku(data.getHacchuukikaku());
        setHacchuuten(data.getHacchuuten());
        setHacchuutani(data.getHacchuutani());
        setIrisuu(data.getIrisuu());
        setHacchuutanisuu(data.getHacchuutanisuu());
        setSaiteihacchuusuu(data.getSaiteihacchuusuu());
        setTanka(data.getTanka());
        setHyoujuntanka(data.getHyoujuntanka());
        setShiireleadtime(data.getShiireleadtime());
        setOndotai(data.getOndotai());
        setShoumikigen_flg(data.getShoumikigen_flg());
        setShoumikigennissuu(data.getShoumikigennissuu());
        setShukkakigennissuu(data.getShukkakigennissuu());
        setKobetsuhacchuu_flg(data.getKobetsuhacchuu_flg());
        setYouchuuihin_flg(data.getYouchuuihin_flg());
        setYouraberu_flg(data.getYouraberu_flg());
        setDaibunrui(data.getDaibunrui());
        setPcode(data.getPcode());
        setZaikohyoukahouhou(data.getZaikohyoukahouhou());
        setJancode(data.getJancode());
        setKataban(data.getKataban());
        setLocation_id1(data.getLocation_id1());
        setLocation_id2(data.getLocation_id2());
        setLocation_id3(data.getLocation_id3());
        setShuubai_flg(data.getShuubai_flg());
        setCreated(data.getCreated());
        setCreatedby(data.getCreatedby());
        setUpdated(data.getUpdated());
        setUpdatedby(data.getUpdatedby());
        setHyoujunbaika(data.getHyoujunbaika());
        setNisugata(data.getNisugata());
        setHacchuucomment(data.getHacchuucomment());
        setIrisuurireki1(data.getIrisuurireki1());
        setHanbaikubun(data.getHanbaikubun());
        setShubetsu(data.getShubetsu());
        setMochikoshi_flg(data.getMochikoshi_flg());
        setShinyanouhin_flg(data.getShinyanouhin_flg());
        return null;
    }
    public void ejbPostCreate(ShouhinData data) {}

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
    public ShouhinData getShouhinData() {
        ShouhinData shouhinData = new ShouhinData(
          this.getShouhin_id(),
          this.getShouhin(),
          this.getShouhinfurigana(),
          this.getShiiresaki_id(),
          this.getHacchuushouhin1(),
          this.getHacchuushouhin2(),
          this.getHacchuushouhin3(),
          this.getKikaku(),
          this.getTani(),
          this.getHacchuukikaku(),
          this.getHacchuuten(),
          this.getHacchuutani(),
          this.getIrisuu(),
          this.getHacchuutanisuu(),
          this.getSaiteihacchuusuu(),
          this.getTanka(),
          this.getHyoujuntanka(),
          this.getShiireleadtime(),
          this.getOndotai(),
          this.getShoumikigen_flg(),
          this.getShoumikigennissuu(),
          this.getShukkakigennissuu(),
          this.getKobetsuhacchuu_flg(),
          this.getYouchuuihin_flg(),
          this.getYouraberu_flg(),
          this.getDaibunrui(),
          this.getPcode(),
          this.getZaikohyoukahouhou(),
          this.getJancode(),
          this.getKataban(),
          this.getLocation_id1(),
          this.getLocation_id2(),
          this.getLocation_id3(),
          this.getShuubai_flg(),
          this.getCreated(),
          this.getCreatedby(),
          this.getUpdated(),
          this.getUpdatedby(),
          this.getHyoujunbaika(),
          this.getNisugata(),
          this.getHacchuucomment(),
          this.getIrisuurireki1(),
          this.getHanbaikubun(),
          this.getShubetsu(),
          this.getMochikoshi_flg(),
          this.getShinyanouhin_flg()
        );
        return shouhinData;
    }

    /**
    * @ejb:interface-method view-type="local"
    **/
    public ShiiresakiLocal getShiiresaki()
      throws NamingException, FinderException {
        ShiiresakiLocalHome slh =
          (ShiiresakiLocalHome)ServiceLocator.getLocalHome(
          "java:comp/env/ejb/ShiiresakiLocal");
        return slh.findByPrimaryKey(getShiiresaki_id());
    }

    /**
    * @ejb:interface-method view-type="local"
    **/
    public TaniLocal getTaniLocal()
      throws NamingException, FinderException {
        TaniLocalHome tlh = (TaniLocalHome)ServiceLocator.getLocalHome(
          "java:comp/env/ejb/TaniLocal");
        TaniPK pk = new TaniPK(getTani());
        return tlh.findByPrimaryKey(pk);
    }

    /**
    * @ejb:interface-method view-type="local"
    **/
    public TaniLocal getHacchuutaniLocal()
      throws NamingException, FinderException {
        TaniLocalHome tlh = (TaniLocalHome)ServiceLocator.getLocalHome(
          "java:comp/env/ejb/TaniLocal");
        TaniPK pk = new TaniPK(getHacchuutani());
        return tlh.findByPrimaryKey(pk);
    }

    /**
    * @ejb:interface-method view-type="local"
    **/
    public OndotaiLocal getOndotaiLocal()
      throws NamingException, FinderException {
        OndotaiLocalHome olh =
          (OndotaiLocalHome)ServiceLocator.getLocalHome(
          "java:comp/env/ejb/OndotaiLocal");
        OndotaiPK pk = new OndotaiPK(getOndotai());
        return olh.findByPrimaryKey(pk);
    }
}
