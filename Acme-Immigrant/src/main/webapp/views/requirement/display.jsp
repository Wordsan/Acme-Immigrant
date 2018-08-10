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

<acme:display code="requirement.title" value="${requirement.title}" />

<acme:display code="requirement.description"
	value="${requirement.description}" />

<spring:message code="requirement.abrogated"></spring:message>
<jstl:if test="${requirement.abrogated == true}">
	<spring:message code="requirement.abrogated.yes"></spring:message>
</jstl:if>
<jstl:if test="${requirement.abrogated == false}">
	<spring:message code="requirement.abrogated.no"></spring:message>
</jstl:if>
<br>

<acme:displayLink link="law/display.do?lawId=${requirement.law.id}"
	value="${requirement.law.title}" code="requirement.law" />

<jstl:if test="${!empty requirement.visas}">
	<fieldset>
		<legend>
			<spring:message code="requirement.visas"></spring:message>
		</legend>
		<jstl:forEach items="${requirement.visas}" var="visa">
			<acme:displayLink code="visa.clase" value="${visa.clase}"
				link="visa/display.do?visaId=${visa.id}"></acme:displayLink>
		</jstl:forEach>
	</fieldset>
</jstl:if>

<security:authorize access="hasRole('ADMIN')">
	<acme:button name="edit"
		url="requirement/admin/edit.do?requirementId=${requirement.id}"
		code="edit.button" />
	<form
		action="requirement/admin/addVisa.do?requirementId=${requirement.id}"
		method="post">
		<select name="visaId">
			<jstl:forEach items="${visas}" var="visa">
				<option value="${visa.id}">
					<jstl:out value="${visa.clase}" />
				</option>
			</jstl:forEach>
		</select>
		<button type="submit">
			<spring:message code="save.button" />
		</button>
	</form>
</security:authorize>