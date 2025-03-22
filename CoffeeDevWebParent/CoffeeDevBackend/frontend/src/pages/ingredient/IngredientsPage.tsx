import { useEffect, useState } from "react";
import { getIngredients } from "../../api/ingredientService";
import { Ingredient } from "../../type/Ingredient";
import IngredientTable from "../../components/IngredientTable";
import ImportExportModal from "../../components/ImportExportModal";
import IngredientForm from "./IngredientForm";
const IngredientsPage = () => {
  const [ingredients, setIngredients] = useState<Ingredient[]>([]);
  const [selectedIngredient, setSelectedIngredient] = useState<Ingredient | null>(null);
  const [modalType, setModalType] = useState<"import" | "export" | null>(null);
  const [modalOpen, setModalOpen] = useState(false);

  useEffect(() => {
    loadIngredients();
  }, []);

  const loadIngredients = async () => {
    try {
      const data = await getIngredients();
      setIngredients(data);
    } catch (error) {
      console.error("Lỗi khi lấy nguyên liệu:", error);
    }
  };

  return (
    <div className="p-4">
      <h1 className="text-2xl font-bold">Quản lý nguyên liệu</h1>

      {/* Button mở form thêm nguyên liệu */}
      <button className="mt-4 p-2 bg-blue-500 text-white rounded" onClick={() => setModalOpen(true)}>
        ➕ Thêm Nguyên Liệu
      </button>

      {/* Bảng danh sách nguyên liệu */}
      <IngredientTable
        ingredients={ingredients}
        onOpenModal={(ing, type) => {
          setSelectedIngredient(ing);
          setModalType(type);
        }}
      />

      {/* Modal nhập/xuất nguyên liệu */}
      {modalType && selectedIngredient && (
        <ImportExportModal
          ingredient={selectedIngredient}
          type={modalType}
          onClose={() => setModalType(null)}
          onReload={loadIngredients}
        />
      )}

      {/* Modal thêm nguyên liệu */}
      {modalOpen && <IngredientForm onClose={() => setModalOpen(false)} onIngredientAdded={loadIngredients} />}
    </div>
  );
};

export default IngredientsPage;
