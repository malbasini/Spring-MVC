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
<!-- Alert Condizionale -->
<br>
<c:if test="${not empty message}">
    <div class="alert alert-danger" role="alert">
        ${message}
    </div>
</c:if>
<c:if test="${empty message}">
    <div class="alert alert-success" role="alert">
            ${message}
    </div>
</c:if>
<br>
<section class="course-info">
    <form method="post" action="${pageContext.request.contextPath}/courses/${course.id}">
        <div class="row">
            <div class="col-md-9">
                <h1>Aggiorna Corso</h1>
            </div>
            <div class="col-md-3 d-flex align-items-center">
                <button type="submit" class="btn btn-primary btn-lg btn-block">Salva</button>&nbsp;&nbsp;&nbsp;&nbsp;
                <button class="btn btn-danger btn-lg btn-block" type="button" data-bs-toggle="modal" data-bs-target="#delete-modal"><i class="fas fa-trash-alt"></i></button>
            </div>
        </div>
        <input type="hidden" ${course.id}>
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
                                    <img id="imagePath" name="imagePath" src="${pageContext.request.contextPath}${course.imagePath}" alt="image" class="img-fluid course-image"">
                                </c:if>
                                <c:if test="${not fn:contains(course.imagePath, 'uploads')}">
                                    <img id="imagePath" name="imagePath" src="${pageContext.request.contextPath}/resources/images/${course.imagePath}" alt="image" class="img-fluid course-image">
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
                <br>
                <label for="imageFile">Immagine Prodotto:</label>
                <br>
                <input type="file" class="btn-btn-danger" name="imageFile" id="imageFile" /><br><br>
                <button type="submit" class="btn btn-dark">Carica</button>
            </form>
        </div>
    </div>
    <section>
    <form method="post" action="${pageContext.request.contextPath}/courses/${course.id}/delete">
        <div id="delete-modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="delete-modal-title" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="delete-modal-title">Conferma eliminazione</h5>
                        <button class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <p>Stai per eliminare il corso e tutte le lezioni associate e non potrai recuperarlo. Confermi l'eliminazione?</p>
                    </div>
                    <div class="modal-footer justify-content-between">
                        <button class="btn btn-light" type="button" data-dismiss="modal">Annulla</button>
                        <button type="submit" class="btn btn-danger">Conferma</button>
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
<%@ include file="/WEB-INF/views/common/footer.jsp" %> <!-- This is the footer of the page -->