import React from 'react';
import { Link } from 'react-router-dom';
import styled from 'styled-components';
import { dummyDB } from '../common/dummyData';

const Columns = () => {
  // 메인 페이지 카드들을 나열하는 부분
  // map 으로 카드들을 나열하고, 각 카드들에는 링크를 걸어 각 카드의 상세페이지로 이동하게 한다.

  // data가 배열이 아니라면 null 을 리턴한다.
  if (!Array.isArray(dummyDB)) return null;

  return (
    <ColumnsContainer>
      {dummyDB.map(el => (
        <Card key={el.id}>
          <Link to={`/card/${el.id}`}>
            <img src={el.storeFileName} alt={el.title} />
          </Link>
        </Card>
      ))}
    </ColumnsContainer>
  );
}

export default Columns;

const ColumnsContainer = styled.ul`
  margin: auto;
  width: 90%;
  max-width: 2000px;
  column-width: 15em;
  column-gap: 1em;
`;

const Card = styled.li`
  display: inline-block;
  padding: 0.4rem;
  width: 100%;

  img {
    border-radius: 10px;
    width: 100%;
  }
`;
