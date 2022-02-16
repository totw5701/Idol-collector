import MainPage from './pages/MainPage';
import { BrowserRouter, Route, Switch } from 'react-router-dom';
import Nav from './components/Nav';
import TopBtn from './components/TopBtn';
import DetailPage from './pages/DetailPage';
import CreatePage from './pages/CreatePage';
import SettingPage from './pages/SettingPage';
import SearchPage from './pages/SearchPage';
import LoginPage from './pages/LoginPage';
import UserPage from './pages/UserPage';
import { useSelector,useDispatch } from 'react-redux';
import ApiService from './ApiService'
import { useState, useEffect } from 'react';
import { getMember } from './redux/modules/actions'

function App() {

  const dispatch = useDispatch()
  const [isLogin, setIsLogin] = useState(false);


/*  post */
   let data = useSelector(({postReducer})=> { return postReducer })
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

/* member
  let axiosMember = {}

  ApiService.getHome().then(( result ) => {
    //console.log(result.data.data.member)
    axiosMember = {...axiosMember,...result.data.data.member}
    //console.log(axiosMember)
    dispatch({ type:'axiosMember', payload: axiosMember })
  }).catch((err) => {
    console.log('axiosMember 데이터 가져오기 에러! '+err )

  })
*/
/* 로그인 시 회원 정보 dispatch */
  useEffect(()=>{
    getMember().then((result) => {
      dispatch(result)
    })
  },[isLogin])



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
            <Route path="/search/:keywords">
              <SearchPage />
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

export default App