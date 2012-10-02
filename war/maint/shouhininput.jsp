<%@ page contentType="text/html; charset=SJIS" %>
<%@ page import="com.oisix.oisystemfr.*,com.oisix.oisystemzs.ejb.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/oisystemfr.tld" prefix="osx" %>
<%@ taglib uri="/WEB-INF/oisystemzs.tld" prefix="osxzs" %>
<script language="JavaScript1.1">
function changeHacchuutanka() {
  irisuustr = document.main.elements["#irisuu"].value;
  if (irisuustr == "") {
    return;
  }
  irisuu = parseFloat(irisuustr);
  hyoujuntankastr = document.main.elements["#hyoujuntanka"].value;
  if (hyoujuntankastr == "") {
    return;
  }
  hacchuutanka = document.main.elements["#tanka"].value;
  if (hacchuutanka != "") {
    if (!confirm("発注単価を変更しますか？")) {
      return;
    }
  }
  hyoujuntanka = parseFloat(hyoujuntankastr);
  document.main.elements["#tanka"].value = irisuu * hyoujuntanka;
}
function checktanka() {
  hacchuutankastr = document.main.elements["#tanka"].value;
  hacchuutanka = parseFloat(hacchuutankastr);
  irisuustr = document.main.elements["#irisuu"].value;
  irisuu = parseFloat(irisuustr);
  hyoujuntankastr = document.main.elements["#hyoujuntanka"].value;
  hyoujuntanka = parseFloat(hyoujuntankastr);
  if (hacchuutanka != irisuu * hyoujuntanka) {
    return confirm("発注単価 " + hacchuutanka + " は、入り数 " + irisuu + " × 標準単価 " + hyoujuntanka + " と一致しません。商品を登録して問題ありませんか？");
  }
  return true;
}
</script>
<%
TransactionEvent event = (TransactionEvent)
  request.getAttribute(TransactionServlet.RESULTKEY);
if (event != null) {
  Iterator iter = event.getErrorlist().iterator();
%>
エラーです。以下のエラーメッセージに従い入力内容を見直してください。
<p>
<%
  while (iter.hasNext()) {
%>
<%=iter.next() %><br>
<%
  }
}
// ここまでエラーだった場合
%>
<osx:form transaction="doshouhininput.ev" result="maint/shouhininputsuccess.ev" method="post" option="name='main' onsubmit='return checktanka()'">
<table>
<tr>
<th>商品コード</th><td><osx:input type="text" name="#shouhin_id" size="6" value=""/></td>
</tr>
<tr>
<th>商品名</th><td><osx:input type="text" name="#shouhin" size="80" value=""/></td>
</tr>
<tr>
<th>商品名フリガナ</th><td><osx:input type="text" name="#shouhinfurigana" size="80" value=""/></td>
</tr>
<tr>
<th>仕入先コード</th><td>
<osx:input type="text" name="#shiiresaki_id" size="6" value=""/><span style="cursor:hand" onClick="openSubSearch('../shared/shiiresakisearch.ev?form=main&shiiresaki_id=shiiresaki_id&shiiresaki=shiiresaki')"><osx:img src="shared/image/icon_search01.gif" width="17" height="15" /></span>
<span id="shiiresaki"></span>
</td>
</tr>
<tr>
<th>発注商品名1</th><td><osx:input type="text" name="#hacchuushouhin1" size="80" value=""/></td>
</tr>
<tr>
<th>発注商品名2</th><td><osx:input type="text" name="#hacchuushouhin2" size="80" value=""/></td>
</tr>
<tr>
<th>発注商品名3</th><td><osx:input type="text" name="#hacchuushouhin3" size="80" value=""/></td>
</tr>
<tr>
<th>
規格
<br>
</th>
<td>
<osx:input type="text" name="#kikaku" value=""/>
(基本的に出荷の規格になります)(例：500g)
</td>
</tr>
<tr>
<th>
単位
</th>
<td>
<osxzs:taniselect name="#tani" defaultval="（単位）" />(上記規格の単位です)
</td>
</tr>
<tr>
<th>
発注規格
</th>
<td>
<osx:input type="text" name="#hacchuukikaku" value=""/>
(発注書の規格になります)(例：130g*6個×4)
</td>
</tr>
<tr>
<th>発注点</th><td><osx:input type="text" name="#hacchuuten" size="5" value=""/></td>
</tr>
<tr>
<th>発注単位</th><td><osxzs:taniselect name="#hacchuutani" defaultval="（単位）" /></td>
</tr>
<tr>
<th>入り数</th><td><osx:input type="text" name="#irisuu" size="5" value="" option="onchange='changeHacchuutanka()'"/></td>
</tr>
<tr>
<th>発注単位数</th><td><osx:input type="text" name="#hacchuutanisuu" size="5" value=""/></td>
</tr>
<tr>
<th>最低発注数</th><td><osx:input type="text" name="#saiteihacchuusuu" size="5" value=""/></td>
</tr>
<tr>
<th>
標準単価
</th>
<td>
<osx:input type="text" name="#hyoujuntanka" size="6" value="" option="onchange='changeHacchuutanka()'"/>
(在庫評価を標準原価法で計算する時の単価。
規格一単位あたりの原価を入力)
</td>
</tr>
<tr>
<th>
発注単価
</th>
<td>
<osx:input type="text" name="#tanka" size="6" value=""/>
(発注規格一単位あたりの価格を入力)
</td>
</tr>
<tr>
<th>
標準売価
</th>
<td>
<osx:input type="text" name="#hyoujunbaika" size="6" value=""/>
(参考項目。規格一単位あたりの売価を入力)
</td>
</tr>
<tr>
<th>仕入リードタイム</th><td><osx:input type="text" name="#shiireleadtime" size="5" value=""/></td>
</tr>
<tr>
<th>温度帯</th><td><osxzs:ondotaiselect name="#ondotai" defaultval="（温度帯）" /></td>
</tr>
<tr>
<th>賞味期限管理</th>
<td>
<select name="#shoumikigen_flg">
<osx:option name="#shoumikigen_flg" value="1" />する
<osx:option name="#shoumikigen_flg" value="0" />しない
</select>
</td>
</tr>
<tr>
<th>賞味期限日数</th><td><osx:input type="text" name="#shoumikigennissuu" size="5" value=""/></td>
</tr>
<tr>
<th>出荷期限日数</th><td><osx:input type="text" name="#shukkakigennissuu" size="5" value=""/></td>
</tr>
<tr>
<th>個別発注</th><td><osx:input type="checkbox" name="#kobetsuhacchuu_flg" value="1"/>個別発注する</td>
</tr>
<tr>
<th>要注意区分</th><td><osxzs:youchuuikubunselect name="#youchuuihin_flg" defaultval="0"/></td>
</tr>
<tr>
<th>要ラベル</th><td><osx:input type="checkbox" name="#youraberu_flg" value="1"/>入荷ラベルを発行する</td>
</tr>
<tr>
<th>大分類</th><td>
<osxzs:shouhindaibunrui name="#daibunrui" type="select" defaultval="" />
</td>
</tr>
<tr>
<th>Pコード</th><td><osx:input type="text" name="#pcode" size="6" value=""/></td>
</tr>
<tr>
<!-- <th>在庫評価方法</th><td></td> 在庫評価方法は移動平均 -->
<input type="hidden" name="#zaikohyoukahouhou" value="1">
<th>JANコード</th><td><osx:input type="text" name="#jancode" size="80" value=""/></td>
</tr>
<tr>
<th>型番</th><td><osx:input type="text" name="#kataban" size="80" value=""/></td>
</tr>
<tr>
<th>ロケーションコード1</th><td><osx:input type="text" name="#location_id1" size="6" value=""/></td>
</tr>
<tr>
<th>ロケーションコード2</th><td><osx:input type="text" name="#location_id2" size="6" value=""/></td>
</tr>
<tr>
<th>ロケーションコード3</th><td><osx:input type="text" name="#location_id3" size="6" value=""/></td>
</tr>
<tr>
<th>荷姿</th><td><osx:input type="text" name="#nisugata" size="80" value=""/></td>
</tr>
<tr>
<th>発注コメント</th><td><osx:input type="text" name="#hacchuucomment" size="80" value=""/></td>
</tr>
<tr>
<th>販売区分</th><td><osxzs:hanbaikubunselect name="#hanbaikubun" defaultval="0"/></td>
</tr>
<tr>
<th>種別</th>
<td>
<select name="#shubetsu">
<osx:option name="#shubetsu" value="青果"/>青果
<osx:option name="#shubetsu" value="日配品"/>日配品
<osx:option name="#shubetsu" value="加工品"/>加工品
</select>
</td>
</tr>
<tr>
<th>翌週持ち越しフラグ</th>
<td><osx:input type="checkbox" name="#mochikoshi_flg" value="1"/></td>
</tr>
<tr>
<th>終売フラグ</th>
<td><osx:input type="checkbox" name="#shuubai_flg" value="1"/></td>
</tr>
<tr>
<th>深夜納品フラグ</th>
<td><osx:input type="checkbox" name="#shinyanouhin_flg" value="1"/></td>
</tr>
</table>
<input type="submit" value="入力">
</osx:form>
