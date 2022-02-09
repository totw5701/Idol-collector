import { BrowserRouter, Route, Switch } from 'react-router-dom';
import Nav from './components/Nav';
import TopBtn from './components/TopBtn';
import DetailPage from './pages/DetailPage';
import MainPage from './pages/MainPage';
import CreatePage from './pages/CreatePage';
import SettingPage from './pages/SettingPage';
import { useSelector, useDispatch } from 'react-redux';
import ApiService from './ApiService';
import { useState } from 'react';
import LoginPage from './pages/LoginPage';
import UserPage from './pages/UserPage';

function App() {
  const dispatch = useDispatch();
  const [isLogin, setIsLogin] = useState(false);

  /*  post */
  let data = useSelector(({ postReducer }) => {
    return postReducer;
  });
  // let data = useSelector((state)=> { return state.postReducer })

  /*   let axiosPost = [];

  ApiService.getHome().then(( result ) => {
    //console.log(result.data.data.member)
    data = [...axiosPost,...result.data.data.cards]
    //console.log(data)
    dispatch({ type:'axiosPost', payload: axiosPost })
  }).catch((err) => {
    console.log('axiosPost 데이터 가져오기 에러! '+err )
  }) */

  /* member */
  let axiosMember = {};

  ApiService.getHome()
    .then(result => {
      //console.log(result.data.data.member)
      axiosMember = { ...axiosMember, ...result.data.data.member };
      //console.log(axiosMember)
      dispatch({ type: 'axiosMember', payload: axiosMember });
    })
    .catch(err => {
      console.log('axiosMember 데이터 가져오기 에러! ' + err);
    });

  return (
    <BrowserRouter>
      {isLogin ? (
        <>
          <Nav isLogin={isLogin} setIsLogin={setIsLogin} />
          <Switch>
          <Route path="/user">
              <UserPage />
            </Route>
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
        </>
      ) : (
        <>
          <LoginPage isLogin={isLogin} setIsLogin={setIsLogin} />
        </>
      )}
    </BrowserRouter>
  );
}

export default App;
