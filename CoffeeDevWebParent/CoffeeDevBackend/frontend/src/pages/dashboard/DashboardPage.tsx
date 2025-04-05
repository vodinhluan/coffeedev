import React, { useEffect, useState } from "react";
import { Container, Grid, CircularProgress, Typography } from "@mui/material";
import { OrderSummary } from "../../type/OrderSummary";
import { getOrderSummary } from "../../api/dashBoardService";
import DashboardCards from "../../components/dashboard/DashboardCards";
import OrderStatusPieChart from "../../components/dashboard/OrderStatusPieChart";
import SalesChart from "../../components/dashboard/SalesChart";
// import RecentOrdersTable from "../../components/dashboard/RecentOrdersTable";


const DashboardPage: React.FC = () => {
  const [orderSummary, setOrderSummary] = useState<OrderSummary | null>(null);
  const [loading, setLoading] = useState<boolean>(true);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const data = await getOrderSummary();
        setOrderSummary(data);
      } catch (error) {
        console.error("Lỗi khi lấy dữ liệu dashboard:", error);
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, []);

  if (loading) {
    return (
      <Container sx={{ display: "flex", justifyContent: "center", alignItems: "center", height: "80vh" }}>
        <CircularProgress />
      </Container>
    );
  }

  if (!orderSummary) {
    return (
      <Container sx={{ textAlign: "center", marginTop: 4 }}>
        <Typography variant="h6" color="error">
          Không có dữ liệu thống kê đơn hàng!
        </Typography>
      </Container>
    );
  }

  return (
    <Container sx={{ mt: 4 }}>
      <Typography variant="h4" gutterBottom>
        <h1 className="text-3xl font-bold">🏠 Dashboard</h1>
      </Typography>

      {/* Thẻ tổng quan */}
      <Grid container spacing={3}>
        <Grid size={{ xs: 12 }}>
          <DashboardCards
            totalOrders={orderSummary.totalOrders}
            totalSales={orderSummary.totalSales}
            orderCountCurrentWeek={orderSummary.orderCountCurrentWeek}
            orderCountCurrentMonth={orderSummary.orderCountCurrentMonth}
            totalSalesCurrentMonth={orderSummary.totalSalesCurrentMonth}
          />
        </Grid>

        {/* Biểu đồ trạng thái đơn hàng & Biểu đồ doanh thu */}
        <Grid size={{ xs: 12, md: 6 }} >
          <OrderStatusPieChart orderStatusCount={orderSummary.orderStatusCount} />
        </Grid>
        <Grid size={{ xs: 12, md: 6 }} >
          <SalesChart totalSalesByDate={orderSummary.totalSalesByDate} />
        </Grid>

        {/* Danh sách đơn hàng gần đây */}
        {/* <Grid size={{ xs: 12 }}>
          <RecentOrdersTable recentOrders={[]} />
        </Grid> */}
      </Grid>
    </Container>
  );
};

export default DashboardPage;
