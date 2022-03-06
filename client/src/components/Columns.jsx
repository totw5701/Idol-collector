import React, { useState, useEffect, useCallback } from 'react'
import { Link } from 'react-router-dom'
import styled from 'styled-components'
import { useInView } from 'react-intersection-observer'
import ApiService from '../ApiService'
import { useSelector } from 'react-redux'

function Columns( props ) {

  const data = useSelector(({postReducer}) => postReducer )

  const [card, setCard] = useState([...data])//postReducer로 받은 데이터가 나오도록
  const [page, setPage] = useState( 0 ) //PageRequest page  0은 그냥 api/home
  const [loading,setLoading] = useState( false )

  const [last,inView] = useInView()//ref속성값으로 last 들어가면 inView가 true로 변함

  //useEffect(()=>{ console.log(card) },[card])
/*   api/home/page: 페이지가 바뀔 때마다 해당 page data받기 */
  const getData = useCallback(() => {
    setLoading(true)

    if( props.pageName === 'main' ){ // 메인페이지
      ApiService.getHomePage(page)
      .then((result) => {
        //console.log(result)
        setCard([...card,...result.data.data.cards])
        setLoading(false)
      })
      .catch((err) => {
        console.log('getHomePage axios 에러! '+err )
        setCard(data)
        setLoading(false)
      })

    }else if( props.pageName === 'search' ){ // search 페이지

      setCard([...props.axiosSearch])
      ApiService.getSearchPage(page, props.param )
      .then((result) => {
        //console.log(result)
        setCard([...props.axiosSearch,...result.data.data.cards])
        setLoading(false);
      })
      .catch((err) => {
        console.log('getSearchPage axios 에러! '+err )
        setLoading(false)
      })

    }
  },[page])

  // getData가 바뀔 때마다 실행
  useEffect(() => {
    getData()
    //console.log('카드 개수'+card.length)
  },[getData])

  // client가 마지막을 보고있고 로딩중이 아닐경우 page를 더해줌
  useEffect(() => {
    if( inView && !loading ) {
      setPage(page+1)
    }
  }, [inView,loading])

  if (!Array.isArray(card)) return null

  return (
   <ColumnsContainer>
      { loading === true
        ? <span>Loading...</span>
        : null
      }
      {card.map((el,idx) => (
        <Card key={el.id}>
          { card.length -1 === idx
            ? (
                <Link to={`/card/${el.id}`} ref={ last } >
                  <img src={el.storeFileName} alt={el.title} />
                </Link>

              )
            : (
                <Link to={`/card/${el.id}`}>
                  <img src={el.storeFileName} alt={el.title} />
                </Link>
              )

          }

        </Card>
      ))}
    </ColumnsContainer>
  );
}

export default Columns;

const ColumnsContainer = styled.ul`
  margin: auto;
  width: 90%;
  max-width: 2000px;
  column-width: 15em;
  column-gap: 1em;
`;

const Card = styled.li`
  display: inline-block;
  padding: 0.4rem;
  width: 100%;
  border-radius: 10px;

  img {
    border-radius: 10px;
    width: 100%;
  }

  &:hover {
    padding: 0;
    box-shadow: 4px 4px 10px grey;
    img {
      display: block;
    }
  }

`;
