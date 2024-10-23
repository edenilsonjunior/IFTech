import { submitPost } from '../components/global.js';

const formId = 'signupForm';
const servletUrl = '/api/customer/signup';
const formContainer = document.getElementById(formId);

formContainer.addEventListener('submit', async (event) => {
  await submitPost(event, servletUrl, formId);
});

document.getElementById('nextStep').addEventListener('click', () => {
  document.getElementById('step1').style.display = 'none';
  document.getElementById('step2').style.display = 'block';
});

document.getElementById('prevStep').addEventListener('click', () => {
  document.getElementById('step1').style.display = 'block';
  document.getElementById('step2').style.display = 'none';
});

document.getElementById('zipCode').addEventListener('input', async (event) => {

  const zipCode = event.target.value.replace('-', '');

  if (zipCode.length < 8)
    return;

  const url = `https://viacep.com.br/ws/${zipCode}/json/`;
  const response = await fetch(url);
  const data = await response.json();

  if (data.erro)
    return;


  document.getElementById('street').value = data.logradouro;
  document.getElementById('district').value = data.bairro;
  document.getElementById('city').value = data.localidade;
  document.getElementById('state').value = data.uf;
});

document.addEventListener('DOMContentLoaded', () => {

  document.getElementById('cpf').addEventListener('input', (event) => {
    event.target.value = cpf(event.target.value);
  });

  document.getElementById('zipCode').addEventListener('input', (event) => {
    event.target.value = zipCode(event.target.value);
  });

  document.getElementById('phone').addEventListener('input', (event) => {
    event.target.value = phone(event.target.value);
  });

});


const cpf = (v) => {

  let digits = '';
  for (let i = 0; i < v.length; i++) {
    if (!isNaN(v[i]) && v[i] !== ' ') {
      digits += v[i];
    }
  }

  // Adiciona os pontos e o hífen
  if (digits.length > 3) {
    digits = digits.slice(0, 3) + '.' + digits.slice(3);
  }
  if (digits.length > 7) {
    digits = digits.slice(0, 7) + '.' + digits.slice(7);
  }
  if (digits.length > 11) {
    digits = digits.slice(0, 11) + '-' + digits.slice(11, 13);
  }

  return digits;
}

const zipCode = (v) => {

  let digits = '';
  for (let i = 0; i < v.length; i++) {
    if (!isNaN(v[i]) && v[i] !== ' ') {
      digits += v[i];
    }
  }

  // Adiciona o hífen
  if (digits.length > 5) {
    digits = digits.slice(0, 5) + '-' + digits.slice(5);
  }

  return digits;
}

const phone = (v) => {

  let digits = '';
  for (let i = 0; i < v.length; i++) {
    if (!isNaN(v[i]) && v[i] !== ' ') {
      digits += v[i];
    }
  }

  // Adiciona os parênteses e o hífen
  if (digits.length > 0) {
    digits = '(' + digits;
  }
  if (digits.length > 3) {
    digits = digits.slice(0, 3) + ') ' + digits.slice(3);
  }
  if (digits.length > 10) {
    digits = digits.slice(0, 10) + '-' + digits.slice(10);
  }

  return digits;

}