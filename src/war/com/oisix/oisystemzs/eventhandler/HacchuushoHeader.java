package com.oisix.oisystemzs.eventhandler;

import com.oisix.oisystemzs.ejb.OfficeData;
import com.oisix.oisystemzs.ejb.SoukoData;
import java.util.TreeMap;

public class HacchuushoHeader {
    private String hacchuu_bg;
    private String bikou;
    private String shiiresaki;
    private String shiiretel;
    private String shiirefax;
    private String nouhinsaki;
    private String nouhinyuubin;
    private String nouhinaddr;
    private String nouhintel;
    private String nouhinfax;
    private int format;
    private String tantousha;
    private TreeMap meisai;
    private OfficeData office;
    private SoukoData souko;

    public void setHacchuu_bg(String hacchuu_bg) {
        this.hacchuu_bg = hacchuu_bg;
    }
    public String getHacchuu_bg() { return hacchuu_bg; }
    public void setBikou(String bikou) {
        this.bikou = bikou;
    }
    public String getBikou() { return bikou; }
    public void setShiiresaki(String shiiresaki) {
        this.shiiresaki = shiiresaki;
    }
    public String getShiiresaki() { return shiiresaki; }
    public void setShiiretel(String shiiretel) {
        this.shiiretel = shiiretel;
    }
    public String getShiiretel() { return shiiretel; }
    public void setShiirefax(String shiirefax) {
        this.shiirefax = shiirefax;
    }
    public String getShiirefax() { return shiirefax; }
    public void setNouhinsaki(String nouhinsaki) {
        this.nouhinsaki = nouhinsaki;
    }
    public String getNouhinsaki() { return nouhinsaki; }
    public void setNouhinaddr(String nouhinaddr) {
        this.nouhinaddr = nouhinaddr;
    }
    public String getNouhinyuubin() { return nouhinyuubin; }
    public void setNouhinyuubin(String nouhinyuubin) {
        this.nouhinyuubin = nouhinyuubin;
    }
    public String getNouhinaddr() { return nouhinaddr; }
    public void setNouhintel(String nouhintel) {
        this.nouhintel = nouhintel;
    }
    public String getNouhintel() { return nouhintel; }
    public void setNouhinfax(String nouhinfax) {
        this.nouhinfax = nouhinfax;
    }
    public String getNouhinfax() { return nouhinfax; }
    public void setFormat(int format) {
        this.format = format;
    }
    public int getFormat() { return format; }
    public void setTantousha(String tantousha) {
        this.tantousha = tantousha;
    }
    public String getTantousha() { return tantousha; }

    public void setMeisai(TreeMap meisai) {
        this.meisai = meisai;
    }
    public TreeMap getMeisai() { return meisai; }

    public void setOfficeData(OfficeData office) { this.office =  office; }
    public OfficeData getOfficeData() { return office; }
    public void setSoukoData(SoukoData souko) { this.souko =  souko; }
    public SoukoData getSoukoData() { return souko; }
}

