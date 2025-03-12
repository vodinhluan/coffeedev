import { Link } from "react-router-dom";
import Logout from "./Logout";

const Sidebar = () => {
    return (
        <div className="w-64 h-screen bg-[#8b5e3c] text-white p-4">
            <h2 className="text-2xl font-bold mb-6">CoffeeDev</h2>
            <ul>
                <li className="mb-4">
                    <Link to="/dashboard" className="block p-2 hover:bg-[#70432b] rounded">🏠 Dashboard</Link>
                </li>
                <li className="mb-4">
                    <Link to="/orders" className="block p-2 hover:bg-[#70432b] rounded">📦 Orders</Link>
                </li>
                <li className="mb-4">
                    <Link to="/products" className="block p-2 hover:bg-[#70432b] rounded">☕ Products</Link>
                </li>
                <li className="mb-4">
                    <Link to="/categories" className="block p-2 hover:bg-[#70432b] rounded">📂 Categories</Link>
                </li>
                <li className="mb-4">
                    <Link to="/customers" className="block p-2 hover:bg-[#70432b] rounded">👤 Customers</Link>
                </li>
                <li>
                    <Link to="/users" className="block p-2 hover:bg-[#70432b] rounded">👥 Users</Link>
                </li>
                <li >
                    <div className="mt-4">
                        <Logout />
                    </div>
                </li>
            </ul>
        </div>
    );
};

export default Sidebar;