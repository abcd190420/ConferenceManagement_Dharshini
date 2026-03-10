import React from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

const Header: React.FC = () => {
  const navigate = useNavigate();

  const user = JSON.parse(localStorage.getItem("user") || "{}");

  const handleLogout = async () => {
    try {
      const response = await axios.get("http://localhost:8080/auth/logout");

      alert(response.data);

      localStorage.clear();

      navigate("/auth");
    } catch (error: any) {
      console.log(error.response?.data);
    }
  };

  return (
    <div className="header">
      <h2>Conference Dashboard</h2>

      <div>
        <span>{user.userName}</span>
        <span>{user.role}</span>
        <span>{user.emailId}</span>

        <button onClick={handleLogout}>Logout</button>
      </div>
    </div>
  );
};

export default Header;
