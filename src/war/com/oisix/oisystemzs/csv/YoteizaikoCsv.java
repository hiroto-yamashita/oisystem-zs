package com.oisix.oisystemzs.csv;

import com.oisix.oisystemfr.csv.CsvObjectBase;
import com.oisix.oisystemfr.csv.CsvLine;
import com.oisix.oisystemfr.DateUtil;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;
import java.text.SimpleDateFormat;
import java.text.DecimalFormat;

public class YoteizaikoCsv extends CsvObjectBase {

    private Collection zaikolist;
    
    public void setZaikolist(Collection list) {
        zaikolist = list;
    }

    protected String getFileTypeName() {
        return "yoteizaiko";
    }

    protected void doMake() throws IOException {
        Iterator iter = zaikolist.iterator();
        CsvLine csvline = new CsvLine();
        while (iter.hasNext()) {
            HashMap line = (HashMap)iter.next();
            csvline.removeAllItems();
            csvline.addItem((String)line.get("shouhin_id"));
            csvline.addItem(String.valueOf(line.get("zaikosuu")));
            csvline.addItem(String.valueOf(line.get("shinyanyuuka_flg")));
            csvwrite.setcsvRecord(csvline.getLine());
        }
    }

}
