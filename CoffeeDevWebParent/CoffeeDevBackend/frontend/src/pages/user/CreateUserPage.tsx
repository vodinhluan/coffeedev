import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { FaUpload, FaUser, FaEnvelope, FaLock } from "react-icons/fa";

interface FormData {
  name: string;
  email: string;
  password: string;
  confirmPassword: string;
  photo: string;
  enabled: boolean;
  roles: string[];
}

const CreateUserPage: React.FC = () => {
  const navigate = useNavigate();
  const [isUploading, setIsUploading] = useState(false);
  const [formData, setFormData] = useState<FormData>({
    name: "",
    email: "",
    password: "",
    confirmPassword: "",
    photo: "",
    enabled: true,
    roles: ["USER"],
  });
  const [errors, setErrors] = useState<Partial<FormData>>({});
  const [photoPreview, setPhotoPreview] = useState<string | null>(null);
  const [selectedFile, setSelectedFile] = useState<File | null>(null); 

  // Available roles
  const availableRoles = ["Admin", "User", "SalePerson", "Shipper"];

  // Handle all input changes
  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value, type, checked } = e.target;
    setFormData({
      ...formData,
      [name]: type === "checkbox" ? checked : value,
    });
  };

  const handleRoleToggle = (role: string) => {
    setFormData((prev) => {
      const currentRoles = [...prev.roles];
      if (currentRoles.includes(role)) {
        return { ...prev, roles: currentRoles.filter((r) => r !== role) };
      } else {
        return { ...prev, roles: [...currentRoles, role] };
      }
    });
  };

   // Handle file selection
   const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0];
    if (file) {
      setSelectedFile(file); // Lưu file để upload sau
      const reader = new FileReader();
      reader.onloadend = () => setPhotoPreview(reader.result as string);
      reader.readAsDataURL(file);
    }
  };

  // Upload ảnh lên Cloudinary (chỉ gọi khi nhấn Submit)
  const uploadImage = async () => {
    if (!selectedFile) return null;

    setIsUploading(true);
    const uploadFormData = new FormData();
    uploadFormData.append("file", selectedFile);

    try {
      const response = await fetch("http://localhost:8082/CoffeeDev/api/upload", {
        method: "POST",
        mode: "cors", // Ensure CORS is enabled
        body: uploadFormData,
      });

      if (!response.ok) throw new Error("Upload failed");

      const data = await response.json();
      console.log('data: ', data);
      return data.url; // Trả về URL ảnh trên Cloudinary
    } catch (error) {
      console.error("Error uploading image:", error);
      alert("Failed to upload image. Please try again.");
      return null;
    } finally {
      setIsUploading(false);
    }
  };
  const validate = (): boolean => {
    const newErrors: Partial<FormData> = {};
    
    if (!formData.name.trim()) newErrors.name = "Name is required";
    if (!formData.email.trim()) {
      newErrors.email = "Email is required";
    } else if (!/\S+@\S+\.\S+/.test(formData.email)) {
      newErrors.email = "Email is invalid";
    }
    
    if (!formData.password) {
      newErrors.password = "Password is required";
    } else if (formData.password.length < 6) {
      newErrors.password = "Password must be at least 6 characters";
    }
    
    if (formData.password !== formData.confirmPassword) {
      newErrors.confirmPassword = "Passwords do not match";
    }

    
    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  // Handle form submission
  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!validate()) return;

    const token = localStorage.getItem("token");
    if (!token) {
      alert("You are not authenticated. Please login first.");
      navigate("/login");
      return;
    }

    try {
      
      // Nếu có ảnh được chọn, upload lên Cloudinary trước khi submit form
      if (selectedFile) {
        const uploadedUrl = await uploadImage();
        if (!uploadedUrl) return console.log('error image');
        
        setFormData(prev => ({ ...prev, photo: uploadedUrl }));
      }

      const userData = {
        ...formData,
        photo: formData.photo, 
        roles: formData.roles.map(role => ({ name: role })),
      };

      const response = await fetch("http://localhost:8082/CoffeeDev/api/users", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(userData),
      });

      if (!response.ok) throw new Error(`Failed to create user: ${response.statusText}`);

      alert("User created successfully!");
      navigate("/users");
    } catch (error) {
      console.error("Error creating user:", error);
      alert("Failed to create user. Please try again.");
    }
  };

  return (
    <div className="max-w-4xl mx-auto p-6">
      <div className="flex justify-between items-center mb-6">
        <h1 className="text-2xl font-bold text-gray-800">Create New User</h1>
        <button 
          onClick={() => navigate("/users")} 
          className="px-4 py-2 bg-gray-200 rounded-md hover:bg-gray-300 transition-colors text-gray-700"
        >
          Back to Users
        </button>
      </div>
      
      <form onSubmit={handleSubmit} className="bg-white rounded-lg shadow-md p-6">
        <div className="grid md:grid-cols-2 gap-6">
          {/* Left Column */}
          <div className="space-y-6">
            {/* Name */}
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">
                Name <span className="text-red-500">*</span>
              </label>
              <div className="relative">
                <div className="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                  <FaUser className="text-gray-400" />
                </div>
                <input
                  type="text"
                  name="name"
                  value={formData.name}
                  onChange={handleInputChange}
                  className={`w-full pl-10 pr-3 py-2 border ${
                    errors.name ? "border-red-500" : "border-gray-300"
                  } rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500`}
                  placeholder="John Doe"
                />
              </div>
              {errors.name && <p className="mt-1 text-sm text-red-600">{errors.name}</p>}
            </div>
            
            {/* Email */}
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">
                Email <span className="text-red-500">*</span>
              </label>
              <div className="relative">
                <div className="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                  <FaEnvelope className="text-gray-400" />
                </div>
                <input
                  type="email"
                  name="email"
                  value={formData.email}
                  onChange={handleInputChange}
                  className={`w-full pl-10 pr-3 py-2 border ${
                    errors.email ? "border-red-500" : "border-gray-300"
                  } rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500`}
                  placeholder="example@email.com"
                />
              </div>
              {errors.email && <p className="mt-1 text-sm text-red-600">{errors.email}</p>}
            </div>
            
            {/* Password */}
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">
                Password <span className="text-red-500">*</span>
              </label>
              <div className="relative">
                <div className="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                  <FaLock className="text-gray-400" />
                </div>
                <input
                  type="password"
                  name="password"
                  value={formData.password}
                  onChange={handleInputChange}
                  className={`w-full pl-10 pr-3 py-2 border ${
                    errors.password ? "border-red-500" : "border-gray-300"
                  } rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500`}
                  placeholder="••••••••"
                />
              </div>
              {errors.password && <p className="mt-1 text-sm text-red-600">{errors.password}</p>}
            </div>
            
            {/* Confirm Password */}
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">
                Confirm Password <span className="text-red-500">*</span>
              </label>
              <div className="relative">
                <div className="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                  <FaLock className="text-gray-400" />
                </div>
                <input
                  type="password"
                  name="confirmPassword"
                  value={formData.confirmPassword}
                  onChange={handleInputChange}
                  className={`w-full pl-10 pr-3 py-2 border ${
                    errors.confirmPassword ? "border-red-500" : "border-gray-300"
                  } rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500`}
                  placeholder="••••••••"
                />
              </div>
              {errors.confirmPassword && (
                <p className="mt-1 text-sm text-red-600">{errors.confirmPassword}</p>
              )}
            </div>
          </div>
          
          {/* Right Column */}
          <div className="space-y-6">
            {/* Profile Photo */}
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">Profile Photo</label>
              <div className="mt-1 flex items-center space-x-4">
                <div className="w-24 h-24 border border-gray-300 rounded-full overflow-hidden bg-gray-100 flex items-center justify-center">
                  {photoPreview ? (
                    <img
                      src={photoPreview}
                      alt="User profile"
                      className="w-full h-full object-cover"
                    />
                  ) : (
                    <FaUser size={32} className="text-gray-400" />
                  )}
                </div>
                <div>
                  <input
                    type="file"
                    id="photo-upload"
                    accept="image/*"
                    className="hidden"
                    onChange={handleFileChange}
                  />
                  <label
                    htmlFor="photo-upload"
                    className={`inline-block px-4 py-2 border border-gray-300 rounded-md shadow-sm text-sm font-medium ${
                      isUploading
                        ? "bg-gray-300 text-gray-500 cursor-wait"
                        : "bg-white text-gray-700 hover:bg-gray-50 cursor-pointer"
                    }`}
                  >
                    <div className="flex items-center">
                      <FaUpload className="mr-2" />
                      {isUploading ? "Uploading..." : "Choose File"}
                    </div>
                  </label>
                  {photoPreview && (
                    <button
                      type="button"
                      className="mt-2 text-sm text-red-600 hover:text-red-800"
                      onClick={() => {
                        setPhotoPreview(null);
                        setFormData((prev) => ({ ...prev, photo: "" }));
                      }}
                    >
                      Remove photo
                    </button>
                  )}
                </div>
              </div>
            </div>
            
            {/* Enabled */}
            <div>
              <div className="flex items-center">
                <input
                  type="checkbox"
                  name="enabled"
                  id="enabled"
                  checked={formData.enabled}
                  onChange={handleInputChange}
                  className="h-4 w-4 text-blue-600 border-gray-300 rounded focus:ring-blue-500"
                />
                <label htmlFor="enabled" className="ml-2 block text-sm text-gray-700">
                  Enabled
                </label>
              </div>
              <p className="mt-1 text-sm text-gray-500">
                Users that are not enabled cannot log in to the system.
              </p>
            </div>
            
            {/* Roles */}
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">
                Roles <span className="text-red-500">*</span>
              </label>
              <div className="space-y-2">
                {availableRoles.map((role) => (
                  <div key={role} className="flex items-center">
                    <input
                      type="checkbox"
                      id={`role-${role}`}
                      checked={formData.roles.includes(role)}
                      onChange={() => handleRoleToggle(role)}
                      className="h-4 w-4 text-blue-600 border-gray-300 rounded focus:ring-blue-500"
                    />
                    <label
                      htmlFor={`role-${role}`}
                      className="ml-2 block text-sm text-gray-700"
                    >
                      {role}
                    </label>
                  </div>
                ))}
              </div>
              {errors.roles && <p className="mt-1 text-sm text-red-600">{errors.roles}</p>}
            </div>
          </div>
        </div>
        
        {/* Submit Button */}
        <div className="mt-8 flex justify-end">
          <button
            type="submit"
            className="px-6 py-2 bg-blue-600 text-white rounded-md hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2 transition-colors"
          >
            Create User
          </button>
        </div>
      </form>
    </div>
  );
};

export default CreateUserPage;
