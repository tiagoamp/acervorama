import React, { Component } from 'react';
import '../css/bootstrap.min.css';

class Header extends Component {

  render() {
    return (

        <nav className="navbar navbar-dark sticky-top bg-dark flex-md-nowrap p-0">
          <a className="navbar-brand col-sm-3 col-md-2 mr-0" href="/">Acervorama</a>          
        </nav>     

    );
  }

}

export default Header;
