import React from "react";
import { BarChart, Bar, XAxis, YAxis, Tooltip, ResponsiveContainer, CartesianGrid } from "recharts";

interface SalesChartProps {
    totalSalesByDate: { [date: string]: number };
}

const SalesChart: React.FC<SalesChartProps> = ({ totalSalesByDate }) => {
    // Chuyển đổi dữ liệu từ object thành mảng { date, sales }
    const data = Object.entries(totalSalesByDate).map(([date, sales]) => ({
        date,
        sales,
    }));

    return (
        <div style={{ width: "100%", height: 300 }}>
            <h2 style={{ textAlign: "center", marginBottom: "10px" }}>Doanh thu theo ngày</h2>
            <ResponsiveContainer width="100%" height="100%">
                <BarChart data={data} margin={{ top: 20, right: 30, left: 20, bottom: 5 }}>
                    <CartesianGrid strokeDasharray="3 3" />
                    <XAxis dataKey="date" />
                    <YAxis />
                    <Tooltip />
                    <Bar dataKey="sales" fill="#4CAF50" barSize={40} />
                </BarChart>
            </ResponsiveContainer>
        </div>
    );
};

export default SalesChart;
