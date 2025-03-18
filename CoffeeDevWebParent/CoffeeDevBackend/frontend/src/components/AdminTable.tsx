import { JSX } from "react";

interface Column<T> {
  header: string;
  accessor: keyof T;
  cell?: (row: T) => JSX.Element;
}

interface AdminTableProps<T extends { id: number }> {
  data: T[];
  columns: Column<T>[];
  onEdit: (item: T) => void;
  onDelete: (item: T) => void;
}

const AdminTable = <T extends { id: number }>({
  data,
  columns,
  onEdit,
  onDelete,
}: AdminTableProps<T>) => {
  return (
    <div className="overflow-x-auto">
      <table className="min-w-full bg-white border-separate border-gray-200">
        <thead>
          <tr>
            {columns.map((col) => (
              <th key={String(col.accessor)} className="px-4 py-2 border-b">
                {col.header}
              </th>
            ))}
            <th className="px-4 py-2 border-b">Actions</th>
          </tr>
        </thead>
        <tbody>
          {data.map((item) => (
            <tr key={item.id} className="hover:bg-gray-100">
              {columns.map((col) => (
                <td key={String(col.accessor)} className="px-4 py-2 border-b">
                  {col.cell
                    ? col.cell(item)
                    : typeof item[col.accessor] === "boolean"
                      ? item[col.accessor]
                        ? "✅ Active"
                        : "❌ Inactive"
                      : item[col.accessor]?.toString() ?? "N/A"}
                </td>
              ))}

              <td className="px-4 py-2 border-b">
                <button
                  className="mr-2 text-blue-500 hover:text-blue-700"
                  onClick={() => onEdit(item)}
                >
                  Edit
                </button>
                <button
                  className="text-red-500 hover:text-red-700"
                  onClick={() => onDelete(item)}
                >
                  Delete
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default AdminTable;
