import Sidebar from "../components/Sidebar";
import DashboardContent from "../components/DashboardContent";

const Dashboard = () => {
  return (
    <div className="flex">
      <Sidebar /> {/* Menu bên trái */}
      <div className="flex-1 p-4">
        <DashboardContent /> {/* Nội dung chính */}
      </div>
    </div>
  );
};

export default Dashboard;
