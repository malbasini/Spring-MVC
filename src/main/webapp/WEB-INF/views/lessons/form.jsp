<%--
  Created by IntelliJ IDEA.
  User: malbasini
  Date: 20/01/25
  Time: 14:51
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<h2>${lesson.id == null ? 'Crea Nuova Lezione' : 'Modifica Lezione'}</h2>
<!-- Determiniamo l'URL di submit in base a creazione o update -->
<c:choose>
  <c:when test="${lesson.id == null}">
    <c:set var="submitUrl" value="${pageContext.request.contextPath}/lessons" />
  </c:when>
  <c:otherwise>
    <c:set var="submitUrl"
           value="${pageContext.request.contextPath}/lessons/${lesson.id}" />
  </c:otherwise>
</c:choose>
<form action="${submitUrl}" method="post">
<!-- Se stiamo creando da zero, la courseId arriva dal form hidden -->
<c:if test="${courseId != null}">
  <input type="hidden" name="courseId" value="${courseId}" />
</c:if>
  <label for="title">Titolo:</label>
  <input type="text" id="title" name="title" value="${lesson.title}" /><br/>
  <label for="description">Descrizione:</label><br/>
  <textarea id="description" name="description">${lesson.description}</textarea>
  <br/>
  <label for="duration">Durata:</label>
  <input type="text" id="duration" name="duration" value="${lesson.duration}" />
  <br/>
  <label for="orderNumber">Ordine:</label>
  <input type="number" id="orderNumber" name="orderNumber" value="${lesson.orderNumber}" /><br/>
  <button type="submit">Salva</button>
</form>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>