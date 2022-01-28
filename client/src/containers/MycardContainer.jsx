import React from 'react';
import MycardList from '../components/MycardList';
import UserInfo from '../components/UserInfo';

const MycardContainer = () => {
    return (
        <div>
            <UserInfo />
            <MycardList />
        </div>
    );
};

export default MycardContainer;