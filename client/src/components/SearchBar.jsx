import { Search } from '@material-ui/icons';
import { useEffect, useState } from 'react';
import styled from 'styled-components';

function SearchBar() {
  const [positionY, setPositionY] = useState(false);

  const onScroll = () => {
    if (window.scrollY >= 380) {
      return setPositionY(true);
    } else if (window.scrollY < 380) {
      return setPositionY(false);
    }
  };

  useEffect(() => {
    window.addEventListener('scroll', onScroll);

    return () => {
      window.addEventListener('scroll', onScroll);
    };
  }, []);

  const fixed = `position: fixed;
      top: 10px;
      left: 15%;`;

  return (
    <StyledSearchBar fixed={positionY && fixed}>
      <Search />
      <SearchInput type="search" placeholder="원하는 순간을 검색해보세요" />
    </StyledSearchBar>
  );
}

export default SearchBar;

const StyledSearchBar = styled.div`
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
