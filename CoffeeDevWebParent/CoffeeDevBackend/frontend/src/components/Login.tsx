import { useState } from "react";

const Login = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const handleLogin = (e: React.FormEvent) => {
    e.preventDefault();
    console.log("Login with:", email, password);
    // TODO: Xử lý đăng nhập (Gọi API)
  };

  return (
    <div className="flex h-screen items-center justify-center bg-[#F3E5AB]">
      <div className="w-full max-w-md bg-[#6F4E37] p-8 rounded-lg shadow-xl border border-[#8D6E63]">
        {/* Logo / Tiêu đề */}
        <h1 className="text-3xl font-bold text-center text-white">CoffeeDevAdmin</h1>
        <h2 className="text-xl font-semibold text-center text-[#D7CCC8] mt-2">Đăng nhập</h2>

        {/* Form đăng nhập */}
        <form onSubmit={handleLogin} className="mt-6">
          <div className="mb-4">
            <label className="block text-[#D7CCC8]">Email</label>
            <input
              type="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
              className="w-full px-4 py-2 mt-2 border border-[#A1887F] rounded-lg focus:outline-none focus:ring-2 focus:ring-[#D7CCC8] bg-[#EFEBE9] text-[#5D4037]"
            />
          </div>
          <div className="mb-4">
            <label className="block text-[#D7CCC8]">Mật khẩu</label>
            <input
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
              className="w-full px-4 py-2 mt-2 border border-[#A1887F] rounded-lg focus:outline-none focus:ring-2 focus:ring-[#D7CCC8] bg-[#EFEBE9] text-[#5D4037]"
            />
          </div>
          <button
            type="submit"
            className="w-full bg-[#4E342E] text-white py-2 rounded-lg hover:bg-[#3E2723] transition duration-300"
          >
            Đăng nhập
          </button>
        </form>
      </div>
    </div>
  );
};

export default Login;
