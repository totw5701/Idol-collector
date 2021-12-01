import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import { Provider } from 'react-redux';
import create from './redux/create';

ReactDOM.render(
  <React.StrictMode>
    <Provider store={create()}>
      <App />
    </Provider>
  </React.StrictMode>,
  document.getElementById('root'),
);
