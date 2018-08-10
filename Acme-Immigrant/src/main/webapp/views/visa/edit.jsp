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

<form:form action="visa/admin/edit.do" method="post"
	modelAttribute="visa">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="abrogated" />
	<form:hidden path="requirements" />
	<form:hidden path="applications" />

	<acme:textbox code="visa.clase" path="clase" />

	<acme:textarea code="visa.description" path="description" />

	<acme:textbox code="visa.price" path="price" placeholder="100$" />

	<acme:select items="${categories}" itemLabel="name"
		code="visa.category" path="category" />

	<acme:select items="${countries}" itemLabel="name" code="visa.country"
		path="country" />
	<acme:cancel url="visa/admin/list.do" code="cancel.button" />
	<acme:submit name="save" code="save.button" />
</form:form>