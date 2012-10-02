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
public class TestZaiko extends ServletTestCase
{

    private ZaikoLocalHome home;
    private ZaikoLocal target;
    private java.util.Date now;

    /**
     * Defines the testcase name for JUnit.
     *
     * @param theName the testcase's name.
     */
    public TestZaiko(String theName)
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
            TestZaiko.class.getName()});
    }

    /**
     * @return a test suite (<code>TestSuite</code>) that includes all methods
     *         starting with "test"
     */
    public static Test suite()
    {
        // All methods starting with "test" will be executed in the test suite.
        return new TestSuite(TestZaiko.class);
    }

    //-------------------------------------------------------------------------

    public void setUp() {
        try {
            Context ctx = new InitialContext();
            this.home = (ZaikoLocalHome)
              ctx.lookup("java:comp/env/ejb/ZaikoLocal");
            now = new java.util.Date();
            target = home.create(
              now,    //zaikodate
              "0001", //souko_id
              "7100", //shouhin_id
              1.0f,   //suuryou
              100.0f, //tanka
              100.0f, //kingaku
              0,      //nyuuko_id
              0,      //shukko_id
              1,      //zorder
              1);     //user_id
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
    public void testZaikodate() throws Exception {
        // Oracle‚ÌDATEŒ^‚Íƒ~ƒŠ•b‚ª•Û‘¶‚Å‚«‚È‚¢‚Ì‚Åƒ~ƒŠ•b‚ð0‚ÉƒZƒbƒg
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        cal.set(Calendar.MILLISECOND, 0);
        now = cal.getTime();
        java.util.Date zaikodate = target.getZaikodate();
        assertEquals(now, zaikodate);
    }

    public void testSouko_id() throws Exception {
        target.setSouko_id("0002");
        //ŒÅ’è’·CHAR‚Å‚Í‹ó”’‚ª“ü‚é‚Ì‚Åtrim()‚µ‚Ä”äŠr
        assertEquals("0002", target.getSouko_id().trim());
    }
}
