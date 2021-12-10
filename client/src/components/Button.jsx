import styled from 'styled-components';

function Button({ children }) {
  return <StyledButton>{children}</StyledButton>;
}

export default Button;

const StyledButton = styled.button`
  cursor: pointer;
  border: none;
  background: transparent;

  font-size: 1.1rem;

  &:hover {
  }
`;
