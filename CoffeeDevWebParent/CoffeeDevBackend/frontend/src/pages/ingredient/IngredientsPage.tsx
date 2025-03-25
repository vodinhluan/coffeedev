import { useEffect, useState } from "react";
import { getIngredients } from "../../api/ingredientService";
import { Ingredient } from "../../type/Ingredient";
import IngredientTable from "../../components/IngredientTable";
import ImportExportModal from "../../components/ImportExportModal";
import IngredientForm from "./IngredientForm";
import { useNavigate } from "react-router-dom";
const IngredientsPage = () => {
  const [ingredients, setIngredients] = useState<Ingredient[]>([]);
  const [selectedIngredient, setSelectedIngredient] = useState<Ingredient | null>(null);
  const [modalType, setModalType] = useState<"import" | "export" | null>(null);
  const [modalOpen, setModalOpen] = useState(false);

  const navigate = useNavigate();


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

      <div className="flex items-center mt-4 space-x-4">
        {/* Button mở form thêm nguyên liệu */}
        <button className="p-2 bg-blue-500 text-white rounded" onClick={() => setModalOpen(true)}>
          ➕ Thêm Nguyên Liệu
        </button>
        <button className="p-2 bg-yellow-300 text-white rounded" onClick={() => {
          navigate("/ingredient-logs");
        }}>
          📜 Lịch Sử
        </button>
      </div>



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
