<%--
  Created by IntelliJ IDEA.
  User: malbasini
  Date: 25/01/25
  Time: 07:48
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!-- Alert Condizionale -->
<br>
<c:if test="${not empty message}">
    <div class="alert alert-danger" role="alert">
        ${message}
    </div>
</c:if>
<c:if test="${not empty message1}">
    <div class="alert alert-success" role="alert">
            ${message1}
    </div>
</c:if>
<br>
<section class="course-info">
    <form method="post" action="${pageContext.request.contextPath}/courses/${course.id}/${iduser}">
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <div class="row">
            <div class="col-md-9">
                <h1>Aggiorna Corso</h1>
            </div>
            <c:if test="${isOwner && isTeacher}">
                <!-- Qui dentro verrà mostrato solo se l'utente loggato ha 'ROLE_TEACHER' -->
                <div class="col-md-3 d-flex align-items-center">
                    <button type="submit" class="btn btn-primary btn-lg btn-block">Salva</button>&nbsp;&nbsp;&nbsp;&nbsp;
                    <button class="btn btn-danger btn-lg btn-block" type="button"  data-bs-toggle="modal" data-bs-target="#staticBackdrop"><i class="fas fa-trash-alt"></i></button>
                </div>
            </c:if>&nbsp;
        </div>
        <input type="hidden" ${course.id}>
        <input type="hidden" id="iduser" name="iduser" value="${iduser}">
        <div class="row">
            <div class="col-md-9">
                <div class="form-group">
                    <label>Titolo</label>
                    <input type="text" id="title" name="title" readonly="readonly"   class="form-control form-control-lg" value="${course.title}">
                </div>
                <br>
                <c:if test="${empty course.author}">
                    <div class="form-group">
                        <label>Autore</label>
                        <input type="text" id="author" name="author" class="form-control form-control-lg" value="${course.author}">
                    </div>
                </c:if>
                <c:if test="${not empty course.author}">
                    <div class="form-group">
                        <label>Autore</label>
                        <input type="text" name="author" class="form-control form-control-lg" value="${course.author}" readonly="readonly">
                    </div>
                </c:if>
                <br>
                <div class="form-group">
                    <label>Descrizione</label>
                    <textarea name="description" id="description" class="form-control" rows="18">${course.description}</textarea>
                </div>
            </div>
            <div class="col-md-3">
                <aside>
                    <div class="form-group">
                        <label>Email</label>
                        <input type="email" id="email" name="email" value="${course.email}" class="form-control">
                    </div>
                    <div class="form-group">
                        <label asp-for="FullPrice">Full Price</label>
                        <div class="input-group">
                            <text class="form-control" id="fullPriceCurrency" name="fullPriceCurrency" readonly="true">${course.fullPriceCurrency}</text>
                            <input type="number" step="any" id="fullPriceAmount" name="fullPriceAmount" value="${course.fullPriceAmount}" class="form-control form-control-block">
                        </div>
                    </div>
                    <div class="form-group">
                        <label>Current Price</label>
                        <div class="input-group">
                            <text class="form-control" id="currentPriceCurrency" name="currentPriceCurrency" readonly="true">${course.currentPriceCurrency}</text>
                            <input type="number" step="any" id="currentPriceAmount" name="currentPriceAmount" value="${course.currentPriceAmount}" class="form-control form-control-block">
                        </div>
                    </div>
                    <div class="form-group">
                        <label>Immagine</label>
                        <div class="input-group">
                                <c:if test="${fn:contains(course.imagePath, 'uploads')}">
                                    <img id="imagePath" name="imagePath" src="${pageContext.request.contextPath}${course.imagePath}" alt="image" class="img-fluid course-image">
                                </c:if>
                                <c:if test="${not fn:contains(course.imagePath, 'uploads')}">
                                    <img id="imagePath" name="imagePath" src="${pageContext.request.contextPath}/static/images/${course.imagePath}" alt="image" class="img-fluid course-image">
                                </c:if>
                        </div>
                    </div>
                </aside>
            </div>
        </div>
    </form>
</section>
    <div class="row">
        <div class="col-md-9"></div>
        <div class="col-md-3 form-group">
            <form action="${pageContext.request.contextPath}/courses/${course.id}/uploadImage" method="post" enctype="multipart/form-data">
                <input type="hidden" name="_csrf" value="${_csrf.token}" />
                    <c:if test="${isOwner && isTeacher}">
                        <br>
                        <label>Immagine Prodotto:</label>
                        <br>
                        <div class="form-group">
                            <div class="preview-container">
                                <input type="hidden" value="${pageContext.request.contextPath}${course.imagePath}">
                                <img id="preview" src=src="${pageContext.request.contextPath}${course.imagePath}">
                            </div>
                            <div class="form-group">
                                <input type="file" id="imageFile" name="imageFile" class="btn btn-success" src="${pageContext.request.contextPath}${course.imagePath}" onchange="updatePreview(this)" />
                                <br>
                                <br>
                                <button type="submit" id="btnUpload" value="Upload Files" class="btn btn-warning"><i class="fas fa-cog"></i>Upload</button>
                            </div>
                        </div>
                    </c:if>&nbsp;
            </form>
        </div>
    </div>
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
<script>
    $(document).ready(function() {
        $('#description').summernote({
            height: 300,                 // Altezza dell'editor
            focus: true,                 // Posiziona il focus sull'editor
            placeholder: '',  // Testo di esempio
            toolbar: [
                ['style', ['bold', 'italic']],
                ['para', ['ul', 'ol']],
                ['insert', ['video']],
                ['view', ['codeview']]
            ]
        });
    });
</script>
<script>
    function updatePreview(inputFile) {
        var reader = new FileReader();
        reader.onload = function (e) {
            $('#preview').attr('src', e.target.result);
        };
        reader.readAsDataURL(inputFile.files[0]);
    }
</script>
<%@ include file="/WEB-INF/views/common/footer.jsp" %> <!-- This is the footer of the page -->