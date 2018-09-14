$(document).ready(function() {
	b = document.getElementById("errorMsg");
	if (window.location.href.search("forbbiden.access.error") != -1) {
		b.hidden = false;
	} else {
		b.hidden = true;
	}
});
