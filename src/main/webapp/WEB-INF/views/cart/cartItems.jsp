<%--
  Created by IntelliJ IDEA.
  User: milvus
  Date: 2023-07-20
  Time: 오전 9:03
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<html>
<head>
    <title>장바구니</title>
</head>

<body>
<div class="container col-5" style="text-align:center">
    <H1>장바구니</H1>
    <br/>
    <form action="/cartItem/order" method="post" name="board_form" id="board_form">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

        <table class="table">

            <thead>
            <tr class="table-secondary">
                <th class="col-1"></th>
                <th class="col-3">상품명</th>
                <th class="col-2">개수</th>
                <th class="col-3">가격</th>
                <th class="col-4">총 가격</th>
                <th class="col-5">date</th>
                <th class="col-1"></th>
            </tr>
            </thead>
            <tbody>


            <c:forEach items="${items.content}" var="item">
                <!-- check box -->
                <tr>
                    <td>
                        <div class="form-check form-check-inline">
                                <%--                            <input type="checkbox" name="cartItemIds">--%>
                            <input class="form-check-input" type="checkbox" name="cartItemIds" value="${item.id}" >
                        </div>
                    </td>

                    <td><c:out value="${item.name}"/></td>
                    <td class="count"><c:out value="${item.count}"/></td>
<%--                    <td class="asd">${item.count}</td>--%>

                    <input type="hidden" name="count" class="form-control"
                           value="${item.count}"/>

                    <input type="hidden" name="stockQuantity" class="form-control"
                           value="${item.stockQuantity}"/>

                    <td><!-- 상품 가격 -->
                        <c:out value="${item.price}"/>
                    </td>

                    <td><!-- 상품 총 가격 -->
                        <c:out value="${item.totalPrice}"/>
                    </td>
                    <td><c:out value="${item.date}"/></td>
                    <td>
                        <a href="/cartItem/${item.id}/cancel" class="btn btn-outline-danger btn-sm"> 주문 취소 </a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <br/>



        <!-- 주문하기 button 장바구니에 아무것도 없으면 주문하기 버튼 생략 -->
        <c:if test="${items.totalElements != 0}">
            <div class="d-grid gap-2">
                <button class="btn btn-outline-secondary" type="button" onclick="stock();">주문하기</button>
            </div>
        </c:if>
    </form>

</div>

<script type="text/javascript">

    function stock() {
        //값이 현재 여러 개 .is() 하면서 체크가 true인 하나를 선정하게 됨

        // input -> cartItemIds인 체크박스에서 체크된 item을 찾음
        let stock = document.querySelectorAll("input[name='cartItemIds']:checked");
        let stockList = [];
        let countList = [];
        for(let i = 0; i < stock.length; i++){
            let el = $(stock[i]).parent().parent().parent();
            stockList.push(el.find("input[name='stockQuantity']").val());
            countList.push(el.find("input[name='count']").val());
        }



        if (stock.length === 0) {
            alert("상품을 선택해주세요.");
        } else {
            let chk = false;
            for(let i=0; i < stockList.length; i++) {
                alert(stockList[i] + " :: " + countList[i]);
                // .val()은 string으로 들어가기 때문에 숫자로 타입 변경
                if (Number(stockList[i]) >= Number(countList[i])) {
                    alert("구매되었습니다.")
                    chk = true;
                }

                else{
                    alert("재고가 부족하여 주문할 수 없습니다.");
                    chk = false;
                }
            }

            //장바구니에 있는 item 개수만큼 반복했을 때 chk가 true면 submit
            if(chk){
                document.getElementById('board_form').submit();
            }
        }
    };

</script>

</body>
</html>







<%--
// jQuery일 때
/*let stock = $("input[name='cartItemIds']:checked");
let stockList = [];
let countList = [];
for(let i = 0; i < stock.length; i++){
    let el = stock.eq(i).parent().parent().parent();
    stockList.push(el.find("input[name='stockQuantity']").val());
    countList.push(el.find("input[name='count']").val());
}*/
--%>