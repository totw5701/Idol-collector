import { ArrowDropDown } from '@material-ui/icons';
import { useState } from 'react';
import styled from 'styled-components/macro';

function Nav() {
  const [dropdown, setDropdown] = useState(false);

  return (
    <Navbar>
      <Logo>LOGO</Logo>
      <LoginRight>
        <LoginMakeCard>카드만들기</LoginMakeCard>

        <LoginNickname onClick={() => setDropdown(!dropdown)}>
          닉네임
          <ArrowDropDown />
        </LoginNickname>

        {dropdown && (
          <DropdownBar>
            <li>나의 카드</li>
            <li>설정</li>
            <li>로그아웃</li>
          </DropdownBar>
        )}
      </LoginRight>
    </Navbar>
  );
}

export default Nav;

const Navbar = styled.nav`
  width: 100%;
  position: fixed;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1.5rem 2.5rem;

  // 수정할 부분
`;

// a tag 및 내부 img 태그로 변경될 예정
const Logo = styled.h1`
  cursor: pointer;
  font-size: 2rem;
`;

const LoginRight = styled.div`
  display: flex;
`;

const LoginMakeCard = styled.div`
  cursor: pointer;
  margin-right: 1rem;
`;

const LoginNickname = styled.div`
  // 화살표는 material-ui로 바꿀 예정
  cursor: pointer;
`;

const DropdownBar = styled.ul`
  position: absolute;
  right: 2rem;
  top: 4rem;
  padding: 0.4rem 1rem;

  border-radius: 5px;
  -webkit-box-shadow: 0px 5px 10px 1px rgba(0, 0, 0, 0.5);
  box-shadow: 0px 5px 10px 1px rgba(0, 0, 0, 0.5);

  li {
    cursor: pointer;
    margin: 1rem 0;
  }
`;

// theme 사용하여 button style을 변수로 만들어 설정할 예정
