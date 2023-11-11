import "./App.css";
import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";

import LoginSignup from "./compent/LoginSignup";
import Header from "./compent/Header";
import Footer from "./compent/Footer";
import About from "./compent/About.jsx";
import Drive from "./compent/Drive.jsx";

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" exact element={<LoginSignup />} />
        <Route path="/drive" exact element={<Drive />} />
        <Route path="/about" element={<About />} />
      </Routes>
    </Router>
  );
}

export default App;
