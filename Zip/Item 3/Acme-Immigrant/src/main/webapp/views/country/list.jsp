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

<display:table name="countries" id="country" requestURI="${requestURI}"
	pagesize="5" class="displaytag">

	<display:column>
		<spring:message code="display.button" var="disp"></spring:message>
		<acme:displayLink link="country/display.do?countryId=${country.id}"
			value="${disp}" />
	</display:column>

	<spring:message code="country.name" var="countryName"></spring:message>
	<display:column property="name" title="${countryName }"
		sortable="true" />

	<spring:message code="country.ISOCode" var="countryCode"></spring:message>
	<display:column property="ISOCode" title="${countryCode }"
		sortable="true" />

</display:table>

<security:authorize access="hasRole('ADMIN')">
	<spring:message code="country.new" var="createCountry"></spring:message>
	<spring:url value="country/admin/create.do" var="url"></spring:url>
	<a href="${url}"><jstl:out value="${createCountry}"></jstl:out></a>
</security:authorize>