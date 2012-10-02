package com.oisix.oisystemzs.pdf;

import com.oisix.oisystemfr.pdf.PdfObjectBase;
import com.oisix.oisystemfr.DateUtil;
import com.oisix.oisystemzs.objectmap.TouchakuMap;
import com.oisix.oisystemzs.ejb.OfficeData;
import com.oisix.oisystemzs.eventhandler.HacchuushoHeader;
import com.oisix.oisystemzs.eventhandler.HacchuushoMeisai;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Cell;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Phrase;
import com.lowagie.text.Table;
import com.lowagie.text.BadElementException;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;
import java.text.SimpleDateFormat;
import java.text.DecimalFormat;

public class ChokusouHacchuushoPdf {

    public static void makeDocument(Document document, PdfContentByte cb,
      PdfWriter writer, HacchuushoHeader h, int index)
      throws IOException, DocumentException {
        int page = 1;
        PdfTemplate template = cb.createTemplate(50, 50);
        makeTemplate(document, cb, page, h, template);
        Table table = getTable();
        TreeMap meisai = h.getMeisai();
        Set mkeys = meisai.keySet();
        Iterator miter = mkeys.iterator();
        float goukei = 0;
        while (miter.hasNext()) {
            String shouhin_id = (String)miter.next();
            TreeMap datemeisai = (TreeMap)meisai.get(shouhin_id);
            Set dkeys = datemeisai.keySet();
            Iterator diter = dkeys.iterator();
            while (diter.hasNext()) {
                java.sql.Date date = (java.sql.Date)diter.next();
                HacchuushoMeisai m =
                  (HacchuushoMeisai)datemeisai.get(date);
                if (m != null) {
                    addRow(m, table);
                    if (!writer.fitsPage(table)) {
                        table.deleteLastRow();
                        document.add(table);
                        document.newPage();
                        page++;
                        makeTemplate(document, cb, page, h, template);
                        table = getTable();
                        addRow(m, table);
                    }
                    goukei += m.getHacchuusuuryou() * m.getHacchuutanka();
                }
            }
        }
        addGoukei(goukei, table);
        if (!writer.fitsPage(table)) {
            table.deleteLastRow();
            document.add(table);
            document.newPage();
            page++;
            makeTemplate(document, cb, page, h, template);
            table = getTable();
            addGoukei(goukei, table);
        }
        document.add(table);
        BaseFont bf = BaseFont.createFont(
          "HeiseiKakuGo-W5", "UniJIS-UCS2-HW-H",false);
        Font font = new Font(bf, 12);
        template.beginText();
        template.setFontAndSize(bf, 10);
        template.showTextAligned(PdfContentByte.ALIGN_RIGHT,
          String.valueOf(page), 20, 0, 0);
        template.endText();
    }

    private static void makeTemplate(Document document, PdfContentByte cb,
      int page, HacchuushoHeader h, PdfTemplate template)
      throws DocumentException, IOException {
        BaseFont bf = BaseFont.createFont(
          "HeiseiKakuGo-W5", "UniJIS-UCS2-HW-H",false);
        Font font = new Font(bf, 12);
        cb.beginText();
        cb.setFontAndSize(bf, 16);
        int y = 842-36-16;
        cb.showTextAligned(PdfContentByte.ALIGN_CENTER,
          "発注書 兼 注文請書", 298, y, 0);
        cb.setFontAndSize(bf, 12);
        y -= 16;
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT,
          h.getShiiresaki() + " 御中", 36, y, 0);
        cb.setFontAndSize(bf, 10);
        y -= 16;
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT,
          "TEL:" + h.getShiiretel(), 36, y, 0);
        y -= 12;
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT,
          "FAX:" + h.getShiirefax(), 36, y, 0);
        y -= 12;
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT,
          "下記のように発注いたします。", 36, y, 0);
        y -= 12 + 16 + 1;
        cb.setFontAndSize(bf, 12);
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT,
          "*** 必ずお読みください ***", 36+2, y, 0);
        cb.setFontAndSize(bf, 10);
        OfficeData office = h.getOfficeData();
        y -= 12 + 3;
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT,
          "・単価・数量をご確認の上、確認欄にご押印頂き、" + office.getFax() +
          "宛てにFAXにてご返信ください。", 36+2, y, 0);
        y -= 12;
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT,
          "  *別途送料をご請求される場合は、送料記入欄に送料総額(税抜)を" + 
          "ご記入ください。", 36+2, y, 0);
        y -= 12;
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT,
          "・FAXのご返信がない場合、当発注書のうち納品が確認できたものにつき" +
          "お支払いいたします。予めご了承ください。", 36+2, y, 0);
        cb.showTextAligned(PdfContentByte.ALIGN_CENTER, "確認欄",
          595-36-60-48/2, 766-13, 0);
        cb.showTextAligned(PdfContentByte.ALIGN_CENTER, "送料記入欄",
          595-36-60/2, 766-13, 0);
        java.util.Date date = DateUtil.getDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        String datepage = sdf.format(date) + " No." + page + "/";
        cb.showTextAligned(PdfContentByte.ALIGN_RIGHT,
          datepage, 595-36-20, 842-36-16-12, 0);
        cb.showTextAligned(PdfContentByte.ALIGN_RIGHT,
          "発注番号 " + h.getHacchuu_bg(), 595-36, 842-36-16-12*2, 0);
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT,
          "オイシックス株式会社 担当 " + h.getTantousha(), 36, 36+16*4 , 0);
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT,
          office.getAddr(), 36, 36+16*3 , 0);
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT,
          "TEL: " + office.getTel(), 36, 36+16*2 , 0);
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT,
          "FAX: " + office.getFax(), 36, 36+16 , 0);
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT,
          "連絡事項", 300, 36+16*4, 0);
        cb.endText();
        //連絡事項
        cb.addTemplate(template, 595-36-20, 842-36-16-12);
        cb.rectangle(300, 36, 595-36-300, 16*4);
        cb.stroke();
        //送料記入欄
        cb.setLineWidth(3.0f);
        cb.rectangle(595-36-60, 766-14, 60, 12);
        cb.stroke();
        cb.rectangle(595-36-60, 766-14-30, 60, 30);
        cb.stroke();
        //確認欄
        cb.rectangle(595-36-60-48, 766-14, 48, 12);
        cb.stroke();
        cb.rectangle(595-36-60-48, 766-14-30, 48, 30);
        cb.stroke();
        //注意書きの囲み
        cb.rectangle(36, 734-72, 595-36-36, 55);
        cb.stroke();
    }

    private static void addRow(HacchuushoMeisai m, Table table)
      throws DocumentException, IOException {
        try {
            BaseFont bf = BaseFont.createFont(
              "HeiseiKakuGo-W5", "UniJIS-UCS2-HW-H",false);
            Font font = new Font(bf, 12);
            DecimalFormat df = new DecimalFormat("###,###,###.#");
            table.addCell(new Phrase(12, m.getHacchuushouhin1(), font));
            table.addCell(new Phrase(12, m.getHacchuukikaku(), font));
            Cell cell = new Cell(new Phrase(12, df.format(
              m.getHacchuusuuryou()), font));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(cell);
            cell = new Cell(new Phrase(12, df.format(m.getHacchuutanka()),
              font));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(cell);
            cell = new Cell(new Phrase(12, df.format(m.getHacchuutanka() * 
              m.getHacchuusuuryou()), font));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(cell);
        } catch (BadElementException be) {
            be.printStackTrace();
        }
    }

    private static void addGoukei(float goukei, Table table)
      throws DocumentException, IOException {
        try {
            BaseFont bf = BaseFont.createFont(
              "HeiseiKakuGo-W5", "UniJIS-UCS2-HW-H",false);
            Font font = new Font(bf, 12);
            DecimalFormat df = new DecimalFormat("###,###,###.#");
            Cell cell = new Cell(new Phrase(12, "合計", font));
            cell.setColspan(4);
            table.addCell(cell);
            cell = new Cell(new Phrase(12, df.format(goukei), font));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(cell);
        } catch (BadElementException be) {
            be.printStackTrace();
        }
    }

    private static Table getTable()
      throws DocumentException, BadElementException, IOException {
        BaseFont bf = BaseFont.createFont(
          "HeiseiKakuGo-W5", "UniJIS-UCS2-HW-H",false);
        Font font = new Font(bf, 12);
        Table table = new Table(5);
        table.setWidth(100);
        int width[] = {49, 20, 10, 10, 11};
        table.setWidths(width);
        table.setPadding(3);
        table.setAutoFillEmptyCells(true);
        table.setDefaultHorizontalAlignment(Element.ALIGN_LEFT);
        table.setDefaultVerticalAlignment(Element.ALIGN_TOP);
        table.addCell(new Phrase(12, "商品名", font));
        table.addCell(new Phrase(12, "規格", font));
        table.addCell(new Phrase(12, "数量", font));
        table.addCell(new Phrase(12, "単価", font));
        table.addCell(new Phrase(12, "金額", font));
        return table;
    }
}
