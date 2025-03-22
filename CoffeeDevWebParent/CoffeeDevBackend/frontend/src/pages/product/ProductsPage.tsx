import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import AdminTable from "../../components/AdminTable";
import useFetchData from "../../useFetchData";
import Pagination from "../../components/Pagination";
import { Product } from "../../type/Product";
import LoadingSpinner from "../../components/LoadingSpinner";
import SearchFilterComponent from "../../components/SearchFilterComponent";

const ProductsPage = () => {
  const { data: products = [], loading, error, setData } = useFetchData<Product[]>(
    "http://localhost:8082/CoffeeDev/api/products"
  );

  const [originalProducts, setOriginalProducts] = useState<Product[]>([]);

  // Khi dữ liệu thay đổi, cập nhật danh sách gốc
  useEffect(() => {
    if (products && products.length > 0 && originalProducts.length === 0) {
      setOriginalProducts(products);
    }
  }, [products, originalProducts.length]);

  const navigate = useNavigate();

  // Pagination state
  const [currentPage, setCurrentPage] = useState(1);
  const productsPerPage = 5;
  const totalPages = products ? Math.ceil(products.length / productsPerPage) : 0;
  const startIndex = (currentPage - 1) * productsPerPage;
  const endIndex = startIndex + productsPerPage;
  const currentProducts = products ? products.slice(startIndex, endIndex) : [];

  const handleEdit = (product: Product) => {
    navigate(`/products/${product.id}`);
  };

  const handleDelete = async (product: Product) => {
    if (!window.confirm(`Bạn có chắc muốn xóa product ${product.name}?`)) return;

    try {
      const token = localStorage.getItem("token");
      if (!token) throw new Error("Token không tồn tại!");

      const response = await fetch(`http://localhost:8082/CoffeeDev/api/products/${product.id}`, {
        method: "DELETE",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
      });

      if (!response.ok) throw new Error("Xóa product thất bại!");

      // Xóa product khỏi danh sách state nếu API xóa thành công
      setData((prevProducts: Product[] | null) =>
        prevProducts ? prevProducts.filter((u) => u.id !== product.id) : []
      );

      alert(`Product ${product.name} đã bị xóa!`);
    } catch (error) {
      console.error("Lỗi khi xóa product:", error);
      alert("Không thể xóa product. Vui lòng thử lại!");
    }
  };

  const handlePageChange = (page: number) => {
    setCurrentPage(page);
  };


  const minPrice = 10;
  const maxPrice = 50;

  if (loading) return <LoadingSpinner />;
  if (error) return <div>Error: {error}</div>;

  return (
    <div className="p-4">
      <h1 className="text-3xl font-bold mb-4">Products Management</h1>
      <button
        className="bg-green-500 text-white py-2 px-4 rounded mb-4 hover:bg-green-600"
        onClick={() =>
          navigate("/create-product")
        }
      >
        Create Product
      </button>
      
      <div className="flex justify-between items-center mb-4">

      </div>

      <SearchFilterComponent
          minPrice={minPrice}
          maxPrice={maxPrice}
          onSearch={(query: string) => {
            if (!query.trim()) {
              setData(originalProducts); // Reset về danh sách gốc
            } else {
              const filteredProducts = originalProducts.filter(product =>
                product.name.toLowerCase().includes(query.toLowerCase())
              );
              setData(filteredProducts);
            }
          }}
          onReset={() => setData(originalProducts)}
          onFilter={(max) => {
            const filteredProducts = originalProducts.filter(product => product.price <= max);
            setData(filteredProducts);
          }}
        />

      {currentProducts.length > 0 ? (
        <>
          <AdminTable<Product>
            data={currentProducts}
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
              {
                header: "Price",
                accessor: "price",
                cell: (row) => <span>{`${row.price}.000`}</span>
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
        <div>No products found.</div>
      )}
    </div>
  );
};

export default ProductsPage;
