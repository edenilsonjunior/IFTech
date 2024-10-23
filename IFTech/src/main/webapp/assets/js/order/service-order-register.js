import {submitPost, submitGet} from '../components/global.js';

const formId = 'createOrderForm';
const servletUrl = '/createOrder';
const formContainer = document.getElementById(formId);

formContainer.addEventListener('submit', async (event)=>{
    await submitPost(event, servletUrl, formId);
});

document.addEventListener('DOMContentLoaded', async () => {

    var data = await submitGet(servletUrl);
    loadOrder(data);
});


const loadOrder = (data) => {

    const paymentMethodElement = document.getElementById('paymentMethod');
    data.paymentMethods.forEach((element) => {
        const option = document.createElement('option');
        option.text = element.name;
        paymentMethodElement.add(option);
    });
};
