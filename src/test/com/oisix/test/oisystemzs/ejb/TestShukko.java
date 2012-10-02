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
 * èoå…
 *
 * @author Yamashita
 */
public class TestShukko extends ServletTestCase
{

    private ZaikoLocalHome zhome;
    private ZaikomeisaiLocalHome zmhome;
    private ZaikoLocal zaiko;
    private ZaikomeisaiLocal zm1;
    private ZaikomeisaiLocal zm2;
    private ZaikoLocal zaiko1;
    private ShukkoLocalHome shome;
    private ShukkoLocal target;
    private String souko_id;
    private String shouhin_id;
    private String location_id;
    private java.util.Date shoumikigen1;
    private java.util.Date shoumikigen2;
    private java.util.Date today;
    private int user_id = 1;

    /**
     * Defines the testcase name for JUnit.
     *
     * @param theName the testcase's name.
     */
    public TestShukko(String theName)
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
            TestShukko.class.getName()});
    }

    /**
     * @return a test suite (<code>TestSuite</code>) that includes all methods
     *         starting with "test"
     */
    public static Test suite()
    {
        // All methods starting with "test" will be executed in the test suite.
        return new TestSuite(TestShukko.class);
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
            zmhome = (ZaikomeisaiLocalHome)
              ctx.lookup("java:comp/env/ejb/ZaikomeisaiLocal");
            cal.set(2002, 11, 10);
            shoumikigen1 = cal.getTime();
            cal.set(2002, 11, 5);
            java.util.Date shukkakigen = cal.getTime();
            location_id = "000001";
            zm1 = zmhome.create(
              zaiko.getZaiko_id(),
              souko_id,
              shouhin_id,
              location_id,  //location_id
              shoumikigen1,
              4,  //suuryou
              shukkakigen,
              user_id);
            cal.set(2002, 11, 20);
            shoumikigen2 = cal.getTime();
            cal.set(2002, 11, 15);
            shukkakigen = cal.getTime();
            zm2 = zmhome.create(
              zaiko.getZaiko_id(),
              souko_id,
              shouhin_id,
              location_id,  //location_id
              shoumikigen2,
              6,  //suuryou
              shukkakigen,
              user_id);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void tearDown() {
        try {
            zaiko.deleteMeisai();
            zaiko.remove();
            zaiko1.deleteMeisai();
            zaiko1.remove();
            target.remove();
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
          "å¬", //tani
          1,    //tankakubun å≈íËíPâø
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
    }

}
