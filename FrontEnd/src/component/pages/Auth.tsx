import React, { useContext, useState } from "react";
import { useNavigate } from "react-router-dom";
import "../css/Auth.css";
import axios from "axios";
import { AuthContext } from "../Hooks/AuthContext";

type UserData = {
  userId: number;
  userName: string;
  password: string;
  role: string;
  emailId: string;
};

const Auth: React.FC = () => {
  const { setUser } = useContext(AuthContext)!;
  const navigate = useNavigate();

  const url = "http://localhost:8080/auth/";

  const [isLogin, setIsLogin] = useState<boolean>(true);
  const [message, setMessage] = useState<string>("");

  const [formData, setFormData] = useState<UserData>({
    userId: 0,
    userName: "",
    password: "",
    role: "",
    emailId: "",
  });

  const handleChange = (
    e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>,
  ) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
  };

  const resetForm = () => {
    setFormData({
      userId: 0,
      userName: "",
      password: "",
      role: "",
      emailId: "",
    });
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    const payload = {
      userName: formData.userName,
      password: formData.password,
      role: formData.role,
      emailId: formData.emailId,
    };

    try {
      let response;

      if (isLogin) {
        const response = await axios.post(url + "login", payload);

        const user = response.data;
        setUser(user);

        localStorage.setItem("user", JSON.stringify(user));
        localStorage.setItem("role", user.role);
        localStorage.setItem("username", user.userName);

        setMessage("Login Successful");

        if (user.role == "Admin") {
          navigate("/dashboardadmin");
        } else {
          navigate("/dashboarduser");
        }
      } else {
        response = await axios.post(url + "register", payload);
        navigate("/auth");
        setMessage(response.data);
        resetForm();
      }
    } catch (error: any) {
      setMessage(error.response?.data || "Something went wrong");
    }
  };

  return (
    <div className="parent-container">
      <div className="auth-card">
        <h2>{isLogin ? "Login" : "Sign Up"}</h2>

        <form onSubmit={handleSubmit}>
          <input
            type="text"
            name="userName"
            placeholder="User Name"
            value={formData.userName}
            onChange={handleChange}
            required
          />

          {!isLogin && (
            <input
              type="email"
              name="emailId"
              placeholder="Email"
              value={formData.emailId}
              onChange={handleChange}
              required
            />
          )}

          {!isLogin && (
            <select
              name="role"
              value={formData.role}
              onChange={handleChange}
              required
            >
              <option value="">Select Role</option>
              <option value="Admin">Admin</option>
              <option value="User">User</option>
            </select>
          )}

          <input
            type="password"
            name="password"
            placeholder="Password"
            value={formData.password}
            onChange={handleChange}
            required
          />

          <button type="submit">{isLogin ? "Login" : "Sign Up"}</button>
        </form>

        {message && <p className="response-message">{message}</p>}

        <p>
          {isLogin ? "Don't have an account?" : "Already have an account?"}

          <span
            onClick={() => {
              setIsLogin(!isLogin);
              setMessage("");
              resetForm();
            }}
          >
            {isLogin ? " Sign Up" : " Login"}
          </span>
        </p>
      </div>
    </div>
  );
};

export default Auth;
