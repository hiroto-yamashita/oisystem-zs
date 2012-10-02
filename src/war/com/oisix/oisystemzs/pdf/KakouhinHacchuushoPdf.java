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
          "������ �� ��������", 421, 541, 0);
        //cb.showTextAligned(PdfContentByte.ALIGN_CENTER,
        //  "������ �� �[���񓚏�", 421, 541, 0);
        //  "���H�H�i���������[���񓚏�", 421, 541, 0);
        //����
        cb.setFontAndSize(bf, 13);
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT,
          h.getShiiresaki() + " �䒆", 36, 525, 0);
        cb.setFontAndSize(bf, 11);
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT,
          "TEL:" + h.getShiiretel(), 36, 525 - 12, 0);
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT,
          "FAX:" + h.getShiirefax(), 36, 525 - 12 * 2, 0);
        cb.setFontAndSize(bf, 10);
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT,
          "���x�i�ʂ̂��������Ă����落�ɂ��肪�Ƃ��������܂��B" +
          "���L�̂悤�ɔ����������܂��̂ŁA��낵�����肢�������܂��B",
          36, 525 - 12 * 3, 0);

        cb.setFontAndSize(bf, 13);
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT,
          "*** �K�����ǂ݂������� ***", 36 + 4, 525 - 12 * 4 - 6, 0);

        cb.setFontAndSize(bf, 10);
        OfficeData office = h.getOfficeData();
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT,
          "�E�K�i�E�P���E���ʁE�[�������m�F�̏�A�m�F���ɂ����󒸂��A " + 
          office.getFax() + " ���ɂe�`�w�ɂĂ��ԐM���������B",
          36, 525 - 12 * 6 - 2, 0);
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT,
          "  �i���e�ɈقȂ�_������ꍇ�A�w��[���ł̔[�i���ł��Ȃ��ꍇ�ɂ́A" +
          "���}���d�b�ɂĂ��m�点���������j",
          36, 525 - 12 * 7 - 2, 0);
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT,
          "  * �ʓr�����������������ꍇ�́A�����L�����ɑ������z�i�Ŕ��j" +
          "�����L���������B", 36, 525 - 12 * 8 - 2, 0);
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT,
          "�EFAX�̂��ԐM�������ꍇ�A���������̋��z�̂����[�i���m�F�ł�������" +
          "�ɂ����x���v���܂��B�\�߂������������B", 36, 525 - 11 * 10 - 2, 0);
        //�E��
        cb.setFontAndSize(bf, 12);
        java.util.Date date = DateUtil.getDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy�NM��d��");
        String datepage = sdf.format(date) + " No." + page + "/";
        cb.showTextAligned(PdfContentByte.ALIGN_RIGHT,
          datepage, 806 - 20, 525, 0);
        //cb.showTextAligned(PdfContentByte.ALIGN_RIGHT,
        //  "9999�N99��99�� No." + page, 806, 525, 0);
        cb.showTextAligned(PdfContentByte.ALIGN_RIGHT,
          "�����ԍ� " + h.getHacchuu_bg(), 806, 525-12, 0);
        cb.showTextAligned(PdfContentByte.ALIGN_RIGHT,
          String.valueOf(index), 806, 525-12*2, 0);
        //��
        cb.setFontAndSize(bf, 10);
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT,
          "�I�C�V�b�N�X������� �S�� " + h.getTantousha(), 36, 100 , 0);
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT,
          "��" + office.getYuubin(), 36, 100-12 , 0);
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT,
          office.getAddr(), 36, 100-12*2 , 0);
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT,
          "TEL: " + office.getTel(), 36, 100-12*3 , 0);
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT,
          "FAX: " + office.getFax(), 36, 100-12*4 , 0);
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT,
          "�[�i��:", 300, 100 , 0);
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT,
          h.getNouhinsaki() , 300, 100-12 , 0);
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT,
          "��" + h.getNouhinyuubin(), 300, 100-12*2 , 0);
        String nouhinaddr = h.getNouhinaddr();
        if (nouhinaddr == null) { nouhinaddr = ""; }
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT,
          nouhinaddr, 300, 100-12*3 , 0);
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT,
          "TEL: " + h.getNouhintel(), 300, 100-12*4 , 0);
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT,
          "FAX: " + h.getNouhinfax(), 300, 100-12*5 , 0);
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT,
          "�A������", 842-36-72*3, 100, 0);
        cb.showTextAligned(PdfContentByte.ALIGN_CENTER,
          "�����L����", 806-60+30, 525-12*4-1, 0);
        cb.showTextAligned(PdfContentByte.ALIGN_CENTER,
          "�m�F��", 806-60-60+30, 525-12*4-1, 0);
        cb.endText();
        cb.addTemplate(template, 806 - 20, 525);
        cb.rectangle(842-36-72*3, 36, 72*3,16*4);
        cb.stroke();
        cb.moveTo(36, 525-1);
        cb.lineTo(36+320, 525-1);
        cb.stroke();
        //���ӏ����̈͂�
        cb.setLineWidth(3.0f);
        //cb.setLineDash(3, 3, 0);
        cb.rectangle(36, 525-12*10-6, 600, 12*7+3);
        cb.stroke();
        //�����L����
        cb.setLineWidth(3.0f);
        cb.rectangle(806-60, 525-12*4-3, 60, 12);
        cb.stroke();
        cb.rectangle(806-60, 525-12*8-3, 60, 48);
        cb.stroke();
        //�m�F��
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
            suuryou = suuryou.replaceAll("0", "�O");
            suuryou = suuryou.replaceAll("1", "�P");
            suuryou = suuryou.replaceAll("2", "�Q");
            suuryou = suuryou.replaceAll("3", "�R");
            suuryou = suuryou.replaceAll("4", "�S");
            suuryou = suuryou.replaceAll("5", "�T");
            suuryou = suuryou.replaceAll("6", "�U");
            suuryou = suuryou.replaceAll("7", "�V");
            suuryou = suuryou.replaceAll("8", "�W");
            suuryou = suuryou.replaceAll("9", "�X");
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
              "M��d��");
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
          5,  //���i�R�[�h
          23, //���i��
          10, //�K�i
          5,  //���萔
          5,  //�P��
          8,  //����
          7,  //�P��
          5,  //�o������
          7,  //���z
          9, //�[�i��
          7,  //���ԑ�
          9};//�[���񓚗�
        table.setWidths(width);
        table.setPadding(3);
        table.setAutoFillEmptyCells(true);
        table.setDefaultHorizontalAlignment(Element.ALIGN_LEFT);
        table.setDefaultVerticalAlignment(Element.ALIGN_TOP);
        table.addCell(new Phrase(10, "���i\n�R�[�h", font));
        table.addCell(new Phrase(10, "���i��", font));
        table.addCell(new Phrase(10, "�K�i", font));
        table.addCell(new Phrase(10, "�ꔠ\n���萔", font));
        table.addCell(new Phrase(10, "�P��", font));
        table.addCell(new Phrase(10, "����", font));
        table.addCell(new Phrase(10, "�P��", font));
        table.addCell(new Phrase(10, "�o��\n����", font));
        table.addCell(new Phrase(10, "���z", font));
        table.addCell(new Phrase(10, "�[�i��", font));
        table.addCell(new Phrase(10, "���ԑ�", font));
        table.addCell(new Phrase(10, "�[���񓚗�", font));
        return table;
    }
}
