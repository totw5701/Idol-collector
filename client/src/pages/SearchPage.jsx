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

  let [axiosSearch,setAxiosSearch] = useState()

    console.log(keywords)



  useEffect(() => {
    //axios.default.paramsSerializer = params => qs.stringify(params)

    ApiService.getSearch( { params: { keywords: keywords} } )
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
      <Columns data = { axiosSearch } pageName = 'main' />
    </>
  );
}

export default SearchPage;