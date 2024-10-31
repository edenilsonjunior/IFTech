import { contextPath } from './global.js';


document.addEventListener('DOMContentLoaded', async () => { 

	const footer = document.getElementById('footer');

	const response = await fetch(`${contextPath}/assets/js/template/footer-template.html`);
    footer.innerHTML = await response.text();
});
