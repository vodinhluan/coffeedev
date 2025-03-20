import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import useFetchData from "../../useFetchData";
import { Product } from "../../type/Product";
import { useImageUpload } from "../../utils/imageUpload";

interface Category {
    id: number;
    name: string;
}

const ProductDetailPage = () => {
    const { id } = useParams<{ id: string }>();
    const navigate = useNavigate();
    const { data: product, loading, error } = useFetchData<Product>(`http://localhost:8082/CoffeeDev/api/products/${id}`);

    const [formData, setFormData] = useState<Product | null>(null);
    const [categories, setCategories] = useState<Category[]>([]);


    // Initialize the image upload hook with the product's photo URL once it's available
    const {
        photoPreview,
        isUploading,
        handleFileChange,
        uploadImage
    } = useImageUpload(product?.image || '');

    useEffect(() => {
        if (product) setFormData(product);
    }, [product]);

    useEffect(() => {
        const token = localStorage.getItem("token");
        fetch("http://localhost:8082/CoffeeDev/api/categories", {
            headers: {
                Authorization: `Bearer ${token}`,
            },
        })
            .then((res) => res.json())
            .then((data) => setCategories(data))
            .catch((err) => console.error("Lỗi khi lấy danh mục:", err));
    }, []);

    const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
        if (!formData) return;
        const { name, value } = e.target;

        setFormData({
            ...formData,
            [name]: name === "categoryId" ? Number(value) : value,

        });
    };

    const handleUpdate = async () => {
        if (!formData) return;

        try {
            let photoUrl = formData.image;
            console.log("photoUrl: ", photoUrl);
            // Only upload if there's a new file selected
            const newPhotoUrl = await uploadImage();
            if (newPhotoUrl) photoUrl = newPhotoUrl;
            console.log('newPhotoUrl: ', photoUrl);

            const updatedData = { ...formData, image: photoUrl };
            console.log('updatedData: ', updatedData);
            const token = localStorage.getItem("token");
            const res = await fetch(`http://localhost:8082/CoffeeDev/api/products/${id}`, {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json",
                    Authorization: `Bearer ${token}`,
                },
                body: JSON.stringify(updatedData),
            });

            if (!res.ok) throw new Error("Failed to update product");

            alert("Product updated successfully!");
            navigate("/products");
        } catch (err) {
            console.error(err);
            alert("Update failed!");
        }
    };

    if (loading) return <div className="text-center py-10">Loading...</div>;
    if (error) return <div className="text-center py-10 text-red-500">Error: {error}</div>;
    if (!formData) return <div className="text-center py-10">No product data found.</div>;

    return (
        <div className="flex min-h-screen bg-gray-100">
            <div className="flex-1 flex justify-center items-center p-10">
                <div className="bg-white shadow-lg rounded-lg p-8 w-full max-w-3xl">
                    <h1 className="text-3xl font-bold text-center text-gray-800 mb-6">Edit Product</h1>

                    <div className="flex items-center justify-center mb-6">
                        <img src={photoPreview || formData.image || "../../../public/avatar_default.png"} alt="Product Avatar" className="w-24 h-24 rounded-full shadow-md" />
                    </div>

                    <div className="grid grid-cols-2 gap-6">
                        <div>
                            <label className="block text-gray-700 font-medium">ID</label>
                            <input type="text" name="id" value={formData.id} disabled className="input-field bg-gray-200" />
                        </div>
                        <div>
                            <label className="block text-gray-700 font-medium">Name</label>
                            <input type="text" name="name" value={formData.name} onChange={handleChange} className="input-field" />
                        </div>

                        <div>
                            <label className="block text-gray-700 font-medium">Alias</label>
                            <input type="text" name="name" value={formData.alias} onChange={handleChange} className="input-field" />
                        </div>

                        <div>
                            <label className="block text-gray-700 font-medium">Description</label>
                            <textarea name="description" value={formData.description} className="input-field" />
                        </div>


                        <div>
                            <label className="block text-gray-700 font-medium">Image</label>
                            <input type="file" name="image" onChange={handleFileChange} className="input-field" />
                        </div>

                        <div>
                            <label className="block text-gray-700 font-medium">Price</label>
                            <input type="text" name="price" value={formData.price} onChange={handleChange} className="input-field" />
                        </div>

                        {/* Sửa chỗ này */}
                        <div>
                            <label className="block text-gray-700 font-medium">Category</label>
                            <select
                                name="categoryId"
                                value={formData.categoryId}
                                onChange={handleChange}
                                className="input-field"
                            >
                                {categories.map((category) => (
                                    <option key={category.id} value={category.id}>
                                        {category.name}
                                    </option>
                                ))}
                            </select>
                        </div>

                        <div className="flex items-center mt-4">
                            <input type="checkbox" name="enabled" checked={formData.enabled} onChange={(e) => setFormData({ ...formData, enabled: e.target.checked })} className="mr-2" />
                            <label className="text-gray-700 font-medium">Active</label>
                        </div>
                    </div>

                    <div className="mt-6 flex justify-end space-x-4">
                        <button className="bg-gray-500 hover:bg-gray-600 text-white font-medium py-2 px-6 rounded-lg" onClick={() => navigate("/products")}>Cancel</button>
                        <button className="bg-blue-500 hover:bg-blue-600 text-white font-medium py-2 px-6 rounded-lg" onClick={handleUpdate} disabled={isUploading}>
                            {isUploading ? "Uploading..." : "Update"}
                        </button>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default ProductDetailPage;