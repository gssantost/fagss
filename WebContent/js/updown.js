const storage = JSON.parse(localStorage.getItem('userInfo'));
let param = location.href.split('?')[1];
fetch('./Login', {withCredentials: true, credentials: 'same-origin', headers: {"Content-type": "application/x-www-form-urlencoded"}})
    .then(res => res.json())
    .then(data => {
        localStorage.setItem("userInfo", JSON.stringify(data.session));
        console.log(data);
        })

window.onload = () => {
    load();
}

function load() {
    let storage = JSON.parse(localStorage.getItem("userInfo"));
    console.log(storage);
    $('title').innerHTML += storage.message;
    navRender();

    $('user-bar').innerHTML =
        `<div>
            <p>${storage.name} ${storage.lastname}</p>
        </div>`;

    fetch('./MediaLoad', config)
        .then(resolve => resolve.json())
        .then(data => {
            console.log(data);
            for (let i = 0; i < data.length; i++) {
                $('video-board').innerHTML +=
                    `<div class="col">
                    <div class="card teal" style="margin: 10px 10px; width: 300px;">
                        <div class="card-content">
                            <span class="card-title">${data[i].media_name}</span>
                            <p class="white-text">${data[i].media_des}</p>
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
        .then(response => response.json())
        .then(data => {
            console.log(data);
        })
        .catch(error => {
            console.log(error.message);
        })
}

