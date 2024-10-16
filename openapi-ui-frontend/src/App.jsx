import { createBrowserRouter, RouterProvider } from "react-router-dom";
import Login from "./components/login/Login.jsx";
import Home from "./components/dashboard/Dashboard.jsx";
import { ThemeProvider } from "./components/theme/theme-provider";
import User from "./components/user/User.jsx";
import Configuration from "./components/configuration/Configuration.jsx";
import Workspace from "./components/workspace/Workspace.jsx";
import Product from "./components/product/Product.jsx";
import Release from "./components/release/Release.jsx";
import Solution from "./components/solution/Solution.jsx";
import Module from "./components/module/Module.jsx";
import Api from "./components/api/Api.jsx";
import { Toaster } from "@/components/ui/toaster"
import {
  TooltipProvider,
} from "@/components/ui/tooltip"
import CreateApi from "./components/api/CreateApi.jsx";
import { store } from './store'
import { Provider } from 'react-redux'
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
    path: "/releases",
    element: <Release />,
  },
  {
    path: "/users",
    element: <User />,
  },
  {
    path: "/solutions",
    element: <Solution />,
  },
  {
    path: "/modules",
    element: <Module />,
  },
  {
    path: "/apis",
    element: <Api />,
  },
  {
    path: "/config",
    element: <Configuration />,
  },
  {
    path: "/api-builder",
    element: <CreateApi />,
  },
]);
function App() {
  return (
    <div className="container">
      <Provider store={store}>
        <ThemeProvider defaultTheme="dark" storageKey="vite-ui-theme">
          <TooltipProvider>
            <main className="main">
              <RouterProvider router={router} />
            </main>
            <Toaster />
          </TooltipProvider>
        </ThemeProvider>
      </Provider>
    </div>
  );
}

export default App;
