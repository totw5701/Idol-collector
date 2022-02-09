import { ArrowDropDown, ArrowDropUp } from '@mui/icons-material'
import { useState } from 'react'
import { Link } from 'react-router-dom'
import { useHistory } from 'react-router'
import styled from 'styled-components/macro'
import Button from './Button'

function Nav({ isLogin, setIsLogin }) {
  const history = useHistory();
  const [dropdown, setDropdown] = useState(false);

  const backToLogin = () => {
    setIsLogin(false);
    history.push('/');
  };

  return (
    <>
      <Navbar>
        <Link to="/">
          <Logo src="/images/로고.png" alt="homepage logo" />
        </Link>
        <LoginRight>
          <LoginMakeCard>
            <Link to="/create">
              <Button src={'카드만들기.png'}>카드만들기</Button>
            </Link>
          </LoginMakeCard>

          <LoginNickname onClick={() => setDropdown(!dropdown)}>
            <Button src={'닉네임.png'}>닉네임</Button>
            {dropdown ? <ArrowDropUp /> : <ArrowDropDown />}
          </LoginNickname>

          {dropdown && (
            <DropdownBar>
              <li>
                <Link to="/user">
                <Button src={'나의카드.png'}>나의 카드</Button>
                </Link>
              </li>
              <li>
                <Link to="/setting">
                  <Button src={'설정.png'}>설정</Button>
                </Link>
              </li>
              <li onClick={backToLogin}>
                <Button 
                src={'로그아웃.png'}     
                >로그아웃</Button>
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
  background: #fff;
  z-index: 5;
`;

const Logo = styled.img`
  cursor: pointer;
  width: 100px;
`;

const LoginRight = styled.div`
  display: flex;
  align-items: center;
`;

const LoginMakeCard = styled.div`
  cursor: pointer;
  margin-right: 1rem;

  img {
    height: 30px;
  }
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
  height: 70px;
`;
