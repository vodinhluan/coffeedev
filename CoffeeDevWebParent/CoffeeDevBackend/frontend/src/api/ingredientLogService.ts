import axios from "axios";
import { IngredientLog } from "../type/Ingredient";

const API_URL = "http://localhost:8082/CoffeeDev/api/ingredient-logs";

// ✅ Lấy lịch sử nhập/xuất kho theo ngày
export const getIngredientLogsByDate = async (date: string): Promise<IngredientLog[]> => {
  const token = localStorage.getItem("token");
  const response = await axios.get(`${API_URL}?date=${date}`, {
    headers: { Authorization: `Bearer ${token}` },
  });
  return response.data;
};
