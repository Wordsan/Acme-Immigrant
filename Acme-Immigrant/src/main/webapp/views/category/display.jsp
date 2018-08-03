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

<acme:display code="category.name" value="${category.name}" />

<jstl:if test="${category.parent != null}">
	<acme:display code="category.parent" value="${category.parent.name}" />
</jstl:if>

<jstl:if test="${!empty category.visas}">
	<fieldset>
		<legend>
			<spring:message code="category.visas"></spring:message>
		</legend>
		<jstl:forEach items="${category.visas}" var="visa">
			<acme:displayLink code="visa.clase" value="${visa.clase}"
				link="visa/display.do?visaId=${visa.id}"></acme:displayLink>
		</jstl:forEach>
	</fieldset>
</jstl:if>

<jstl:if test="${!empty category.childs}">
	<fieldset>
		<legend>
			<spring:message code="category.childs"></spring:message>
		</legend>
		<jstl:forEach items="${category.childs}" var="child">
			<acme:displayLink code="category.name" value="${child.name}"
				link="category/display.do?categoryId=${child.id}"></acme:displayLink>
		</jstl:forEach>
	</fieldset>
</jstl:if>

<security:authorize access="hasRole('ADMIN')">
	<acme:button name="edit"
		url="category/edit.do?categoryId=${categoryId}" code="edit.button" />
</security:authorize>