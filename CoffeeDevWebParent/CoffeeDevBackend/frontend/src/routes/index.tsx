import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Login from "../components/Login";
import MainLayout from "../components/MainLayout";
import DashboardPage from "../pages/DashboardPage";
import UsersPage from "../pages/UsersPage";
import OrdersPage from "../pages/OrdersPage";
import ProductsPage from "../pages/ProductsPage";
import CategoriesPage from "../pages/CategoriesPage";
import CustomersPage from "../pages/CustomersPage";

const AppRoutes = () => {
  return (
    <Router>
      <Routes>
      <Route path="/login" element={<Login />} />
      <Route path="/" element={<MainLayout />}>
        <Route path="/dashboard" element={<DashboardPage  />} />
        <Route path="/orders" element={<OrdersPage  />} />
        <Route path="/products" element={<ProductsPage  />} />
        <Route path="/categories" element={<CategoriesPage  />} />
        <Route path="/customers" element={<CustomersPage  />} />
        <Route path="/users" element={<UsersPage  />} />
      </Route>
      </Routes>
    </Router>
  );
};

export default AppRoutes;
