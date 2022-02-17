import React from 'react';
import styled from 'styled-components';
import googleLogo from '../images/googleLogo.jpg';
import KaKaoLogo from '../images/kakaoLogo.png';
import NaverLogo from '../images/NaverLogo.png';

const LoginPage = ({ isLogin, setIsLogin }) => {

    const goToMain = () => {
        setIsLogin(true);
    };

    const googleLoginHandle = () => {
      window.location = "http://ec2-52-79-146-243.ap-northeast-2.compute.amazonaws.com:8080/"
    }


  return (
    <>
      <LoginContainer>
        <LoginIn>
          <WelcomeText>
            <div className="mainText">You're my celebrity.</div>
            <div className="subText">Login</div>
          </WelcomeText>
          <LoginModal>
            <Logo src="/images/로고.png" alt="homepage logo" />
            <LoginInput>
                <GoogleLogin onClick={googleLoginHandle}>
                  <img className="googleLogo" src={googleLogo} />
                  <div className="googleLogin">Login with Google</div>
                </GoogleLogin>
                <NaverLogin onClick={goToMain}>
                  <img className="googleLogo" src={NaverLogo} />
                  <div className="googleLogin">Login with Naver</div>
                </NaverLogin>
                <KakaoLogin onClick={goToMain}>
                  <img className="googleLogo" src={KaKaoLogo} />
                  <div className="googleLogin">Login with Kakao</div>
                </KakaoLogin>
              <ForgotId>
                <div className="forgotId">Welcome to idol-collector</div>
              </ForgotId>
            </LoginInput>
          </LoginModal>
        </LoginIn>
      </LoginContainer>
    </>
  );
};

export default LoginPage;

const LoginContainer = styled.section`
  width: 100%;
`;

const LoginIn = styled.div`
  position: relative;
  display: flex;
  height: 100vh;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
  background-color: lightgray;
`;

const LoginModal = styled.div`
  position: absolute;
  display: flex;
  margin: 11vh 10vw 0 0;
  width: 450px;
  height: 78vh;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  text-align: center;
  top: 0px;
  right: 0px;
  background-color: white;
`;

const Logo = styled.img`
position: relative;
display: flex;
justify-content: left;
left: -40px;
  width: 200px;
  margin-bottom: 20px;
`;

const WelcomeText = styled.div`
    position: relative;
    display: flex;
    flex-direction: column;
    /* align-items: center; */
    margin-left: 10%;
    left: 0px;
    .mainText {
        font-size: 1.5em;
    }
    .subText {
        font-size: 3.5em;
    }
`;

const LoginInput = styled.div`
  position: relative;
  display: flex;
  flex-direction: column;
`;

const GoogleLogin = styled.div`
position: relative;
display: flex;
flex-direction: row;
align-items: center;
width: 300px;
height: 45px;
border-radius: 5px;
margin-top: 10px;
background-color: #4385f5;
:hover {
    cursor: pointer;
    background-color: #3c75d8;
}
  .googleLogo {
      background-color: white;
    width: 30px;
    border-radius: 10px;
    margin-left: 7px;
  }
  .googleLogin {
      margin-left: 10px;
      font-size: 1em;
      color: white;
      font-weight: lighter;
  }
`;

const NaverLogin = styled.div`
position: relative;
display: flex;
flex-direction: row;
align-items: center;
width: 300px;
height: 45px;
border-radius: 5px;
margin-top: 10px;
background-color: #03c75a;
:hover {
    cursor: pointer;
    background-color: #02a94d;
}
  .googleLogo {
      /* background-color: white; */
    width: 40px;
    border-radius: 10px;
    margin-left: 5px;
  }
  .googleLogin {
      margin-left: 5px;
      font-size: 1em;
      color: white;
      font-weight: lighter;
  }
`;

const KakaoLogin = styled.div`
position: relative;
display: flex;
flex-direction: row;
align-items: center;
width: 300px;
height: 45px;
border-radius: 5px;
margin-top: 10px;
background-color: #ffdc00;
:hover {
    cursor: pointer;
    background-color: #d7ba01;
}
  .googleLogo {
      /* background-color: white; */
    width: 26px;
    border-radius: 10px;
    margin-left: 11px;
  }
  .googleLogin {
      margin-left: 10px;
      font-size: 1em;
      color: black;
      font-weight: lighter;
  }
`;

const ForgotId = styled.div`
  position: relative;
  display: flex;
  flex-direction: row;
  justify-content: center;
  margin-top: 20px;
  .forgotId {
    color: gray;
    margin-right: 10px;
  }
  .forgotPath {
      color: #4385f5;
      :hover {
          cursor: pointer;
          text-decoration: underline;
      }
  }
`;
