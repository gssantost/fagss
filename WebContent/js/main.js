const formToJson = (elements) => [].reduce.call(elements, (data, element) => {
    data[element.name] = element.value;
    return data;
}, {});

function isValidElement(element) {
    return element.name && element.value;
}

function isEmpty(obj) {
    for (let key in obj) {
        if (obj[key] === "") {
            return true;
        }
    }
    return false;
}

const handleFormLogin = e => {
    e.preventDefault();
    const data = formToJson(formLogin.elements);
    if (isEmpty(data)) {
        alert("No se puede enviar un formulario vacío. Revise los campos");
    } else {
        login(data);
    }
}

const handleFormRegister = e => {
    e.preventDefault();
    const data = formToJson(formRegister.elements);
    if (isEmpty(data)) {
        alert("No se puede enviar un formulario vacío. Revise los campos");
    } else {
        register(data);
    }
}

function login(data) {
    let config = {
        method: 'post',
        withCredentials: true,
        credentials: 'same-origin',
        headers: {
            "Content-type": "application/x-www-form-urlencoded"
        },
        body: JSON.stringify(data)
    }

    fetch('./Login', config)
        .then(resolve => resolve.json())
        .then(data => {
            console.log(data);
            //Materialize.toast(data.res, 2000);
            M.toast({html: data.res, displayLength: 2000});
            //localStorage.setItem("userInfo", JSON.stringify(data.session));
            if (data.status === 200) {
                setInterval(() => window.location.href = "userview.html", 2000);
            } else {
                console.log("Wrong!");
            }
        })
        .catch(reject => {
            Materialize.toast("Imposible iniciar sesión: " + reject);
        })
}

function register(data) {
    let config = {
        method: 'post',
        withCredentials: true,
        credentials: 'same-origin',
        headers: {
            'Content-type': 'application/x-www-form-urlencoded'
        },
        body: JSON.stringify(data)
    }

    fetch('./Register', config)
        .then(resolve => resolve.json())
        .then(data => {
            console.log(data);

        })
}

const formLogin = $('login-form');
const formRegister = $('registry-form');
formLogin.addEventListener('submit', handleFormLogin);
formRegister.addEventListener('submit', handleFormRegister);