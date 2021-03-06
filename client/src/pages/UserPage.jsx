import React, { useEffect, useState } from 'react';
import styled from 'styled-components';
import { useSelector, useDispatch } from 'react-redux';
import ApiService2 from '../ApiService2';
import UserCard from '../components/UserCard';
import UserScrap from '../components/UserScrap';


const UserPage = () => {

  const data = useSelector(({ memberReducer }) => {
    return memberReducer;
  });
  const card = useSelector(({ userCardReducer }) => {
    return userCardReducer;
  });
  const scrap = useSelector(({ scrapReducer }) => {
    return scrapReducer;
  });

  const [isUserInfo, setUserInfo] = useState(data[0]);
  const [isChange, setIsChange] = useState(false);

  const handleChangeCard = () => {
    setIsChange(true);
  };

  const handleChangeScrap = () => {
    setIsChange(false);
  };

  // console.log(ApiService2.getNotice())

  useEffect((id) => {
    ApiService2.getCardBundleInfo()
    .then((result) => {
      console.log(result)
    })
    .catch((err) => {
      console.log(err);
    })
  }, []);

  return (
    <>
      <UserPageContainer>
        <UserPageIn>
          <UserInfo>
            <img src="images/회원개인정보-사진.png" />
            <div className="userName">{isUserInfo.name}</div>
            <div className="userEmail">{isUserInfo.email}</div>
            <CardScrapTitle>
            <div className="userCardNum">카드 {card.length}개</div>
            <div className="userScrapNum">스크랩 {scrap.length}개</div>
            </CardScrapTitle>
          </UserInfo>
          <CardScrapMain>
          <ChangeBtn>
            <button className="userCardList" onClick={handleChangeCard}>Card</button>
            <button className="userScrapList" onClick={handleChangeScrap}>Scrap</button>
          </ChangeBtn>   
          {isChange ? (
            <>
              <CardList>
                <UserCard />
              </CardList>
            </>
          ) : (
            <>
              <ScrapList>
                <UserScrap />
              </ScrapList>
            </>
          )}
          </CardScrapMain>
        </UserPageIn>
      </UserPageContainer>
    </>
  );
};

export default UserPage;

const UserPageContainer = styled.section`
 width: 100%;
`;

const UserPageIn = styled.div`
    width: 100%;
    position: relative;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    text-align: center;
`;

const UserInfo = styled.div`
  img {
    width: 7.8rem;
    margin-top: 15px;
    margin-bottom: 10px;
  }
  .userName {
     font-size: 2.2em;
     margin-bottom: 10px;
  }
  .userEmail {
    color: #aeaeae;
    margin-bottom: 20px;
  }
`;

const CardScrapTitle = styled.div`
    position: relative;
    display: flex;
    flex-direction: row;
    justify-content: center;
    margin-bottom: 30px;
    .userCardNum {
        font-size: 1.3em;
        margin-right: 20px;
    }
    .userScrapNum {
        font-size: 1.3em;
    }
`

const CardScrapMain = styled.div`
    
`

const ChangeBtn = styled.div`
    position: relative;
    display: flex;
    flex-direction: row;
    justify-content: center;
    margin-bottom: 30px;

    .userCardList {
        font-size: 1.2em;
        margin-right: 20px;
        width: 80px;
        height: 30px;
        border: 1px solid black;
        border-radius: 5px;
        :hover {
            background-color: lightgray;
        }

    }
    .userScrapList {
        font-size: 1.2em;
        width: 80px;
        height: 30px;
        border: 1px solid black;
        border-radius: 5px;
        :hover {
            background-color: lightgray;
        }
    }
`;

const CardList = styled.div``;

const ScrapList = styled.div``;
