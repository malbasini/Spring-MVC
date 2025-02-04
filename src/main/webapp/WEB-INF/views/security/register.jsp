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
<br>
<c:if test="${not empty errore}">
    <div class="alert alert-danger" role="alert">
            ${errore}
    </div>
</c:if>
<c:if test="${not empty message}">
    <div class="alert alert-success" role="alert">
            ${message}
    </div>
</c:if>
<br>
<h2>Registrati</h2>
<br>
<section class="course-info">
  <form action="${pageContext.request.contextPath}/doRegister" method="post">
      <div class="row">
          <div class="input-group input-group-sm mb-3 col-md-3">
              <div class="input-group-prepend">
                  <span class="input-group-text" id="inputGroup-sizing-sm">Username</span>
              </div>
              <input type="text" class="form-control" placeholder="Username" name="username" aria-label="Small" aria-describedby="inputGroup-sizing-sm">
          </div>
          <div class="input-group input-group-sm mb-3 col-md-3">
              <div class="input-group-prepend">
                  <span class="input-group-text" id="inputGroup-sizing-sm6">Fullname</span>
              </div>
              <input type="text" class="form-control" placeholder="Full name" name="fullname" aria-label="Small" aria-describedby="inputGroup-sizing-sm">
          </div>
          <div class="input-group input-group-sm mb-3 col-md-3">
              <div class="input-group-prepend">
                  <span class="input-group-text" id="inputGroup-sizing-sm1">Password</span>
              </div>
              <input type="password" class="form-control" placeholder="Password" name="password" aria-label="Small" aria-describedby="inputGroup-sizing-sm">
          </div>
          <div class="input-group input-group-sm mb-3 col-md-3">
              <div class="input-group-prepend">
                  <span class="input-group-text" id="inputGroup-sizing-sm2">Email</span>
              </div>
              <input type="email" class="form-control" placeholder="Email" name="email" aria-label="Small" aria-describedby="inputGroup-sizing-sm">
          </div>
      </div>
      <div class="row">
          <div class="input-group input-group-sm mb-3 col-md-4">
              <div class="input-group-prepend">
                  <span class="input-group-text" id="inputGroup-sizing-sm3">Ruolo</span>
                  <select class="form-select form-select-sm" name="roleId" aria-label="Small select example">
                      <option selected>Open this select menu</option>
                      <option value="ROLE_TEACHER">ROLE_TEACHER</option>
                      <option value="ROLE_STUDENT">ROLE_STUDENT</option>
                  </select>
             </div>
          </div>
             <div class="input-group input-group-sm mb-3 col-md-4">
                 <button type="submit" class="btn btn-outline-secondary">Registrati</button>
             </div>
      </div>
  </form>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>
</section>



