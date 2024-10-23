import {checkLoginStatus, contextPath} from './global.js';

const navbarContainer = document.getElementById('nav-bar');

document.addEventListener('DOMContentLoaded', async () => {

    const response = await fetch(`${contextPath}/assets/js/components/navbar-template.html`);
    navbarContainer.innerHTML = await response.text();

    const data = await checkLoginStatus();
    await customizeNavbar(data.loggedIn);
});

const customizeNavbar = async (loggedIn) => {

    document.getElementById('nav-logo').src = `${contextPath}/assets/images/iftech.jpg`;
    loadNavbarLinks();
    loadAuthLinks(loggedIn);
};

const loadNavbarLinks = () =>{

    document.getElementById('nav-links').innerHTML = `
        <li class="nav-item">
            <a class="nav-link active" aria-current="page" href="${contextPath}/index.html">Página Inicial</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="${contextPath}/views/order/service-order-list.html">Ordens de serviço</a>
        </li>
    `;
}

const loadAuthLinks = (loggedIn)  =>{
    const authLinks = document.getElementById('auth-links');
    if (!loggedIn) {
        authLinks.innerHTML = `
            <li class="nav-item">
                <a href="${contextPath}/views/customer/signup.html" class="btn btn-outline-secondary" role="button">SignUp</a>
                <a href="${contextPath}/views/customer/login.html" class="btn btn-secondary" role="button">Login</a>
            </li>
        `;
    } else {
        authLinks.innerHTML = `
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                    <img src="https://cdn-icons-png.flaticon.com/512/1144/1144760.png" alt="user" width="30px">
                </a>
                <ul class="dropdown-menu">
                    <li><a class="dropdown-item" href="${contextPath}/views/customer/profile.html">Perfil</a></li>
                    <li><hr class="dropdown-divider"></li>
                    <li><a class="dropdown-item" href="${contextPath}/api/customer/logout">Sair</a></li>
                </ul>
            </li>
        `;
    }

}