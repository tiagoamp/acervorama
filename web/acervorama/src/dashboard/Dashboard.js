import React, { Component } from 'react';
import $ from 'jquery';
import MediaCharts from './MediaCharts';
import MediaTables from './MediaTables';

export class Dashboard extends Component {

    constructor() {
        super();  
        this.state = { totalAudio: 0, totalVideo: 0, totalImage: 0, totalText: 0};  
    }

    componentWillMount() {
        $.ajax({
          url:"http://localhost:8080/media",
          dataType: 'json',
          success: function(response) {
            let totAud = 0, totVid = 0, totImg = 0, totTxt = 0;
            response.forEach(elt => {
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
          }.bind(this),
          error: function(response) {
            console.log('Error: ' + response);
          }
        });
    }

    render() {
        return(
            <div>
                 <MediaCharts />

                <canvas className="my-4" id="myChart" width="900" height="380"></canvas>

                <MediaTables totalAudio={this.state.totalAudio} totalVideo={this.state.totalVideo} totalImage={this.state.totalImage} totalText={this.state.totalText} />

            </div>
        );
    }
}