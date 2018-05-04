import React, { Component } from 'react';

import SideMenu from './SideMenu';
import { DashboardBox } from './dashboard/Dashboard';

import './css/bootstrap.min.css';
import './css/dashboard.css';
import './css/pnotify.custom.min.css';


class App extends Component {

  constructor() {
    super();
    this.state = { activePanel: 0 };
  }
  
  render() {
    return (

      <div>
        
        <div className="container-fluid">
          <div className="row">

            <SideMenu activeItem={this.state.activePanel} />
            
            <main role="main" className="col-md-9 ml-sm-auto col-lg-10 pt-3 px-4">

              <DashboardBox />

            </main>

          </div>
        </div>
      </div>

    );
  }

}

export default App;
