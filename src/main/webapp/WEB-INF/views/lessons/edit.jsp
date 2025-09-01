<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--
  Created by IntelliJ IDEA.
  User: malbasini
  Date: 26/01/25
  Time: 18:46
  To change this template use File | Settings | File Templates.
--%>
<!-- Alert Condizionale -->
<br>
<c:if test="${not empty message}">
  <div class="alert alert-danger" role="alert">
      ${message}
  </div>
</c:if>
<br>
<section class="lesson-info">
  <form method="post" action="${pageContext.request.contextPath}/lessons/${lesson.id}/${lesson.course.id}">
    <input type="hidden" id="CourseId" name="CourseId" value="${courseId}">
    <div class="row">
      <div class="col-md-9">
        <h1>Modifica</h1>
      </div>
      <div class="col-md-3 d-flex align-items-center">
        <button type="submit" class="btn btn-primary btn-lg btn-block">Salva</button>&nbsp;&nbsp;&nbsp;&nbsp;
        <button class="btn btn-danger btn-lg ml-3" type="button" data-bs-toggle="modal" data-bs-target="#staticBackdrop"><i class="fas fa-trash-alt"></i></button>
      </div>
    </div>
    <div class="row">
      <div class="col-md-9">
        <div class="form-group">
          <label>Titolo</label>
          <input type="text" id="title" name="title" class="form-control form-control-lg" value="${lesson.title}">
        </div>
        <br>
        <div class="form-group">
          <label>Descrizione</label>
          <textarea id="description" name="description" class="form-control" rows="18">${lesson.description}</textarea>
        </div>
      </div>
      <div class="col-md-3">
        <aside>
          <div class="form-group">
            <label>Duration</label>
            <input type="time" id="duration" name="duration" value="${lesson.duration}" class="form-control" step="1">
          </div>
        </aside>
      </div>
    </div>
  </form>
  <form method="post" action="${pageContext.request.contextPath}/lessons/${lesson.id}/delete">
    <!-- Modal -->
    <div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
      <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
          <div class="modal-header">
            <h1 class="modal-title fs-5" id="staticBackdropLabel">Elimina lezione</h1>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
            Stai per eliminare la lezione. Sei sicuro?
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
<%@ include file="/WEB-INF/views/common/footer.jsp" %>