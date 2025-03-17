import { useState } from "react";
import { useNavigate } from "react-router-dom";
import AdminTable from "../../components/AdminTable";
import useFetchData from "../../useFetchData";
import Pagination from "../../components/Pagination";
import { Product } from "../../type/Product";

const ProductsPage = () => {
  const { data: products = [], loading, error, setData } = useFetchData<Product[]>(
    "http://localhost:8082/CoffeeDev/api/products"
  );

  const navigate = useNavigate();
  
  // Pagination state
  const [currentPage, setCurrentPage] = useState(1);
  const productsPerPage = 5; 
  const totalPages = products ? Math.ceil(products.length / productsPerPage) : 0;
  const startIndex = (currentPage - 1) * productsPerPage;
  const endIndex = startIndex + productsPerPage;
  const currentProducts = products ? products.slice(startIndex, endIndex) : [];
  console.log("Current Products: ", currentProducts);

  const handleEdit = (product: Product) => {
    navigate(`/products/${product.id}`);
  };

  const handleDelete = (product: Product) => {
    console.log("Delete product: ", product);
    setData((prevProducts: Product[] | null) => 
      prevProducts ? prevProducts.filter((c) => c.id !== product.id) : []
    );
  };

  const handlePageChange = (page: number) => {
    setCurrentPage(page);
  };

  if (loading) return <div>Loading...</div>;
  if (error) return <div>Error: {error}</div>;

  return (
    <div className="p-4">
      <h1 className="text-3xl font-bold mb-4">Products Management</h1>
      <button
        className="bg-green-500 text-white py-2 px-4 rounded mb-4 hover:bg-green-600"
        onClick={() => console.log("Create product")}
      >
        Create Product
      </button>

      {currentProducts.length > 0 ? (
        <>
          <AdminTable<Product> 
            data={currentProducts} 
            columns={[
              { header: "ID", accessor: "id" },
              { header: "Name", accessor: "name" },
              { header: "Image", accessor: "image" },
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
        <div>No products found.</div>
      )}
    </div>
  );
};

export default ProductsPage;
