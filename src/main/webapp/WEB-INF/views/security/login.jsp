<%--
  Created by IntelliJ IDEA.
  User: malbasini
  Date: 30/01/25
  Time: 11:18
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<br>
<!-- Alert Condizionale -->
<c:if test="${param.error != null}">
  <div class="alert alert-danger" role="alert">
    <p>I dati inseriti non sono corretti</p>
  </div>
</c:if>
<c:if test="${param.logout != null}">
  <div class="alert alert-success" role="alert">
    You have been logged out successfully.
  </div>
</c:if>
<br>
<h2>Login</h2>
<br>
<section class="course-info">
<form:form action="${pageContext.request.contextPath}/doLogin" modelAttribute="userForm" method="post">
    <div class="row">
      <div class="col-md-4"></div>
      <div class="input-group input-group-sm mb-3 col-md-4">
        <div class="input-group-prepend">
          <span class="input-group-text" id="inputGroup-sizing-sm">Username</span>
        </div>
        <form:input path="username" type="text" id="username" name="username" class="form-control" autocomplete="false"/>
      </div>
      <div class="input-group input-group-sm mb-3 col-md-4">
        <div class="input-group-prepend">
          <span class="input-group-text" id="inputGroup-sizing-sm1">Password</span>
        </div>
        <form:input path="password" type="password" id="password" name="password" class="form-control" autocomplete="false"/>
      </div>
    </div>
    <br>
    <div class="row">
      <div class="col-md-4">
        <div class="form-group">
          <button type="submit" class="btn btn-outline-secondary">Login</button>
        </div>
      </div>
      <div class="col-md-8"></div>
    </div>
  </form:form>
  <%@ include file="/WEB-INF/views/common/footer.jsp" %>
</section>

