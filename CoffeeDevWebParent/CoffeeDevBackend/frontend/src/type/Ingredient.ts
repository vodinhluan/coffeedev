export interface Ingredient {
    id: number;
    name: string;
    unit: string;
    quantity: number;
    minQuantity: number;
}

export interface IngredientLog {
    id: number;
    ingredientName: string;
    type: "IMPORT" | "EXPORT";
    quantity: number;
    createdBy: string;
    createdAt: string;
}