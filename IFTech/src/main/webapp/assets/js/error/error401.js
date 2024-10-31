import {checkLoginStatus, contextPath} from "../components/global.js";

document.addEventListener("DOMContentLoaded", async () => {
    let container = document.getElementById('401-text');
    let result = await checkLoginStatus();

    if (result.loggedIn) {
        container.innerHTML = `Tente voltar ao <a href="${contextPath}/index.html" class="text-primary">início</a>.`;
    } else {
        container.innerHTML = `<a href="${contextPath}/views/customer/login.html" class="text-primary">Faça login</a> para continuar.`;
    }
});
