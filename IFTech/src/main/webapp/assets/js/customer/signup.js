import { submitPost } from '../components/global.js';

const formId = 'signupForm';
const servletUrl = '/api/customer/signup';
const formContainer = document.getElementById(formId);

const errorMessageElement = document.getElementById('error-message');

const nextStep = document.getElementById('nextStep');
const prevStep = document.getElementById('prevStep');
const step1 = document.getElementById('step1');
const step2 = document.getElementById('step2');


const zipCodeField = document.getElementById('zipCode');
const streetField = document.getElementById('street');
const districtField = document.getElementById('district');
const cityField = document.getElementById('city');
const stateField = document.getElementById('state');

const cpfField = document.getElementById('cpf');
const phoneField = document.getElementById('phone');
const nameField = document.getElementById('name');
const emailField = document.getElementById('email');
const passwordField = document.getElementById('password');

const nameError = document.getElementById('name-error');
const cpfError = document.getElementById('cpf-error');
const emailError = document.getElementById('email-error');
const phoneError = document.getElementById('phone-error');
const passwordError = document.getElementById('password-error');


const formatCpf = (v) => {

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

const formatZipCode = (v) => {

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

const formatPhone = (v) => {

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

const validateEmail = (email) => {

  const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  
  if(email.trim().length === 0)
    return 'O campo email é obrigatório.';

  if(!re.test(email))
    return 'Email inválido.';

  return '';
};

const validateCPF = (cpf) => {

  if(cpf.trim().length === 0)
    return 'O campo CPF é obrigatório.';

  cpf = cpf.trim();
  cpf = cpf.replaceAll('-', '');
  cpf = cpf.replaceAll('.', '');
  cpf = cpf.replaceAll(' ', '');

  if (cpf.length !== 11)
    return 'CPF inválido.';

    return '';
  };

const validatePhone = (phone) => {
  
  if(phone.trim().length === 0)
    return 'O campo telefone é obrigatório.';
  
  let cleanPhone = phone.trim().replace(/[^\d]+/g, '');
  
  if (cleanPhone.length < 10 || cleanPhone.length > 11)
    return 'Telefone inválido.';

  return '';
};

const validateZipCode = (zipCode) => {
  
  if(zipCode.trim().length === 0)
    return 'O campo CEP é obrigatório.';

  let cleanZipCode = zipCode.trim().replace(/[^\d]+/g, '');

  if (cleanZipCode.length !== 8)
    return 'CEP inválido.';

  return '';
};

const validatePassword = (password) => {

  if(password.trim().length === 0)
    return 'O campo senha é obrigatório.';

  if(password.trim().length < 6)
    return 'Senha inválida.';

  return '';
};

const validateName = (name) => {

  if(name.trim().length === 0)
    return 'O campo nome é obrigatório.';

  if(name.trim().length < 3)
    return 'Nome inválido.';

  return '';
};


nextStep.addEventListener('click', () => {

  nameError.innerText = '';
  nameError.style.display = 'none';

  cpfError.innerText = '';
  cpfError.style.display = 'none';

  emailError.innerText = '';
  emailError.style.display = 'none';

  phoneError.innerText = '';
  phoneError.style.display = 'none';

  passwordError.innerText = '';
  passwordError.style.display = 'none';

  let error = false;
  debugger;

  if(validateName(nameField.value) !== '') {
    nameError.innerText = validateName(nameField.value);
    nameError.style.display = 'block';
    error = true;
  }

  if (validateName(nameField.value) !== '') {
    nameError.innerText = validateName(nameField.value);
    nameError.style.display = 'block';
    error = true;
  } 
  
  if (validateCPF(cpfField.value) !== ''){
    cpfError.innerText = validateCPF(cpfField.value);
    cpfError.style.display = 'block';
    error = true;
  } 

  if (validateEmail(emailField.value) !== '') {
    emailError.innerText = validateEmail(emailField.value);
    emailError.style.display = 'block';
    error = true;
  }
  
  if (validatePhone(phoneField.value) !== '') {
    phoneError.innerText = validatePhone(phoneField.value);
    phoneError.style.display = 'block';
    error = true;
  }
  
  if(validatePassword(passwordField.value) !== '') {
    passwordError.innerText = validatePassword(passwordField.value);
    passwordError.style.display = 'block';
    error = true;
  }
 
  console.log(error);
  if (error)
    return;

  step1.style.display = 'none';
  step2.style.display = 'block';
});

prevStep.addEventListener('click', () => {
  step1.style.display = 'block';
  step2.style.display = 'none';
});

zipCodeField.addEventListener('input', async (event) => {

  const zipCode = event.target.value.replace('-', '');

  if (zipCode.length < 8)
    return;

  const url = `https://viacep.com.br/ws/${zipCode}/json/`;
  const response = await fetch(url);
  const data = await response.json();

  if (data.erro)
    return;

  streetField.value = data.logradouro;
  districtField.value = data.bairro;
  cityField.value = data.localidade;
  stateField.value = data.uf;
});

document.addEventListener('DOMContentLoaded', () => {

  cpfField.addEventListener('input', (event) => {
    event.target.value = formatCpf(event.target.value);
  });

  zipCodeField.addEventListener('input', (event) => {
    event.target.value = formatZipCode(event.target.value);
  });

  phoneField.addEventListener('input', (event) => {
    event.target.value = formatPhone(event.target.value);
  });

});

formContainer.addEventListener('submit', async (event) => {
  event.preventDefault();

  if (!validateZipCode(zipCode.value)) 
    errorMessageElement.innerText += 'CEP inválido.\n';

  await submitPost(event, servletUrl, formId);
});
