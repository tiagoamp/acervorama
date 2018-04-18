import React, { Component } from 'react';

import SideMenu from './SideMenu';
import Header from './commom/Header';
//import ScanForm from './scanner/ScanForm';

import $ from 'jquery';
import PubSub from 'pubsub-js';

import UIMessageDispatcher from './UIMessageDispatcher';

import './css/bootstrap.min.css';
import './css/pnotify.custom.min.css';
import './css/scanner.css';


class Scan extends Component {

  constructor() {
    super();
    this.state = { activePanel: 1 };

    this.state = { mediaPath:'', mediaType:'', mediaScannedList: [] }; 

    this.sendScanForm = this.sendScanForm.bind(this); 
    this.setMediaPath = this.setMediaPath.bind(this);
    this.setMediaType = this.setMediaType.bind(this);
  }

  sendScanForm(event) {
    event.preventDefault();

    $.ajax({
      url:"http://localhost:8080/scanner",
      /*headers: { 'Access-Control-Allow-Origin': '*', 
                 'Access-Control-Allow-Credentials': true, 
                 'Access-Control-Allow-Methods': 'GET, POST, PATCH, PUT, DELETE, OPTIONS',
                 'Access-Control-Allow-Headers': 'Origin, X-Requested-With, Content-Type, Accept, X-Auth-Token',                  
                 'Access-Control-Expose-Headers': 'Access-Control-*, Origin, X-Requested-With, Content-Type, Accept, Authorization' 
                },*/
      crossDomain: true,        
      dataType: 'json',
      data: {type:this.state.mediaType , dirPath:this.state.mediaPath},
      success: function(response) {
        this.setState( {mediaScannedList: response} );
        PubSub.publish('scanned-topic',response);
      }.bind(this),
      error: function(response) {        
        console.log("Error: " + JSON.stringify(response));
        PubSub.publish('error-topic','Error while accessing api service!');
      }
    });

  }

  setMediaPath(event) {
    this.setState({mediaPath:event.target.value});
  }

  setMediaType(event) {
    this.setState({mediaType:event.target.value});
  }


  componentDidMount() {
    PubSub.subscribe('error-topic', function(topico, content) {
        UIMessageDispatcher.showErrorMessage(content);                        
    });      
  }
  
  render() {
    return (

      <div>
        
        <Header/> 

        <div className="container-fluid">
          <div className="row">

            <SideMenu activeItem={this.state.activePanel} />
            
            <main role="main" className="col-md-9 ml-sm-auto col-lg-10 pt-3 px-4">

              <section>
                <h2 className="sub-header">Media Collection Scanner</h2>
                <hr />

                <form onSubmit={this.sendScanForm} method="POST">
                  <div className="form-group row">
                    <label className="col-sm-2 col-form-label">Directory path: </label>
                    <div className="col-sm-10">
                      <input type="text" className="form-control" id="inputDirectory" name="inputDirectory" value={this.state.mediaPath} onChange={this.setMediaPath} placeholder="Enter directory path" />
                    </div>
                  </div>                
                  <fieldset className="form-group">
                    <div className="row">
                      <legend className="col-form-label col-sm-2 pt-0">Media Type: </legend>
                      <div className="col-sm-10">
                        <div className="form-check form-check-inline">
                          <input className="form-check-input" type="radio" name="inputMediaType" id="inputAudio" value="AUDIO" onClick={this.setMediaType} />
                          <label className="form-check-label" htmlFor="inputAudio">
                            Audio
                          </label>
                        </div>
                        <div className="form-check form-check-inline">
                          <input className="form-check-input" type="radio" name="inputMediaType" id="inputImage" value="IMAGE" onClick={this.setMediaType} />
                          <label className="form-check-label" htmlFor="inputImage">
                            Image
                          </label>
                        </div>
                        <div className="form-check form-check-inline">
                          <input className="form-check-input" type="radio" name="inputMediaType" id="inputText" value="TEXT" onClick={this.setMediaType} />
                          <label className="form-check-label" htmlFor="inputText">
                            Text
                          </label>
                        </div>
                        <div className="form-check form-check-inline">
                          <input className="form-check-input" type="radio" name="inputMediaType" id="inputVideo" value="VIDEO" onClick={this.setMediaType} />
                          <label className="form-check-label" htmlFor="inputVideo">
                            Video
                          </label>
                        </div> 
                      </div>
                    </div>
                  </fieldset>                
                  <div className="form-group row">
                    <div className="col-sm-10">
                      <button type="submit" className="btn btn-primary">Scan!</button>
                    </div>
                  </div>
                </form>

                <hr/>
                
                <h2 className="sub-header">Media Collection List</h2>
                <hr/>

                <form onSubmit={this.sendMediaToSaveForm} method="POST">

                  <div className="form-group row">
                    <div className="col-sm-10">
                      <button type="button" className="btn btn-outline-primary">Save All</button>
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
                        this.state.mediaScannedList.map(function(item, index) {
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

              </section>

            </main>

          </div>
        </div>
      </div>

    );
  }

}

export default Scan;
