document.addEventListener('DOMContentLoaded', async () => {

    const contextPath = window.location.pathname.substring(0, window.location.pathname.indexOf("/", 2));
    const tableBody = document.querySelector("#customer-list tbody");

    try {
        const response = await fetch(contextPath + "/retrieveCustomer");
        var data = await response.json();

        if(data.customers.length === 0) {
            const noDataRow = document.createElement('tr');
            noDataRow.innerHTML = `<td colspan="5" class="text-danger text-center">Nenhum cliente cadastrado</td>`;
            tableBody.appendChild(noDataRow);
        }

        data.customers.forEach(customer => {
            const customerRow = document.createElement('tr');

            customerRow.innerHTML = `
                <td>` + customer.name + `</td>
                <td>` + customer.cpf + `</td>
                <td>` + customer.email + `</td>
                <td>` + customer.phone + `</td>
                <td>
                    <a href="#" class="btn btn-warning btn-sm">Editar</a>
                    <a href="#" class="btn btn-danger btn-sm">Excluir</a>
                </td>
            `;

            tableBody.appendChild(customerRow);
        });

    } catch (error) {
        console.error(error);
    }

});
