import React, { Component } from 'react';

import '../css/bootstrap.min.css';
import '../css/pnotify.custom.min.css';
import '../css/scanner.css';


class ScanTable extends Component {

    constructor() {
        super();
        this.state = { selectedItem: {} };        
    }

    render() {
        return (

            <div>

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
                        this.props.scannedList.map(function(item, index) {
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
