<%--
  Created by IntelliJ IDEA.
  User: malbasini
  Date: 03/02/25
  Time: 20:04
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<br>
<c:if test="${not empty message}">
    <div class="alert alert-danger" role="alert">
            ${message}
    </div>
</c:if>
<br>
<h1>Fai una domanda a ${courses.author} docente di ${courses.title}</h1>
<div class="row">
    <div class="col-12">
        <form method="POST" action="${pageContext.request.contextPath}/courses/${courses.id}/sendquestion">
            <label>Fai una domanda</label>
            <textarea name="question" class="form-control mb-3" rows="10"></textarea>
            <button type="submit" class="btn btn-primary mt-3"><i class="far fa-paper-plane"></i> Invia domanda</button>
            <br>
                <div class="row">
                    <div class="col-md-4"></div>
                    <div class="col-md-8">
                        <div class="g-recaptcha" data-sitekey="${sitetkey}"></div>
                    </div>
                </div>
        </form>
    </div>
</div>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>