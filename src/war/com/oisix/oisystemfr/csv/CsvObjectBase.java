package com.oisix.oisystemfr.csv;

import com.oisix.oisystemfr.KeyGeneratorUtil;
import java.io.IOException;

public abstract class CsvObjectBase {

    private String fileName;
    protected String fileTypeName;
    private static String csvBasePath = "../file/csv/";
    protected CsvWrite csvwrite;

    public static void setCsvBasePath(String path) {
        csvBasePath = path;
    }

    public static String getCsvBasePath() {
        return csvBasePath;
    }

    public String getFileName() { return fileName; }

    public void make() throws IOException {
        init();
        doMake();
        finalize();
    }

    protected void init() throws IOException {
        int id = KeyGeneratorUtil.getNext("ZS_PRINT");
        fileTypeName = getFileTypeName();
        fileName = fileTypeName + "-" + id + ".csv";
        csvwrite = new CsvWrite();
        csvwrite.setLineseparator("\r\n");
        csvwrite.csvFileOpen(csvBasePath + fileName);
    }

    protected abstract String getFileTypeName();
    protected abstract void doMake() throws IOException;
    protected void finalize() throws IOException {
        csvwrite.csvFileClose();
    }
}
