import {submitPost, submitGet} from '../components/global.js';

const formId = 'createPaymentMethodForm';
const servletUrl = '/api/order/create-payment-method';
const formContainer = document.getElementById(formId);

formContainer.addEventListener('submit', async (event)=>{
    await submitPost(event, servletUrl, formId);
});
