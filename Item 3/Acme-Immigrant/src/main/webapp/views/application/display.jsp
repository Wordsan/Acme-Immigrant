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
<script type="text/javascript">
	$(document).ready(function() {
		a = $("#creditCard");
		b = a.text().trim();
		c = Array(b.length - 3).join("*");
		a.text(c + b.substring(b.length - 4));
	})
</script>
<security:authentication property="principal.username" var="username" />

<acme:display code="application.ticker" value="${application.ticker}"></acme:display>

<spring:message code="date.hour.pattern" var="patternHour"></spring:message>
<spring:message code="date.pattern" var="pattern" />

<spring:message code="application.openedMoment"></spring:message>
<fmt:formatDate value="${application.openedMoment}" pattern="${pattern}"
	var="openedMoment" />
<jstl:out value="${openedMoment}"></jstl:out>
<br>

<spring:message code="application.closedMoment"></spring:message>
<fmt:formatDate value="${application.closedMoment}" pattern="${pattern}"
	var="closedMoment" />
<jstl:out value="${closedMoment}"></jstl:out>
<br>

<security:authorize access="hasRole('OFFICER')">
	<spring:message code="application.immigrant"></spring:message>
	<acme:displayLink
		link="view/immigrant/display.do?immigrantId=${application.immigrant.id}"
		value="${application.immigrant.name}" />
</security:authorize>

<acme:displayLink code="application.visa"
	link="visa/display.display.do?visaId=${application.visa.id}"
	value="${application.visa.clase}"></acme:displayLink>

<acme:display code="application.status" value="${application.status}"></acme:display>

<security:authorize access="hasRole('IMMIGRANT')">
	<spring:message code="application.creditCard" />
	<div id="creditCard">
		<jstl:out value="${application.creditCard.number}"></jstl:out>
	</div>
</security:authorize>

<jstl:if test="${application.decision!=null}">
	<spring:message code="application.decision"></spring:message>
</jstl:if>
<security:authorize access="hasRole('OFFICER')">
	<jstl:if
		test="${application.officer.userAccount.username == username and application.decision == null}">
		<fieldset>
			<legend>
				<spring:message code="decision.create"></spring:message>
			</legend>
			<form
				action="decision/officer/create.do?applicationId=${application.id}"
				method="POST">
				<spring:message code="decision.reason"></spring:message>
				<input type="text" name="reason" id="reason" /> <span class="error"
					id="errorDecision"><spring:message
						code="decision.reason.error" /></span>
				<script type="text/javascript">
					b = document.getElementById("errorDecision");
					if (window.location.href.search("decision.reason.error") != -1) {
						b.hidden = false;
					} else {
						b.hidden = true;
					}
				</script>
				<br>
				<spring:message code="decision.accepted"></spring:message>
				<input type="checkbox" name="accepted" />
				<button type="submit" name="create">
					<spring:message code="save.button" />
				</button>
			</form>
		</fieldset>
	</jstl:if>
</security:authorize>
<jstl:if test="${application.decision.accepted == true}">
	<spring:message code="application.decision.yes"></spring:message>
</jstl:if>

<jstl:if test="${application.decision.accepted == false}">
	<spring:message code="application.decision.no"></spring:message>
	<br>
	<acme:display code="decision.reason"
		value="${application.decision.comments}" />
</jstl:if>

<fieldset>

	<fieldset>
		<legend>
			<spring:message code="application.personalSection"></spring:message>
		</legend>
		<acme:display value="${application.personalSection.fullNames}"
			code="personalSection.fullNames"></acme:display>
		<spring:message code="personalSection.birthDate"></spring:message>
		<fmt:formatDate value="${application.personalSection.birthDate}"
			pattern="${pattern}" var="birth" />
		<jstl:out value="${birth}"></jstl:out>
		<br>
		<acme:display value="${application.personalSection.birthPlace}"
			code="personalSection.birthPlace"></acme:display>
		<acme:display value="${application.personalSection.picture}"
			code="personalSection.picture"></acme:display>
	</fieldset>


	<fieldset>
		<legend>
			<spring:message code="application.contactSection"></spring:message>
		</legend>

		<jstl:forEach items="${application.contactSections}" var="cSection">
			<div class="box">
				<acme:display code="contactSection.emailAddress"
					value="${cSection.emailAddress}"></acme:display>
				<jstl:if test="${cSection.phoneNumber != null}">
					<acme:display code="contactSection.phoneNumber"
						value="${cSection.phoneNumber}"></acme:display>
				</jstl:if>
				<jstl:if test="${cSection.pagerNumber != null}">
					<acme:display code="contactSection.pagerNumber"
						value="${cSection.pagerNumber}"></acme:display>
				</jstl:if>
			</div>
		</jstl:forEach>
		<acme:button name="new"
			url="contactSection/immigrant/create.do?applicationId=${application.id}"
			code="add.new.button" />
	</fieldset>


	<fieldset>
		<legend>
			<spring:message code="application.socialSection"></spring:message>
		</legend>
		<jstl:forEach items="${application.socialSections}" var="sSection">
			<div class="box">
				<acme:display code="socialSection.nickname"
					value="${sSection.nickname}"></acme:display>
				<acme:display code="socialSection.socialNetwork"
					value="${sSection.socialNetwork}"></acme:display>
				<acme:display code="socialSection.linkProfile"
					value="${sSection.linkProfile}"></acme:display>
			</div>
		</jstl:forEach>
		<acme:button name="new"
			url="socialSection/immigrant/create.do?applicationId=${application.id}"
			code="add.new.button" />
	</fieldset>


	<fieldset>
		<legend>
			<spring:message code="application.educationSection"></spring:message>
		</legend>
		<jstl:forEach items="${application.educationSections}" var="eSection">
			<div class="box">
				<acme:display code="educationSection.degreeName"
					value="${eSection.degreeName}"></acme:display>
				<acme:display code="educationSection.institution"
					value="${eSection.institution}"></acme:display>
				<jstl:if test="${eSection.awarded != null}">
					<spring:message code="educationSection.awarded"></spring:message>
					<fmt:formatDate value="${eSection.awarded}" pattern="${pattern}"
						var="awarded" />
					<jstl:out value="${awarded}"></jstl:out>
					<br>
				</jstl:if>
				<acme:display code="educationSection.level"
					value="${eSection.level}"></acme:display>
			</div>
		</jstl:forEach>
		<acme:button name="new"
			url="educationSection/immigrant/create.do?applicationId=${application.id}"
			code="add.new.button" />
	</fieldset>

	<fieldset>
		<legend>
			<spring:message code="application.workSection"></spring:message>
		</legend>
		<jstl:forEach items="${application.workSections}" var="wSection">
			<div class="box">
				<acme:display code="workSection.companyName"
					value="${wSection.companyName}"></acme:display>
				<acme:display code="workSection.position"
					value="${wSection.position}"></acme:display>
				<spring:message code="workSection.startDate"></spring:message>
				<fmt:formatDate value="${wSection.startDate}" pattern="${pattern}"
					var="startDate" />
				<jstl:out value="${startDate}"></jstl:out>
				<br>
				<jstl:if test="${wSection.endDate != null}">
					<spring:message code="workSection.endDate"></spring:message>
					<fmt:formatDate value="${wSection.endDate}" pattern="${pattern}"
						var="endDate" />
					<jstl:out value="${endDate}"></jstl:out>
					<br>
				</jstl:if>
			</div>
		</jstl:forEach>
		<acme:button name="new"
			url="workSection/immigrant/create.do?applicationId=${application.id}"
			code="add.new.button" />
	</fieldset>
</fieldset>

<jstl:if test="${!empty application.questions}">
	<fieldset>
		<legend>
			<spring:message code="application.questions"></spring:message>
		</legend>

		<jstl:forEach items="${application.questions}" var="q">
			<div class="box">
				<acme:display code="question.statement" value="${q.statement}"></acme:display>
				<spring:message code="question.madeMoment"></spring:message>
				<fmt:formatDate value="${q.madeMoment}" pattern="${patternHour}"
					var="qMadeMoment" />
				<jstl:out value="${qMadeMoment}"></jstl:out>
				<br>
				<jstl:if test="${q.answer != null}">
					<acme:display code="question.answer" value="${q.answer}"></acme:display>
					<spring:message code="question.answerMoment"></spring:message>
					<fmt:formatDate value="${q.answerMoment}" pattern="${patternHour}"
						var="qAnswerMoment" />
					<jstl:out value="${qAnswerMoment}"></jstl:out>
					<br>
				</jstl:if>
			</div>
		</jstl:forEach>
	</fieldset>
</jstl:if>

<jstl:if test="${!empty application.linkedApplications}">
	<fieldset>
		<legend>
			<spring:message code="application.linkedApps"></spring:message>
		</legend>
		<jstl:forEach items="${application.linkedApplications}"
			var="linkedApp">
			<div class="box">
				<security:authorize access="hasRole('IMMIGRANT')">
					<jstl:set var="role" value="immigrant"></jstl:set>
				</security:authorize>
				<security:authorize access="hasRole('OFFICER')">
					<jstl:set var="role" value="officer"></jstl:set>
				</security:authorize>
				<security:authorize access="hasRole('INVESTIGATOR')">
					<jstl:set var="role" value="investigator"></jstl:set>
				</security:authorize>
				<acme:displayLink
					link="application/${role}/display.do?applicationId=${linkedApp.id}"
					value="${linkedApp.ticker}" />
			</div>
		</jstl:forEach>
	</fieldset>
</jstl:if>


<jstl:if
	test="${application.immigrant.userAccount.username == username}">
	<fieldset>
		<jstl:if test="${application.closedMoment == null}">
			<acme:button name="close" code="close.button"
				url="application/immigrant/close.do?applicationId=${application.id}" />
			<acme:button name="edit" code="edit.button"
				url="application/immigrant/edit.do?applicationId=${application.id}" />
			<br>
		</jstl:if>
		<form action="application/immigrant/link.do" method="post">
			<input type="hidden" name="applicationId" value="${application.id}" />
			<label><spring:message code="application.ticker"></spring:message></label>
			<input type="text" name="ticker" />
			<acme:submit name="link" code="link.button" />
		</form>
	</fieldset>
</jstl:if>
<jstl:if test="${application.officer != null}">
	<jstl:if
		test="${application.officer.userAccount.username == username and application.status != 'OPENED'}">
		<fieldset>
			<spring:message code="question.create"></spring:message>
			<form
				action="question/officer/create.do?applicationId=${application.id}"
				method="POST">
				<spring:message code="question.statement"></spring:message>
				<textarea name="statement" id="statement" rows="4"></textarea>
				<span class="error" id="errorQuestion"><spring:message
						code="question.create.error" /></span>
				<script type="text/javascript">
					b = document.getElementById("errorQuestion");
					if (window.location.href.search("question.create.error") != -1) {
						b.hidden = false;
					} else {
						b.hidden = true;
					}
				</script>
				<button type="submit" name="create">
					<spring:message code="save.button" />
				</button>
			</form>
		</fieldset>
	</jstl:if>
</jstl:if>
