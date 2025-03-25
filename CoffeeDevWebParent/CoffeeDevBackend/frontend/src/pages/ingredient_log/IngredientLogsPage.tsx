import { useEffect, useState } from "react";
import { IngredientLog } from "../../type/Ingredient";
import { getIngredientLogsByDate } from "../../api/ingredientLogService";

const IngredientLogsPage = () => {
  const [logs, setLogs] = useState<IngredientLog[]>([]);
  const [selectedDate, setSelectedDate] = useState<string>(new Date().toISOString().split("T")[0]); // Mặc định là hôm nay

  useEffect(() => {
    loadLogs(selectedDate);
  }, [selectedDate]);

  const loadLogs = async (date: string) => {
    try {
      const data = await getIngredientLogsByDate(date);
      setLogs(data);
    } catch (error) {
      console.error("Lỗi khi lấy lịch sử kho:", error);
    }
  };

  return (
    <div className="p-4">
      <h1 className="text-2xl font-bold">Lịch sử nhập/xuất kho</h1>

      {/* Chọn ngày */}
      <input
        type="date"
        className="border p-2 mt-4"
        value={selectedDate}
        onChange={(e) => setSelectedDate(e.target.value)}
      />

      {/* Hiển thị lịch sử */}
      <table className="mt-4 w-full border-collapse border">
        <thead>
          <tr className="bg-gray-200">
            <th className="border p-2">Thời gian</th>
            <th className="border p-2">Nguyên liệu</th>
            <th className="border p-2">Loại</th>
            <th className="border p-2">Số lượng</th>
            <th className="border p-2">Người thực hiện</th>
          </tr>
        </thead>
        <tbody>
          {logs.length > 0 ? (
            logs.map((log) => (
              <tr key={log.id} className="border">
                <td className="p-2">{new Date(log.createdAt).toLocaleString("vi-VN")}</td>
                <td className="p-2">{log.ingredientName}</td>
                <td className={`p-2 ${log.type === "IMPORT" ? "text-green-500" : "text-red-500"}`}>
                  {log.type}
                </td>
                <td className="p-2">{log.quantity}</td>
                <td className="p-2">{log.createdBy}</td>
              </tr>
            ))
          ) : (
            <tr>
              <td colSpan={5} className="text-center p-4 text-gray-500">
                Không có dữ liệu lịch sử cho ngày {selectedDate}
              </td>
            </tr>
          )}
        </tbody>
      </table>
    </div>
  );
};

export default IngredientLogsPage;
