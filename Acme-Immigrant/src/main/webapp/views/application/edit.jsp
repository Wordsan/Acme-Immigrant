

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
	$(function() {
		a = $('#datepicker');
		a.datepicker();
		$('#datepicker').on("change", function() {
			a.datepicker("option", "dateFormat", "dd/mm/yy");
		});

	});
</script>

<form:form action="application/immigrant/edit.do" method="post"
	modelAttribute="apps">
	<form:hidden path="visaId" />
	<acme:select items="${creditCards}" itemLabel="number"
		code="application.creditCard" path="creditCard" identificador="number" />
	<fieldset>
		<legend>
			<spring:message code="application.personalSection" />
		</legend>
		<acme:textarea code="personalSection.fullNames" path="fullNames" />
		<acme:textbox code="personalSection.birthPlace" path="birthPlace" />
		<spring:message code="personalSection.birthDate" />
		<form:input path="birthDate" id="datepicker" />
		<form:errors path="birthDate" cssClass="error" />
		<br>
		<acme:textarea code="personalSection.picture" path="picture" />
	</fieldset>
	<fieldset>
		<legend>
			<spring:message code="application.socialSection" />
		</legend>
		<acme:textbox code="socialSection.nickname" path="nickname" />
		<acme:textbox code="socialSection.socialNetwork" path="socialNetwork" />
		<acme:textbox code="socialSection.linkProfile" path="linkProfile" />
	</fieldset>
	<acme:cancel url="visa/search.do" code="cancel.button" />
	<acme:submit name="save" code="save.button" />
</form:form>