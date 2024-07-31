
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import Login from "./components/login/Login.jsx";
import Home from "./components/dashboard/Dashboard.jsx";
import { ThemeProvider } from "./components/theme/theme-provider"
const router = createBrowserRouter([
  {
    path: "/",
    element: <Home />,
  },
  {
    path: "/login",
    element: <Login />,
  },
]);
function App() {

  return (
    <div className="container">
      <ThemeProvider defaultTheme="dark" storageKey="vite-ui-theme">
        <main className="main" >
          <RouterProvider router={router} />
        </main>
      </ThemeProvider>
    </div>

  )
}

export default App
