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

function $(id) {
    return document.getElementById(id);
}




function upload(){
const fd= new FormData();
fd.append('file', document.getElementById('file').files[0]);
fd.append('name',  document.getElementById('name'));
fd.append('description',  document.getElementById('descrip'));

let configs ={
		method:'POST',
		credentials: 'same-origin',
		body: fd
}
fetch('./MediaUpload', configs)
.then(response => response.json())
.then(data => {
	console.log(data);
	document.getElementById('uploadStatus').textContext = data +'\nFile Uploaded';
}).catch(error =>{
	console.log(error.message);
	})
}
