const storage = JSON.parse(localStorage.getItem('userInfo'));
let param = location.href.split('?')[1];

window.onload = () => {
    $('media').src = './watch?' + param;

    fetch(`./setView?${param}`, config).then(res => res.json()).then(data => {
        console.log(data);
    })
    
    fetch('./Video?' + param, config)
        .then(res => res.json())
        .then(data => {
            console.log(data);
            let m = data.mediaInfo[0];
            $('user-chip').innerHTML = m.username;
            $('name').innerHTML = m.media_name;
            $('views').innerHTML += m.media_views;
            $('date').innerHTML = `Publicado el ${new Date(m.created_at).toLocaleDateString()}`;
            $('description').innerHTML = m.media_des;

            switch(data.typeInfo) {
                case 1:
                    $('likBtn').disabled = true;
                    $('disBtn').disabled = true;
                    $('comment').disabled = true;
                    $('commentBtn').disabled = true;
                    M.toast({html: 'Para poder dar ME GUSTA! y COMENTAR debes crear tu propio USUARIO', displayLength: 2000});
                break;
                
                case 2:
                    $('likBtn').disabled = false;
                    $('disBtn').disabled = false;
                break;

                case 3:
                    console.log(`You're Admin :~)`);
                    $('likBtn').disabled = false;
                    $('disBtn').disabled = false;
                break;
            }
        })

    fetch('./UpLike?' + param, config)
        .then(res => res.json())
        .then(data => {
            console.log(data);
            updateLikes(data[0]);
        })

    fetch('./Comment?' + param, config)
        .then(res => res.json())
        .then(data => {
            console.log(data);
            let c = data.commentInfo;
            for (let i = 0; i < c.length; i++) {
                let commentBox = new CommentBox(c[i], param);
                if (data.typeInfo !== 1) {
                    commentBox.setDeleteOn();
                    commentBox.setReportOn();
                }
                $('commentList').appendChild(commentBox.box);
            }
        })
}

var likes = 0,
    dislikes = 0;

function like() {
    likes++;
    calculateBar();
    
    fetch('./SetLike?' + param, config)
    .then(res => res.json())
    .then(data => console.log(data))

    fetch('./UpLike?' + param, config)
        .then(res => res.json())
        .then(data => {
            console.log(data);
            updateLikes(data[0]);
        })
}

function dislike() {
    dislikes++;
    calculateBar();
   
    fetch('./SetDislike?' + param, config)
    .then(res => res.json())
    .then(data => console.log(data))

    fetch('./UpLike?' + param, config)
        .then(res => res.json())
        .then(data => {
            console.log(data);
            updateLikes(data[0]);
        })
}

function calculateBar() {
    var total = likes + dislikes;
    var percentageLikes = (likes / total) * 100;
    var percentageDislikes = (dislikes / total) * 100;

    document.getElementById('likes').style.width = percentageLikes.toString() + "%";
    document.getElementById('dislikes').style.width = percentageDislikes.toString() + "%";
}

function comment() {
    let configs = {
        method: 'POST',
        withCredentials: true,
        credentials: 'same-origin',
        body: JSON.stringify({media_id: param.split("=").pop(), comment: $("comment").value})
    }
    //POST THE COMMENT
    fetch('./Comment', configs)
        .then(res => res.json())
        .then(data => console.log(data))
        .catch(error => console.log(error.message))

    //DO A GET FOR REFRESH COMMENTS
    fetch('./Comment?' + param, config)
        .then(res => res.json())
        .then(data => {
            let c = data.commentInfo;
            while ($('commentList').firstChild) {
                $('commentList').removeChild($('commentList').firstChild);
            }
            for (let i = 0; i < c.length; i++) {
                let commentBox = new CommentBox(c[i], param);
                if (data.typeInfo !== 1) {
                    commentBox.setDeleteOn();
                    commentBox.setReportOn();
                }
                $('commentList').appendChild(commentBox.box);
            }
        })
        .catch(error => console.log(error.message))
        clear();
}

function clear() {
    document.getElementById("comment").value = "";
}

function updateLikes(data) {
    $('likesCount').innerHTML = "";
    $('dislikesCount').innerHTML = "";
    $('likesCount').innerHTML = data.likes;
    $('dislikesCount').innerHTML = data.dislikes;
}

//calculateBar();

$('removeBtn').onclick = () => {
    fetch('./RemoveVideo?' + param, config)
        .then(res => res.json())
        .then(data => {
            console.log(data);
            M.toast({ html: data.msg });
            if (data.status === 200) {
                setInterval(() => location.href = "index.html", 4000);
            }
        })
        .catch(error => console.log(error.message));
}