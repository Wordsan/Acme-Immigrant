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
		a = $("tr td:nth-child(2)");
		$(a).each(function(index, element) {
			b = element.textContent;
			c = Array(b.length - 3).join("*");
			element.textContent = c + b.substring(b.length - 4);
		});
	});
</script>

<security:authorize access="hasRole('IMMIGRANT')">
	<display:table name="cards" id="c" requestURI="${requestURI}"
		pagesize="5" class="displaytag">

		<acme:column code="cc.brandName" property="brandName" />
		<acme:column code="cc.number" property="number" />
		<acme:column code="cc.expirationMonth" property="expirationMonth" />
		<acme:column code="cc.expirationYear" property="expirationYear" />
		<acme:column code="cc.CVVCode" property="CVVCode" />
		<spring:message code="cc.edit" var="editHeader"></spring:message>
		<spring:message code="cc.editC" var="editCHeader"></spring:message>
		<display:column title="${editCHeader}">
			<spring:url
				value="creditCard/immigrant/edit.do?creditCardNumber=${c.number}"
				var="url"></spring:url>
			<a href="${url}"><jstl:out value="${editHeader}"></jstl:out></a>
		</display:column>
	</display:table>
</security:authorize>