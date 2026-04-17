<%--
  Created by IntelliJ IDEA.
  User: malbasini
  Date: 05/02/25
  Time: 18:14
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<br>
<!-- Alert Condizionale -->
<c:if test="${not empty message}">
  <div class="alert alert-danger" role="alert">
      ${message}
  </div>
</c:if>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>
