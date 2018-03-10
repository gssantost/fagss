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