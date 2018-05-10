import React, { Component } from 'react';
import { Link } from 'react-router-dom';

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
                    <Link to='/' className={this.isActive(0)}>Dashboard</Link> { this.isActive(0) ? (<span className="sr-only">(current)</span>) : "" }
                  </li>    

                  <li className="nav-item">                    
                    <Link to='/scan' className={this.isActive(1)}>Scan</Link> { this.isActive(1) ? (<span className="sr-only">(current)</span>) : "" }
                  </li>

                  <li className="nav-item">                    
                    <Link to='/management' className={this.isActive(2)}>Management</Link> { this.isActive(2) ? (<span className="sr-only">(current)</span>) : "" }
                  </li> 

                </ul>
              </div>
            </nav>
        );
    }

}