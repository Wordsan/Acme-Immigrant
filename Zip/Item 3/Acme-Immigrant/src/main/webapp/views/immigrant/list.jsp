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

<display:table name='immigrants' id='immigrant'
	requestURI='immigrant/investigator/list.do' pagesize="5"
	class="displaytag">
	<display:column>
		<spring:message code="display.button" var="disp"></spring:message>
		<acme:displayLink
			link="view/immigrant/display.do?immigrantId=${immigrant.id}"
			value="${disp}" />
	</display:column>

	<spring:message code="actor.name" var="nameTitle" />
	<display:column property="name" title="${nameTitle}" sortable="true"></display:column>

	<spring:message code="actor.surname" var="surnameTitle" />
	<display:column property="surname" title="${surnameTitle}" sortable="true"></display:column>

	<spring:message code="actor.email" var="emailTitle" />
	<display:column property="email" title="${emailTitle}" sortable="true"></display:column>
</display:table>
