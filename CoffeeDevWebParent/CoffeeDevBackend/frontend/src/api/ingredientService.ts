import axios from "axios";
import { Ingredient, IngredientLog } from "../type/Ingredient";

const API_URL = "http://localhost:8082/CoffeeDev/api/ingredients";

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
      // window.location.href = "/login";
    }
    return Promise.reject(error);
  }
);

// 🟢 API CALLS
export const getIngredients = async (): Promise<Ingredient[]> => {
  const response = await api.get("");
  return response.data;
};

export const createIngredient = async (ingredient: Partial<Ingredient>) => {
  return await api.post("", ingredient);
};

export const importIngredient = async (id: number, quantity: number, createdBy: string) => {
  return await api.post(`/import/${id}`, { quantity, createdBy });
};

export const exportIngredient = async (id: number, quantity: number, createdBy: string) => {
  return await api.post(`/export/${id}`, { quantity, createdBy });
};

export const getInventoryLogs = async (): Promise<IngredientLog[]> => {
  const response = await api.get("/logs");
  return response.data;
};
