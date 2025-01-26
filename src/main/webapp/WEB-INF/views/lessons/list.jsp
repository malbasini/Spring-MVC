<%--
  Created by IntelliJ IDEA.
  User: malbasini
  Date: 20/01/25
  Time: 14:45
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<h2>Lezioni per il Corso: ${course.title}</h2>
<table border="1">
    <thead>
    <tr>
        <th>ID</th>
        <th>Titolo</th>
        <th>Durata</th>
        <th>Ordine</th>
        <th>Azioni</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="lesson" items="${lessons}">
    <tr>
        <td>${lesson.id}</td>
        <td>${lesson.title}</td>
        <td>${lesson.duration}</td>
        <td>${lesson.orderNumber}</td>
        <td>
            <form action="${pageContext.request.contextPath}/lessons/${lesson.id}/edit" method="get">
                <button type="submit">Modifica</button>
            </form>
            <form>
                action="${pageContext.request.contextPath}/lessons/${lesson.id}/delete"
                method="post">
                <button type="submit" onclick="return confirm('Sei sicuro?')">Elimina</button>
            </form>
        </td>
    </tr>
    </c:forEach>
    </tbody>
</table>
<br/>
<a href="${pageContext.request.contextPath}/lessons/new?courseId=${course.id}">Crea Nuova Lezione</a>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>