package com.taotao.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

public class SensitiveInfoUtils {
	public static String getHiddenCertid(String certid) {
		// 首部显示6位，尾部显示1位，中间显示为*
		String hiddenCertid = "";
		String str = "*";
		int length = certid.length();
		if (length > 8) {
			int count = 1;
			while (count != length) {
				str = str + "*";
				count++;
			}
			hiddenCertid = certid.substring(0, 5) + str + certid.substring(length - 1);
		}
		return hiddenCertid;
	}

	/**
	 * 根据身份证号获取年龄
	 * 
	 * @param certId
	 * @return
	 */
	public static String getAgeByCertId(String certId) {
		String birthday = "";
		if (certId.length() == 18) {
			birthday = certId.substring(6, 10) + "/" + certId.substring(10, 12) + "/" + certId.substring(12, 14);
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Date now = new Date();
		Date birth = new Date();
		try {
			birth = sdf.parse(birthday);
		} catch (ParseException e) {
		}
		long intervalMilli = now.getTime() - birth.getTime();
		int age = (int) (intervalMilli / (24 * 60 * 60 * 1000)) / 365;
		// System.out.println(age);
		return age + "";
	}

	// 获取隐藏姓名
	public static String getHiddenTrueName(String type, String name) {
		if (StringUtils.isBlank(name)) {
			return "";
		}
		if ("01".equals(type)) {// 个人用户显示姓
			return StringUtils.rightPad(StringUtils.left(name, 1), StringUtils.length(name), "*");
		} else {
			// 企业用户：前二后二显示中间隐藏
			String concat = StringUtils.left(name, 2).concat(StringUtils
					.removeStart(StringUtils.leftPad(StringUtils.right(name, 2), StringUtils.length(name), "*"), "**"));
			return concat;
		}
	}

	// 获取隐藏姓名
	public static String getHiddenTrueName(String userType, Object truename) {
		if (truename == null) {
			return "";
		}
		String sTrueName = truename.toString();
		int iLength = sTrueName.length();
		String sTmp = "";
		if ("01".equals(userType)) {
			// 个人用户显示姓
			for (int i = 1; i < iLength; i++) {
				sTmp += "*";
			}
			sTrueName = sTrueName.substring(0, 1) + sTmp;

		} else {
			// 企业用户：前二后二显示中间隐藏
			for (int i = 2; i < iLength - 2; i++) {
				sTmp += "*";
			}
			sTrueName = sTrueName.substring(0, 2) + sTmp
					+ sTrueName.substring(sTrueName.length() - 2, sTrueName.length());
		}

		return sTrueName;
	}
}
