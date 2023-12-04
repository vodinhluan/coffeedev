//package com.coffeedev.order;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.coffeedev.checkout.CheckoutInfo;
//import com.coffeedev.common.entity.CartItem;
//import com.coffeedev.common.entity.Customer;
//import com.coffeedev.common.entity.District;
//import com.coffeedev.common.entity.Order;
//import com.coffeedev.common.entity.PaymentMethod;
//
//@RestController
//@RequestMapping("/api/orders")
//public class OrderController {
//
//    @Autowired
//    private OrderService orderService;
//
//    @PostMapping("/create")
//    public ResponseEntity<String> createOrder(@RequestBody CheckoutInfo checkoutInfo) {
//        try {
//
//            Customer customer = checkoutInfo.getCustomer();
//            District district = checkoutInfo.getDistrict();
//            List<CartItem> cartItems = checkoutInfo.getCartItems();
//            PaymentMethod paymentMethod = checkoutInfo.getPaymentMethod();
//
//            // Gọi service để tạo đơn hàng
//            Order savedOrder = orderService.createOrder(customer, district, cartItems, paymentMethod, checkoutInfo);
//
//            // Trả về thông điệp hoặc ID của đơn hàng mới tạo
//            return ResponseEntity.ok("Order created successfully with ID: " + savedOrder.getId());
//        } catch (Exception e) {
//            // Xử lý exception nếu có
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating order");
//        }
//    }
//
//}
//
