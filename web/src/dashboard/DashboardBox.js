import React, { Component } from 'react';
import PubSub from 'pubsub-js';

import MediaCharts from './MediaCharts';
import MediaTables from './MediaTables';
import UIMessageDispatcher from '../UIMessageDispatcher';
import AcervoramaService from '../service/AcervoramaService';


export class DashboardBox extends Component {

    constructor() {
        super();  
        this.state = { totalAudio: 0, totalVideo: 0, totalImage: 0, totalText: 0 };  
    }

    componentWillMount() {

        const service = new AcervoramaService();
        service.getMediaItems()
            .then(res => {
                const totalAudio = res.filter(item => item.resource.type === 'AUDIO').length;
                const totalVideo = res.filter(item => item.resource.type === 'VIDEO').length;
                const totalImage = res.filter(item => item.resource.type === 'IMAGE').length;
                const totalText = res.filter(item => item.resource.type === 'TEXT').length;
                this.setState({ totalAudio, totalVideo, totalImage, totalText});
                PubSub.publish('info-topic','Media Items data loaded \nat ' + new Date());  
            })
            .catch(err => {
                console.log(err);
                PubSub.publish('error-topic','Error to access api service!')
            });
      
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

                {this.state.totalAudio > 0 ? (
                    <MediaCharts totalAudio={this.state.totalAudio} totalVideo={this.state.totalVideo} totalImage={this.state.totalImage} totalText={this.state.totalText} />
                ) : (
                    'Loading data...'
                )}

                <MediaTables totalAudio={this.state.totalAudio} totalVideo={this.state.totalVideo} totalImage={this.state.totalImage} totalText={this.state.totalText} />

            </div>
        );
    }
}