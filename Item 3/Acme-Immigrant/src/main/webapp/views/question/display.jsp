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

<spring:message code="date.hour.pattern" var="pattern"></spring:message>

<acme:display code="question.statement" value="${question.statement}" />

<spring:message code="question.madeMoment"></spring:message>
<fmt:formatDate value="${question.madeMoment}" pattern="${pattern}"
	var="mMoment" />
<jstl:out value="${mMoment}"></jstl:out>
<br>

<jstl:if test="${question.answer != null and question.answer != ''}">
	<acme:display code="question.answer" value="${question.answer}" />
	<spring:message code="question.answerMoment"></spring:message>
	<fmt:formatDate value="${question.answerMoment}" pattern="${pattern}"
		var="aMoment" />
	<jstl:out value="${aMoment}"></jstl:out>
	<br>
</jstl:if>

<security:authorize access="hasRole('IMMIGRANT')">
	<jstl:if test="${question.answer == '' or question.answer == null}">
		<form action="question/immigrant/answer.do?questionId=${question.id}"
			method="post">
			<input type="text" id="answer" name="answer" />
			<button type="submit" name="answerButton">
				<spring:message code="save.button" />
			</button>
		</form>
		<acme:cancel url="question/immigrant/list.do" code="cancel.button" />
	</jstl:if>
</security:authorize>
