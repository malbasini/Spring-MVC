<html>
<!-- Inclusione dell'header -->
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="th" uri="http://www.springframework.org/tags/form" %>
<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <title>Ruoli</title>
</head>
<body>
<br>
<!-- Alert Condizionale -->
<c:if test="${not empty message}">
    <div class="alert alert-success" role="alert">
            ${message}
    </div>
</c:if>
<c:if test="${not empty message1}">
    <div class="alert alert-danger" role="alert">
            ${message1}
    </div>
</c:if>
<br>
<h1>Ruoli</h1>
<br>
<form method="POST">
    <div class="row">
                <div class="col-md-4">
                    <label>Email:</label>
                    <input type="text" name="email" class="form-control" placeholder="Email">
                </div>
                <div class="col-md-4">
                    <label>Ruolo:</label>
                    <select class="form-select form-select-sm" name="role" aria-label="Small select example"
                            required="required">
                        <option value="" disabled selected>Selezionare un ruolo</option>
                        <c:forEach var="role" items="${roles}">
                            <option value="${role.name}">${role.name}</option>
                        </c:forEach>

                    </select>
                </div>
                <div class="col-md-4">
                    <label>Operazione</label>
                    <div>
                        <button type="submit" formaction="${pageContext.request.contextPath}/assign" class="btn btn-success">Assegna</button>
                        <button type="submit" class="btn btn-danger" formaction="${pageContext.request.contextPath}/revoke">Revoca</button>
                    </div>
                </div>
    </div>
</form>
<br>
<!-- Tab di ruoli -->
<ul class="nav nav-tabs mb-3">
    <c:forEach var="role" items="${roles}">
        <li class="nav-item">
            <a class="nav-link ${role.name eq activeRole ? 'active' : ''}"
               aria-current="page"
               href="${pageContext.request.contextPath}/role/${role.name}">
                    ${role.name}
            </a>
        </li>
    </c:forEach>
</ul>
<br>
<c:if test="${activeRole != null && activeRole eq 'ROLE_EDITOR'}">
    <c:forEach var="user" items="${users}">
        <div class="row">
            <div class="col-6">${user.fullname}</div>
            <div class="col-6">${user.email}</div>
        </div>
        <hr>
    </c:forEach>
</c:if>
<c:if test="${activeRole != null && activeRole eq 'ROLE_ADMIN'}">
    <c:forEach var="admin" items="${admins}">
        <div class="row">
            <div class="col-6">${admin.fullname}</div>
            <div class="col-6">${admin.email}</div>
        </div>
        <hr>
    </c:forEach>
</c:if>
</body>
</html>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>