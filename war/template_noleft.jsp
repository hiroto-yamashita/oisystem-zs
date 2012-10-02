<%@ page contentType="text/html; charset=SJIS"
%><%@ page import="com.oisix.oisystemfr.ScreenManager"
%><%
if (request.getAttribute("NOCACHE") != null) {
  response.setHeader("Expires", "Mon, 26 Jul 1997 05:00:00 GMT"); 
  response.setHeader("Last-Modified", "Mon, 26 Jul 1997 05:00:00 GMT");
  response.setHeader("Cache-Control","no-cache, must-revalidate");
  response.setHeader("Pragma","no-cache");
}

String header = (String)request.getAttribute(ScreenManager.HEADERJSP);
String main = (String)request.getAttribute(ScreenManager.MAINJSP);
String footer = (String)request.getAttribute(ScreenManager.FOOTERJSP);
String title = (String)request.getAttribute(ScreenManager.JSPTITLE);
String script = (String)request.getAttribute(ScreenManager.SCRIPT);
try {
%>
<html>
<head>
<title><%=title%></title>
<style type="text/css">
<!--
  body { font-size:12px }
  table { font-size:12px; border-style:none }
  td { background-color:#EEEEE0 }
  th { text-align:left; background-color:#99CCFF }
  input { font-size:12px }
  .layout { border-style:none; background-color:white }
  a{ color:#3322FF }
  a:visited{ color:#3322FF }
  a:hover{ color:#3322FF }
  a:active{ color:#3322FF }

  A.link-navigation {
    margin: 0px;
    padding: 0px;
    width: 100%;
    display: block;
    color: #0000FF;
    background-color:#88BBEE;
    text-decoration: none;
  }

  A.link-navigation:hover, A.link-navigation:active {
    color: #ddddff;
  }

  .navigation {
    background-color:#88BBEE;
    margin: 0px;
    padding: 2px 2px 2px 2px;
    display: block;
    font-size: 12px;
    line-height: 16px;
    text-decoration: none;
  }
-->
</style>
<script language="JavaScript" src="<%=script%>"></script>
<script language="JavaScript">
<!--
self.window.name="Main"
SubSearch= 0;
function openSubSearch(url) {
SubSearch=window.open(url,"subsearch","width=400,height=400,scrollbars=yes,resizable=yes");
SubSearch.focus();
}
function closeSubSearch() {
    if ((SubSearch != 0) && (!SubSearch.closed)) { SubSearch.close(); }
}

// メニュークラスの定義
function MyMenu(index, title, url)
{
	this.index = index;
	this.title = title;
	this.url = url;
	this.submenu = new Array();
	this.addSubMenu = addSubMenu;
	this.printMenu = printMenu;
	this.printSubMenu = printSubMenu;
}

function addSubMenu(title, url)
{
	var len = this.submenu.length;
	this.submenu[len] = new MyMenu(len+1, title, url);
	return this.submenu[len];
}
function printMenu()
{
	var menuid = 'menu' + ('00' + this.index).substr(-2);
	var url = this.url;
	if (url == null) {
		url = 'javascript:void(0);';
	}

	document.write('<TABLE BORDER="0" CELLSPACING="0" CELLPADDING="0"><TR>');
	document.write('<TD NOWRAP CLASS="navigation" STYLE="cursor:hand">');
	document.write('<SPAN CLASS="navigation" onMouseOver="showMenu(\'' + menuid + '\');" onMouseOut="outMenu();">');
	document.write(this.title);
	document.write('</SPAN>');
	document.write('</TD>');
	document.write('</TR></TABLE>');


	document.write('<SPAN ID="' + menuid + '" STYLE="position:absolute;z-index:1;background-color:white;display:none;">');
	document.write('<TABLE BORDER="0" CELLSPACING="0" CELLPADDING="0">');
	for (var i = 0; i < this.submenu.length; i++) {
		this.submenu[i].printSubMenu(menuid);
	}
	document.write('</TABLE>');
	document.write('</SPAN>');
}

function printSubMenu(menuid)
{
	var url = this.url;
	if (url == null) {
		url = 'javascript:void(0);';
	}

	document.write('<TR><TD NOWRAP CLASS="navigation">');
	document.write('<SPAN CLASS="navigation" onMouseOver="onMenu(\'' + menuid + '\');" onMouseOut="outMenu();">');
	document.write(this.title);
	document.write('</SPAN>');
	document.write('</TD></TR>');
}
// メニュークラスの定義


// メニュー項目の定義
var menulist = new Array();

function createMenu(title, url)
{
	var len = menulist.length;
	menulist[len] = new MyMenu(len+1, title, url);
	return menulist[len];
}

function drawMenu()
{
	document.write('<TABLE BORDER="0" CELLSPACING="0" CELLPADDING="0"><TR>');
	var i;
	for (i = 0; i < menulist.length; i++) {
		document.write('<TD NOWRAP CLASS="navigation">');
		menulist[i].printMenu();
		document.write('</TD>');
	}
	document.write('<TD WIDTH="100%" CLASS="navigation"></TD>');
	document.write('</TR></TABLE>');
}

// プルダウンメニュー動作定義
var menuShowing = -1;
var menuTimerID = -1;

function showMenu(menuid) {
	unshowDetail();
	toggleform("hidden");

	var submenu = document.getElementById(menuid);

	submenu.style.display = "block";
	menuShowing = menuid;
}
function outMenu() {
	menuTimerID = setTimeout("unshowDetail()", 500);
}
function onMenu() {
	clearTimeout(menuTimerID);
}
function unshowDetail() {
	if (menuShowing != -1) {
		document.getElementById(menuShowing).style.display = "none";
	}
	toggleform("visible");

	clearTimeout(menuTimerID);
}

function toggleform(visibility) {
	len = document.forms.length;
	for (i=0; i<len; i++) {
		len1 = document.forms[i].length;
		for (j=0; j<len1; j++) {
			if (document.forms[i].elements[j].type == "select-one") {
				document.forms[i].elements[j].style.visibility = visibility;
			}
		}
	}
}

//-->
</script>
</head>
<body OnUnload="closeSubSearch()">
<table width="100%" class="layout">
<tr><td  class="layout">
<jsp:include page="<%=header%>" flush="true" />
</td></tr></table>

<table width="100%" class="layout">
<tr><td  class="layout">
<jsp:include page="<%=main%>" flush="true" />
</td></tr></table>

<table width="100%" class="layout">
<tr><td  class="layout">
<jsp:include page="<%=footer%>" flush="true" />
</td></tr></table>

</body>
</html>

<%
//ここからエラー処理
} catch (Exception e) {
%>
<pre>
<%
  //e.printStackTrace(new PrintWriter(out));
  e.printStackTrace();
  //Java Scriptでエラーページにジャンプ？
}
%>
</pre>
</body>
</html>