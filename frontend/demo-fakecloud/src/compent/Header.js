import React from 'react';
import './Header.css';

const Header = () => {
  return (
    <header>
      <h1>Fake Drive</h1>
      <nav>
        <a href="/">Home</a>
        <a href="/about">About</a>
        <a href="/files">Files</a>
        <a href="/shared">Shared</a>
      </nav>
    </header>
  );
};

export default Header;