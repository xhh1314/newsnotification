package cn.haiwai.newsnotification.manage.util;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

import cn.haiwai.newsnotification.manage.response.Response;

/**
 * 工具类，response写入流
 * 
 * @author lh
 * @time 2017年10月25日
 * @version 1.0
 */
public class ResponseWrite {

	/**
	 * 把response使用json格式写出去
	 * @param response
	 * @param json
	 */
	public static void wirteJson(HttpServletResponse response, Response json) {
		response.setContentType("application/json;charset=utf-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.print(JSON.toJSONString(json));
			//System.out.println("repsonse返回字符：" + JSON.toJSONString(json));
			out.flush();
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			if (out != null)
				out.close();

		}

	}
}
