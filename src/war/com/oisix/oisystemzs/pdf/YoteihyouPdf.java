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
        SimpleDateFormat ymd = new SimpleDateFormat("yyyy年M月d日");
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
        //発注番号 発注区分
        table.addCell(new Phrase(12, hacchuu_bgkubun, sfont));
        //仕入先
        String shiiresaki = (String)item.get("shiiresakimei");
        if(shiiresaki.length()>12){
            shiiresaki = shiiresaki.substring(0,11);
        }
        table.addCell(new Phrase(12, shiiresaki, sfont));
        //商品コード
        String shouhin_id = (String)item.get("shouhin_id");
        String location_id1 = (String)item.get("location_id1");
        if (location_id1 != null) {
            shouhin_id = shouhin_id + "\n" + location_id1;
        }
        table.addCell(new Phrase(12, shouhin_id, font));
        //商品名 規格
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
        //入荷予定数量（バラ数）
        float nyuukasuuryou = ((Float)item.get("nyuukasuuryou")).floatValue();
        float jitsunyuukasuu = ((Float)item.get("jitsunyuukasuu")).floatValue();
        float nyuukayoteisuu = nyuukasuuryou - jitsunyuukasuu;
        int intnyuukayoteisuu = (int)nyuukayoteisuu;
        String nyuukayoteisuustr = Integer.toString(intnyuukayoteisuu);
        //入荷予定数（ケース数）バラ数/入り数→入荷予定の発注数
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
        //入り数 入荷予定数/ケース数==入り数の時表示
        if(nyuukayoteisuu/nyuukayoteics==flirisuu){
            //table.addCell(new Phrase(12, irisuu, lfont));
            cell = new Cell(new Phrase(12, irisuu, lfont));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(cell);
        }else{
            table.addCell(new Phrase(12, (""), lfont));
        }
        //入荷予定数
        //table.addCell(new Phrase(12, nyuukayoteisuustr, lfont));
        cell = new Cell(new Phrase(12, nyuukayoteisuustr, lfont));
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(cell);
        //入荷数
        table.addCell(new Phrase(12, (""), font));
        //入荷予定日
        SimpleDateFormat sdfymd = new SimpleDateFormat("yy/M/d");
        String nyuukayoteifm = sdfymd.format(nyuukayotei_date);
        
        table.addCell(new Phrase(12, nyuukayoteifm, sfont));
        //時間帯
        String touchakujikan = (String)item.get("touchakujikan");
        table.addCell(new Phrase(12, touchakujikan, sfont));
        //賞味期限
        int shoumikigenflg = ((Integer)item.get("shoumikigenflg")).intValue();
        if(shoumikigenflg==1&&!shoumikigen.equals("")){
            String shoumikigenstr = sdfymd.format(shoumikigen);
            table.addCell(new Phrase(12, shoumikigenstr, sfont));
        }else{
            table.addCell(new Phrase(12, (""), sfont));
        }
        //入荷期限 出荷期限管理が必要な場合と必要ない場合
        if(shukkakigennissuu != 0){
            nyuukakigen.add(nyuukakigen.DATE,shukkakigennissuu);
            int yearnyuukakigen = nyuukakigen.get(nyuukakigen.YEAR);
            int monthnyuukakigen = nyuukakigen.get(nyuukakigen.MONTH) +1 ;
            int datenyuukakigen = nyuukakigen.get(nyuukakigen.DATE);
            Calendar calnyuukakigen = Calendar.getInstance();
            calnyuukakigen.set(yearnyuukakigen,monthnyuukakigen-1,datenyuukakigen);
            java.util.Date nyuukadate = calnyuukakigen.getTime();
            //入荷期限と賞味期限を比較
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
        //出荷期限日数
        Integer shoumikigennissuu = ((Integer)item.get("shoumikigennissuu"));
        int shoumikigenint = shoumikigennissuu.intValue();
        int shukkanissuu = shoumikigenint - shukkakigennissuu;
        String shukkanissuustr = Integer.toString(shukkanissuu);
        table.addCell(new Phrase(12, "  "+shukkanissuustr, font));
        //出荷期限
        java.util.Date shukkakigen = (java.util.Date)item.get("shukkakigen");
        if(shukkakigennissuu != 0 && shoumikigenflg==1){
            String shukkakigenfm = sdfymd.format(shukkakigen);
            table.addCell(new Phrase(12, shukkakigenfm, sfont));
        }else{
            table.addCell(new Phrase(12, (""), sfont));
        }
        //温度帯 ラベル
        String ondotai = (String)item.get("ondotai");
        String youraberu_flg = null; 
        Integer youraberu = (Integer)item.get("youraberu_flg");
        int intyouraberu_flg = youraberu.intValue();
        if(youraberu != null){
            if(intyouraberu_flg == Names.ON){
                  youraberu_flg = "要";
            }else{
                  youraberu_flg = "不要";
            }
        }
        String ondoraberu = ondotai+"\n"+youraberu_flg;
        table.addCell(new Phrase(12, ondoraberu, font));
        //引当数
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
        //JANコード->削除
        //table.addCell(new Phrase(12, (""), font));
        //備考
        table.addCell(new Phrase(12, (""), font));
    }
    
    protected void makeTemplate(BaseFont bf, int page, PdfTemplate template) {
        cb.beginText();
        // ヘッダー
        cb.setFontAndSize(bf, 10);
        //yoteihyoudate = yoteihyoudate.substring(2);
        //String yoteihyoudatestr = "入荷予定日:"+yoteihyoudate+"日";
        //yoteihyoudate = yoteihyoudate.substring(2);
        String yoteihyoudatestr = "入荷予定日:"+yoteihyoudate.substring(2)+"日";
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT,
          yoteihyoudatestr, 430, 561, 0);
        cb.setFontAndSize(bf, 12);
        cb.showTextAligned(PdfContentByte.ALIGN_CENTER,
          "入荷予定表", 370, 560, 0);
        cb.showTextAligned(PdfContentByte.ALIGN_CENTER,
          "担当者", 680, 561, 0);
        cb.showTextAligned(PdfContentByte.ALIGN_CENTER,
          "入力者", 724, 561, 0);
        cb.showTextAligned(PdfContentByte.ALIGN_CENTER,
          "管理者", 768, 561, 0);
        SimpleDateFormat ymdhms = new SimpleDateFormat("yy年MM月dd日 H:m:s");
        cb.setFontAndSize(bf, 10);
        String datepage = "出力日時:"+ymdhms.format(makedate);
        datepage += "　";
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
          7, // 発注番号、区分
          7, // 仕入先
          5, // 商品コード
          21, // 商品名 規格 荷姿
          5, // ケース数
          4, // 入り数
          5, // 入荷予定数
          4, // 入荷数
          4, // 入荷予定日
          4, // 時間帯
          4, // 賞味期限
          4, // 入荷期限
          3, // 出荷期限日数
          4, // 出荷期限
          4, // 温度帯 ラベル
          3, // 引当数
          6}; // 備考
        table.setWidths(width);
        table.setPadding(1);
        table.setAutoFillEmptyCells(true);
        table.setDefaultHorizontalAlignment(Element.ALIGN_LEFT);
        table.setDefaultVerticalAlignment(Element.ALIGN_TOP);
        if (daibunrui == null) { daibunrui = ""; }
        Cell cell = new Cell(new Phrase(12, daibunrui, lfont));
        cell.setColspan(17);
        table.addCell(cell);
        table.addCell(new Phrase(12, "発注番号\n発注区分", font));
        table.addCell(new Phrase(12, "仕入先", font));
        table.addCell(new Phrase(12, "商品\nコード\nロケ", font));
        table.addCell(new Phrase(12, "商品名\n\n規格 : 荷姿", font));
        table.addCell(new Phrase(12, "ケース数", font));
        table.addCell(new Phrase(12, "入り数", font));
        table.addCell(new Phrase(12, "入荷\n予定数", font));
        table.addCell(new Phrase(12, "入荷数", font));
        table.addCell(new Phrase(12, "入荷\n予定日", font));
        table.addCell(new Phrase(12, "時間帯", font));
        table.addCell(new Phrase(12, "賞味期限", font));
        table.addCell(new Phrase(12, "入荷期限", font));
        table.addCell(new Phrase(12, "出荷\n期限\n日数", font));
        table.addCell(new Phrase(12, "出荷期限", font));
        table.addCell(new Phrase(12, "温度帯\n\nラベル", font));
        table.addCell(new Phrase(12, "引当数", font));
        //table.addCell(new Phrase(12, "JAN\nコード", font));
        table.addCell(new Phrase(12, "備考", font));
        return table;
    }
}
