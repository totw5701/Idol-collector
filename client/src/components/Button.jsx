import React from 'react';
import styled from 'styled-components';

const Button = ({ children, src }) => {
  // nav바 상단 카드만들기, 닉네임 부분 

  return (
    <StyledButton>
      {src && <IconButton src={`/images/${src}`} />} 
      {children} 
    </StyledButton>
  );
}

export default Button;

const StyledButton = styled.button`
  padding: 5px 10px;
  border-radius: 8px;

  display: flex;
  align-items: center;

  font-size: 1.1rem;

  &:hover {
    background: rgb(240, 240, 240);
  }
`;

const IconButton = styled.img`
  height: 30px;
  width: 30px;
`;
