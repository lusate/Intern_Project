<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<c:set var="req" value="${pageContext.request}" />
<c:choose>
	<c:when test="${header['X-Forwarded-Proto'] eq 'https'}">
		<c:set var="baseURL" value="https://${req.serverName}${req.contextPath}" />
	</c:when>
	<c:otherwise>
		<c:set var="baseURL" value="${req.scheme}://${req.serverName}:${req.serverPort}${req.contextPath}" />
	</c:otherwise>
</c:choose>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
		<title>::TEST</title>
		<script type="text/javascript" src="/js/jquery-2.1.3.min.js"></script>
		<script type="text/javascript" src="/js/common.js?v=0.01"></script>
		<script type="text/javascript" src="/js/jquery.validate.min.js"></script>
	</head>
	<body>
		index body
		<br/>
		<br/>
		<c:if test="${index}">
			<tiles:insertAttribute name="${index_view}" />
		</c:if>
		<br/>
		<c:if test="${index2}">
			<tiles:insertAttribute name="${index2_view}" />
		</c:if>
	</body>
</html>