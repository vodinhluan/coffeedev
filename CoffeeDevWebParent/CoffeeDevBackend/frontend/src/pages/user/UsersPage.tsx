import { useState } from "react";
import { useNavigate } from "react-router-dom";
import AdminTable from "../../components/AdminTable";
import { User } from "../../type/User";
import useFetchData from "../../useFetchData";

const UsersPage = () => {
  // Fetch user data
  const { data: users = [], loading, error, setData } = useFetchData<User[]>(
    "http://localhost:8082/CoffeeDev/api/users"
  );

  const navigate = useNavigate();

  // Pagination state
  const [currentPage, setCurrentPage] = useState(1);
  const usersPerPage = 6; // Show 6 users per page

  // Calculate pagination indexes
  const totalPages = users ? Math.ceil(users.length / usersPerPage) : 0;
  const startIndex = (currentPage - 1) * usersPerPage;
  const endIndex = startIndex + usersPerPage;
  const currentUsers = users ? users.slice(startIndex, endIndex) : [];

  const handleEdit = (user: User) => {
    navigate(`/users/${user.id}`);
  };

  const handleDelete = (user: User) => {
    console.log("Delete user: ", user);
    setData((prevUsers: User[] | null) => 
      prevUsers ? prevUsers.filter((u) => u.id !== user.id) : []
    );
  };

  if (loading) return <div>Loading...</div>;
  if (error) return <div>Error: {error}</div>;

  return (
    <div className="p-4">
      <h1 className="text-3xl font-bold mb-4">Users Management</h1>
      <button
        className="bg-green-500 text-white py-2 px-4 rounded mb-4 hover:bg-green-600"
        onClick={() => console.log("Create user")}
      >
        Create User
      </button>

      {currentUsers.length > 0 ? (
        <>
          <AdminTable<User> 
            data={currentUsers} 
            columns={[
              { header: "ID", accessor: "id" },
              { header: "Name", accessor: "name" },
              { header: "Email", accessor: "email" },
              { header: "Photo", accessor: "photo" },
              { header: "Status", accessor: "enabled" },
              { header: "Roles", accessor: "roles" },
            ]} 
            onEdit={handleEdit} 
            onDelete={handleDelete} 
          />

          {/* Pagination Controls */}
          <div className="flex justify-center mt-4 space-x-2">
            <button
              className={`px-4 py-2 rounded bg-gray-300 hover:bg-gray-400 ${currentPage === 1 ? "opacity-50 cursor-not-allowed" : ""}`}
              onClick={() => setCurrentPage((prev) => Math.max(prev - 1, 1))}
              disabled={currentPage === 1}
            >
              Previous
            </button>
            <span className="px-4 py-2 bg-gray-200 rounded">{currentPage} / {totalPages}</span>
            <button
              className={`px-4 py-2 rounded bg-gray-300 hover:bg-gray-400 ${currentPage === totalPages ? "opacity-50 cursor-not-allowed" : ""}`}
              onClick={() => setCurrentPage((prev) => Math.min(prev + 1, totalPages))}
              disabled={currentPage === totalPages}
            >
              Next
            </button>
          </div>
        </>
      ) : (
        <div>No users found.</div>
      )}
    </div>
  );
};

export default UsersPage;
