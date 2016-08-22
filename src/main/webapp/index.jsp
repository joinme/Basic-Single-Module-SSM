<%@ page import="com.youmeek.common.sso.model.User" %>
<%@ page import="java.util.Enumeration" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@ include file="/WEB-INF/view/common/tagPage.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>首页</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
</head>
<body>

<h6>
    <%
        out.println(session.getId());
        User user = (User) session.getAttribute("user");
        request.setAttribute("user", user);
        Enumeration attributeNames = session.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            out.println(attributeNames.nextElement());
        }
    %>


    <br>
    <%
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            out.println(cookie.getName() + " ::: " + cookie.getValue());
        }
    %>
</h6>

<h2>
    <a href="" target="_blank">Hello ${user.userName}</a>
    <br>
    <c:if test="${user != null}">
        <a href="${webRoot}/login/service/out">quit  ${user.userName}</a>
        <%--<a href="" target="_blank">quit  ${user.userName}</a>--%>
    </c:if>
</h2>

<br>


<a href="/sysUserController/showUserToJspById/1" target="_blank">查询用户信息并跳转到一个JSP页面</a>

<br>

<a href="/sysUserController/showUserToJSONById/1" target="_blank">查询用户信息并直接输出JSON数据</a>

</body>
</html>
