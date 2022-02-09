import { BrowserRouter, Route, Switch } from 'react-router-dom';
import Nav from './components/Nav';
import TopBtn from './components/TopBtn';
import DetailPage from './pages/DetailPage';
import MainPage from './pages/MainPage';
import CreatePage from './pages/CreatePage';
import SettingPage from './pages/SettingPage';
import SearchPage from './pages/SearchPage';
import { useSelector,useDispatch } from 'react-redux';
import ApiService from './ApiService'

function App() {

  const dispatch = useDispatch()

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

/* member */
  let axiosMember = {}

  ApiService.getHome().then(( result ) => {
    //console.log(result.data.data.member)
    axiosMember = {...axiosMember,...result.data.data.member}
    //console.log(axiosMember)
    dispatch({ type:'axiosMember', payload: axiosMember })
  }).catch((err) => {
    console.log('axiosMember 데이터 가져오기 에러! '+err )

  })






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
        <Route path="/search/:keywords">
          <SearchPage />
        </Route>
      </Switch>
      <TopBtn />
    </BrowserRouter>
  );
}

export default App;
