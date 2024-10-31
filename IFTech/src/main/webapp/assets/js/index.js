import { checkLoginStatus } from "./components/global.js";

const loadName = async () => {

    const data = await checkLoginStatus();

    if (data.loggedIn) {
        document.getElementById('welcome').innerText = `Bem-vindo ao IFTech, ${data.customer.name}`;
    }
}

document.addEventListener('DOMContentLoaded', loadName);