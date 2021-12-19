import { useParams } from 'react-router-dom';
import DetailContainer from '../containers/DetailContainer';

function DetailPage() {
  const { cardId } = useParams();

  return <DetailContainer />;
}

export default DetailPage;
