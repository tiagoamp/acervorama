import React, { Component } from 'react';
import { Modal, Button } from 'react-bootstrap';

import TagsInput from 'react-tagsinput';

import AcervoramaService from '../service/AcervoramaService';

import 'react-tagsinput/react-tagsinput.css';
import '../css/bootstrap.min.css';


class MediaItemView extends Component {

    constructor(props) {
        super(props);
        this._service = new AcervoramaService();

        const media = this.props.media;
        for (let prop in media) {
            if (media[prop] == null) media[prop] = '';
        }

        this.state = { media: media };        

        this.updateMedia = this.updateMedia.bind(this);
        this.deleteItem = this.deleteItem.bind(this);
    }

    componentDidMount() {
        this._service.subscribeToTopics(['error-topic','info-topic']);        
    }
    
    setInputValueToState(inputName,event) {
        const media = this.state.media;
        media[inputName] = event.target.value; 
        this.setState(media);
    }

    handleTagsChange(tags) {
        const media = this.state.media;
        media.tags = tags.join(',');
        this.setState({media});
    }

    _showAuthorField(media) {
        if (this.state.media.type === 'AUDIO' || this.state.media.type === 'TEXT') {
            return (
                <div className="form-group row">
                    <label htmlFor="input-title" className="col-sm-2 col-form-label col-form-label-sm">Title</label>
                    <div className="col-sm-10">
                        <input type="text" className="form-control form-control-sm" id="input-title" value={media.title} onChange={this.setInputValueToState.bind(this,'title')} placeholder="Enter title..." />
                    </div>
                </div>
            );
        }
    }

    _showTitleField(media) {
        if (this.state.media.type === 'AUDIO'  || this.state.media.type === 'TEXT' || this.state.media.type === 'VIDEO') {
            return (
                <div className="form-group row">
                    <label htmlFor="input-author" className="col-sm-2 col-form-label col-form-label-sm">Author</label>
                    <div className="col-sm-10">
                        <input type="text" className="form-control form-control-sm" id="input-author" value={media.author} onChange={this.setInputValueToState.bind(this,'author')} placeholder="Enter author..." />
                    </div>
                </div>
            );
        }
    }

    updateMedia(event) {
        event.preventDefault();    
        const media = this.state.media;

        this._service.updateMediaItem(media)
            .then( res => {
                this._service.publishMessage('info-topic','Updated item: ' + JSON.stringify(media.filename));
                this.props.cbUpdateState(media, 'UPDATE');
            })
            .catch(err => {
                this._service.publishMessage('error-topic',err.message);
            });         
      }

    deleteItem(event) {
        event.preventDefault();    
        const media = this.state.media;

        this._service.deleteMediaItem(media)
            .then( res => {                
                this._service.publishMessage('info-topic','Deleted item: ' + JSON.stringify(media.filename));
                this.props.cbUpdateState(media, 'DELETE');
            })
            .catch(err => {
                this._service.publishMessage('error-topic',err.message);
            });
    }


    render() {
        const media = this.state.media;

        return (
            <div>

                <Modal.Dialog bsSize="large">
                    <Modal.Header>
                        <Modal.Title>Media Item</Modal.Title>
                    </Modal.Header>
                
                    <form onSubmit={this.updateMedia} method="POST">

                        <Modal.Body>
                            <div className="form-group row">
                                <label htmlFor="input-filepath" className="col-sm-2 col-form-label col-form-label-sm">File Path</label>
                                <div className="col-sm-10">
                                    <input type="text" className="form-control form-control-sm" id="input-filepath" value={media.filePathAsString} readOnly />
                                </div>
                            </div>
                            <div className="form-group row">
                                <label htmlFor="input-filename" className="col-sm-2 col-form-label col-form-label-sm">File Name</label>
                                <div className="col-sm-10">
                                    <input type="text" className="form-control form-control-sm" id="input-filename" value={media.filename} readOnly />
                                </div>
                            </div>
                            <div className="form-group row">
                                <label htmlFor="input-registration-date" className="col-sm-2 col-form-label col-form-label-sm">Regist. Date</label>
                                <div className="col-sm-10">
                                    <input type="text" className="form-control form-control-sm" id="input-registration-date" value={media.registerDate} readOnly />
                                </div>
                            </div>
                            <div className="form-group row">
                                <label htmlFor="input-mediatype" className="col-sm-2 col-form-label col-form-label-sm">Media Type</label>
                                <div className="col-sm-10">
                                    <input type="text" className="form-control form-control-sm" id="input-mediatype" value={media.type} readOnly />
                                </div>
                            </div>
                            <div className="form-group row">
                                <label htmlFor="input-classification" className="col-sm-2 col-form-label col-form-label-sm">Classification</label>
                                <div className="col-sm-10">
                                    <input type="text" className="form-control form-control-sm" id="input-classification" value={media.classification} onChange={this.setInputValueToState.bind(this,'classification')} placeholder="Enter classification..." />
                                </div>
                            </div>


                            { this._showTitleField(media) }
                            
                            { this._showAuthorField(media) }

                            
                            <div className="form-group row">
                                <label htmlFor="input-description" className="col-sm-2 col-form-label col-form-label-sm">Description</label>
                                <div className="col-sm-10">
                                    <textarea className="form-control form-control-sm" id="input-description" rows="2" value={media.description} onChange={this.setInputValueToState.bind(this,'description')} placeholder="Enter description..."></textarea>                                    
                                </div>
                            </div>
                            <div className="form-group row">
                                <label htmlFor="input-comments" className="col-sm-2 col-form-label col-form-label-sm">Comments</label>
                                <div className="col-sm-10">
                                    <textarea className="form-control form-control-sm" id="input-comments" rows="2" value={media.additionalInformation} onChange={this.setInputValueToState.bind(this,'additionalInformation')} placeholder="Enter comments..."></textarea>
                                </div>
                            </div>
                            <div className="form-group row">
                                <label htmlFor="input-tags" className="col-sm-2 col-form-label col-form-label-sm">Tags</label>
                                <div className="col-sm-10">
                                    <TagsInput value={this.state.media.tags ? this.state.media.tags.split(",") : []} onChange={this.handleTagsChange.bind(this)} />  
                                </div>
                            </div>
                            
                        </Modal.Body>
                    
                        <Modal.Footer>
                            <Button bsSize="small" onClick={this.props.cbClose}>Close</Button>
                            <Button bsStyle="danger" bsSize="small" onClick={this.deleteItem}>Delete</Button>
                            <Button bsStyle="primary" bsSize="small" type="submit">Save changes</Button>
                        </Modal.Footer>

                    </form>

                </Modal.Dialog>

            </div>
        );
    }
  
}

export default MediaItemView;        