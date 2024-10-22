import {submitPost} from '../components/global.js';

const formId = 'signupForm';
const servletUrl = '/signup';
const formContainer = document.getElementById(formId);

formContainer.addEventListener('submit', async (event)=>{
    await submitPost(event, servletUrl, formId);
});

function nextStep() {
    document.getElementById('step1').style.display = 'none';
    document.getElementById('step2').style.display = 'block';
  }

  function prevStep() {
    document.getElementById('step1').style.display = 'block';
    document.getElementById('step2').style.display = 'none';
  }