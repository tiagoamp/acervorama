import React, { Component } from 'react';

import AcervoramaService from '../service/AcervoramaService';

import '../css/bootstrap.min.css';
import '../css/pnotify.custom.min.css';
import '../css/scanner.css';


class ScanTable extends Component {

    constructor() {
        super();
        this.state = { scannedList: [], mediaType: '', hasUnsavedMedia: true };        
        this._service = new AcervoramaService();

        this.saveAllMedia = this.saveAllMedia.bind(this);
        this._updateState = this._updateState.bind(this);
    }

    componentWillMount() {
        const mediaList = this.props.scannedList.map( (path) => { return { path: path, isSaved: false}; } );        
        this.setState( {scannedList: mediaList, mediaType: this.props.mediaType} );
    }

    componentDidMount() {
        this._service.subscribeToTopics(['error-topic','info-topic']);        
    }

    _postMedia(m) {
        this._service.saveScannedMedia(m.path, this.state.mediaType)
            .then( res => {
                this._service.publishMessage('info-topic','Saved item: ' + JSON.stringify(res.resource.filename));
                this._updateState(m);
            })
            .catch(err => {
                this._service.publishMessage('error-topic',err.message);
            });
    }

    saveAllMedia(event) {
        event.preventDefault();
        this.state.scannedList.forEach( (m) => {
            if (!m.isSaved) this._postMedia(m);            
        } );            
      }

    saveSelectedMedia(index,event) {
        event.preventDefault();
        this._postMedia(this.state.scannedList[index]);
    }

    _updateState(savedMedia) {        
        const newList = this.state.scannedList.map( (media) => {
            if (media.path === savedMedia.path) media.isSaved = true;
            return media;
        });
        
        let savedCounter = newList.reduce((acc,curr) => {
            if (curr.isSaved) acc++;
            return acc;
        },0);

        const hasUnsavedMedia = savedCounter < newList.length;
        this.setState( {scannedList: newList, hasUnsavedMedia: hasUnsavedMedia} )        
    }


    render() {

        return (

            <div>
                <hr/>

                <h2 className="sub-header">Media Collection List</h2>
                <hr/>

                <form onSubmit={this.saveAllMedia} method="POST">

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
                                    <td>{filename}</td>
                                    <td>{item.path}</td>
                                    <td className="centered">
                                        {item.isSaved ? (
                                            <button disabled type="button" className="btn btn-outline-info">Saved!</button>
                                        ) : (
                                            <button type="button" className="btn btn-outline-info" onClick={this.saveSelectedMedia.bind(this,index)} >Save</button>
                                        )}

                                    </td>
                                </tr>      
                                );
                            }.bind(this))
                        }


                    </tbody>
                    </table>
                </form>  

            </div>

        );
    }

}

export default ScanTable;