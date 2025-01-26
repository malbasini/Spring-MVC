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
<form target="_top">
   <c:forEach var="course" items="${courses}">
       <button name="Vote" type="submit" class="border-0 bg-transparent mx-0 display-4 text-warning" value="@i" onmouseover="highlightVote(@i)" onmouseout="resetVote()">
           <i class="far fa-star"></i>
       </button>
   </c:forEach>
</form>
<script>
  function highlightVote(vote) {
    const buttons = $('button[name="Vote"]');
    buttons.slice(0, vote).find('i').addClass('fas').removeClass('far');
    buttons.slice(vote).find('i').addClass('far').removeClass('fas');
  }

  function resetVote() {
    highlightVote(@Model.Vote);
  }
  resetVote();
</script>
}
<%@ include file="/WEB-INF/views/common/footer.jsp" %>