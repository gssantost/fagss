let param = location.href.split('?')[1];

var modal = document.querySelectorAll('.modal');
var modalInstance = M.Modal.init(modal);

fetch('./Login', {withCredentials: true, credentials: 'same-origin', headers: {"Content-type": "application/x-www-form-urlencoded"}})
    .then(res => res.json())
    .then(data => {
        localStorage.setItem("userInfo", JSON.stringify(data.session.username));
        console.log(data);
        $('title').innerHTML += data.session.message;
        $('title').classList.add('animated', 'rollIn');
        $('name').innerHTML += `${data.session.name} ${data.session.lastname}`;
        $('user').innerHTML += data.session.username;
        if (data.session.typeUser === 3) {
            $('type').innerHTML += 'Administrador';
        } else {
            $('type').innerHTML += 'Usuario';
        }
    })

window.onload = () => {
    load();
}

function load() {
    navRender();
    fetch('./MediaLoad', config)
        .then(resolve => resolve.json())
        .then(data => {
            console.log(data);
            for (let i = 0; i < data.length; i++) {
                $('video-board').innerHTML +=
                    `<div class="col center-align">
                    <div class="card" style="margin: 10px 10px; width: 300px;">
                        <div class="card-content">
                            <span class="card-title">${data[i].media_name}</span>
                            <p class="grey-text">${data[i].media_des}</p>
                        </div>
                        <div class="card-action">
                            <a href="./MediaDownload?id=${data[i].media_id}"> DESCARGAR </a>
                            <a href="./video.html?id=${data[i].media_id}"> VER! </a>
                        </div>
                    </div>
                </div>`;
            }
        })
}

function upload() {
    if (($('name').value.trim() || $('description').value.trim()) !== '');
    $('fetch-loader').style.display = 'block';
    const fd = new FormData();
    fd.append('file', $('file').files[0]);
    fd.append('name', $('name').value);
    fd.append('description', $('descrip').value);

    let configs = {
        method: 'POST',
        withCredentials: true,
        credentials: 'same-origin',
        body: fd
    }

    fetch('./MediaUpload', configs)
        .then(res => {
            $('fetch-loader').style.display = 'none';
            return res.json();
        })
        .then(data => {
            console.log(data);
            $('response-text').innerHTML = data.message;
        })
        .catch(error => {
            console.log(error.message);
        })
}

function moreUpload() {
    $('name').value = '';
    $('descrip').value = '';
    $('file').value = '';
    $('response-text').value = '';
}