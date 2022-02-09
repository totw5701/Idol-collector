import React from 'react';
import styled from 'styled-components';
import googleLoge from '../images/googleLogo.jpg';

const LoginPage = ({ isLogin, setIsLogin }) => {

    const goToMain = () => {
        setIsLogin(true);
    };

    const googleLoginHandle = () => {
      window.location = "http://ec2-52-79-146-243.ap-northeast-2.compute.amazonaws.com:8080/oauth2/authorization/google"
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
                  <img className="googleLogo" src={googleLoge} />
                  <div className="googleLogin">Login with Google</div>
                </GoogleLogin>
                <InputIdPw>
                  <div className="or">or</div>
                  <input
                    className="id"
                    type="text"
                    placeholder="USER ID"
                  ></input>
                  <input
                    className="pw"
                    type="password"
                    placeholder="USER PASSWORD"
                  ></input>
                  <button 
                  className="submit" 
                  type="submit"
                  onClick={goToMain}
                  >
                    클릭하면 Main으로 진입 (임시)
                  </button>
                </InputIdPw>
              <ForgotId>
                <div className="forgotId">Forgot your id?</div>
                <div className="forgotPath">Click Here</div>
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
border-radius: 10px;
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

const InputIdPw = styled.div`
  position: relative;
  display: flex;
  flex-direction: column;
  .or {
      margin: 10px 0px 10px 0px;
      color: gray;
  }
  .id {
    width: auto;
    height: 35px;
    border-radius: 7px;
    border: 1px solid gray;
    margin-bottom: 7px;
    ::placeholder {
        align-items: center;
        padding-left: 6px;
    }
  }
  .pw {
    width: auto;
    height: 35px;
    border-radius: 7px;
    border: 1px solid gray;
    ::placeholder {
        align-items: center;
        padding-left: 6px;
    }
  }
  .submit {
      height: 35px;
      margin-top: 10px;
      border-radius: 10px;
      background-color: #b580d1;
      font-size: 1em;
      font-weight: bold;
      color: white;
      :hover {
        background-color: #9d6eb6;
      }
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
