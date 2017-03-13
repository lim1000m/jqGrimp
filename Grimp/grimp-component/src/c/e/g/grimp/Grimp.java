package c.e.g.grimp;

import java.lang.reflect.Field;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import c.e.g.annotation.Gentity;
import c.e.g.annotation.Grider;
import c.e.g.annotation.Grider.Format;
import c.e.g.annotation.Grider.Sort;
import c.e.g.annotation.config.Gid;
import c.e.g.domain.Grivo;
import c.e.g.exception.GrimpExceptionHandler;
import c.e.g.exception.GrimpExceptionHandler.GrimpError;
import c.e.g.grimp.extend.GrimpUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @File Name : GrimpUtil.java
 * @Project Name : grimp
 * @package Name : grid.xGrid
 * @create Date : 2016. 2. 19.
 * @explain : Domain 객체의 Annotation 정보는 토대로 Grid,Tree에 적합한 Json,Xml 형태로 생성
 * @made by : "GOEDOKID"
 * @version 0.9
 * @since 수정자/수정내용/날짜
 * @see 김영훈/grimp Grid Util 생성/2016.02.18
 */
@Component
public class Grimp extends GrimpUtil {
	
	//데이터를 화면에 출력할 형태
	public enum ExprType {TREE, GRID};
	//데이터 포맷 형태
	public enum DataType {JSON, XML};
	
	//각각의 형태에 대한 Default 값 설정
	private ExprType exprType = ExprType.GRID;
	private DataType dataType = DataType.JSON;

	/**
	 * @Component를 통해서 등록될때 사용하는 생서앚 
	 */
	public Grimp() {}
	
	/**
	 * Spring Config에  @Bean설정을 통해 등록될때 사용할 생성자
	 * @param message
	 */
	public Grimp(MessageSource message) {
		this.message = message;
	}

	/**
	 * @Bean생성을 통해서 WebApplicationContext를 생성자로 받을 경우
	 * @param wac
	 */
	public Grimp(WebApplicationContext wac) {
		this.wac = wac;
		this.message = (MessageSource) wac.getBean("messageSource");
	}
	
	/**
	 * @Method Name : headerBuilder
	 * @create Date : 2016. 2. 24.
	 * @made by : "GOEDOKID"
	 * @explain : MessageSource를 직접 전달해서 사용 
	 * @param : Class<?> cls(해더 정보를 추출할 클래스), MessageSource message, Locale locale
	 * @return : String
	 */
	public String buildGrimpHeader(Class<?> cls, MessageSource message, Locale locale) {
		this.message = message;
		this.locale = locale;
		String buildStr = "";
		
		try {
			buildStr = buildHeader(cls).toString();
		} catch (GrimpExceptionHandler e) {
			e.printStackTrace();
		}
		return buildStr;
	}
	
	/**
	 * @Method Name : headerBuilder
	 * @create Date : 2016. 2. 24.
	 * @made by : "GOEDOKID"
	 * @explain : @Bean설정에 의해서 MessageSource가 등록되었을 경우 사용
	 * @param : Class<?> cls, Locale locale
	 * @return : String
	 */
	public String buildGrimpHeader(Class<?> cls, Locale locale) {
		this.locale = locale;
		String buildStr = "";
		
		try {
			buildStr = buildHeader(cls).toString();
		} catch (GrimpExceptionHandler e) {
			e.printStackTrace();
		}
		return buildStr;
	}
	
	/**
	 * @Method Name : buildGrimp
	 * @create Date : 2016. 2. 19.
	 * @made by : "GOEDOKID"
	 * @explain : Domain 형태를 통해 Grid 데이터를 조합할 경우
	 * 			  Tree,Grid 분기 및 각각의 데이터를 JSON, XML 형태로 가져올지 설정
	 *            데이터 형태 기본값(ExprType.GRID, DataType.JSON)
	 * @param : Grivo grino, Class<?> cls          
	 * @return : JSONObject
	 */	
	public JSONObject buildGrimp(Grivo grivo, Class<?> cls) {
		
		JSONObject buildStr = new JSONObject(); 
		try {
			buildStr = buildTypeCheck(grivo, cls);
		} catch (GrimpExceptionHandler e) {
			e.printStackTrace();
		}
		
		return buildStr;
	}
	
	/**
	 * @Method Name : buildGrimp
	 * @create Date : 2016. 2. 19.
	 * @made by : "GOEDOKID"
	 * @explain : Domain 형태를 통해 Grid 데이터를 조합할 경우
	 * 			  Tree,Grid 분기 및 각각의 데이터를 JSON, XML 형태로 가져올지 설정
	 *            데이터 형태 기본값(ExprType.GRID, DataType.JSON)
	 * @param : Grivo grivo          
	 * @return : JSONObject
	 * @throws GrimpExceptionHandler 
	 */
	public JSONObject buildGrimp(Grivo grivo) {
		
		JSONObject buildStr = new JSONObject(); 
		try {
			buildStr = buildTypeCheck(grivo , null);
		} catch (GrimpExceptionHandler e) {
			e.printStackTrace();
		}
		return buildStr;
	}
	
	/**
	 * @Method Name : buildGrimp
	 * @create Date : 2016. 2. 19.
	 * @made by : "GOEDOKID"
	 * @explain : Domain 형태를 통해 Grid 데이터를 조합할 경우
	 * 			  Tree,Grid 분기 및 각각의 데이터를 JSON, XML 형태로 가져올지 설정
	 *            데이터 형태 기본값(ExprType.GRID, DataType.JSON)
	 * @param : Grivo grivo          
	 * @return : String
	 * @throws GrimpExceptionHandler 
	 */
	public String buildGrimpToStr(Grivo grivo) {
		
		String buildStr = ""; 
		try {
			buildStr = buildTypeCheck(grivo , null).toString();
		} catch (GrimpExceptionHandler e) {
			e.printStackTrace();
		}
		return buildStr;
	}
	
	/**
	 * @Method Name : buildGrimp
	 * @create Date : 2016. 2. 19.
	 * @made by : "GOEDOKID"
	 * @explain : Domain 형태를 통해 Grid 데이터를 조합할 경우
	 * 			  Tree,Grid 분기 및 각각의 데이터를 JSON, XML 형태로 가져올지 설정
	 *            데이터 형태 기본값(ExprType.GRID, DataType.JSON)
	 * @param : Grivo grino, Class<?> cls          
	 * @return : String
	 */	
	public String buildGrimpToStr(Grivo grivo, Class<?> cls) {
		
		String buildStr = ""; 
		try {
			buildStr = buildTypeCheck(grivo, cls).toString();
		} catch (GrimpExceptionHandler e) {
			e.printStackTrace();
		}
		return buildStr;
	}
	
	/**
	 * @Method Name : buildTypeCheck
	 * @create Date : 2016. 3. 4.
	 * @made by : "GOEDOKID"
	 * @explain : 원하는 타입에 따라 데이터 조합 형태 분기 
	 * @param : Grivo grivo, Class<?> cls
	 * 			Domain 형태일 경우 : buildTypeCheck(Grivo grivo)
	 * 	 		Map 형태일 경우 : buildTypeCheck(Grivo grivo, Domain.class)
	 * @return : String
	 * @throws GrimpExceptionHandler 
	 */
	private JSONObject buildTypeCheck(Grivo grivo, Class<?> cls) throws GrimpExceptionHandler {
		
		if(grivo.getPager().containsKey("exprType")) 
			this.exprType = (ExprType) grivo.getPager().get("exprType");
		if(grivo.getPager().containsKey("dataType"))
			this.dataType = (DataType) grivo.getPager().get("dataType");
		
		JSONObject grimpRst = new JSONObject();
		
		try {
			if(!grivo.getIsEmptyGrid()) {
				if(this.exprType.equals(ExprType.GRID)) {
					if(this.dataType.equals(DataType.JSON))
						if(cls != null)
							grimpRst = getJsonToGridMap(grivo, cls);
						else 
							grimpRst = getJsonToGridDomain(grivo);
					else
						grimpRst = getXmlToGrid(grivo);
				} else {
					if(this.dataType.equals(DataType.JSON))
						grimpRst = getJsonToTree(grivo);
					else
						grimpRst = getXmlToTree(grivo);
				}	
			} else {
				if(this.exprType.equals(ExprType.GRID)) {
					if(this.dataType.equals(DataType.JSON))
						grimpRst = getEmptyJson();
					else
						grimpRst =  getEmptyXml();
				} else {
					if(this.dataType.equals(DataType.JSON))
						grimpRst =  getEmptyTreeJson();
					else
						grimpRst =  getEmptyTreeXml();
				}	
			}
			
		
		} catch (InstantiationException e) {
			throw new GrimpExceptionHandler(GrimpError.CIE, e);
		} catch (ClassNotFoundException e) {
			throw new GrimpExceptionHandler(GrimpError.CNFE, e);
		} catch (IllegalAccessException e) {
			throw new GrimpExceptionHandler(GrimpError.IAE, e);
		} catch (NoSuchFieldException e) {
			throw new GrimpExceptionHandler(GrimpError.NSFE, e);
		} catch (NullPointerException e) {
			throw new GrimpExceptionHandler(GrimpError.NPE, e);
		} 
			
		return grimpRst;
	}
	
	/**
	 * @Method Name : buildHeader
	 * @create Date : 2016. 2. 19.
	 * @made by : "GOEDOKID"
	 * @explain : GRID 헤더 정보 생성 
	 * @param : Class<?> cls(Annotation을 설정한 클래스)
	 * @return : String
	 * @throws GrimpExceptionHandler 
	 */
	private JSONArray buildHeader(Class<?> cls) throws GrimpExceptionHandler {
		
		Field[] fields = cls.getDeclaredFields();
		
		JSONArray jsonList = new JSONArray();
		
		Gentity gentity = cls.getAnnotation(Gentity.class);
		
		for (int i = 0; i < fields.length; i++) {
			Grider grider = fields[i].getAnnotation(Grider.class);
			if(grider != null) {
				JSONObject map = new JSONObject();
				map.put("label", getLabelMessage(gentity, grider.label(), fields[i].getName()));
				map.put("name", fields[i].getName());
				map.put("hidden", grider.hidden());
				map.put("align", grider.align());
				map.put("width", grider.width());
				
				if(grider.sort().equals(Sort.none)) map.put("sortable", false); 
				else map.put("sortable", grider.sort());
				
				if(!grider.format().equals(Format.function)) 
					if(!grider.format().equals(Format.text) && !grider.format().equals(Format.customCheckbox))
						if(grider.format().equals(Format.checkbox)) {
							map.put("formatter", grider.format());
							map.put("formatoptions", "{disabled:false}");
							map.put("editoptions", "{ value:'Y:N'}");
						}
				jsonList.add(map);	
			} 
		}
		return jsonList;
	}
	
	/**
	 * @Method Name : getJsonToGrid
	 * @create Date : 2016. 2. 19.
	 * @made by : "GOEDOKID"
	 * @explain : GRID 목록을 JSON 형태로 반환
	 * @param : Grivo grivo 
	 * @return : void
	 * @throws JsonProcessingException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws SecurityException 
	 * @throws FieldNullPointException 
	 * @throws NoSuchFieldException 
	 * @throws ClassNotFoundException 
	 * @throws InstantiationException 
	 */
	private JSONObject getJsonToGridDomain(Grivo grivo) 
			throws IllegalArgumentException, 
				   IllegalAccessException, InstantiationException, ClassNotFoundException, 
				   NoSuchFieldException, NullPointerException { 

		Map<String, Object> grimpMap = new HashMap<String, Object>(); //최종 JSON 형태를 생성하는 grimpMap
		ArrayList<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>(); //데이터 정보를 생성하는 MAP
		Grider grider = null; //Grider Annotation vo 정의
		Gid gid = null; //Grider Annotation vo 정의
		
		ArrayList<?> grivoList = grivo.getGridList(); //데이터(VO), 페이징 정보
		Map<String, Object> grivoPager = grivo.getPager(); //grivo에서 페이징 정보 획득
		
		
		if(grivoList.size() > 0) { //조회된 데이터가 없을 경우
			
			Class<?> cls = grivoList.get(0).getClass();
			Field[] fields = cls.getDeclaredFields(); //데이터 리스트의 첫번째 데이터의 클래스에 정의된 필드를 조회한다.
			String keyVar = getGrimpGid(cls);
			
			for (Object obj :  grivoList) { 
				
				Map<String, Object> jsonMap  = new HashMap<String, Object>();
				ArrayList<String> cellsArr = new ArrayList<String>();
				
				for (int i = 0; i < fields.length; i++) {
					grider = fields[i].getAnnotation(Grider.class);
					gid = fields[i].getAnnotation(Gid.class);
					
					if(gid != null) 
						jsonMap.put("id", getMetaDataValue(obj, fields[i].getName()));
					
					if(grider != null) {
						if(!grider.format().equals(Format.function)) {
							if(grider.format().equals(Format.customCheckbox)) {
								cellsArr.add(getCustomCheckBox(getMetaDataValue(obj, keyVar), fields[i].getName()));
							} else {
								cellsArr.add(getMetaDataValue(obj, fields[i].getName()).toString());
							}
						} else {
							cellsArr.add(getFunction(obj, fields[i].getName(), null));
						}
					}
				}
				jsonMap.put("cell", cellsArr);
				dataList.add(jsonMap);
			}
		}
		
		grimpMap.put("page", Integer.parseInt(grivoPager.get("page").toString())); //현재 페이지
		grimpMap.put("total", Integer.parseInt(grivoPager.get("pageCnt").toString())); //페이지 개수
		grimpMap.put("records", Integer.parseInt(grivoPager.get("totalCnt").toString())); //총 레코드 수
		grimpMap.put("rows", dataList);

		JSONObject grimpResult = new JSONObject();
		grimpResult.putAll(grimpMap);
		
		return grimpResult;
	}
	
	/**
	 * @Method Name : getJsonToGrid
	 * @create Date : 2016. 2. 19.
	 * @made by : "GOEDOKID"
	 * @explain : GRID 목록을 JSON 형태로 반환
	 * @param : Grivo grivo 
	 * @return : void
	 * @throws SecurityException 
	 * @throws NoSuchFieldException 
	 * @throws FieldNullPointException 
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws JsonProcessingException 
	 */
	@SuppressWarnings("unchecked")
	private JSONObject getJsonToGridMap(Grivo grivo, Class<?> cls) 
			throws NoSuchFieldException, InstantiationException, 
				   IllegalAccessException, ClassNotFoundException, GrimpExceptionHandler
				   { 

		Map<String, Object> grimpMap = new HashMap<String, Object>(); //최종 JSON 형태를 생성하는 grimpMap
		ArrayList<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>(); //데이터 정보를 생성하는 MAP
		Grider grider = null; //Grider Annotation vo 정의
		Gid gid = null; //Grider Annotation vo 정의
		
		ArrayList<?> grivoList = grivo.getGridList(); //데이터(VO), 페이징 정보
		Map<String, Object> grivoPager = grivo.getPager(); //grivo에서 페이징 정보 획득
		
		if(grivoList.size() > 0) { //조회된 데이터가 없을 경우
			String keyVar = getGrimpGid(cls);
			for (Object  map :  grivoList) { 
				Map<String, Object> jsonMap  = new HashMap<String, Object>();
				ArrayList<String> cellsArr = new ArrayList<String>();
				
				Map<String, Object> thisMap = (Map<String, Object>) map;
				Field[] fields = cls.getDeclaredFields();
				for (int i = 0; i < fields.length; i++) {
					grider = fields[i].getAnnotation(Grider.class);
					gid = fields[i].getAnnotation(Gid.class);
					
					if(gid != null) 
						jsonMap.put("id", thisMap.get(cnvtDmnToClmn(fields[i].getName())));
					
					if(grider != null) {
						if(!grider.format().equals(Format.function)) {
							if(grider.format().equals(Format.customCheckbox)){ 
								cellsArr.add(getCustomCheckBox(thisMap, fields[i].getName(), keyVar));
							} else {
								cellsArr.add(thisMap.get(cnvtDmnToClmn(fields[i].getName())).toString());
							}
						} else {
							cellsArr.add(getFunction(map, fields[i].getName(), cls));
						}
					}
				}
				jsonMap.put("cell", cellsArr);
				dataList.add(jsonMap);
			}
		}
		
		grimpMap.put("rows", dataList);
		grimpMap.put("page", Integer.parseInt(grivoPager.get("page").toString())); //현재 페이지
		grimpMap.put("total", Integer.parseInt(grivoPager.get("pageCnt").toString())); //페이지 개수
		grimpMap.put("records", Integer.parseInt(grivoPager.get("totalCnt").toString())); //총 레코드 수
		
		JSONObject grimpResult = new JSONObject();
		grimpResult.putAll(grimpMap);
		
		return grimpResult;
	}
	
	/**
	 * @Method Name : getXmlToGrid
	 * @create Date : 2016. 2. 23.
	 * @made by : "GOEDOKID"
	 * @explain : GRID에 XML 형태로 데이터를 리턴
	 * @param : ArrayList<Object> clsList 
	 * @return : String
	 */
	private JSONObject getXmlToGrid(Grivo grivo) {
		return new JSONObject();
	}

	/**
	 * @Method Name : getXmlToTree
	 * @create Date : 2016. 2. 23.
	 * @made by : "GOEDOKID"
	 * @explain : XML 형태로 TREE에 표출
	 * @param : Grivo grivo
	 * @return : JSONObject
	 */
	@Deprecated
	private JSONObject getXmlToTree(Grivo grivo) {
		return new JSONObject();
	}

	/**
	 * @Method Name : getJsonToTree
	 * @create Date : 2016. 2. 23.
	 * @made by : "GOEDOKID"
	 * @explain : JSON 형태로 TREE에 표출 
	 * @param : Grivo grivo)
	 * @return : JSONObject
	 */
	@Deprecated
	private JSONObject getJsonToTree(Grivo grivo) {
		return new JSONObject();
	}
	
	/**
	 * @Method Name : getEmptyJson
	 * @create Date : 2016. 3. 25.
	 * @made by : "GOEDOKID"
	 * @explain : 최초나 어떤 이유에 의해서 빈 Grid를 보여줘야 할 필요가 있을 경우 JSON 형태의 빈 데이터를 리턴한다.
	 * @param : 
	 * @return : String
	 */
	private JSONObject getEmptyJson() throws GrimpExceptionHandler  {
		Map<String, Object> grimpMap = new HashMap<String, Object>(); //최종 JSON 형태를 생성하는 grimpMap
		ArrayList<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>(); //데이터 정보를 생성하는 MAP
		Map<String, Integer> pageMap = new HashMap<String, Integer>(); //페이지 정보를 생성하는 MAP
		pageMap.put("page", 1); 
		pageMap.put("records", 0); 
		pageMap.put("total", 0); 
		grimpMap.put("rows", dataList);
		grimpMap.put("page", pageMap);
		JSONObject mapper = new JSONObject();
		mapper.putAll(grimpMap);
		return mapper;
	}
	
	/**
	 * @Method Name : getEmptyXml
	 * @create Date : 2016. 3. 25.
	 * @made by : "GOEDOKID"
	 * @explain : 빈 Grid를 최초에 호출해야 할 필요가 있을때 XML 형태의 빈 데이터를 리턴한다.
	 * @return : JSONObject
	 */
	@Deprecated
	private JSONObject getEmptyXml() {
		return new JSONObject();
	}
	
	/**
	 * @Method Name : getEmptyTreeXml
	 * @create Date : 2016. 3. 25.
	 * @made by : "GOEDOKID"
	 * @explain : 빈 Tree를 최초에 호출해야 할 필요가 있을때 XML 형태의 빈 데이터를 리턴한다.
	 * @return : JSONObject
	 */
	@Deprecated
	private JSONObject getEmptyTreeXml() {
		return new JSONObject();
	}
	
	/**
	 * @Method Name : getEmptyTreeJson
	 * @create Date : 2016. 3. 25.
	 * @made by : "GOEDOKID"
	 * @explain : 빈 Tree를 최초에 호출해야 할 필요가 있을때 XML 형태의 빈 데이터를 리턴한다.
	 * @return : JSONObject
	 */
	@Deprecated
	private JSONObject getEmptyTreeJson() {
		return new JSONObject();
	}
}
