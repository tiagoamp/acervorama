import PubSub from 'pubsub-js';
import fetch from 'node-fetch'; 

import UIMessageDispatcher from '../UIMessageDispatcher';


export default class AcervoramaService {

    constructor() {
        this._API_BASE_URL = 'http://localhost:8080';
        this._MEDIA_RESOURCE = '/media';
        this._SCANNER_RESOURCE = '/scanner';
        this._MEDIA_API_URL = this._API_BASE_URL + this._MEDIA_RESOURCE;
        this._SCANNER_API_URL = this._API_BASE_URL + this._SCANNER_RESOURCE;
    }


    getMediaItems() {
        return fetch(this._MEDIA_API_URL)
                    .then(response => {
                        if (!response.ok) throw new Error('Error to access api service!');
                        return response.json();
                    }) 
                    .catch(err => { 
                        console.log(JSON.stringify(err.message)); 
                        throw err;
                    });
    }

    scanDirectory(mediaType, mediaPath) {
        var params = {
            type: mediaType,
            dirPath: mediaPath
        };
        
        var enc = encodeURIComponent;
        var queryParams = Object.keys(params).map(k => enc(k) + '=' + enc(params[k])).join('&');
      
        return fetch(this._SCANNER_API_URL + '?' + queryParams)
                .then(response => {
                    if (response.status === 400) throw new Error('Bad parameters!');
                    else if (response.status === 500) throw new Error('Error to access api service!');
                    return response.json()
                })
                .catch( err => {
                    console.log(JSON.stringify(err.message)); 
                    throw err;
                });
    }


    publishMessage(topic, content) {
        PubSub.publish(topic,content);  
    }

    subscribeToTopic(topic) {
        PubSub.subscribe(topic, function(topic, content) {
            if (topic === 'info-topic') UIMessageDispatcher.showInfoMessage(content);
            else if (topic === 'error-topic') UIMessageDispatcher.showErrorMessage(content);
        });
    }

} 