<%@ page import="com.omnilab.templatekotlin.domain.DeliveryStatus" %>
<%--
  Created by IntelliJ IDEA.
  User: milvus
  Date: 2023-07-20
  Time: 오전 9:02
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>주문 리스트</title>
    <meta id="_csrf" name="_csrf" content="${_csrf.token}"/>
    <!-- default header name is X-CSRF-TOKEN -->
    <meta id="_csrf_header" name="_csrf_header" content="${_csrf.headerName}"/>

    <style>
        .table{
            margin-left: -80px;
        }
    </style>
</head>
<body>
<div class="container col-5" style="text-align:center">
    <H1>주문 리스트</H1>
    <br/>

    <div>
        <hr/>


        <table class="table" style="width: 750px">
            <thead>
            <tr class="table-success">
                <th>회원명</th>
                <th>상품 이름</th>
                <th>주문가격</th>
                <th>주문수량</th>
                <th>총 가격</th>
                <th>주문 상태</th>
                <th>배송 상태</th>
                <th>날짜</th>
                <th></th>
            </tr>
            </thead>

            <tbody>
                <sec:authorize access="hasRole('ROLE_USER')">
                    <c:forEach var="order" items="${orders}">
                            <c:forEach var="orderItem" items="${order.orderItems}">
                                <tr>
                                    <td><c:out value="${order.member.username}" /></td>
                                    <td><c:out value="${orderItem.item.name}" /></td>
                                    <td><c:out value="${orderItem.orderPrice}" /></td>
                                    <td><c:out value="${orderItem.count}" /></td>
                                    <td><c:out value="${orderItem.totalPrice}" /></td>
                                    <td><c:out value="${order.status}" /></td>

<%--                                    <c:out value="${order.delivery.status}" />--%>
                                    <td>
                                        <span class="badge bg-secondary"><c:if test="${order.delivery.status eq 'WAITING'}"> 배송 준비 </c:if></span>
                                        <span class="badge bg-info"><c:if test="${order.delivery.status eq 'START'}"> 배송 중 </c:if></span>
                                        <span class="badge bg-success"><c:if test="${order.delivery.status eq 'COMPLETE'}"> 배송 완료 </c:if></span>
                                    </td>

                                    <td><c:out value="${order.orderDate}" /></td>

                                </tr>
                            </c:forEach>
                    </c:forEach>
                </sec:authorize>


                <sec:authorize access="hasRole('ROLE_ADMIN')">
                    <c:forEach var="order" items="${orderAdmin}">
                        <c:forEach var="orderItem" items="${order.orderItems}">
                            <tr>
                                <td><c:out value="${order.member.username}" /></td>
                                <td><c:out value="${orderItem.item.name}" /></td>
                                <td><c:out value="${orderItem.orderPrice}" /></td>
                                <td><c:out value="${orderItem.count}" /></td>
                                <td><c:out value="${orderItem.totalPrice}" /></td>
                                <td><c:out value="${order.status}" /></td>



                                <input type="hidden" name="deliveryId" class="form-control"
                                       value="${order.delivery.id}"/>
                                <td>
                                    <%-- 배송 중 / 배송 완료 --%>
                                    <select class="status">
                                        <option value="WAITING">배송 대기</option>
                                        <option value="START">배송중</option>
                                        <option value="COMPLETE">배송 완료</option>
                                    </select>
                                    <div class = "col-auto">

                                        <button class="btn btn-outline-dark" type="button">
                                            업데이트
                                        </button>
                                    </div>
                                </td>



                                <td><c:out value="${order.orderDate}" /></td>
                            </tr>
                        </c:forEach>
                    </c:forEach>
                </sec:authorize>
            </tbody>
        </table>
    </div>
</div> <!-- /container -->

<script type="text/javascript">
    let token = $("meta[name='_csrf']").attr("content");
    let header = $("meta[name='_csrf_header']").attr("content");
    $(function () {
        $(document).ajaxSend(function (e, xhr) {
            xhr.setRequestHeader(header, token);
        });
    });


    $(function () {

        // 배송 정보 업데이트 버튼
        $('.btn-outline-dark').click(function () {
            let deliveryId = $(this).parent().parent().parent().find("input[name='deliveryId']").val();
            let status = $(this).parent().parent().find(".status").val(); //select에 있는 class를 찾음

            let data = {"deliveryId": deliveryId, "status": status}

            $.ajax({
                url: "/order/update/" + deliveryId,
                type: "PUT",
                data: JSON.stringify(data),
                contentType: "application/json; charset=utf-8",
                dataType: "text",
                success: function (result) {
                    console.log("result: " + result);
                    self.location.reload();

                }
            })
        });
    });
</script>

</body>
</html>