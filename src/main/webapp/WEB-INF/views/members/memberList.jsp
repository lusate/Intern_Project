<%--
  Created by IntelliJ IDEA.
  User: milvus
  Date: 2023-07-20
  Time: 오전 9:02
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>MEMBER LIST</title>
</head>
<body>
<div class="container  col-5" style="text-align:center">
    <H1>MEMBER LIST</H1>
    <br/>

    <h2>관리자 전용 페이지</h2>
    <p>관리자 : <sec:authentication property="principal"/></p>


<%--    <a href="http://localhost:8080/excel/download?excelDownload=true">Excel</a>--%>


    <%--    여기--%>
    <form action="/excel/download" method="post">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token }"/>
        <input type="submit" name="itemType" value="Excel Download">
    </form>

    <table class="table">
        <thead>
        <tr class="table-danger">
            <th class="col-3">이름</th>
            <th class="col-3">도시</th>
            <th class="col-3">주소</th>
            <th class="col-3">상세 주소</th>
            <th class="col-3">권한</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${members.content}" var="member">
            <tr>
                <td><c:out value="${member.username}"/></td>
                <td><c:out value="${member.city}"/></td>
                <td><c:out value="${member.street}"/></td>
                <td><c:out value="${member.detail}"/></td>
                <td><c:out value="${member.role}"/></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

</div> <!-- /container -->

</body>
</html>
