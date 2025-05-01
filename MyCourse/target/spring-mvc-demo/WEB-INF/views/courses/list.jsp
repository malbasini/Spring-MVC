<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<% String pageTitle = "Lista Corsi"; request.setAttribute("pageTitle", pageTitle); %>

<h2>Elenco Corsi</h2>
<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>Titolo</th>
        <th>Autore</th>
        <th>Azioni</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="course" items="${courses}">
    <tr>
        <td>${course.id}</td>
        <td>${course.title}</td>
        <td>${course.author}</td>
        <td>
            <form
                    action="${pageContext.request.contextPath}/courses/${course.id}/edit" method="get">
                <button type="submit">Modifica</button>
            </form>
            <form
                    action="${pageContext.request.contextPath}/courses/${course.id}/delete"
                    method="post">
                <button type="submit" onclick="return confirm('Sei sicuro?')">Elimina</button>
            </form>
        </td>
    </tr>
    </c:forEach>
    </tbody>
</table>
<br/>
<a href="${pageContext.request.contextPath}/courses/new">Crea Nuovo Corso</a>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>
