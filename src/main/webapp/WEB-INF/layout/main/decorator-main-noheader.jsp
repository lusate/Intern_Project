<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<c:set var="url">${pageContext.request.requestURL}</c:set>
<c:set var="baseURL" value="${fn:replace(url, pageContext.request.requestURI, pageContext.request.contextPath)}" />
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
		<title>miCRM::<sitemesh:write property='title' /></title>
		<link rel="stylesheet" href="/css/micrm-font-ui.css?v=0.01">
		<link rel="stylesheet" href="/css/micrm-common-style.css?v=0.01">
		<link rel="stylesheet" href="/css/micrm-custom-dialog.css?v=0.01">
		<link rel="stylesheet" href="/css/micrm-custom-select.css?v=0.01">
		<link rel="stylesheet" href="/css/jquery-ui.css?v=0.01">
		<script type="text/javascript" src="/js/jquery-2.1.3.min.js"></script>
		<script type="text/javascript" src="/js/common.js"></script>
		<script type="text/javascript" src="/js/jquery.validate.min.js"></script>
		<script type="text/javascript" src="/js/html5shiv.js"></script>
		<script type="text/javascript" src="/js/jquery-ui.js"></script>
		<script type="text/javascript" src="/js/commonAjax.js"></script>
		<script type="text/javascript" src="/js/customDialog.js"></script>
		<script type="text/javascript" src="/js/customSelect.js"></script>
		<script type="text/javascript" src="/js/jquery.number.js"></script>
		<sitemesh:write property='head' />
		<script type="text/javascript">function linkToOpener(URL){window.opener.location = URL;window.close();}</script>
		<style>html{min-width: none;}</style>
	</head>
	<body>
		<input type="hidden" name="_csrfparameterName" value="${_csrf.parameterName}">
		<input type="hidden" name="_csrfHeaderName" value="${_csrf.headerName }">
		<input type="hidden" name="_csrfToken" value="${_csrf.token }">
		<sitemesh:write property='body' />
		<div id="loadingCover" class="layer-lv3">
		<div class="spinner">
			<i></i><i></i><i></i><i></i><i></i><i></i><i></i><i></i><i></i><i></i><i></i><i></i>
		</div>
		<div class="loading">NOW LOADING...</div>
	</div>
	</body>
	<sitemesh:write property='page.script' />
</html>