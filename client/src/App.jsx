import { BrowserRouter, Route, Switch } from 'react-router-dom';
import Nav from './components/Nav';
import DetailPage from './pages/DetailPage';
import MainPage from './pages/MainPage';

function App() {
  return (
    <BrowserRouter>
      <Nav />
      <Switch>
        <Route path="/card/:cardId">
          <DetailPage />
        </Route>
        <Route path="/" exact>
          <MainPage />
        </Route>
      </Switch>
    </BrowserRouter>
  );
}

export default App;