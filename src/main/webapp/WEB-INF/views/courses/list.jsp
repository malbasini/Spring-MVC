<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<!-- Alert Condizionale -->
<br>
<c:if test="${not empty message}">
    <div class="alert alert-success" role="alert">
            ${message}
    </div>
</c:if>
<br>
<% String pageTitle = "Catalogo dei corsi"; request.setAttribute("pageTitle", pageTitle); %>
<div class="row">
    <div class="col-md-8">
            <c:if test="${isTeacher}">
                <div>
                    <h2><%=request.getAttribute("pageTitle")%> <a href="${pageContext.request.contextPath}/courses/new" class="btn btn-outline-primary btn-sm"><i class="fa fa-plus"></i> Crea nuovo </a></h2>
                </div>
            </c:if>
            <!-- Qui dentro verrà mostrato solo se l'utente loggato ha 'ROLE_TEACHER' -->
    </div>
    <div class="col-md-4 d-flex align-items-center">
        <form method="get" action="${pageContext.request.contextPath}/courses">
            <div class="input-group">
                <input type="text" name="title" value="${titleFilter}" placeholder="Cerca..." aria-label="Cerca..." aria-describedby="search-button">
                <button class="input-group-text" id="search"><i class="fas fa-search"></i></button>
            </div>
        </form>
    </div>
</div>
<br>
<div class="row">
    <div class="col-md-1"></div>
    <div class="col-md-5">
        <a href="?page=${currentPage}&size=10&title=${titleFilter}&sortBy=title&sortDirection=${sortBy == 'title' && sortDirection == 'asc' ? 'desc' : 'asc'}">
            Titolo
            <c:choose>
                <c:when test="${sortBy == 'title' && sortDirection == 'asc'}">
                    <i class="fas fa-caret-up"></i>
                </c:when>
                <c:when test="${sortBy == 'title' && sortDirection == 'desc'}">
                    <i class="fas fa-caret-down"></i>
                </c:when>
            </c:choose>
        </a>
    </div>
    <div class="col-md-2">
        <a href="?page=${currentPage}&size=10&title=${titleFilter}&sortBy=rating&sortDirection=${sortBy == 'rating' && sortDirection == 'asc' ? 'desc' : 'asc'}">
            Valutazione
            <c:choose>
                <c:when test="${sortBy == 'rating' && sortDirection == 'asc'}">
                    <i class="fas fa-caret-up"></i>
                </c:when>
                <c:when test="${sortBy == 'rating' && sortDirection == 'desc'}">
                    <i class="fas fa-caret-down"></i>
                </c:when>
            </c:choose>
        </a>
    </div>
    <div class="col-md-2">
        <a href="?page=${currentPage}&size=10&title=${titleFilter}&sortBy=currentPriceAmount&sortDirection=${sortBy == 'currentPriceAmount' && sortDirection == 'asc' ? 'desc' : 'asc'}">
            Prezzo
            <c:choose>
                <c:when test="${sortBy == 'currentPriceAmount' && sortDirection == 'asc'}">
                    <i class="fas fa-caret-up"></i>
                </c:when>
                <c:when test="${sortBy == 'currentPriceAmount' && sortDirection == 'desc'}">
                    <i class="fas fa-caret-down"></i>
                </c:when>
            </c:choose>
        </a>
    </div>
    <div class="col-md-2"></div>
</div>
    <c:forEach var="course" items="${courses}">
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
<br/>
<nav aria-label="Page navigation">
    <ul class="pagination justify-content-center">
        <li class="page-item ${currentPage == 0 ? 'disabled' : ''}">
            <a class="page-link" href="?page=${currentPage - 1}&size=10&title=${titleFilter}&sortBy=${sortBy}&sortDirection=${sortDirection}" aria-label="Previous">
                <span aria-hidden="true">&laquo;</span>
            </a>
        </li>

        <c:forEach begin="0" end="${totalPages - 1}" var="i">
            <li class="page-item ${i == currentPage ? 'active' : ''}">
                <a class="page-link" href="?page=${i}&size=10&title=${titleFilter}&sortBy=${sortBy}&sortDirection=${sortDirection}">
                        ${i + 1}
                </a>
            </li>
        </c:forEach>

        <li class="page-item ${currentPage == totalPages - 1 ? 'disabled' : ''}">
            <a class="page-link" href="?page=${currentPage + 1}&size=10&title=${titleFilter}&sortBy=${sortBy}&sortDirection=${sortDirection}" aria-label="Next">
                <span aria-hidden="true">&raquo;</span>
            </a>
        </li>
    </ul>
</nav>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>
