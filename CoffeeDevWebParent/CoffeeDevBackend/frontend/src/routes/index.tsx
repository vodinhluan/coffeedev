import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Login from "../components/Login";
// import App from "../App"; 

const AppRoutes = () => {
  return (
    <Router>
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="/*" element={<Login />} />
        {/* <Route path="/*" element={<App />} /> */}
      </Routes>
    </Router>
  );
};

export default AppRoutes;
