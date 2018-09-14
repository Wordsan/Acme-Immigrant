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

<acme:phone />

<form:form
	action="contactSection/immigrant/edit.do?applicationId=${applicationId}"
	method="post" modelAttribute="contactSection"
	onsubmit="return checkTel($('#phoneNumber').val())">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<acme:textbox code="contactSection.emailAddress" path="emailAddress"
		placeholder="mail@mail.com" />
	<acme:textbox code="contactSection.phoneNumber" path="phoneNumber"
		placeholder="+34 (548) 2467" />
	<acme:textbox code="contactSection.pagerNumber" path="pagerNumber" />
	<acme:cancel
		url="application/immigrant/display.do?applicationId=${applicationId}"
		code="cancel.button" />
	<acme:submit name="save" code="save.button" />
</form:form>