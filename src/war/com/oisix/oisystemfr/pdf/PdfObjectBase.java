package com.oisix.oisystemfr.pdf;

import com.oisix.oisystemfr.KeyGeneratorUtil;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.PdfContentByte;
import java.io.IOException;
import java.io.FileOutputStream;

public abstract class PdfObjectBase {

    private String fileName;
    private String docTypeName;
    private static String pdfBasePath = "../file/pdf/";
    protected Document document;
    protected PdfWriter writer;
    protected PdfContentByte cb;

    public static void setPdfBasePath(String path) {
        pdfBasePath = path;
    }

    public static String getPdfBasePath() {
        return pdfBasePath;
    }

    public String getFileName() { return fileName; }

    public void makeDocument() throws IOException, DocumentException {
        init();
        doMakeDocument();
        finalize();
    }

    protected void init() throws IOException, DocumentException {
        document = getDocument();
        int id = KeyGeneratorUtil.getNext("ZS_PRINT");
        docTypeName = getDocTypeName();
        fileName = docTypeName + "-" + id + ".pdf";
        writer = PdfWriter.getInstance(document, new FileOutputStream(
          pdfBasePath + fileName));
        document.open();
        cb = writer.getDirectContent();
    }

    protected abstract String getDocTypeName();
    protected abstract Document getDocument();

    protected abstract void doMakeDocument()
      throws IOException, DocumentException;
    protected void finalize() {
        document.close();
    }
}
