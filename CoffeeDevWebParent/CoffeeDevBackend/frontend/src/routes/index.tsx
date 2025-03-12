import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Login from "../components/Login";
import Dashboard from "../pages/Dashboard";
// import App from "../App"; 

const AppRoutes = () => {
  return (
    <Router>
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="/*" element={<Login />} />
        <Route path="/dashboard" element={<Dashboard />} />

        {/* <Route path="/*" element={<App />} /> */}
      </Routes>
    </Router>
  );
};

export default AppRoutes;
