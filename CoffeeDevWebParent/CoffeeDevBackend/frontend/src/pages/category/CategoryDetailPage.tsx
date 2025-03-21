import { JSX, useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import useFetchData from "../../useFetchData";
import { Category } from "../../type/Category";
import { useImageUpload } from "../../utils/imageUpload";

const CategoryDetailPage = () => {
    const { id } = useParams<{ id: string }>();
    const navigate = useNavigate();
    const { data: category, loading, error } = useFetchData<Category>(`http://localhost:8082/CoffeeDev/api/categories/${id}`);
    const { data: allCategories, loading: loadingCategories } = useFetchData<Category[]>("http://localhost:8082/CoffeeDev/api/categories");
    const filteredCategories = allCategories?.filter(cat => cat.enabled);

    const [formData, setFormData] = useState<Category | null>(null);
    const [selectedChildren, setSelectedChildren] = useState<number[]>([]);
    const [currentParentId, setCurrentParentId] = useState<number | null>(null);

    // Initialize the image upload hook with the category's photo URL once it's available
    const {
        photoPreview,
        isUploading,
        handleFileChange,
        uploadImage
    } = useImageUpload(category?.image || '');

    useEffect(() => {
        if (category) {
            setFormData(category);
            // Initialize selected children from existing data
            if (category.children && category.children.length > 0) {
                setSelectedChildren(category.children.map(child => child.id));
            }
            // Save the parent id if this category has a parent
            if (category.parent) {
                setCurrentParentId(category.parent.id);
            }
        }
    }, [category]);

    // Tìm tất cả các danh mục con (bao gồm con của con) của một danh mục
    const findAllChildrenIds = (categoryId: number, categories: Category[]): number[] => {
        const directChildren = categories.filter(c => c.parent && c.parent.id === categoryId);
        if (directChildren.length === 0) return [];

        const directChildrenIds = directChildren.map(c => c.id);
        const nestedChildrenIds = directChildren.flatMap(child =>
            findAllChildrenIds(child.id, categories)
        );

        return [...directChildrenIds, ...nestedChildrenIds];
    };

    // Tìm tất cả các danh mục cha (bao gồm cha của cha) của một danh mục
    const findAllParentIds = (categoryId: number, categories: Category[]): number[] => {
        const category = categories.find(c => c.id === categoryId);
        if (!category || !category.parent) return [];

        const parentId = category.parent.id;
        const ancestorIds = findAllParentIds(parentId, categories);

        return [parentId, ...ancestorIds];
    };

    // Lọc danh sách các danh mục không hợp lệ (không thể chọn làm con)
    const getInvalidCategoryIds = (currentId: number, categories: Category[]): number[] => {
        if (!categories) return [];
    
        const selfId = [currentId];
        const allChildrenIds = findAllChildrenIds(currentId, categories);
        const allParentIds = findAllParentIds(currentId, categories);
    
        // ⚠ Loại bỏ danh mục cha của danh mục hiện tại
        const currentCategory = categories.find(cat => cat.id === currentId);
        const parentId = currentCategory?.parent ? [currentCategory.parent.id] : [];
    
        return [...selfId, ...allChildrenIds, ...allParentIds, ...parentId];
    };
    

    // Xây dựng cấu trúc cây danh mục với UI rõ ràng hơn
    const buildCategoryTree = (categories: Category[], currentCategoryId: number) => {
        if (!categories) return null;

        // Lấy danh sách ID không hợp lệ
        const invalidCategoryIds = getInvalidCategoryIds(currentCategoryId, categories);

        // Lọc ra các danh mục gốc (không có parent)
        const rootCategories = categories.filter(cat => !cat.parent);

        // Hàm đệ quy để xây dựng cây cho mỗi danh mục
        const renderCategory = (category: Category, level: number, isLast: boolean, parentPath: string = ""): JSX.Element | null => {
            if (invalidCategoryIds.includes(category.id)) return null;
    
            // ✅ Bỏ danh mục cha khỏi danh sách có thể chọn
            if (currentParentId !== null && category.id === currentParentId) return null;
    
            const validChildren = categories
                .filter(child => child.parent && child.parent.id === category.id)
                .filter(child => !invalidCategoryIds.includes(child.id));
    

            // Tạo đường dẫn mới
            const currentPath = parentPath + (isLast ? "    " : "│   ");

            // Biểu tượng nhánh: ├── cho các mục không phải cuối cùng, └── cho mục cuối cùng
            const branchSymbol = isLast ? "└── " : "├── ";

            return (
                <div key={category.id} className="font-mono">
                    <div className="flex items-center">
                        <span className="text-gray-500">{parentPath}{branchSymbol}</span>
                        <label className="flex items-center cursor-pointer">
                            <input
                                type="checkbox"
                                checked={selectedChildren.includes(category.id)}
                                onChange={(e) => {
                                    if (e.target.checked) {
                                        setSelectedChildren(prev => [...prev, category.id]);
                                    } else {
                                        setSelectedChildren(prev => prev.filter(id => id !== category.id));
                                    }
                                }}
                                className="mr-2"
                            />
                            <span className="font-semibold">{category.name}</span>
                        </label>
                    </div>

                    {/* Hiển thị các danh mục con */}
                    <div>
                        {validChildren.map((child, index) =>
                            renderCategory(
                                child,
                                level + 1,
                                index === validChildren.length - 1,
                                currentPath
                            )
                        )}
                    </div>
                </div>
            );
        };

        // Kiểm tra có danh mục hợp lệ nào không
        const validRootCategories = rootCategories
            .filter(root => !invalidCategoryIds.includes(root.id))
            .filter(root => currentParentId === null || root.id !== currentParentId);

        if (validRootCategories.length === 0) {
            return (
                <div className="border rounded-md p-4 bg-gray-50 text-center text-gray-500">
                    Không có danh mục nào khả dụng để chọn làm danh mục con
                </div>
            );
        }

        // Bắt đầu xây dựng cây từ các danh mục gốc
        return (
            <div className="max-h-64 overflow-y-auto border rounded-md p-4 bg-gray-50">
                {validRootCategories.map((root, index, arr) =>
                    renderCategory(root, 0, index === arr.length - 1)
                )}
            </div>
        );
    };

    const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
        if (!formData) return;
        const { name, value } = e.target;

        setFormData({
            ...formData,
            [name]: value,
        });
    };

    const handleUpdate = async () => {
        if (!formData || !filteredCategories) return;

        try {
            let photoUrl = formData.image;
            // Only upload if there's a new file selected
            const newPhotoUrl = await uploadImage();
            if (newPhotoUrl) photoUrl = newPhotoUrl;

            // Create the updated children array based on selected IDs
            const childrenCategories = selectedChildren.map(childId =>
                filteredCategories.find(cat => cat.id === childId)
            ).filter(Boolean) as Category[];

            // Create the payload with the correct format
            const updatedData = {
                ...formData,
                image: photoUrl,
                children: childrenCategories
            };

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

    // Hiển thị thông tin danh mục cha (nếu có)
    const renderParentInfo = () => {
        if (!formData || !formData.parent) return null;

        return (
            <div className="mt-4 p-3 bg-blue-50 rounded-md border border-blue-200">
                <h3 className="font-medium text-blue-700">Parent Category:</h3>
                <div className="flex items-center mt-1">
                    <div className="w-8 h-8 bg-blue-100 rounded-full flex items-center justify-center mr-2">
                        <span className="text-blue-600 text-lg">↑</span>
                    </div>
                    <div>
                        <p className="font-medium">{formData.parent.name}</p>
                    </div>
                </div>
            </div>
        );
    };

    if (loading || loadingCategories) return <div className="text-center py-10">Loading...</div>;
    if (error) return <div className="text-center py-10 text-red-500">Error: {error}</div>;
    if (!formData || !allCategories) return <div className="text-center py-10">No category data found.</div>;

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
                            <label className="block text-gray-700 font-medium">Image</label>
                            <input type="file" name="image" onChange={handleFileChange} className="input-field" />
                        </div>

                        <div className="flex items-center mt-4">
                            <input
                                type="checkbox"
                                name="enabled"
                                checked={formData.enabled}
                                onChange={(e) => setFormData({ ...formData, enabled: e.target.checked })}
                                className="mr-2"
                            />
                            <label className="text-gray-700 font-medium">Active</label>
                        </div>
                    </div>

                    {/* Hiển thị thông tin về danh mục cha nếu có */}
                    {renderParentInfo()}

                    <div className="mt-6">
                        <label className="block text-gray-700 font-medium mb-2">Select Children Categories</label>
                        {filteredCategories && buildCategoryTree(filteredCategories, formData.id)}
                        <p className="text-xs text-gray-500 mt-1">
                            Chọn các danh mục sẽ trở thành danh mục con
                        </p>
                    </div>

                    <div className="mt-6 flex justify-end space-x-4">
                        <button className="bg-gray-500 hover:bg-gray-600 text-white font-medium py-2 px-6 rounded-lg" onClick={() => navigate("/categories")}>
                            Cancel
                        </button>
                        <button
                            className="bg-blue-500 hover:bg-blue-600 text-white font-medium py-2 px-6 rounded-lg"
                            onClick={handleUpdate}
                            disabled={isUploading}
                        >
                            {isUploading ? "Uploading..." : "Update"}
                        </button>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default CategoryDetailPage;