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
public class TestNyuukayoteimeisai extends ServletTestCase
{

    private NyuukayoteimeisaiLocal target;
    private int user_id = 1;

    /**
     * Defines the testcase name for JUnit.
     *
     * @param theName the testcase's name.
     */
    public TestNyuukayoteimeisai(String theName)
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
            TestNyuukayoteimeisai.class.getName()});
    }

    /**
     * @return a test suite (<code>TestSuite</code>) that includes all methods
     *         starting with "test"
     */
    public static Test suite()
    {
        // All methods starting with "test" will be executed in the test suite.
        return new TestSuite(TestNyuukayoteimeisai.class);
    }

    //-------------------------------------------------------------------------

    public void setUp() {
        try {
            Context ctx = new InitialContext();
            NyuukayoteimeisaiLocalHome nyhome = (NyuukayoteimeisaiLocalHome)
              ctx.lookup("java:comp/env/ejb/NyuukayoteimeisaiLocal");
            target = nyhome.create(
              null, //hacchuu_bg
              "000001", //souko_id
              "7101", //shouhin_id
              5.0f, //hacchuusuuryou
              "ƒZƒbƒg", //hacchuutani
              200.0f, //hacchuutanka
              10.0f, //nyuukasuuryou
              "ŒÂ", //nyuukatani
              new java.util.Date(), //nyuukayotei_date
              1, //touchakujikan
              1, //ondotai
              1, //hacchuukubun
              new java.util.Date(), //shoumikigen
              new java.util.Date(), //shukkakigen
              1, //nyuukokubun
              null, //bikou
              0, //jitsunyuukasuu
              1, //nyuukajoukyou –¢“ü‰×
              user_id);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void tearDown() {
        try {
            target.remove();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Verify
     */
    public void testIchubunyuuka() throws Exception {
        target.addJitsunyuukasuu(3.0f, user_id);
        assertEquals(2, target.getNyuukajoukyou());
    }

    public void testNyuukakanryou() throws Exception {
        target.addJitsunyuukasuu(10.0f, user_id);
        assertEquals(3, target.getNyuukajoukyou());
    }

    public void testNyuukakanryou1() throws Exception {
        target.addJitsunyuukasuu(11.0f, user_id);
        assertEquals(3, target.getNyuukajoukyou());
    }

    public void testMinyuuka() throws Exception {
        target.addJitsunyuukasuu(3.0f, user_id);
        target.addJitsunyuukasuu(-3.0f, user_id);
        assertEquals(1, target.getNyuukajoukyou());
    }
}
