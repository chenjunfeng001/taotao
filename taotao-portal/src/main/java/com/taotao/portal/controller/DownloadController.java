package com.taotao.portal.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

public class DownloadController {

	@RequestMapping("/download")
	public String showCartItems(HttpServletRequest request, HttpServletResponse response, Model model) {
		String asFileName = request.getParameter("projectname");
		asFileName = "***清单-" + asFileName + ".xls";
		String clientInfo = request.getHeader("User-agent");
		try {
			if (clientInfo != null && clientInfo.indexOf("MSIE") > 0) {
				// IE采用URLEncoder方式处理
				if (clientInfo.indexOf("MSIE 6") > 0 || clientInfo.indexOf("MSIE 5") > 0) {
					// IE6，用GBK，此处实现 有局限性
					asFileName = new String(asFileName.getBytes("UTF-8"), "ISO-8859-1");
				} else {
					// ie7+用URLEncoder方式
					asFileName = java.net.URLEncoder.encode(asFileName, "UTF-8");
				}
			}

			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=" + asFileName);
			response.addHeader("Cache-Control", "no-cache");

			OutputStream os = response.getOutputStream();
			BufferedWriter bufferWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
			String sFileName = "";
			FileInputStream fis = new FileInputStream(new File(sFileName));

			IOUtils.copy(fis, bufferWriter);

			bufferWriter.flush();
			bufferWriter.close();
			bufferWriter = null;
			response.flushBuffer();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String serverName = request.getClass().getName();
		// 如果tomcat执行以下语句
//		if ("org.apache.catalina.connector.RequestFacade".equals(serverName)) {
//			out.clear();
//			out = pageContext.pushBody();
//		}
		return "download";
	}

}
