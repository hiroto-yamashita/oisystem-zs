package com.oisix.oisystemzs.pdf;

import com.oisix.oisystemfr.pdf.PdfObjectBase;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Phrase;
import com.lowagie.text.Table;
import com.lowagie.text.Element;
import com.lowagie.text.Cell;
import com.lowagie.text.BadElementException;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfTemplate;
import java.io.IOException;
import java.util.HashMap;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.text.DecimalFormat;

public class TanaoroshilistPdf extends PdfObjectBase {

    private java.util.Date zaikodate;
    private LinkedList tanaoroshilist;
    private HashMap shouhincount;

    private java.util.Date makedate;

    private String preshouhin_id;
    private DecimalFormat df = new DecimalFormat("0.###");
    private Font font;
    private Font lfont;

    public void setTanaoroshilist(LinkedList tanaoroshilist) {
        this.tanaoroshilist = tanaoroshilist;
    }
    public void setShouhincount(HashMap shouhincount) {
        this.shouhincount = shouhincount;
    }
    public void setZaikodate(java.util.Date zaikodate) {
        this.zaikodate = zaikodate;
    }

    protected String getDocTypeName() { return "tanaoroshilist"; }
    protected Document getDocument() {
        return new Document(PageSize.A4, 36, 36, 36, 36);
    }

    protected void doMakeDocument() throws IOException, DocumentException {
        Calendar cal = Calendar.getInstance();
        makedate = cal.getTime();

        BaseFont bf = BaseFont.createFont(
          "HeiseiKakuGo-W5", "UniJIS-UCS2-HW-H",false);
        PdfTemplate template = cb.createTemplate(50, 50);
        int page = 1;
        font = new Font(bf, 8);
        lfont = new Font(bf, 12);

        makeTemplate(bf, page, template);
        Table table = getTable(font);

        String predaibunrui = null;
        Iterator iter = tanaoroshilist.iterator();
        while (iter.hasNext()) {
            Collection meisailist = (Collection)iter.next();
            Iterator meisaiiter = meisailist.iterator();
            HashMap tanaoroshi = (HashMap)meisaiiter.next();
            boolean changedaibunrui = false;
            String daibunrui = (String)tanaoroshi.get("DAIBUNRUI");
            if (predaibunrui == null) {
                predaibunrui = daibunrui;
                changedaibunrui = true;
            } else {
                if (!predaibunrui.equals(daibunrui)) {
                    //document.add(table);
                    //document.newPage();
                    //page++;
                    //makeTemplate(bf, page, template);
                    //table = getTable(font);
                    predaibunrui = daibunrui;
                    changedaibunrui = true;
                }
            }
            //if (changedaibunrui) {
            //    document.add(new Phrase(8, daibunrui, font));
            //}

            addNewRow(table, meisailist);
            if (!writer.fitsPage(table)) {
                for (int i=0; i<meisailist.size(); i++) {
                    table.deleteLastRow();
                }
                document.add(table);
                document.newPage();
                page++;
                makeTemplate(bf, page, template);
                table = getTable(font);
                addNewRow(table, meisailist);
            }
        }
        document.add(table);
        template.beginText();
        template.setFontAndSize(bf, 8);
        template.showText(String.valueOf(page));
        template.endText();
    }

    protected void addNewRow(Table table, Collection meisailist)
      throws BadElementException {
        Iterator meisaiiter = meisailist.iterator();
        boolean isfirst = true;
        while (meisaiiter.hasNext()) {
            HashMap tanaoroshi = (HashMap)meisaiiter.next();
            String shouhin_id = (String)tanaoroshi.get("SHOUHIN_ID");
            float suuryou = ((Float)tanaoroshi.get("SUURYOU")).floatValue();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            java.util.Date shoumikigen = null;
            shoumikigen = (java.util.Date)tanaoroshi.get("SHOUMIKIGEN");
            String shoumikigenstr = "--------";
            if (shoumikigen != null) {
                shoumikigenstr = sdf.format(shoumikigen);
            }
            if (tanaoroshi.get("NYUUKAYOTEI") != null) {
                shoumikigenstr = "(入荷予定)";
            }
            java.util.Date shukkakigen = null;
            shukkakigen = (java.util.Date)tanaoroshi.get("SHUKKAKIGEN");
            String shukkakigenstr = "--------";
            if (shukkakigen != null) {
                shukkakigenstr = sdf.format(shukkakigen);
            }
            String location = "";
            String location1 = (String)tanaoroshi.get("LOCATION_ID1");
            if (location1 != null) {
                location = location1;
            }
            String location2 = (String)tanaoroshi.get("LOCATION_ID2");
            if (location2 != null) {
                location += "\n" + location2;
            }
            String location3 = (String)tanaoroshi.get("LOCATION_ID3");
            if (location3 != null) {
                location += "\n" + location3;
            }
            Integer count = (Integer)shouhincount.get(shouhin_id);
            int rows = count.intValue();
            if (isfirst) {
                Cell cell = new Cell(new Phrase(8, shouhin_id, font));
                cell.setRowspan(rows);
                table.addCell(cell);
                String shouhinmei = (String)tanaoroshi.get("SHOUHINMEI");
                String shuubai_flg = (String)tanaoroshi.get("SHUUBAI_FLG");
                if (shuubai_flg.equals("1")) {
                    shouhinmei += "\n***** 終売 *****";
                }
                cell = new Cell(new Phrase(8, shouhinmei, font));
                cell.setRowspan(rows);
                table.addCell(cell);
                cell = new Cell(
                  new Phrase(8,(String)tanaoroshi.get("KIKAKU"), font));
                cell.setRowspan(rows);
                table.addCell(cell);
            }
            table.addCell(new Phrase(8, shoumikigenstr, font));
            table.addCell(new Phrase(8, "", font));
            table.addCell(new Phrase(8, shukkakigenstr, font));
            Cell cell = new Cell(new Phrase(12, df.format(suuryou), lfont));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(cell);
            table.addCell(new Phrase(8, "", font));
            if (isfirst) {
                cell = new Cell(new Phrase(8, location, font));
                cell.setRowspan(rows);
                table.addCell(cell);
                isfirst = false;
            }
            table.addCell(new Phrase(8, "", font));
        }
    }

    protected void makeTemplate(BaseFont bf, int page, PdfTemplate template) {
        cb.beginText();
        // ヘッダー
        String title = "棚卸リスト";
        cb.setFontAndSize(bf, 14);
        cb.showTextAligned(PdfContentByte.ALIGN_CENTER, title, 297, 812, 0);
        cb.setFontAndSize(bf, 10);
        SimpleDateFormat ymd = new SimpleDateFormat("yyyy年M月d日");
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT,
          "在庫日　" + ymd.format(zaikodate), 400, 812, 0);

        // フッター
        float height = 30;
        cb.setFontAndSize(bf, 8);
        SimpleDateFormat ymdhms = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String datepage = ymdhms.format(makedate);
        datepage += "　　　";
        datepage += "page:" + page + "/";
        cb.showTextAligned(PdfContentByte.ALIGN_CENTER,
          datepage, 297, height, 0);
        cb.endText();
        float len = bf.getWidthPoint(datepage, 8);
        cb.addTemplate(template, 297 + len / 2, height);
    }

    private Table getTable(Font font)
      throws DocumentException, BadElementException, IOException {
            Table table = new Table(10);
            table.setOffset(0);
            table.setWidth(100);
            int width[] = {6, 18, 12, 8, 8, 8, 6, 6, 10, 6};
            table.setWidths(width);
            table.setPadding(1);
            table.setAutoFillEmptyCells(true);
            table.setDefaultHorizontalAlignment(Element.ALIGN_LEFT);
            table.setDefaultVerticalAlignment(Element.ALIGN_TOP);
            table.addCell(new Phrase(8, "商品\nコード", font));
            table.addCell(new Phrase(8, "商品名", font));
            table.addCell(new Phrase(8, "規格", font));
            table.addCell(new Phrase(8, "賞味期限", font));
            table.addCell(new Phrase(8, "実賞味期限", font));
            table.addCell(new Phrase(8, "出荷期限", font));
            table.addCell(new Phrase(8, "数量", font));
            table.addCell(new Phrase(8, "実数", font));
            table.addCell(new Phrase(8, "ロケーション", font));
            table.addCell(new Phrase(8, "担当者", font));
            return table;
    }
}
