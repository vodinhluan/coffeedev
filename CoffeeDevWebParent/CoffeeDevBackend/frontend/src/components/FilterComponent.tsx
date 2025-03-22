import React, { useState } from "react";

interface FilterProps {
    minPrice: number;
    maxPrice: number;
    onFilter: (value: number) => void;
}

const FilterComponent: React.FC<FilterProps> = ({ minPrice, maxPrice, onFilter }) => {
    const [showFilter, setShowFilter] = useState(false);
    const [price, setPrice] = useState(maxPrice);

    const handleFilter = () => {
        onFilter(price);
        setShowFilter(false); // Ẩn filter sau khi áp dụng
    };

    return (
        <div className="relative">
            <button
                onClick={() => setShowFilter(!showFilter)}
                className="bg-yellow-300 text-gray-800 px-4 py-2 rounded-lg hover:bg-yellow-400 transition"
            >
                Filter
            </button>

            {showFilter && (
                <div className="absolute mt-2 p-4 border border-gray-300 rounded-lg shadow-md bg-white w-64">
                    <h2 className="text-lg font-bold mb-4">Filter by Price</h2>

                    <div className="text-center mb-2 text-gray-600">
                        {minPrice}K - {price}K
                    </div>

                    <input
                        type="range"
                        min={minPrice}
                        max={maxPrice}
                        value={price}
                        onChange={(e) => setPrice(Number(e.target.value))}
                        className="w-full"
                    />

                    <button
                        onClick={handleFilter}
                        className="mt-4 w-full bg-blue-500 text-white py-2 rounded-lg hover:bg-blue-600 transition"
                    >
                        Apply
                    </button>
                </div>
            )}
        </div>
    );
};

export default FilterComponent;
