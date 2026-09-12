import { createBrowserRouter } from "react-router-dom";
import LoginPage from "../modules/auth/pages/LoginPage";
import DashboardPage from "../modules/dashboard/pages/DashboardPage";

const router = createBrowserRouter([
  {
    path: "/login",
    element: <LoginPage />,
  },
  {
    path: "/",
    element: <DashboardPage />,
  },
  // {
  //   path: "/users",
  //   element: <UserList />,
  // },
  // {
  //   path: "/users/create",
  //   element: <UserCreate />,
  // },
  // {
  //   path: "/users/:id/edit",
  //   element: <UserEdit />,
  // },
  // {
  //   path: "/products",
  //   element: <ProductList />,
  // },
  // {
  //   path: "/suppliers",
  //   element: <SupplierList />,
  // },
  // {
  //   path: "/locations",
  //   element: <LocationList />,
  // },
  // {
  //   path: "/inventory",
  //   element: <InventoryList />,
  // },
]);

export default router;
