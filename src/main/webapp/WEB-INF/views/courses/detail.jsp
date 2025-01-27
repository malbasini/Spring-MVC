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
<br>
<section class="course-info">
    <div class="row">
        <div class="col-md-9">
            <h1>Dettaglio</h1>
        </div>
        <div class="col-md-3 d-flex align-items-center">
            <a class="btn btn-primary btn-block" href="${pageContext.request.contextPath}/courses/${course.id}/edit">Modifica</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <button class="btn btn-warning btn-bloc">Iscriviti</button>
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
            </aside>
        </div>
    </div>
</section>
<section class="course-lessons">
    <h2>Lezioni</h2>
    <a class ="btn btn-outline-primary btn-sm" href="${pageContext.request.contextPath}/lessons/new/${course.id}"><i class ="fas fa-plus" ></i > Crea nuova </a>
    <c:forEach var="lesson" items="${course.lessons}">
        <hr />
    <div class="row">
        <div class="col-md-3 d-flex align-items-center">
            <a href="${pageContext.request.contextPath}/lessons/${lesson.id}/detail">${lesson.title}</a>
        </div>
        <div class="col-md-7 d-flex align-items-center">
            <span>${lesson.description}</span>
        </div>
        <div class="col-md-2 d-flex align-items-center justify-content-end lesson-duration">
            <i class="far fa-clock"></i><time>${lesson.duration}</time>
            <a class="btn btn-outline-primary btn-sm ml-3" href="${pageContext.request.contextPath}/lessons/${lesson.id}/edit"><i class="fas fa-pencil-alt"></i></a>
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
<%@ include file="/WEB-INF/views/common/footer.jsp" %>