import Columns from '../components/Columns';
import Jumbotron from '../components/Jumbotron';
import { useParams,useHistory } from 'react-router-dom';
import ApiService from '../ApiService'
import SearchBar from '../components/SearchBar'
import { useEffect, useState } from 'react'
import axios from 'axios'
import qs from 'qs'
import styled from 'styled-components';
import { css } from 'styled-components';

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
      console.log('getSearch axios 에러! '+err );
      console.log(keywords);
    })

  },[keywords])

  return (
    <>
      <StyledBanner>
        <Banner>'{keywords.keywords}'로 검색한 결과입니다</Banner>
        <SearchBar />
      </StyledBanner>
      <Line/>
      <Columns pageName = 'search' param = { param } />
    </>
  );
}

export default SearchPage;

const BannerColor = '#b580d1';
const LineColor = '#b580d1';

const StyledBanner = styled.div`
  margin: 0 auto 20px auto;
  padding-top: 65px;
`;

const Banner = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
  height: 100%;
  margin: 20px auto 50px auto;
  font-size: 30px;
  font-weight: 700;
  color: ${ BannerColor };
`;

const Line = styled.div`
  width: 90%;
  margin: 0 auto 20px auto;
  height: 4px;
  background-color: ${ LineColor };
`;