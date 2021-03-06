<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/lib/jqgrid/css/ui.jqgrid.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/lib/jquery/ui/css/ui-lightness/jquery-ui-1.10.1.custom.css" />
<script type="text/javascript" src="${pageContext.request.contextPath }/js/lib/jquery/jquery-2.1.4.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/lib/jquery/ui/js/jquery-ui-1.10.1.custom.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/lib/jqgrid/js/jquery.jqGrid.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/lib/jqgrid/js/i18n/grid.locale-kr.js"></script>

<script type="text/javascript">

$(document).ready(function() {
	uf_initGrid();
});

/**********************************************************
내	용	: jQuery 그리드 로딩 _ 그룹 코드
파라미터	:
리 턴 값	:
참고사항 	:
***********************************************************/
function uf_initGrid() {
	
	$("#gridTbl").jqGrid({
		url : "<c:url value='/getDvsnList.do' />", //VO 객체를 통한 Hibernate로 조회
// 		url : "<c:url value='/grider.do' />", //쿼리의 결과가 Domain인 경우 조회
// 		url : "<c:url value='/mrider.do' />", //쿼리의 결과가 Map인 경우 조회
		datatype : "json" ,
		pager: '#jqGridPager',
		viewrecords : true,
		rowNum : 5,
		width:1200,
		height:600,
		rowList : [5, 10],
		sortname : 'dvsnCd',
		sortorder : "asc",
		postData : {
	   		lang : 'kr'
	   	},   
		loadComplete : function() {
		},
		colModel:${grimpModel}
	});
	
}

/**********************************************************
내	용	: 커스텀 체크박스에 매핑된 버튼 수행
파라미터	:
리 턴 값	:
참고사항 	:
***********************************************************/
function showOrdr(flag) {
	alert(flag);
}
</script>
</head>
<body>
 <table id="gridTbl"></table>
<div id="jqGridPager"></div>	
</body>
</html>