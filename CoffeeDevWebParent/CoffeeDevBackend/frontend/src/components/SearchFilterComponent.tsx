import React, { useState } from "react";

interface SearchFilterProps {
  onSearch: (query: string) => void;
  onReset: () => void;
  onFilter: (maxPrice: number) => void;
  minPrice?: number;
  maxPrice?: number;
}

const SearchFilterComponent: React.FC<SearchFilterProps> = ({ onSearch, onReset, onFilter, minPrice, maxPrice }) => {
  const [query, setQuery] = useState("");
  const [showFilter, setShowFilter] = useState(false);
  const [price, setPrice] = useState<number>(maxPrice ?? 0);

  const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setQuery(event.target.value);
  };

  const handleSearch = () => {
    onSearch(query);
  };

  const handleReset = () => {
    setQuery("");
    onReset();
  };

  // Khi nhấn Enter trong ô search, thực hiện tìm kiếm
  const handleKeyDown = (event: React.KeyboardEvent<HTMLInputElement>) => {
    if (event.key === "Enter") {
      handleSearch();
    }
  };

  // Khi áp dụng filter, gọi onFilter và ẩn thanh trượt
  const handleApplyFilter = () => {
    if (price !== undefined) {
      onFilter(price);
    }
    setShowFilter(false);
  };

  return (
    <div className="flex flex-col space-y-2">
      {/* Hàng đầu: Search, Reset, Filter buttons nằm ngang */}
      <div className="flex items-center space-x-2">
        <input
          type="text"
          value={query}
          onChange={handleInputChange}
          onKeyDown={handleKeyDown}
          placeholder="Search for name..."
          className="border border-gray-300 rounded-lg px-4 py-2 w-full max-w-md focus:outline-none focus:ring-2 focus:ring-blue-500"
        />
        <button
          onClick={handleSearch}
          className="bg-blue-500 text-white px-4 py-2 rounded-lg hover:bg-blue-600"
        >
          Search
        </button>
        <button
          onClick={handleReset}
          className="bg-gray-400 text-white px-4 py-2 rounded-lg hover:bg-gray-500"
        >
          Reset
        </button>
        <button
          onClick={() => setShowFilter(!showFilter)}
          className="bg-yellow-300 text-gray-800 px-4 py-2 rounded-lg hover:bg-yellow-400 transition"
        >
          Filter
        </button>
      </div>

      {/* Hàng thứ hai: Thanh trượt Filter (chỉ hiện khi người dùng bấm Filter) */}
      {showFilter && (
        <div className="p-4 border border-gray-300 rounded-lg shadow-md bg-white w-full max-w-md mx-auto">
          <h2 className="text-lg font-bold mb-2 text-center">Filter by Price</h2>
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
            onClick={handleApplyFilter}
            className="mt-4 w-full bg-blue-500 text-white py-2 rounded-lg hover:bg-blue-600 transition"
          >
            Apply
          </button>
        </div>
      )}
    </div>
  );
};

export default SearchFilterComponent;