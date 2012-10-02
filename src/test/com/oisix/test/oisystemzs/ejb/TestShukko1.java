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
 * 後続の在庫があるものを出庫。後続の在庫が更新される。
 *
 * @author Yamashita
 */
public class TestShukko1 extends ServletTestCase
{

    private ZaikoLocalHome zhome;
    private ZaikomeisaiLocalHome zmhome;
    private ZaikoLocal zaiko;
    private ZaikomeisaiLocal zm1;
    private ZaikomeisaiLocal zm2;
    private ZaikoLocal zaiko1;
    private ZaikoLocal zaiko2;
    private ShukkoLocalHome shome;
    private ShukkoLocal target;
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
    public TestShukko1(String theName)
    {
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
            TestShukko1.class.getName()});
    }

    /**
     * @return a test suite (<code>TestSuite</code>) that includes all methods
     *         starting with "test"
     */
    public static Test suite()
    {
        // All methods starting with "test" will be executed in the test suite.
        return new TestSuite(TestShukko1.class);
    }

    //-------------------------------------------------------------------------

    public void setUp() {
        try {
            Context ctx = new InitialContext();
            ControllerEjbLocalHome chome = (ControllerEjbLocalHome)ctx.lookup(
              "java:/comp/env/ControllerEjbLocal");
            ControllerEjbLocal cejb = chome.create(
              "java:comp/env/oisystemPool");
            zhome = (ZaikoLocalHome)
              ctx.lookup("java:comp/env/ejb/ZaikoLocal");
            Calendar cal = Calendar.getInstance();
            cal.clear();
            cal.set(2002, 10, 28);
            today = cal.getTime();
            souko_id = "000001";
            shouhin_id = "TESTID";
            zaiko = zhome.create(
              today,    //zaikodate
              souko_id, //souko_id
              shouhin_id, //shouhin_id
              10.0f,   //suuryou
              100.0f, //tanka
              1000.0f, //kingaku
              0,      //nyuuko_id
              0,      //shukko_id
              1,      //zorder
              user_id);     //user_id
System.out.println("zaiko:"+zaiko.getZaiko_id());
System.out.println("zaikodate:"+zaiko.getZaikodate());
            zmhome = (ZaikomeisaiLocalHome)
              ctx.lookup("java:comp/env/ejb/ZaikomeisaiLocal");
            cal.set(2002, 11, 10);
            shoumikigen1 = cal.getTime();
            cal.set(2002, 11, 5);
            shukkakigen1 = cal.getTime();
            location_id = "000001";
            zm1 = zmhome.create(
              zaiko.getZaiko_id(),
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
            zm2 = zmhome.create(
              zaiko.getZaiko_id(),
              souko_id,
              shouhin_id,
              location_id,  //location_id
              shoumikigen2,
              6,  //suuryou
              shukkakigen2,
              user_id);
            // zaiko 2
            cal.setTime(today);
            cal.add(Calendar.DATE, 1);
            java.util.Date tomorrow = cal.getTime();System.out.println("tomorror:"+tomorrow);
            zaiko2 = zhome.create(
              tomorrow,    //zaikodate
              souko_id, //souko_id
              shouhin_id, //shouhin_id
              10.0f,   //suuryou
              100.0f, //tanka
              1000.0f, //kingaku
              0,      //nyuuko_id
              0,      //shukko_id
              2,      //zorder
              user_id);     //user_id
            zaiko.setNext_zaiko_id(zaiko2.getZaiko_id());
System.out.println("zaiko2:"+zaiko2.getZaiko_id());
System.out.println("zaiko2date:"+zaiko2.getZaikodate());
            zm1 = zmhome.create(
              zaiko2.getZaiko_id(),
              souko_id,
              shouhin_id,
              location_id,  //location_id
              shoumikigen1,
              4,  //suuryou
              shukkakigen1,
              user_id);
            zm2 = zmhome.create(
              zaiko2.getZaiko_id(),
              souko_id,
              shouhin_id,
              location_id,  //location_id
              shoumikigen2,
              6,  //suuryou
              shukkakigen2,
              user_id);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void tearDown() {
        try {
            zaiko.deleteMeisai();
            zaiko.remove();
            zaiko2.deleteMeisai();
            zaiko2.remove();
            target.remove();
            zaiko1.deleteMeisai();
            zaiko1.remove();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Verify
     */
    public void testCreate() throws Exception {
        InitialContext ctx = new InitialContext();
        shome = (ShukkoLocalHome)ctx.lookup("java:comp/env/ejb/ShukkoLocal");
        target = shome.create(
          1,         //shukkokubun
          "000001",  //nouhinsaki_id
          souko_id,
          shouhin_id,
          location_id,
          today,     //shukko_date
          shoumikigen1,
          2,    //suuryou
          "個", //tani
          1,    //tankakubun 固定単価
          200.0f, //tanka
          400.0f, //kingaku
          0, //shukkayoteimeisai_id
          0, //teisei_flg
          0, //teiseishukko_id
          user_id,
          0);  //hyoujunbaika

        zaiko1 = zhome.findByShukko_id(target.getShukko_id());
        assertEquals(8.0f, zaiko1.getSuuryou(), 0.0f);
        assertEquals(75.0f, zaiko1.getTanka(), 0.0f);
        assertEquals(600.0f, zaiko1.getKingaku(), 0.0f);
        ZaikomeisaiLocal zm1 = (ZaikomeisaiLocal)(zmhome.findByShoumi(
          zaiko1.getZaiko_id(), shoumikigen1).iterator()).next();
        assertEquals(2.0f, zm1.getSuuryou(), 0.0f);
        ZaikomeisaiLocal zm2 = (ZaikomeisaiLocal)(zmhome.findByShoumi(
          zaiko1.getZaiko_id(), shoumikigen2).iterator()).next();
        assertEquals(6.0f, zm2.getSuuryou(), 0.0f);
        assertEquals(zaiko1.getZaiko_id(), zaiko.getNext_zaiko_id());
        assertEquals(2, zaiko1.getZorder());

        zaiko2 = zaiko1.getNextZaiko();
        assertEquals(8.0f, zaiko2.getSuuryou(), 0.0f);
        assertEquals(75.0f, zaiko2.getTanka(), 0.0f);
        assertEquals(600.0f, zaiko2.getKingaku(), 0.0f);
        zm1 = (ZaikomeisaiLocal)(zmhome.findByShoumi(
          zaiko2.getZaiko_id(), shoumikigen1).iterator()).next();
        assertEquals(2.0f, zm1.getSuuryou(), 0.0f);
        zm2 = (ZaikomeisaiLocal)(zmhome.findByShoumi(
          zaiko2.getZaiko_id(), shoumikigen2).iterator()).next();
        assertEquals(6.0f, zm2.getSuuryou(), 0.0f);
        assertEquals(zaiko2.getZaiko_id(), zaiko1.getNext_zaiko_id());
        assertEquals(3, zaiko2.getZorder());
    }

}
