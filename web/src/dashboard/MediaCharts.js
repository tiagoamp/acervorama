import React, { Component } from 'react';
import { Pie } from 'react-chartjs-2';


export default class MediaCharts extends Component {

    constructor() {
        super();
        this.state = { data: {} };
    }

    componentDidMount() {
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
        return(

            <div>
                <h4>Media Amount</h4>
                <Pie data={this.state.data} height={60} width={400} />
            </div>
            
        );
    }

}