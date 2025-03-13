import { NavLink } from "react-router-dom";
import { useRecoilValue } from "recoil";
import { userState } from "../state";
import Logout from "./Logout";

const Sidebar = ({ isOpen, toggleSidebar }: { isOpen: boolean; toggleSidebar: () => void }) => {
  const user = useRecoilValue(userState);
  const username = user?.name || "User";
  
  if (!user.token) {
    return null; 
  }
  
  const name = username || "User";


  return (
    <div
      className={`fixed inset-y-0 left-0 w-64 bg-[#8b5e3c] text-white p-4 transition-transform duration-300
      ${isOpen ? "translate-x-0" : "-translate-x-64"} md:relative md:translate-x-0 md:w-64 md:h-screen`}
    >
      <button className="md:hidden absolute top-4 right-4 text-white" onClick={toggleSidebar}>
        ✖
      </button>

      <h2 className="text-2xl font-bold mb-6">CoffeeDev</h2>
      <h3 className="text-balance font-serif mb-6">Xin Chào, {name}!</h3>
      <ul>
        {[
          { to: "/", label: "🏠 Dashboard" },
          { to: "/orders", label: "📦 Orders" },
          { to: "/products", label: "☕ Products" },
          { to: "/categories", label: "📂 Categories" },
          { to: "/customers", label: "👤 Customers" },
          { to: "/users", label: "👥 Users" }
        ].map(({ to, label }) => (
          <li key={to} className="mb-4">
            <NavLink
              to={to}
              className={({ isActive }) =>
                `block p-2 rounded transition ${isActive ? "bg-[#70432b] font-bold" : "hover:bg-[#70432b]"}`
              }
              onClick={toggleSidebar}
            >
              {label}
            </NavLink>
          </li>
        ))}

        <li>
          <div className="mt-4">
            <Logout />
          </div>
        </li>
      </ul>
    </div>
  );
};

export default Sidebar;
