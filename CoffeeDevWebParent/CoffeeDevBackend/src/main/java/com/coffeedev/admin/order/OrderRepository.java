package com.coffeedev.admin.order;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.coffeedev.common.entity.Order;

/**
 * Repository cho thực thể Order, cung cấp các phương thức truy vấn dữ liệu từ cơ sở dữ liệu.
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

	/**
	 * Tìm tất cả các đơn hàng có tên hoặc số điện thoại chứa từ khóa được cung cấp.
	 * 
	 * @param keyword Từ khóa để tìm kiếm trong tên hoặc số điện thoại.
	 * @param sort Đối tượng sắp xếp kết quả.
	 * @return Danh sách các đơn hàng phù hợp với từ khóa.
	 */
	@Query("SELECT o FROM Order o WHERE o.name LIKE %:keyword% OR o.phoneNumber LIKE %:keyword%")
	List<Order> findAll(@Param("keyword") String keyword, Sort sort);

	/**
	 * Tìm kiếm các đơn hàng có tên hoặc số điện thoại chứa từ khóa được cung cấp, với phân trang.
	 * 
	 * @param keyword Từ khóa để tìm kiếm trong tên hoặc số điện thoại.
	 * @param pageable Đối tượng phân trang và sắp xếp.
	 * @return Trang kết quả chứa các đơn hàng phù hợp với từ khóa.
	 */
	@Query("SELECT o FROM Order o WHERE o.name LIKE %:keyword% OR o.phoneNumber LIKE %:keyword%")
	Page<Order> searchOrders(@Param("keyword") String keyword, org.springframework.data.domain.Pageable pageable);

	/**
	 * Đếm số lượng đơn hàng dựa trên ID.
	 * 
	 * @param id ID của đơn hàng.
	 * @return Số lượng đơn hàng có ID tương ứng.
	 */
	public Long countById(Integer id);

	/**
	 * Tìm đơn hàng theo ID và lấy kèm thông tin chi tiết đơn hàng, bao gồm sản phẩm.
	 * 
	 * @param id ID của đơn hàng.
	 * @return Đối tượng Optional chứa đơn hàng và thông tin chi tiết nếu tìm thấy.
	 */
	@Query("SELECT o FROM Order o LEFT JOIN FETCH o.orderDetails od LEFT JOIN FETCH od.product WHERE o.id = :id")
	Optional<Order> findByIdWithDetails(@Param("id") Integer id);

	@Query("SELECT COUNT(o) FROM Order o")
    long getTotalOrders();

    @Query("SELECT SUM(o.totalCost) FROM Order o")
    Double getTotalSales();

    @Query("SELECT o.orderStatus, COUNT(o) FROM Order o GROUP BY o.orderStatus")
    List<Object[]> getOrderCountByStatus();

    @Query("SELECT DATE(o.orderTime), COUNT(o) FROM Order o GROUP BY DATE(o.orderTime) ORDER BY DATE(o.orderTime) DESC")
    List<Object[]> getOrderCountByDate();

    @Query(value = "SELECT COUNT(*) FROM orders WHERE order_time BETWEEN CURDATE() - INTERVAL (DAYOFWEEK(CURDATE())-1) DAY AND CURDATE()", nativeQuery = true)
    long getOrderCountCurrentWeek();

    @Query(value = "SELECT COUNT(*) FROM orders WHERE MONTH(order_time) = MONTH(CURDATE()) AND YEAR(order_time) = YEAR(CURDATE())", nativeQuery = true)
    long getOrderCountCurrentMonth();

    @Query(value = "SELECT SUM(total_cost) FROM orders WHERE MONTH(order_time) = MONTH(CURDATE()) AND YEAR(order_time) = YEAR(CURDATE())", nativeQuery = true)
    Double getTotalSalesCurrentMonth();

	// tôi muốn query doanh thu theo ngày
	@Query(value = "SELECT DATE(order_time), SUM(total_cost) FROM orders GROUP BY DATE(order_time) ORDER BY DATE(order_time) DESC", nativeQuery = true)
	List<Object[]> getTotalSalesByDate();
}