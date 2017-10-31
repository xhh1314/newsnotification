package cn.haiwai.newsnotification.manage.response;

/**
 * 封装 Restful消息 
 * @author lh
 * @time 2017年10月25日
 * @version 1.0
 */
public class Meta {
	private final boolean success;
	private final String message;
	public Meta(boolean success, String message) {
		this.success = success;
		this.message = message;
	}
	public boolean isSuccess() {
		return success;
	}
	public String getMessage() {
		return message;
	}
	
}
