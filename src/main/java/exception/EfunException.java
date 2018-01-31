package exception;

/**
 * 自定义异常
 * 
 * @author acel
 * @date 2017年10月27日 下午10:11:27
 */
public class EfunException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
    private String msg;
    private String code = "500";
    
    public EfunException(String msg) {
		super(msg);
		this.msg = msg;
	}
	
	public EfunException(String msg, Throwable e) {
		super(msg, e);
		this.msg = msg;
	}
	
	public EfunException(String msg, String code) {
		super(msg);
		this.msg = msg;
		this.code = code;
	}
	
	public EfunException(String msg, String code, Throwable e) {
		super(msg, e);
		this.msg = msg;
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	
}
