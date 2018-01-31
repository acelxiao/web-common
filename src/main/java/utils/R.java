package utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回数据实体
 * 
 * @author acel
 */
public class R extends HashMap<String, Object> {
	private static final long serialVersionUID = 1L;
	
	public R() {
		put("code", Rcode.SUCCESS[0]);
	}
	
	public static R error() {
		return error(Rcode.SYSTEM_ERROR[0], "未知异常，请联系管理员");
	}
	
	public static R error(String message) {
		return error(Rcode.SYSTEM_ERROR[0], message);
	}
	
	public static R error(String code, String message) {
		R r = new R();
		r.put("code", code);
		r.put("message", message);
		return r;
	}

	public static R ok() {
		R r = new R();
		r.put("code", Rcode.SUCCESS[0]);
		r.put("message", "success");
		return r;
	}

	public static R ok(String message) {
		R r = new R();
		r.put("code", Rcode.SUCCESS[0]);
		r.put("message", message);
		return r;
	}

	public static R ok(Object data) {
		R r = new R();
		r.put("code", Rcode.SUCCESS[0]);
		r.put("message", MessageUtil.getMessage(Rcode.SUCCESS[1]));
		r.put("data", data);
		return r;
	}

	public static R ok(String message, Object data) {
		R r = new R();
		r.put("code", Rcode.SUCCESS[0]);
		r.put("message", message);
		r.put("data", data);
		return r;
	}


	public static R signError() {
		R r = new R();
		r.put("code", Rcode.SIGN_ERROR[0]);
		r.put("message", MessageUtil.getMessage(Rcode.SIGN_ERROR[1]));
		return r;
	}

	public static R code(String[] name) {
		R r = new R();
		r.put("code", name[0]);
		r.put("message", MessageUtil.getMessage(name[1]));
		return r;
	}

	public String getCode() {
		return this.get("code").toString();
	}

	public String getMsg() {
		return this.get("message").toString();
	}

	public Object getData() {
		return this.get("data");
	}
}
