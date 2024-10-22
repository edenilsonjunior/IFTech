import {submitPost} from '../components/global.js';

const formId = 'loginForm';
const servletUrl = '/login';
const formContainer = document.getElementById(formId);

formContainer.addEventListener('submit', async (event)=>{
    await submitPost(event, servletUrl, formId);
});