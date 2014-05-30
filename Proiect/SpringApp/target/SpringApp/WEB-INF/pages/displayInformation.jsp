<%--
  Created by IntelliJ IDEA.
  User: astingu
  Date: 5/26/2014
  Time: 3:04 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title></title>
</head>
<body>
    <div id="wrapper" style="width:100%; text-align:center">
        <img src="http://www.searchtechnologies.com/images/federated-search1.png" alt="Endava! In your zone!">
    </div>

    <br>

   <div  id="information" style="text-align:center">
        <c:forEach items="${moviesDetails}" var="movie">
            <p><b>Title: </b></p>
            <c:out value="${movie.title}"/>
            <br>
            <p><b>Actors: </b></p>
            <c:out value="${movie.actors}"/>
            <br>

            <p><b>URL: </b></p>
            <a href="${movie.url}"/>${movie.url}</a>
            <br>

            <p><b>Country: </b></p>
            <c:out value="${movie.country}"/>
            <br>

            <p><b>Genre: </b></p>
            <c:out value="${movie.genre}"/>
            <br>

            <p><b>Plot: </b></p>
            <c:out value="${movie.plot}"/>
            <br>

            <p><b>Poster: </b></p>
            <img src="${movie.poster}" alt="There is no image!"/>
            <br>

            <p><b>Year: </b></p>
            <c:out value="${movie.year}"/>
            <br>

        </c:forEach>
    </div>

</body>
</html>
