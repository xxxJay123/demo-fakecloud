import React, { useState } from "react";
/* import { useNavigate, useLocation } from "react-router"; */
import "./LoginSignup.css";
import { FaGooglePlusG, FaFacebookF, FaGithub } from "react-icons/fa";
import axios from "axios";
import Cookies from "js-cookie";
import Header from "./Header";
import Footer from "./Footer";

const LoginSignup = () => {
  // const navigate = useNavigate();
  // const location = useLocation();
  // const from = location.state?.from?.pathname || "/linkpage";
  const [isSignIn, setIsSignIn] = useState(true);
  const [userName, setUserName] = useState("");
  const [userEmail, setUserEmail] = useState("");
  const [userPassword, setUserPassword] = useState("");
  const [error, setError] = useState("");

  const toggleForm = () => {
    setIsSignIn(!isSignIn);
  };

  const API = axios.create({
    baseURL: "http://localhost:8096/api/auth/",
    credentials: true,
    allowedHeaders: [
      "Content-Type",
      "Authorization",
      "Access-Control-Allow-Credentials",
    ],
  });

  const handleOnlySignInSubmit = async (e) => {
    e.preventDefault();
    if (!userName || !userPassword) {
      setError("Please fill in username/password");
      return;
    }

    try {
      const loginResponse = await API.post("/login", {
        userName,
        userPassword,
      });

      // Save the token in a cookie
      Cookies.set("accessToken", loginResponse.data.accessToken, {
        expires: 7,
      }); // Set the expiration time as needed
      console.log("accessToken:", loginResponse.data.accessToken);

      setUserName("");
      setUserPassword("");
      console.log("Correct submission");

      // Redirect the user to the user page (replace "/userpage" with your actual route)
      window.location.href = "/drive";
    } catch (error) {
      if (!error?.response) {
        setError("No server response");
      } else {
        setError("Login failed");
      }
    }
  };
  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!userName || !userEmail || !userPassword) {
      setError("Please fill in username/email/password");
      return;
    }

    try {
      // Attempt registration
      const registrationResponse = await API.post("/register", {
        userName,
        userPassword,
        userEmail,
      });
      setUserName("");
      setUserPassword("");
      setUserEmail("");
      console.log("Correct submission");
      toggleForm();
    } catch (err) {
      if (!err?.response) {
        setError("No server response");
      } else {
        setError("Registration failed");
      }
    }
  };
  return (
    <div>
      <Header />
      <div className="description">
        <h2>Welcome to Fake Drive</h2>
        <p>Your content goes here...</p>
      </div>
      <div className={`container ${isSignIn ? "" : "active"}`} id="container">
        <div className={`form-container ${isSignIn ? "sign-in" : "sign-up"}`}>
          <form method="post" onSubmit={handleSubmit}>
            <h1>{isSignIn ? "Sign In" : "Create Account"}</h1>
            <div className="social-icons">
              <a href="#" className="icon">
                <FaGooglePlusG />
              </a>
              <a href="#" className="icon">
                <FaFacebookF />
              </a>
              <a href="#" className="icon">
                <FaGithub />
              </a>
            </div>
            <span>
              {isSignIn
                ? "or use your username and password"
                : "or use your email for registration"}
            </span>
            {!isSignIn && (
              <input
                type="email"
                placeholder="Email"
                value={userEmail}
                name="useremail"
                onChange={(event) => setUserEmail(event.target.value)}
              />
            )}
            <input
              type="text"
              placeholder="Username"
              value={userName}
              name="username"
              onChange={(event) => setUserName(event.target.value)}
            />
            <input
              type="password"
              placeholder="Password"
              value={userPassword}
              name="password"
              onChange={(event) => setUserPassword(event.target.value)}
            />
            {!isSignIn && (
              <button type="submit" onClick={handleSubmit}>
                Sign Up
              </button>
            )}
            {isSignIn && (
              <>
                <a href="#">Forget Your Password?</a>
                <button type="submit" onClick={handleOnlySignInSubmit}>
                  Sign In
                </button>
              </>
            )}
          </form>
        </div>
        <div className="toggle-container">
          <div className="toggle">
            <div
              className={`toggle-panel ${
                isSignIn ? "toggle-left" : "toggle-right"
              }`}
            >
              <h1>{isSignIn ? "Welcome Back!" : "Hello, Friend!"}</h1>
              <p>
                {isSignIn
                  ? "Enter your personal details to use all site features"
                  : "Register with your personal details to use all site features"}
              </p>
              <button className="hidden" onClick={toggleForm}>
                {isSignIn ? "Sign In" : "Sign Up"}
              </button>
            </div>
            <div
              className={`toggle-panel ${
                isSignIn ? "toggle-right" : "toggle-left"
              }`}
            >
              <h1>{isSignIn ? "Hello, Friend!" : "Welcome Back!"}</h1>
              <p>
                {isSignIn
                  ? "Register with your personal details to use all site features"
                  : "Enter your personal details to use all site features"}
              </p>
              <button className="hidden" onClick={toggleForm}>
                {isSignIn ? "Sign Up" : "Sign In"}
              </button>
            </div>
          </div>
        </div>
      </div>
      <Footer />
    </div>
  );
};

export default LoginSignup;
