package com.oisix.oisystemfr;

import java.util.StringTokenizer;
import java.util.HashMap;
import java.net.URL;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class ScreenManager {

    //最終的にはXMLから読み込んでオーバーライドしたい
    private String defaultHeaderPath = "shared/head/";
    private String defaultHeader = defaultHeaderPath + "header.jsp";
    private String defaultLeftPath = "shared/left/";
    private String defaultLeft = defaultLeftPath + "left.jsp";
    private String defaultFooterPath = "shared/foot/";
    private String defaultFooter = defaultFooterPath + "footer.jsp";
    private String defaultTemplate = "template.jsp";
    private String noleftTemplate = "template_noleft.jsp";
    private String defaultTitle = "OTIS sample";
    private boolean defaultNeedLeft = false;
    // ディレクトリごとのヘッダーが必要か
    private boolean needHeaderByDir = false;
    // ディレクトリごとのフッターが必要か
    private boolean needFooterByDir = false;
    // 全ページログインが必要な場合はtrue
    private boolean defaultNeedLogin = false;
    // ログインページのURI(サフィックスなし)
    private String loginURI = "login";
    // ログイン成功ページのURI(サフィックスなし)
    private String loginSuccessURI = "loginsuccess";
    // ログイン後URI(nullの場合最初にリクエストがあったページ)
    private String loginRedirectURI = null;
    private String headersuffix = "h.jsp";
    private String leftsuffix = "l.jsp";
    private String mainsuffix = ".jsp";
    private String footersuffix = "f.jsp";
    private String scriptsuffix = ".js";
    private HashMap screenDef = new HashMap();

    public static final String HEADERJSP = "headerjsp";
    public static final String LEFTJSP = "leftjsp";
    public static final String MAINJSP = "mainjsp";
    public static final String FOOTERJSP = "footjsp";
    public static final String SCRIPT = "script";
    public static final String JSPTITLE = "jsptitle";

    public String getLoginURI() { return loginURI; }
    public String getLoginRedirectURI() { return loginRedirectURI; }

    //public void init(String smxmlurl) {
    public void init(URL url) {
        //XMLからデフォルトを読み込む
        //XMLからカスタマイズデータを読み込む
        load(url);
    }

    private void load(URL url) {
        try {
            //URL url = new URL(location);
            InputSource xmlInp = new InputSource(url.openStream());
            DocumentBuilderFactory factory =
              DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = null;
            builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlInp);
            NodeList nodeList = doc.getElementsByTagName("default");
            Element element;
            for (int i=0; i<nodeList.getLength(); i++) {
                element = (Element)nodeList.item(i);
                needHeaderByDir = getBoolean(
                  "need-header-by-dir", element, needHeaderByDir);
                needFooterByDir = getBoolean(
                  "need-footer-by-dir", element, needFooterByDir);
                defaultNeedLeft = getBoolean(
                  "need-left", element, defaultNeedLeft);
                String val = getString("title", element);
                if (val != null) {
                    defaultTitle = val;
                }
                defaultNeedLogin = getBoolean(
                  "need-login", element, defaultNeedLogin);
                val = getString("login-uri", element);
                if (val != null) {
                    loginURI = val;
                }
                val = getString("login-success-uri", element);
                if (val != null) {
                    loginSuccessURI = val;
                }
                val = getString("login-redirect-uri", element);
                if (val != null) {
                    loginRedirectURI = val;
                }
                val = getString("title", element);
                if (val != null) {
                    defaultTitle = val;
                }
            }
            nodeList = doc.getElementsByTagName("screen");
            String template = null;
            String header = null;
            String left = null;
            String main = null;
            String footer = null;
            String script = null;
            String title = null;
            boolean needLeft;
            boolean needSSL;
            boolean needLogin;
            boolean nocache;
            ScreenDefinition def = null;
            for (int i=0; i<nodeList.getLength(); i++) {
                element = (Element)nodeList.item(i);
                String uri = element.getAttribute("uri");
                template = getString("template", element);
                header = getString("header", element);
                left = getString("left", element);
                main = getString("main", element);
                footer = getString("footer", element);
                script = getString("script", element);
                title = getString("title", element);
                needLeft = getBoolean("needleft", element, defaultNeedLeft);
                needSSL = getBoolean("needssl", element ,false);
                needLogin = getBoolean("needlogin", element ,false);
                nocache = getBoolean("nocache", element ,false);
                def = new ScreenDefinition(template, header, left, main,
                  footer, script, title, needLeft, needSSL, needLogin,
                  nocache);
                screenDef.put(uri, def);
            }
        } catch (Exception e) {
            Debug.println(e);
        }
    }

    private String getString(String tagname,Element element) {
        NodeList children = element.getElementsByTagName(tagname);
        if ((children != null) && (children.getLength() > 0)) {
            Node node = children.item(0).getFirstChild();
            if ((node != null) && (node.getNodeType() == Node.TEXT_NODE)) {
                String str = ((Text)node).getData();
                return str;
            }
        }
        return null;
    }

    private boolean getBoolean(
      String tagname, Element element, boolean def) {
        String str = getString(tagname, element);
        if (str == null) { return def; }
        return str.equals("true");
    }

    public String setNextScreen(HttpServletRequest request) {
        //String uri = request.getPathInfo();
        String uri = (String)request.getAttribute(ControllerServlet.PATH_INFO);
        String dir = null;
        if (uri.lastIndexOf("/") > 0) {
            dir = uri.substring(0, uri.lastIndexOf("/"));
            Debug.println("dir="+dir, this);
        }
        Debug.println("uri="+uri, this);

        String template = getTemplate(uri);
        request.setAttribute(HEADERJSP, getHeader(uri, dir));
        Debug.println("header="+request.getAttribute(HEADERJSP), this);
        if (needLeft(uri)) {
            request.setAttribute(LEFTJSP, getLeft(uri, dir));
            Debug.println("left="+request.getAttribute(LEFTJSP), this);
        }
        request.setAttribute(MAINJSP, getMain(uri));
        Debug.println("main="+request.getAttribute(MAINJSP), this);
        request.setAttribute(FOOTERJSP, getFooter(uri, dir));
        Debug.println("footer="+request.getAttribute(FOOTERJSP), this);
        request.setAttribute(SCRIPT, getScript(uri));
        Debug.println("script="+request.getAttribute(SCRIPT), this);
        request.setAttribute(JSPTITLE, getTitle(uri));

        // ログインチェック
        if (!uri.equals(loginURI) && !uri.equals(loginSuccessURI)
          && !uri.equals(UrlUtil.TRANSACTION_URL)) {
            if (defaultNeedLogin) {
                request.setAttribute("NEEDLOGIN", "TRUE");
            } else {
                ScreenDefinition def = (ScreenDefinition)screenDef.get(uri);
                if ((def != null) && (def.getNeedLogin())) {
                    request.setAttribute("NEEDLOGIN", "TRUE");
                }
            }
        }

        //キャッシュ
        if (getNocache(uri)) {
            request.setAttribute("NOCACHE", "TRUE");
        }

        return template;
    }

    private String getTemplate(String uri) {
        String template = getTemplateFromUri(uri);
        if (template == null) {
            template = defaultTemplate;
            if (!needLeft(uri)) {
                template = noleftTemplate;
            }
        }
        return template;
    }

    private String getTemplateFromUri(String uri) {
        ScreenDefinition def = (ScreenDefinition)screenDef.get(uri);
        if (def != null) {
            return def.getTemplate();
        }
        return null;
    }

    private String getHeader(String uri, String dir) {
        String header = null;
        header = getHeaderFromUri(uri);
        if (header == null) {
            header = getDefaultHeader(dir);
        }
        return header;
    }

    //最終的にはXMLから設定を読み込む
    private String getHeaderFromUri(String uri) {
        ScreenDefinition def = (ScreenDefinition)screenDef.get(uri);
        if (def != null) {
            return def.getHeader();
        }
        return null;
    }

    private String getDefaultHeader(String dir) {
        if (needHeaderByDir && (dir != null)) {
            return defaultHeaderPath + dir + headersuffix;
        }
        return defaultHeader;
    }

    private String getLeft(String uri, String dir) {
        String left = null;
        left = getLeftFromUri(uri);
        if (left == null) {
            left = getDefaultLeft(dir);
        }
        return left;
    }

    //最終的にはXMLから設定を読み込む
    private String getLeftFromUri(String uri) {
        ScreenDefinition def = (ScreenDefinition)screenDef.get(uri);
        if (def != null) {
            return def.getLeft();
        }
        return null;
    }

    private String getDefaultLeft(String dir) {
        if (dir != null) {
            return defaultLeftPath + dir + leftsuffix;
        }
        return defaultLeft;
    }

    private boolean needLeft(String uri) {
        ScreenDefinition def = (ScreenDefinition)screenDef.get(uri);
        if (def != null) {
            return def.getNeedLeft();
        }
        return defaultNeedLeft;
    }

    private String getMain(String uri) {
        String main = null;
        main = getMainFromUri(uri);
        if (main == null) {
            main = getDefaultMain(uri);
        }
        return main;
    }

    //最終的にはXMLから設定を読み込む
    private String getMainFromUri(String uri) {
        ScreenDefinition def = (ScreenDefinition)screenDef.get(uri);
        if (def != null) {
            return def.getMain();
        }
        return null;
    }

    private String getDefaultMain(String uri) {
        return uri + mainsuffix;
    }

    private String getFooter(String uri, String dir) {
        String footer = null;
        footer = getFooterFromUri(uri);
        if (footer == null) {
            footer = getDefaultFooter(dir);
        }
        return footer;
    }

    //最終的にはXMLから設定を読み込む
    private String getFooterFromUri(String uri) {
        ScreenDefinition def = (ScreenDefinition)screenDef.get(uri);
        if (def != null) {
            return def.getFooter();
        }
        return null;
    }

    private String getDefaultFooter(String dir) {
        if (needFooterByDir && (dir != null)) {
            return defaultFooterPath + dir + footersuffix;
        }
        return defaultFooter;
    }

    private String getScript(String uri) {
        String script = null;
        script = getScriptFromUri(uri);
        if (script == null) {
            script = getDefaultScript(uri);
        }
        return script;
    }

    private String getScriptFromUri(String uri) {
        ScreenDefinition def = (ScreenDefinition)screenDef.get(uri);
        if (def != null) {
            return def.getScript();
        }
        return null;
    }

    private String getDefaultScript(String uri) {
        return uri + scriptsuffix;
    }

    private String getTitle(String uri) {
        String title = null;
        title = getTitleFromUri(uri);
        if (title == null) {
            title = getDefaultTitle();
        }
        return title;
    }

    private String getDefaultTitle() { return defaultTitle; }

    private String getTitleFromUri(String uri) {
        ScreenDefinition def = (ScreenDefinition)screenDef.get(uri);
        if (def != null) {
            return def.getTitle();
        }
        return null;
    }

    private boolean getNocache(String uri) {
        ScreenDefinition def = (ScreenDefinition)screenDef.get(uri);
        if (def != null) {
            return def.getNocache();
        }
        return false;
    }
}
