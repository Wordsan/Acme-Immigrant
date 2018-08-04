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

<security:authorize access="hasRole('IMMIGRANT')">
<form:form action="creditCard/immigrant/edit.do" method="post"
	modelAttribute="creditCard">
	<form:hidden path="holderName" />

	<acme:textbox code="cc.brandName" path="brandName" placeholder="VISA|MASTERCARD|DINNERS|AMEX" />

	<acme:textbox code="cc.number" path="number" />

	<acme:textbox code="cc.expirationMonth" path="expirationMonth" />

	<acme:textbox code="cc.expirationYear" path="expirationYear" />
	
	<acme:textbox code="cc.CVVCode" path="CVVCode"/>

	<acme:submit name="save" code="save.button" />
	<acme:cancel url="/welcome/index.do" code="cancel.button" />
</form:form>
</security:authorize>