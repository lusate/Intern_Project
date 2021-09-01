<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="strping" %>
<style>
    html, body {
        margin: 0;
        height: 100%;
        overflow: hidden;
    }
</style>
<iframe src="http://tableau-report.com/trusted/${ticket}/views/Outback_CRMReport_Draft_20180831_Upload/Dashboard?:embed=yes&:tabs=no&:toolbar=no" style="width: 100%; height: 100%;"></iframe>

