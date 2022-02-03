import React from 'react';
import ReactDOM from 'react-dom';
import App from './App';
import { Provider } from 'react-redux';
import create from './redux/create';
import GlobalStyle from './common/GlobalStyle';

/*const store = createStore(()=>{ return dummyPost});*/

const store = create;

ReactDOM.render(
  <React.StrictMode>
    <Provider store = { store }>
      <GlobalStyle />
      <App />
    </Provider>
  </React.StrictMode>,
  document.getElementById('root'),
);
