// HomePage.js
import React from 'react';
import './HomePage.css';
import '../animation/fadeinbox.css';

const HomePage = () => {
  return (
    <div className='home-page'>

      <div className='fade-in-box' style={{ display: 'flex', justifyContent: 'start'}}>
        <h1>눈이 올까요</h1>
      </div>

      <div className='fade-in-box' style={{ animation : 'fadein 7s', display: 'flex', justifyContent: 'end' }}>
        <h3>우리 자는 동안에</h3>
      </div>

      {/* <div>
        <img src='/winter_forest.jpg' alt="winter_forest"/>
      </div> */}

    </div>
  );
};

export default HomePage;