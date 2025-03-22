export interface Ingredient {
    id: number;
    name: string;
    unit: string;
    quantity: number;
    minQuantity: number;
}

export interface IngredientLog {
    id: number;
    ingredientId: number;
    type: "import" | "export";
    quantity: number;
    createdBy: string;
    createdAt: string;
}