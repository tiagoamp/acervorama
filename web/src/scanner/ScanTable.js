import React, { Component } from 'react';

import $ from 'jquery';
import PubSub from 'pubsub-js';

import UIMessageDispatcher from '../UIMessageDispatcher';

import '../css/bootstrap.min.css';
import '../css/pnotify.custom.min.css';
import '../css/scanner.css';


class ScanTable extends Component {

    constructor() {
        super();
        this.state = { scannedList: [], mediaType: '', hasUnsavedMedia: true };        
        
        this.saveSelectedMedias = this.saveSelectedMedias.bind(this);
        this._updateState = this._updateState.bind(this);
    }

    componentWillMount() {
        const mediaList = this.props.scannedList.map( (path) => { return { path: path, isSaved: false}; } );        
        this.setState( {scannedList: mediaList, mediaType: this.props.mediaType} );
    }

    componentDidMount() {
        PubSub.subscribe('error-topic', function(topico, content) {
          UIMessageDispatcher.showErrorMessage(content);                        
        }); 
        PubSub.subscribe('info-topic', function(topico, content) {
          UIMessageDispatcher.showInfoMessage(content);            
        });   
      }

    saveSelectedMedias(event) {
        event.preventDefault();

        this.state.scannedList.forEach( (m) => {
            const media = { filePath: m.path, type: this.state.mediaType };
            
            $.ajax({
                url:"http://localhost:8080/media",
                headers: { 'Content-Type': 'application/json' },
                type: 'POST',
                crossDomain: true,        
                dataType: 'json',
                data: JSON.stringify(media),
                success: function(response) {
                    PubSub.publish('info-topic','Saved item: ' + JSON.stringify(response.resource.filename));
                    this._updateState(m);
                }.bind(this),
                error: function(response) {        
                    console.log("Error: " + JSON.stringify(response));
                    PubSub.publish('error-topic',response.responseJSON.message);
                }
              });
        } );
            
      }

    _updateState(savedMedia) {
        let savedCounter = 0;

        const newList = this.state.scannedList.map( (media) => {
            if (media.path === savedMedia.path) media.isSaved = true;
            if (media.isSaved) savedCounter++;
            return media;
        } );

        const hasUnsavedMedia = savedCounter < newList.length;
        this.setState( {scannedList: newList, hasUnsavedMedia: hasUnsavedMedia} )        
    }


    render() {

        return (

            <div>
                <hr/>

                <h2 className="sub-header">Media Collection List</h2>
                <hr/>

                <form onSubmit={this.saveSelectedMedias} method="POST">

                    <div className="form-group row">
                        <div className="col-sm-10">
                            {this.state.hasUnsavedMedia ? (
                                <button type="submit" className="btn btn-outline-primary">Save All</button>
                            ) : (
                                <button disabled type="submit" className="btn btn-outline-primary">All Saved!</button>
                            )}
                        </div>
                    </div>               
                
                    <table className="table table-bordered table-hover">
                        <thead>
                            <tr key="header">
                            <th scope="col" className="centered">#</th>
                            <th scope="col">File Name</th>
                            <th scope="col">Full Path</th>
                            <th scope="col" className="centered">Action</th>
                            </tr>
                        </thead>
                    <tbody>


                        {
                        this.state.scannedList.map(function(item, index) {
                                const filename = item.path.replace(/^.*[\\/]/, '');                                
                                return (
                                <tr key={index + 1 + item}>
                                    <td className="centered">{index+1}</td>
                                    <td>{filename + " - " + item.isSaved}</td>
                                    <td>{item.path}</td>
                                    <td className="centered">
                                        {item.isSaved ? (
                                            <button disabled type="button" className="btn btn-outline-info">Saved!</button>
                                        ) : (
                                            <button type="button" className="btn btn-outline-info">Save</button>
                                        )}

                                    </td>
                                </tr>      
                                );
                            })
                        }


                    </tbody>
                    </table>
                </form>  

            </div>

        );
    }

}

export default ScanTable;