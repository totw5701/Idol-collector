import { Link } from 'react-router-dom';
import styled from 'styled-components';

function Columns({ data }) {
  if (!Array.isArray(data)) return null;

  return (
    <ColumnsContainer>
      {data.map(el => (
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
