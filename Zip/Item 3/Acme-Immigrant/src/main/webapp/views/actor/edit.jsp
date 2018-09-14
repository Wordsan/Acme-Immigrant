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

<acme:phone/>

<form:form action="${formUri}" method="post" id="form" name="form"
	modelAttribute="formActor" onsubmit="return checkTel($('#phoneNumber').val())">
	<form:hidden path="id" />
	<acme:textbox code="actor.name" path="name" placeholder="John"/>
	<acme:textbox code="actor.surname" path="surname"  placeholder="Doe"/>
	<acme:textbox code="actor.email" path="email"  placeholder="email@mail.com"/>
	<acme:textbox code="actor.phoneNumber" path="phoneNumber"  placeholder="+34 (25) 2475"/>
	<acme:textbox code="actor.address" path="address"  placeholder="Plaza Mayor, 7"/>
	<acme:textbox code="actor.username" path="username" />
	<acme:password code="actor.password" path="password" />
	<acme:password code="actor.repassword" path="repassword" />
	<spring:message code="actor.terms" var="terms" />
	<input type="checkbox" name="terms" />
	<form:errors cssClass="error" path="terms" />
	<a href="views/misc/terms.jsp"><jstl:out value="${terms}" /></a>
	<acme:cancel url="welcome/index.do" code="cancel.button" />
	<acme:submit name="save" code="save.button" />
</form:form>