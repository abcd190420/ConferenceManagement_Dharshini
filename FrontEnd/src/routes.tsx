import Auth from "./component/pages/Auth";
import DashboardAdmin from "./component/pages/DashboardAdmin";
import DashboardUser from "./component/pages/DashboardUser";
import { Navigate } from "react-router-dom";

const routes = [
  {
    path: "/",
    children: [
      {
        path: "dashboardadmin",
        element: <DashboardAdmin />,
      },
      {
        path: "dashboarduser",
        element: <DashboardUser />,
      },
      {
        path: "auth",
        element: <Auth />,
      },
      {
        index: true,
        element: <Navigate to="/auth" />,
      },
    ],
  },
  {
    path: "*",
    element: <Navigate to="/auth" />,
  },
];

export default routes;
