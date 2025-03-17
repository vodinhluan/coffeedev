import React from "react";

interface PaginationProps {
  currentPage: number;
  totalPages: number;
  onPageChange: (page: number) => void;
  maxPageButtons?: number;
}

const Pagination: React.FC<PaginationProps> = ({
  currentPage,
  totalPages,
  onPageChange,
  maxPageButtons = 5,
}) => {
  // Generate page numbers to display
  const getPageNumbers = () => {
    const pageNumbers: (number | string)[] = [];
    
    if (totalPages <= maxPageButtons) {
      // Show all pages if total is less than max buttons
      for (let i = 1; i <= totalPages; i++) {
        pageNumbers.push(i);
      }
    } else {
      // Always show first page
      pageNumbers.push(1);
      
      // Calculate start and end of middle section
      let startPage = Math.max(2, currentPage - Math.floor(maxPageButtons / 2));
      const endPage = Math.min(totalPages - 1, startPage + maxPageButtons - 3);
      
      // Adjust if we're near the end
      if (endPage - startPage < maxPageButtons - 3) {
        startPage = Math.max(2, totalPages - maxPageButtons + 2);
      }
      
      // Add ellipsis if needed
      if (startPage > 2) {
        pageNumbers.push('...');
      }
      
      // Add middle pages
      for (let i = startPage; i <= endPage; i++) {
        pageNumbers.push(i);
      }
      
      // Add ellipsis if needed
      if (endPage < totalPages - 1) {
        pageNumbers.push('...');
      }
      
      // Always show last page
      if (totalPages > 1) {
        pageNumbers.push(totalPages);
      }
    }
    
    return pageNumbers;
  };

  if (totalPages <= 1) return null;

  return (
    <nav aria-label="Pagination" className="flex justify-center my-6">
      <ul className="flex items-center space-x-1">
        {/* Previous button */}
        <li>
          <button
            onClick={() => onPageChange(currentPage - 1)}
            disabled={currentPage === 1}
            className={`relative block rounded px-3 py-1.5 text-sm transition-all duration-300
              ${currentPage === 1
                ? "pointer-events-none text-gray-400"
                : "text-gray-700 hover:bg-blue-50 hover:text-blue-600"
              }`}
            aria-label="Previous"
          >
            <span aria-hidden="true">&laquo;</span>
          </button>
        </li>
        
        {/* Page numbers */}
        {getPageNumbers().map((page, index) => (
          <li key={index}>
            {typeof page === 'number' ? (
              <button
                onClick={() => onPageChange(page)}
                aria-current={currentPage === page ? "page" : undefined}
                className={`relative block rounded px-3 py-1.5 text-sm transition-all duration-300
                  ${currentPage === page
                    ? "bg-blue-600 text-white font-medium" 
                    : "text-gray-700 hover:bg-blue-50 hover:text-blue-600"
                  }`}
              >
                {page}
              </button>
            ) : (
              <span className="px-3 py-1.5 text-sm text-gray-700">
                {page}
              </span>
            )}
          </li>
        ))}
        
        {/* Next button */}
        <li>
          <button
            onClick={() => onPageChange(currentPage + 1)}
            disabled={currentPage === totalPages}
            className={`relative block rounded px-3 py-1.5 text-sm transition-all duration-300
              ${currentPage === totalPages
                ? "pointer-events-none text-gray-400"
                : "text-gray-700 hover:bg-blue-50 hover:text-blue-600"
              }`}
            aria-label="Next"
          >
            <span aria-hidden="true">&raquo;</span>
          </button>
        </li>
      </ul>
    </nav>
  );
};

export default Pagination;