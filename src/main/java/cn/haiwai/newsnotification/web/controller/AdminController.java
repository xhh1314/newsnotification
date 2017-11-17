package cn.haiwai.newsnotification.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import cn.haiwai.newsnotification.manage.AbstractPage;
import cn.haiwai.newsnotification.manage.response.Response;
import cn.haiwai.newsnotification.service.ContentBO;
import cn.haiwai.newsnotification.service.ContentService;
import cn.haiwai.newsnotification.service.TagBO;

/**
 * 后台管理控制类 所有文章操作都在这里转发
 * 
 * @author lh
 * @date 2017年10月27日
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/admin")
public class AdminController {
	@Autowired
	private ContentService cs;

	/**
	 * 后台主页
	 * 
	 * @param model
	 * @return 返回到后台主页-即列表页
	 */
	@RequestMapping(value = "/index")
	public String index(ModelMap model,HttpServletRequest request) {
		String pageNumberFlag=request.getParameter("pageNumberFlag");
		AbstractPage page = AbstractPage.getPageInstance(1, 0, 0);
		List<ContentVO> contents = cs.listContentsByPage(page, "assign");
		model.addAttribute("contents", contents);
		model.addAttribute("page", page);
		if(pageNumberFlag!=null)
			model.addAttribute("pageNumberFlag",pageNumberFlag);
		return "back/admin/index";
	}

	/**
	 * 分页查询content
	 * 
	 * @param model
	 * @param action
	 * @param beginPage
	 * @param endPage
	 * @param currentPage
	 * @return 返回到后台主页-即列表页
	 */
	@RequestMapping(value = "/contentPage")
	public String contentPage(ModelMap model,@RequestParam("beginPage") int beginPage, @RequestParam("endPage") int endPage,
			@RequestParam("currentPage") int currentPage,HttpServletRequest request) {
		 String action=request.getParameter("action");
		 //处理掉线重新登录后没有值得问题
		 if(action==null || "".equals(action)){
			 action="assign";
		 }
		AbstractPage page = AbstractPage.getPageInstance(currentPage, beginPage, endPage);
		List<ContentVO> contents = cs.listContentsByPage(page, action);
		model.addAttribute("contents", contents);
		model.addAttribute("page", page);
		return "back/admin/index";
	}

	/**
	 * @return 新增content视图
	 */
	@RequestMapping(value = "/contentEdit")
	public String contentEdit(ModelMap model,HttpServletRequest request) {
		String pageNumberFlag=request.getParameter("pageNumberFlag");
		if(pageNumberFlag!=null)
			model.addAttribute("pageNumberFlag",pageNumberFlag);
		List<TagBO> tags;
		if ((tags = cs.listAllTag()) != null)
			model.addAttribute("tags", tags);
		return "back/admin/content_edit";
	}

	/**
	 * 更新content之前渲染视图
	 * 
	 * @param cid
	 * @param model
	 * @return 返回到更新编辑文章页面
	 */
	@RequestMapping(value = "/updateContent/{cid}")
	public String updateContent(@PathVariable("cid") String cid, ModelMap model) {
		ContentBO contentBo = cs.getContent(Integer.parseInt(cid));
		if (contentBo == null) {
			return "back/comm/error_404";
		}
		List<TagBO> tags;
		if ((tags = cs.listAllTag()) != null)
			model.addAttribute("tags", tags);
		model.addAttribute("content", new ContentVO(contentBo));
		return "back/admin/content_edit";
	}

	/**
	 * 匹配日期的正则
	 */
	private final String datePatter = "^[2][0]\\d{2}\\-\\d{2}\\-\\d{2}$";

	/**
	 * 保存content
	 * 
	 * @param content
	 * @return 返回json
	 */
	@RequestMapping(value = "/saveContent", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Response saveContent(@RequestBody ContentVO content) {
		StringBuilder message = new StringBuilder();
		if (!content.getReceiveTime().matches(datePatter)) {
			message.append("日期格式不正确;");
		}
		if (!StringUtils.hasText(content.getTitle())) {
			message.append("标题不能为空;");
		}
		if (!StringUtils.hasText(content.getTags())) {
			message.append("标签不能为空！");
		}
		if (content.getStatus() == 1 && content.getStatus() != 1) {
			message.append("文章状态不正确！请刷新页面!");
		}
		if (message.length() > 1) {
			String msg = message.toString();
			// 截取掉最后一个分号
			if (msg.endsWith(";")) {
				msg = msg.substring(0, msg.length() - 1);
			}
			return Response.failure(msg);
		}
		ContentBO b = cs.saveContent(new ContentBO(content));
		if (b == null) {
			return Response.failure("保存失败！请稍后重试");
		}
		b.setContent(null);
		return Response.success(b, "保存成功！");
	}

	/**
	 * 查看一条content
	 * 
	 * @param cid
	 * @param model
	 * @return 返回到前台的文章展示页面
	 */
	@RequestMapping(value = "/getContent/{cid}")
	public String getContent(@PathVariable("cid") String cid, ModelMap model) {
		ContentBO contentBo = cs.getContent(Integer.parseInt(cid));
		if (contentBo == null) {
			return "back/comm/error_404";
		}
		model.addAttribute("content", contentBo);
		return "content";
	}

	/**
	 * 更新文章状态
	 * 
	 * @param cid
	 * @param status
	 * @return json
	 */
	@RequestMapping(value = "/updateStatus")
	@ResponseBody
	public Response updateStatus(@RequestParam("cid") String cid, @RequestParam("status") String status) {
		if (cid == null || status == null) {
			return Response.failure("id 或者status 参数缺失！");
		}
		if (Integer.parseInt(status) != 0 && Integer.parseInt(status) != 1) {
			return Response.failure("status字段只能是0或者1");
		}
		if (cs.updateContentStatus(Integer.parseInt(cid), Integer.parseInt(status))) {
			return Response.success();
		} else {
			return Response.failure();
		}
	}

	/**
	 * 根据cid删除一条content记录
	 * 
	 * @param cid
	 * @return json
	 */
	@RequestMapping(value = "/deleteContent/{cid}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response deleteContent(@PathVariable("cid") String cid) {
		if (cs.deleteContent(cid)) {
			return Response.success();
		} else {
			return Response.failure();
		}
	}

	/**
	 * @return 返回登录页面
	 */
	@RequestMapping(value = "/login")
	public String login() {
		return "back/admin/login";
	}
	
	@RequestMapping(value="/ueditor")
	public String UeditorTest(){
		
		return "back/admin/ueditor";
	}
	/**
	 * 列示所有tag
	 * @param model
	 * @return tag集合
	 */
	@RequestMapping(value="/listTag")
	public String ListTag(ModelMap model,HttpServletRequest request){
		String pageNumberFlag=request.getParameter("pageNumberFlag");
		if(pageNumberFlag!=null)
			model.addAttribute("pageNumberFlag",pageNumberFlag);
		List<TagBO> tags=cs.listAllTag();
		if(tags==null)
			return "back/admin/tagManage";
		model.addAttribute("tags",tags);
		return "back/admin/tagManage";
	}

	/**
	 * 更新tag名称
	 * @param tid
	 * @param name
	 * @return json 参数为空 或者 参数包含空格，返回false
	 */
	@RequestMapping(value = "/updateTag/{tid}",method=RequestMethod.PUT)
	@ResponseBody
	public Response updateTag(@PathVariable("tid") String tid,@RequestParam String name) {
		if (tid == null || name == null)
			return Response.failure("参数不能为空！");
		if(StringUtils.containsWhitespace(name))
			return Response.failure("一个标签名称不能包含空格！");
		
		if (cs.updateTag(tid, name))
			return Response.success();
		else
			return Response.failure();
	}
	
	/**
	 * 删除tag
	 * @param tid
	 * @return json
	 */
	@RequestMapping(value = "/deleteTag/{tid}",method=RequestMethod.DELETE)
	@ResponseBody
	public Response deleteTag(@PathVariable("tid") String tid){
		if (tid == null)
			return Response.failure("参数不能为空！");
		if(cs.getTagFromContentTagTable(tid)!=null)
			return Response.failure("标签已经被引用，不能删除！");
		
		if(cs.deleteTag(tid))
			return Response.success();
		
			

			return Response.failure(); 
		
	}
	/**
	 * 接收一个String 字符串
	 * @param name
	 * @return tag列表
	 */
	@RequestMapping(value="/addTag")
	public String addTag(@RequestParam String name){
		if(!StringUtils.hasText(name))
			return "forward:/listTag";
	     cs.addTag(name);
		return "forward:listTag";
	}
	
	/**
	 * 后台不正确的地址，全部转发到后台首页转发
	 * @param name
	 * @return
	 */
	@RequestMapping(value="/")
	public String adminForward(){
		return "redirect:/admin/index";
	}

}
