import {
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow,
    Paper,
    Typography,
    Chip,
} from "@mui/material";

interface Order {
    id: number;
    customerName: string;
    status: string;
    totalPrice: number;
    orderDate: string;
}

interface RecentOrdersTableProps {
    recentOrders: Order[];
}

const getStatusColor = (status: string) => {
    switch (status) {
        case "NEW":
            return "primary";
        case "PROCESSING":
            return "warning";
        case "DELIVERED":
            return "success";
        case "CANCELLED":
            return "error";
        case "PAID":
            return "secondary";
        default:
            return "default";
    }
};

const RecentOrdersTable: React.FC<RecentOrdersTableProps> = ({ recentOrders }) => {
    return (
        <TableContainer component={Paper} sx={{ boxShadow: 3 }}>
            <Typography variant="h6" textAlign="center" fontWeight="bold" sx={{ p: 2 }}>
                Đơn hàng gần đây
            </Typography>
            <Table>
                <TableHead>
                    <TableRow>
                        <TableCell><strong>ID</strong></TableCell>
                        <TableCell><strong>Khách hàng</strong></TableCell>
                        <TableCell><strong>Trạng thái</strong></TableCell>
                        <TableCell><strong>Giá trị đơn hàng</strong></TableCell>
                        <TableCell><strong>Ngày đặt hàng</strong></TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {recentOrders.map((order) => (
                        <TableRow key={order.id}>
                            <TableCell>{order.id}</TableCell>
                            <TableCell>{order.customerName}</TableCell>
                            <TableCell>
                                <Chip label={order.status} color={getStatusColor(order.status)} />
                            </TableCell>
                            <TableCell>{`$${order.totalPrice.toFixed(2)}`}</TableCell>
                            <TableCell>{order.orderDate}</TableCell>
                        </TableRow>
                    ))}
                </TableBody>
            </Table>
        </TableContainer>
    );
};

export default RecentOrdersTable;
