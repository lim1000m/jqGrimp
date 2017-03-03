package com.ese.config.exception;

import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import com.sun.istack.internal.logging.Logger;

/**
 * Global Exception Processor
 * @File Name : ExceptionController.java
 * @Project Name : DMS
 * @pakage Name : com.ese.config.exception
 * @create Date : 2015. 7. 22.
 * @explain : 
 * @made by : GOEDOKID
 */
@ControllerAdvice
public class ExceptionController {
	
	Logger logger = Logger.getLogger(ExceptionController.class);
	
	/**
	 * Exception Message Return
	 * @Method Name : exception
	 * @create Date : 2015. 7. 23.
	 * @made by : GOEDOKID
	 * @explain : 
	 * @param : 
	 * @return : GeneralException
	 */
	@ExceptionHandler(Exception.class)
	public @ResponseBody JSONObject exception(Exception ex) {
		JSONObject exceptionObject = new JSONObject();
		exceptionObject.put("errMsg", ex.getMessage());
		
		logger.log(null, "DEBUG", ex.toString());
		
		return exceptionObject;
	}
}
