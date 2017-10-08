<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page pageEncoding="UTF-8" %>

<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<head>
    <link href="/resources/style.css">
    <script src="http://code.jquery.com/jquery-1.10.2.js"
            type="text/javascript"></script>
</head>
<body>
<h1>Управление задачами студентов</h1>

<form:form id="updateStudentTaskId" action="update/studentTask"
           method="post" modelAttribute="studentTask">
    Студент:
    <select name="studentId" form="updateStudentTaskId">
        <c:forEach items="${studentTaskViews}" var="studentTask">
            <option value="${studentTask.student.id}">
                    ${studentTask.student.fullName}
            </option>
        </c:forEach>
    </select>
    Задача №:
    <select name="taskId" form="updateStudentTaskId">
        <c:forEach items="${taskList}" var="task">
            <option value="${task.id}">
                    ${task.id}
            </option>
        </c:forEach>
    </select>
    <input type="submit" value="Изменить задачу студента">
    <span class="errorMessages">${errorMessage}</span>
</form:form>

<p>Список студентов и их задач</p>
<table>
    <tr>
        <th>Имя</th>
        <th>Фамилия</th>
        <th>№ задачи</th>
    </tr>
    <c:forEach items="${studentTaskViews}" var="studentTask">
        <tr>
            <td><c:out value="${studentTask.student.firstName}"/></td>
            <td><c:out value="${studentTask.student.lastName}"/></td>
            <td>${studentTask.taskId}</td>
        </tr>
    </c:forEach>
</table>

<p>Если Вы не нашли себя в списке, вы можете добавить себя</p>
<form:form action="create/student" method="post" modelAttribute="studentToAdd">
    <form:label for="firstName" path="firstName">Имя</form:label>
    <form:input id="firstName" type="text" path="firstName"/>

    <form:label for="lastName" path="lastName">Фамилия</form:label>
    <form:input id="lastName" type="text" path="lastName"/>

    <input type="submit" value="Добавить"/>
</form:form>
</body>
</html>