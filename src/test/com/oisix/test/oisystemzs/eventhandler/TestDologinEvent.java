package com.oisix.test.oisystemzs.eventhandler;

import com.oisix.oisystemfr.*;
import com.oisix.oisystemfr.ejb.*;
import com.oisix.oisystemzs.eventhandler.*;
import junit.framework.Test;
import junit.framework.TestSuite;

import org.apache.cactus.Cookie;
import org.apache.cactus.ServletTestCase;
import org.apache.cactus.WebRequest;
import org.apache.cactus.WebResponse;

import java.io.IOException;
import java.util.*;
import javax.naming.*;

/**
 * Tests of the <code>SampleServlet</code> servlet class.
 *
 * @author Yamashita
 */
public class TestDologinEvent extends ServletTestCase {

    protected DologinEvent ev;
    protected ControllerEjbLocal cejb;

    /**
     * Defines the testcase name for JUnit.
     *
     * @param theName the testcase's name.
     */
    public TestDologinEvent(String theName)
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
            TestDologinEvent.class.getName()});
    }

    /**
     * @return a test suite (<code>TestSuite</code>) that includes all methods
     *         starting with "test"
     */
    public static Test suite()
    {
        // All methods starting with "test" will be executed in the test suite.
        return new TestSuite(TestDologinEvent.class);
    }

    //-------------------------------------------------------------------------

    public void setUp() {
        ev = new DologinEvent();
        try {
            Context ctx = JNDIUtil.getContext();
            Object objref = ctx.lookup("java:comp/env/ControllerEjbLocal");
            ControllerEjbLocalHome cejbh = (ControllerEjbLocalHome)objref;
            cejb = cejbh.create();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void beginSuccess(WebRequest webRequest) {
    }

    /**
     * Verify that we can assert the servlet output stream.
     * パスワード "test" のユーザーを定義しておくこと
     */
    public void testSuccess() throws Exception {
        HashMap inputval = new HashMap();
        String[] str = {"test"};
        inputval.put("#password", str);
        String[] str1 = {""};
        inputval.put("#souko_id", str1);
        inputval.put("#office_id", str1);
        session.setAttribute("INPUTVALUE", inputval);

        ev.preInit(request);
        ev.init(request);
        ev.postInit();
        cejb.handleEvent(ev);
        ev.postHandle(request);
        assertEquals(TransactionEvent.RC_SUCCEED, ev.getResult());
        assertEquals("LOGIN", session.getAttribute("LOGINSTATUS"));
    }

    public void beginNoPass(WebRequest webRequest) {
    }

    public void testNoPass() throws Exception {
        HashMap inputval = new HashMap();
        inputval.put("#password", null);
        String[] str1 = {""};
        inputval.put("#souko_id", str1);
        inputval.put("#office_id", str1);
        session.setAttribute("INPUTVALUE", inputval);

        ev.preInit(request);
        ev.init(request);
        ev.postInit();
        cejb.handleEvent(ev);
        ev.postHandle(request);
        assertEquals(TransactionEvent.RC_INPUTERROR, ev.getResult());
    }

    public void beginNoPass1(WebRequest webRequest) {
    }

    public void testNoPass1() throws Exception {
        HashMap inputval = new HashMap();
        String[] str = {""};
        inputval.put("#password", str);
        String[] str1 = {""};
        inputval.put("#souko_id", str1);
        inputval.put("#office_id", str1);
        session.setAttribute("INPUTVALUE", inputval);

        ev.preInit(request);
        ev.init(request);
        ev.postInit();
        cejb.handleEvent(ev);
        ev.postHandle(request);
        assertEquals(TransactionEvent.RC_INPUTERROR, ev.getResult());
    }

    public void beginInvalidPass(WebRequest webRequest) {
    }

    public void testInvalidPass() throws Exception {
        HashMap inputval = new HashMap();
        String[] str = {"passerror"};
        inputval.put("#password", str);
        String[] str1 = {""};
        inputval.put("#souko_id", str1);
        inputval.put("#office_id", str1);
        session.setAttribute("INPUTVALUE", inputval);

        ev.preInit(request);
        ev.init(request);
        ev.postInit();
        cejb.handleEvent(ev);
        ev.postHandle(request);
        assertEquals(TransactionEvent.RC_INPUTERROR, ev.getResult());
    }

    public void beginInvalidSouko(WebRequest webRequest) {
    }

    public void testInvalidSouko() throws Exception {
        HashMap inputval = new HashMap();
        String[] str = {"test"};
        inputval.put("#password", str);
        String[] str1 = {"err"};
        inputval.put("#souko_id", str1);
        String[] str2 = {""};
        inputval.put("#office_id", str2);
        session.setAttribute("INPUTVALUE", inputval);

        ev.preInit(request);
        ev.init(request);
        ev.postInit();
        cejb.handleEvent(ev);
        ev.postHandle(request);
        assertEquals(TransactionEvent.RC_INPUTERROR, ev.getResult());
    }


    public void beginInvalidOffice(WebRequest webRequest) {
    }

    public void testInvalidOffice() throws Exception {
        HashMap inputval = new HashMap();
        String[] str = {"test"};
        inputval.put("#password", str);
        String[] str1 = {"000001"};
        inputval.put("#souko_id", str1);
        String[] str2 = {"err"};
        inputval.put("#office_id", str2);
        session.setAttribute("INPUTVALUE", inputval);

        ev.preInit(request);
        ev.init(request);
        ev.postInit();
        cejb.handleEvent(ev);
        ev.postHandle(request);
        assertEquals(TransactionEvent.RC_INPUTERROR, ev.getResult());
    }
}
