import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom";
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
import NotFoundPage from "../pages/errors/NotFoundPage";
import CreateUserPage from "../pages/user/CreateUserPage";
import CategoryDetailPage from "../pages/category/CategoryDetailPage";
import CreateCategoryPage from "../pages/category/CreateCategoryPage";
import ProductDetailPage from "../pages/product/ProductDetailPage";
import CreateProductPage from "../pages/product/CreateProductPage";
import OrderDetailPage from "../pages/order/OrderDetailPage";
import CreateOrderPage from "../pages/order/CreateOrderPage";

const AppRoutes = () => {
  const isAuthenticated = useRecoilValue(authState);

  return (
    <Router>
      <Routes>
        <Route path="/login" element={<Login />} />
        {/* Redirect from / to /login */}
        <Route path="/" element={<Navigate to="/login" replace />} />
        <Route
          path="/"
          element={
            <ProtectedRoute isAuthenticated={isAuthenticated}>
              <MainLayout />
            </ProtectedRoute>
          }
        >
          <Route path="dashboard" element={<DashboardPage />} />

          {/* Order Routes */}
          <Route path="orders" element={<OrdersPage />} />
          <Route path="orders/:id" element={<OrderDetailPage />} />
          <Route path="create-order" element={<CreateOrderPage />} />

          {/* Product Routes */}
          <Route path="products" element={<ProductsPage />} />
          <Route path="products/:id" element={<ProductDetailPage />} />
          <Route path="create-product" element={<CreateProductPage />} />

          {/* Category Routes */}
          <Route path="categories" element={<CategoriesPage />} />
          <Route path="categories/:id" element={<CategoryDetailPage />} />
          <Route path="create-category" element={<CreateCategoryPage />} />
          
          {/* Customer Routes */}
          <Route path="customers" element={<CustomersPage />} />

          {/* User Routes */}
          <Route path="users" element={<UsersPage />} />
          <Route path="/users/:id" element={<UserDetailPage />} />
          <Route path="create-user" element={<CreateUserPage />} />

        </Route>
        <Route path="*" element={<NotFoundPage />} />
      </Routes>
    </Router>
  );
};

export default AppRoutes;
