package com.ese.main;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import c.e.g.domain.Grivo;
import c.e.g.grimp.Grimp;
import com.ese.config.resolverHandler.RequestMap.RequestMap;
import com.ese.domain.Dvsn;
import com.ese.main.service.MainService;

/**
 * @File Name : MainController.java
 * @Project Name : grimp
 * @pakage Name : com.ese.main
 * @create Date : 2016. 2. 23.
 * @explain : AXISJ GRID 테스트 클래스
 * 			    클래스 단에 RequestMapping을 넣어준 것은 
 * 			  HttpStringConverter 기본 설정이 text/plain charset=ISO-8859이여서 
 * 			    한글 정보가 깨진다. response header의 content-type을 변경한다. 
 *            response content-type이 application/json charset=UTF-8이라면 
 *            제거해도 된다.
 * @made by : "GOEDOKID"
 */
@Controller
@RequestMapping(produces="application/json; charset=utf8") 
public class MainController {
	
	@Autowired
	Grimp grimp;

	@Autowired
	MainService mainService;
	
	@Autowired
	MessageSource message;
	
	/**
	 * @Method Name : main_jq
	 * @create Date : 2016. 3. 11.
	 * @made by : "GOEDOKID"
	 * @explain : 그리드 화면 조회
	 * @param : 
	 * @return : String
	 */
	@RequestMapping(value="/main")
	public String main_jq(
			Model model,
			Locale locale
			) {
		model.addAttribute("grimpModel", grimp.buildGrimpHeader(Dvsn.class, message, locale));
		return "main/main";
	}
	
	/**
	 * @Method Name : grider
	 * @create Date : 2016. 3. 11.
	 * @made by : "GOEDOKID"
	 * @explain : 그리드 목록 조회 ControllerAdvice, ArgumentResolver Spring 설정
	 * @param : RequestMap requestMap
	 * @return : Grivo
	 */
	@RequestMapping(value="/grider")
	public @ResponseBody Grivo grider(
			RequestMap requestMap
			) {
		return mainService.getDvsnDomain(requestMap.getMap());
	}
	
	/**
	 * @Method Name : mrider
	 * @create Date : 2016. 3. 11.
	 * @made by : "GOEDOKID"
	 * @explain : 맵 그리드 목록 조회 ControllerAdvice, ArgumentResolver Spring 설정
	 * @param : RequestMap requestMap
	 * @return : Grivo
	 */
	@RequestMapping(value="/mrider")
	public @ResponseBody Grivo mrider(
			RequestMap requestMap
			) {
		return  mainService.getDvsnMap(requestMap.getMap());
	}
	
	/**
	 * @Method Name : grider
	 * @create Date : 2016. 3. 11.
	 * @made by : "GOEDOKID"
	 * @explain : 그리드 목록 조회
	 * @param : 
	 * @return : String
	 */
	@RequestMapping(value="/griderStr")
	public @ResponseBody String griderStr(
			@RequestParam("page") String page,
			@RequestParam("rows") String rows
			) {
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("page", page);
		paramMap.put("rows", rows);
		
		return grimp.buildGrimpToStr(mainService.getDvsnDomain(paramMap));
	}
	
	/**
	 * @Method Name : mrider
	 * @create Date : 2016. 3. 11.
	 * @made by : "GOEDOKID"
	 * @explain : 맵 그리드 목록 조회
	 * @param : 
	 * @return : String
	 */
	@RequestMapping(value="/mriderStr")
	public @ResponseBody String mriderStr(
			@RequestParam("page") String page,
			@RequestParam("rows") String rows
			) {
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("page", page);
		paramMap.put("rows", rows);
		
		return grimp.buildGrimpToStr(mainService.getDvsnMap(paramMap), Dvsn.class);
	}
	
	/**
	 * @Method Name : getDvsnList
	 * @create Date : 2016. 3. 14.
	 * @made by : "GOEDOKID"
	 * @explain : Hibernate로 조회 
	 * @param : 
	 * @return : String
	 */
	@RequestMapping(value="/getDvsnList")
	public @ResponseBody String getDvsnList(
			@RequestParam("page") String page,
			@RequestParam("rows") String rows,
			@RequestParam("sidx") String sidx,
			@RequestParam("sord") String sord
			) {

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("page", page);
		paramMap.put("rows", rows);
		paramMap.put("sidx", sidx);
		paramMap.put("sord", sord);
		
		Grivo grivo =  mainService.getDvsnList(paramMap);
		
		return grimp.buildGrimpToStr(grivo);
	}
}
