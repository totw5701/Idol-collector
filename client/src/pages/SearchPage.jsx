import Columns from '../components/Columns';
import Jumbotron from '../components/Jumbotron';
import { useParams,useHistory } from 'react-router-dom';
import ApiService from '../ApiService'
import SearchBar from '../components/SearchBar'
import { useEffect, useState } from 'react'
import axios from 'axios'
import qs from 'qs'

function SearchPage() {

  const keywords = useParams()

  const param = { params: { keywords: keywords} }

  let [axiosSearch,setAxiosSearch] = useState()

    //console.log(keywords)

  useEffect(() => { // 이거 안해도 columns 에서 page별 axios해오자냐.. 필요할까? 얜 post처럼 영구적인 데이터도 아니고,,
    //axios.default.paramsSerializer = params => qs.stringify(params)

    ApiService.getSearch( param )
    .then((result) => {
      console.log('검색완료')
      setAxiosSearch(result.data.data.cards)
    })
    .catch((err) => {
      console.log('getSearch axios 에러! '+err )
    })

  },[keywords])

  return (
    <>
      <SearchBar />
      <Columns pageName = 'search' param = { param } />
    </>
  );
}

export default SearchPage;