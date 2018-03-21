<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head></head>
<body>
<h1>Geonote Register</h1>
<form:form action="${pageContext.request.contextPath}/user/register"
           method="POST"
           modelAttribute="simpleUser">
    <table>
        <tr>
            <td>User:</td>
            <td><form:input path="name"/></td>
        </tr>
        <tr>
            <td>Password:</td>
            <td><form:password path="password" /></td>
        </tr>
        <tr>
            <td><input type="submit" value="Register" /></td>
        </tr>
    </table>
</form:form>
</body>
</html>
