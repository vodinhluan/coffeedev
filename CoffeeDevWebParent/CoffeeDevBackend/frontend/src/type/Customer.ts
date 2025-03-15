export interface Customer {
    id: number;
    email: string;
    name: string;
    phoneNumber: string;
    address: string;
    enabled: boolean;
    createdTime: Date;
    authenticationType: string;
    districtId: number;
    districtName: string;
}