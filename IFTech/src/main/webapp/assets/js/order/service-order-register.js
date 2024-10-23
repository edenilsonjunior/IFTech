import {submitPost} from '../components/global.js';

const formId = 'createOrderForm';
const servletUrl = '/createOrder';
const formContainer = document.getElementById(formId);

formContainer.addEventListener('submit', async (event)=>{
    await submitPost(event, servletUrl, formId);
});