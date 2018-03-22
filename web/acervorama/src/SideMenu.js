import React, { Component } from 'react';

export default class SideMenu extends Component {

    constructor() {
        super();
        this.isActive.bind(this);
    }

    isActive(value) {
        return 'nav-link ' + (value === this.props.activeItem ? 'active' : '');
    }

    render() {
        return(
            <nav className="col-md-2 d-none d-md-block bg-light sidebar">
              <div className="sidebar-sticky">
                <ul className="nav flex-column">

                  <li className="nav-item">
                    <a className={this.isActive(0)} href="">
                      <span data-feather="dashboard"></span>
                      Dashboard { this.isActive(0) ? (<span className="sr-only">(current)</span>) : "" }
                    </a>
                  </li>    

                  <li className="nav-item">
                    <a className={this.isActive(1)} href="">
                      <span data-feather="Scan"></span>
                      Scan { this.isActive(1) ? (<span className="sr-only">(current)</span>) : "" }
                    </a>
                  </li>

                  <li className="nav-item">
                    <a className={this.isActive(2)} href="">
                      <span data-feather="management"></span>
                      Management { this.isActive(2) ? (<span className="sr-only">(current)</span>) : "" }
                    </a>
                  </li> 

                </ul>
              </div>
            </nav>
        );
    }

}