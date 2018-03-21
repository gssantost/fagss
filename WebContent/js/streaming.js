const storage = JSON.parse(localStorage.getItem('userInfo'));
let key = location.href.split('?')[1];

window.onload = () => {
    $('media').src = './watch?' + key;

    fetch('./Video?' + key, config)
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
