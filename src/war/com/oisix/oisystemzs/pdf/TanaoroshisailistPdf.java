package com.oisix.oisystemzs.pdf;

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
import java.io.IOException;
import java.util.HashMap;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Calendar;
import java.text.SimpleDateFormat;

public class TanaoroshisailistPdf extends PdfObjectBase {

    private java.util.Date tanaoroshidate;
    private LinkedList tanaoroshisailist;

    private java.util.Date makedate;

    public void setTanaoroshisailist(LinkedList tanaoroshisailist) {
        this.tanaoroshisailist = tanaoroshisailist;
    }
    public void setTanaoroshidate(java.util.Date tanaoroshidate) {
        this.tanaoroshidate = tanaoroshidate;
    }

    protected String getDocTypeName() { return "tanaoroshisailist"; }
    protected Document getDocument() {
        return new Document(PageSize.A4.rotate(), 36, 36,
          36 + 16 * 3, 36 + 16 * 3);
    }

    protected void doMakeDocument() throws IOException, DocumentException {
        Calendar cal = Calendar.getInstance();
        makedate = cal.getTime();

        BaseFont bf = BaseFont.createFont(
          "HeiseiKakuGo-W5", "UniJIS-UCS2-HW-H",false);
        Font font = new Font(bf, 12);
        PdfTemplate template = cb.createTemplate(50, 50);
        int page = 1;

        makeTemplate(bf, page, template);
        Table table = getTable(font);

        Iterator iter = tanaoroshisailist.iterator();
        while (iter.hasNext()) {
            HashMap tanaoroshi = (HashMap)iter.next();
            addNewColumn(table, font, tanaoroshi);
            if (!writer.fitsPage(table)) {
                table.deleteLastRow();
                document.add(table);
                document.newPage();
                page++;
                makeTemplate(bf, page, template);
                table = getTable(font);
                addNewColumn(table, font, tanaoroshi);
            }
        }
        document.add(table);
        template.beginText();
        template.setFontAndSize(bf, 12);
        template.showText(String.valueOf(page));
        template.endText();
    }

    protected void addNewColumn(Table table, Font font, HashMap tanaoroshi)
      throws BadElementException {
        float suuryou = ((Float)tanaoroshi.get("SUURYOU")).floatValue();
        float sai = ((Float)tanaoroshi.get("SAI")).floatValue();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        java.util.Date shoumikigen = null;
        shoumikigen = (java.util.Date)tanaoroshi.get("SHOUMIKIGEN");
        String shoumikigenstr = "--------";
        if (shoumikigen != null) {
            shoumikigenstr = sdf.format(shoumikigen);
        }
        java.util.Date shukkakigen = null;
        shukkakigen = (java.util.Date)tanaoroshi.get("SHUKKAKIGEN");
        String shukkakigenstr = "--------";
        if (shukkakigen != null) {
            shukkakigenstr = sdf.format(shukkakigen);
        }
        table.addCell(new Phrase(12,
          (String)tanaoroshi.get("SHOUHIN_ID"), font));
        table.addCell(new Phrase(12,
          (String)tanaoroshi.get("SHOUHINMEI"), font));
        table.addCell(new Phrase(12,
          (String)tanaoroshi.get("KIKAKU"), font));
        table.addCell(new Phrase(12, shoumikigenstr, font));
        table.addCell(new Phrase(12, shukkakigenstr, font));
        table.addCell(new Phrase(12, String.valueOf(suuryou), font));
        table.addCell(new Phrase(12, String.valueOf(sai), font));
        table.addCell("");
    }

    protected void makeTemplate(BaseFont bf, int page, PdfTemplate template) {
        cb.beginText();
        // ヘッダー
        String title = "棚卸差異リスト";
        cb.setFontAndSize(bf, 20);
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT, title, 220, 541, 0);
        cb.setFontAndSize(bf, 12);
        SimpleDateFormat ymd = new SimpleDateFormat("yyyy年M月d日");
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT,
          "棚卸日　" + ymd.format(tanaoroshidate), 560, 541, 0);

        // フッター
        float left1 = 100;
        float left2 = 340;
        float height = 100;

        height = 30;
        cb.setFontAndSize(bf, 12);
        SimpleDateFormat ymdhms = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String datepage = ymdhms.format(makedate);
        datepage += "　　　";
        datepage += "page:" + page + "/";
        cb.showTextAligned(PdfContentByte.ALIGN_CENTER,
          datepage, 421, height, 0);
        cb.endText();
        float len = bf.getWidthPoint(datepage, 12);
        cb.addTemplate(template, 421 + len / 2, height);
    }

    private Table getTable(Font font)
      throws DocumentException, BadElementException, IOException {
            Table table = new Table(8);
            table.setWidth(100);
            int width[] = {6, 17, 7, 10, 10, 6, 6, 20};
            table.setWidths(width);
            table.setPadding(1);
            table.setAutoFillEmptyCells(true);
            table.setDefaultHorizontalAlignment(Element.ALIGN_LEFT);
            table.setDefaultVerticalAlignment(Element.ALIGN_TOP);
            table.addCell(new Phrase(12, "商品\nコード", font));
            table.addCell(new Phrase(12, "商品名", font));
            table.addCell(new Phrase(12, "規格", font));
            table.addCell(new Phrase(12, "賞味期限", font));
            table.addCell(new Phrase(12, "出荷期限", font));
            table.addCell(new Phrase(12, "数量", font));
            table.addCell(new Phrase(12, "差異", font));
            table.addCell(new Phrase(12, "差異の原因", font));
            return table;
    }
}
