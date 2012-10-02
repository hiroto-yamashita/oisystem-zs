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
 * 入荷予定がある場合の入庫のテスト
 *
 * @author Yamashita
 */
public class TestNyuuko2 extends ServletTestCase
{

    private ShouhinLocalHome shome;
    private ShouhinLocal shouhin;
    private String shouhin_id;
    private NyuukoLocal target;
    private ZaikoLocal zaiko;
    private NyuukayoteimeisaiLocal nyuukayotei;
    private int user_id = 1;

    /**
     * Defines the testcase name for JUnit.
     *
     * @param theName the testcase's name.
     */
    public TestNyuuko2(String theName)
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
            TestNyuuko2.class.getName()});
    }

    /**
     * @return a test suite (<code>TestSuite</code>) that includes all methods
     *         starting with "test"
     */
    public static Test suite()
    {
        // All methods starting with "test" will be executed in the test suite.
        return new TestSuite(TestNyuuko2.class);
    }

    //-------------------------------------------------------------------------

    public void setUp() {
        try {
            Context ctx = new InitialContext();
            ControllerEjbLocalHome chome = (ControllerEjbLocalHome)ctx.lookup(
              "java:/comp/env/ControllerEjbLocal");
            ControllerEjbLocal cejb = chome.create(
              "java:comp/env/oisystemPool");
            shome = (ShouhinLocalHome)ctx.lookup(
              "java:comp/env/ejb/ShouhinLocal");
            shouhin_id = "TEST01";
            shouhin = shome.create(
              shouhin_id,
              "テスト商品",
              "てすとしょうひん",
              "000001", //shiiresaki_id
              "", //発注商品名1
              "", //発注商品名2
              "", //発注商品名3
              "500g", //規格
              1, //単位
              "24入りケース", //hacchuukikaku
              0, //発注点
              2, //hacchuutani
              24, //irisuu
              1, //hacchuutanisuu
              1, //saiteihacchuusuu
              500.0f, //tanka
              500.0f, //hyoujuntanka
              2, //shiireleadtime
              1, //ondotai
              1, //shoumikigen_flg
              90, //shoumikigennissuu
              60, //shukkakigennissuu
              0, //kobetsuhacchuu_flg
              0, //youchuuihin_flg
              1, //youraberu_flg
              "青果", //daibunrui
              "", //pcode
              0, //zaikohyoukahouhou
              "", //jancode
              "", //kataban
              "000001", //location_id1
              null, //location_id2
              null, //location_id3
              0, //shuubai_flg
              user_id, 
              100.0f, //hyoujunbaika
              "",  // nisugata
              "",  // hacchuucomment
              0,  // irisuurireki1
              0,  // hanbaikubun
              "",  // shubetsu
              0,  //mochikoshi_flg
              0); //shinyanouhin_flg
            NyuukayoteimeisaiLocalHome nyhome = (NyuukayoteimeisaiLocalHome)
              ctx.lookup("java:comp/env/ejb/NyuukayoteimeisaiLocal");
            nyuukayotei = nyhome.create(
              null, //hacchuu_bg
              "000001", //souko_id
              shouhin_id, //shouhin_id
              1.0f, //hacchuusuuryou
              "セット", //hacchuutani
              300.0f, //hacchuutanka
              6.0f, //nyuukasuuryou
              "個", //nyuukatani
              new java.util.Date(), //nyuukayotei_date
              1, //touchakujikan
              1, //ondotai
              1, //hacchuukubun
              new java.util.Date(), //shoumikigen
              new java.util.Date(), //shukkakigen
              1, //nyuukokubun
              null, //bikou
              0, //jitsunyuukasuu
              1, //nyuukajoukyou 未入荷
              user_id);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void tearDown() {
        try {
            shouhin.remove();
            if (target != null) {
                target.remove();
            }
            if (zaiko != null) {
                zaiko.deleteMeisai();
                zaiko.remove();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Verify
     */
    public void testCreate() throws Exception {
        InitialContext ctx = new InitialContext();
        NyuukoLocalHome nhome = (NyuukoLocalHome)ctx.lookup(
          "java:comp/env/ejb/NyuukoLocal");
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(2002, 11, 01);
        java.util.Date today = cal.getTime();
        cal.set(2002, 11, 15);
        java.util.Date shoumikigen = cal.getTime();
        target = nhome.create(
          today,  //nyuuko_date
          "000001", //souko_id
          shouhin_id,
          1.0f, //shiiresuuryou
          "ケース", //shiiretani
          300.0f, //shiiretanka
          6.0f, //nyuukosuuryou
          "個", //nyuukotani
          50.0f, //nyuukotanka
          shoumikigen,
          null, //shukkakigen
          1, //nyuukokubun 仕入
          "000001", //shiiresaki_id
          "000001",  //nouhinsaki_id
          nyuukayotei.getNyuukayotei_id(), //nyuukayotei_id
          0, //teisei_flg
          0, //teiseinyuuko_id
          user_id);
        assertEquals(6.0f, nyuukayotei.getJitsunyuukasuu(), 0.0f);
    }

}
