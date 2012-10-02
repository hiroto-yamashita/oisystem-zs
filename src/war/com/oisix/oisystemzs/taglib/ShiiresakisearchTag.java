package com.oisix.oisystemzs.taglib;

import com.oisix.oisystemfr.Debug;
import com.oisix.oisystemfr.taglib.DBCollectionTagBase;
import java.util.Collection;
import java.util.LinkedList;
import java.util.HashMap;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ShiiresakisearchTag extends DBCollectionTagBase {
    protected Collection findDBCollection(Connection con) throws Exception {
        Debug.println("findDBCollection start", this);
        Collection col = new LinkedList();
        String sql = makeSql();
        if (sql == null) {
            //検索ボタンが押されていない(初回表示or未入力)
            return null;
        }
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        if (rs == null) { Debug.println("rs null", this);return null; }

        //fetch
        HashMap item = null;
        while (rs.next()) {
            item = new HashMap();
            int index = 1;
            item.put("shiiresaki_id", rs.getString(index++));
            item.put("shiiresakimei", rs.getString(index++));
            item.put("furigana", rs.getString(index++));
            col.add(item);
        }

        rs.close();
        if (ps!= null) { ps.close(); }
        return col;
    }

    public String makeSql() {
        HttpServletRequest request =
          (HttpServletRequest)pageContext.getRequest();
        String shiiresakihead = request.getParameter("head");
        if (shiiresakihead == null || shiiresakihead.equals("")) {
            // クッキーから以前選択したものを取得
            shiiresakihead = "";
        }
        if (shiiresakihead == null || shiiresakihead.equals("")) {
            return null;
        }
        String sql =
          "SELECT " + 
          "t1.SHIIRESAKI_ID, " +
          "t1.NAME, " +
          "t1.FURIGANA " +
          "FROM ZM_SHIIRESAKI t1 ";
        if (shiiresakihead.equals("a")) {
            sql += "WHERE (t1.FURIGANA >= 'あ' " +
                   "AND t1.FURIGANA < 'か') " +
                   "OR (t1.FURIGANA >= 'ア' " +
                   "AND t1.FURIGANA < 'カ') ";
        } else if (shiiresakihead.equals("k")) {
            sql += "WHERE (t1.FURIGANA >= 'か' " +
                   "AND t1.FURIGANA < 'さ') " +
                   "OR (t1.FURIGANA >= 'カ' " +
                   "AND t1.FURIGANA < 'サ') ";
        } else if (shiiresakihead.equals("s")) {
            sql += "WHERE (t1.FURIGANA >= 'さ' " +
                   "AND t1.FURIGANA < 'た') " +
                   "OR (t1.FURIGANA >= 'サ' " +
                   "AND t1.FURIGANA < 'タ') ";
        } else if (shiiresakihead.equals("t")) {
            sql += "WHERE (t1.FURIGANA >= 'た' " +
                   "AND t1.FURIGANA < 'な') " +
                   "OR (t1.FURIGANA >= 'タ' " +
                   "AND t1.FURIGANA < 'ナ') ";
        } else if (shiiresakihead.equals("n")) {
            sql += "WHERE (t1.FURIGANA >= 'な' " +
                   "AND t1.FURIGANA < 'は') " +
                   "OR (t1.FURIGANA >= 'ナ' " +
                   "AND t1.FURIGANA < 'ハ') ";
        } else if (shiiresakihead.equals("h")) {
            sql += "WHERE (t1.FURIGANA >= 'は' " +
                   "AND t1.FURIGANA < 'ま') " +
                   "OR (t1.FURIGANA >= 'ハ' " +
                   "AND t1.FURIGANA < 'マ') ";
        } else if (shiiresakihead.equals("m")) {
            sql += "WHERE (t1.FURIGANA >= 'ま' " +
                   "AND t1.FURIGANA < 'や') " +
                   "OR (t1.FURIGANA >= 'マ' " +
                   "AND t1.FURIGANA < 'ヤ') ";
        } else if (shiiresakihead.equals("y")) {
            sql += "WHERE (t1.FURIGANA >= 'や' " +
                   "AND t1.FURIGANA < 'ら') " +
                   "OR (t1.FURIGANA >= 'ヤ' " +
                   "AND t1.FURIGANA < 'ラ') ";
        } else if (shiiresakihead.equals("r")) {
            sql += "WHERE (t1.FURIGANA >= 'ら' " +
                   "AND t1.FURIGANA < 'わ') " +
                   "OR (t1.FURIGANA >= 'ラ' " +
                   "AND t1.FURIGANA < 'ワ') ";
        } else if (shiiresakihead.equals("w")) {
            sql += "WHERE (t1.FURIGANA >= 'わ' " +
                   "AND t1.FURIGANA < 'ん') " +
                   "OR (t1.FURIGANA >= 'ワ' " +
                   "AND t1.FURIGANA < 'ン') ";
        }
        sql += "ORDER BY t1.FURIGANA ";
        return sql;
    }
}
