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
        this.state = { scannedList: [], mediaType: '' };        
        
        this.saveSelectedMedias = this.saveSelectedMedias.bind(this);
    }

    componentWillMount() {
        this.setState( {scannedList: this.props.scannedList, mediaType: this.props.mediaType} );
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

        this.state.scannedList.forEach( (path) => {
            const media = { filePath: path, type: this.state.mediaType };
            
            $.ajax({
                url:"http://localhost:8080/media",
                headers: { 'Content-Type': 'application/json' },
                type: 'POST',
                crossDomain: true,        
                dataType: 'json',
                data: JSON.stringify(media),
                success: function(response) {
                  console.log('Item saved: ', JSON.stringify(response));
                  PubSub.publish('info-topic','Saved item: ' + JSON.stringify(response.resource.filename));

                //   const newList = this.state.scannedList.map( (path) => {
                //       if (path === media.resource.filename) {

                //       }
                //   } );

                },//.bind(this),
                error: function(response) {        
                  console.log("Error: " + JSON.stringify(response));
                  PubSub.publish('error-topic',response.responseJSON.message);
                }
              });
        } );

            
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
                            <button type="submit" className="btn btn-outline-primary">Save All</button>
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
                                const filename = item.replace(/^.*[\\/]/, '');
                                return (
                                <tr key={index + 1 + item}>
                                    <td className="centered">{index+1}</td>
                                    <td>{filename}</td>
                                    <td>{item}</td>
                                    <td className="centered"><button type="button" className="btn btn-outline-info">Save</button></td>
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