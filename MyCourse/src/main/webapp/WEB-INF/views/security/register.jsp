<%--
  Created by IntelliJ IDEA.
  User: malbasini
  Date: 30/01/25
  Time: 11:19
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!-- Alert Condizionale -->
<html>
<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <title>Login</title>
</head>
<body>
<div class="row">
    <div class="col-md-4"></div>
    <div class="col-md-4">
        <c:if test="${not empty errore}">
            <div class="alert alert-danger" role="alert">
                    ${errore}
            </div>
        </c:if>
    </div>
    <div class="col-md-4"></div>
</div>
<div class="row">
    <div class="col-md-4"></div>
    <div class="col-md-4">
        <c:if test="${not empty message}">
            <div class="alert alert-success" role="alert">
                    ${message}
            </div>
        </c:if>
        <c:if test="${not empty error}">
            <div class="alert alert-danger" role="alert">
                    ${error}
            </div>
        </c:if>
    </div>
    <div class="col-md-4"></div>
</div>
<br>
<div class="row">
    <div class="col-md-4"></div>
    <div class="col-md-4">
        <h2>Registrati</h2>
    </div>
    <div class="col-md-4"></div>
</div>
<br>
<section class="course-info">
  <form action="${pageContext.request.contextPath}/doRegister" method="post">
      <div class="row">
          <div class="col-md-4"></div>
          <div class="col-md-4">
              <span class="input-group-text" id="inputGroup-sizing-sm1">Username</span>
              <br>
              <input type="text" class="form-control" placeholder="Username" value="${username}" name="username" aria-label="Small" aria-describedby="inputGroup-sizing-sm">
          </div>
          <div class="col-md-4"></div>
      </div>
      <br>
      <div class="row">
          <div class="col-md-4"></div>
          <div class="col-md-4">
              <span class="input-group-text" id="inputGroup-sizing-sm2">Nome Completo</span>
              <br>
              <input type="text" class="form-control" placeholder="Full name" name="fullname" aria-label="Small" aria-describedby="inputGroup-sizing-sm" value="${fullname}">
          </div>
          <div class="col-md-4"></div>
      </div>
      <br>
      <div class="row">
          <div class="col-md-4"></div>
          <div class="col-md-4">
              <span class="input-group-text" id="inputGroup-sizing-sm3">Password</span>
              <br>
              <input type="password" class="form-control" placeholder="Password" name="password" aria-label="Small" aria-describedby="inputGroup-sizing-sm" value="${password}">
          </div>
          <div class="col-md-4"></div>
      </div>
      <br>
      <div class="row">
          <div class="col-md-4"></div>
          <div class="col-md-4">
              <span class="input-group-text" id="inputGroup-sizing-sm4">Email</span>
              <br>
              <input type="email" class="form-control" placeholder="Email" name="email" aria-label="Small" aria-describedby="inputGroup-sizing-sm" value="${email}">
          </div>
          <div class="col-md-4"></div>
      </div>
      <br>
      <div class="row">
          <div class="col-md-4"></div>
          <div class="col-md-4">
              <span class="input-group-text" id="inputGroup-sizing-sm5">Ruolo</span>
              <br>
              <select class="form-select form-select-sm" name="roleId" aria-label="Small select example">
                  <option value="selected Open this select menu"></option>
                  <option value="ROLE_ADMIN">AMMINISTRATORE</option>
                  <option value="ROLE_STUDENT">STUDENTE</option>
                  <option value="ROLE_EDITOR">DOCENTE</option>
              </select>
          </div>
          <div class="col-md-4"></div>
      </div>
      <br>
      <br>
      <div class="row">
          <div class="col-md-4"></div>
          <div class="col-md-8">
              <div class="g-recaptcha" data-sitekey="${sitetkey}"></div>
          </div>
      </div>
      <br>
      <div class="row">
          <div class="col-md-4"></div>
          <div class="col-md-4">
              <br>
              <button type="submit"  class="btn btn-primary">Registrati</button>
          </div>
          <div class="col-md-4"></div>
      </div>
  </form>
</section>
</body>
</html>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>



