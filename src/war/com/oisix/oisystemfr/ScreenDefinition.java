package com.oisix.oisystemfr;

public class ScreenDefinition {
    private String template;
    private String header;
    private String left;
    private String main;
    private String footer;
    private String script;
    private String title;
    private boolean needLeft;
    private boolean needSSL;
    private boolean needLogin;
    private boolean nocache;

    public ScreenDefinition(String template, String header, String main,
      String left, String footer, String script, String title,
      boolean needLeft, boolean needSSL, boolean needLogin, boolean nocache) {
        this.template = template;
        this.header = header;
        this.left = left;
        this.main = main;
        this.footer = footer;
        this.script = script;
        this.title = title;
        this.needLeft = needLeft;
        this.needSSL = needSSL;
        this.needLogin = needLogin;
        this.nocache = nocache;
    }

    public String getTemplate() { return template; }
    public String getHeader() { return header; }
    public String getLeft() { return left; }
    public String getMain() { return main; }
    public String getFooter() { return footer; }
    public String getScript() { return script; }
    public String getTitle() { return title; }
    public boolean getNeedLeft() { return needLeft; }
    public boolean getNeedSSL() { return needSSL; }
    public boolean getNeedLogin() { return needLogin; }
    public boolean getNocache() { return nocache; }
}
