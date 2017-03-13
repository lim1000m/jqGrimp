package com.ese.main.service.mapper;

import java.util.ArrayList;
import java.util.Map;
import org.apache.ibatis.annotations.Select;
import com.ese.domain.Dvsn;

/**
 * @File Name : MainMapper.java
 * @Project Name : DMS
 * @pakage Name : com.ese.main.service.mapper
 * @create Date : 2015. 9. 2.
 * @explain : Mapper 
 * @made by : GOEDOKID
 */
public interface MainMapper {

	
	/**
	 * @Method Name : getDvsnList
	 * @create Date : 2016.02.23
	 * @made by : GOEDOKID
	 * @explain : 도면 분야 조회 
	 * @param : 
	 * @return : ArrayList<Dvsn>
	 */

	@Select("SELECT * FROM("
			+ "		SELECT ROW_NUMBER() OVER() AS no, DVSN_CD dvsnCd, DVSN_NM dvsnNm, STD_DVSN_CD stdDvsnCd, ORDR ordr, USE_YN useYn, '20170313200321' as dateTime,'N' as check"
			+ "		FROM EEAM_DVSN_TBL) A "
		    + "WHERE no BETWEEN ${startNum} AND ${endNum} "
		    + "ORDER BY no") 
	ArrayList<Dvsn> getDvsnDomain(Map<String, Object> paramMap);
	
	/**
	 * @Method Name : getDvsnCnt
	 * @create Date : 2016.02.23
	 * @made by : GOEDOKID
	 * @explain : 도면 분야 카운트 
	 * @param : 
	 * @return : int
	 */
	@Select("SELECT count(dvsn_cd) FROM EEAM_DVSN_TBL")
	int getDvsnDomainCnt();
	
	/**
	 * @Method Name : getDvsnList
	 * @create Date : 2016.02.23
	 * @made by : GOEDOKID
	 * @explain : 도면 분야 조회 
	 * @param : 
	 * @return : ArrayList<Map<String, String>>
	 */
	@Select("SELECT dvsn_cd, dvsn_nm, ordr, std_dvsn_cd, use_yn FROM EEAM_DVSN_TBL")
	ArrayList<Map<String, String>> getDvsnMap();
	
	/**
	 * @Method Name : getDvsnCnt
	 * @create Date : 2016.02.23
	 * @made by : GOEDOKID
	 * @explain : 도면 분야 카운트 
	 * @param : 
	 * @return : int
	 */
	@Select("SELECT count(dvsn_cd) FROM EEAM_DVSN_TBL")
	int getDvsnMapCnt();
	
	/**
	 * @Method Name : getDvsnList
	 * @create Date : 2016.02.23
	 * @made by : GOEDOKID
	 * @explain : 도면 분야 조회 
	 * @param : 
	 * @return : ArrayList<Map<String, Object>
	 */
	@Select("SELECT * FROM("
			+ "		SELECT ROW_NUMBER() OVER() AS no, DVSN_CD, DVSN_NM, STD_DVSN_CD, ORDR, USE_YN, ORDR as nowordr, 'N' as check, '20170313200321' as DATE_TIME"
			+ "		FROM EEAM_DVSN_TBL "
			+ "		ORDER BY ordr) A "
		    + "WHERE no BETWEEN ${startNum} AND ${endNum} "
		    + "ORDER BY no") 
	ArrayList<Map<String, Object>> getDvsnDomainMap(Map<String, Object> paramMap);
	
 }