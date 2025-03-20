export interface Product {
    id: number;
    name: string;
    alias: string;
    description: string;
    price: number;
    image: string;
    enabled: boolean;
    categoryId: number;

    // categoryName: string;
    // createdTime: Date;
    // updatedTime: Date;
    // createdBy: string;
    // updatedBy: string;
}