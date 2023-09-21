<%@ page import="com.omnilab.templatekotlin.domain.item.ItemType" %><%--
  Created by IntelliJ IDEA.
  User: milvus
  Date: 2023-07-20
  Time: 오전 9:09
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE HTML>
<html>
<head>
    <title>상점</title>
</head>
<body>
<div class="container col-5" style="text-align:center">

    <h1>상점</h1>
    <br/>

    <div>
        <div>
            <%-- 이거 --%>
            <form class="row g-3">
                <input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }" />


                <div class="form-floating mb-3 col-sm-5">
                    <input type="text" name="itemName" class="form-control" placeholder="상품명"/>
                    <label>상품명</label>
                </div>

                <div class="form-floating col-sm-5">
                    <select name="itemType" class="form-select" id="floatingSelect">
                        <option value=""> ALL </option>

                        <!-- var는 변수명, items는 반복할 객체명 enum인 itemType을 forEach시키는 방법-->
                        <c:forEach var="type" items="<%= ItemType.values()%>">
                            <%-- 해당 옵션이 선택될 때 서버로 제출되는 값을 명시 --%>
                            <option value="${type}">${type}</option>
                        </c:forEach>

                    </select>
                    <label for="floatingSelect">상품 타입</label>
                </div>


                <button type="submit" class="btn btn-secondary mb-3 col-sm-2">검색</button>
            </form>
        </div>
        <hr/>


        <div style="height : 480px">
            <div class="row row-cols-1 row-cols-md-4 g-4">
                <!-- boardList.content라고 해서 검색할 때 items에 있는 것들을 모두 출력한 것-->
                <c:forEach var="item" items="${items.content}">
                    <div class="col">

                        <a class="card" style="width: 100%; height: 100%;" href="/item/info/${item.id}/all">
                            <span style="height:60%">


<%--                                <img src="/img/<c:out value="${item.saveName}" />" class="card-img-top" style="width: 100%; height: auto;">--%>
                                <img src="/img/${item.saveName}" class="card-img-top" style="width: 100%; height: auto;">
<%--                                <img src="<c:url value='/static/css/images/images?id=${item.id}'/>" class="card-img-top">--%>

                            </span>

                            <div class="card-body">
                                <br>
                                <p class="card-title"><c:out value="${item.name}" /></p>
                            </div>
                        </a>
                    </div>
                </c:forEach>
            </div>
        </div>

        <br/>



        <%--<div class="btn-group me-2" role="group" aria-label="">
            <ul class="pagination">

                <!-- 이전과 처음으로 -->
                <li class="page-item">
                    <a class="page-link" href="/items?page=${startPage}" aria-label="Previous">
                        <span aria-hidden="true"> << </span> <!-- 줄바꿈 X -->
                    </a>
                </li>

                <li class="page-item">
                    <a class="page-link" href="/items?page=${nowPage - 1}" aria-label="Previous">
                        <span aria-hidden="true"> < </span>
                    </a>
                </li>


                <c:forEach var="page" begin="${startPage}" end="${endPage}">
                    <li class="page-item">
                        <c:choose>
                            <c:when test="${boardList.pageable.pageNumber+1 != page}">
                                <a type="button" class="btn btn-outline-secondary"
                                   href="/items?page=${page}"/>${page}</a>
                            </c:when>

                            <c:otherwise>
                                <strong type="button" class="btn btn-outline-secondary"
                                        style="color:red">${page}</strong>
                            </c:otherwise>

                        </c:choose>
                    </li>
                </c:forEach>


                <!-- 다음과 마지막으로 -->
                <li class="page-item">
                    <a class="page-link" href="/items?page=${nowPage + 1}" aria-label="Next">
                        <span aria-hidden="true"> > </span>
                    </a>
                </li>

                <li class="page-item">
                    <a class="page-link" href="/items?page=${endPage}" aria-label="Next">
                        <span aria-hidden="true"> >> </span> <!-- 줄바꿈 X -->
                    </a>
                </li>

            </ul>
        </div>--%>
        <div class="btn-group me-2" role="group" aria-label="">
            <ul class="pagination">

                <c:if test="${not boardList.first}">
                    <li class="page-item">
                        <a class="page-link" href="/items?page=1">
                            <span aria-hidden="true"> << </span> <!-- 줄바꿈 X -->
                        </a>
                    </li>
                </c:if>

                <c:if test="${not boardList.first}">
                    <li class="page-item">
                        <a class="page-link" href="/items?page=${nowPage - 1}">
                            <span aria-hidden="true"> < </span> <!-- 줄바꿈 X -->
                        </a>
                    </li>
                </c:if>


                <c:forEach var="page" begin="${startPage}" end="${endPage}">
                    <li class="page-item">
                        <c:choose>
                            <c:when test="${boardList.pageable.pageNumber+1 != page}">
                                <a type="button" class="btn btn-outline-secondary"
                                   href="/items?page=${page}">${page}</a>
                            </c:when>

                            <c:otherwise>
                                <strong type="button" class="btn btn-outline-secondary"
                                        style="color:red">${page}</strong>
                            </c:otherwise>

                        </c:choose>
                    </li>
                </c:forEach>

                <c:if test="${not boardList.last}">
                    <li class="page-item">
                        <a class="page-link" href="/items?page=${nowPage + 1}">
                            <span aria-hidden="true"> > </span> <!-- 줄바꿈 X -->
                        </a>
                    </li>
                </c:if>
                <c:if test="${not boardList.last}">
                    <li class="page-item">
                        <a class="page-link" href="/items?page=${boardList.totalPages}">
                            <span aria-hidden="true"> >> </span> <!-- 줄바꿈 X -->
                        </a>
                    </li>
                </c:if>
            </ul>
        </div>

    </div>


</div>
</body>
</html>