import { contextPath, submitGet } from '../components/global.js';


const tableBody = document.getElementById('order-list-body');
const templateRow = document.getElementById('order-row-template');

const translated = {
    PENDING_APPROVAL: 'Pendente de aprovação',
    APPROVED: 'Aprovado',
    IN_PROGRESS: 'Em andamento',
    COMPLETED: 'Concluído',
    CANCELED: 'Cancelado'
};


const loadOrders = async () => {


    tableBody.innerHTML = '';

    var data = await submitGet("/api/order/retrieve");
    if (data.orders.length === 0) {
        tableBody.innerHTML = '<tr><td colspan="10" class="text-danger text-center">Nenhuma ordem de serviço cadastrada</td></tr>';
        return;
    }


    data.orders.forEach(order => {
        const row = templateRow.cloneNode(true);
        row.classList.remove('d-none');
        row.id = `order-${order.id}`;

        row.querySelector('.order-id').textContent = order.id;
        row.querySelector('.order-description').textContent = order.description;
        row.querySelector('.order-status').textContent = translated[order.status];
        row.querySelector('.order-issueDate').textContent = order.issueDate;
        row.querySelector('.order-endDate').textContent = order.endDate;
        row.querySelector('.order-price').textContent = order.price;
        row.querySelector('.order-paymentMethod').textContent = order.paymentMethod.name;
        row.querySelector('.order-observation').textContent = order.observation;

        row.querySelector('.edit-button').href = `${contextPath}/views/order/service-order-edit.html?id=${order.id}`;
        row.querySelector('.delete-button').dataset.id = order.id;
        row.querySelector('.delete-button').addEventListener('click', () => deleteOrder(order.id));

        tableBody.appendChild(row);
    });

    document.querySelectorAll('.delete-button').forEach(button => {
        button.addEventListener('click', (event) => {
            deleteOrder(event.target.dataset.id);
        });
    });
}


const deleteOrder = async (id) => {
    const result = await Swal.fire({
        title: 'Deseja realmente excluir esta ordem de serviço?',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Sim, excluir!',
        cancelButtonText: 'Cancelar'
    });

    if (result.isConfirmed) {
        var data = await submitGet(`/api/order/delete?id=${id}`);

        if (data.success) {
            const successMessageElement = document.getElementById('success-message');
            successMessageElement.textContent = data.success;
            successMessageElement.style.display = 'block';
        }
        else if (data.error) {
            document.getElementById('error-message').innerText = data.error;
            document.getElementById('error-message').style.display = 'block';
        }

        await loadOrders();
    }
};

document.getElementById('new-service-order').href = `${contextPath}/views/order/service-order-register.html`;
document.addEventListener('DOMContentLoaded', loadOrders);