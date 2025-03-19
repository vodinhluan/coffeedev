import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import useFetchData from "../../useFetchData";
import { Category } from "../../type/Category";
import { useImageUpload } from "../../utils/imageUpload";


const CategoryDetailPage = () => {
    const { id } = useParams<{ id: string }>();
    const navigate = useNavigate();
    const { data: category, loading, error } = useFetchData<Category>(`http://localhost:8082/CoffeeDev/api/categories/${id}`);

    const [formData, setFormData] = useState<Category | null>(null);
    
    // Initialize the image upload hook with the category's photo URL once it's available
    const { 
        photoPreview, 
        isUploading, 
        handleFileChange, 
        uploadImage 
    } = useImageUpload(category?.image || '');

    useEffect(() => {
        if (category) setFormData(category);
    }, [category]);

    const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
        if (!formData) return;
        const { name, value } = e.target;

        setFormData({
            ...formData,
            [name]: name === "roles" ? [value] : value,
        });
    };

    const handleUpdate = async () => {
        if (!formData) return;

        try {
            let photoUrl = formData.image;
            // Only upload if there's a new file selected
            const newPhotoUrl = await uploadImage();
            if (newPhotoUrl) photoUrl = newPhotoUrl;

            const updatedData = { ...formData, photo: photoUrl };
            const token = localStorage.getItem("token");
            const res = await fetch(`http://localhost:8082/CoffeeDev/api/categories/${id}`, {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json",
                    Authorization: `Bearer ${token}`,
                },
                body: JSON.stringify(updatedData),
            });

            if (!res.ok) throw new Error("Failed to update category");

            alert("Category updated successfully!");
            navigate("/categories");
        } catch (err) {
            console.error(err);
            alert("Update failed!");
        }
    };

    if (loading) return <div className="text-center py-10">Loading...</div>;
    if (error) return <div className="text-center py-10 text-red-500">Error: {error}</div>;
    if (!formData) return <div className="text-center py-10">No category data found.</div>;

    return (
        <div className="flex min-h-screen bg-gray-100">
            <div className="flex-1 flex justify-center items-center p-10">
                <div className="bg-white shadow-lg rounded-lg p-8 w-full max-w-3xl">
                    <h1 className="text-3xl font-bold text-center text-gray-800 mb-6">Edit Category</h1>

                    <div className="flex items-center justify-center mb-6">
                        <img src={photoPreview || formData.image || "../../../public/avatar_default.png"} alt="Category Avatar" className="w-24 h-24 rounded-full shadow-md" />
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
                            <label className="block text-gray-700 font-medium">Photo</label>
                            <input type="file" name="photo" onChange={handleFileChange} className="input-field" />
                        </div>
                        
                        <div className="flex items-center mt-4">
                            <input type="checkbox" name="enabled" checked={formData.enabled} onChange={(e) => setFormData({ ...formData, enabled: e.target.checked })} className="mr-2" />
                            <label className="text-gray-700 font-medium">Active</label>
                        </div>
                    </div>

                    <div className="mt-6 flex justify-end space-x-4">
                        <button className="bg-gray-500 hover:bg-gray-600 text-white font-medium py-2 px-6 rounded-lg" onClick={() => navigate("/categories")}>Cancel</button>
                        <button className="bg-blue-500 hover:bg-blue-600 text-white font-medium py-2 px-6 rounded-lg" onClick={handleUpdate} disabled={isUploading}>
                            {isUploading ? "Uploading..." : "Update"}
                        </button>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default CategoryDetailPage;