function $(id) {
    return document.getElementById(id);
}

const navRender = () => {
    const navLogin = document.getElementById('session-anchor');
    const navChannel = document.getElementById('user-anchor');
    //Si mi objeto localStorage no posee el item userInfo (es nulo o indefinido) significa que el usuario es del tipo invitado
        //por tanto, tengo la opción de logearme o registrarme en cualquier momento
    if (localStorage.getItem("userInfo") === null || undefined) {
        navLogin.innerHTML += 'Sign In';
        navLogin.removeAttribute('onclick');
        navLogin.href = 'login.html';
        navChannel.innerHTML = '';
        navChannel.href = '';
    } else {
        navLogin.innerHTML += 'Log Out';
        navLogin.addEventListener('click', logout);
        navChannel.innerHTML = `${JSON.parse(localStorage["userInfo"])}'s channel`;
        navChannel.href = "userview.html";
    }
}

function logout(e) {
    e.preventDefault();
    fetch('./SessionEnd', config)
        .then(res => res.json())
        .then(data => {
            M.toast({html: data.message, displayLength: 2000});
            //Materialize.toast(data.message, 2000);
            localStorage.clear();
            setInterval(() => location.href = "index.html", 2000);
        })
}

const config = {
    withCredentials: true,
    credentials: 'same-origin',
    headers: {
        "Content-type": "application/x-www-form-urlencoded"
    }
}