

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
		a.datepicker({
			changeYear : true,
			yearRange : "1930:2018"
		});
		$('#datepicker').on("change", function() {
			a.datepicker("option", "dateFormat", "mm/dd/yy");
		});
		b = $("#creditCard");
		$(b.children()).each(function(index, element) {
			c = element.textContent;
			d = Array(c.length - 3).join("*");
			element.textContent = d + c.substring(c.length - 4);
		});

	});
</script>

<form:form action="${formURI}" method="post"
	modelAttribute="applicationSections" id="form" name="form">
	<form:hidden path="id" />
	<form:hidden path="visaId" />
	<form:hidden path="socialSectionId" />
	<form:label path="creditCard">
		<spring:message code="application.creditCard" />
	</form:label>
	<form:select id="creditCard" path="creditCard">
		<form:option value="null">---</form:option>
		<form:options items="${creditCards}" itemLabel="number" />
	</form:select>
	<form:errors path="creditCard" cssClass="error" />
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
		<acme:textarea code="personalSection.picture" path="picture"
			placeholder="https://imgur.es/skdjfls.jpeg" />
	</fieldset>
	<fieldset>
		<legend>
			<spring:message code="application.socialSection" />
		</legend>
		<acme:textbox code="socialSection.nickname" path="nickname" />
		<acme:textbox code="socialSection.socialNetwork" path="socialNetwork" />
		<acme:textbox code="socialSection.linkProfile" path="linkProfile"
			placeholder="https://facebook.com/JohnDoe" />
	</fieldset>
	<acme:cancel url="visa/search.do" code="cancel.button" />
	<acme:submit name="save" code="save.button" />
</form:form>