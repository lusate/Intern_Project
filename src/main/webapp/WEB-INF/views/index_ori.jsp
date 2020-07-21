<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>오류 페이지</title>
</head>
<body style="position: absolute; width: 100%; height: 100%; margin: 0; padding: 0;">
	<div style="width: 100%; text-align: center; padding-top: 10%;">야호</div>
	<h1>HTML COMPRESSOR</h1>
	<p class="lead">
		<a href="https://code.google.com/p/htmlcompressor/downloads/list" target="_blank">DOWNLOAD</a>
	</p>
	<button>Remove Subheadings</button>
	<input type="text" name="id">
	<input type="text" name="passwrod">
	<input type="button" name="move">
	<%--<c:import url="index2.jsp"></c:import>--%>
	<%--<%@ include file="/WEB-INF/views/index2.jsp" %>--%>
	<%--<jsp:include page="/WEB-INF/views/error.jsp"/>--%>
</body>
</html>