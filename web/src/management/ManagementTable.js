import React, { Component } from 'react';

import $ from 'jquery';
import PubSub from 'pubsub-js';

import UIMessageDispatcher from '../UIMessageDispatcher';

import '../css/bootstrap.min.css';
import '../css/pnotify.custom.min.css';


class ManagementTable extends Component {

    constructor() {
        super();
        this.state = { mediaList: [] };
    }

    componentWillMount() {
        const mediaList = this.props.mediaList.map( (item) => { return item.resource } );        
        this.setState( {mediaList: mediaList} );
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
      return(

        <div>
          <hr/>

          <h2 className="sub-header">Media Collection List</h2>
          <hr/>

          <form onSubmit={this.saveAllMedia} method="POST">

              <table className="table table-bordered table-hover">
                  <thead>
                      <tr key="header">
                      <th scope="col" className="centered">#</th>
                      <th scope="col">File Name</th>
                      <th scope="col">Full Path</th>
                      <th scope="col">Tags</th>
                      <th scope="col" className="centered">Action</th>
                      </tr>
                  </thead>
                <tbody>

                  {
                  this.state.mediaList.map(function(media, index) {
                          const filename = media.filePath.replace(/^.*[\\/]/, '');
                          return (
                          <tr key={media.hash}>
                              <td className="centered">{index+1}</td>
                              <td>{filename}</td>
                              <td>{media.filePath}</td>
                              <td>{media.tags}</td>
                              <td className="centered">
                                <button type="button" className="btn btn-outline-info" onClick={() => console.log("click")} >View</button>
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

export default ManagementTable;        