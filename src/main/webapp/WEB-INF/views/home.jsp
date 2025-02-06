<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<br>
<div class="jumbotron">
    <h1 class="display-4">Benvenuto su MyCourse!</h1>
    <p class="lead">Impara in maniera facile e divertente con i nostri corsi online!<br>
        Scegli da un vasto catalogo sempre a tua disposizione.</p>
    <hr class="my-4">
    <p class="lead">
        <a class="btn btn-dark" href="courses">Sfoglia il catalogo dei corsi <i class="fas fa-angle-right"></i></a>
    </p>
</div>
<br>
<section>
    <h2>Aggiunti di recente</h2>
    <input type="hidden" name="_csrf" value="${_csrf.token}" />
    <c:forEach items="${newestCourses}" var="course">
        <hr>
        <div class="row course">
            <div class="col-md-1">
                <c:if test="${fn:contains(course.imagePath, 'uploads')}">
                    <img src="${pageContext.request.contextPath}${course.imagePath}" alt="image" width="52" height="52">
                </c:if>
                <c:if test="${not fn:contains(course.imagePath, 'uploads')}">
                    <img src="${pageContext.request.contextPath}/static/images/${course.imagePath}" alt="image" width="52" height="52">
                </c:if>
            </div>
            <div class="col-md-5">
                <h2>${course.title}</h2>
                <author>di ${course.author}</author>
            </div>
            <div class="col-md-2 rating" title="Rating">
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
            <div class="col-md-2 price-container">
                <span class="current-price">EUR ${course.currentPriceAmount}</span>
                <c:if test="${course.fullPriceAmount != course.currentPriceAmount}">
                    <br><span class="full-price">EUR ${course.fullPriceAmount}</span>
                </c:if>
            </div>
            <div class="col-md-2 d-flex align-items-center">
                <a class="btn btn-outline-primary" href="${pageContext.request.contextPath}/courses/course/${course.id}/detail">Dettaglio</a>
            </div>
        </div>
    </c:forEach>
</section>
<br>
<section>
    <h2>I migliori di sempre</h2>
    <c:forEach items="${topRatedCourses}" var="course">
        <hr>
        <div class="row course">
            <div class="col-md-1">
                <c:if test="${fn:contains(course.imagePath, 'uploads')}">
                    <img src="${pageContext.request.contextPath}${course.imagePath}" alt="image" width="52" height="52">
                </c:if>
                <c:if test="${not fn:contains(course.imagePath, 'uploads')}">
                    <img src="${pageContext.request.contextPath}/static/images/${course.imagePath}" alt="image" width="52" height="52">
                </c:if>
            </div>
            <div class="col-md-5">
                <h2>${course.title}</h2>
                <author>di ${course.author}</author>
            </div>
            <div class="col-md-2 rating" title="Rating">

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
            <div class="col-md-2 price-container">
                <span class="current-price">EUR ${course.currentPriceAmount}</span>
                <c:if test="${course.fullPriceAmount != course.currentPriceAmount}">
                    <br><span class="full-price">EUR ${course.fullPriceAmount}</span>
                </c:if>
            </div>
            <div class="col-md-2 d-flex align-items-center">
                <a class="btn btn-outline-primary" href="${pageContext.request.contextPath}/courses/course/${course.id}/detail">Dettaglio</a>
            </div>
        </div>
    </c:forEach>
</section>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>
