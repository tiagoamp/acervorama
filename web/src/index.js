import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import registerServiceWorker from './registerServiceWorker';

import { BrowserRouter, Switch, Route } from 'react-router-dom';

import App from './App';
import Scan from './Scan';
import Management from './Management';

ReactDOM.render(

    (
        <BrowserRouter>
            <Switch>
                <Route exact path='/' component={App}/>
                <Route path='/scan' component={Scan}/>
                <Route path='/management' component={Management}/>
            </Switch>
        </BrowserRouter>        
    ),

    document.getElementById('root'));
registerServiceWorker();
