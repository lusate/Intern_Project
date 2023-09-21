<%@ page import="com.omnilab.templatekotlin.domain.item.ItemType" %><%--
  Created by IntelliJ IDEA.
  User: milvus
  Date: 2023-07-20
  Time: 오전 9:03
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
        .collapsible:after { /* collasible : 접었다 펴기 기능 */
            content: '\002B'; /* "\002B" 는 + 로 표기됩니다. */
            color: white;
            font-weight: bold;
            float: right;
            margin-left: 5px;
        }

        /* .active : 버튼을 눌렀다 때는 시점까지의 효과 */
        /* .hover : 마우스를 요소 위에 올리는 시점 */
        .active:after {
            content: "\2212"; /* "\2212" 는 + 로 표기됩니다. */
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


    <div>
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


            <button type="submit" class="btn btn-secondary btn">등록할 상품 종류 검색</button>
        </form>
    </div>


    <c:if test="${itemSearch.itemType eq 'BOOK'}">
        <button type="button" class="collapsible" onclick="collapse(this);"> # BOOK</button>
        <div class="content">
                <%--                    <form action="/items/new/book" method="post" enctype="multipart/form-data">--%>
            <form:form action="/items/new/register/book?${_csrf.parameterName}=${_csrf.token}" method="post" name="regist"
                  enctype="multipart/form-data">
                <!-- 폼 데이터에서 CSRT 토큰 값을 함께 전달하는 방법 -->
                    <%--                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />--%>

                <input type="hidden" name="memberId" value="${form.memberId}"/>

                <!-- 이미지 넣기 -->
                <label>상품 이미지 업로드</label>
                <div class="form-floating mb-3">
                    <input type="file" class="form-control" id="imgFile1" name="files" multiple="multiple">
                </div>

                <div class="form-floating mb-3">
                    <input type="text" name="name" class="form-control" placeholder="이름을 입력하세요">
                    <label>상품명</label>
                </div>

                <div class="form-floating mb-3">
                    <input type="number" name="price" class="form-control" placeholder="가격을 입력하세요">
                    <label>가격</label>
                </div>

                <div class="form-floating mb-3">
                    <input type="number" name="stockQuantity" class="form-control" placeholder="수량을 입력하세요">
                    <label>수량</label>
                </div>

                <div class="form-floating mb-3">
                    <input type="text" name="author" class="form-control" placeholder="아티스트를 입력하세요">
                    <label>아티스트</label>
                </div>

                <div class="form-floating mb-3">
                    <input type="text" name="isbn" class="form-control" placeholder="기타 정보를 입력하세요">
                    <label>ETC</label>
                </div>

                <div class="d-grid gap-2">
                    <input type="button" class="btn btn-outline-secondary" name="numberCheck" value="상품 등록"/>
                </div>
            </form:form>
        </div>

    </c:if>


    <c:if test="${itemSearch.itemType eq 'CLOTH'}">
        <button type="button" class="collapsible" onclick="collapse(this);"> # CLOTH</button>
        <div class="content">
            <!-- multipart/form-data로 지정된 경우에는 action의 url정보에 토큰 값을 같이 넘겨준다. -->
            <form:form action="/items/new/register/cloth?${_csrf.parameterName}=${_csrf.token}" method="post" name="regist"
                  enctype="multipart/form-data">

                <input type="hidden" name="memberId" value="${form.memberId}"/>

                <!-- 이미지 넣기 -->
                <label>상품 이미지 업로드</label>
                <div class="form-floating mb-3">
                    <input type="file" class="form-control" id="imgFile2" name="files" multiple="multiple">
                </div>

                <div class="form-floating mb-3">
                    <input type="text" name="name" class="form-control" placeholder="이름을 입력하세요">
                    <label>상품명</label>
                </div>

                <div class="form-floating mb-3">
                    <input type="number" name="price" class="form-control" placeholder="가격을 입력하세요">
                    <label>가격</label>
                </div>

                <div class="form-floating mb-3">
                    <input type="number" name="stockQuantity" min="1" class="form-control" placeholder="수량을 입력하세요">
                    <label>수량</label>
                </div>

                <div class="form-floating mb-3">
                    <input type="text" name="brand" class="form-control" placeholder="브랜드를 입력하세요">
                    <label>브랜드</label>
                </div>

                <div class="form-floating mb-3">
                    <input type="number" name="size" min="1" class="form-control" placeholder="사이즈를 입력하세요">
                    <label>사이즈</label>
                </div>

                <div class="form-floating mb-3">
                    <input type="text" name="color" class="form-control" placeholder="색상을 입력하세요">
                    <label>색상</label>
                </div>

                <div class="d-grid gap-2">
                    <input type="button" class="btn btn-outline-secondary" name="numberCheck" value="상품 등록"/>
<%--                    <button class="btn btn-outline-secondary" type="submit">상품 등록</button>--%>
                </div>
            </form:form>
        </div>
    </c:if>


    <div class="footer"></div>
</div>
</div>


<script type="text/javascript">
    $(function () {
        $('input[name=numberCheck]').click(function () {
            if ($('input[name=files]').val() == '') {
                alert("파일을 업로드 해주세요");
                $('input[name=files]').focus();

            } else if ($('input[name=name]').val() == '') {
                alert("상품명을 입력해주세요.");
                $('input[name=name]').focus();

            } else if ($('input[name=price]').val() == '') {
                alert("가격을 입력해주세요");
                $('input[name=price]').focus();

            } else if ($('input[name=stockQuantity]').val() == '') {
                alert("재고 수량을 입력해주세요.");
                $('input[name=stockQuantity]').focus();

            } else if ($('input[name=author]').val() == '') {
                alert("작가를 입력해주세요");
                $('input[name=author]').focus();

            } else if ($('input[name=isbn]').val() == '') {
                alert("국제표준도서번호을 입력해주세요");
                $('input[name=isbn]').focus();

            } else if ($('input[name=brand]').val() == '') {
                alert("브랜드를 입력해주세요");
                $('input[name=brand]').focus();

            } else if ($('input[name=size]').val() == '') {
                alert("사이즈를 입력해주세요");
                $('input[name=size]').focus();

            } else if ($('input[name=color]').val() == '') {
                alert("색상을 입력해주세요");
                $('input[name=color]').focus();

            }else{
                $('form[name=regist]').submit();
            }
        });
    });

</script>

</body>
</html>
