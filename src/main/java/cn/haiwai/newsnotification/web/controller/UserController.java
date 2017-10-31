package cn.haiwai.newsnotification.web.controller;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import cn.haiwai.newsnotification.manage.response.Response;
import cn.haiwai.newsnotification.manage.util.VerifyCodeUtil;
import cn.haiwai.newsnotification.service.UserBO;
import cn.haiwai.newsnotification.service.UserService;

/**
 * 用户控制类，处理用户登录、注册、刷新验证码操作
 * 
 * @author lh
 * @time 2017年10月25日
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired
	private UserService userService;

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String registerBefor(ModelMap model) {
		System.out.println("访问到页面!");
		model.addAttribute("user", new UserBO());
		return "fore/register";
	}

	/**
	 * 注册控制器，需要验证验证码是否正确
	 * 
	 * @param user
	 * @param verifyCode
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public Response register(@ModelAttribute UserBO user, @RequestParam("verifyCode") String verifyCode, ModelMap model,
			HttpServletRequest request) {
		String verifyCodeSession = (String) request.getSession().getAttribute("verifyCode");
		if (!verifyCodeSession.equalsIgnoreCase(verifyCode)) {
			return Response.failure("验证码错误!");
		}

		
		try {
			if (userService.register(user)) {
				// 注册成功
				return Response.success();
			} else {
				return Response.failure("注册失败，未知错误！");
			} 
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("注册失败！{}",e);
			return Response.failure("注册失败，未知错误！");
		}

	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginBefor(ModelMap model, HttpServletRequest request) {
		model.addAttribute("user", new UserBO());
		//// 这里为了解决权限拦截ajax请求时，登录成功后返回到原网页的问题
		String previousUri = request.getParameter("previousUri");
		if (previousUri != null) {
			model.addAttribute("previousUri", previousUri);
		}
		return "back/admin/login";
	}

	/**
	 * 登录
	 * 
	 * @param name
	 * @param password
	 * @param request
	 * @param model
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@RequestParam("name") String name, @RequestParam("password") String password,
			HttpServletRequest request, ModelMap model, HttpServletResponse response)
			throws UnsupportedEncodingException {
		UserBO user = new UserBO();
		user.setName(name);
		user.setPassword(password);
		UserBO newUser = userService.verificationUser(user);
		// 验证user用户名密码是否匹配
		if (newUser != null) {
			logger.info("用户:{}登录了系统！", newUser.getName());
			HttpSession session = request.getSession();
			session.setAttribute("user", newUser);
			String previousUri = request.getParameter("previousUri");// previousUri是登录页面隐藏表单
			// 如果是从其他链接跳转到登录界面的，则登录成功之后返回登录之前的页面
			if (previousUri == null || previousUri.equals("")) {
				return "redirect:/admin/index";
			} else {
				// 截取掉项目前缀，如/shop/forePermission/addOderItem
				// 截取后为/forePermission/addOderItem
				return "redirect:" + previousUri.substring(request.getContextPath().length());
			}
		} else {
			model.addAttribute("user", user);
			model.addAttribute("message", "用户名或者密码错误！");
			return "back/admin/login";

		}

	}

	private final JsonParser jsonParser = new JsonParser();

	/**
	 * 前台通过ajax传入name，后台判定该name是否可以注册 因为可能包含特殊字符@，直接传入字符串后端出现乱码，所以前台转换成json格式，传入后台
	 * 使用Gson解析传入的json数据
	 * 
	 * @param email
	 * @param response
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/isexist", method = RequestMethod.POST)
	public Response detection(@RequestBody String name, HttpServletResponse response, HttpServletRequest request) {
		JsonElement element = jsonParser.parse(name);
		JsonObject jsonObj = element.getAsJsonObject();
		name = jsonObj.get("name").toString();
		// System.out.println("执行成功！打印出:"+email);
		// Gson取出的值带有“”号，导致传入数据库的参数多了个“”,所以这里把双引号截取掉
		name = name.substring(name.indexOf("\"") + 1, name.lastIndexOf("\""));
		UserBO user = userService.getUserByName(name);
		if (user == null) {
			return Response.success(null, "用户名可以使用!");
		} else {
			return Response.failure("用户" + name + "已经存在!");
		}

	}

	/**
	 * 前台ajax访问后台，看用户是否登录
	 */
	@ResponseBody
	@RequestMapping(value = "/userExist", method = RequestMethod.GET)
	public Response userExist(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		if (session.getAttribute("user") != null) {
			return Response.success(session.getAttribute("user"), "用户已经登录");
		} else {
			return Response.failure("用户未登录!");

		}

	}

	/**
	 * 刷新验证码操作
	 * 
	 * @param response
	 * @param request
	 */
	@RequestMapping(value = "/verifyCode/{random}", method = RequestMethod.GET)
	public void verifyCode(HttpServletResponse response, HttpServletRequest request) {
		String verifyCode = VerifyCodeUtil.generateVerifyCode(4);
		request.getSession().setAttribute("verifyCode", verifyCode);
		VerifyCodeUtil.excuteVericode(verifyCode, response);
		// return "fore/verifyCode";
	}

	/**
	 * 前台验证用户录入的验证码是否正确
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/detectVerifyCode", method = RequestMethod.GET)
	public Response detectVerifyCode(@RequestParam("verifyCode") String verifyCode, HttpServletRequest request) {
		// response是否可以存在ConcurrentHashMap中，以session为key
		if (verifyCode == null) {
			return Response.failure("", "验证码不能为空！");
		}
		String code = (String) request.getSession(false).getAttribute("verifyCode");
		if (code == null) {
			return Response.failure("", "后台系统出现异常！验证码不存在");
		}
		if (code.equalsIgnoreCase(verifyCode)) {
			// 验证码输入正确
			return Response.success();
		} else {
			// 验证码输入错误
			return Response.failure("", "验证码错误！");
		}
	}

	/**
	 * 注销用户
	 * 
	 * @return
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (session.getAttribute("user") != null) {
			session.removeAttribute("user");
		}
		return "redirect:/user/login";
	}
	@ResponseBody
	@RequestMapping(value="/resetPassword",method=RequestMethod.PUT)
	public Response resetPassword(@RequestBody String password,HttpServletRequest request ){
		JsonElement element = jsonParser.parse(password);
		JsonObject jsonObj = element.getAsJsonObject();
		String oldPassword = jsonObj.get("oldPassword").toString();
		String newPassword = jsonObj.get("newPassword").toString();
		// System.out.println("执行成功！打印出:"+email);
		// Gson取出的值带有“”号，导致传入数据库的参数多了个“”,所以这里把双引号截取掉
		oldPassword = oldPassword.substring(oldPassword.indexOf("\"") + 1, oldPassword.lastIndexOf("\""));
		newPassword = newPassword.substring(newPassword.indexOf("\"") + 1, newPassword.lastIndexOf("\""));
		UserBO user=(UserBO)request.getSession(false).getAttribute("user");
		try {
			if (userService.resetPassword(oldPassword, newPassword, user))
				return Response.success();
			else
				return Response.failure();
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("用户{}更新密码失败！{}",user.getName(),e);
			return Response.failure();
		}
	}

}
