class XhrPromise {
    constructor(properties) {
        
        const mergeJson = (defaults, userDefined) => {
            let props = [];
            for (let attr in defaults) {
                props[attr] = defaults[attr]
            } 
            for (let attr in userDefined) {
                props[attr] = userDefined[attr]
            }
            return props;
        }
        
        const defaults = { url:'', method:'GET', params:'' };
        
        this.properties = (properties) ? mergeJson(this.defaults, properties) : this.defaults;
    }
    
    do() {
        return new Promise((resolve, reject) => {
            try {
                const xhr = new XMLHttpRequest();
                xhr.onload = () => {
                    if (xhr.readyState === 4 && (xhr.status >= 200 && xhr.status < 300)) {
                        resolve(JSON.parse(xhr.responseText));
                    } 
                    else {
                        reject({status: xhr.status, statusText: xhr.statusText})
                    }
                }
                xhr.open(this.properties.method, this.properties.url, true);
                
                if (this.properties.method === 'POST') {
                    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
                }
                
                if (this.properties.method === 'POST') {
                    xhr.send(JSON.stringify(this.properties.params));
                } else {
                    xhr.send();
                }
            } catch(err) {
                console.log('Error. No se ha podido realizar su petición');
                reject(err);
            }
        });
    }

    get () {
        return new Promise((resolve, reject) => {
            this.do().then(data => resolve(data));
        });
    }
    
    post () {
        return new Promise((resolve, reject) => {
            this.do().then(data => resolve(data));
        });
    }
    
}