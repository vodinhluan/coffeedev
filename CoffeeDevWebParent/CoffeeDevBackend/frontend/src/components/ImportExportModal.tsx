import { useState } from "react";
import { Ingredient } from "../type/Ingredient";
import { exportIngredient, importIngredient } from "../api/ingredientService";


interface Props {
  ingredient: Ingredient;
  type: "import" | "export";
  onClose: () => void;
  onReload: () => void;
}

const ImportExportModal = ({ ingredient, type, onClose, onReload }: Props) => {
  const [quantity, setQuantity] = useState<number>(0);
  const [createdBy, setCreatedBy] = useState<string>("");

  const handleSubmit = async () => {
    try {
      if (type === "import") {
        await importIngredient(ingredient.id, quantity, createdBy);
      } else {
        await exportIngredient(ingredient.id, quantity, createdBy);
      }
      onReload();
      onClose();
    } catch (error) {
      console.error("Lỗi khi cập nhật kho:", error);
    }
  };

  return (
    <div className="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50">
      <div className="bg-white p-4 rounded shadow-md">
        <h2 className="text-xl font-bold">{type === "import" ? "Nhập kho" : "Xuất kho"}</h2>
        <p>{ingredient.name} ({ingredient.unit})</p>

        <input type="number" placeholder="Số lượng" value={quantity} onChange={(e) => setQuantity(Number(e.target.value))} className="border p-2 w-full mt-2" />
        <input type="text" placeholder="Người thực hiện" value={createdBy} onChange={(e) => setCreatedBy(e.target.value)} className="border p-2 w-full mt-2" />

        <div className="mt-4 flex justify-end">
          <button onClick={onClose} className="mr-2">❌ Hủy</button>
          <button onClick={handleSubmit}>✅ Xác nhận</button>
        </div>
      </div>
    </div>
  );
};

export default ImportExportModal;
