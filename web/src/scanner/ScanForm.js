import React, { Component } from 'react';

import '../css/bootstrap.min.css';
import '../css/pnotify.custom.min.css';


class ScanForm extends Component {

  render() {
    return (

            <section>
              <h2 className="sub-header">Media Collection Scanner</h2>
              <hr />
              <form>
                <div className="form-group row">
                  <label className="col-sm-2 col-form-label">Directory path: </label>
                  <div className="col-sm-10">
                    <input type="text" className="form-control" id="inputDirectory" name="inputDirectory" placeholder="Enter directory path" />
                  </div>
                </div>                
                <fieldset className="form-group">
                  <div className="row">
                    <legend className="col-form-label col-sm-2 pt-0">Media Type: </legend>
                    <div className="col-sm-10">
                      <div className="form-check form-check-inline">
                        <input className="form-check-input" type="radio" name="inputMediaType" id="inputAudio" value="AUDIO" />
                        <label className="form-check-label" htmlFor="inputAudio">
                          Audio
                        </label>
                      </div>
                      <div className="form-check form-check-inline">
                        <input className="form-check-input" type="radio" name="inputMediaType" id="inputImage" value="IMAGE" />
                        <label className="form-check-label" htmlFor="inputImage">
                          Image
                        </label>
                      </div>
                      <div className="form-check form-check-inline">
                        <input className="form-check-input" type="radio" name="inputMediaType" id="inputText" value="TEXT" />
                        <label className="form-check-label" htmlFor="inputText">
                          Image
                        </label>
                      </div>
                      <div className="form-check form-check-inline">
                        <input className="form-check-input" type="radio" name="inputMediaType" id="inputVideo" value="VIDEO" />
                        <label className="form-check-label" htmlFor="inputVideo">
                          Image
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
            </section>

    );
  }

}

export default ScanForm;
