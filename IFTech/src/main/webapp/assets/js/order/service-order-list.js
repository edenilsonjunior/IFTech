import { contextPath, submitGet } from '../components/global.js';

const loadOrders = async () => {

    const tableBody = document.querySelector("#order-list tbody");
    tableBody.innerHTML = '';


    var data = await submitGet("/api/order/retrieve");

    if (data.orders.length === 0) {
        const noDataRow = document.createElement('tr');
        noDataRow.innerHTML = `<td colspan="5" class="text-danger text-center">Nenhuma order de serviço cadastrada</td>`;
        tableBody.appendChild(noDataRow);
    }

    data.orders.forEach(order => {
        const row = document.createElement('tr');
        row.id = `order-${order.id}`;

        const status = {
            PENDING_APPROVAL: 'Pendente de aprovação',
            APPROVED: 'Aprovado',
            IN_PROGRESS: 'Em andamento',
            COMPLETED: 'Concluído',
            CANCELED: 'Cancelado'
        };

        order.status = status[order.status];

        row.innerHTML = `
                <td scope="row">${order.id}</td>
                <td>${order.description}</td>
                <td>${order.status}</td>
                <td>${order.issueDate}</td>
                <td>${order.endDate}</td>
                <td>${order.price}</td>
                <td>${order.paymentMethod.name}</td>
                <td>${order.observation}</td>
                <td>
                    <a href="${contextPath}/views/order/service-order-edit.html?id=${order.id}" class="btn btn-warning btn-sm">Editar</a>
                 </td>
                 <td>
                    <a class="btn btn-danger btn-sm delete-button" data-id="${order.id}">Excluir</a>
                </td>
            `;
        tableBody.appendChild(row);
    });

    document.querySelectorAll('.delete-button').forEach(button => {
        button.addEventListener('click', (event) => {
            deleteOrder(event.target.dataset.id);
        });
    });
    
};

const deleteOrder = async (id) => {
    const result = await Swal.fire({
        title: 'Deseja realmente excluir esta ordem de serviço?',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Sim, excluir!',
        cancelButtonText: 'Cancelar'
    });

    if (result.isConfirmed) {
        console.log("Excluir ordem de serviço com id: " + id);

        var data = await submitGet(`/api/order/delete?id=${id}`);

        if (data.success) {
            const successMessageElement = document.getElementById('success-message');
            successMessageElement.textContent = data.success;
            successMessageElement.style.display = 'block';
        }

        if (data.error) {
            document.getElementById('error-message').innerText = data.error;
            document.getElementById('error-message').style.display = 'block';
        }

        await loadOrders();
    }
};


document.getElementById("new-service-order").href = `${contextPath}/views/order/service-order-register.html`;
document.addEventListener('DOMContentLoaded', await loadOrders());
