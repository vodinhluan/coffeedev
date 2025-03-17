export interface Order {
    id: number;
    name: string;
    phoneNumber: string;
    address: string;
    orderTime: Date;
    totalCost: number;  
    paymentMethod: string;
    orderStatus: string;
    customerId: number;
    orderDetails: OrderDetail[];
}

export interface OrderDetail {
    id: number;
    quantity: number;
    productCost: number;
    shippingCost: number;
    subtotalCost: number;
    totalCost: number;
    productId: number;
    productName: string;
}

export interface OrderTrack {
    id: number;
    notes: string;
    updatedTime: Date;
    status: string;
    orderId: number;
}