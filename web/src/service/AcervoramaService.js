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
        var params = { type: mediaType, dirPath: mediaPath };        
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

    saveScannedMedia(filePath, mediaType) {
        const media = { filePath, type: mediaType };        
        const options = {
            method: 'POST',
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(media)
        }; 

        return fetch(this._MEDIA_API_URL, options)
            .then(response => {
                if (response.status === 409) throw new Error('File path already exists: ' + filePath);
                else if (response.status !== 201 && response.message) throw new Error(response.message);
                return response.json();
            })
            .catch( err => {
                console.log(JSON.stringify(err.message)); 
                throw err;
            });
    }

    searchMediaItems(filename, classification, mediaType, tagsCsv) {
        var params = {filename, classification, type: mediaType, tags: tagsCsv};        
        var enc = encodeURIComponent;
        var queryParams = Object.keys(params).map(k => enc(k) + '=' + enc(params[k])).join('&');

        return fetch(this._MEDIA_API_URL + '?' + queryParams)
            .then(response => { 
                if (response.status !== 200) throw new Error('Error to access api service!');
                return response.json();
            })
            .catch( err => {
                console.log(JSON.stringify(err.message)); 
                throw err;
            });
    }

    updateMediaItem(media) {
        const options = {
            method: 'PUT',
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(media)
        };

        return fetch(this._MEDIA_API_URL + "/" + media.id, options)
            .then(response => { 
                if (response.status === 404) throw new Error('File path do not exists: ' + media.filePathAsString);
                else if (response.status !== 200) throw new Error('Error to access api service!');                
            })
            .catch( err => {
                console.log(JSON.stringify(err.message)); 
                throw err;
            });
    }

    deleteMediaItem(media) {
        const options = { method: 'DELETE' };
        return fetch(this._MEDIA_API_URL + '/' + media.id, options)
            .then(response => { 
                if (response.status !== 204) throw new Error('Error to access api service!');                
            })
            .catch( err => {
                console.log(JSON.stringify(err.message)); 
                throw err;
            });
    }

    publishMessage(topic, content) {
        PubSub.publish(topic,content);  
    }

    subscribeToTopics(topicsArr) {
        topicsArr.forEach( topic => {
            PubSub.subscribe(topic, function(topic, content) {
                if (topic === 'info-topic') UIMessageDispatcher.showInfoMessage(content);
                else if (topic === 'error-topic') UIMessageDispatcher.showErrorMessage(content);
            });
        });        
    }

} 