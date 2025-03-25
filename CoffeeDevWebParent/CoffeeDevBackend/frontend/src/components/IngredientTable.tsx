import { Ingredient } from "../type/Ingredient";
import { deleteIngredient } from "../api/ingredientService";

interface Props {
  ingredients: Ingredient[];
  onOpenModal: (ingredient: Ingredient, type: "import" | "export") => void;
  onReload: () => void; // ✅ Hàm reload danh sách sau khi xóa
}

const IngredientTable = ({ ingredients, onOpenModal, onReload }: Props) => {
  const handleDelete = async (id: number) => {
    const confirmDelete = window.confirm("Bạn có chắc chắn muốn xóa nguyên liệu này?");
    if (!confirmDelete) return;

    try {
      await deleteIngredient(id);
      alert("Đã xóa nguyên liệu thành công!");
      onReload();
    } catch (error) {
      console.error("Lỗi khi xóa nguyên liệu:", error);
      alert("Xóa nguyên liệu thất bại! Vui lòng thử lại.");
    }
  };

  return (
    <table className="min-w-full bg-white shadow-md rounded mt-4">
      <thead>
        <tr>
          <th className="p-2">Tên</th>
          <th className="p-2">Đơn vị</th>
          <th className="p-2">Số lượng</th>
          <th className="p-2">Nhập/Xuất</th>
          <th className="p-2">Actions</th>
        </tr>
      </thead>
      <tbody>
        {ingredients.map((ing) => (
          <tr key={ing.id} className={ing.quantity < ing.minQuantity ? "bg-red-100" : ""}>
            <td className="p-2">{ing.name}</td>
            <td className="p-2">{ing.unit}</td>
            <td className="p-2">{ing.quantity}</td>
            <td className="p-2">
              <button onClick={() => onOpenModal(ing, "import")}>📥 Nhập</button>
              <button onClick={() => onOpenModal(ing, "export")} className="ml-2">📤 Xuất</button>
            </td>
            <td className="p-2">
              <button onClick={() => handleDelete(ing.id)} className="text-red-500">🗑️ Xóa</button>
            </td>
          </tr>
        ))}
      </tbody>
    </table>
  );
};

export default IngredientTable;
