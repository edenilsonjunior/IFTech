import {submitPost, submitGet} from '../components/global.js';

const formId = 'createOrderForm';
const servletUrl = '/api/order/create';
const formContainer = document.getElementById(formId);



formContainer.addEventListener('submit', async (event)=>{

    const paymentMethodElement = document.getElementById('paymentMethod');
    

    if (paymentMethodElement.options.length <= 1) {
        Swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: 'Nenhum método de pagamento cadastrado',
        });

        event.preventDefault();
        return;
    }

    await submitPost(event, servletUrl, formId);
});

document.addEventListener('DOMContentLoaded', async () => {

    const data = await submitGet(servletUrl);
    loadOrder(data);
});


const loadOrder = (data) => {

    const paymentMethodElement = document.getElementById('paymentMethod');
    data.paymentMethods.forEach((element) => {
        const option = document.createElement('option');
        option.text = element.name;
        paymentMethodElement.add(option);
    });

    if(data.paymentMethods.length > 0){
        document.getElementById('paymentMethod').required = true;
    }
};
