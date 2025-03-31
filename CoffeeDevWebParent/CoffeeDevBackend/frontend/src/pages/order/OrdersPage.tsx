import { useState } from "react";
import { useNavigate } from "react-router-dom";
import AdminTable from "../../components/AdminTable";
import useFetchData from "../../useFetchData";
import Pagination from "../../components/Pagination";
import { Order } from "../../type/Order";
import LoadingSpinner from "../../components/LoadingSpinner";
import { deleteOrder } from "../../api/orderService";

const OrdersPage = () => {
  const { data: orders = [], loading, error, setData } = useFetchData<Order[]>(
    "http://localhost:8082/CoffeeDev/api/orders"
  );

  const navigate = useNavigate();

  // Pagination state
  const [currentPage, setCurrentPage] = useState(1);
  const ordersPerPage = 5;
  const totalPages = Math.ceil((orders?.length || 0) / ordersPerPage);
  const startIndex = (currentPage - 1) * ordersPerPage;
  const endIndex = startIndex + ordersPerPage;
  const currentOrders = (orders ?? []).slice(startIndex, endIndex);
  console.log("Current Orders: ", currentOrders);

  const handleEdit = (order: Order) => {
    navigate(`/orders/${order.id}`);
  };

  const handleDelete = async (order: Order) => {
    if (window.confirm(`Are you sure you want to delete the order with ID: ${order.id}?`)) {
      try {
        await deleteOrder(order.id); 
        console.log("Order deleted: ", order);
        setData((prevOrders: Order[] | null) => {
          if (!prevOrders) return [];
          return prevOrders.filter(o => o.id !== order.id);
        });
      } catch (error) {
        console.error("Failed to delete order: ", error);
        alert("Failed to delete the order. Please try again.");
      }
    }
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
              {
                header: "Total Cost", 
                accessor: "totalCost" as keyof Order, 
                cell: (row: Order) => {
                  const cost = row.totalCost || 0;
                  return <>{cost.toLocaleString('en-US', { minimumFractionDigits: 3, maximumFractionDigits: 3 })}</>;
                }
              },
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
