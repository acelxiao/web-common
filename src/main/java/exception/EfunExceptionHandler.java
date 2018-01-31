package exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import utils.R;
import utils.Rcode;

/**
 * 异常处理器
 * 
 * @author acel
 * @date 2017年10月27日 下午10:16:19
 */
@RestControllerAdvice
public class EfunExceptionHandler {
	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 自定义异常
	 */
	@ExceptionHandler(EfunException.class)
	public R handleEfunException(EfunException e){
		R r = new R();
		r.put("code", e.getCode());
		r.put("msg", e.getMessage());

		return r;
	}

	@ExceptionHandler(Exception.class)
	public R handleException(Exception e){
		logger.error(e.getMessage(), e);
		return R.error();
	}

	/**
	 * 参数验证
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public R handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		BindingResult result = e.getBindingResult();
		StringBuffer sb = new StringBuffer();
		int i = 0;
		for (ObjectError error : result.getAllErrors()) {
			String field = result.getFieldErrors().get(i).getField();
			i++;
			String code = error.getDefaultMessage();
			String message = String.format("|%s:%s|", field, code);
			sb.append(message);
		}
		return R.error(Rcode.PARAMS_ERROR[0], sb.toString());
	}

	/**
	 * 参数绑定异常处理
	 */
	@ExceptionHandler(BindException.class)
	public R handleBindException(BindException e) {
		BindingResult result = e.getBindingResult();
		StringBuffer sb = new StringBuffer();
		int i = 0;
		for (ObjectError error : result.getAllErrors()) {
			String field = result.getFieldErrors().get(i).getField();
			i++;
			String code = error.getDefaultMessage();
			String message = String.format("|%s:%s|", field, code);
			sb.append(message);
		}
		return R.error(Rcode.PARAMS_ERROR[0], sb.toString());
	}

}
