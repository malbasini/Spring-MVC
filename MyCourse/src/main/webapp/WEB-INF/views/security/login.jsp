
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<br>
<div class="row">
  <div class="col-md-4"></div>
  <div class="col-md-4">
    <!-- Alert Condizionale -->
    <c:if test="${param.error != null}">
      <div class="alert alert-danger" role="alert">
        <p>I dati inseriti non sono corretti</p>
      </div>
    </c:if>
  </div>
  <div class="col-md-4"></div>
</div>
<div class="row">
  <div class="col-md-4"></div>
  <div class="col-md-4">
    <c:if test="${param.logout != null}">
      <div class="alert alert-success" role="alert">
        You have been logged out successfully.
      </div>
    </c:if>
  </div>
  <div class="col-md-4"></div>
</div>
<br>
<div class="row">
  <div class="col-md-4"></div>
  <div class="col-md-4">
    <h2>Login</h2>
  </div>
  <div class="col-md-4"></div>
</div>
<br>
<section class="course-info">
<form:form action="${pageContext.request.contextPath}/doLogin" modelAttribute="userForm" method="post">
  <div class="row">
    <div class="col-md-4"></div>
    <div class="col-md-4">
      <span class="input-group-text" id="inputGroup-sizing-sm">Username</span>
      <br>
      <form:input path="username" type="text" id="username" name="username" class="form-control" autocomplete="false"/>
    </div>
    <div class="col-md-4"></div>
  </div>
  <br>
  <div class="row">
    <div class="col-md-4"></div>
    <div class="col-md-4">
      <span class="input-group-text" id="inputGroup-sizing-sm1">Password</span>
      <br>
      <form:input path="password" type="password" id="password" name="password" class="form-control" autocomplete="false"/>
    </div>
    <div class="col-md-4"></div>
  </div>
  <br>
  <div class="row">
    <div class="form-check col-md-4"></div>
    <div class="col-md-4">
      <!-- Deve essere name="remember-me" -->
      <input type="checkbox" id="remember-me" name="remember-me" />
      <label for="remember-me">Ricordami</label>
    </div>
    <div class="col-md-4"></div>
  </div>
  <br>
  <div class="row">
    <div class="col-md-4">
    </div>
    <div class="col-md-4">
      <button type="submit" class="btn btn-primary">Login</button>
    </div>
    <div class="col-md-4"></div>
  </div>
  </form:form>
</section>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>
