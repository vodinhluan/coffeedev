import { useResetRecoilState, useSetRecoilState } from "recoil";
import { authState, userState } from "../state";
import { useNavigate } from "react-router-dom";

const Logout = () => {
  const resetUser = useResetRecoilState(userState);
  const navigate = useNavigate();
  const setAuth = useSetRecoilState(authState); 

  const handleLogout = () => {
    localStorage.removeItem("token");
    resetUser(); 
    setAuth(false);
    alert("Bạn đang đăng xuất khỏi hệ thống");
    navigate("/login"); 
  };

  return (
    <button onClick={handleLogout} className="w-full bg-red-500 py-2 rounded-lg hover:bg-red-600 transition duration-300">
      Logout
    </button>
  );
};

export default Logout;
