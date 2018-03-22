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
        <nav className="navbar navbar-dark sticky-top bg-dark flex-md-nowrap p-0">
          <a className="navbar-brand col-sm-3 col-md-2 mr-0" href="">Acervorama</a>
          <input className="form-control form-control-dark w-100" type="text" placeholder="Search" aria-label="Search" />
          <ul className="navbar-nav px-3">
            <li className="nav-item text-nowrap">
              <a className="nav-link" href="">Sign out</a>
            </li>
          </ul>
        </nav>

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
