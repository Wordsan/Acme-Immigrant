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
		var p = document.getElementById("precio");
		if (p.textContent.includes("?")) {
			p.textContent = p.textContent.replace("?", "\u20AC");
		}
	});
</script>

<acme:display code="visa.clase" value="${visa.clase}"></acme:display>

<acme:display code="visa.description" value="${visa.description}"></acme:display>

<div id="precio">
	<acme:display code="visa.price" value="${visa.price}"></acme:display>
</div>

<spring:message code="visa.category"></spring:message>
<acme:displayLink value="${visa.category.name}"
	link="category/display.do?categoryId=${visa.category.id}"></acme:displayLink>

<spring:message code="visa.country"></spring:message>
<acme:displayLink value="${visa.country.name}"
	link="country/display.do?countryId=${visa.country.id}"></acme:displayLink>

<security:authorize access="hasRole('ADMIN')">
	<spring:message code="visa.abrogated" />
	<jstl:if test="${visa.abrogated == true}">
		<spring:message code="visa.abrogated.yes"></spring:message>
	</jstl:if>

	<jstl:if test="${visa.abrogated == false}">
		<spring:message code="visa.abrogated.no"></spring:message>
		<br>
	</jstl:if>
</security:authorize>

<jstl:if test="${statistics1 != null}">
	<spring:message code="visa.statistics.uno" />
	<jstl:out value="${statistics1}" />
	<spring:message code="visa.statistics.dos" />
	<jstl:out value="${statistics2}" />
	<br>
</jstl:if>

<jstl:if test="${!empty visa.requirements}">
	<fieldset>
		<legend>
			<spring:message code="visa.requirements"></spring:message>
		</legend>
		<jstl:forEach items="${visa.requirements}" var="req">
			<acme:displayLink
				link="requirement/display.do?requirementId=${req.id}"
				value="${req.title}" />
		</jstl:forEach>
	</fieldset>
</jstl:if>

<security:authorize access="hasRole('ADMIN')">
	<br>
	<acme:button url="visa/admin/edit.do?visaId=${visa.id}" name="edit"
		code="edit.button" />
	<jstl:if test="${visa.abrogated == false}">
		<acme:button url="visa/admin/abrogate.do?visaId=${visa.id}"
			name="abrogate" code="abrogate.visa.button" />
	</jstl:if>
</security:authorize>