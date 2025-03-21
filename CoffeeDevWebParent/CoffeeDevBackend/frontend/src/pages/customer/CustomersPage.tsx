import { useState } from "react";
import { useNavigate } from "react-router-dom";
import AdminTable from "../../components/AdminTable";
import useFetchData from "../../useFetchData";
import { Customer } from "../../type/Customer";
import Pagination from "../../components/Pagination";
import LoadingSpinner from "../../components/LoadingSpinner";

const CustomersPage = () => {
  const { data: customers = [], loading, error, setData } = useFetchData<Customer[]>(
    "http://localhost:8082/CoffeeDev/api/customers"
  );

  const navigate = useNavigate();
  
  // Pagination state
  const [currentPage, setCurrentPage] = useState(1);
  const customersPerPage = 5; 
  const totalPages = customers ? Math.ceil(customers.length / customersPerPage) : 0;
  const startIndex = (currentPage - 1) * customersPerPage;
  const endIndex = startIndex + customersPerPage;
  const currentCustomers = customers ? customers.slice(startIndex, endIndex) : [];

  const handleEdit = (customer: Customer) => {
    navigate(`/customers/${customer.id}`);
  };

  const handleDelete = (customer: Customer) => {
    console.log("Delete customer: ", customer);
    setData((prevCustomers: Customer[] | null) => 
      prevCustomers ? prevCustomers.filter((c) => c.id !== customer.id) : []
    );
  };

  const handlePageChange = (page: number) => {
    setCurrentPage(page);
  };

if (loading) return <LoadingSpinner />;
  if (error) return <div>Error: {error}</div>;

  return (
    <div className="p-4">
      <h1 className="text-3xl font-bold mb-4">Customers Management</h1>
      <button
        className="bg-green-500 text-white py-2 px-4 rounded mb-4 hover:bg-green-600"
        onClick={() => console.log("Create customer")}
      >
        Create Customer
      </button>

      {currentCustomers.length > 0 ? (
        <>
          <AdminTable<Customer> 
            data={currentCustomers} 
            columns={[
              { header: "ID", accessor: "id" },
              { header: "Email", accessor: "email" },
              { header: "Name", accessor: "name" },
              { header: "Phone", accessor: "phoneNumber" },
              { header: "Created", accessor: "createdTime" },
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
        <div>No customers found.</div>
      )}
    </div>
  );
};

export default CustomersPage;
