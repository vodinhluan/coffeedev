import { Ingredient } from "../type/Ingredient";


interface Props {
  ingredients: Ingredient[];
  onOpenModal: (ingredient: Ingredient, type: "import" | "export") => void;
}

const IngredientTable = ({ ingredients, onOpenModal }: Props) => {
  return (
    <table className="min-w-full bg-white shadow-md rounded mt-4">
      <thead>
        <tr>
          <th className="p-2">Tên</th>
          <th className="p-2">Đơn vị</th>
          <th className="p-2">Số lượng</th>
          <th className="p-2">Nhập/Xuất</th>
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
          </tr>
        ))}
      </tbody>
    </table>
  );
};

export default IngredientTable;
