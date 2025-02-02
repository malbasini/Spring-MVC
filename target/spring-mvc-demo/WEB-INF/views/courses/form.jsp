<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<h2>${course.id == null ? 'Crea Nuovo Corso' : 'Modifica Corso'}</h2>
<form action="${course.id == null ? pageContext.request.contextPath + '/courses': pageContext.request.contextPath + '/courses/' + course.id }" method="post">
    <label for="title">Titolo:</label>
    <input type="text" id="title" name="title" value="${course.title}" /><br/>
    <label for="author">Autore:</label>
    <input type="text" id="author" name="author" value="${course.author}" /><br/>
    <label for="email">Email:</label>
    <input type="text" id="email" name="email" value="${course.email}" /><br/>
    <label for="fullPriceAmount">Full Price :</label>
    <input type="number" id="fullPriceAmount" name="fullPriceAmount" value="${course.fullPriceAmount}" /><br/>
    <label for="fullPriceCurrency">Currency Full Price :</label>
    <input type="text" id="fullPriceCurrency" name="fullPriceCurrency" value="${course.fullPriceCurrency}" /><br/>
    <label for="currentPriceAmount">Current Price :</label>
    <input type="number" id="currentPriceAmount" name="currentPriceAmount" value="${course.currentPriceAmount}" /><br/>
    <label for="currentPriceCurrency">Currency Full Price :</label>
    <input type="text" id="currentPriceCurrency" name="currentPriceCurrency" value="${course.currentPriceCurrency}" /><br/>
    <label for="description">Descrizione:</label><br/>
    <textarea id="description" name="description">${course.description}</textarea>
    <br/>
    <button type="submit">Salva</button>
</form>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>
