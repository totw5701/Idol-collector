import { BrowserRouter, Route, Switch } from 'react-router-dom';
import Jumbotron from './components/Jumbotron';
import Nav from './components/Nav';
import DetailPage from './pages/DetailPage';

function App() {
  return (
    <BrowserRouter>
      <Nav />
      <Switch>
        <Route path="/card/:cardId">
          <DetailPage />
        </Route>
        <Route path="/" exact>
          <Jumbotron />
        </Route>
      </Switch>
    </BrowserRouter>
  );
}

export default App;
