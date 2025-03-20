import { useState } from "react";
import { useNavigate } from "react-router-dom";
import AdminTable from "../../components/AdminTable";
import useFetchData from "../../useFetchData";
import Pagination from "../../components/Pagination";
import { Category } from "../../type/Category";

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

  const handleDelete = (category: Category) => {
    console.log("Delete category: ", category);
    setData((prevCategories: Category[] | null) =>
      prevCategories ? prevCategories.filter((c) => c.id !== category.id) : []
    );
  };

  const handlePageChange = (page: number) => {
    setCurrentPage(page);
  };

  if (loading) return <div>Loading...</div>;
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
                  console.log("Hello");
                  console.log("Photo URL:", row.image); // ✅ Giờ sẽ log đúng
                  return (
                    <img
                      src={row.image ? row.image : "/avatar_default.png"}
                      alt={row.name}
                      className="w-16 h-16 rounded-full object-cover"
                    />
                  );
                },
              }, { header: "Enabled", accessor: "enabled" },
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
