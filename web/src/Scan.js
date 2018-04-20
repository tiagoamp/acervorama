import React, { Component } from 'react';

import SideMenu from './SideMenu';
import Header from './commom/Header';
import ScanTable from './scanner/ScanTable';

import $ from 'jquery';
import PubSub from 'pubsub-js';

import UIMessageDispatcher from './UIMessageDispatcher';

import './css/bootstrap.min.css';
import './css/pnotify.custom.min.css';
import './css/scanner.css';


class Scan extends Component {

  constructor() {
    super();
    this.state = { activePanel: 1, mediaPath:'', mediaType:'', mediaScannedList: [], formErrors: new Map() }; 

    this.sendScanForm = this.sendScanForm.bind(this);
    this._cleanFormErrorMessages = this._cleanFormErrorMessages.bind(this);
  }

  _cleanFormErrorMessages() {
    this.setState ( {formErrors: new Map()} );
  }

  _validateScanForm() {    
    let errorsMap = new Map();
    if (this.state.mediaPath === '') {
      errorsMap.set('media-path','Empty media path');      
    }
    if (this.state.mediaType === '') {
      errorsMap.set('media-type','Empty media type');
    }
    return errorsMap;
  }

  sendScanForm(event) {
    event.preventDefault();

    this._cleanFormErrorMessages();

    const errors = this._validateScanForm();
    if (errors.size > 0) {
      console.log("Form validation Errors ocurred!");
      PubSub.publish('error-topic','Input validation errors found!');
      this.setState ( {formErrors: new Map(errors)} );      
      return;
    }

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
        PubSub.publish('info-topic','Scan Performed!');
      }.bind(this),
      error: function(response) {        
        console.log("Error: " + JSON.stringify(response));
        PubSub.publish('error-topic','Error while accessing api service!');
      }
    });

  }

  setInputValueToState(inputName,event) {
    let alteredInput = {};
    alteredInput[inputName] = event.target.value; 
    this.setState(alteredInput);
  }


  componentDidMount() {
    PubSub.subscribe('error-topic', function(topico, content) {
      UIMessageDispatcher.showErrorMessage(content);                        
    }); 
    PubSub.subscribe('info-topic', function(topico, content) {
      UIMessageDispatcher.showInfoMessage(content);            
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
                      <input type="text" className={ 'form-control'.concat(this.state.formErrors.get('media-path') === undefined ? '' : " is-invalid") } id="inputDirectory" name="inputDirectory" value={this.state.mediaPath} onChange={this.setInputValueToState.bind(this,'mediaPath')} placeholder="Enter directory path" />                      
                      <div className="invalid-feedback"> {this.state.formErrors.get('media-path')} </div>
                    </div>                    
                  </div>                
                  <fieldset className="form-group">
                    <div className="row">
                      <legend className="col-form-label col-sm-2 pt-0">Media Type: </legend>                      
                      <div className="col-sm-10">
                        <div className="form-check form-check-inline">
                          <input className={ 'form-check-input'.concat(this.state.formErrors.get('media-type') === undefined ? '' : " is-invalid") } type="radio" name="inputMediaType" id="inputAudio" value="AUDIO" onClick={this.setInputValueToState.bind(this,'mediaType')} />
                          <label className="form-check-label" htmlFor="inputAudio">
                            Audio
                          </label>                          
                        </div>
                        <div className="form-check form-check-inline">
                          <input className={ 'form-check-input'.concat(this.state.formErrors.get('media-type') === undefined ? '' : " is-invalid") } type="radio" name="inputMediaType" id="inputImage" value="IMAGE" onClick={this.setInputValueToState.bind(this,'mediaType')} />
                          <label className="form-check-label" htmlFor="inputImage">
                            Image
                          </label>                          
                        </div>
                        <div className="form-check form-check-inline">
                          <input className={ 'form-check-input'.concat(this.state.formErrors.get('media-type') === undefined ? '' : " is-invalid") } type="radio" name="inputMediaType" id="inputText" value="TEXT" onClick={this.setInputValueToState.bind(this,'mediaType')} />
                          <label className="form-check-label" htmlFor="inputText">
                            Text
                          </label>                          
                        </div>
                        <div className="form-check form-check-inline">
                          <input className={ 'form-check-input'.concat(this.state.formErrors.get('media-type') === undefined ? '' : " is-invalid") } type="radio" name="inputMediaType" id="inputVideo" value="VIDEO" onClick={this.setInputValueToState.bind(this,'mediaType')} />
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


                {this.state.mediaScannedList.length > 0 ? (
                    <ScanTable scannedList={this.state.mediaScannedList} mediaType={this.state.mediaType} />
                ) : (
                    ''
                )}               
                

              </section>

            </main>

          </div>
        </div>
      </div>

    );
  }

}

export default Scan;
