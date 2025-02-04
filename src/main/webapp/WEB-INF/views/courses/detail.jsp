<%--
  Created by IntelliJ IDEA.
  User: malbasini
  Date: 24/01/25
  Time: 22:03
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<br>
<c:if test="${not empty message}">
    <div class="alert alert-success" role="alert">
            ${message}
    </div>
</c:if>
<br>
<section class="course-info">
    <div class="row">
        <div class="col-md-9">
            <h1>Dettaglio</h1>
        </div>
        <div class="col-md-3 d-flex align-items-center">
            <c:if test="${isOwner && isTeacher}">
                <a class="btn btn-primary btn-block" href="${pageContext.request.contextPath}/courses/${course.id}/edit">Modifica</a>&nbsp;&nbsp;&nbsp;
            </c:if>&nbsp;
            <c:if test="${isStudent && not subscription}">
                <form action="${pageContext.request.contextPath}/courses/${course.id}/${course.userOwner.id}/subscription" method="post">
                     <button type="submit" class="btn btn-warning btn-bloc">Iscriviti</button>
                </form>
            </c:if>&nbsp;
        </div>
    </div>
    <div class="row">
        <div class="col-md-9">
            ${course.description}
        </div>
        <div class="col-md-3">
            <aside>
                <author>di ${course.author}</author>
                <div class="rating" title="${course.rating}">
                    <%-- Stelle Piene --%>
                    <c:forEach var="i" begin="1" end="${course.fullStars}">
                        <i class="fas fa-star" style="color: gold;"></i>
                    </c:forEach>

                    <%-- Mezza Stella --%>
                    <c:if test="${course.hasHalfStar}">
                        <i class="fas fa-star-half-alt" style="color: gold;" ></i>
                    </c:if>

                    <%-- Stelle Vuote --%>
                    <c:forEach var="i" begin="1" end="${course.emptyStars}">
                        <i class="far fa-star" style="color: gold;"></i>
                    </c:forEach>
                </div>
                <div class="price">
                    <span class="current-price">EUR ${course.currentPriceAmount}</span>
                    <c:if test="${course.fullPriceAmount != course.currentPriceAmount}">
                        <br><span class="full-price">EUR ${course.fullPriceAmount}</span>
                    </c:if>
                </div>
                <c:if test="${fn:contains(course.imagePath, 'upload')}">
                    <img src="${pageContext.request.contextPath}${course.imagePath}" alt="image" class="img-fluid course-image">
                </c:if>
                <c:if test="${not fn:contains(course.imagePath, 'upload')}">
                    <img src="${pageContext.request.contextPath}/resources/images/${course.imagePath}" alt="image" class="img-fluid course-image">
                </c:if>
                <c:if test="${subscription && isStudent}">
                    <a href="${pageContext.request.contextPath}/courses/${course.id}/question" class="btn btn-block btn-outline-secondary mt-3">Fai una domanda</a>
                    <a href="${pageContext.request.contextPath}/courses/${course.id}/vote" class="btn btn-block btn-outline-secondary mt-3" data-bs-toggle="modal" data-bs-target="#voteModal">Valuta il corso</a>
                </c:if>
            </aside>
        </div>
    </div>
</section>
<section class="course-lessons">
    <c:if test="${(isOwner && isTeacher) || (subscription && isStudent)}">
    <h2>Lezioni</h2>
    <c:if test="${(isOwner && isTeacher)}">
           <a class ="btn btn-outline-primary btn-sm" href="${pageContext.request.contextPath}/lessons/new/${course.id}"><i class ="fas fa-plus" ></i > Crea nuova </a>
    </c:if>
        <c:forEach var="lesson" items="${course.lessons}">
        <hr />
    <div class="row">
        <div class="col-md-3 d-flex align-items-center">
            <c:if test="${(isOwner && isTeacher) || (subscription && isStudent)}">
                  <a href="${pageContext.request.contextPath}/lessons/${lesson.id}/detail">${lesson.title}</a>
            </c:if>
            <c:if test="${not (isOwner && isTeacher) && not (subscription && isStudent)}">
                ${lesson.title}
            </c:if>
        </div>
        <div class="col-md-7 d-flex align-items-center">
            <span>${lesson.description}</span>
        </div>
        <div class="col-md-2 d-flex align-items-center justify-content-end lesson-duration">
            <i class="far fa-clock"></i><time>${lesson.duration}</time>
            <c:if test="${isOwner && isTeacher}">
                <a class="btn btn-outline-primary btn-sm ml-3" href="${pageContext.request.contextPath}/lessons/${lesson.id}/edit"><i class="fas fa-pencil-alt"></i></a>
            </c:if>
        </div>
    </div>
    </c:forEach>
    <br>
    <div class="row course-duration">
        <div class="col-md-9 d-flex align-items-center">
            Totale
        </div>
        <div class="col-md-3 d-flex align-items-center justify-content-end">
            <i class="far fa-clock"></i><time>${totalDuration}</time>
        </div>
    </div>
    </c:if>
</section>
<!-- Modal -->
<div class="modal fade" id="voteModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title fs-5" id="exampleModalLabel">Esprimi la tua valutazione</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <iframe width="100%" height="100" frameBorder="0"></iframe>
            </div>
        </div>
    </div>
</div>
<script>
    $('#voteModal').on('shown.bs.modal', function(event) {
        $(this).find('iframe').attr('src', event.relatedTarget.href);
    });
</script>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>