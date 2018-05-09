const fetch = require('node-fetch'); 

export default class AcervoramaService {

    constructor() {
        this._API_BASE_URL = 'http://localhost:8080';
        this._MEDIA_RESOURCE = '/media';
        this._MEDIA_API_URL = this._API_BASE_URL + this._MEDIA_RESOURCE;
    }


    getMediaItems() {
        return fetch(this._MEDIA_API_URL)
                    .then(response => {
                        if (!response.ok) throw new Error(response);
                        return response.json();
                    }) 
                    .catch(err => { 
                        throw new Error(err.message) 
                    });
    }


} 