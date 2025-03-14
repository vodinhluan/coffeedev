import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { useRecoilValue } from "recoil";
import { authState } from "../state";
import Login from "../components/Login";
import MainLayout from "../components/MainLayout";
import DashboardPage from "../pages/DashboardPage";
import UsersPage from "../pages/user/UsersPage";
import OrdersPage from "../pages/order/OrdersPage";
import ProductsPage from "../pages/product/ProductsPage";
import CategoriesPage from "../pages/category/CategoriesPage";
import CustomersPage from "../pages/customer/CustomersPage";
import ProtectedRoute from "./ProtectedRoute";
import UserDetailPage from "../pages/user/UserDetailPage";

const AppRoutes = () => {
  const isAuthenticated = useRecoilValue(authState);

  return (
    <Router>
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route
          path="/"
          element={
            <ProtectedRoute isAuthenticated={isAuthenticated}>
              <MainLayout />
            </ProtectedRoute>
          }
        >
          <Route path="dashboard" element={<DashboardPage />} />
          <Route path="orders" element={<OrdersPage />} />
          <Route path="products" element={<ProductsPage />} />
          <Route path="categories" element={<CategoriesPage />} />
          <Route path="customers" element={<CustomersPage />} />
          <Route path="users" element={<UsersPage />} />
          <Route path="/users/:id" element={<UserDetailPage />} />

        </Route>
        <Route path="*" element={<h1>404 Not Found</h1>} />
      </Routes>
    </Router>
  );
};

export default AppRoutes;
