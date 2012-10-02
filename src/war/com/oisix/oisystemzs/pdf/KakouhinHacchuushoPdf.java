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

public class KakouhinHacchuushoPdf {

    public static void makeDocument(Document document, PdfContentByte cb,
      PdfWriter writer, HacchuushoHeader h, int index)
      throws IOException, DocumentException {
        int page = 1;
        PdfTemplate template = cb.createTemplate(50, 50);
        makeTemplate(document, cb, page, h, template, index);
        Table table = getTable();
        TreeMap meisai = h.getMeisai();
        Set mkeys = meisai.keySet();
        Iterator miter = mkeys.iterator();
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
                        makeTemplate(document, cb, page, h, template, index);
                        table = getTable();
                        addRow(m, table);
                    }
                }
            }
        }
        document.add(table);
        BaseFont bf = BaseFont.createFont(
          "HeiseiKakuGo-W5", "UniJIS-UCS2-H",false);
        template.beginText();
        template.setFontAndSize(bf, 12);
        template.showTextAligned(PdfContentByte.ALIGN_RIGHT,
          String.valueOf(page), 20, 0, 0);
        template.endText();
    }

    private static void makeTemplate(Document document, PdfContentByte cb,
      int page, HacchuushoHeader h, PdfTemplate template, int index)
      throws DocumentException, IOException {
        BaseFont bf = BaseFont.createFont(
          "HeiseiKakuGo-W5", "UniJIS-UCS2-H",false);
        Font font = new Font(bf, 12);
        cb.beginText();
        cb.setFontAndSize(bf, 16);
        cb.showTextAligned(PdfContentByte.ALIGN_CENTER,
          "発注書 兼 注文請書", 421, 541, 0);
        //cb.showTextAligned(PdfContentByte.ALIGN_CENTER,
        //  "発注書 兼 納期回答書", 421, 541, 0);
        //  "加工食品発注書兼納期回答書", 421, 541, 0);
        //左上
        cb.setFontAndSize(bf, 13);
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT,
          h.getShiiresaki() + " 御中", 36, 525, 0);
        cb.setFontAndSize(bf, 11);
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT,
          "TEL:" + h.getShiiretel(), 36, 525 - 12, 0);
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT,
          "FAX:" + h.getShiirefax(), 36, 525 - 12 * 2, 0);
        cb.setFontAndSize(bf, 10);
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT,
          "毎度格別のお引き立てを賜り誠にありがとうございます。" +
          "下記のように発注いたしますので、よろしくお願いいたします。",
          36, 525 - 12 * 3, 0);

        cb.setFontAndSize(bf, 13);
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT,
          "*** 必ずお読みください ***", 36 + 4, 525 - 12 * 4 - 6, 0);

        cb.setFontAndSize(bf, 10);
        OfficeData office = h.getOfficeData();
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT,
          "・規格・単価・数量・納期をご確認の上、確認欄にご押印頂き、 " + 
          office.getFax() + " 宛にＦＡＸにてご返信ください。",
          36, 525 - 12 * 6 - 2, 0);
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT,
          "  （内容に異なる点がある場合、指定納期での納品ができない場合には、" +
          "至急お電話にてお知らせください）",
          36, 525 - 12 * 7 - 2, 0);
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT,
          "  * 別途送料をご請求される場合は、送料記入欄に送料総額（税抜）" +
          "をご記入下さい。", 36, 525 - 12 * 8 - 2, 0);
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT,
          "・FAXのご返信が無い場合、当発注書の金額のうち納品が確認できたもの" +
          "につきお支払致します。予めご了承下さい。", 36, 525 - 11 * 10 - 2, 0);
        //右上
        cb.setFontAndSize(bf, 12);
        java.util.Date date = DateUtil.getDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年M月d日");
        String datepage = sdf.format(date) + " No." + page + "/";
        cb.showTextAligned(PdfContentByte.ALIGN_RIGHT,
          datepage, 806 - 20, 525, 0);
        //cb.showTextAligned(PdfContentByte.ALIGN_RIGHT,
        //  "9999年99月99日 No." + page, 806, 525, 0);
        cb.showTextAligned(PdfContentByte.ALIGN_RIGHT,
          "発注番号 " + h.getHacchuu_bg(), 806, 525-12, 0);
        cb.showTextAligned(PdfContentByte.ALIGN_RIGHT,
          String.valueOf(index), 806, 525-12*2, 0);
        //下
        cb.setFontAndSize(bf, 10);
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT,
          "オイシックス株式会社 担当 " + h.getTantousha(), 36, 100 , 0);
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT,
          "〒" + office.getYuubin(), 36, 100-12 , 0);
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT,
          office.getAddr(), 36, 100-12*2 , 0);
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT,
          "TEL: " + office.getTel(), 36, 100-12*3 , 0);
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT,
          "FAX: " + office.getFax(), 36, 100-12*4 , 0);
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT,
          "納品先:", 300, 100 , 0);
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT,
          h.getNouhinsaki() , 300, 100-12 , 0);
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT,
          "〒" + h.getNouhinyuubin(), 300, 100-12*2 , 0);
        String nouhinaddr = h.getNouhinaddr();
        if (nouhinaddr == null) { nouhinaddr = ""; }
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT,
          nouhinaddr, 300, 100-12*3 , 0);
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT,
          "TEL: " + h.getNouhintel(), 300, 100-12*4 , 0);
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT,
          "FAX: " + h.getNouhinfax(), 300, 100-12*5 , 0);
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT,
          "連絡事項", 842-36-72*3, 100, 0);
        cb.showTextAligned(PdfContentByte.ALIGN_CENTER,
          "送料記入欄", 806-60+30, 525-12*4-1, 0);
        cb.showTextAligned(PdfContentByte.ALIGN_CENTER,
          "確認欄", 806-60-60+30, 525-12*4-1, 0);
        cb.endText();
        cb.addTemplate(template, 806 - 20, 525);
        cb.rectangle(842-36-72*3, 36, 72*3,16*4);
        cb.stroke();
        cb.moveTo(36, 525-1);
        cb.lineTo(36+320, 525-1);
        cb.stroke();
        //注意書きの囲み
        cb.setLineWidth(3.0f);
        //cb.setLineDash(3, 3, 0);
        cb.rectangle(36, 525-12*10-6, 600, 12*7+3);
        cb.stroke();
        //送料記入欄
        cb.setLineWidth(3.0f);
        cb.rectangle(806-60, 525-12*4-3, 60, 12);
        cb.stroke();
        cb.rectangle(806-60, 525-12*8-3, 60, 48);
        cb.stroke();
        //確認欄
        cb.rectangle(806-60-60, 525-12*4-3, 60, 12);
        cb.stroke();
        cb.rectangle(806-60-60, 525-12*8-3, 60, 48);
        cb.stroke();

    }

    private static void addRow(HacchuushoMeisai m, Table table)
      throws DocumentException, IOException {
        try {
            BaseFont bf = BaseFont.createFont(
              "HeiseiKakuGo-W5", "UniJIS-UCS2-H",false);
            Font font = new Font(bf, 10);
            Font bigfont = new Font(bf, 15);
            Font big2font = new Font(bf, 18);
            DecimalFormat df = new DecimalFormat("###,###,###.#");
            Cell cell = new Cell(new Phrase(18, m.getShouhin_id(), font));
            cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
            table.addCell(cell);
            cell = new Cell(new Phrase(18, m.getHacchuushouhin1(), font));
            cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
            table.addCell(cell);
            cell = new Cell(new Phrase(18, m.getHacchuukikaku(), font));
            cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
            table.addCell(cell);
            cell = new Cell(
              new Phrase(18, String.valueOf(m.getIrisuu()), font));
            cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
            table.addCell(cell);
            cell = new Cell(
              new Phrase(18, df.format(m.getHacchuutanka()), font));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
            table.addCell(cell);
            String suuryou = df.format(m.getHacchuusuuryou());
            suuryou = suuryou.replaceAll("0", "０");
            suuryou = suuryou.replaceAll("1", "１");
            suuryou = suuryou.replaceAll("2", "２");
            suuryou = suuryou.replaceAll("3", "３");
            suuryou = suuryou.replaceAll("4", "４");
            suuryou = suuryou.replaceAll("5", "５");
            suuryou = suuryou.replaceAll("6", "６");
            suuryou = suuryou.replaceAll("7", "７");
            suuryou = suuryou.replaceAll("8", "８");
            suuryou = suuryou.replaceAll("9", "９");
            cell = new Cell(
              new Phrase(18, suuryou, bigfont));
            cell.setBorderWidth(2);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
            table.addCell(cell);
            cell = new Cell(new Phrase(18, m.getHacchuutani(), bigfont));
            cell.setBorderWidth(2);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
            table.addCell(cell);
            cell = new Cell(new Phrase(18, "(" + 
              df.format(m.getNyuukasuuryou()) + ")", font));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
            table.addCell(cell);
            cell = new Cell(new Phrase(18, df.format(m.getHacchuutanka() * 
              m.getHacchuusuuryou()), font));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
            table.addCell(cell);
            SimpleDateFormat sdf = new SimpleDateFormat(
              "M月d日");
            String date = sdf.format(m.getNyuukayotei_date());
            cell = new Cell(new Phrase(18, date, bigfont));
            cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
            table.addCell(cell);
            String touchaku = TouchakuMap.getTouchaku(m.getTouchakujikan()).
              getTouchaku();
            cell = new Cell(new Phrase(18, touchaku, font));
            cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
            table.addCell(cell);
            table.addCell(new Phrase(18, " ", big2font));
        } catch (BadElementException be) {
            be.printStackTrace();
        }
    }

    private static Table getTable()
      throws DocumentException, BadElementException, IOException {
        BaseFont bf = BaseFont.createFont(
          "HeiseiKakuGo-W5", "UniJIS-UCS2-H",false);
        Font font = new Font(bf, 10);
        Table table = new Table(12);
        table.setWidth(100);
        int width[] = {
          5,  //商品コード
          23, //商品名
          10, //規格
          5,  //入り数
          5,  //単価
          8,  //数量
          7,  //単位
          5,  //バラ数量
          7,  //金額
          9, //納品日
          7,  //時間帯
          9};//納期回答欄
        table.setWidths(width);
        table.setPadding(3);
        table.setAutoFillEmptyCells(true);
        table.setDefaultHorizontalAlignment(Element.ALIGN_LEFT);
        table.setDefaultVerticalAlignment(Element.ALIGN_TOP);
        table.addCell(new Phrase(10, "商品\nコード", font));
        table.addCell(new Phrase(10, "商品名", font));
        table.addCell(new Phrase(10, "規格", font));
        table.addCell(new Phrase(10, "一箱\n入り数", font));
        table.addCell(new Phrase(10, "単価", font));
        table.addCell(new Phrase(10, "数量", font));
        table.addCell(new Phrase(10, "単位", font));
        table.addCell(new Phrase(10, "バラ\n数量", font));
        table.addCell(new Phrase(10, "金額", font));
        table.addCell(new Phrase(10, "納品日", font));
        table.addCell(new Phrase(10, "時間帯", font));
        table.addCell(new Phrase(10, "納期回答欄", font));
        return table;
    }
}
