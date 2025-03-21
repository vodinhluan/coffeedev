import { useState } from "react";
import { useNavigate } from "react-router-dom";
import AdminTable from "../../components/AdminTable";
import useFetchData from "../../useFetchData";
import Pagination from "../../components/Pagination";
import { Category } from "../../type/Category";
import LoadingSpinner from "../../components/LoadingSpinner";

const CategoriesPage = () => {
  const { data: categories = [], loading, error, setData } = useFetchData<Category[]>(
    "http://localhost:8082/CoffeeDev/api/categories"
  );

  const navigate = useNavigate();

  // Pagination state
  const [currentPage, setCurrentPage] = useState(1);
  const categoriesPerPage = 5;
  const totalPages = categories ? Math.ceil(categories.length / categoriesPerPage) : 0;
  const startIndex = (currentPage - 1) * categoriesPerPage;
  const endIndex = startIndex + categoriesPerPage;
  const currentCategories = categories ? categories.slice(startIndex, endIndex) : [];

  const handleEdit = (category: Category) => {
    navigate(`/categories/${category.id}`);
  };

const handleDelete = async (category: Category) => {
    if (!window.confirm(`Bạn có chắc muốn xóa category ${category.name}?`)) return;

    try {
      const token = localStorage.getItem("token");
      if (!token) throw new Error("Token không tồn tại!");

      const response = await fetch(`http://localhost:8082/CoffeeDev/api/categories/${category.id}`, {
        method: "DELETE",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
      });

      if (!response.ok) throw new Error("Xóa category thất bại!");

      // Xóa category khỏi danh sách state nếu API xóa thành công
      setData((prevCategories: Category[] | null) =>
        prevCategories ? prevCategories.filter((u) => u.id !== category.id) : []
      );

      alert(`Category ${category.name} đã bị xóa!`);
    } catch (error) {
      console.error("Lỗi khi xóa category:", error);
      alert("Không thể xóa category. Vui lòng thử lại!");
    }
  };

  const handlePageChange = (page: number) => {
    setCurrentPage(page);
  };

if (loading) return <LoadingSpinner />;
  if (error) return <div>Error: {error}</div>;

  return (
    <div className="p-4">
      <h1 className="text-3xl font-bold mb-4">Categories Management</h1>
      <button
        className="bg-green-500 text-white py-2 px-4 rounded mb-4 hover:bg-green-600"
        onClick={() => {
          navigate("/create-category");
        }}
      >
        Create Category
      </button>


      {currentCategories.length > 0 ? (
        <>
          <AdminTable<Category>
            data={currentCategories}
            columns={[
              { header: "ID", accessor: "id" },
              { header: "Name", accessor: "name" },
              {
                header: "Image",
                accessor: "image",
                cell: (row) => {
                  return (
                    <div className="flex justify-center">
                      <img
                        src={row.image ? row.image : "/avatar_default.png"}
                        alt={row.name}
                        className="w-16 h-16 rounded-full object-cover"
                      />
                    </div>
                  );
                },
              }, 
              { header: "Enabled", accessor: "enabled" },
            ]}
            onEdit={handleEdit}
            onDelete={handleDelete}
          />

          {/* Pagination Controls */}
          <Pagination
            currentPage={currentPage}
            totalPages={totalPages}
            onPageChange={handlePageChange}
          />
        </>
      ) : (
        <div>No categories found.</div>
      )}
    </div>
  );
};

export default CategoriesPage;
