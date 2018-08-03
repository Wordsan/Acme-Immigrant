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

<acme:display code="country.name" value="${country.name}" />

<acme:display code="country.ISOCode" value="${country.ISOCode}" />

<spring:message code="country.flag" />
<spring:message code="country.flag.alt" var="flagAlt" />
<spring:url value="${country.flag}" var="bandera" />
<img src="${bandera}" alt="${flagAlt}" />
<br>

<spring:message code="country.wikiPage" />
<br>
<spring:url value="${country.wikiPage}" var="url" />
<iframe src="${url}" style="width : 70%;"></iframe>
<br>

<jstl:if test="${!empty country.laws}">
	<fieldset>
		<legend>
			<spring:message code="country.laws"></spring:message>
		</legend>
		<jstl:forEach items="${country.laws}" var="law">
			<acme:displayLink code="law.title" value="${law.title}"
				link="law/display.do?lawId=${law.id}"></acme:displayLink>
		</jstl:forEach>
	</fieldset>
</jstl:if>

<jstl:if test="${!empty country.visas}">
	<fieldset>
		<legend>
			<spring:message code="country.visas"></spring:message>
		</legend>
		<jstl:forEach items="${country.visas}" var="visa">
			<acme:displayLink code="visa.clase" value="${visa.clase}"
				link="visa/display.do?visaId=${visa.id}"></acme:displayLink>
		</jstl:forEach>
	</fieldset>
</jstl:if>

<security:authorize access="hasRole('ADMIN')">
	<acme:button name="edit" url="country/admin/edit.do?countryId=${country.id}"
		code="edit.button" />
</security:authorize>