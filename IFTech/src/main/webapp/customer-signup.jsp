<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registro</title>
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
    <c:if test="${result == 'notRegistered'}">
      <div class="alert alert-danger alert-dismissible fade show" role="alert">
        CPF já cadastrado. Tente novamente.
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
      </div>
    </c:if>

    <form action="signup" method="post" id="form1">
      <h1 class="text-center">Cadastre-se</h1>

      <div class="mb-2">
        <label for="name">Nome completo*</label>
        <input type="text"
               name="name" id="name" class="form-control" minlength="3"
               maxlength="50" required="required">
        <span id="0"></span>
      </div>

      <!--Customer fields-->
      <div class="mb-2">
        <label for="cpf">CPF*</label>
        <input type="text" name="cpf" id="cpf" class="form-control" required="required">
        <span id="1"></span>
      </div>

      <div class="mb-2">
        <label for="email">E-mail*</label>
        <input type="email" name="email"
               id="email" class="form-control" required="required"> <span
              id="2"></span>
      </div>

      <div class="mb-2">
        <label for="phone">Telefone*</label>
        <input type="text" name="phone"
               id="phone" class="form-control" required="required"> <span
              id="3"></span>
      </div>

      <div class="mb-2">
        <label for="password">Senha*</label>
        <input type="password" name="password"
               id="password" class="form-control" required="required"> <span
              id="3"></span>
      </div>


      <!--Address fields-->
      <div class="mb-2">
        <label for="street">Rua*</label>
        <input type="text" name="street"
               id="street" class="form-control" required="required"> <span
              id="3"></span>
      </div>

      <div class="mb-2">
        <label for="number">Numero*</label>
        <input type="text" name="number"
               id="number" class="form-control" required="required"> <span
              id="3"></span>
      </div>

      <div class="mb-2">
        <label for="complement">Complemento*</label>
        <input type="text" name="complement"
               id="complement" class="form-control" required="required"> <span
              id="3"></span>
      </div>

      <div class="mb-2">
        <label for="district">Bairro*</label>
        <input type="text" name="district"
               id="district" class="form-control" required="required"> <span
              id="3"></span>
      </div>

      <div class="mb-2">
        <label for="zipCode">CEP*</label>
        <input type="text" name="zipCode"
               id="zipCode" class="form-control" required="required"> <span
              id="3"></span>
      </div>

      <div class="mb-2">
        <label for="city">Cidade*</label>
        <input type="text" name="city"
               id="city" class="form-control" required="required"> <span
              id="3"></span>
      </div>

      <div class="mb-2">
        <label for="state">Estado*</label>
        <input type="text" name="state"
               id="state" class="form-control" required="required"> <span
              id="3"></span>
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
