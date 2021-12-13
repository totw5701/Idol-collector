import styled from 'styled-components';

function Button({ children }) {
  return <StyledButton>{children}</StyledButton>;
}

export default Button;

const StyledButton = styled.button`
  cursor: pointer;
  border: none;
  background: transparent;
  padding: 5px 10px;
  border-radius: 8px;

  font-size: 1.1rem;

  &:hover {
    background: rgb(240, 240, 240);
  }
`;
