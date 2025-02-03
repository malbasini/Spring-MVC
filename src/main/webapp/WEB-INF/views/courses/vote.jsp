<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%--
  Created by IntelliJ IDEA.
  User: malbasini
  Date: 26/01/25
  Time: 05:57
  To change this template use File | Settings | File Templates.
--%>
<form action="${pageContext.request.contextPath}/courses/${courses.id}/vote" method="post" class="text-center" target="_top">
    <c:forEach var="i" begin="1" end="5">
        <button name="vote" id="vote" type="submit" class="border-0 bg-transparent mx-0 display-4 text-warning" value="${i}" onmouseover="highlightVote(${i})" onmouseout="resetVote()">
            <c:set var="actionValue" value="${i}" />
            <i class="far fa-star"></i>
        </button>
    </c:forEach>
</form>
<script>
  function highlightVote(vote) {
    const buttons = $('button[name="vote"]');
    buttons.slice(0, vote).find('i').addClass('fas').removeClass('far');
    buttons.slice(vote).find('i').addClass('far').removeClass('fas');
  }

  function resetVote() {
    highlightVote(${vote});
  }
  resetVote();
</script>
}
<%@ include file="/WEB-INF/views/common/footer.jsp" %>