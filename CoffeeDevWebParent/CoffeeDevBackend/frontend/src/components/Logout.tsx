import { useNavigate } from "react-router-dom";

const Logout = () => {
    const navigate = useNavigate();

    const handleLogout = () => {
        alert('Bạn đang đăng xuất khỏi hệ thống');
        navigate('/login');
    };

    return (
        <div className="flex left">
            <button onClick={handleLogout} className="px-4 py-2 bg-red-500 text-white rounded hover:bg-red-600 transition duration-300">Logout</button>
        </div>
    );
}

export default Logout;