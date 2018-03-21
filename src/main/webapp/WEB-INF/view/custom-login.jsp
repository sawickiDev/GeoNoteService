<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head></head>
<body>
<h1>Geonote Login</h1>
<form:form action="${pageContext.request.contextPath}/authenticateTheUser" method='POST'>
    <table>
        <tr>
            <td>User:</td>
            <td><input type='text' name='username'></td>
        </tr>
        <tr>
            <td>Password:</td>
            <td><input type='password' name='password' /></td>
        </tr>
    </table>
    <div>
        <a href="${pageContext.request.contextPath}/user/register-form">Register</a>
    </div>
    <input type="submit" value="submit" />
</form:form>
</body>
</html>
