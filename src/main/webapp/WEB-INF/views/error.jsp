<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>오류 페이지</title>
</head>
<body style="position: absolute; width: 100%; height: 100%; margin: 0; padding: 0;">
	<div style="width: 100%; text-align: center; padding-top: 10%;">
		[<c:out value="${requestScope['javax.servlet.error.request_uri']}"/>] 
		<c:out value="${requestScope['javax.servlet.error.message']}"/>
		<br/>
		<br/>
		<a href="javascript:history.go(-1)">이전 페이지로 돌아가기</a>
	</div>
</body>
<script>
	$(function(){
		
	});
</script>
</html>