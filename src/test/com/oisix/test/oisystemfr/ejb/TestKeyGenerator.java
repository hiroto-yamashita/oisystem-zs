package com.oisix.test.oisystemfr.ejb;

import com.oisix.oisystemfr.ejb.*;
import junit.framework.Test;
import junit.framework.TestSuite;
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
public class TestKeyGenerator extends ServletTestCase
{

    private KeyGeneratorLocal keygen;

    /**
     * Defines the testcase name for JUnit.
     *
     * @param theName the testcase's name.
     */
    public TestKeyGenerator(String theName)
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
            TestKeyGenerator.class.getName()});
    }

    /**
     * @return a test suite (<code>TestSuite</code>) that includes all methods
     *         starting with "test"
     */
    public static Test suite()
    {
        // All methods starting with "test" will be executed in the test suite.
        return new TestSuite(TestKeyGenerator.class);
    }

    //-------------------------------------------------------------------------

    public void setUp() {
        try {
            Context ctx = new InitialContext();
            KeyGeneratorLocalHome home = (KeyGeneratorLocalHome)
              ctx.lookup("java:comp/env/ejb/KeyGeneratorLocal");
            this.keygen = home.create();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Verify
     */
    public void testGetNext() throws Exception {
        int id = keygen.getNext("ZS_USER");
        System.out.println(id);
        assertTrue((id > 0));
   }

}
