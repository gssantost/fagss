window.onload = () => {
    navRender();
    fetch('./show', config).then(res => res.json()).then(data => {
        console.log(data);
        let m = data.mediaInfo;
        switch (data.typeInfo) {
            case 1:
                for (let i = 0; i < m.length; i++) {
                    $('video-board').innerHTML +=
                        `<div class="col">
                                    <div class="card" style="margin: 10px 10px; width: 300px;">
                                        <div class="card-content">
                                            <span class="card-title">${m[i].media_name}</span>
                                            <p class="grey-text">${m[i].media_des}</p>
                                        </div>
                                        <div class="card-action">
                                            <a href="./video.html?id=${m[i].media_id}"> VER! </a>
                                        </div>
                                    </div>
                                </div>`
                        ;
                }
                break;

            case 2:
                for (let i = 0; i < m.length; i++) {
                    $('video-board').innerHTML +=
                        `<div class="col">
                                    <div class="card" style="margin: 10px 10px; width: 300px;">
                                        <div class="card-content">
                                            <span class="card-title">${m[i].media_name}</span>
                                            <p class="grey-text">${m[i].media_des}</p>
                                        </div>
                                        <div class="card-action">
                                            <a href="./MediaDownload?id=${m[i].media_id}"> DESCARGAR </a>
                                            <a href="./video.html?id=${m[i].media_id}"> VER! </a>
                                        </div>
                                    </div>
                                </div>`
                        ;
                }
                break;

            case 3:
                for (let i = 0; i < m.length; i++) {
                    $('video-board').innerHTML +=
                        `<div class="col">
                                    <div class="card" style="margin: 10px 10px; width: 300px;">
                                        <div class="card-content">
                                            <span class="card-title">${m[i].media_name}</span>
                                            <p class="grey-text">${m[i].media_des}</p>
                                        </div>
                                        <div class="card-action">
                                            <a href="./MediaDownload?id=${m[i].media_id}"> DESCARGAR </a>
                                            <a href="./video.html?id=${m[i].media_id}"> VER! </a>
                                        </div>
                                    </div>
                                </div>`
                        ;
                }
                break;
        } // end 
    })
}