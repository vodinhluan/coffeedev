import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { FaUpload, FaImage, FaGlassCheers } from "react-icons/fa";
import { useImageUpload } from "../../utils/imageUpload";

interface FormData {
  name: string;
  image: string;
  enabled: boolean;
}

const CreateProductPage: React.FC = () => {
  const navigate = useNavigate();
  const [formData, setFormData] = useState<FormData>({
    name: "",
    image: "",
    enabled: true,
  });
  const [errors, setErrors] = useState<Partial<FormData>>({});

  // Use the image upload hook
  const {
    photoPreview,
    isUploading,
    handleFileChange,
    uploadImage,
    resetImage
  } = useImageUpload();


  // Handle all input changes
  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value, type, checked } = e.target;
    setFormData({
      ...formData,
      [name]: type === "checkbox" ? checked : value,
    });
  };

  const validate = (): boolean => {
    const newErrors: Partial<FormData> = {};

    if (!formData.name.trim()) newErrors.name = "Name is required";


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
      let uploadedUrl = formData.image; // Keep existing if available

      // Only upload if there's a new file selected
      if (photoPreview && !uploadedUrl) {
        uploadedUrl = (await uploadImage()) || "";
        if (!uploadedUrl) return console.log("Error uploading image.");
      }

      const productData = {
        ...formData,
        image: uploadedUrl, // Assign image URL to formData
      };

      console.log("productData being sent:", productData);

      const response = await fetch("http://localhost:8082/CoffeeDev/api/products", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(productData),
      });

      if (!response.ok)
        throw new Error(`Failed to create product: ${response.statusText}`);

      alert("Product created successfully!");
      navigate("/products");
    } catch (error) {
      console.error("Error creating product:", error);
      alert("Failed to create product. Please try again.");
    }
  };

  return (
    <div className="max-w-4xl mx-auto p-6">
      <div className="flex justify-between items-center mb-6">
        <h1 className="text-2xl font-bold text-gray-800">Create New Product</h1>
        <button
          onClick={() => navigate("/products")}
          className="px-4 py-2 bg-gray-200 rounded-md hover:bg-gray-300 transition-colors text-gray-700"
        >
          Back to Products
        </button>
      </div>

      <form onSubmit={handleSubmit} className="bg-white rounded-lg shadow-md p-6">
        <div className="grid md:grid-cols-1 gap-6">
          {/* Left Column */}
          <div className="space-y-6">
            {/* Name */}
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">
                Name <span className="text-red-500">*</span>
              </label>
              <div className="relative">
                <div className="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                  <FaGlassCheers  className="text-gray-400" />
                </div>
                <input
                  type="text"
                  name="name"
                  value={formData.name}
                  onChange={handleInputChange}
                  className={`w-full pl-10 pr-3 py-2 border ${errors.name ? "border-red-500" : "border-gray-300"
                    } rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500`}
                  placeholder="Coffee..."
                />
              </div>
              {errors.name && <p className="mt-1 text-sm text-red-600">{errors.name}</p>}
            </div>


          </div>

          <div className="space-y-6">
            {/* Profile Photo */}
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">Profile Photo</label>
              <div className="mt-1 flex items-center space-x-4">
                <div className="w-24 h-24 border border-gray-300 rounded-full overflow-hidden bg-gray-100 flex items-center justify-center">
                  {photoPreview ? (
                    <img
                      src={photoPreview}
                      alt="Product profile"
                      className="w-full h-full object-cover"
                    />
                  ) : (
                    <FaImage size={32} className="text-gray-400" />
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
                    className={`inline-block px-4 py-2 border border-gray-300 rounded-md shadow-sm text-sm font-medium ${isUploading
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
                        resetImage();
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
                Products that are not enabled cannot log in to the system.
              </p>
            </div>

          </div>
        </div>

        {/* Submit Button */}
        <div className="mt-8 flex justify-end">
          <button
            type="submit"
            className="px-6 py-2 bg-blue-600 text-white rounded-md hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2 transition-colors"
          >
            Create Product
          </button>
        </div>
      </form>
    </div>
  );
};

export default CreateProductPage;