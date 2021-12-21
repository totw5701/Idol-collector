import { useEffect, useState } from 'react';
import styled from 'styled-components';
import { css } from 'styled-components';

function SearchBar() {
  const [isFixed, setIsFixed] = useState(false);
  const [searchValue, setSearchValue] = useState();

  useEffect(() => {
    window.addEventListener('scroll', onScroll);

    return () => {
      window.removeEventListener('scroll', onScroll);
    };
  }, []);

  const onScroll = () => {
    // console.log(window.innerHeight);
    // console.log(window.scrollY);
    if (window.scrollY >= window.innerHeight * 0.32) {
      return setIsFixed(true);
    } else if (window.scrollY < window.innerHeight * 0.32) {
      return setIsFixed(false);
    }
  };

  const handleSearch = e => {
    e.preventDefault();
    console.log(searchValue);
  };

  return (
    <StyledSearchBar onSubmit={handleSearch} fixed={isFixed}>
      <SearchIcon src="./images/검색.png" />
      <SearchInput
        onChange={e => setSearchValue(e.target.value)}
        type="search"
        placeholder="원하는 순간을 검색해보세요"
      />
    </StyledSearchBar>
  );
}

export default SearchBar;

const StyledSearchBar = styled.form`
  position: sticky;
  ${({ fixed }) =>
    fixed &&
    css`
      position: fixed;
      top: 10px;
      left: 15%;
    `}

  margin: auto;

  width: 62%;
  padding: 4px 10px;

  display: flex;
  align-items: center;

  background: #e1e1e1;
  border: 2px solid #b580d1;
  border-radius: 50px;
  z-index: 5;

  svg {
    flex: 0.6;
    font-size: 40px;
  }
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
`;
