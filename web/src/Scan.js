import React, { Component } from 'react';

import SideMenu from './SideMenu';
import Header from './commom/Header';

import './css/bootstrap.min.css';
import './css/pnotify.custom.min.css';


class Scan extends Component {

  constructor() {
    super();
    this.state = { activePanel: 1 };
  }
  
  render() {
    return (

      <div>
        
        <Header/> 

        <div className="container-fluid">
          <div className="row">

            <SideMenu activeItem={this.state.activePanel} />
            
            <main role="main" className="col-md-9 ml-sm-auto col-lg-10 pt-3 px-4">

              <h1>Test !!!!</h1>

            </main>

          </div>
        </div>
      </div>

    );
  }

}

export default Scan;
