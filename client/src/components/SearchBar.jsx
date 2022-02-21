import { useEffect, useState } from 'react';
import { useHistory } from 'react-router-dom';
import styled from 'styled-components';
import { css } from 'styled-components';
import ApiService from '../ApiService'

function SearchBar() {
  const [isFixed, setIsFixed] = useState(true);
  const [searchValue, setSearchValue] = useState();
  const [keywords, setKeywords] = useState([searchValue]);
  const history = useHistory()

  useEffect(() => {
    window.addEventListener('scroll', onScroll);

    return () => {
      window.removeEventListener('scroll', onScroll);
    };
  }, []);

  const onScroll = () => {
    // console.log(window.innerHeight);
    // console.log(window.scrollY);
    if (window.scrollY >= window.innerHeight * 0.1) {
      return setIsFixed(false);
    } else if (window.scrollY < window.innerHeight * 0.32) {
      return setIsFixed(true);
    }
  };

  const handleSearch = e => {
    e.preventDefault()
    console.log(searchValue)
    let keywords = [searchValue]
    if(searchValue != null && searchValue != ''){
      if(searchValue.includes(' ')){
        keywords = [searchValue,...searchValue.split(' ')]
        console.log(keywords)
      }
        history.push('/search/'+ keywords)

    }else{
      alert('검색어를 입력해주세요!')
    }
  };

  return (
    <StyledSearchBar
      onSubmit={ handleSearch }
      fixed={isFixed}>
      <SearchInput
        onChange={e => setSearchValue(e.target.value)}
        type="search"
        placeholder="원하는 순간을 검색해보세요"
      />
      <button type = 'onSubmit'>
        <SearchIcon src="/images/검색.png" />
      </button>
    </StyledSearchBar>
  );
}

export default SearchBar;

const bgColor = '#edebeb'
const borderColor = '#b580d1'

const StyledSearchBar = styled.form`
  position: fixed;
  top: 10px;
  left: 15%;
  margin: auto;

  width: 62%;
  padding: 4px 10px;

  display: flex;
  align-items: center;

  border: 2px solid ${ borderColor };
  border-radius: 50px;
  z-index: 2;

   svg {
     flex: 0.6;
     font-size: 40px;
   }

  @media screen and (max-width: 1200px) {
    width: 80%;
    top: 70px;
    left: 50%;
    transform: translateX(-50%);
    display: ${({fixed})=> !fixed && 'none'};
  }

/*
   @media screen and (max-width: 400px) {
     width: 80%;
     top: 60px;
     left: 50%;
     transform: translateX(-50%);
     display: flex;
     fixed랑 sticky 둘 다 쓰는 경우 scroll 위치 fixed여부 받아옴
     width: ${ props => props.fixed? '80%' : '62%' };
     top: ${ props => props.fixed? '60px' : '140px' };
*/

   }

/* 기본 sticky로 하다가 scroll시 fixed로 바꾸는 css

   ${({ fixed }) =>
     fixed &&
     css`
       position: fixed;
       top: 10px;
       left: 20%;
     `}

 */

`;

const SearchIcon = styled.img`
  width: 30px;
  height: 30px;

`;

const SearchInput = styled.input`
  flex: 9.4;
  background: none;
  border: none;
  font-size: 1.4rem;
  margin-left: 10px;

  &:focus {
    outline: none;
  }

  @media screen and (max-width: 930px) {
    margin-left: 5px;
    font-size: 16px;
  }

  @media screen and (max-width: 630px) {
    margin-left: 5px;
    font-size: 15px;
  }

`;
