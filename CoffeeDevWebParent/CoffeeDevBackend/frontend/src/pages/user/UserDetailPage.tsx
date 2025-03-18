import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import useFetchData from "../../useFetchData";
import { User } from "../../type/User";

const ROLES = ["SalePerson", "Admin", "Shipper"];

const UserDetailPage = () => {
    const { id } = useParams<{ id: string }>();
    const navigate = useNavigate();
    const { data: user, loading, error } = useFetchData<User>(`http://localhost:8082/CoffeeDev/api/users/${id}`);

    const [formData, setFormData] = useState<User | null>(null);
    const [selectedFile, setSelectedFile] = useState<File | null>(null);
    const [photoPreview, setPhotoPreview] = useState<string | null>(null);
    const [isUploading, setIsUploading] = useState(false);

    useEffect(() => {
        if (user) setFormData(user);
    }, [user]);

    const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
        if (!formData) return;
        const { name, value } = e.target;

        setFormData({
            ...formData,
            [name]: name === "roles" ? [value] : value,
        });
    };

    const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const file = e.target.files?.[0];
        if (file) {
            setSelectedFile(file);
            const reader = new FileReader();
            reader.onloadend = () => setPhotoPreview(reader.result as string);
            reader.readAsDataURL(file);
        }
    };

    const uploadImage = async () => {
        if (!selectedFile) return null;

        setIsUploading(true);
        const uploadFormData = new FormData();
        uploadFormData.append("file", selectedFile);

        try {
            const response = await fetch("http://localhost:8082/CoffeeDev/api/upload", {
                method: "POST",
                mode: "cors",
                body: uploadFormData,
            });

            if (!response.ok) throw new Error("Upload failed");

            const data = await response.json();
            return data.url;
        } catch (error) {
            console.error("Error uploading image:", error);
            alert("Failed to upload image. Please try again.");
            return null;
        } finally {
            setIsUploading(false);
        }
    };

    const handleUpdate = async () => {
        if (!formData) return;

        try {
            let photoUrl = formData.photo;
            if (selectedFile) {
                const uploadedUrl = await uploadImage();
                if (uploadedUrl) photoUrl = uploadedUrl;
            }

            const updatedData = { ...formData, photo: photoUrl };
            const token = localStorage.getItem("token");
            const res = await fetch(`http://localhost:8082/CoffeeDev/api/users/${id}`, {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json",
                    Authorization: `Bearer ${token}`,
                },
                body: JSON.stringify(updatedData),
            });

            if (!res.ok) throw new Error("Failed to update user");

            alert("User updated successfully!");
            navigate("/users");
        } catch (err) {
            console.error(err);
            alert("Update failed!");
        }
    };

    if (loading) return <div className="text-center py-10">Loading...</div>;
    if (error) return <div className="text-center py-10 text-red-500">Error: {error}</div>;
    if (!formData) return <div className="text-center py-10">No user data found.</div>;

    return (
        <div className="flex min-h-screen bg-gray-100">
            <div className="flex-1 flex justify-center items-center p-10">
                <div className="bg-white shadow-lg rounded-lg p-8 w-full max-w-3xl">
                    <h1 className="text-3xl font-bold text-center text-gray-800 mb-6">Edit User</h1>

                    <div className="flex items-center justify-center mb-6">
                        <img src={photoPreview || formData.photo || "../../../public/avatar_default.png"} alt="User Avatar" className="w-24 h-24 rounded-full shadow-md" />
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
                            <label className="block text-gray-700 font-medium">Email</label>
                            <input type="email" name="email" value={formData.email} onChange={handleChange} className="input-field" />
                        </div>
                        <div>
                            <label className="block text-gray-700 font-medium">Photo</label>
                            <input type="file" name="photo" onChange={handleFileChange} className="input-field" />
                        </div>
                        <div>
                            <label className="block text-gray-700 font-medium">Role</label>
                            <select
                                name="roles"
                                value={formData.roles[0] || ""}
                                onChange={handleChange}
                                className="block w-full p-2 border border-gray-300 rounded-lg bg-white focus:ring-blue-500 focus:border-blue-500"
                            >
                                {ROLES.map((role) => (
                                    <option key={role} value={role}>
                                        {role}
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
                        <button className="bg-gray-500 hover:bg-gray-600 text-white font-medium py-2 px-6 rounded-lg" onClick={() => navigate("/users")}>Cancel</button>
                        <button className="bg-blue-500 hover:bg-blue-600 text-white font-medium py-2 px-6 rounded-lg" onClick={handleUpdate} disabled={isUploading}>
                            {isUploading ? "Uploading..." : "Update"}
                        </button>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default UserDetailPage;