import React, { useState } from "react";
import * as Slider from "@radix-ui/react-slider";

interface SearchFilterProps {
  onSearch: (query: string) => void;
  onReset: () => void;
  onFilter: (minPrice: number, maxPrice: number) => void;
  minPrice: number;
  maxPrice: number;
}

const SearchFilterComponent: React.FC<SearchFilterProps> = ({ onSearch, onReset, onFilter, minPrice, maxPrice }) => {
  const [query, setQuery] = useState("");
  const [showFilter, setShowFilter] = useState(false);
  const [priceRange, setPriceRange] = useState<[number, number]>([minPrice, maxPrice]);

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

  const handleKeyDown = (event: React.KeyboardEvent<HTMLInputElement>) => {
    if (event.key === "Enter") {
      handleSearch();
    }
  };

  const handlePriceChange = (values: number[]) => {
    setPriceRange([values[0], values[1]]);
  };

  const handleApplyFilter = () => {
    onFilter(priceRange[0], priceRange[1]);
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

          {/* Hiển thị giá trị Min - Max */}
          <div className="flex justify-between text-sm text-gray-600 mb-2">
            <span>{priceRange[0]}.000</span>
            <span>{priceRange[1]}.000</span>
          </div>

          {/* Thanh trượt Min - Max */}
          <Slider.Root
            className="relative flex items-center select-none touch-none w-full h-6"
            value={priceRange}
            onValueChange={handlePriceChange}
            min={minPrice}
            max={maxPrice}
            step={1}
          >
            <Slider.Track className="bg-gray-300 relative grow rounded-full h-2">
              <Slider.Range className="absolute bg-blue-500 rounded-full h-full" />
            </Slider.Track>
            <Slider.Thumb
              className="block w-4 h-4 bg-blue-500 rounded-full shadow cursor-pointer hover:bg-blue-600"
              aria-label="Minimum Price"
            />
            <Slider.Thumb
              className="block w-4 h-4 bg-blue-500 rounded-full shadow cursor-pointer hover:bg-blue-600"
              aria-label="Maximum Price"
            />
          </Slider.Root>

          {/* Nút Apply */}
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
