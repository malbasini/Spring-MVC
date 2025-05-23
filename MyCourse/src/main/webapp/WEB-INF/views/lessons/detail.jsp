<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--
  Created by IntelliJ IDEA.
  User: malbasini
  Date: 26/01/25
  Time: 22:12
  To change this template use File | Settings | File Templates.
--%>
<br>
<section class="lesson-info">
    <div class="row">
        <div class="col-md-12">
            <h1>${lesson.title}</h1>
            <br>
            <p>Durata stimata: ${lesson.duration}</p>
        </div>
        <div class="col-md-12">
            ${lesson.description}
        </div>
        <div class="col-md-12 mt-3">
            <a href="${pageContext.request.contextPath}/courses/course/${lesson.course.id}/detail"><i class="fas fa-arrow-left"></i> Torna al corso</a>
        </div>
    </div>
</section>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>