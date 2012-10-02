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

public class NouhinshoPdf extends PdfObjectBase {

    private ShukkayoteiData shukkayoteiData;
    private Collection nouhinList;
    private java.util.Date makedate;

    public void setShukkayoteiData(ShukkayoteiData data) {
        this.shukkayoteiData = data;
    }
    public void setNouhinList(Collection nouhinList) {
        this.nouhinList = nouhinList;
    }

    protected String getDocTypeName() { return "nouhinsho"; }
    protected Document getDocument() {
        return new Document(PageSize.A4, 36, 36, 36 + 16 * 7, 36 + 16 * 6);
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

        Iterator iter = nouhinList.iterator();
        while (iter.hasNext()) {
            HashMap nouhin = (HashMap)iter.next();
            ShukkayoteimeisaiData meisai =
              (ShukkayoteimeisaiData)nouhin.get("SHUKKAYOTEIMEISAI");
            ShouhinData shouhin = (ShouhinData)nouhin.get("SHOUHIN");
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
          "納品書", 297, 788, 0);
        cb.setFontAndSize(bf, 12);
        int left1 = 50;
        int left2 = 200;
        int left3 = 350;
        int height = 756;
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT,
          "納品先コード", left1, height, 0);
        if (shukkayoteiData.getNouhinsaki_id() != null) {
            cb.showTextAligned(PdfContentByte.ALIGN_LEFT,
              shukkayoteiData.getNouhinsaki_id(), left2, height, 0);
        }
        height -= 16;
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT,
          "納品先名", left1, height, 0);
        String nouhinsakiname = shukkayoteiData.getNouhinsakiname();
        if (nouhinsakiname != null) {
            cb.showTextAligned(PdfContentByte.ALIGN_LEFT,
              nouhinsakiname, left2, height, 0);
        }
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT,
          "様", left3, height, 0);
        height -= 16;
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT,
          "納品先住所", left1, height, 0);
        String nouhinsakizip = shukkayoteiData.getNouhinsakizip();
        String nouhinsakiaddr = shukkayoteiData.getNouhinsakiaddr();
        if (nouhinsakizip != null && nouhinsakiaddr != null) {
            cb.showTextAligned(PdfContentByte.ALIGN_LEFT,
              "〒" + nouhinsakizip + "　" + nouhinsakiaddr,
              left2, height, 0);
        }

        // フッター
        SimpleDateFormat ymdhms = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String datepage = ymdhms.format(makedate);
        datepage += "　　　";
        datepage += "page:" + page + "/";
        cb.showTextAligned(PdfContentByte.ALIGN_CENTER,
          datepage, 297, 30, 0);
        cb.endText();
        float len = bf.getWidthPoint(datepage, 12);
        cb.addTemplate(template, 297 + len / 2, 30);
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
