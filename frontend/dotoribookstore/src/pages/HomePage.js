// HomePage.js
import React from 'react';
import './HomePage.css';
import './fadeinbox.css';

const HomePage = () => {
  return (
    <div>
      <div className='fade-in-box'>
        <h1>Welcome to the Home Page</h1>
        <p>This is the content of the home page.</p>
        {/* 추가적인 컴포넌트, 기능 등 */}
      </div>
      <div>
        <img src='/winter_forest.jpg' alt="winter_forest"/>
      </div>
    </div>
  );
};

export default HomePage;