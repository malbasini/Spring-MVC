<html>
<!-- Inclusione dell'header -->
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="th" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
                    <hr>
                    <input type="text" name="email" class="form-control" placeholder="Email">
                </div>
                <div class="col-md-4">
                    <label>Ruolo:</label>
                    <hr>
                    <select class="form-select form-select-md" name="role" aria-label="Small select example"
                            required="required">
                        <option value="" disabled selected>Selezionare un ruolo</option>
                        <c:forEach var="role" items="${roles}">
                            <c:if test="${role.name eq 'ROLE_ADMIN'}">
                                <option value="${role.name}">AMMINISTRATORE</option>
                            </c:if>
                            <c:if test="${role.name eq 'ROLE_EDITOR'}">
                                <option value="${role.name}">DOCENTE</option>
                            </c:if>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-md-4">
                    <label>Operazione</label>
                    <hr>
                    <div>
                        <button type="submit" formaction="${pageContext.request.contextPath}/assign" class="btn btn-success btn-md">Assegna</button>
                        <button type="submit" class="btn btn-danger btn-md" formaction="${pageContext.request.contextPath}/revoke">Revoca</button>
                    </div>
                </div>
    </div>
</form>
<br>
<!-- Tab di ruoli -->
<ul class="nav nav-tabs mb-3">
    <c:forEach var="role" items="${roles}">
        <li class="nav-item">
            <c:if test="${role.name eq 'ROLE_ADMIN'}">
            <a class="nav-link ${role.name eq activeRole ? 'active' : ''}"
               aria-current="page"
               href="${pageContext.request.contextPath}/role/${role.name}">
                    ${fn:replace(role.name, "ROLE_ADMIN", "AMMINISTRATORE")}
            </a>
            </c:if>
            <c:if test="${role.name eq 'ROLE_EDITOR'}">
                <a class="nav-link ${role.name eq activeRole ? 'active' : ''}"
                   aria-current="page"
                   href="${pageContext.request.contextPath}/role/${role.name}">
                        ${fn:replace(role.name, "ROLE_EDITOR", "DOCENTE")}
                </a>
            </c:if>
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
    </c:forEach>
</c:if>
</body>
</html>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>