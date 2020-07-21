<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="url">${pageContext.request.requestURL}</c:set>
<c:choose><c:when test="${header['X-Custom-Header'] eq 'https'}"><c:set var="baseURL" value="https://${header['host']}" /></c:when><c:otherwise><c:set var="baseURL" value="${fn:replace(url, pageContext.request.requestURI, pageContext.request.contextPath)}" /></c:otherwise></c:choose>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
		<meta name="apple-mobile-web-app-status-bar-style" content="yes">
		<meta name="apple-mobile-web-app-status-bar-style" content="black" />
		<meta http-equiv="X-UA-Compatible" content="IE=Edge">
		<script type="text/javascript" src="/js/jquery-2.1.3.min.js"></script>
		<script type="text/javascript" src="/js/common.js"></script>
		<script type="text/javascript" src="/js/jquery.validate.min.js"></script>
		<link rel="stylesheet" href="/css/omnilab-common-ui.css?v=0.01" type="text/css" />
		<title>miCRM::스마트 마케팅 솔루션</title>		
		<sitemesh:write property='head' />
	</head>

	<body style="background-image: url('/img/main/backgroundImage/main_background_04.jpg')">
		<sitemesh:write property='body'/>
	</body>
	<div id="loadingCover" class="layer-lv3">
		<div class="spinner">
			<i></i><i></i><i></i><i></i><i></i><i></i><i></i><i></i><i></i><i></i><i></i><i></i>
		</div>
		<div class="loading">NOW LOADING...</div>
	</div>	
	<sitemesh:write property='page.script' />
</html>