import styled from 'styled-components';

function SettingContainer() {
  return (
    <SettingWrap>
      <SettingLeft></SettingLeft>
      <CenterLine />
      <SettingRight></SettingRight>
    </SettingWrap>
  );
}

export default SettingContainer;

const SettingWrap = styled.div`
  width: 90%;
  max-width: 1440px;
  height: 70vh;
  margin: 3.5rem auto;

  display: flex;

  border-top: none;
  border-left: none;
  border-radius: 30px;
  box-shadow: 5px 5px 5px 0px rgba(0, 0, 0, 0.2);
`;

const SettingLeft = styled.div`
  background: lightgrey;
`;

const CenterLine = styled.div`
  width: 2px;
  height: 100%;
  background: #b580d1;
  margin: auto;
`;

const SettingRight = styled.div`
  background: lightyellow;
`;
