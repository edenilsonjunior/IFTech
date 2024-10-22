function createFooter() {

    const footer = document.getElementById('footer');

    footer.innerHTML = '';

    footer.innerHTML = `
		<footer class="py-5 text-center text-body-secondary bg-body-tertiary">
			<p>Site IFTech da disciplina ARQWEB2 do curso <a href="https://www.arq.ifsp.edu.br/superiores/tecnologia-em-sistemas-para-internet" target="_blank">TSI</a> by 
				<a href="https://github.com/edenilsonjunior" target="_blank">Edenilson Garcia</a>,
			<p class="mb-0">
				<a href="#">Back to top</a>
			</p>
		</footer>
	`;
}

document.addEventListener('DOMContentLoaded', createFooter);
