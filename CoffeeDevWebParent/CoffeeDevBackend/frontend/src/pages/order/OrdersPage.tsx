import { useState } from "react";
import { useNavigate } from "react-router-dom";
import AdminTable from "../../components/AdminTable";
import useFetchData from "../../useFetchData";
import Pagination from "../../components/Pagination";
import { Order } from "../../type/Order";
import LoadingSpinner from "../../components/LoadingSpinner";

interface ApiResponse {
  content: Order[];
  // You can add other pagination fields from the response if needed
  // totalPages?: number;
  // totalElements?: number;
}

const OrdersPage = () => {
  const { data: apiResponse = { content: [] }, loading, error, setData } = useFetchData<ApiResponse>(
    "http://localhost:8082/CoffeeDev/api/orders"
  );

  // Extract orders from the content array
  const orders = apiResponse?.content || [];

  const navigate = useNavigate();

  // Pagination state
  const [currentPage, setCurrentPage] = useState(1);
  const ordersPerPage = 5;
  const totalPages = Math.ceil(orders.length / ordersPerPage);
  const startIndex = (currentPage - 1) * ordersPerPage;
  const endIndex = startIndex + ordersPerPage;
  const currentOrders = orders.slice(startIndex, endIndex);
  console.log("Current Orders: ", currentOrders);

  const handleEdit = (order: Order) => {
    navigate(`/orders/${order.id}`);
  };

  const handleDelete = (order: Order) => {
    console.log("Delete order: ", order);
    setData((prevResponse: ApiResponse | null) => {
      if (!prevResponse) return { content: [] };

      const updatedContent = prevResponse.content.filter(o => o.id !== order.id);
      return { ...prevResponse, content: updatedContent };
    });
  };

  const handlePageChange = (page: number) => {
    setCurrentPage(page);
  };

  if (loading) return <LoadingSpinner />;
  if (error) return <div>Error: {error}</div>;

  return (
    <div className="p-4">
      <h1 className="text-3xl font-bold mb-4">Orders Management</h1>
      <button
        className="bg-green-500 text-white py-2 px-4 rounded mb-4 hover:bg-green-600"
        onClick={() => console.log("Create order")}
      >
        Create Order
      </button>

      {currentOrders.length > 0 ? (
        <>
          <AdminTable<Order>
            data={currentOrders}
            columns={[
              { header: "ID", accessor: "id" },
              { header: "Name", accessor: "name" },
              { header: "Phone Number", accessor: "phoneNumber" },
              { header: "Order Time", accessor: "orderTime" },
              { header: "Total Cost", accessor: "totalCost" },
              { header: "Payment Method", accessor: "paymentMethod" },
              { header: "Order Status", accessor: "orderStatus" },
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
        <div>No orders found.</div>
      )}
    </div>
  );
};

export default OrdersPage;
