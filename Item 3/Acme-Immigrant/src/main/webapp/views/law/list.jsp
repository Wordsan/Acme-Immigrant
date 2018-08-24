<%--
 * action-1.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<display:table name="laws" id="law" requestURI="${requestUri}"
	pagesize="5" class="displaytag">

	<display:column>
		<spring:message code="display.button" var="disp"></spring:message>
		<acme:displayLink link="law/display.do?lawId=${law.id}"
			value="${disp}" />
	</display:column>

	<spring:message code="law.title" var="lawTitle"></spring:message>
	<display:column property="title" title="${lawTitle }" sortable="true" />

	<spring:message code="law.country" var="lawCountry"></spring:message>
	<display:column title="${lawCountry}">
		<acme:displayLink
			link="country/display.do?countryId=${law.country.id}"
			value="${law.country.name}" />
	</display:column>


</display:table>

<security:authorize access="hasRole('ADMIN')">
	<spring:message code="law.new" var="createLaw"></spring:message>
	<spring:url value="law/admin/create.do" var="url"></spring:url>
	<a href="${url}"><jstl:out value="${createLaw}"></jstl:out></a>
</security:authorize>