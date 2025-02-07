<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<br>
<!-- Alert Condizionale -->
<c:if test="${not empty message}">
    <div class="alert alert-danger" role="alert">
            ${message}
    </div>
</c:if>
<c:if test="${not empty message1}">
    <div class="alert alert-success" role="alert">
            ${message1}
    </div>
</c:if>
<c:if test="${not empty error}">
    <div class="alert alert-danger" role="alert">
            ${error}
    </div>
</c:if>
<br>
<h1>Crea nuovo corso</h1>
<br>
<form method="post" action="${pageContext.request.contextPath}/courses">
    <input type="hidden" name="_csrf" value="${_csrf.token}" />
    <div class="row">
        <div class="col-md-1">
            <label for="title" class="form-label">Titolo del corso</label>
        </div>
        <div class="col-md-7 mb-3">
            <input type="text" id="title" name="title" class="form-control form-control-lg" placeholder="Digita un titolo che attiri gli studenti..."><br/>
        </div>
        <div class="col-md-4">
            <button type="submit" class="btn btn-primary btn-lg btn-block"><i class="fas fa-rocket"></i> Crea corso!</button>
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
