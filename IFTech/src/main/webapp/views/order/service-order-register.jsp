<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
    <link
        href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
        rel="stylesheet"
        integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
        crossorigin="anonymous">
    <link href="css/styles.css" rel="stylesheet">
    <link href="css/errors.css" rel="stylesheet">
</head>
<body>

<div class="container">

    <div class="col-lg-6 offset-lg-3 col-sm-12">
        <c:if test="${not empty error}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                ${error}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        </c:if>

        <form action="createOrder" method="post" id="form1">
            <h1 class="text-center">Login</h1>

            <div class="mb-2">
                <label for="email">Email*</label>
                <input type="text"
                       name="email" id="email" class="form-control" minlength="3"
                       maxlength="50" required="required">
                <span id="0"></span>
            </div>

            <div class="mb-2">
                <label for="paymentMethod">Payment Method*</label>
                <input type="text"
                       name="paymentMethod" id="paymentMethod" class="form-control" required>
            </div>

            <div class="mb-2">
                <label for="description">Description</label>
                <textarea name="description" id="description" class="form-control" rows="3"></textarea>
            </div>

            <div class="mb-2">
                <label for="observation">Observation</label>
                <textarea name="observation" id="observation" class="form-control" rows="3"></textarea>
            </div>

            <div class="mb-2">
                <label for="price">Price*</label>
                <input type="number" step="0.01"
                       name="price" id="price" class="form-control" required>
            </div>

            <div class="mb-2">
                <label for="issueDate">Issue Date*</label>
                <input type="date"
                       name="issueDate" id="issueDate" class="form-control" required>
            </div>

            <div class="mb-2">
                <button type="submit" class="btn btn-primary">Salvar</button>
            </div>
        </form>
    </div>
</div>

<script
        src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous">
</script>

</body>
</html>
