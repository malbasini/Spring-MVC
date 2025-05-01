<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--
  Created by IntelliJ IDEA.
  User: malbasini
  Date: 26/01/25
  Time: 14:31
  To change this template use File | Settings | File Templates.
--%>
<br>
<c:if test="${not empty message}">
    <div class="alert alert-danger" role="alert">
            ${message}
    </div>
</c:if>
<br>
<c:if test="${not empty error}">
    <div class="alert alert-danger" role="alert">
            ${error}
    </div>
</c:if>
<br>
<h1>Crea Lezione</h1>
<br>
<form method="post" action="${pageContext.request.contextPath}/lessons">
    <input type="hidden" name="courseId" value="${courseId}">
    <div class="row">
        <div class="col-md-2">
            <label for="title" class="form-label">Titolo della lezione</label>
        </div>
        <div class="col-md-8 mb-3">
            <input type="text" id="title" name="title" class="form-control form-control-lg" placeholder="Digita il titolo...">
        </div>
        <div class="col-md-2">
            <button type="submit" class="btn btn-primary btn-lg btn-block">Crea lezione</button>
        </div>
    </div>
    <br>
    <div class="row">
        <div class="col-md-4"></div>
        <div class="col-md-8">
            <div class="g-recaptcha" data-sitekey="${sitetkey}"></div>
        </div>
    </div>
</form>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>
