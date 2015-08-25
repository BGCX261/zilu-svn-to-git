<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@tag description="basic layout"%>
<%@attribute name="head" fragment="true"%>
<%@attribute name="scripts" fragment="true"%>
<%@attribute name="title" required="true" rtexprvalue="true"
	type="java.lang.String"%>
<html>
<head>
<jsp:include page="/WEB-INF/jsp/includes/comVars.jsp" flush="true" />
<title>${title}</title>
<meta content="text/html; charset=UTF-8" http-equiv="Content-Type" />
<script src="/shared/javascripts/global.js" type="text/javascript"></script>
<jsp:invoke fragment="head" />
</head>
<body>
<jsp:doBody />
<script type="text/javascript">
		var Page = {
			init: function () {
			}
		};
	</script>
<jsp:invoke fragment="scripts" />
</body>
</html>