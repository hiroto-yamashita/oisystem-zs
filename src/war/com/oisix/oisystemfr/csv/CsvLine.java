/*
 * CsvLine.java
 */

package com.oisix.oisystemfr.csv;
import java.util.Vector;
import java.util.Enumeration;

/**
 * CSV形式の1行分のデータを保持するクラス。<br>
 * CSV形式への書き出しでエンクォートの指定、<br>
 * 区切り記号(カンマ、タブなど)が指定可能。
 * <pre>
 * 1.0  2002/02/25  初版
 * </pre>
 * @version 1.0
 * @author MQS)ide
 */
public class CsvLine {
	// 項目を格納するVector
	private Vector items;
	// 区切り記号
	private char p_delimiter;

	/**
	 * CsvLine のインスタンスを作成する。
	 * デフォルトの区切り記号は","とする。
	 */
	public CsvLine() {
		this(',');
	}

	/**
	 * 区切り記号を指定してCsvLineのインスタンスを作成する。
	 * 例えば、タブを指定する場合は'\t'を指定する。
	 *
	 * @param delimiter 区切り記号
	 */
	public CsvLine(char delimiter) {
		setDelimiter(delimiter);
		items = new Vector();
	}

	/**
	 * 既存のVectorオブジェクトからCsvLineのインスタンスを作成する。
	 *
	 * @param items Vectorオブジェクト
	 */
	public CsvLine(Vector items) {
		this(items, ',');
	}

	/**
	 * 既存のVectorオブジェクトと区切り記号を指定してCsvLineのインスタンスを作成する。
	 *
	 * @param items Vectorオブジェクト
	 * @param delimiter 区切り記号
	 */
	public CsvLine(Vector items, char delimiter) {
		setDelimiter(delimiter);
		this.items = items;
	}

	/**
	 * 区切り記号の設定。
	 */
	public void setDelimiter(char delimiter) {
		this.p_delimiter = delimiter;
	}

	/**
	 * 区切り記号の取得。
	 */
	public char getDelimiter() {
		return p_delimiter;
	}

	/**
	 * 引数で指定された文字列を末尾に追加。
	 *
	 * @param item 追加する文字列
	 */
	public void addItem(String item) {
		addItem(item, false);
	}

	/**
	 * 引数で指定された文字列を、末尾に追加する。
	 * CSV形式のデータとして出力されるとき、その項目を強制的に
	 * エンクォートするかどうかを引数によって指定する。
	 *
	 * @param item 追加する文字列
	 * @param enquote trueだと、強制的にエンクォートされる。
	 */
	public void addItem(String item, boolean enquote) {
		items.addElement(this.enquote( item ,enquote, p_delimiter ));
	}

	/**
	 * 1行のCSV形式のデータを返す。
	 *
	 * @return １行のCSV形式のデータ
	 */
	public String getLine() {
		StringBuffer list = new StringBuffer();
		for (int i = 0; i < items.size(); i++) {
			// 要素を文字列に変換しカンマ区切りにする
			list.append(items.elementAt(i).toString());
			if (items.size() - 1 != i) {
				list.append(p_delimiter);
			}
		}
		return new String(list);
	}

	/**
	 * 1行の項目数を返す。
	 *
	 * @return CsvLineに含んでいる項目数
	 */
	public int size() {
		return items.size();
	}

	/**
	 * n番目の項目を String で返す。
	 *
	 * @param n 項目の番号 [0 ～ size()-1]
	 * @return n番目の文字列。
	 */
	public String getItem(int n) {
		return (String)items.elementAt(n);
    }

	/**
	 * n番目の項目を削除する。
	 *
	 * @param n 項目の番号 [0 ～ size()-1]
	 */
	public void removeItem(int n) {
		items.removeElementAt(n);
	}

	/**
	 * すべての要素を削除し、サイズを0に設定する。
	 */
	public void removeAllItems() {
		items.removeAllElements();
	}


	/**
	 * 引数の文字列 item を CSV で出力できるように加工した文字列を
	 * 返す。<br>
	 * item が " か , を含んでいるときには item 全体を " で囲み
	 * （エンクォートし）、" を "" に置き換える。また" と , のどち
	 * らも含んでいないときは、item をそのまま返す。<br>
	 *
	 * @param item 処理したい文字列
	 * @return item を処理した文字列
	 */
    public static String enquote(String item) {
		return enquote(item, false);
	}

	/**
	 * 引数の文字列 item を CSV で出力できるように加工した文字列を
	 * 返す。<br>
	 * enquote が true のときは、強制的にエンクォートする。つまり、
	 * item を " で囲った文字列を返す。<br>
	 * false のときは、エンクォートするかどうかは、item による。
	 * item が " か , を含んでいるときには item 全体を " で囲み
	 * （エンクォートし）、" を "" に置き換える。また" と , のどち
	 * らも含んでいないときは、item をそのまま返す。<br>
	 * item が空、つまり長さがゼロの文字列だった場合、何もせずに
	 * 空の文字列をそのまま返す。
	 *
	 * @param item 処理したい文字列
	 * @param enquote trueなら強制的にエンクォートする
	 * @return item を処理した文字列
	 */
	public static String enquote(String item, boolean enquote) {
		return enquote(item,enquote,',');
	}

	/**
	 * 区切り記号を指定してエンクォートする。
	 *
	 * @param item 処理したい文字列
	 * @param enquote trueなら強制的にエンクォートする
	 * @param delimiter 区切り記号
	 * @return item を処理した文字列
	 */
	public static String enquote(String item, boolean enquote, char delimiter) {
		if (item.length() == 0) {
			return item;
		}
		if (item.indexOf('"') < 0 && item.indexOf(delimiter) < 0 && enquote == false) {
			return item;
        }

		// StringBufferのサイズは、最も異常な場合を想定した。
		// 文字列 """"" をエンクォートして出力するようなときのこと。

		StringBuffer sb = new StringBuffer(item.length() * 2 + 2);
		sb.append('"');
		for (int ind = 0; ind < item.length(); ind ++) {
			char ch = item.charAt(ind);
			if ('"' == ch) {
				sb.append("\"\"");
			} else {
				sb.append(ch);
			}
		}
		sb.append('"');

		return new String(sb);
	}
}
