import React, { Component } from 'react';

export default class MediaTables extends Component {

    render() {
        return(
            
            <div>
                <h4>Media Types</h4>
                <div className="table-responsive">
                    <table className="table table-striped table-sm">
                        <thead>
                        <tr>
                            <th>#Total</th>
                            <th>Audio</th>
                            <th>Video</th>
                            <th>Image</th>
                            <th>Text</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>{this.props.totalAudio + this.props.totalVideo + this.props.totalImage + this.props.totalText}</td>
                            <td>{this.props.totalAudio}</td>
                            <td>{this.props.totalVideo}</td>
                            <td>{this.props.totalImage}</td>
                            <td>{this.props.totalText}</td>                            
                        </tr>                                     
                        </tbody>
                    </table>
                </div>
            </div>

        );
    }

}