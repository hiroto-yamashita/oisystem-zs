/*
 * CsvLine.java
 */

package com.oisix.oisystemfr.csv;
import java.util.Vector;
import java.util.Enumeration;

/**
 * CSV�`����1�s���̃f�[�^��ێ�����N���X�B<br>
 * CSV�`���ւ̏����o���ŃG���N�H�[�g�̎w��A<br>
 * ��؂�L��(�J���}�A�^�u�Ȃ�)���w��\�B
 * <pre>
 * 1.0  2002/02/25  ����
 * </pre>
 * @version 1.0
 * @author MQS)ide
 */
public class CsvLine {
	// ���ڂ��i�[����Vector
	private Vector items;
	// ��؂�L��
	private char p_delimiter;

	/**
	 * CsvLine �̃C���X�^���X���쐬����B
	 * �f�t�H���g�̋�؂�L����","�Ƃ���B
	 */
	public CsvLine() {
		this(',');
	}

	/**
	 * ��؂�L�����w�肵��CsvLine�̃C���X�^���X���쐬����B
	 * �Ⴆ�΁A�^�u���w�肷��ꍇ��'\t'���w�肷��B
	 *
	 * @param delimiter ��؂�L��
	 */
	public CsvLine(char delimiter) {
		setDelimiter(delimiter);
		items = new Vector();
	}

	/**
	 * ������Vector�I�u�W�F�N�g����CsvLine�̃C���X�^���X���쐬����B
	 *
	 * @param items Vector�I�u�W�F�N�g
	 */
	public CsvLine(Vector items) {
		this(items, ',');
	}

	/**
	 * ������Vector�I�u�W�F�N�g�Ƌ�؂�L�����w�肵��CsvLine�̃C���X�^���X���쐬����B
	 *
	 * @param items Vector�I�u�W�F�N�g
	 * @param delimiter ��؂�L��
	 */
	public CsvLine(Vector items, char delimiter) {
		setDelimiter(delimiter);
		this.items = items;
	}

	/**
	 * ��؂�L���̐ݒ�B
	 */
	public void setDelimiter(char delimiter) {
		this.p_delimiter = delimiter;
	}

	/**
	 * ��؂�L���̎擾�B
	 */
	public char getDelimiter() {
		return p_delimiter;
	}

	/**
	 * �����Ŏw�肳�ꂽ������𖖔��ɒǉ��B
	 *
	 * @param item �ǉ����镶����
	 */
	public void addItem(String item) {
		addItem(item, false);
	}

	/**
	 * �����Ŏw�肳�ꂽ��������A�����ɒǉ�����B
	 * CSV�`���̃f�[�^�Ƃ��ďo�͂����Ƃ��A���̍��ڂ������I��
	 * �G���N�H�[�g���邩�ǂ����������ɂ���Ďw�肷��B
	 *
	 * @param item �ǉ����镶����
	 * @param enquote true���ƁA�����I�ɃG���N�H�[�g�����B
	 */
	public void addItem(String item, boolean enquote) {
		items.addElement(this.enquote( item ,enquote, p_delimiter ));
	}

	/**
	 * 1�s��CSV�`���̃f�[�^��Ԃ��B
	 *
	 * @return �P�s��CSV�`���̃f�[�^
	 */
	public String getLine() {
		StringBuffer list = new StringBuffer();
		for (int i = 0; i < items.size(); i++) {
			// �v�f�𕶎���ɕϊ����J���}��؂�ɂ���
			list.append(items.elementAt(i).toString());
			if (items.size() - 1 != i) {
				list.append(p_delimiter);
			}
		}
		return new String(list);
	}

	/**
	 * 1�s�̍��ڐ���Ԃ��B
	 *
	 * @return CsvLine�Ɋ܂�ł��鍀�ڐ�
	 */
	public int size() {
		return items.size();
	}

	/**
	 * n�Ԗڂ̍��ڂ� String �ŕԂ��B
	 *
	 * @param n ���ڂ̔ԍ� [0 �` size()-1]
	 * @return n�Ԗڂ̕�����B
	 */
	public String getItem(int n) {
		return (String)items.elementAt(n);
    }

	/**
	 * n�Ԗڂ̍��ڂ��폜����B
	 *
	 * @param n ���ڂ̔ԍ� [0 �` size()-1]
	 */
	public void removeItem(int n) {
		items.removeElementAt(n);
	}

	/**
	 * ���ׂĂ̗v�f���폜���A�T�C�Y��0�ɐݒ肷��B
	 */
	public void removeAllItems() {
		items.removeAllElements();
	}


	/**
	 * �����̕����� item �� CSV �ŏo�͂ł���悤�ɉ��H�����������
	 * �Ԃ��B<br>
	 * item �� " �� , ���܂�ł���Ƃ��ɂ� item �S�̂� " �ň͂�
	 * �i�G���N�H�[�g���j�A" �� "" �ɒu��������B�܂�" �� , �̂ǂ�
	 * ����܂�ł��Ȃ��Ƃ��́Aitem �����̂܂ܕԂ��B<br>
	 *
	 * @param item ����������������
	 * @return item ����������������
	 */
    public static String enquote(String item) {
		return enquote(item, false);
	}

	/**
	 * �����̕����� item �� CSV �ŏo�͂ł���悤�ɉ��H�����������
	 * �Ԃ��B<br>
	 * enquote �� true �̂Ƃ��́A�����I�ɃG���N�H�[�g����B�܂�A
	 * item �� " �ň͂����������Ԃ��B<br>
	 * false �̂Ƃ��́A�G���N�H�[�g���邩�ǂ����́Aitem �ɂ��B
	 * item �� " �� , ���܂�ł���Ƃ��ɂ� item �S�̂� " �ň͂�
	 * �i�G���N�H�[�g���j�A" �� "" �ɒu��������B�܂�" �� , �̂ǂ�
	 * ����܂�ł��Ȃ��Ƃ��́Aitem �����̂܂ܕԂ��B<br>
	 * item ����A�܂蒷�����[���̕����񂾂����ꍇ�A����������
	 * ��̕���������̂܂ܕԂ��B
	 *
	 * @param item ����������������
	 * @param enquote true�Ȃ狭���I�ɃG���N�H�[�g����
	 * @return item ����������������
	 */
	public static String enquote(String item, boolean enquote) {
		return enquote(item,enquote,',');
	}

	/**
	 * ��؂�L�����w�肵�ăG���N�H�[�g����B
	 *
	 * @param item ����������������
	 * @param enquote true�Ȃ狭���I�ɃG���N�H�[�g����
	 * @param delimiter ��؂�L��
	 * @return item ����������������
	 */
	public static String enquote(String item, boolean enquote, char delimiter) {
		if (item.length() == 0) {
			return item;
		}
		if (item.indexOf('"') < 0 && item.indexOf(delimiter) < 0 && enquote == false) {
			return item;
        }

		// StringBuffer�̃T�C�Y�́A�ł��ُ�ȏꍇ��z�肵���B
		// ������ """"" ���G���N�H�[�g���ďo�͂���悤�ȂƂ��̂��ƁB

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
