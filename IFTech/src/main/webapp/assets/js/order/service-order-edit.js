import { submitGet, submitPost } from '../components/global.js';

const formId = 'editOrderForm';
const servletUrl = '/api/customer/update';
const formContainer = document.getElementById(formId);

document.addEventListener('DOMContentLoaded', async () => {

    const orderId = new URLSearchParams(window.location.search).get('id');
    var data = await submitGet(`/api/order/update?id=${orderId}`);

    loadOrder(data);
});

const loadOrder = (data) => {

    document.getElementById('id').value = data.order.id;
    document.getElementById('description').value = data.order.description;
    document.getElementById('observation').value = data.order.observation;
    document.getElementById('price').value = data.order.price;
    document.getElementById('issueDate').value = data.order.issueDate;

    const paymentMethodElement = document.getElementById('paymentMethod');
    data.paymentMethods.forEach((element) => {
        const option = document.createElement('option');
        option.text = element.name;
        paymentMethodElement.add(option);

        if (element.name === data.order.paymentMethod.name) {
            option.selected = true;
        }
    });

    const statusElement = document.getElementById('status');
    const status = data.status;
    status.forEach((element) => {
        const option = document.createElement('option');
        option.text = element;
        statusElement.add(option);

        if (element === data.order.status) {
            option.selected = true;
        }
    });
}

formContainer.addEventListener('submit', async (event)=>{
    await submitPost(event, servletUrl, formId);
});
