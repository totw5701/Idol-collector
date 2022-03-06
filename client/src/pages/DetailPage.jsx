import DetailContainer from '../containers/DetailContainer';
import SimilarContainer from '../containers/SimilarContainer';
import { useParams } from 'react-router-dom';
import { useSelector } from 'react-redux';
import ApiService from '../ApiService'

function DetailPage() {

  return (
    <>
      <DetailContainer />
      <SimilarContainer />
    </>
  );
}

export default DetailPage;
