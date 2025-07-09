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
<c:if test="${not empty message1}">
    <div class="alert alert-danger" role="alert">
            ${message1}
    </div>
</c:if>
<!-- ID del corso -->
<c:set var="courseId" value="${course.id}" />
<br>
<section class="course-info">
    <div class="row">
        <div class="col-md-9">
            <h1>${course.title}
                <c:if test="${canEdit}">
                    <a class="btn btn-outline-primary btn-sm" href="${pageContext.request.contextPath}/courses/${course.id}/edit"><i class="fas fa-pencil-alt"></i> Modifica</a>&nbsp;&nbsp;&nbsp;
                </c:if>&nbsp;


            </h1>
        </div>
        <br>
                <div class="col-md-3">
                <c:if test="${isStudent or canEdit or isAdmin}">
                    <aside>
                    <br>
                    <c:if test="${subscription}">
                          <span class="alert alert-secondary btn-block">Sei iscritto al corso</span>
                    </c:if>
                    <c:if test="${canEdit and not subscription}">
                        <span class="alert alert-secondary btn-block">Sei l'autore del corso</span>
                    </c:if>
                    <c:if test="${isAdmin}">
                        <button class="btn btn-danger btn-lg btn-block" type="button"  data-bs-toggle="modal" data-bs-target="#staticBackdrop">Elimina</button>
                     </c:if>
                    <c:if test="${isStudent && not subscription}">
                        <div class="dropdown">
                                <button class="btn btn-warning dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">
                                    Iscriviti
                                </button>
                                <ul class="dropdown-menu">
                                    <li><a class="dropdown-item" href="${pageContext.request.contextPath}/payment/paypal/${courseId}/pay">Paypal</a></li>
                                    <li><a class="dropdown-item" href="${pageContext.request.contextPath}/payment/stripe/${courseId}/pay">Stripe</a></li>
                                </ul>
                        </div>
                    </c:if>&nbsp;
                </aside>
                </c:if>&nbsp;
            </div>
    </div>
    <br>
    <br>
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
                <c:if test="${fn:contains(course.imagePath, 'uploads')}">
                    <img src="${pageContext.request.contextPath}${course.imagePath}" alt="${course.imagePath}" class="img-fluid course-image">
                </c:if>
                <c:if test="${not fn:contains(course.imagePath, 'uploads')}">
                    <img src="${pageContext.request.contextPath}/static/images/${course.imagePath}" alt="image" class="img-fluid course-image">
                </c:if>
                <c:if test="${subscription && isStudent}">
                    <a href="${pageContext.request.contextPath}/courses/${course.id}/question" class="btn btn-block btn-outline-secondary mt-3">Fai una domanda</a>
                    <a href="${pageContext.request.contextPath}/courses/${course.id}/vote" class="btn btn-block btn-outline-secondary mt-3" data-bs-toggle="modal" data-bs-target="#voteModal">Valuta il corso</a>
                </c:if>
            </aside>
        </div>
    </div>
</section>
<section>
       <form method="post" action="${pageContext.request.contextPath}/courses/${course.id}/delete">
           <input type="hidden" name="_csrf" value="${_csrf.token}" />
           <!-- Modal -->
           <div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
               <div class="modal-dialog modal-dialog-centered">
                   <div class="modal-content">
                       <div class="modal-header">
                           <h1 class="modal-title fs-5" id="staticBackdropLabel">Elimina Corso</h1>
                           <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                       </div>
                       <div class="modal-body">
                           Stai per eliminare il corso e tutte le lezioni associate. Sei sicuro?
                       </div>
                       <div class="modal-footer">
                           <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                           <button type="submit" class="btn btn-primary">Elimina</button>
                       </div>
                   </div>
               </div>
           </div>
       </form>
   </section>
<section class="course-lessons">
<c:if test="${(canEdit or subscription)}">
            <h2>Lezioni</h2>
            <c:if test="${(canEdit)}">
                   <a class ="btn btn-outline-primary btn-sm" href="${pageContext.request.contextPath}/lessons/new/${course.id}"><i class ="fas fa-plus" ></i > Crea nuova </a>
            </c:if>
                <c:forEach var="lesson" items="${course.lessons}">
                <hr />
            <div class="row">
                <div class="col-md-3 d-flex align-items-center">
                    <c:if test="${(canEdit) || (subscription)}">
                          <a href="${pageContext.request.contextPath}/lessons/${lesson.id}/detail">${lesson.title}</a>
                    </c:if>
                    <c:if test="${not (canEdit) and not (subscription)}">
                        ${lesson.title}
                    </c:if>
                </div>
                <div class="col-md-7 d-flex align-items-center">
                    <span>${lesson.description}</span>
                </div>
                <div class="col-md-2 d-flex align-items-center justify-content-end lesson-duration">
                    <i class="far fa-clock"></i><time>${lesson.duration}</time>
                    <c:if test="${canEdit}">
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
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js" integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy" crossorigin="anonymous"></script>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>