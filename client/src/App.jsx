import React, { useState } from 'react';
import { BrowserRouter, Route, Switch } from 'react-router-dom';
import Nav from './components/Nav';
import TopBtn from './components/TopBtn';
import DetailPage from './pages/DetailPage';
import MainPage from './pages/MainPage';
import CreatePage from './pages/CreatePage';
import SettingPage from './pages/SettingPage';
import MycardPage from './pages/MycardPage';

function App() {

  const [isLogin, setIsLogin] = useState(false);

  return (
    <BrowserRouter>
      <Nav 
      isLogin={isLogin}
      setIsLogin={setIsLogin}/>
      <Switch>
        <Route path="/setting">
          <SettingPage />
        </Route>
        <Route path="/mycard">
          <MycardPage />
        </Route>
        <Route path="/create">
          <CreatePage />
        </Route>
        <Route path="/card/:cardId">
          <DetailPage />
        </Route>
        <Route path="/" exact>
          <MainPage />
        </Route>
      </Switch>
      <TopBtn />
    </BrowserRouter>
  );
}

export default App;
