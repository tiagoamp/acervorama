import React, { Component } from 'react';

import MediaCharts from './MediaCharts';
import MediaTables from './MediaTables';

import AcervoramaService from '../service/AcervoramaService';


export class DashboardBox extends Component {

    constructor() {
        super();  
        this._service = new AcervoramaService();
        this.state = { totalAudio: 0, totalVideo: 0, totalImage: 0, totalText: 0 };  
    }

    componentWillMount() {

        this._service.getMediaItems()
            .then(res => {
                const totalAudio = res.filter(item => item.resource.type === 'AUDIO').length;
                const totalVideo = res.filter(item => item.resource.type === 'VIDEO').length;
                const totalImage = res.filter(item => item.resource.type === 'IMAGE').length;
                const totalText = res.filter(item => item.resource.type === 'TEXT').length;
                this.setState({ totalAudio, totalVideo, totalImage, totalText});
                this._service.publishMessage('info-topic','Media Items data loaded \nat ' + new Date());
            })
            .catch(err => {
                console.log(err);
                this._service.publishMessage('error-topic','Error to access api service!');
            });
      
    }

    componentDidMount() {
        this._service.subscribeToTopic('error-topic');
        this._service.subscribeToTopic('info-topic');
    }

    showMediaCharts() {
        const noMediaWasRegistered = (this.state.totalAudio + this.state.totalVideo + this.state.totalImage + this.state.totalText) === 0;
        if (noMediaWasRegistered) return null;
        return (<MediaCharts totalAudio={this.state.totalAudio} totalVideo={this.state.totalVideo} 
                             totalImage={this.state.totalImage} totalText={this.state.totalText} />);
    }

    render() {
        

        return(
            
            <div>

                { this.showMediaCharts() }

                <MediaTables totalAudio={this.state.totalAudio} totalVideo={this.state.totalVideo} totalImage={this.state.totalImage} totalText={this.state.totalText} />

            </div>
        );
    }
}