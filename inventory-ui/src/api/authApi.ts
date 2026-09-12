import type { LoginRequest, LoginResponse } from "../types/auth";
import api from "./axios";

export const login = async (data: LoginRequest): Promise<LoginResponse> => {
  const response = await api.post<LoginResponse>("/api/auth/login", data);

  return response.data;
};
