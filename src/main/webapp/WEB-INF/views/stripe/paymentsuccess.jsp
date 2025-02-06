<%--
  Created by IntelliJ IDEA.
  User: malbasini
  Date: 05/02/25
  Time: 18:18
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<br>
  <div class="alert alert-info" role="alert">
      Pagamento avvenuto con successo!
  </div>
<br>
<div class="col-md-12 mt-3">
    <a href="${pageContext.request.contextPath}/courses/course/${subscription.course.id}/detail"><i class="fas fa-arrow-left"></i> Torna al corso che hai acquistato</a>
</div>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>
