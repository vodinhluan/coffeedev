import { Card, CardHeader, Typography, Box } from "@mui/material";
import { DollarSign, ShoppingBag, Calendar, BarChart, ClipboardList } from "lucide-react";

interface DashboardCardsProps {
  totalOrders: number;
  totalSales: number;
  orderCountCurrentWeek: number;
  orderCountCurrentMonth: number;
  totalSalesCurrentMonth: number;
}

const DashboardCards: React.FC<DashboardCardsProps> = ({
  totalOrders,
  totalSales,
  orderCountCurrentWeek,
  orderCountCurrentMonth,
  totalSalesCurrentMonth,
}) => {
  const cards = [
    {
      title: "Tổng số đơn hàng",
      value: totalOrders,
      icon: <ShoppingBag className="w-6 h-6 text-blue-500" />,
    },
    {
      title: "Tổng doanh thu",
      value: `$${totalSales.toFixed(2)}`,
      icon: <DollarSign className="w-6 h-6 text-green-500" />,
    },
    {
      title: "Đơn hàng tuần này",
      value: orderCountCurrentWeek,
      icon: <Calendar className="w-6 h-6 text-yellow-500" />,
    },
    {
      title: "Đơn hàng tháng này",
      value: orderCountCurrentMonth,
      icon: <ClipboardList className="w-6 h-6 text-orange-500" />,
    },
    {
      title: "Doanh thu tháng",
      value: `$${totalSalesCurrentMonth.toFixed(2)}`,
      icon: <BarChart className="w-6 h-6 text-purple-500" />,
    },
  ];

  return (
    <Box sx={{ display: "grid", gridTemplateColumns: "repeat(auto-fill, minmax(250px, 1fr))", gap: 2 }}>
      {cards.map((card, index) => (
        <Card key={index} sx={{ boxShadow: 3, transition: "all 0.3s ease", ":hover": { boxShadow: 6 } }}>
          <CardHeader
            sx={{ display: "flex", alignItems: "center", gap: 2 }}
            avatar={<Box sx={{ backgroundColor: "lightgray", padding: 2, borderRadius: "50%" }}>{card.icon}</Box>}
            title={<Typography variant="h6">{card.title}</Typography>}
            subheader={<Typography variant="h5" fontWeight="bold">{card.value}</Typography>}
          />
        </Card>
      ))}
    </Box>
  );
};

export default DashboardCards;
