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

<display:table name="requirements" id="requirement"
	requestURI="${requestURI}" pagesize="5" class="displaytag">

	<display:column>
		<spring:message code="display.button" var="disp"></spring:message>
		<acme:displayLink
			link="requirement/display.do?requirementId=${requirement.id}"
			value="${disp}" />
	</display:column>


	<spring:message code="requirement.law" var="lawTitle"></spring:message>
	<display:column title="${lawTitle}">
		<acme:displayLink link="law/display.do?lawId=${requirement.law.id}"
			value="${requirement.law.title}" />
	</display:column>

	<spring:message code="requirement.title" var="requirementTitle"></spring:message>
	<display:column property="title" title="${requirementTitle }"
		sortable="true" />

	<spring:message code="requirement.abrogated" var="requirementAbrogated"></spring:message>
	<display:column title="${requirementAbrogated }"
		sortable="true" >
		<jstl:if test="${requirement.abrogated == true}">
			<spring:message code="requirement.abrogated.yes"/>
		</jstl:if>
		<jstl:if test="${requirement.abrogated == false}">
			<spring:message code="requirement.abrogated.no"/>
		</jstl:if>
	</display:column>

</display:table>