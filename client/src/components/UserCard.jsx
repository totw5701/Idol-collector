import React from 'react';
import { useSelector } from 'react-redux';

const UserCard = () => {
    const bundle = useSelector(({ bundleReducer }) => {
        return bundleReducer;
      });

      console.log(bundle)
    return (
        <div>
            {bundle.map((el,idx) => (
                <img src={el} />
            ))}
        </div>
    );
};

export default UserCard;