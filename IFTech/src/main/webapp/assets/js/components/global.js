export const contextPath = window.location.pathname.substring(0, window.location.pathname.indexOf("/", 2));

export const loadData = async () => {
    try {
        const response = await fetch(`${contextPath}/load-data`);
        return await response.json();
    } catch (error) {
        console.error(error);
    }
};

export const checkLoginStatus = async () => {
    try {
        const response = await fetch(`${contextPath}/api/customer/check-login`);
        return await response.json();
    } catch (error) {
        console.error(error);
    }
}


export const checkUserPermission = async () => {

    let response = await checkLoginStatus();

    if (!response.loggedIn) {
        window.location.href = `${contextPath}/views/erros/401.html`;
    }

}

export const submitPost = async (event, servletUrl, formId) => {

    event.preventDefault();
    const formData = new FormData(document.getElementById(formId));

    try {
        const response = await fetch(contextPath + servletUrl, {
            method: 'POST',
            body: formData
        });

        if (response.redirected) {
            window.location.href = response.url;
            return;
        }

        const data = await response.json();

        if (data.error) {
            const errorMessageElement = document.getElementById('error-message');
            errorMessageElement.textContent = data.error;
            errorMessageElement.style.display = 'block';
        }

    } catch (error) {
        console.error('Error:', error);
    }
}

export const submitGet = async (servletUrl) => {
    try {
        const response = await fetch(contextPath + servletUrl);
        return await response.json();
    } catch (error) {
        console.error(error);
    }
}