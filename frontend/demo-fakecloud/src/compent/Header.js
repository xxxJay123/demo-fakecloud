import React from "react";
import "./Header.css";
import { Link } from "react-router-dom";


const Header = () => {
  return (
    <header>
      <h1>Fake Drive</h1>
      <nav>
        <Link to="/">Home</Link>
        <Link to="/about">About</Link>

      </nav>
    </header>
  );
};

export default Header;
