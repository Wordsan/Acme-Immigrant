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

<display:table class="displaytag" name="questions"
	requestURI="${requestURI}" pagesize="5" id="question">

	<display:column>
		<spring:message code="display.button" var="disp"></spring:message>
		<acme:displayLink
			link="question/immigrant/display.do?questionId=${question.id}"
			value="${disp}" />
	</display:column>

	<spring:message code="question.statement" var="qStatement"></spring:message>
	<display:column property="statement" title="${qStatement}"
		sortable="true" />

	<spring:message code="date.pattern" var="pattern"></spring:message>
	<spring:message code="question.madeMoment" var="qMadeMoment"></spring:message>
	<display:column property="madeMoment" title="${qMadeMoment}"
		sortable="true" format="{0,date, ${pattern}}" />

	<spring:message code="question.answer" var="qAnswer"></spring:message>
	<display:column property="answer" title="${qAnswer}" sortable="true" />

	<spring:message code="question.answerMoment" var="qAMoment"></spring:message>
	<display:column property="answerMoment" title="${qAMoment}"
		sortable="true" format="{0,date, ${pattern}}" />

</display:table>