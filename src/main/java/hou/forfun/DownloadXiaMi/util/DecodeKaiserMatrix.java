package hou.forfun.DownloadXiaMi.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * @author houweitao
 * @date 2016年5月1日上午11:20:25 7h%5xo%4%%47%%E3hD5892212-l
 *       t2.im265584554%_3E9beb4%%l tFfa%F%EE%7EE_3k218a2%655
 *       p%im212%%28_2lFe%39%852EE %2liF%F55F159.ay56454E1%-
 *       3Fe.452EE148%mu%Ed7E3c45n Am.c6E1287685pt3%584e-7Eu
 */

public class DecodeKaiserMatrix {

	public static void main(String[] args) {
		DecodeKaiserMatrix dkm = new DecodeKaiserMatrix();
		String str = "5h3%..i%%52525%74_55.3h%%E54a23547%5ltA2fx.22EFE%E2465EEmF_35187%eeE625Elt%FiicFF42%58F7%824pakDE398522c2%E-p2mlao41615E418589_3ue3%689E8b-15-n%F5emm6%%%E%871E%%l%ty25d9b44%14E%u";
		System.out.println(decodeRubust(str));
	}

	static public String decodeRubust(String str) {
		if (str == null || str.length() < 1)
			return null;
		int num = Integer.valueOf(str.charAt(0) + "");
		int step = (str.length() - 1) / num;
		int helpStep = step;
		String[] matrix = new String[num];
		System.out.println(str.length() - 1);

		int duo = (str.length() - 1) % num;

		for (int i = 0, j = 1; i < matrix.length; i++, j = j + helpStep) {
			if (i < duo)
				helpStep = step + 1;
			else
				helpStep = step;

			matrix[i] = str.substring(j, j + helpStep);
			System.out.println(matrix[i]);
		}
		// print(matrix);

		return makeSense(matrix);

	}

	static private String decode(String str) {
		if (str == null || str.length() < 1)
			return null;
		int num = Integer.valueOf(str.charAt(0) + "");
		// System.out.println(num);
		System.out.println(str.length() - 1);
		if ((str.length() - 1) % num != 0)
			return null;

		int step = (str.length() - 1) / num;

		String[] matrix = new String[num];
		for (int i = 0, j = 1; i < matrix.length; i++, j = j + step) {
			matrix[i] = str.substring(j, Math.max(j + step, matrix.length));
			System.out.println(matrix[i]);
		}

		// print(matrix);

		return makeSense(matrix);
	}

	private static String makeSense(String[] matrix) {
		StringBuffer sb = new StringBuffer();
		for (int j = 0; j < matrix[0].length(); j++) {
			for (int i = 0; i < matrix.length; i++) {
				if (matrix[i].length() - 1 >= j)
					sb.append(matrix[i].charAt(j));
			}
		}

		System.out.println(sb.toString());

		String tmp = new String();
		try {
			tmp = URLDecoder.decode(sb.toString(), "UTF-8");
			tmp = tmp.replaceAll("\\^", "0");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			tmp = null;
		}

		if (tmp.subSequence(0, 7).equals("http://") && tmp.substring(tmp.length()-6).equals("0-null"))
			return tmp;
		else
			return null;
	}

	private void print(String[] matrix) {
		for (int i = 0; i < matrix.length; i++) {
			System.out.println(matrix[i]);
		}
		System.out.println();
	}

}
