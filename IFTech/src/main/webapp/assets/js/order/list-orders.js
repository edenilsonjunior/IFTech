import {contextPath, submitGet} from '../components/global.js';

document.addEventListener('DOMContentLoaded', async () => {

    const tableBody = document.querySelector("#order-list tbody");

        var data = await submitGet("/retrieveOrders");

        if(data.orders.length === 0) {
            const noDataRow = document.createElement('tr');
            noDataRow.innerHTML = `<td colspan="5" class="text-danger text-center">Nenhuma order de serviço cadastrada</td>`;
            tableBody.appendChild(noDataRow);
        }

        data.orders.forEach(order => {
            const row = document.createElement('tr');

            row.innerHTML = `
                <td>${order.id}</td>
                <td>${order.description}</td>
                <td>${order.status}</td>
                <td>${order.issueDate}</td>
                <td>${order.endDate}</td>
                <td>${order.observation}</td>
                <td>
                    <a href="#" class="btn btn-warning btn-sm">Editar</a>
                    <a href="#" class="btn btn-danger btn-sm">Excluir</a>
                </td>
            `;
            tableBody.appendChild(row);
        });

});

document.getElementById("new-service-order").href = `${contextPath}/views/order/service-order-register.html`;
