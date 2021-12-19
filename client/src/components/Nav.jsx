import { ArrowDropDown, ArrowDropUp } from '@material-ui/icons';
import { useState } from 'react';
import styled from 'styled-components/macro';
import Button from './Button';

function Nav() {
  const [dropdown, setDropdown] = useState(false);

  return (
    <>
      <Navbar>
        <Logo>LOGO</Logo>
        <LoginRight>
          <LoginMakeCard>
            <Button>카드만들기</Button>
          </LoginMakeCard>

          <LoginNickname onClick={() => setDropdown(!dropdown)}>
            <Button>닉네임</Button>
            {dropdown ? <ArrowDropUp /> : <ArrowDropDown />}
          </LoginNickname>

          {dropdown && (
            <DropdownBar>
              <li>
                <Button>나의 카드</Button>
              </li>
              <li>
                <Button>설정</Button>
              </li>
              <li>
                <Button>로그아웃</Button>
              </li>
            </DropdownBar>
          )}
        </LoginRight>
      </Navbar>
      <Space />
    </>
  );
}

export default Nav;

const Navbar = styled.nav`
  width: 100%;
  position: fixed;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 15px 30px;
`;

// a tag 및 내부 img 태그로 변경될 예정
const Logo = styled.h1`
  cursor: pointer;
  font-size: 2rem;
`;

const LoginRight = styled.div`
  display: flex;
  align-items: center;
`;

const LoginMakeCard = styled.div`
  cursor: pointer;
  margin-right: 1rem;
`;

const LoginNickname = styled.div`
  position: relative;
  cursor: pointer;
  line-height: 35px;

  svg {
    position: absolute;
    right: -10px;
    top: 3px;
  }
`;

const DropdownBar = styled.ul`
  position: absolute;
  right: 2rem;
  top: 4rem;
  padding: 0.4rem 1rem;

  background: #fff;
  border-radius: 5px;
  -webkit-box-shadow: 0px 5px 10px 1px rgba(0, 0, 0, 0.5);
  box-shadow: 0px 5px 10px 1px rgba(0, 0, 0, 0.5);

  li {
    margin: 1rem 0;
  }
`;

const Space = styled.div`
  height: 65px;
`;
