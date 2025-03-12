import { useState } from "react";

const Login = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");

  const handleLogin = async (e: React.FormEvent) => {
    e.preventDefault();
    setError("");

    try {
      const response = await fetch("http://localhost:8082/CoffeeDev/api/auth/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ email, password }),
      });

      if (!response.ok) {
        throw new Error("Email hoặc mật khẩu không đúng!");
      }

      const data = await response.json();
      console.log('Data: ', data, data.token);
      localStorage.setItem("token", data.token);
      alert("Đăng nhập thành công!");
      
      // Chuyển hướng hoặc cập nhật state đăng nhập tại đây
    } catch (error) {
      if (error instanceof Error) {
        setError(error.message);
      } else {
        setError("Có lỗi xảy ra khi đăng nhập");
      }
    }
  };

  return (
    <div className="flex h-screen items-center justify-center bg-[#f5e1c5]">
      <div className="w-full max-w-md bg-[#8b5e3c] p-8 rounded-lg shadow-lg text-white">
        <h2 className="text-2xl font-bold text-center">CoffeeDevAdmin</h2>
        {error && <p className="text-red-400 text-center mt-2">{error}</p>}
        <form onSubmit={handleLogin} className="mt-6">
          <div className="mb-4">
            <label className="block">Email</label>
            <input
              type="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
              className="w-full px-4 py-2 mt-2 rounded-lg bg-[#e2cbb3] text-black"
            />
          </div>
          <div className="mb-4">
            <label className="block">Mật khẩu</label>
            <input
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
              className="w-full px-4 py-2 mt-2 rounded-lg bg-[#e2cbb3] text-black"
            />
          </div>
          <button
            type="submit"
            className="w-full bg-[#d4a373] py-2 rounded-lg hover:bg-[#c47f41] transition duration-300"
          >
            Đăng nhập
          </button>
        </form>
      </div>
    </div>
  );
};

export default Login;
