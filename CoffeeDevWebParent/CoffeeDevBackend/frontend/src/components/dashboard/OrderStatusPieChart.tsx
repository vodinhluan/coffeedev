import { PieChart, Pie, Cell, Tooltip, Legend, ResponsiveContainer } from "recharts";
import { Card, CardContent, Typography } from "@mui/material";

interface OrderStatusPieChartProps {
  orderStatusCount: { [key: string]: number };
}

// 🎨 Màu sắc cho từng trạng thái
const COLORS = ["#0088FE", "#FF8042", "#00C49F", "#FFBB28", "#FF4560", "#6A0DAD"];

const OrderStatusPieChart: React.FC<OrderStatusPieChartProps> = ({ orderStatusCount }) => {
  // Chuyển đổi dữ liệu từ object sang array để sử dụng với PieChart
  const data = Object.entries(orderStatusCount).map(([status, count]) => ({
    name: status,
    value: count,
  }));

  return (
    <Card sx={{ boxShadow: 3, p: 2 }}>
      <CardContent>
        <Typography variant="h6" textAlign="center" fontWeight="bold">
          Trạng thái đơn hàng
        </Typography>
        <ResponsiveContainer width="100%" height={300}>
          <PieChart>
            <Pie
              data={data}
              cx="50%"
              cy="50%"
              outerRadius={100}
              dataKey="value"
              label
            >
              {data.map((_, index) => (
                <Cell key={`cell-${index}`} fill={COLORS[index % COLORS.length]} />
              ))}
            </Pie>
            <Tooltip />
            <Legend verticalAlign="bottom" height={36} />
          </PieChart>
        </ResponsiveContainer>
      </CardContent>
    </Card>
  );
};

export default OrderStatusPieChart;
