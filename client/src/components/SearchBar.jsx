import { Search } from '@material-ui/icons';
import { useEffect, useState } from 'react';
import styled from 'styled-components';

function SearchBar() {
  const [isFixed, setIsFixed] = useState(false);
  const [searchValue, setSearchValue] = useState();

  const fixed = `position: fixed; top: 10px; left: 15%;`;

  useEffect(() => {
    window.addEventListener('scroll', onScroll);

    return () => {
      window.removeEventListener('scroll', onScroll);
    };
  }, []);

  const onScroll = () => {
    if (window.scrollY >= 380) {
      return setIsFixed(true);
    } else if (window.scrollY < 380) {
      return setIsFixed(false);
    }
  };

  const handleSearch = e => {
    e.preventDefault();
    console.log(searchValue);
  };

  return (
    <>
      <StyledSearchBar onSubmit={handleSearch} fixed={isFixed && fixed}>
        <Search />
        <SearchInput
          onChange={e => setSearchValue(e.target.value)}
          type="search"
          placeholder="원하는 순간을 검색해보세요"
        />
      </StyledSearchBar>
    </>
  );
}

export default SearchBar;

const StyledSearchBar = styled.form`
  ${({ fixed }) => fixed}

  margin: auto;

  width: 68%;
  padding: 4px 10px;

  display: flex;
  align-items: center;

  background: #e1e1e1;
  border: 1.5px solid #000;
  border-radius: 50px;

  svg {
    flex: 0.6;
    font-size: 40px;
  }
`;

const SearchInput = styled.input`
  flex: 9.4;
  background: none;
  border: none;
  font-size: 1.4rem;

  &:focus {
    outline: none;
  }
`;
