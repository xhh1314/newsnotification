package cn.haiwai.newsnotification.web.controller;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
			logger.error("注册失败！{}", e);
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
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@RequestParam("name") String username, @RequestParam("password") String password, RedirectAttributes redirectAttributes)
			throws UnsupportedEncodingException {
		UserBO user = new UserBO();
		user.setName(username);
		user.setPassword(password);
		UsernamePasswordToken token=new UsernamePasswordToken(username,password);
		//获取当前的subject
		Subject currentUser= SecurityUtils.getSubject();
		try {
			//在调用了login方法后,SecurityManager会收到AuthenticationToken,并将其发送给已配置的Realm执行必须的认证检查
			//每个Realm都能在必要时对提交的AuthenticationTokens作出反应
			//所以这一步在调用login(token)方法时,它会走到MyRealm.doGetAuthenticationInfo()方法中,具体验证方式详见此方法
			logger.info("对用户[" + username + "]进行登录验证..验证开始");
			currentUser.login(token);
			logger.info("对用户[" + username + "]进行登录验证..验证通过");
		}catch(UnknownAccountException uae){
			logger.info("对用户[" + username + "]进行登录验证..验证未通过,未知账户");
			redirectAttributes.addFlashAttribute("message", "未知账户");
		}catch(IncorrectCredentialsException ice){
			logger.info("对用户[" + username + "]进行登录验证..验证未通过,错误的凭证");
			redirectAttributes.addFlashAttribute("message", "密码不正确");
		}catch(LockedAccountException lae){
			logger.info("对用户[" + username + "]进行登录验证..验证未通过,账户已锁定");
			redirectAttributes.addFlashAttribute("message", "账户已锁定");
		}catch(ExcessiveAttemptsException eae){
			logger.info("对用户[" + username + "]进行登录验证..验证未通过,错误次数过多");
			redirectAttributes.addFlashAttribute("message", "用户名或密码错误次数过多");
		}catch(AuthenticationException ae){
			//通过处理Shiro的运行时AuthenticationException就可以控制用户登录失败或密码错误时的情景
			logger.info("对用户[" + username + "]进行登录验证..验证未通过,堆栈轨迹如下");
			ae.printStackTrace();
			redirectAttributes.addFlashAttribute("message", "用户名或密码不正确");
		}
		//验证是否通过
		if(currentUser.isAuthenticated()){
			return "redirect:/admin/index";
		}else {
			return "redirect:/user/login";
		}

	}

	private final JsonParser jsonParser = new JsonParser();

	/**
	 * 前台通过ajax传入name，后台判定该name是否可以注册
	 * 因为可能包含特殊字符@，直接传入字符串后端出现乱码，所以前台转换成json格式，传入后台 使用Gson解析传入的json数据
	 *
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
	public String logout(HttpServletRequest request,RedirectAttributes redirectAttributes ) {
		//使用权限管理工具进行用户的退出，跳出登录，给出提示信息
		SecurityUtils.getSubject().logout();
		redirectAttributes.addFlashAttribute("message", "您已安全退出");
		return "redirect:/user/login";
	}

	@ResponseBody
	@RequestMapping(value = "/resetPassword", method = RequestMethod.PUT)
	public Response resetPassword(@RequestBody String password, HttpServletRequest request) {
		JsonElement element = jsonParser.parse(password);
		JsonObject jsonObj = element.getAsJsonObject();
		String oldPassword = jsonObj.get("oldPassword").toString();
		String newPassword = jsonObj.get("newPassword").toString();
		// System.out.println("执行成功！打印出:"+email);
		// Gson取出的值带有“”号，导致传入数据库的参数多了个“”,所以这里把双引号截取掉
		oldPassword = oldPassword.substring(oldPassword.indexOf("\"") + 1, oldPassword.lastIndexOf("\""));
		newPassword = newPassword.substring(newPassword.indexOf("\"") + 1, newPassword.lastIndexOf("\""));
		UserBO user = (UserBO) request.getSession(false).getAttribute("user");
		try {
			if (userService.resetPassword(oldPassword, newPassword, user))
				return Response.success();
			else
				return Response.failure();
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("用户{}更新密码失败！{}", user.getName(), e);
			return Response.failure();
		}
	}
	@RequestMapping(value = "/403")
	public String get403(){
		return "common/403";
	}

}
