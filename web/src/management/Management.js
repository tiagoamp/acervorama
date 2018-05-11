import React, { Component } from 'react';
import TagsInput from 'react-tagsinput'

import AcervoramaService from '../service/AcervoramaService';
import SideMenu from '../commom/SideMenu';
import ManagementTable from './ManagementTable';

import 'react-tagsinput/react-tagsinput.css'; 
import '../css/bootstrap.min.css';
import '../css/pnotify.custom.min.css';


class Management extends Component {

  constructor() {
    super();
    this.state = { activePanel: 2, filename:'', classification:'', mediaType:'', tags: [], mediaList: [] };
    this._service = new AcervoramaService();

    this.sendSearchForm = this.sendSearchForm.bind(this);
  }

  componentDidMount() {
    this._service.subscribeToTopics(['error-topic','info-topic']);        
  }

  setInputValueToState(inputName,event) {
    let alteredInput = {};
    alteredInput[inputName] = event.target.value; 
    this.setState(alteredInput);
  }

  handleTagsChange(tags) {
    this.setState({tags})
  }

  _showManagementTable() {
    if (this.state.mediaList.length > 0) {
      return (<ManagementTable mediaList={this.state.mediaList} />);
    } 
  }  

  sendSearchForm(event) {
    event.preventDefault();
    const tagsCsv = this.state.tags.join(",");

    this._service.searchMediaItems(this.state.filename, this.state.classification, this.state.mediaType, tagsCsv)
      .then( res => {
        this.setState( {mediaList: res} );
        this._service.publishMessage('info-topic','Search Performed! \nFound ' + res.length + ' items.');
      })
      .catch(err => {
        this._service.publishMessage('error-topic',err.message);
      });         
  }

  
  render() {
    return (

      <div>
        
        <div className="container-fluid">
          <div className="row">

            <SideMenu activeItem={this.state.activePanel} />
            
            <main role="main" className="col-md-9 ml-sm-auto col-lg-10 pt-3 px-4">

              <section>

                <h2 className="sub-header">Media Collection Management</h2>
                <hr />

                <form onSubmit={this.sendSearchForm} method="POST">
                  <div className="form-group row">
                    <label className="col-sm-2 col-form-label">File Name: </label>
                    <div className="col-sm-10">
                      <input type="text" className="form-control" id="inputFilename" name="inputFilename" value={this.state.filename} onChange={this.setInputValueToState.bind(this,'filename')} placeholder="Enter file name" />
                    </div>                    
                  </div>
                  <div className="form-group row">
                    <label className="col-sm-2 col-form-label">Classification: </label>
                    <div className="col-sm-10">
                      <input type="text" className="form-control" id="inputClassification" name="inputClassification" value={this.state.classification} onChange={this.setInputValueToState.bind(this,'classification')} placeholder="Enter classification" />
                    </div>                    
                  </div>
                  <div className="form-group row">
                    <label className="col-sm-2 col-form-label">Tags: </label>
                    <div className="col-sm-10">                    
                      <TagsInput value={this.state.tags} onChange={this.handleTagsChange.bind(this)} />  
                    </div>                    
                  </div>
                  <fieldset className="form-group">
                    <div className="row">
                      <legend className="col-form-label col-sm-2 pt-0">Media Type: </legend>                      
                      <div className="col-sm-10">
                        <div className="form-check form-check-inline">
                          <input className="form-check-input" type="radio" name="inputMediaType" id="inputAudio" value="AUDIO" onClick={this.setInputValueToState.bind(this,'mediaType')} />
                          <label className="form-check-label" htmlFor="inputAudio">
                            Audio
                          </label>                          
                        </div>
                        <div className="form-check form-check-inline">
                          <input className="form-check-input" type="radio" name="inputMediaType" id="inputImage" value="IMAGE" onClick={this.setInputValueToState.bind(this,'mediaType')} />
                          <label className="form-check-label" htmlFor="inputImage">
                            Image
                          </label>                          
                        </div>
                        <div className="form-check form-check-inline">
                          <input className="form-check-input" type="radio" name="inputMediaType" id="inputText" value="TEXT" onClick={this.setInputValueToState.bind(this,'mediaType')} />
                          <label className="form-check-label" htmlFor="inputText">
                            Text
                          </label>                          
                        </div>
                        <div className="form-check form-check-inline">
                          <input className="form-check-input" type="radio" name="inputMediaType" id="inputVideo" value="VIDEO" onClick={this.setInputValueToState.bind(this,'mediaType')} />
                          <label className="form-check-label" htmlFor="inputVideo">
                            Video
                          </label>                          
                        </div> 
                      </div>
                    </div>
                  </fieldset>                                     
                  <div className="form-group row">
                    <div className="col-sm-10">
                      <button type="submit" className="btn btn-primary">Search!</button>
                    </div>
                  </div>
                </form>


                { this._showManagementTable() }
                

              </section>

            </main>

          </div>
        </div>

      </div>

    );
  }

}

export default Management;
