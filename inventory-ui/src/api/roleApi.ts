import type { Role } from "../types/user";
import api from "./axios";

export const getRoles = async (): Promise<Role[]> => {
  const response = await api.get<Role[]>("/api/roles");

  return response.data;
};

