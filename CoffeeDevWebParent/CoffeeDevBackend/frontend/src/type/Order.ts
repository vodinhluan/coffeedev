export interface Order {
    id: number;
    name: string;
    phoneNumber: string;
    address: string;
    district?: string; 
    orderTime: string | Date;
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