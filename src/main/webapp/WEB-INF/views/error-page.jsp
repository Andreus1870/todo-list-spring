<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <title>Error ${status}</title>

    <link href="<c:url value="/resources/css/error-page.css"/>" rel="stylesheet">
</head>

<body>

<div class="page-wrapper">
    <div class="error-container">

        <div class="error-code">
            ${status}
        </div>

        <div class="error-title">
            Something went wrong
        </div>

        <div class="error-message">
            ${message}
        </div>

        <a href="<c:url value="/"/>" class="back-button">
            Back to ToDo List
        </a>

    </div>
</div>

</body>
</html>