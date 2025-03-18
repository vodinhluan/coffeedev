import { useState } from "react";
import { useNavigate } from "react-router-dom";
import AdminTable from "../../components/AdminTable";
import Pagination from "../../components/Pagination";
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
  const usersPerPage = 5;

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

  const handlePageChange = (page: number) => {
    setCurrentPage(page);
  };

  if (loading) return <div>Loading...</div>;
  if (error) return <div>Error: {error}</div>;

  return (
    <div className="p-4">
      <h1 className="text-3xl font-bold mb-4">Users Management</h1>
      <button
        className="bg-green-500 text-white py-2 px-4 rounded mb-4 hover:bg-green-600"
        onClick={() => {
          navigate("/create-user");
        }}
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
              {
                header: "Photo",
                accessor: "photo",
                cell: (row) => {
                  console.log("Hello");
                  console.log("Photo URL:", row.photo); // ✅ Giờ sẽ log đúng
                  return (
                    <img
                      src={row.photo ? row.photo : "/avatar_default.png"}
                      alt={row.name}
                      className="w-16 h-16 rounded-full object-cover"
                    />
                  );
                },
              },
              


              { header: "Status", accessor: "enabled" },
              { header: "Roles", accessor: "roles" },
            ]}
            onEdit={handleEdit}
            onDelete={handleDelete}
          />


          {/* Use the Pagination component */}
          <Pagination
            currentPage={currentPage}
            totalPages={totalPages}
            onPageChange={handlePageChange}
          />
        </>
      ) : (
        <div>No users found.</div>
      )}
    </div>
  );
};

export default UsersPage;
