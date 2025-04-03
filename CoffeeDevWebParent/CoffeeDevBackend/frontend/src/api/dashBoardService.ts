// services/dashBoardService.ts
import axios from "axios";
import { OrderSummary } from "../type/OrderSummary";

const API_URL = "http://localhost:8082/CoffeeDev/api/dashboard";

// ✅ Luôn lấy token mới nhất từ `localStorage`
const getAuthToken = () => {
  return localStorage.getItem("token") ? `Bearer ${localStorage.getItem("token")}` : null;
};

// ✅ Cấu hình Axios
const api = axios.create({
  baseURL: API_URL,
});

// ✅ Cập nhật Interceptor để lấy token mỗi lần request
api.interceptors.request.use(
  (config) => {
    const token = getAuthToken();
    if (token) {
      console.log("🚀 Đã thêm token vào headers của request:", token);
      config.headers.Authorization = token;
    } else {
      console.warn("⚠️ Không tìm thấy token trong localStorage!");
    }
    return config;
  },
  (error) => Promise.reject(error)
);

// ✅ Xử lý lỗi 401 - Unauthorized
api.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response?.status === 401) {
      console.error("🔴 Lỗi 401: Token không hợp lệ hoặc hết hạn.");
      alert("Phiên đăng nhập hết hạn, vui lòng đăng nhập lại.");
    }
    return Promise.reject(error);
  }
);

// 🟢 API CALLS

// ✅ Lấy dữ liệu tổng hợp đơn hàng từ API
export const getOrderSummary = async (): Promise<OrderSummary | null> => {
  try {
    const response = await api.get<OrderSummary>("/orders/summary");
    console.log("📊 Dữ liệu Dashboard:", response.data);
    return response.data;
  } catch (error) {
    console.error("❌ Lỗi khi lấy dữ liệu Dashboard:", error);
    return null;
  }
};
