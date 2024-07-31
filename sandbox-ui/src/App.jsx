
import Header from "./components/layout/Header/Header.jsx"
import Footer from "./components/layout/Footer/Footer.jsx"
import Navbar from "./components/layout/Navbar/Navbar.jsx"
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import Home from "./components/home/Home.jsx";
import Login from "./components/login/Login.jsx";
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
      <Header />
      <Navbar />
      <main className="main" >
        <RouterProvider router={router} />
      </main>
      <Footer />
    </div>

  )
}

export default App
