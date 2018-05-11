import React, { Component } from 'react';

import AcervoramaService from '../service/AcervoramaService';
import MediaItemView from './MediaItemView';

import '../css/bootstrap.min.css';
import '../css/pnotify.custom.min.css';


class ManagementTable extends Component {

    constructor() {
        super();
        this.state = { mediaList: [], showModal: false, selectedMedia: {} };
        this._service = new AcervoramaService();
    }

    componentWillMount() {
        const mediaList = this.props.mediaList.map( (item) => { return item.resource } );        
        this.setState( {mediaList: mediaList} );
    }

    componentDidMount() {
        this._service.subscribeToTopics(['error-topic','info-topic']);        
    }
   
    handleModalClose() {
        this.setState({ showModal: false });
    }
    
    handleModalShow(index,event) {
        event.preventDefault();
        this.setState({ showModal: true, selectedMedia: this.state.mediaList[index] });        
    }

    updateMediaListAfterAction(alteredMedia, action) {
        let mediaList = this.state.mediaList;
        const index = mediaList.findIndex( (media) => media.hash === alteredMedia.hash );

        if (action === 'DELETE') {
            mediaList.splice(index, 1);
            this.handleModalClose();
        } else {
            mediaList[index] = alteredMedia;
        }

        this.setState(mediaList);
    }

    _showModalFrame() {
        if (this.state.showModal) {
          return (<MediaItemView cbClose={this.handleModalClose.bind(this)} cbUpdateState={this.updateMediaListAfterAction.bind(this)} 
                                 media={this.state.selectedMedia} />);
        } 
      } 


    render() {
      return(

        <div>
          <hr/>

          <h2 className="sub-header">Media Collection List</h2>
          <hr/>

          
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
                            const filename = media.filePathAsString.replace(/^.*[\\/]/, '');
                            return (
                                <tr key={media.hash}>
                                    <td className="centered">{index+1}</td>
                                    <td>{filename}</td>
                                    <td>{media.filePathAsString}</td>
                                    <td>{media.tags}</td>
                                    <td className="centered">
                                    <button type="button" className="btn btn-outline-info" onClick={this.handleModalShow.bind(this,index)} >View</button>
                                    </td>
                                </tr>
                            );
                        }.bind(this))
                    }

                </tbody>
            </table>
            

            { this._showModalFrame() }

            
        </div>
        
      );
    }

}

export default ManagementTable;        