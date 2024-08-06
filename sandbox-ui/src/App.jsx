import { createBrowserRouter, RouterProvider } from "react-router-dom";
import Login from "./components/login/Login.jsx";
import Home from "./components/dashboard/Dashboard.jsx";
import { ThemeProvider } from "./components/theme/theme-provider";
import User from "./components/user/User.jsx";
import Configuration from "./components/configuration/Configuration.jsx";
import Workspace from "./components/workspace/Workspace.jsx";
import Product from "./components/product/Product.jsx";
const router = createBrowserRouter([
  {
    path: "/",
    element: <Home />,
  },
  {
    path: "/login",
    element: <Login />,
  },
  {
    path: "/workspaces",
    element: <Workspace />,
  },
  {
    path: "/products",
    element: <Product />,
  },
  {
    path: "/users",
    element: <User />,
  },
  {
    path: "/config",
    element: <Configuration />,
  },
]);
function App() {
  return (
    <div className="container">
      <ThemeProvider defaultTheme="dark" storageKey="vite-ui-theme">
        <main className="main">
          <RouterProvider router={router} />
        </main>
      </ThemeProvider>
    </div>
  );
}

export default App;
