package com.oisix.oisystemzs.csv;

import com.oisix.oisystemfr.csv.CsvObjectBase;
import com.oisix.oisystemfr.csv.CsvLine;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class JancodeCsv extends CsvObjectBase {

    public static final String SHOUHIN_ID = "shouhin_id";
    public static final String DAIBUNRUI = "daibunrui";
    public static final String JANCODE = "jancode";

    private Collection jancodelist;
    
    public void setJancodelist(Collection list) {
        jancodelist = list;
    }

    protected String getFileTypeName() {
        return "jancode";
    }

    protected void doMake() throws IOException {
        Iterator iter = jancodelist.iterator();
        CsvLine csvline = new CsvLine();
        while (iter.hasNext()) {
            HashMap line = (HashMap)iter.next();
            csvline.removeAllItems();
            csvline.addItem((String)line.get(SHOUHIN_ID));
            csvline.addItem((String)line.get(DAIBUNRUI));
            String jancode = (String)line.get(JANCODE);
            if (jancode == null) {
                jancode = "";
            }
            csvline.addItem(jancode);
            csvwrite.setcsvRecord(csvline.getLine());
        }
    }

}
