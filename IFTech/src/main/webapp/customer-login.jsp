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

    <form action="login" method="post" id="form1">
      <h1 class="text-center">Login</h1>

      <div class="mb-2">
        <label for="name">Email*</label>
        <input type="text"
               name="email" id="email" class="form-control" minlength="3"
               maxlength="50" required="required">
        <span id="0"></span>
      </div>

      <div class="mb-2">
        <label for="password">Senha*</label>
        <input type="password" name="password" id="password" class="form-control" required="required">
        <span id="1"></span>
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