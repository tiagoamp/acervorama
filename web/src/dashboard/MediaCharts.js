import React, { Component } from 'react';
import { Pie } from 'react-chartjs-2';


export default class MediaCharts extends Component {

    constructor() {
        super();
        this.state = { data: {} };
    }

    componentWillMount() {
        const mediaLabels = [ 'Audio', 'Video', 'Image', 'Text' ];
        const mediaTotals = [this.props.totalAudio, this.props.totalVideo, this.props.totalImage, this.props.totalText ];

        const data = {
            labels: mediaLabels,
            datasets: [{
                data: mediaTotals,
                backgroundColor: [
                '#F14D39',
                '#008080',
                '#E86800',
                '#6B0021'
                ],
                hoverBackgroundColor: [
                '#F14D39',
                '#008080',
                '#E86800',
                '#6B0021'
                ]
            }]
        };

        this.setState( { data: data }); 
    }

    render() {

        const data = this.state.data;
        
        return(

            <div>
                <div className="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pb-2 mb-3 border-bottom">
                    <h1 className="h2">Dashboard</h1>
                    <div className="btn-toolbar mb-2 mb-md-0">
                        <div className="btn-group mr-2">
                        <button className="btn btn-sm btn-outline-secondary">Share</button>
                        <button className="btn btn-sm btn-outline-secondary">Export</button>
                        </div>
                        <button className="btn btn-sm btn-outline-secondary dropdown-toggle">
                        <span data-feather="calendar"></span>
                        This week
                        </button>
                    </div>

                </div>

                <div>
                    <h4>Media Amount</h4>
                    <Pie data={data} height={60} width={400} />
                </div>
            </div>

        );
    }

}