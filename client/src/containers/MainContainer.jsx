import { useEffect } from 'react';
import Axios from 'axios';
import styled from 'styled-components';
import Jumbotron from '../components/Jumbotron';

function MainContainer({ data }) {
  useEffect(() => {
    Axios.get('http://localhost:8080/api/home/')
      .then(response => {
        console.log(response.data);
      })
      .catch(() => {
        alert('ERROR OCCURRED');
      });
  }, []);

  console.log(data);
  return (
    <>
      <Jumbotron />
      <MainArea>
        <CardContainer>
          {data.map(card => {
            return (
              <Card key={card.id}>
                <img src={card.storeFileName} alt={card.title} />
              </Card>
            );
          })}
        </CardContainer>
      </MainArea>
    </>
  );
}

export default MainContainer;

const MainArea = styled.main`
  margin: auto;
  width: 90%;
  max-width: 2000px;
`;

const CardContainer = styled.div`
  column-width: 15em;
  column-gap: 1em;
`;

const Card = styled.div`
  display: inline-block;
  padding: 0.4rem;
  width: 100%;

  &:hover {
    cursor: pointer;
  }

  img {
    border-radius: 10px;
    width: 100%;
  }
`;
