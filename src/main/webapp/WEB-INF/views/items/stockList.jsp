<%@ page import="com.omnilab.templatekotlin.domain.item.ItemType" %><%--
  Created by IntelliJ IDEA.
  User: milvus
  Date: 2023-07-20
  Time: 오전 9:03
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>상품 관리</title>

    <style>
        /* 짝수 행들 */
        tr:nth-child(even) {
            background-color: #c6bfe0;
        }

        /* 홀수 행들 */
        tr:nth-child(odd) {
            background-color: #dfdae0;
        }

        th {
            background-color: #434245;
            color: white;
        }

        /* !important
        해당 속성이 변경되지 않도록 하는 역할
        사용자가 합당한 이유로 웹 개발자의 스타일을 덮어쓰는 경우 !important 사용
        만약 변경하고 싶다면 똑같이 속성에 !important 속성을 삽입
        */

        /* 등록
            .collasible
        */
        .collapsible {
            background-color: #777;
            color: black;
            cursor: pointer;
            padding: 18px;
            width: 100%;
            border: none;
            text-align: left;
            outline: none;
            font-size: 15px;
        }

        /*.active,*/
        /*.collapsible:hover {*/
        /*    background-color: #555;*/
        /*}*/

        .content {
            padding: 0 18px;
            max-height: 0;
            overflow: hidden;
            transition: max-height 0.2s ease-out;
            color: black;
            background-color: #f1f1f1;
        }

        /* :after , :before
        요소 내용 끝에 새 컨텐츠를 추가.  요소 내용 앞쪽에 새 컨텐츠를 추가.
        가상 클래스 : 여러 태그 중에서 원하는 태그를 선택하기 위해 사용하는 선택자 ex) :hover, :active, :forcus , :visited
        */
        /*.collapsible:after { !* collasible : 접었다 펴기 기능 *!
            content: '\002B'; !* "\002B" 는 + 로 표기됩니다. *!
            color: white;
            font-weight: bold;
            float: right;
            margin-left: 5px;
        }*/

        /* .active : 버튼을 눌렀다 때는 시점까지의 효과 */
        /* .hover : 마우스를 요소 위에 올리는 시점 */
        .active:after {
            content: "\2212"; /* "\2212" 는 + 로 표기됩니다. */
        }


        .btn btn-secondary mb-3 col-sm-2 {
            margin-right: 5px;
        }

        .box1{
            margin:30px;
        }
        .excelBtn {
            margin: 30px;

        }
        .contain{
            margin-left: -80px;
        }
    </style>

    <script>
        function collapse(element) {
            var before = document.getElementsByClassName("active")[0]               // 기존에 활성화된 버튼
            if (before && document.getElementsByClassName("active")[0] != element) {  // 자신 이외에 이미 활성화된 버튼이 있으면
                before.nextElementSibling.style.maxHeight = null;   // 기존에 펼쳐진 내용 접고
                before.classList.remove("active");                  // 버튼 비활성화
            }
            element.classList.toggle("active");         // 활성화 여부 toggle

            var content = element.nextElementSibling;
            if (content.style.maxHeight != 0) {         // 버튼 다음 요소가 펼쳐져 있으면
                content.style.maxHeight = null;         // 접기
            } else {
                content.style.maxHeight = content.scrollHeight + "px";  // 접혀있는 경우 펼치기
            }
        }
    </script>

</head>
<body>
<div class="container col-5" style="text-align:center">

    <div class="header"></div>

    <h1>상품 관리</h1>


    <div class="box1">
        <form class="row g-3">
            <input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }"/>

            <div class="form-floating col-sm-5">
                <select name="itemType" class="form-select" id="floatingSelect">
                    <option value=""> 종류 선택</option>

                    <!-- var는 변수명, items는 반복할 객체명 enum인 itemType을 forEach시키는 방법-->
                    <c:forEach var="type" items="<%= ItemType.values()%>">
                        <%-- 해당 옵션이 선택될 때 서버로 제출되는 값을 명시 --%>
                        <option value="${type}">${type}</option>
                    </c:forEach>

                </select>
                <label for="floatingSelect">상품 타입</label>
            </div>


            <button type="submit" class="btn btn-secondary mb-3 col-sm-2">검색</button>
            <a href="/items/new/register" class="btn btn-secondary mb-3 col-sm-4">상품 등록</a>
        </form>
    </div>

    <%-- 여기에 주문한 리스트들 <li> 나열. 그리고 등록 따로   --%>

    <form action="/excel/down" method="post" class="excelBtn">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token }"/>
        <input type="hidden" name="itemType" class="form-control"
                value="${itemSearch.itemType}"/>

        <input type="submit" value="Excel Download">


    </form>




    <div class="contain col-10" style="width: 100%">
        <div class="table-responsive" style="width: 800px">

            <table class="table">
                <thead>
                <tr>
                    <%-- scope : 해당 셀이 열(column)을 위한 헤더 셀임을 명시함. --%>
                    <th scope="col"> NO</th>
                    <th scope="col"> 상품 이름</th>
                    <th scope="col"> 상품 종류</th>
                    <th scope="col"> 상품 가격</th>
                    <th scope="col"> 재고</th>
                    <th scope="col"> 재고 수정</th>
                    <th scope="col"> 수정 완료</th>
                    <th scope="col"> 삭제</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="item" items="${items.content}">
                    <form action="/items/new/${item.id}/edit" method="post">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

                        <tr>
                            <td class="noBorder"><c:out value="${item.id}"/></td>
                            <td class="noBorder"><c:out value="${item.name}"/></td>
                            <td class="noBorder"><c:out value="${item.dtype}"/></td>
                            <td class="noBorder"><c:out value="${item.price}"/></td>

                            <td class="noBorder"><c:out value="${item.stockQuantity}"/></td>

                            <td>
                                <div class="input-group input-group-sm mb-3">
                                    <input type="number" min="0" name="stockQuantity" class="form-control"
                                           aria-label="Sizing example input" aria-describedby="inputGroup-sizing-sm">
                                </div>
                            </td>
                            <td>
                                <button type="submit" class="btn btn-outline-secondary">수정하기</button>
                            </td>

                                <%-- 상품 관리 쪽에서도 취소가 가능하도록 cancel 넣어주기 --%>
                            <td class="noBorder"><a href="/items/new/${item.id}/delete"
                                                    class="btn btn-outline-danger btn-sm"> 재고 삭제 </a></td>
                        </tr>
                    </form>

                </c:forEach>

                </tbody>
            </table>

        </div>
    </div>



    <div class="btn-group me-2" role="group">
        <ul class="pagination">
            <!-- 이전과 처음으로 -->
            <li class="page-item">
                <a class="page-link" href="/items/new?page=${startPage}" aria-label="Previous">
                    <span aria-hidden="true"> << </span> <!-- 줄바꿈 X -->
                </a>
            </li>

            <li class="page-item">
                <a class="page-link" href="/items/new?page=${nowPage - 1}" aria-label="Previous">
                    <span aria-hidden="true"> < </span>
                </a>
            </li>


            <c:forEach var="page" begin="${startPage}" end="${endPage}">
                <li class="page-item">
                    <c:choose>
                        <c:when test="${boardList.pageable.pageNumber+1 != page}">
                            <a type="button" class="btn btn-outline-secondary"
                               href="/items/new?page=${page}"/>${page}</a>
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
                <a class="page-link" href="/items/new?page=${nowPage + 1}" aria-label="Next">
                    <span aria-hidden="true"> > </span>
                </a>
            </li>

            <li class="page-item">
                <a class="page-link" href="/items/new?page=${endPage}" aria-label="Next">
                    <span aria-hidden="true"> >> </span> <!-- 줄바꿈 X -->
                </a>
            </li>

        </ul>
    </div>

    <div class="footer"></div>
</div>
</div>
</body>
</html>
