import { createContext, useContext } from "react";

export interface User {
  id: string;
  name: string;
  email: string;
  roles: string[];
}

export interface AuthContextType {
  isAuthenticated: boolean;
  user: User | null;
  token: string | null;
}

export const AuthContext = createContext<AuthContextType | null>(null);

export const useAuth = () => {
  const ctx = useContext(AuthContext);
  if (!ctx) throw new Error("useAuth must be used within AuthProvider");
  return ctx;
};
