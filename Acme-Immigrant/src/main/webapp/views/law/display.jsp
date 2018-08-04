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

<acme:display code="law.title" value="${law.title}" />

<acme:display code="law.text" value="${law.text}" />

<spring:message code="date.pattern" var="pattern"></spring:message>

<spring:message code="law.createdAt"></spring:message>
<fmt:formatDate value="${law.createdAt}" pattern="${pattern}"
	var="createdAt" />
<jstl:out value="${createdAt}"></jstl:out>
<br>

<acme:displayLink link="country/display.do?countryId=${law.country.id}"
	value="${law.country.name}" code="law.country" />

<jstl:if test="${law.abrogatedAt != null}">
	<spring:message code="law.abrogatedAt"></spring:message>
	<fmt:formatDate value="${law.abrogatedAt}" pattern="${pattern}"
		var="abrogatedAt" />
	<jstl:out value="${abrogatedAt}"></jstl:out>
	<br>
</jstl:if>

<jstl:if test="${!empty law.requirements}">
	<fieldset>
		<legend>
			<spring:message code="law.requirements"></spring:message>
		</legend>
		<jstl:forEach items="${law.requirements}" var="req">
			<acme:displayLink code="requirement.title" value="${req.title}"
				link="requirement/display.do?requirementId=${req.id}"></acme:displayLink>
		</jstl:forEach>
	</fieldset>
</jstl:if>

<jstl:if test="${!empty law.relatedLaws}">
	<fieldset>
		<legend>
			<spring:message code="law.relatedLaws"></spring:message>
		</legend>
		<jstl:forEach items="${law.relatedLaws}" var="rLaw">
			<acme:displayLink code="law.title" value="${rLaw.title}"
				link="requirement/display.do?requirementId=${rLaw.id}"></acme:displayLink>
		</jstl:forEach>
	</fieldset>
</jstl:if>

<security:authorize access="hasRole('ADMIN')">
	<acme:button name="edit" url="law/admin/edit.do?lawId=${law.id}"
		code="edit.button" />
</security:authorize>