import React, { useState ,useEffect } from "react";
import axios from "axios";
import Cookies from "js-cookie";
import "./Drive.css";
import Header from "./Header";
import Footer from "./Footer";
import { MdCloudUpload, MdDelete } from "react-icons/md";
import { AiFillFileImage } from "react-icons/ai";

const Drive = () => {
  const [image, setImage] = useState(null);
  const [fileName, setFileName] = useState("No selected file");
  const [token, setToken] = useState(null);
  const [success, setSuccess] = useState(false); // New state for success
  const [successMessage, setSuccessMessage] = useState(""); 

  useEffect(() => {
    // Retrieve token from cookies when the component mounts
    const accessToken = Cookies.get("accessToken");
    if (accessToken) {
      setToken(accessToken);
    }
  }, []);

  const API = axios.create({
    baseURL: "http://localhost:8096/api/files",
    credentials: true,
    allowedHeaders: [
      "Content-Type",
      "Authorization",
      "Access-Control-Allow-Credentials",
    ],
  });


  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!image) {
      setError("Please select the file");
      return;
    }
    try {
      // Get the file input element
      const fileInput = document.querySelector(".input-field");
  
      // Check if a file is selected
      if (!fileInput || !fileInput.files || fileInput.files.length === 0) {
        console.error("No file selected");
        return;
      }
  
      // Get the first file from the input
      const file = fileInput.files[0];
  
      // Create a FormData object to send the file
      const formData = new FormData();
      formData.append("file", file);


      const accessToken = Cookies.get("accessToken");
      if (accessToken) {
        setToken(accessToken);
      }
      // Make the API call using Axios
      const response = await API.post("/upload", formData, {
        headers: {
          "Content-Type": "multipart/form-data",
          Authorization: `Bearer ${accessToken}`, // Add your authentication token
        },
      });
  
      // Handle the response
      console.log("File upload response:", response.data);
      // You can add further logic based on the response if needed
      setSuccess(true);
      setSuccessMessage("File uploaded successfully!");

      // Reset success state and message after a few seconds
      setTimeout(() => {
        setSuccess(false);
        setSuccessMessage("");
      }, 3000); 
    } catch (error) {
      console.error("Error uploading file:", error);
      // Handle the error as needed
    }
  };

  return (
    <div>
      <Header />
      <div className="drive-container">
        <main>
          <form
            action=""
            onClick={() => document.querySelector(".input-field").click()}
          >
            <input
              type="file"
              accept="image/*"
              className="input-field"
              hidden
              onChange={({ target: { files } }) => {
                files[0] && setFileName(files[0].name);
                if (files) {
                  setImage(URL.createObjectURL(files[0]));
                }
              }}
            />
            {image ? (
              <img src={image} width={200} height={200} alt={fileName} />
            ) : (
              <>
                <MdCloudUpload color="#512da8" size={60} />
                <p>Browe File to Upload </p>
              </>
            )}
         
          </form>
          <section className="uploaded-row">
            <AiFillFileImage color="#512da8" />
            <span className="uploaded-content">
              {fileName}
              <MdDelete 
                onClick={() => {
                  setFileName("No selected file");
                  setImage(null);
                }}
              />
            </span>
            
          </section>
          {success && <div className="success-message">{successMessage}</div>}
          <button type="submit" onClick={handleSubmit}>Upload File to Drive</button>
        </main>
      </div>
      <Footer />
    </div>
  );
};

export default Drive;
