import { useNavigate } from "react-router-dom";
import AdminTable from "../../components/AdminTable";
import { User } from "../../type/User";
import useFetchData from "../../useFetchData";

const UsersPage = () => {
  // Định kiểu cho useFetchData để tránh lỗi unknown[]
  const { data: users = [], loading, error, setData } = useFetchData<User[]>(
    "http://localhost:8082/CoffeeDev/api/users"
  );

  const columns: { header: string; accessor: keyof User }[] = [
    { header: "ID", accessor: "id" },
    { header: "Name", accessor: "name" },
    { header: "Email", accessor: "email" },
    { header: "Photo", accessor: "photo" },
    { header: "Status", accessor: "enabled" },
    { header: "Roles", accessor: "roles" },
  ];

  const navigate = useNavigate();

  const handleEdit = (user: User) => {
    navigate(`/users/${user.id}`);
  };

  const handleDelete = (user: User) => {
    console.log("Delete user: ", user);
    setData((prevUsers: User[] | null) => prevUsers ? prevUsers.filter((u) => u.id !== user.id) : []);
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
      {users && users.length > 0 ? (
        <AdminTable<User> data={users} columns={columns} onEdit={handleEdit} onDelete={handleDelete} />
      ) : (
        <div>No users found.</div>
      )}
    </div>
  );
};

export default UsersPage;
