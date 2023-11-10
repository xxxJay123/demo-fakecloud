import "./App.css";
import React, { useState } from "react";
import LoginSignup from "./compent/LoginSignup";
import Header from "./compent/Header";
import Footer from "./compent/Footer";

function App() {
  return (
    <div className="hearder">
      <Header />
      <div className="description">
        <h2>Welcome to Fake Drive</h2>
        <p>Your content goes here...</p>
      </div>
      <LoginSignup />

      <Footer />
    </div>
  );
}

export default App;
