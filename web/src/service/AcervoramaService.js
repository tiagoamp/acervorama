import PubSub from 'pubsub-js';
import fetch from 'node-fetch'; 

import UIMessageDispatcher from '../UIMessageDispatcher';
import { config } from './config';

export default class AcervoramaService {

    constructor() {
        this.config = config;        
    }


    getMediaItems() {
        return fetch(this.config.MEDIA_API_URL)
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
        const params = { type: mediaType, dirPath: mediaPath };        
        const enc = encodeURIComponent;
        const queryParams = Object.keys(params).map(k => enc(k) + '=' + enc(params[k])).join('&');
      
        return fetch(this.config.SCANNER_API_URL + '?' + queryParams)
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

    saveScannedMedia(filePath, type) {
        const media = { filePath, type };        
        const options = {
            method: 'POST',
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(media)
        }; 

        return fetch(this.config.MEDIA_API_URL, options)
            .then(response => {
                if (response.status === 409) throw new Error('File path already exists: ' + filePath);
                else if (response.status === 500) throw new Error('Error to access api service!');
                else if (response.status !== 201 && response.message) throw new Error(response.message);
                return response.json();
            })
            .catch( err => {
                console.log(JSON.stringify(err.message)); 
                throw err;
            });
    }

    searchMediaItems(filename, classification, type, tagsCsv) {
        const params = {filename, classification, type, tags: tagsCsv};        
        const enc = encodeURIComponent;
        const queryParams = Object.keys(params).map(k => enc(k) + '=' + enc(params[k])).join('&');

        return fetch(this.config.MEDIA_API_URL + '?' + queryParams)
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

        return fetch(this.config.MEDIA_API_URL + "/" + media.id, options)
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
        return fetch(this.config.MEDIA_API_URL + '/' + media.id, options)
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