import { BrowserRouter, Route, Switch } from 'react-router-dom';
import Nav from './components/Nav';
import TopBtn from './components/TopBtn';
import DetailPage from './pages/DetailPage';
import MainPage from './pages/MainPage';
import CreatePage from './pages/CreatePage';
import SettingPage from './pages/SettingPage';
import { useSelector } from 'react-redux';


function App() {

 let data = useSelector(({postReducer})=> { return postReducer })
 /*  let data = useSelector((state)=> { return state.postReducer }) */

  return (
    <BrowserRouter>
      <Nav />
      <Switch>
        <Route path="/setting">
          <SettingPage />
        </Route>
        <Route path="/create">
          <CreatePage />
        </Route>
        <Route path="/card/:cardId">
          <DetailPage />
        </Route>
        <Route path="/" exact>
          <MainPage data={data} />
        </Route>
      </Switch>
      <TopBtn />
    </BrowserRouter>
  );
}

export default App;
