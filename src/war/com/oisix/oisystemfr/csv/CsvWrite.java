/*
 * CsvWrite.java
 */
package com.oisix.oisystemfr.csv;

import java.io.*;
import java.util.*;

/**
 * ���ۂ�CSV�t�@�C�����������ރN���X�B
 * <pre>
 * 1.0  2002/02/25  ����
 * </pre>
 * @author MQS)ide
 * @version 1.0
 */
public class CsvWrite {
    private FileWriter fw;
    private PrintWriter prnWriter;
    private String lineseparator;

    /**
     * ��̃R���X�g���N�^
     */
    public CsvWrite() {
    }

    /**
     * FileWriter���w�肵�č\�z�B
     * @param fw FileWriter�I�u�W�F�N�g
     */
    public CsvWrite(FileWriter fw) {
        this.fw = fw;
    }

    /**
     * ���s��������w�肷��B
     * @param ls ���s������
     */
    public void setLineseparator(String ls) {
        this.lineseparator = ls;
    }

    /**
     * �I�u�W�F�N�g(FileWrite)�����o���i���ڎg�p���֎~�j�B
     * @return FileWriter�I�u�W�F�N�g
     */
    public FileWriter getFileWriter(){
        return(fw);
    }

    /**
     * �I�u�W�F�N�g(FileWrite)�ɒl���i�[����i���ڎg�p���֎~�j�B
     * @param fw FileWriter�I�u�W�F�N�g
     */
    public void setFileWriter(FileWriter fw){
        this.fw = fw;
    }

    /**
     * CSV�t�@�C���̃I�[�v�����s���B
     * @param fileName �t�@�C����
     * @exception IOException IO�G���[
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
     * CSV�t�@�C���̃N���[�Y���s���B
     * @exception IOException IO�G���[
     */
    public void csvFileClose() throws IOException{
        prnWriter.flush();
        prnWriter.close();
    }

    /**
     * CSV�t�@�C���֕�����̏������݂��s���B
     * @param s ������
     * @exception IOException IO�G���[
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
     * CSV�t�@�C���֔z��(Vector�I�u�W�F�N�g)�̏������݂��s���B<br>
     * CsvLine�N���X�Ŕz���W�J���ď������݂��s���B
     * @param v �z��
     * @exception IOException IO�G���[
     */
    public void setcsvRecord(Vector v) throws IOException{
        CsvLine csvLine = new CsvLine(v);
        setcsvRecord( csvLine.getLine() );
    }
}
