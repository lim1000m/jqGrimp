package c.e.g.grimp;

import java.util.Map;

/**
 * @File Name : Pager.java
 * @Project Name : grimp
 * @package Name : c.e.g.xGrid
 * @create Date : 2016. 2. 24.
 * @explain : 페이징 처리 
 * @made by : "GOEDOKID"
 */
public class Pager {

	/**
	 * @Method Name : paging
	 * @create Date : 2016. 2. 24.
	 * @made by : "GOEDOKID"
	 * @explain : 페이징 처리 메서드 
	 * @param : int pageNo(페이지 번호), int pageSize(페이지 크기), int totalCnt(조회된 전체크기)
	 * @return : Integer[]
	 *           [0]. startNum 시작번호
	 *           [1]. endNum 종료번호
	 *           [2]. pageCnt 페이지 개수
	 *           [3]. totalCnt 총 로우 수
	 */
	public static Integer[] paging(int pageNo, int pageSize, int totalCnt) {
		Integer[] paging = new Integer[4];
		
		int pageCnt = totalCnt/pageSize;
		if(pageCnt == 0) pageCnt = 1;
		
		int startNum = 1;
		if(pageNo > 1) startNum = (pageNo-1)*pageSize;
		
		int endNum = startNum+pageSize-1;
		
		paging[0] = startNum;
		paging[1] = endNum;
		paging[2] = pageCnt;
		paging[3] = totalCnt;
		paging[4] = pageSize;
		
		return paging;
	}
	
	/**
	 * @Method Name : paging
	 * @create Date : 2016. 2. 24.
	 * @made by : "GOEDOKID"
	 * @explain : 페이징 처리 메서드 
	 * @param : String pageNoStr(페이지 번호), String pageSizeStr(페이지 크기), int totalCnt(조회된 전체크기)
	 * @return : String[]
	 *           [0]. startNum 시작번호
	 *           [1]. endNum 종료번호
	 *           [2]. pageCnt 페이지 개수
	 *           [3]. totalCnt 총 로우 수
	 */
	public static String[] paging(String pageNoStr, String pageSizeStr, int totalCnt) {
		String[] paging = new String[4];
		
		Integer pageNo = Integer.parseInt(pageNoStr);
		Integer pageSize = Integer.parseInt(pageSizeStr);
		
		int pageCnt = totalCnt/pageSize;
		if(pageCnt == 0) pageCnt = 1;
		
		int startNum = 1;
		if(pageNo > 1) startNum = (pageNo-1)*pageSize;
		
		int endNum = startNum+pageSize-1;
		
		paging[0] = Integer.toString(startNum);
		paging[1] = Integer.toString(endNum);
		paging[2] = Integer.toString(pageCnt);
		paging[3] = Integer.toString(totalCnt);
		paging[4] = Integer.toString(pageSize);
		return paging;
	}
	
	/**
	 * @Method Name : paging
	 * @create Date : 2016. 2. 24.
	 * @made by : "GOEDOKID"
	 * @explain : Map<String, Object> 형태의 페이징 처리 
	 * @param : Map<String, String> Map, int totalCnt
	 * @return : Map<String,Object> 
	 *           1. 사용자가 넣은 검색 조건
	 *           2. startNum 시작번호
	 *           3. endNum 종료번호
	 *           4. pageCnt 페이지 개수
	 *           5. totalCnt 총 로우 수
	 */
	public static Map<String, Object> paging(Map<String, Object> map, int totalCnt) {
		
		int pageNo = Integer.parseInt(map.get("page").toString());
		int pageSize = Integer.parseInt(map.get("rows").toString());
		
		int pageCnt = totalCnt/pageSize;
		if(pageCnt == 0) pageCnt = 1;
		if(pageSize*pageCnt < totalCnt) pageCnt++;
		int startNum = 1;
		startNum = ((pageNo-1)*pageSize);
		int endNum = startNum+pageSize;
		if(pageNo > 1) startNum++;
		
		map.put("startNum", startNum);
		map.put("endNum", endNum);
		map.put("pageCnt", pageCnt);
		map.put("totalCnt", totalCnt);
		
		return map;
	}
}
 