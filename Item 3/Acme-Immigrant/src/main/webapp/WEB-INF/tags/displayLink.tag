<%--
 * cancel.tag
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@ tag language="java" body-content="empty"%>

<%-- Taglibs --%>

<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<%-- Attributes --%>

<%@ attribute name="link" required="true"%>
<%@ attribute name="value" required="false"%>
<%@ attribute name="code" required="false"%>

<%-- Definition --%>
<jstl:if test="${code!=null and value==null}">
	<spring:message code="${code}" var="msg"></spring:message>
	<jstl:set var="value" value="${msg}"></jstl:set>
</jstl:if>
<jstl:if test="${value!=null and code != null}">
	<spring:message code="${code}"></spring:message>
</jstl:if>
<spring:url value="${link}" var="url"></spring:url>
<a href="${url}"><jstl:out value="${value}"></jstl:out></a>
<br />

