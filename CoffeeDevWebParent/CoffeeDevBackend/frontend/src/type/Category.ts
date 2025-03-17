export interface Category {
    id: number;
    name: string;
    image: string;
    enabled: boolean;
    parent: Category;
    children: Category[];
}