import React, { Component } from 'react';
import { Modal, Button } from 'react-bootstrap';

import '../css/bootstrap.min.css';


class MediaItemView extends Component {

    constructor() {
        super();
        this.state = { show: false, media: {} };        
    }

    componentDidMount() {
        this.setState( {media: this.props.media }) ;
    }

    render() {
        return (
            <div >

                <Modal.Dialog bsSize="large">
                    <Modal.Header>
                        <Modal.Title>Modal title</Modal.Title>
                    </Modal.Header>
                
                    <Modal.Body>
                        One fine body...
                        <p>
                            { JSON.stringify(this.state.media) }
                        </p>
                    </Modal.Body>
                
                    <Modal.Footer>
                        <Button onClick={this.props.cbClose}>Close</Button>
                        <Button bsStyle="primary">Save changes</Button>
                    </Modal.Footer>
                </Modal.Dialog>

            </div>
        );
    }
  
}

export default MediaItemView;        