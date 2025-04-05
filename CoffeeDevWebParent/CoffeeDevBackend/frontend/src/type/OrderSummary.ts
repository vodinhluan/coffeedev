
export type OrderStatusCount = {
    [key: string]: number;
  };
  
  export type TotalSalesByDate = {
    [date: string]: number;
  };
  
  export interface OrderSummary {
    totalOrders: number;
    totalSales: number;
    orderCountCurrentWeek: number;
    orderCountCurrentMonth: number;
    totalSalesCurrentMonth: number;
    orderStatusCount: OrderStatusCount;
    totalSalesByDate: TotalSalesByDate;
  }
  