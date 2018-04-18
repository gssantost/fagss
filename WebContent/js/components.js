class CommentBox {
    constructor(props, id) {
        this.chip = document.createElement('div');
        this.small = document.createElement('small');
        this.p = document.createElement('p');
        this.row = document.createElement('div');
        this.remBtn = document.createElement('button');
        this.banBtn = document.createElement('button');
        this.box = document.createElement('div');
        this.i = [];
        this.i.push(document.createElement('i'));
        this.i.push(document.createElement('i'));
        this.box.className = 'card-panel';
        this.chip.className = 'chip';
        this.chip.innerText = props.username;
        this.small.innerText = `Publicado el ${new Date(props.created_at).toLocaleDateString()} a las ${new Date(props.created_at).toLocaleTimeString()}`;
        this.p.innerText = props.comment_text;
        this.i[0].className = 'material-icons';
        this.i[0].innerHTML = 'delete';
        this.i[1].className = 'material-icons';
        this.i[1].innerHTML = 'report';
        this.remBtn.classList.add('btn-flat', 'waves-effect', 'waves-teal', 'disabled');
        this.remBtn.innerHTML = '';
        this.banBtn.classList.add('btn-flat', 'waves-effect', 'waves-teal', 'disabled');
        this.banBtn.innerHTML = '';
        this.row.classList.add('row', 'right-align');
        this.remBtn.appendChild(this.i[0]);
        this.banBtn.appendChild(this.i[1]);
        this.row.appendChild(this.remBtn);
        this.row.appendChild(this.banBtn);
        this.box.appendChild(this.chip);
        this.box.appendChild(this.small);
        this.box.appendChild(this.p);
        this.box.appendChild(this.row);

        this.remBtn.onclick = () => {
            fetch(`./removeMyComment?comment_id=${props.comment_id}&${id}`, {credentials: 'same-origin'})
                .then(res => res.json())
                .then(data => {
                    console.log(data);
                    M.toast({html: data.msg});
                })
        }

        this.banBtn.onclick = () => {
            fetch(`./BanComment?comment_id=${props.comment_id}&${id}`, {credentials: 'same-origin'})
                .then(res => res.json())
                .then(data => {
                    console.log(data);
                    M.toast({html: data.msg});
                })
        }
    }

    setDeleteOn() {
        this.remBtn.classList.remove('disabled');
    }

    setReportOn() {
        this.banBtn.classList.remove('disabled');
    }
}