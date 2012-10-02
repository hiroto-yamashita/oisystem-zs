package com.oisix.oisystemzs.pdf;

import com.oisix.oisystemfr.pdf.PdfObjectBase;
import com.oisix.oisystemzs.Names;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Cell;
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
import java.util.Iterator;
import java.util.ListIterator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.text.DecimalFormat;

public class YoteihyouPdf extends PdfObjectBase {

    private java.util.Date makedate;
    private Calendar nyuukakigen;
    private LinkedList nyuukaList;
    private LinkedList sysuuryouList;
    private String yoteihyoudate;

    public void setNyuukaList(LinkedList nyuukaList) {
        this.nyuukaList = nyuukaList;
    }

    public void setSysuuryouList(LinkedList sysuuryouList) {
        this.sysuuryouList = sysuuryouList;
    }
    
    public void setYoteihyouDate(String yoteihyoudate){
        this.yoteihyoudate = yoteihyoudate;
    }
    
    protected String getDocTypeName() { return "yoteihyou"; }
    protected Document getDocument() {
        return new Document(PageSize.A4.rotate(), 10, 10,
          70, 15);
    }

    protected void doMakeDocument() throws IOException, DocumentException {
        Calendar cal = Calendar.getInstance();
        makedate = cal.getTime();
        
        BaseFont bf = BaseFont.createFont(
          "HeiseiKakuGo-W5", "UniJIS-UCS2-HW-H",false);
        Font font = new Font(bf, 10);
        Font sfont = new Font(bf, 8);
        Font lfont = new Font(bf, 12);
        PdfTemplate template = cb.createTemplate(50, 50);
        int page = 1;
        HashMap firstitem = (HashMap)nyuukaList.getFirst();
        String daibunrui = (String)firstitem.get("daibunrui");
        Iterator iter = nyuukaList.iterator();
        ListIterator syiter = sysuuryouList.listIterator();
        makeTemplate(bf, page, template);
        Table table = getTable(sfont, daibunrui);
        int next_zaiko_id = 0;

        String predaibunrui = daibunrui;
        while (iter.hasNext()) {
            HashMap item = (HashMap)iter.next();
            daibunrui = (String)item.get("daibunrui");
            if ((daibunrui != null) && (!daibunrui.equals(predaibunrui))) {
                predaibunrui = daibunrui;
                document.add(table);
                document.newPage();
                page++;
                makeTemplate(bf, page, template);
                table = getTable(sfont, daibunrui);
            }
            addNewRow(table,font,sfont,lfont,item,syiter);

            if (!writer.fitsPage(table)) {
                table.deleteLastRow();
                document.add(table);
                document.newPage();
                page++;
                makeTemplate(bf, page, template);
                table = getTable(sfont, daibunrui);
                HashMap prehm = (HashMap)syiter.previous();
                addNewRow(table, font, sfont, lfont, item, syiter);
            }
        }
        document.add(table);
        template.beginText();
        template.setFontAndSize(bf, 10);
        template.showText(String.valueOf(page));
        template.endText();
    }
    
    protected void addNewRow(Table table, Font font, Font sfont, Font lfont,
      HashMap item,Iterator syiter) throws BadElementException {
        DecimalFormat df = new DecimalFormat("###,###,###.#");
        Cell cell = null;
        nyuukakigen = Calendar.getInstance();
        SimpleDateFormat ymd = new SimpleDateFormat("yyyy�NM��d��");
        int shukkakigennissuu = ((Integer)item.get("shukkakigennissuu")).intValue();
        java.util.Date nyuukayotei_date = (java.util.Date)item.get("nyuukayotei_date");
        java.util.Date shoumikigen = (java.util.Date)item.get("shoumikigen");
        String shukkakigenstr = "--------";
        String nyuukayotei = "--------";
        if (nyuukayotei_date != null) {
            String nyuukayoteistr = ymd.format(nyuukayotei_date);
            String nyuukayoteiyear = nyuukayoteistr.substring(0,4);
            String nyuukayoteidate = nyuukayoteistr.substring(5);
            nyuukayotei = nyuukayoteiyear+"\n"+nyuukayoteidate;
        }
        String hacchuu_bg =(String)item.get("hacchuu_bg");
        String hacchuukubun =(String)item.get("hacchuukubun");
        String hacchuu_bgkubun = hacchuu_bg+"\n"+hacchuukubun;
        //�����ԍ� �����敪
        table.addCell(new Phrase(12, hacchuu_bgkubun, sfont));
        //�d����
        String shiiresaki = (String)item.get("shiiresakimei");
        if(shiiresaki.length()>12){
            shiiresaki = shiiresaki.substring(0,11);
        }
        table.addCell(new Phrase(12, shiiresaki, sfont));
        //���i�R�[�h
        String shouhin_id = (String)item.get("shouhin_id");
        String location_id1 = (String)item.get("location_id1");
        if (location_id1 != null) {
            shouhin_id = shouhin_id + "\n" + location_id1;
        }
        table.addCell(new Phrase(12, shouhin_id, font));
        //���i�� �K�i
        String shouhin = (String)item.get("shouhin");
        String kikaku = (String)item.get("kikaku");
        float flirisuu = ((Float)item.get("irisuu")).floatValue();
        int intirisuu = (int)flirisuu;
        String irisuu = Integer.toString(intirisuu);
        String shouhinkikaku = shouhin+"\n"+kikaku; 
        String nisugata = (String)item.get("nisugata");
        if (nisugata != null) {
            shouhinkikaku += " : " + nisugata;
        }
        table.addCell(new Phrase(12, shouhinkikaku, font));
        //���ח\�萔�ʁi�o�����j
        float nyuukasuuryou = ((Float)item.get("nyuukasuuryou")).floatValue();
        float jitsunyuukasuu = ((Float)item.get("jitsunyuukasuu")).floatValue();
        float nyuukayoteisuu = nyuukasuuryou - jitsunyuukasuu;
        int intnyuukayoteisuu = (int)nyuukayoteisuu;
        String nyuukayoteisuustr = Integer.toString(intnyuukayoteisuu);
        //���ח\�萔�i�P�[�X���j�o����/���萔�����ח\��̔�����
        //float nyuukayoteics;
        //if(nyuukayoteisuu!=0&&flirisuu!=0){
        //   nyuukayoteics = nyuukayoteisuu/flirisuu;
        //}else{
        //    nyuukayoteics = 0;
        //}
        //String nyuukayoteicsstr = Float.toString(nyuukayoteics);
        //if(nyuukayoteicsstr.length()>6){
        //    nyuukayoteicsstr = nyuukayoteicsstr.substring(0,5);
        //}
        float nyuukayoteics = ((Float)item.get("hacchuusuuryou")).floatValue();
        //String nyuukayoteicsstr = Float.toString(nyuukayoteics);
        String nyuukayoteicsstr = df.format(nyuukayoteics);
        //table.addCell(new Phrase(12, nyuukayoteicsstr, lfont));
        cell = new Cell(new Phrase(12, nyuukayoteicsstr, lfont));
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(cell);
        //���萔 ���ח\�萔/�P�[�X��==���萔�̎��\��
        if(nyuukayoteisuu/nyuukayoteics==flirisuu){
            //table.addCell(new Phrase(12, irisuu, lfont));
            cell = new Cell(new Phrase(12, irisuu, lfont));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(cell);
        }else{
            table.addCell(new Phrase(12, (""), lfont));
        }
        //���ח\�萔
        //table.addCell(new Phrase(12, nyuukayoteisuustr, lfont));
        cell = new Cell(new Phrase(12, nyuukayoteisuustr, lfont));
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(cell);
        //���א�
        table.addCell(new Phrase(12, (""), font));
        //���ח\���
        SimpleDateFormat sdfymd = new SimpleDateFormat("yy/M/d");
        String nyuukayoteifm = sdfymd.format(nyuukayotei_date);
        
        table.addCell(new Phrase(12, nyuukayoteifm, sfont));
        //���ԑ�
        String touchakujikan = (String)item.get("touchakujikan");
        table.addCell(new Phrase(12, touchakujikan, sfont));
        //�ܖ�����
        int shoumikigenflg = ((Integer)item.get("shoumikigenflg")).intValue();
        if(shoumikigenflg==1&&!shoumikigen.equals("")){
            String shoumikigenstr = sdfymd.format(shoumikigen);
            table.addCell(new Phrase(12, shoumikigenstr, sfont));
        }else{
            table.addCell(new Phrase(12, (""), sfont));
        }
        //���׊��� �o�׊����Ǘ����K�v�ȏꍇ�ƕK�v�Ȃ��ꍇ
        if(shukkakigennissuu != 0){
            nyuukakigen.add(nyuukakigen.DATE,shukkakigennissuu);
            int yearnyuukakigen = nyuukakigen.get(nyuukakigen.YEAR);
            int monthnyuukakigen = nyuukakigen.get(nyuukakigen.MONTH) +1 ;
            int datenyuukakigen = nyuukakigen.get(nyuukakigen.DATE);
            Calendar calnyuukakigen = Calendar.getInstance();
            calnyuukakigen.set(yearnyuukakigen,monthnyuukakigen-1,datenyuukakigen);
            java.util.Date nyuukadate = calnyuukakigen.getTime();
            //���׊����Əܖ��������r
            //if(nyuukadate.compareTo(shoumikigen)<0){
            //}
            calnyuukakigen.clear();
            String stryear = Integer.toString(yearnyuukakigen);
            String strmonth = Integer.toString(monthnyuukakigen);
            String strdate = Integer.toString(datenyuukakigen);
            String nen = "/";
            String tsuki = "/";
            stryear = stryear.substring(2,4);
            String strnyuukakigen = stryear+nen+strmonth+tsuki+strdate;
            table.addCell(new Phrase(12, strnyuukakigen, sfont));
            nyuukakigen.clear();
        }else{
            table.addCell(new Phrase(12, "", sfont));
        }
        //�o�׊�������
        Integer shoumikigennissuu = ((Integer)item.get("shoumikigennissuu"));
        int shoumikigenint = shoumikigennissuu.intValue();
        int shukkanissuu = shoumikigenint - shukkakigennissuu;
        String shukkanissuustr = Integer.toString(shukkanissuu);
        table.addCell(new Phrase(12, "  "+shukkanissuustr, font));
        //�o�׊���
        java.util.Date shukkakigen = (java.util.Date)item.get("shukkakigen");
        if(shukkakigennissuu != 0 && shoumikigenflg==1){
            String shukkakigenfm = sdfymd.format(shukkakigen);
            table.addCell(new Phrase(12, shukkakigenfm, sfont));
        }else{
            table.addCell(new Phrase(12, (""), sfont));
        }
        //���x�� ���x��
        String ondotai = (String)item.get("ondotai");
        String youraberu_flg = null; 
        Integer youraberu = (Integer)item.get("youraberu_flg");
        int intyouraberu_flg = youraberu.intValue();
        if(youraberu != null){
            if(intyouraberu_flg == Names.ON){
                  youraberu_flg = "�v";
            }else{
                  youraberu_flg = "�s�v";
            }
        }
        String ondoraberu = ondotai+"\n"+youraberu_flg;
        table.addCell(new Phrase(12, ondoraberu, font));
        //������
        float hikiatesuu = 0;
        if(syiter.hasNext()){
            HashMap sysuuryou = (HashMap)syiter.next();
            hikiatesuu = ((Float)sysuuryou.get("hikiatesuu")).floatValue();
        }
        if(hikiatesuu<0){
            hikiatesuu = -1*hikiatesuu;
            //String strhikiatesuu = Float.toString(hikiatesuu);
            String strhikiatesuu = df.format(hikiatesuu);
            cell = new Cell(new Phrase(12, strhikiatesuu, lfont));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            //table.addCell(new Phrase(12, strhikiatesuu, font));
            table.addCell(cell);
        }else{
            table.addCell(new Phrase(12, (""), font));
        }
        //JAN�R�[�h->�폜
        //table.addCell(new Phrase(12, (""), font));
        //���l
        table.addCell(new Phrase(12, (""), font));
    }
    
    protected void makeTemplate(BaseFont bf, int page, PdfTemplate template) {
        cb.beginText();
        // �w�b�_�[
        cb.setFontAndSize(bf, 10);
        //yoteihyoudate = yoteihyoudate.substring(2);
        //String yoteihyoudatestr = "���ח\���:"+yoteihyoudate+"��";
        //yoteihyoudate = yoteihyoudate.substring(2);
        String yoteihyoudatestr = "���ח\���:"+yoteihyoudate.substring(2)+"��";
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT,
          yoteihyoudatestr, 430, 561, 0);
        cb.setFontAndSize(bf, 12);
        cb.showTextAligned(PdfContentByte.ALIGN_CENTER,
          "���ח\��\", 370, 560, 0);
        cb.showTextAligned(PdfContentByte.ALIGN_CENTER,
          "�S����", 680, 561, 0);
        cb.showTextAligned(PdfContentByte.ALIGN_CENTER,
          "���͎�", 724, 561, 0);
        cb.showTextAligned(PdfContentByte.ALIGN_CENTER,
          "�Ǘ���", 768, 561, 0);
        SimpleDateFormat ymdhms = new SimpleDateFormat("yy�NMM��dd�� H:m:s");
        cb.setFontAndSize(bf, 10);
        String datepage = "�o�͓���:"+ymdhms.format(makedate);
        datepage += "�@";
        datepage += "page:" + page + "/" ;
        cb.showTextAligned(PdfContentByte.ALIGN_CENTER,
          datepage, 535, 545, 0);
        cb.endText();
        
        float len = bf.getWidthPoint(datepage, 12);
        float x = 750;
        float y = 559;
        float width = 44;
        float height = 14;
        cb.addTemplate(template, 517 + len / 2, 545);
        cb.rectangle(x,y,width,height);
        cb.rectangle(x,y-height,width,height);
        cb.rectangle(x-width,y,width,height);
        cb.rectangle(x-width,y-height,width,height);
        cb.rectangle(x-width*2,y,width,height);
        cb.rectangle(x-width*2,y-height,width,height);
        cb.stroke();
    }

    private Table getTable(Font font, String daibunrui)
      throws DocumentException, BadElementException, IOException {
        BaseFont bf = BaseFont.createFont(
          "HeiseiKakuGo-W5", "UniJIS-UCS2-HW-H",false);
        Font lfont = new Font(bf, 12);
        Table table = new Table(17);
        table.setWidth(100);
        //int width[] = {8, 7, 4, 23, 5, 5, 5, 4, 4, 4, 4, 4, 4, 4, 4, 3, 2};
        int width[] = {
          7, // �����ԍ��A�敪
          7, // �d����
          5, // ���i�R�[�h
          21, // ���i�� �K�i �׎p
          5, // �P�[�X��
          4, // ���萔
          5, // ���ח\�萔
          4, // ���א�
          4, // ���ח\���
          4, // ���ԑ�
          4, // �ܖ�����
          4, // ���׊���
          3, // �o�׊�������
          4, // �o�׊���
          4, // ���x�� ���x��
          3, // ������
          6}; // ���l
        table.setWidths(width);
        table.setPadding(1);
        table.setAutoFillEmptyCells(true);
        table.setDefaultHorizontalAlignment(Element.ALIGN_LEFT);
        table.setDefaultVerticalAlignment(Element.ALIGN_TOP);
        if (daibunrui == null) { daibunrui = ""; }
        Cell cell = new Cell(new Phrase(12, daibunrui, lfont));
        cell.setColspan(17);
        table.addCell(cell);
        table.addCell(new Phrase(12, "�����ԍ�\n�����敪", font));
        table.addCell(new Phrase(12, "�d����", font));
        table.addCell(new Phrase(12, "���i\n�R�[�h\n���P", font));
        table.addCell(new Phrase(12, "���i��\n\n�K�i : �׎p", font));
        table.addCell(new Phrase(12, "�P�[�X��", font));
        table.addCell(new Phrase(12, "���萔", font));
        table.addCell(new Phrase(12, "����\n�\�萔", font));
        table.addCell(new Phrase(12, "���א�", font));
        table.addCell(new Phrase(12, "����\n�\���", font));
        table.addCell(new Phrase(12, "���ԑ�", font));
        table.addCell(new Phrase(12, "�ܖ�����", font));
        table.addCell(new Phrase(12, "���׊���", font));
        table.addCell(new Phrase(12, "�o��\n����\n����", font));
        table.addCell(new Phrase(12, "�o�׊���", font));
        table.addCell(new Phrase(12, "���x��\n\n���x��", font));
        table.addCell(new Phrase(12, "������", font));
        //table.addCell(new Phrase(12, "JAN\n�R�[�h", font));
        table.addCell(new Phrase(12, "���l", font));
        return table;
    }
}
