package com.oisix.oisystemzs.pdf;

import com.oisix.oisystemfr.pdf.PdfObjectBase;
import com.oisix.oisystemzs.eventhandler.HacchuushoHeader;
import com.oisix.oisystemzs.eventhandler.HacchuushoMeisai;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.BaseFont;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;

public class HacchuushoPdf extends PdfObjectBase {

    private Collection hacchuuList;

    public void setData(Collection hacchuuList) {
        this.hacchuuList = hacchuuList;
    }

    protected String getDocTypeName() { return "hacchuusho"; }
    protected Document getDocument() {
        Iterator iter = hacchuuList.iterator();
        HacchuushoHeader h = (HacchuushoHeader)iter.next();
        if (h.getFormat() == 1) {
            //左、右、上、下
            return new Document(PageSize.A4.rotate(), 36, 36,
              36 + 16 * 11, 36 + 16 * 6);
        } else {
            //直送
            return new Document(PageSize.A4, 36, 36, 36+16*10, 36+16*6);
        }
    }

    protected void doMakeDocument() throws IOException, DocumentException {
        Iterator iter = hacchuuList.iterator();
        boolean isfirst = true;
        int index = 1;
        while (iter.hasNext()) {
            HacchuushoHeader h = (HacchuushoHeader)iter.next();
            if (h.getFormat() == 1) {
                if (!isfirst) {
                    document.setPageSize(PageSize.A4.rotate());
                    document.newPage();
                }
                KakouhinHacchuushoPdf.makeDocument(
                  document, cb, writer, h, index);
            } else {
                if (!isfirst) {
                    document.setPageSize(PageSize.A4);
                    document.newPage();
                }
                ChokusouHacchuushoPdf.makeDocument(
                  document, cb, writer, h, index);
            }
            isfirst = false;
            index++;
        }
    }

}
