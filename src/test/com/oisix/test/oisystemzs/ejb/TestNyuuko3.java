package com.oisix.test.oisystemzs.ejb;

import com.oisix.oisystemfr.ejb.*;
import com.oisix.oisystemzs.ejb.*;
import com.oisix.oisystemzs.ejb.exception.*;
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
 * 入庫訂正
 *
 * @author Yamashita
 */
public class TestNyuuko3 extends ServletTestCase
{

    private ZaikoLocalHome zhome;
    private ZaikomeisaiLocalHome zmhome;
    private ZaikoLocal zaiko;
    private ZaikomeisaiLocal zm1;
    private ZaikomeisaiLocal zm2;
    private ZaikoLocal zaiko1;
    private ZaikoLocal zaiko2;
    private NyuukoLocalHome nhome;
    private NyuukoLocal target;
    private NyuukoLocal teiseinyuuko;
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
    public TestNyuuko3(String theName)
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
            TestNyuuko3.class.getName()});
    }

    /**
     * @return a test suite (<code>TestSuite</code>) that includes all methods
     *         starting with "test"
     */
    public static Test suite()
    {
        // All methods starting with "test" will be executed in the test suite.
        return new TestSuite(TestNyuuko3.class);
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
            nhome = (NyuukoLocalHome)ctx.lookup(
              "java:comp/env/ejb/NyuukoLocal");
            target = nhome.create(
              today,  //nyuuko_date
              souko_id,
              shouhin_id,
              2.0f, //shiiresuuryou
              "ケース", //shiiretani
              300.0f, //shiiretanka
              6.0f, //nyuukosuuryou
              "個", //nyuukotani
              100.0f, //nyuukotanka
              shoumikigen2,
              null, //shukkakigen
              1, //nyuukokubun 仕入
              "000001", //shiiresaki_id
              "000001",  //nouhinsaki_id
              0, //nyuukayotei_id
              0, //teisei_flg
              0, //teiseinyuuko_id
              user_id);
            zaiko1 = zhome.findByNyuuko_id(target.getNyuuko_id());
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
            if (zaiko2 != null) {
                zaiko2.deleteMeisai();
                zaiko2.remove();
            }
            if (teiseinyuuko != null) {
                teiseinyuuko.remove();
            }
            target.remove();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Verify
     */
    public void testModify() throws Exception {
        teiseinyuuko = target.modify(-6.0f, -1.0f, user_id);
        assertEquals(-1.0f, teiseinyuuko.getShiiresuuryou(), 0.0f);
        assertEquals(300.0f, teiseinyuuko.getShiiretanka(), 0.0f);
        assertEquals(-6.0f, teiseinyuuko.getNyuukosuuryou(), 0.0f);
        assertEquals(50.0f, teiseinyuuko.getNyuukotanka(), 0.0f);
        assertEquals(teiseinyuuko.getSouko_id(), target.getSouko_id());
        assertEquals(teiseinyuuko.getShouhin_id(), target.getShouhin_id());
        assertEquals(teiseinyuuko.getTeiseinyuuko_id(), target.getNyuuko_id());

        zaiko2 = zhome.findByNyuuko_id(teiseinyuuko.getNyuuko_id());
        assertEquals(10.0f, zaiko2.getSuuryou(), 0.0f);
        assertEquals(130.0f, zaiko2.getTanka(), 0.0f);
        assertEquals(1300.0f, zaiko2.getKingaku(), 0.0f);

        ZaikomeisaiLocal zm2 = (ZaikomeisaiLocal)(zmhome.findByShoumi(
          zaiko2.getZaiko_id(), shoumikigen2).iterator()).next();
        assertEquals(6.0f, zm2.getSuuryou(), 0.0f);
        assertEquals(zaiko2.getZaiko_id(), zaiko1.getNext_zaiko_id());
        assertEquals(3, zaiko2.getZorder());
    }

    public void testModifyError1() throws Exception {
        teiseinyuuko = target.modify(-6.0f, -1.0f, user_id);
        //あとで削除するため一応取得
        zaiko2 = zhome.findByNyuuko_id(teiseinyuuko.getNyuuko_id());
        try {
            teiseinyuuko.modify(0.0f, 0.0f, user_id);
        } catch (NyuukoModifyException nme) {
            return;
        }
        this.fail("訂正に訂正をかけたのにNyuukoModifyExceptionがthrowされませんでした");
    }

    public void testModifyError2() throws Exception {
        try {
            teiseinyuuko = target.modify(-7.0f, -1.0f, user_id);
        } catch (NyuukoModifyException nme) {
            return;
        }
        this.fail("入庫数以上に訂正したのにNyuukoModifyExceptionがthrowされませんでした");
    }

    public void testModifyError3() throws Exception {
        try {
            teiseinyuuko = target.modify(-1.0f, -3.0f, user_id);
        } catch (NyuukoModifyException nme) {
            return;
        }
        this.fail("仕入数量以上に訂正したのにNyuukoModifyExceptionがthrowされませんでした");
    }
}
