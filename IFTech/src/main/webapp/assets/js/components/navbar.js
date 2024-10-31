import { checkLoginStatus, contextPath } from './global.js';

const navbarContainer = document.getElementById('nav-bar');


document.addEventListener('DOMContentLoaded', async () => {

    const response = await fetch(`${contextPath}/assets/js/template/navbar-template.html`);
    navbarContainer.innerHTML = await response.text();

    const data = await checkLoginStatus();
    await customizeNavbar(data.loggedIn);
});

const customizeNavbar = async (loggedIn) => {

    document.getElementById('nav-logo').src = `${contextPath}/assets/images/iftech.jpg`;
    document.getElementById('main-page').href = `${contextPath}/index.html`;
    document.getElementById('orders-list').href = `${contextPath}/views/order/service-order-list.html`;
    document.getElementById('payment-method-register').href = `${contextPath}/views/order/payment-method-register.html`;
    document.getElementById('logout').href = `${contextPath}/api/customer/logout`;
    document.getElementById('profile').href = `${contextPath}/views/customer/profile.html`;
    document.getElementById('signup').href = `${contextPath}/views/customer/signup.html`;
    document.getElementById('login').href = `${contextPath}/views/customer/login.html`;

    if (loggedIn) {
        document.getElementById('orders-list-li').classList.remove('d-none');
        document.getElementById('payment-method-register-li').classList.remove('d-none');
        document.getElementById('user-dropdown').classList.remove('d-none');
    }
    else {
        document.getElementById('login-register-options').classList.remove('d-none');
    }

};