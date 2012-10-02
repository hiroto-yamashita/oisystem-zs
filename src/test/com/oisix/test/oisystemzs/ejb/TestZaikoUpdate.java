package com.oisix.test.oisystemzs.ejb;

import com.oisix.oisystemfr.ejb.*;
import com.oisix.oisystemzs.ejb.*;
import junit.framework.Test;
import junit.framework.TestSuite;
import java.sql.*;
import java.util.*;
import javax.naming.*;
import javax.ejb.*;

import org.apache.cactus.Cookie;
import org.apache.cactus.ServletTestCase;
import org.apache.cactus.WebRequest;
import org.apache.cactus.WebResponse;

import java.io.IOException;

/**
 * Tests of the <code>SampleServlet</code> servlet class.
 *
 * @author Yamashita
 */
public class TestZaikoUpdate extends ServletTestCase {

    private ZaikoLocalHome zhome;
    private ZaikomeisaiLocalHome zmhome;
    private ZaikoLocal target;
    private ZaikomeisaiLocal zmb1;
    private ZaikoLocal zaiko2;  //with nyuuko
    private NyuukoLocal nyuuko2;
    private ZaikoLocal zaiko3;  //with shukko (自動単価)
    private ShukkoLocal shukko3;
    private ZaikoLocal zaiko4;  //with shukko (固定単価)
    private ShukkoLocal shukko4;
    private String souko_id;
    private String shouhin_id;
    private String location_id;
    private java.util.Date shoumikigen1;
    private java.util.Date shoumikigen2;
    private java.util.Date shukkakigen1;
    private java.util.Date shukkakigen2;
    private java.util.Date today;
    private int user_id = 1;

    /**
     * Defines the testcase name for JUnit.
     *
     * @param theName the testcase's name.
     */
    public TestZaikoUpdate(String theName) {
        super(theName);
    }

    /**
     * Start the tests.
     *
     * @param theArgs the arguments. Not used
     */
    public static void main(String[] theArgs)
    {
        junit.textui.TestRunner.main(new String[]{
            TestZaikoUpdate.class.getName()});
    }

    /**
     * @return a test suite (<code>TestSuite</code>) that includes all methods
     *         starting with "test"
     */
    public static Test suite()
    {
        // All methods starting with "test" will be executed in the test suite.
        return new TestSuite(TestZaikoUpdate.class);
    }

    //-------------------------------------------------------------------------

    public void setUp() {
        try {
            Context ctx = new InitialContext();
            zhome = (ZaikoLocalHome)
              ctx.lookup("java:comp/env/ejb/ZaikoLocal");
            Calendar cal = Calendar.getInstance();
            cal.clear();
            cal.set(2002, 10, 10);
            today = cal.getTime();
            souko_id = "000001";
            shouhin_id = "7100";
            int zorder = 1;
            // line1
            target = zhome.create(
              today,    //zaikodate
              souko_id, //souko_id
              shouhin_id, //shouhin_id
              10.0f,   //suuryou
              100.0f, //tanka
              1000.0f, //kingaku
              0,      //nyuuko_id
              0,      //shukko_id
              zorder,      //zorder
              user_id);     //user_id
            zorder++;
            zmhome = (ZaikomeisaiLocalHome)
              ctx.lookup("java:comp/env/ejb/ZaikomeisaiLocal");
            cal.set(2002, 11, 10);
            shoumikigen1 = cal.getTime();
            cal.set(2002, 11, 5);
            shukkakigen1 = cal.getTime();
            location_id = "000001";
            ZaikomeisaiLocal zma1 = zmhome.create(
              target.getZaiko_id(),
              souko_id,
              shouhin_id,
              location_id,  //location_id
              shoumikigen1,
              4,  //suuryou
              shukkakigen1,
              user_id);
            cal.set(2002, 11, 20);
            shoumikigen2 = cal.getTime();
            cal.set(2002, 11, 15);
            shukkakigen2 = cal.getTime();
            zmb1 = zmhome.create(
              target.getZaiko_id(),
              souko_id,
              shouhin_id,
              location_id,  //location_id
              shoumikigen2,
              6,  //suuryou
              shukkakigen2,
              user_id);
            // line2
            NyuukoLocalHome nhome = (NyuukoLocalHome)ctx.lookup(
              "java:comp/env/ejb/NyuukoLocal");
            nyuuko2 = nhome.create(
              today, //nyuuko_date
              souko_id,
              shouhin_id,
              1.0f, //shiiresuuryou
              "ケース", //shiiretani
              1000.0f,  //shiiretanka
              10.0f,    //nyuukosuuryou
              "パック", //nyuukotani
              100.0f,  //nyuukotanka
              shoumikigen2,
              shoumikigen2,
              1,        //nyuukokubun 仕入
              "000001", //shiiresaki_id
              "000001", //nouhinsaki_id
              0,        //nyuukayotei_id
              0,        //teisei_flg
              0,        //teiseinyuuko_id
              user_id);
            zaiko2 = zhome.create(
              today,    //zaikodate
              souko_id, //souko_id
              shouhin_id, //shouhin_id
              10.0f + 10.0f,   //suuryou
              100.0f, //tanka
              2000.0f, //kingaku
              nyuuko2.getNyuuko_id(), //nyuuko_id
              0,      //shukko_id
              zorder,      //zorder
              user_id);     //user_id
              zorder++;
            target.setNext_zaiko_id(zaiko2.getZaiko_id());
            ZaikomeisaiLocal zma2 = zmhome.create(
              zaiko2.getZaiko_id(),
              souko_id,
              shouhin_id,
              location_id,  //location_id
              shoumikigen1,
              4,  //suuryou
              shukkakigen1,
              user_id);
            ZaikomeisaiLocal zmb2 = zmhome.create(
              zaiko2.getZaiko_id(),
              souko_id,
              shouhin_id,
              location_id,  //location_id
              shoumikigen2,
              16,  //suuryou
              shukkakigen2,
              user_id);
            // line3
            ShukkoLocalHome shome = (ShukkoLocalHome)ctx.lookup(
              "java:comp/env/ejb/ShukkoLocal");
            shukko3 = shome.create(
              1,         //shukkokubun
              "000001",  //nouhinsaki_id
              souko_id,
              shouhin_id,
              location_id,
              today,     //shukko_date
              shoumikigen1,
              2,    //suuryou
              "個", //tani
              0,    //tankakubun 自動単価
              100.0f, //tanka
              200.0f, //kingaku
              0, //shukkayoteimeisai_id
              0, //teisei_flg
              0, //teiseishukko_id
              user_id,
              0);  // 標準売価
            zaiko3 = zhome.create(
              today,    //zaikodate
              souko_id, //souko_id
              shouhin_id, //shouhin_id
              18.0f,   //suuryou
              100.0f, //tanka
              1800.0f, //kingaku
              0, //nyuuko_id
              shukko3.getShukko_id(),  //shukko_id
              zorder,      //zorder
              user_id);     //user_id
              zorder++;
            zaiko2.setNext_zaiko_id(zaiko3.getZaiko_id());
            ZaikomeisaiLocal zma3 = zmhome.create(
              zaiko3.getZaiko_id(),
              souko_id,
              shouhin_id,
              location_id,  //location_id
              shoumikigen1,
              2,  //suuryou
              shukkakigen1,
              user_id);
            ZaikomeisaiLocal zmb3 = zmhome.create(
              zaiko3.getZaiko_id(),
              souko_id,
              shouhin_id,
              location_id,  //location_id
              shoumikigen2,
              16,  //suuryou
              shukkakigen2,
              user_id);
            // line4
            shukko4 = shome.create(
              1,         //shukkokubun
              "000001",  //nouhinsaki_id
              souko_id,
              shouhin_id,
              location_id,
              today,     //shukko_date
              shoumikigen2,
              8,    //suuryou
              "個", //tani
              1,    //tankakubun 固定単価
              100.0f, //tanka
              800.0f, //kingaku
              0, //shukkayoteimeisai_id
              0, //teisei_flg
              0, //teiseishukko_id
              user_id,
              0);  //hyoujunbaika
            zaiko4 = zhome.create(
              today,    //zaikodate
              souko_id, //souko_id
              shouhin_id, //shouhin_id
              10.0f,   //suuryou
              100.0f, //tanka
              1000.0f, //kingaku
              0, //nyuuko_id
              shukko4.getShukko_id(),  //shukko_id
              zorder,      //zorder
              user_id);     //user_id
              zorder++;
            zaiko3.setNext_zaiko_id(zaiko4.getZaiko_id());
            ZaikomeisaiLocal zma4 = zmhome.create(
              zaiko4.getZaiko_id(),
              souko_id,
              shouhin_id,
              location_id,  //location_id
              shoumikigen1,
              2,  //suuryou
              shukkakigen1,
              user_id);
            ZaikomeisaiLocal zmb4 = zmhome.create(
              zaiko4.getZaiko_id(),
              souko_id,
              shouhin_id,
              location_id,  //location_id
              shoumikigen2,
              8,  //suuryou
              shukkakigen2,
              user_id);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void tearDown() {
        try {
            target.deleteMeisai();
            target.remove();
            //zma1.remove();
            //zmb1.remove();
            if (zaiko2 != null) {
                zaiko2.deleteMeisai();
                zaiko2.remove();
            }
            //if (zma2 != null) {
            //    zma2.remove();
            //}
            //if (zmb2 != null) {
            //    zmb2.remove();
            //}
            if (nyuuko2 != null) {
                nyuuko2.remove();
            }
            if (zaiko3 != null) {
                zaiko3.deleteMeisai();
                zaiko3.remove();
            }
            //if (zma3 != null) {
            //    zma3.remove();
            //}
            //if (zmb3 != null) {
            //    zmb3.remove();
            //}
            if (shukko3 != null) {
                shukko3.remove();
            }
            if (zaiko4 != null) {
                zaiko4.deleteMeisai();
                zaiko4.remove();
            }
            //if (zma4 != null) {
            //    zma4.remove();
            //}
            //if (zmb4 != null) {
            //    zmb4.remove();
            //}
            if (shukko4 != null) {
                shukko4.remove();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Verify
     */
    public void testUpdate() throws Exception {
        //初期数量を変更して、他が全部変更されるかテスト
        target.setSuuryou(15.0f);
        target.setTanka(50.0f);
        target.setKingaku(750.0f);
        zmb1.setSuuryou(11.0f);
        ZaikoData zd1 = target.getZaikoData();
        Collection meisai1 = target.getZaikomeisaiData();
        zaiko2.update(zd1, meisai1, 1);

        assertEquals(25.0f, zaiko2.getSuuryou(), 0.0f);
        assertEquals(70.0f, zaiko2.getTanka(), 0.0f);
        assertEquals(1750.0f, zaiko2.getKingaku(), 0.0f);
        ZaikomeisaiLocal zmb2 = (ZaikomeisaiLocal)(zmhome.findByShoumi(
          zaiko2.getZaiko_id(), shoumikigen2).iterator()).next();
        assertEquals(21.0f, zmb2.getSuuryou(), 0.0f);
        assertEquals(70.0f, shukko3.getTanka(), 0.0f);
        assertEquals(140.0f, shukko3.getKingaku(), 0.0f);
        assertEquals(23.0f, zaiko3.getSuuryou(), 0.0f);
        assertEquals(70.0f, zaiko3.getTanka(), 0.0f);
        assertEquals(1610.0f, zaiko3.getKingaku(), 0.0f);
        ZaikomeisaiLocal zma3 = (ZaikomeisaiLocal)(zmhome.findByShoumi(
          zaiko3.getZaiko_id(), shoumikigen1).iterator()).next();
        assertEquals(2.0f, zma3.getSuuryou(), 0.0f);
        assertEquals(100.0f, shukko4.getTanka(), 0.0f);
        assertEquals(15.0f, zaiko4.getSuuryou(), 0.0f);
        assertEquals(54.0f, zaiko4.getTanka(), 0.0f);
        assertEquals(810.0f, zaiko4.getKingaku(), 0.0f);
        ZaikomeisaiLocal zmb4 = (ZaikomeisaiLocal)(zmhome.findByShoumi(
          zaiko4.getZaiko_id(), shoumikigen2).iterator()).next();
        assertEquals(13.0f, zmb4.getSuuryou(), 0.0f);
    }

}
