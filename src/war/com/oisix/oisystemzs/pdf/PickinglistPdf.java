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
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.text.DecimalFormat;
import com.oisix.oisystemzs.ejb.ShukkayoteiData;
import com.oisix.oisystemzs.ejb.ShukkayoteimeisaiData;
import com.oisix.oisystemzs.ejb.ShouhinData;
import com.oisix.oisystemzs.ejb.NouhinsakiData;

public class PickinglistPdf extends PdfObjectBase {

    private ShukkayoteiData shukkayoteiData;
    private NouhinsakiData nouhinsakiData;
    private Collection pickinglist;
    private java.util.Date makedate;

    public void setShukkayoteiData(ShukkayoteiData data) {
        this.shukkayoteiData = data;
    }
    public void setNouhinsakiData(NouhinsakiData data) {
        this.nouhinsakiData = data;
    }
    public void setPickinglist(Collection pickinglist) {
        this.pickinglist = pickinglist;
    }

    protected String getDocTypeName() { return "pickinglist"; }
    protected Document getDocument() {
        return new Document(PageSize.A4, 36, 36, 36 + 16 * 3, 36 + 16);
    }

    protected void doMakeDocument() throws IOException, DocumentException {
        Calendar cal = Calendar.getInstance();
        makedate = cal.getTime();

        BaseFont bf = BaseFont.createFont(
          "HeiseiKakuGo-W5", "UniJIS-UCS2-HW-H",false);
        Font font = new Font(bf, 12);
        Font sfont = new Font(bf, 10);
        PdfTemplate template = cb.createTemplate(50, 50);
        int page = 1;

        makeTemplate(bf, page, template);

        Table headertable = new Table(6);
        headertable.setWidth(80);
        int width[] = { 8, 12, 8, 12, 14, 16 };
        headertable.setWidths(width);
        headertable.setPadding(3);
        headertable.setAutoFillEmptyCells(true);
        headertable.setAlignment(Element.ALIGN_LEFT);
        headertable.setDefaultHorizontalAlignment(Element.ALIGN_LEFT);
        headertable.setDefaultVerticalAlignment(Element.ALIGN_TOP);
        headertable.addCell(new Phrase(12, "出荷日", font));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String shukkayotei_date = sdf.format(
          shukkayoteiData.getShukkayotei_date());
        headertable.addCell(new Phrase(12, shukkayotei_date, font));
        headertable.addCell(new Phrase(12, "納品日", font));
        java.util.Date nouhinyotei_date =shukkayoteiData.getNouhinyotei_date();
        if (nouhinyotei_date != null) {
            String nyd = sdf.format(nouhinyotei_date);
            headertable.addCell(new Phrase(12, nyd, font));
        } else {
            headertable.addCell("");
        }
        headertable.addCell(new Phrase(12, "出荷予定番号", font));
        headertable.addCell(new Phrase(12,
          shukkayoteiData.getShukkayotei_bg(), font));
        document.add(headertable);

        Table headertable1 = new Table(2);
        headertable1.setWidth(80);
        int width1[] = { 1, 3 };
        headertable1.setWidths(width1);
        headertable1.setPadding(3);
        headertable1.setAutoFillEmptyCells(true);
        headertable1.setAlignment(Element.ALIGN_LEFT);
        headertable1.setDefaultHorizontalAlignment(Element.ALIGN_LEFT);
        headertable1.setDefaultVerticalAlignment(Element.ALIGN_TOP);
        headertable1.addCell(new Phrase(12, "納品先コード", font));
        String nouhinsaki_id = shukkayoteiData.getNouhinsaki_id();
        Phrase space = new Phrase(12, "", font);
        if (nouhinsaki_id != null) {
            headertable1.addCell(new Phrase(12, nouhinsaki_id, font));
        } else {
            headertable1.addCell(space);
        }
        headertable1.addCell(new Phrase(12, "納品先名", font));
        String nouhinsaki = shukkayoteiData.getNouhinsakiname();
        if ((nouhinsaki == null) && (nouhinsakiData != null)) {
            nouhinsaki = nouhinsakiData.getName();
        }
        if (nouhinsaki != null) {
            headertable1.addCell(new Phrase(12, nouhinsaki, font));
        } else {
            headertable1.addCell(space);
        }

        headertable1.addCell(new Phrase(12, "納品先郵便番号", font));
        String nouhinsakizip = shukkayoteiData.getNouhinsakizip();
        if ((nouhinsakizip == null) && (nouhinsakiData != null)) {
            nouhinsakizip = nouhinsakiData.getYuubin();
        }
        if (nouhinsakizip != null) {
            headertable1.addCell(new Phrase(12, nouhinsakizip, font));
        } else {
            headertable1.addCell(space);
        }

        headertable1.addCell(new Phrase(12, "納品先住所", font));
        String nouhinsakiaddr = shukkayoteiData.getNouhinsakiaddr();
        if ((nouhinsakiaddr == null) && (nouhinsakiData != null)) {
            nouhinsakiaddr = nouhinsakiData.getAddr();
        }
        if (nouhinsakiaddr != null) {
            headertable1.addCell(new Phrase(12, nouhinsakiaddr, font));
        } else {
            headertable1.addCell(space);
        }

        headertable1.addCell(new Phrase(12, "納品先電話番号", font));
        String nouhinsakitel = shukkayoteiData.getNouhinsakitel();
        if ((nouhinsakitel == null) && (nouhinsakiData != null)) {
            nouhinsakitel = nouhinsakiData.getTel();
        }
        if (nouhinsakitel != null) {
            headertable1.addCell(new Phrase(12, nouhinsakitel, font));
        } else {
            headertable1.addCell(space);
        }
        document.add(headertable1);

        document.add(new Phrase(6, "\n", font));

        Table table = getTable(font);
        Iterator iter = pickinglist.iterator();
        while (iter.hasNext()) {
            HashMap picking = (HashMap)iter.next();
            ShukkayoteimeisaiData meisai =
              (ShukkayoteimeisaiData)picking.get("SHUKKAYOTEIMEISAI");
            ShouhinData shouhin = (ShouhinData)picking.get("SHOUHIN");
            addNewColumn(table, font, shouhin, meisai);
            if (!writer.fitsPage(table)) {
                table.deleteLastRow();
                document.add(table);
                document.newPage();
                page++;
                makeTemplate(bf, page, template);
                table = getTable(font);
                addNewColumn(table, font, shouhin, meisai);
            }
        }
        document.add(table);

        document.add(new Phrase(12, "\n\n備考", font));

        Table footertable = new Table(1);
        footertable.setOffset(0);
        footertable.setWidth(100);
        footertable.setPadding(3);
        footertable.setAutoFillEmptyCells(true);
        footertable.setAlignment(Element.ALIGN_LEFT);
        footertable.setDefaultHorizontalAlignment(Element.ALIGN_LEFT);
        footertable.setDefaultVerticalAlignment(Element.ALIGN_TOP);
        String bikou = shukkayoteiData.getBikou();
        if (bikou != null) {
            footertable.addCell(new Phrase(12, bikou, font));
        }
        document.add(footertable);

        document.add(new Phrase(6, "\n", font));

        Table footertable1 = new Table(7);
        footertable1.setWidth(80);
        int width2[] = { 1, 1, 1, 1, 1, 1, 1 };
        footertable1.setWidths(width2);
        footertable1.setPadding(1);
        footertable1.setAutoFillEmptyCells(true);
        footertable1.setAlignment(Element.ALIGN_LEFT);
        footertable1.setDefaultHorizontalAlignment(Element.ALIGN_LEFT);
        footertable1.setDefaultVerticalAlignment(Element.ALIGN_TOP);
        footertable1.addCell(new Phrase(10, "加工品P", sfont));
        footertable1.addCell(new Phrase(10, "日配品P", sfont));
        footertable1.addCell(new Phrase(10, "冷凍P", sfont));
        footertable1.addCell(new Phrase(10, "生鮮P", sfont));
        footertable1.addCell(new Phrase(10, "梱包", sfont));
        footertable1.addCell(new Phrase(10, "出庫", sfont));
        footertable1.addCell(new Phrase(10, "リスト返却", sfont));
        footertable1.addCell(new Phrase(20, "", font));
        footertable1.addCell(new Phrase(20, "", font));
        footertable1.addCell(new Phrase(20, "", font));
        footertable1.addCell(new Phrase(20, "", font));
        footertable1.addCell(new Phrase(20, "", font));
        footertable1.addCell(new Phrase(20, "", font));
        footertable1.addCell(new Phrase(20, "", font));

        document.add(footertable1);

        template.beginText();
        template.setFontAndSize(bf, 12);
        template.showText(String.valueOf(page));
        template.endText();
    }

    protected void addNewColumn(Table table, Font font,
      ShouhinData shouhin, ShukkayoteimeisaiData meisai)
      throws BadElementException {
        float suuryou = meisai.getShukkayoteisuuryou();
        DecimalFormat df = new DecimalFormat("0.###");
        String suuryoustr = df.format(suuryou);
        table.addCell(new Phrase(12, shouhin.getShouhin_id(), font));
        table.addCell(new Phrase(12, shouhin.getShouhin(), font));
        table.addCell(new Phrase(12, shouhin.getKikaku(), font));
        table.addCell(new Phrase(12, suuryoustr, font));
        table.addCell(new Phrase(12, meisai.getTani(), font));
        table.addCell(new Phrase(12, "", font));
    }

    protected void makeTemplate(BaseFont bf, int page, PdfTemplate template) {
        cb.beginText();
        // ヘッダー
        cb.setFontAndSize(bf, 12);
        SimpleDateFormat ymd = new SimpleDateFormat("yyyy年M月d日");
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT,
          ymd.format(makedate), 20, 812, 0);
        cb.setFontAndSize(bf, 16);
        cb.showTextAligned(PdfContentByte.ALIGN_CENTER,
          "ピッキングリスト", 297, 788, 0);
        cb.setFontAndSize(bf, 12);

        // フッター
        int height = 30;
        SimpleDateFormat ymdhms = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String datepage = ymdhms.format(makedate);
        datepage += "　　　";
        datepage += "page:" + page + "/";
        cb.showTextAligned(PdfContentByte.ALIGN_CENTER,
          datepage, 297, height, 0);
        cb.endText();
        float len = bf.getWidthPoint(datepage, 12);
        cb.addTemplate(template, 297 + len / 2, height);
    }

    private Table getTable(Font font)
      throws DocumentException, BadElementException, IOException {
            Table table = new Table(6);
            table.setWidth(100);
            int width[] = {2, 5, 2, 1, 2, 3};
            table.setWidths(width);
            table.setPadding(3);
            table.setAutoFillEmptyCells(true);
            table.setDefaultHorizontalAlignment(Element.ALIGN_LEFT);
            table.setDefaultVerticalAlignment(Element.ALIGN_TOP);
            table.addCell(new Phrase(12, "商品コード", font));
            table.addCell(new Phrase(12, "商品名", font));
            table.addCell(new Phrase(12, "規格", font));
            table.addCell(new Phrase(12, "数量", font));
            table.addCell(new Phrase(12, "単位", font));
            table.addCell(new Phrase(12, "備考", font));
            return table;
    }
}
