import { atom } from "recoil";


export const userState = atom({
  key: "userState",
  default: {
    name: localStorage.getItem("username") || "",
    token: localStorage.getItem("token") || "",
  },
});


export const authState = atom({
    key: "authState",
    default: Boolean(localStorage.getItem("token")),
  });