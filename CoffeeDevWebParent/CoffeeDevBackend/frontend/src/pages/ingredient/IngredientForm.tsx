import { useState } from "react";
import { createIngredient } from "../../api/ingredientService";

interface IngredientFormProps {
  onClose: () => void;
  onIngredientAdded: () => void;
}

const IngredientForm: React.FC<IngredientFormProps> = ({ onClose, onIngredientAdded }) => {
  const [name, setName] = useState("");
  const [unit, setUnit] = useState(""); // Cho phép nhập đơn vị tự do
  const [quantity, setQuantity] = useState("");
  const [minQuantity, setMinQuantity] = useState("");

  // Danh sách đơn vị gợi ý
  const suggestedUnits = ["kg", "g", "ml", "l", "hộp", "chai", "cái", "lon"];

  const handleSubmit = async () => {
    if (!name || !unit || !quantity || !minQuantity) {
      alert("Vui lòng nhập đầy đủ thông tin!");
      return;
    }

    try {
      await createIngredient({
        name,
        unit,
        quantity: Number(quantity),
        minQuantity: Number(minQuantity),
      });

      alert("Thêm nguyên liệu thành công!");
      onIngredientAdded();
      onClose();
    } catch (error) {
      console.error("Lỗi khi thêm nguyên liệu:", error);
      alert("Thêm nguyên liệu thất bại!");
    }
  };

  return (
    <div className="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50">
      <div className="bg-white p-6 rounded shadow-lg w-96">
        <h2 className="text-lg font-semibold mb-4">Thêm Nguyên Liệu</h2>

        {/* Tên nguyên liệu */}
        <input
          type="text"
          className="border p-2 w-full mb-3"
          placeholder="Tên nguyên liệu"
          value={name}
          onChange={(e) => setName(e.target.value)}
        />

        {/* Chọn hoặc nhập đơn vị */}
        <div className="mb-3">
          <input
            type="text"
            className="border p-2 w-full"
            placeholder="Đơn vị (vd: kg, g, l)"
            value={unit}
            onChange={(e) => setUnit(e.target.value)}
            list="unit-options"
          />
          <datalist id="unit-options">
            {suggestedUnits.map((u) => (
              <option key={u} value={u} />
            ))}
          </datalist>
        </div>

        {/* Số lượng hiện tại */}
        <input
          type="number"
          className="border p-2 w-full mb-3"
          placeholder="Số lượng"
          value={quantity}
          onChange={(e) => setQuantity(e.target.value)}
        />

        {/* Số lượng tối thiểu */}
        <input
          type="number"
          className="border p-2 w-full mb-3"
          placeholder="Số lượng tối thiểu"
          value={minQuantity}
          onChange={(e) => setMinQuantity(e.target.value)}
        />

        <button className="bg-green-500 text-white p-2 rounded w-full" onClick={handleSubmit}>
          ✅ Xác nhận
        </button>

        <button className="mt-2 text-red-500 w-full" onClick={onClose}>
          ❌ Hủy
        </button>
      </div>
    </div>
  );
};

export default IngredientForm;
