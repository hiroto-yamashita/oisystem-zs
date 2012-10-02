/**
 * @author	山下 寛人
 * @version	%I%, %G%
 * Copyright 2001 Oisix Co,Ltd.
 */
package com.oisix.oisystemfr;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

/**
 * XMLファイルからMap形式のデータを生成します。
 * XMLファイルが更新されると自動的に内容が更新されます。
 * XMLファイルはoisix.property.rootシステムプロパティのパスに配置します。
 * XMLファイルは、<br>&lt;ファイル名(拡張子なし)&gt;&lt;マップ名&gt;
 * &lt;prop key="key"&gt;value&lt/.. <br>の形式で記述します。
 * <br>
 * staticのgetメソッドで拡張子なしのファイル名を指定し、新規のインスタンスを
 * 取得します。そのインスタンスに対し、getMapメソッドでHashMapを取得します。
 */
public class XMLMapFactory {
    private static final String propRootProp = "oisix.property.root";
    private static final String suffix = ".xml";
    private static String root;
    private static final String propTag = "prop";
    private static final String keyAttr = "key";
    private static HashMap factories = new HashMap();

    private String filename;
    private String fullfilename;
    private HashMap maps = null;
    private long cachedTime = 0;

    static {
        root = System.getProperty(propRootProp);
    }

    private XMLMapFactory() {}

    private XMLMapFactory(String fname) {
        this.filename = fname;

        if ((null != root) && (root.length() > 0) &&
            (filename.indexOf("/") != 0) &&
            (filename.indexOf("\\") != 0) &&
            (filename.indexOf(":") != 1)) {
            char c = root.charAt(root.length() - 1);
            if ((c != '/') && ( c != '\\')) root = root + "/";
            fullfilename = root + filename + suffix;
        }
    }

    /**
     * 指定したタイプ(ファイル名)のXMLMapFactoryインスタンスを返します。
     */
    public static synchronized XMLMapFactory get(String filename) {
        XMLMapFactory factory = null;
        try {
            factory = (XMLMapFactory)factories.get(filename);
        } catch (Exception e) {}

        if (factory == null) {
            factory = new XMLMapFactory(filename);
            factories.put(filename, factory);
        }
        return factory;
    }

    /**
     * XMLMapFactoryから指定したHashMapを返します。
     * XMLファイルが更新されていた場合は再読み込みします。
     * @exception	SAXParseException	XMLのフォーマットが不適切な場合
     * @exception	SAXException	XMLのフォーマットが不適切な場合
     * @exception	IOException	XMLファイルの読み込み時の例外
     */
    public HashMap getMap(String mapname) throws SAXException,IOException {
        if (mapname == null) { return null; }

        checkUpdate();
        HashMap map = (HashMap)maps.get(mapname);

        return (HashMap)map.clone();
    }

    private synchronized void checkUpdate() throws SAXException, IOException {
        File file = new File(fullfilename);
        long lastModified = file.lastModified();
        if ((lastModified > cachedTime) || (maps == null)) {
            cachedTime = lastModified;
            load(file);
        }
    }

    private synchronized void load(File file)
      throws SAXException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        }
        if (builder == null) { return; }
        Document doc = builder.parse(file);

        Node all = doc.getFirstChild();
        Node mapNode = all.getFirstChild();
        HashMap map;
        Element element;
        String tagname;

        while (mapNode != null) {
            if (mapNode.getNodeType() == Node.ELEMENT_NODE) {
                element = (Element)mapNode;
                tagname = element.getTagName();
                map = new HashMap();
                Node prop = mapNode.getFirstChild();
                while (prop != null) {
                    if (prop.getNodeType() == Node.ELEMENT_NODE) {
                        element = (Element)prop;
                        if (element.getTagName().equals(propTag)) {
                            String key = element.getAttribute(keyAttr);
                            Text text = (Text)prop.getFirstChild();
                            if (text != null) {
                                map.put(key, text.getData());
                            }
                        }
                    }
                    prop = prop.getNextSibling();
                }
                if (maps == null) { maps = new HashMap(); }
                maps.put(tagname, map);
            }
            mapNode = mapNode.getNextSibling();
        }
    }

}
