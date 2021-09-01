<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="strping" %>
<head>
    <style>
        html, body {
            margin: 0;
            height: 100%;
            overflow: hidden;
        }
    </style>
</head>
<script type='text/javascript' src='https://tableau-report.com/javascripts/api/viz_v1.js'></script>
<div class='tableauPlaceholder' style='width:100%; height:100%;'>
    <object class='tableauViz' width='100%' height='100%' style='display:none;'>
        <param name='host_url' value='https://tableau-report.com/'/>
        <param name='name' value='Outback_CRMReport_Draft_20180831_Upload/Dashboard'/>
        <param name="ticket" value="${ticket}"/>
        <param name='tabs' value='no'/>
        <param name='toolbar' value='no'/>
    </object>
</div>