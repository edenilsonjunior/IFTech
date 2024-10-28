import { submitGet, submitPost } from '../components/global.js';

const formId = 'profileForm';
const servletUrl = '/api/customer/update';
const formContainer = document.getElementById(formId);

document.addEventListener('DOMContentLoaded', async () => {

    var data = await submitGet(servletUrl);

    loadProfile(data);
});

const loadProfile = (data) => {

    const customer = data.customer;

    const username = document.getElementById('username');
    username.textContent = customer.name;

    document.getElementById('email').value = customer.email;
    document.getElementById('phone').value = customer.phone;

    document.getElementById('street').value = customer.address.street;
    document.getElementById('number').value = customer.address.number;
    document.getElementById('complement').value = customer.address.complement;
    document.getElementById('district').value = customer.address.district;
    document.getElementById('zipCode').value = customer.address.zipCode;
    document.getElementById('city').value = customer.address.city;
    document.getElementById('state').value = customer.address.state;
}

formContainer.addEventListener('submit', async (event) => {

    const passwordField = document.getElementById('password');
    const confirmPasswordField = document.getElementById('confirmPassword');

        if (passwordField.value !== confirmPasswordField.value) {
            const passwordError = document.getElementById('password-error');

            passwordError.innerText = 'As senhas não coincidem. Por favor, verifique.';
            passwordError.style.display = 'block';
            confirmPasswordField.focus();
            return;
        }

    await submitPost(event, servletUrl, formId);
});
