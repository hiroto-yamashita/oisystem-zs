package com.oisix.oisystemzs.pdf;

import com.oisix.oisystemfr.Debug;
import com.oisix.oisystemfr.pdf.PdfObjectBase;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Phrase;
import com.lowagie.text.Table;
import com.lowagie.text.Element;
import com.lowagie.text.BadElementException;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfPTable;
import java.awt.Point;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import com.oisix.oisystemzs.ejb.NyuukayoteimeisaiData;

public class Labelsakuseipdf extends PdfObjectBase {

    private Collection labelsakuseiList;
    private HashMap buranku;
    private String burank="";
    private NyuukayoteimeisaiData nyuukayoteimeisaiData;
    private java.util.Date makedate;
    private int burankumaisuu = 0;
    private int size = 0;
    
    public void setNyuukayoteimeisaiData(NyuukayoteimeisaiData data){
        this.nyuukayoteimeisaiData = data;
    }

    public void setBuranku(HashMap buranku) {
        this.buranku = buranku;
    }

    public void setLabelsakuseiList(Collection labelsakuseiList) {
        this.labelsakuseiList = labelsakuseiList;
    }

    protected String getDocTypeName() { return "label"; }
    protected Document getDocument() {
        return new Document(PageSize.A4, 53, 53,
          61, 61);
    }

    protected void doMakeDocument() throws IOException, DocumentException {
        Calendar cal = Calendar.getInstance();
        makedate = cal.getTime();
        
        BaseFont bf = BaseFont.createFont(
          "HeiseiKakuGo-W5", "UniJIS-UCS2-HW-H",false);
        Font font = new Font(bf, 10);
        Font smallfont = new Font(bf, 8);
        PdfTemplate template = cb.createTemplate(50, 50);
        int page = 1;
        int wide = 1;
        int hei = 0;
        float maisuu = 0;
        buranku.put("buranku",burank);
        String strburankumaisuu = (String)buranku.get("BURANKUMAISUU");
        try{
            burankumaisuu = Integer.parseInt(strburankumaisuu);
        }catch (NumberFormatException nfe) {
                Debug.println(nfe);
        }
        Iterator iter = labelsakuseiList.iterator();
        while(iter.hasNext()){
            HashMap label = (HashMap)iter.next();
            maisuu = ((Float)label.get("maisuu")).floatValue();
            for(int j=0;j<maisuu;j++){
                PdfPTable ptable = getTable();
                addNewColumn(ptable, font, smallfont, label);
                //ラベル数１ページ10枚
                if(size%10==0){
                    document.newPage();
                    page++;
                    wide = 1;
                    size = 0;
                    hei = 0;
                    //addNewColumn(ptable, font, label);
                }
                wide++;
                size++;
                if (!writer.fitsPage(ptable)) {
                    document.add(ptable);
                }
                if(wide%2==0){
                    hei++;
                    ptable.writeSelectedRows(0,-1,72,929-hei*150,cb);
                    ptable.endWritingRows(ptable.beginWritingRows(cb));
                }else{
                    ptable.writeSelectedRows(0,-1,72+257,929-hei*150,cb);
                    ptable.endWritingRows(ptable.beginWritingRows(cb));
                }
            }
        }
        for(int i=0;i<burankumaisuu;i++){
            PdfPTable ptable = getTable();
            addNewCol(ptable,font);
            //ラベル数１ページ10枚
            if((size+i)%10==0){
                document.newPage();
                page++;
                wide = 1;
                hei = 0;
                //addNewCol(ptable,font);
            }
            wide++;
            if (!writer.fitsPage(ptable)) {
                document.add(ptable);
            }
            if(wide%2==0){
                hei++;
                ptable.writeSelectedRows(0,-1,72,929-hei*150,cb);
                ptable.endWritingRows(ptable.beginWritingRows(cb));
            }else{
                ptable.writeSelectedRows(0,-1,72+257,929-hei*150,cb);
                ptable.endWritingRows(ptable.beginWritingRows(cb));
            }
        }
        template.beginText();
        template.setFontAndSize(bf, 12);
        template.showText(String.valueOf(page));
        template.endText();
    }

    protected void addNewColumn(PdfPTable table, Font font,
      Font smallfont ,HashMap label)
      throws BadElementException {
        table.addCell(new Phrase(12, "商品コード", font));
        table.addCell(new Phrase(12, (String)label.get("shouhin_id"), font));
        table.addCell(new Phrase(12, "商品", font));
        String strlabel = (String)label.get("shouhin");
        if(strlabel.length()<=60){
            table.addCell(new Phrase(12, strlabel, font));
        }else{
            table.addCell(new Phrase(12, strlabel, smallfont));
        }
        String strshoumikigen = "";
        table.addCell(new Phrase(12, "賞味期限", font));
        SimpleDateFormat ymd = new SimpleDateFormat("yyyy年M月d日");
        java.util.Date shoumikigen = (java.util.Date)label.get("shoumikigen");
        if(shoumikigen != null){
            strshoumikigen = ymd.format(shoumikigen);
        }
        table.addCell(new Phrase(12, strshoumikigen, font));
        String strshukkakigen = "";
        table.addCell(new Phrase(12, "出荷期限", font));
        java.util.Date shukkakigen = (java.util.Date)label.get("shukkakigen");
        if(shukkakigen != null){
            strshukkakigen = ymd.format(shukkakigen);
        }
        table.addCell(new Phrase(12, strshukkakigen, font));
    }
    protected void addNewCol(PdfPTable table, Font font)
      throws BadElementException {
        table.addCell(new Phrase(12, "商品コード", font));
        table.addCell(new Phrase(12, "", font));
        table.addCell(new Phrase(12, "商品", font));
        table.addCell(new Phrase(12, "", font));
        table.addCell(new Phrase(12, "賞味期限", font));
        table.addCell(new Phrase(12, "", font));
        table.addCell(new Phrase(12, "出荷期限", font));
        table.addCell(new Phrase(12, "", font));
    }
    private static PdfPTable getTable() 
      throws DocumentException, BadElementException, IOException {
        PdfPTable ptable = new PdfPTable(2);
        int wp = 41;
        int width[] = {35,65};
        ptable.getDefaultCell().setPaddingLeft(5);
        ptable.getDefaultCell().setPaddingTop(3);
        ptable.getDefaultCell().setPaddingBottom(6);
        ptable.setWidthPercentage(wp);
        ptable.setWidths(width);
        return ptable;
    }
}
