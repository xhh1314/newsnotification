package cn.haiwai.newsnotification.manage.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

/**
 * 工具类，response写入流
 * @author lh
 * @time 2017年10月25日
 * @version 1.0
 */
public class ResponseWrite {
	public static void write(HttpServletResponse response,String obj) throws IOException{
		response.setHeader("Content-type", "text/html;charset=utf-8");
		PrintWriter out=response.getWriter();
		out.print(obj);
		System.out.println("repsonse返回字符："+obj);
		out.flush();
		out.close();
	}
}
