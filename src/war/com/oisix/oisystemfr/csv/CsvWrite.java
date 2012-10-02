/*
 * CsvWrite.java
 */
package com.oisix.oisystemfr.csv;

import java.io.*;
import java.util.*;

/**
 * 実際にCSVファイルを書き込むクラス。
 * <pre>
 * 1.0  2002/02/25  初版
 * </pre>
 * @author MQS)ide
 * @version 1.0
 */
public class CsvWrite {
    private FileWriter fw;
    private PrintWriter prnWriter;
    private String lineseparator;

    /**
     * 空のコンストラクタ
     */
    public CsvWrite() {
    }

    /**
     * FileWriterを指定して構築。
     * @param fw FileWriterオブジェクト
     */
    public CsvWrite(FileWriter fw) {
        this.fw = fw;
    }

    /**
     * 改行文字列を指定する。
     * @param ls 改行文字列
     */
    public void setLineseparator(String ls) {
        this.lineseparator = ls;
    }

    /**
     * オブジェクト(FileWrite)を取り出す（直接使用を禁止）。
     * @return FileWriterオブジェクト
     */
    public FileWriter getFileWriter(){
        return(fw);
    }

    /**
     * オブジェクト(FileWrite)に値を格納する（直接使用を禁止）。
     * @param fw FileWriterオブジェクト
     */
    public void setFileWriter(FileWriter fw){
        this.fw = fw;
    }

    /**
     * CSVファイルのオープンを行う。
     * @param fileName ファイル名
     * @exception IOException IOエラー
     */
    public void csvFileOpen(String fileName) throws IOException{
        if(fw == null) {
            prnWriter = new PrintWriter(
              new BufferedWriter(
                new OutputStreamWriter(
                  new FileOutputStream(fileName), "Shift_JIS")
              )
            );
        }
        else {
            prnWriter = new PrintWriter(new BufferedWriter(fw));
        }
    }

    /**
     * CSVファイルのクローズを行う。
     * @exception IOException IOエラー
     */
    public void csvFileClose() throws IOException{
        prnWriter.flush();
        prnWriter.close();
    }

    /**
     * CSVファイルへ文字列の書き込みを行う。
     * @param s 文字列
     * @exception IOException IOエラー
     */
    public void setcsvRecord(String s) throws IOException{
        if (lineseparator == null) {
            prnWriter.println( s );
        } else {
            prnWriter.print(s);
            prnWriter.print(lineseparator);
        }
    }

    /**
     * CSVファイルへ配列(Vectorオブジェクト)の書き込みを行う。<br>
     * CsvLineクラスで配列を展開して書き込みを行う。
     * @param v 配列
     * @exception IOException IOエラー
     */
    public void setcsvRecord(Vector v) throws IOException{
        CsvLine csvLine = new CsvLine(v);
        setcsvRecord( csvLine.getLine() );
    }
}
