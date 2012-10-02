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
public class TestShouhin extends ServletTestCase
{

    private ShouhinLocalHome home;
    private ShouhinLocal target;
    private java.util.Date now;

    /**
     * Defines the testcase name for JUnit.
     *
     * @param theName the testcase's name.
     */
    public TestShouhin(String theName)
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
            TestShouhin.class.getName()});
    }

    /**
     * @return a test suite (<code>TestSuite</code>) that includes all methods
     *         starting with "test"
     */
    public static Test suite()
    {
        // All methods starting with "test" will be executed in the test suite.
        return new TestSuite(TestShouhin.class);
    }

    //-------------------------------------------------------------------------

    public void setUp() {
        try {
            Context ctx = new InitialContext();
            this.home = (ShouhinLocalHome)
              ctx.lookup("java:comp/env/ejb/ShouhinLocal");
            now = new java.util.Date();
            target = home.findByPrimaryKey("7100");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void tearDown() {
        try {
            //target.remove();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Verify
     */
    public void testShouhinData() throws Exception {
        ShouhinData shouhinData = target.getShouhinData();
    }

}
