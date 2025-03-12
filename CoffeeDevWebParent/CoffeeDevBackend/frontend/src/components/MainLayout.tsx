import { useState } from "react";
import { Outlet } from "react-router-dom";
import Sidebar from "./Sidebar";

const MainLayout = () => {
  const [isSidebarOpen, setSidebarOpen] = useState(false);
  const toggleSidebar = () => setSidebarOpen(!isSidebarOpen);

  return (
    <div className="flex h-screen transition-all duration-300">
      {/* Sidebar */}
      <Sidebar isOpen={isSidebarOpen} toggleSidebar={toggleSidebar} />

      {/* Nội dung chính + Button toggle nằm cùng một khối để đẩy nhau */}
      <div
        className={`flex-1 transition-all duration-300 ${
          isSidebarOpen ? "ml-64" : "ml-0"
        }`}
      >
        {/* Button Toggle, nằm trong div để nó cũng bị đẩy theo */}
        <button
          className="md:hidden p-2 text-white bg-[#8b5e3c] fixed top-4 left-4 z-50 transition-all duration-300"
          style={{ left: isSidebarOpen ? "13rem" : "1rem" }} // Dịch button theo Sidebar
          onClick={toggleSidebar}
        >
          ☰
        </button>

        {/* Nội dung trang */}
        <div className="p-4 pl-16">
          <Outlet />
        </div>
      </div>
    </div>
  );
};

export default MainLayout;
