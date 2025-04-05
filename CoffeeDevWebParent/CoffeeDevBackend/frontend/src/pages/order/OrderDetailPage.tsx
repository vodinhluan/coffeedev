import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import useFetchData from "../../useFetchData";
import { Order, OrderDetail } from "../../type/Order";
import { updateOrder } from "../../api/orderService";


const OrderDetailPage = () => {
    const { id } = useParams<{ id: string }>();
    const navigate = useNavigate();

    const { data: order, loading, error } = useFetchData<Order>(`http://localhost:8082/CoffeeDev/api/orders/${id}`);
    const [formData, setFormData] = useState<Order | null>(null);

    useEffect(() => {
        if (order) setFormData(order);
    }, [order]);

    const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement | HTMLTextAreaElement>) => {
        if (!formData) return;
        const { name, value } = e.target;
        setFormData({
            ...formData,
            [name]: value,
        });
    };

    // Xử lý cập nhật trạng thái đơn hàng
    const handleUpdate = async () => {
        if (!formData) return;
        try {
            const updatedOrder = await updateOrder(formData.id, {
                name: formData.name,
                phoneNumber: formData.phoneNumber,
                address: formData.address,
                district: formData.district ?? "",
                orderTime: formData.orderTime, 
                totalCost: formData.totalCost,
                paymentMethod: formData.paymentMethod,
                orderStatus: formData.orderStatus,
                customerId: formData.customerId,
                orderDetails: formData.orderDetails.map(detail => ({
                    id: detail.id,
                    quantity: detail.quantity,
                    productCost: detail.productCost,
                    shippingCost: detail.shippingCost,
                    subtotalCost: detail.subtotalCost,
                    totalCost: detail.totalCost,
                    productId: detail.productId,
                    productName: detail.productName,
                })),
            });

            setFormData(updatedOrder.data);
            alert("Cập nhật đơn hàng thành công");
        } catch (error) {
            console.log("orderTime: ", formData?.orderTime);
            console.error("Lỗi cập nhật:", error);
            alert("Cập nhật thất bại");
        }
    };



    // Tính tổng phí tạm tính (subtotalCost của tất cả sản phẩm)
    const totalSubtotalCost = formData?.orderDetails?.reduce(
        (sum, item) => sum + item.subtotalCost,
        0
    ) || 0;

    // Lấy giá trị shippingCost từ orderDetails (giả sử chỉ có 1 giá trị)
    const shippingCost = formData?.orderDetails[0]?.shippingCost || 0;

    // Tính tổng đơn hàng bao gồm cả phí giao hàng
    const finalTotalCost = (totalSubtotalCost || 0) + shippingCost;


    if (loading) return <div className="text-center py-10">Loading...</div>;
    if (error) return <div className="text-center py-10 text-red-500">Error: {error}</div>;
    if (!formData) return <div className="text-center py-10">No order data found.</div>;

    return (
        <div className="flex min-h-screen bg-gray-100">
            <div className="flex-1 flex justify-center items-center p-10">
                <div className="bg-white shadow-lg rounded-lg p-8 w-full max-w-4xl">
                    <h1 className="text-3xl font-bold text-center text-gray-800 mb-6">CHI TIẾT ĐƠN HÀNG</h1>

                    <div className="grid grid-cols-2 gap-6">
                        {/* Thông tin cơ bản */}
                        <div>
                            <label className="block text-gray-700 font-medium">ID</label>
                            <input type="text" name="id" value={formData.id} disabled className="input-field bg-gray-200" />
                        </div>
                        <div>
                            <label className="block text-gray-700 font-medium">Tên khách hàng</label>
                            <input type="text" name="name" value={formData.name} onChange={handleChange} className="input-field" />
                        </div>
                        <div>
                            <label className="block text-gray-700 font-medium">Số điện thoại</label>
                            <input type="text" name="phoneNumber" value={formData.phoneNumber} onChange={handleChange} className="input-field" />
                        </div>
                        <div>
                            <label className="block text-gray-700 font-medium">Địa chỉ</label>
                            <textarea
                                name="address"
                                value={formData.address}
                                onChange={handleChange}
                                className="input-field"
                                rows={4}
                                placeholder="Nhập địa chỉ chi tiết"
                            />
                        </div>
                        <div>
                            <label className="block text-gray-700 font-medium">Quận</label>
                            <input type="text" name="district" value={(formData).district || ""} onChange={handleChange} className="input-field" />
                        </div>
                        <div>
                            <label className="block text-gray-700 font-medium">Thời gian đặt hàng</label>
                            <input
                                type="text"
                                name="orderTime"
                                value={new Date(formData.orderTime).toISOString().replace("Z", "+00:00")}
                                disabled
                                className="input-field bg-gray-200"
                            />                        </div>


                        <div>
                            <label className="block text-gray-700 font-medium">Tổng chi phí</label>
                            <input type="text" name="totalCost" value={formData.totalCost + ".000"} disabled className="input-field bg-gray-200" />
                        </div>
                        <div>
                            <label className="block text-gray-700 font-medium">Phương thức thanh toán</label>
                            <select name="paymentMethod" value={formData.paymentMethod} onChange={handleChange} className="input-field">
                                <option value="COD">COD</option>
                                <option value="CHUYENKHOAN">CHUYỂN KHOẢN</option>
                            </select>
                        </div>
                        <div>
                            <label className="block text-gray-700 font-medium">Trạng thái đơn hàng</label>
                            <select name="orderStatus" value={formData.orderStatus} onChange={handleChange} className="input-field">
                                <option value="NEW">NEW</option>
                                <option value="PROCESSING">PROCESSING</option>
                                <option value="PICKED">PICKED</option>
                                <option value="PAID">PAID</option>
                                <option value="CANCELLED">CANCELLED</option>
                                <option value="DELIVERED">DELIVERED</option>
                                <option value="REFUNDED">REFUNDED</option>
                            </select>
                        </div>
                        <div>
                            <label className="block text-gray-700 font-medium">ID Khách hàng</label>
                            <input type="text" name="customerId" value={formData.customerId} disabled className="input-field bg-gray-200" />
                        </div>
                    </div>

                    {/* Bảng hiển thị Order Details */}
                    <div className="mt-8">
                        <h2 className="text-2xl font-bold text-gray-800 mb-4 text-center">THANH TOÁN</h2>
                        {formData.orderDetails && formData.orderDetails.length > 0 ? (
                            <div className="bg-white p-4 rounded shadow-md">
                                <table className="min-w-full border-collapse border border-gray-300">
                                    <thead>
                                        <tr className="bg-gray-100">
                                            <th className="py-2 px-4 border">Sản phẩm</th>
                                            <th className="py-2 px-4 border">Số lượng</th>
                                            <th className="py-2 px-4 border">Giá SP</th>
                                            <th className="py-2 px-4 border">Tạm tính</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        {formData.orderDetails.map((item: OrderDetail) => (
                                            <tr key={item.id} className="hover:bg-gray-50">
                                                <td className="py-2 px-4 border">{item.productName}</td>
                                                <td className="py-2 px-4 border text-center">{item.quantity}</td>
                                                <td className="py-2 px-4 border text-right">{item.productCost}.000</td>
                                                <td className="py-2 px-4 border text-right">{item.subtotalCost}.000</td>
                                            </tr>
                                        ))}
                                    </tbody>
                                </table>

                                {/* Phần tổng kết đơn hàng */}
                                <div className="mt-4 p-4 bg-gray-100 rounded">
                                    <div className="flex justify-between items-center mb-2">
                                        <span className="font-medium">Tổng tạm tính:</span>
                                        <span>{totalSubtotalCost}.000</span>
                                    </div>
                                    <div className="flex justify-between items-center mb-2">
                                        <span className="font-medium">Phí giao hàng:</span>
                                        <span>{shippingCost}.000</span>
                                    </div>
                                    <div className="flex justify-between items-center text-lg font-semibold text-red-600">
                                        <span>Tổng đơn hàng:</span>
                                        <span>{finalTotalCost}.000</span>
                                    </div>
                                </div>
                            </div>
                        ) : (
                            <p>Không có chi tiết đơn hàng nào.</p>
                        )}

                    </div>

                    {/* Nút hành động */}
                    <div className="mt-6 flex justify-end space-x-4">
                        <button
                            className="bg-green-500 hover:bg-green-600 text-white font-medium py-2 px-6 rounded-lg"
                            onClick={handleUpdate}
                        >
                            Cập nhật
                        </button>
                        <button className="bg-gray-500 hover:bg-gray-600 text-white font-medium py-2 px-6 rounded-lg" onClick={() => navigate("/orders")}>
                            Huỷ
                        </button>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default OrderDetailPage;
