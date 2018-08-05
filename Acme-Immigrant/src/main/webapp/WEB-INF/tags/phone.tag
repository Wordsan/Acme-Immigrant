<%--
 * phone.tag
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@ tag language="java" body-content="empty" %>

<%-- Taglibs --%>

<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<%-- Attributes --%> 

<%-- Definition --%>
	<script type="text/javascript">
		$("body").append("<input type='hidden' id='confirmTel' value='${confirmTel}' />");
		//^[+]?(\d{1,3})\s\(?(\d{1,3})\)\s?(\d{4})
		var pattern = /^[+](\d{1,3})\((\d{0,3})\)(\d{4})$/;
		var confirmT = document.getElementById("confirmTel").value;
		function checkTel(tel) {
			if(tel == "") {
			
			}else if(!pattern.test(tel)) {
				return confirm(confirmT);
			}
		}
	</script>