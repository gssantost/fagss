const storage = JSON.parse(localStorage.getItem('userInfo'));
let param = location.href.split('?')[1];

window.onload = () => {
    $('media').src = './watch?' + param;

    fetch('./Video?' + param, config)
        .then(res => res.json())
        .then(data => {
            console.log(data);
            $('user-chip').innerHTML = `${storage.username}`;
            $('name').innerHTML = data[0].media_name;
            $('views').innerHTML += data[0].media_views;
            $('date').innerHTML = `Publicado el ${data[0].created_at}`;
            $('description').innerHTML = data[0].media_des;
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
}

function dislike() {
    dislikes++;
    calculateBar();
   
    fetch('./SetDislike?' + param, config)
    .then(res => res.json())
    .then(data => console.log(data))
    
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
        credentials: 'same-origin'
    }
    //
    fetch('./SetComment?' +param + "&comment="+$("comment").value, configs)
        .then(response => response.json())
        .then(data => {
            console.log(data);
        })
        .catch(error => {
            console.log(error.message);
        })
    
    var texto=  document.getElementById("comment").value;
    document.getElementById("textToEncode").value = texto;
  
    }

function limpiar() {
    document.getElementById("comment").value = "";
}



//calculateBar();