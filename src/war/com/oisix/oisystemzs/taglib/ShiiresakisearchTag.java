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
            //�����{�^����������Ă��Ȃ�(����\��or������)
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
            // �N�b�L�[����ȑO�I���������̂��擾
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
            sql += "WHERE (t1.FURIGANA >= '��' " +
                   "AND t1.FURIGANA < '��') " +
                   "OR (t1.FURIGANA >= '�A' " +
                   "AND t1.FURIGANA < '�J') ";
        } else if (shiiresakihead.equals("k")) {
            sql += "WHERE (t1.FURIGANA >= '��' " +
                   "AND t1.FURIGANA < '��') " +
                   "OR (t1.FURIGANA >= '�J' " +
                   "AND t1.FURIGANA < '�T') ";
        } else if (shiiresakihead.equals("s")) {
            sql += "WHERE (t1.FURIGANA >= '��' " +
                   "AND t1.FURIGANA < '��') " +
                   "OR (t1.FURIGANA >= '�T' " +
                   "AND t1.FURIGANA < '�^') ";
        } else if (shiiresakihead.equals("t")) {
            sql += "WHERE (t1.FURIGANA >= '��' " +
                   "AND t1.FURIGANA < '��') " +
                   "OR (t1.FURIGANA >= '�^' " +
                   "AND t1.FURIGANA < '�i') ";
        } else if (shiiresakihead.equals("n")) {
            sql += "WHERE (t1.FURIGANA >= '��' " +
                   "AND t1.FURIGANA < '��') " +
                   "OR (t1.FURIGANA >= '�i' " +
                   "AND t1.FURIGANA < '�n') ";
        } else if (shiiresakihead.equals("h")) {
            sql += "WHERE (t1.FURIGANA >= '��' " +
                   "AND t1.FURIGANA < '��') " +
                   "OR (t1.FURIGANA >= '�n' " +
                   "AND t1.FURIGANA < '�}') ";
        } else if (shiiresakihead.equals("m")) {
            sql += "WHERE (t1.FURIGANA >= '��' " +
                   "AND t1.FURIGANA < '��') " +
                   "OR (t1.FURIGANA >= '�}' " +
                   "AND t1.FURIGANA < '��') ";
        } else if (shiiresakihead.equals("y")) {
            sql += "WHERE (t1.FURIGANA >= '��' " +
                   "AND t1.FURIGANA < '��') " +
                   "OR (t1.FURIGANA >= '��' " +
                   "AND t1.FURIGANA < '��') ";
        } else if (shiiresakihead.equals("r")) {
            sql += "WHERE (t1.FURIGANA >= '��' " +
                   "AND t1.FURIGANA < '��') " +
                   "OR (t1.FURIGANA >= '��' " +
                   "AND t1.FURIGANA < '��') ";
        } else if (shiiresakihead.equals("w")) {
            sql += "WHERE (t1.FURIGANA >= '��' " +
                   "AND t1.FURIGANA < '��') " +
                   "OR (t1.FURIGANA >= '��' " +
                   "AND t1.FURIGANA < '��') ";
        }
        sql += "ORDER BY t1.FURIGANA ";
        return sql;
    }
}
