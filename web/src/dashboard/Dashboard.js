import React, { Component } from 'react';
import PubSub from 'pubsub-js';

import MediaCharts from './MediaCharts';
import MediaTables from './MediaTables';
import UIMessageDispatcher from '../UIMessageDispatcher';


export class DashboardBox extends Component {

    constructor() {
        super();  
        this.state = { totalAudio: 0, totalVideo: 0, totalImage: 0, totalText: 0 };  
    }

    componentWillMount() {

        fetch('http://localhost:8080/media')
            .then(response => response.json())
            .then( res => {
                let totAud = 0, totVid = 0, totImg = 0, totTxt = 0;
                res.forEach(elt => {
                if (elt.resource.type === "AUDIO") {
                    totAud++;
                } else if (elt.resource.type === "VIDEO") {
                    totVid++;
                } else if (elt.resource.type === "IMAGE") {
                    totImg++;
                } else if (elt.resource.type === "TEXT") {
                    totTxt++;
                }          
                });
                this.setState({ totalAudio: totAud, totalVideo: totVid, totalImage: totImg, totalText: totTxt});
                PubSub.publish('info-topic','Media Items data loaded \nat ' + new Date());  
            })
            .catch( err => {
                console.log(err);
                PubSub.publish('error-topic','Error to access api service!');
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