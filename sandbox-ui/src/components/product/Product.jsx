import { useEffect, useState } from "react";
import Header from "../layout/Header";
import Logo from "../layout/Logo";
import Navbar from "../layout/Navbar";
import { getAllProducts } from "../../services/productService.js";

import {
  Card,
  CardContent,
  CardHeader,
  CardFooter,
} from "@/components/ui/card";

import TablePagination from "../shared/TablePagination";
import ProductTable from "./ProductTable";
import ProductTableFilter from "./ProductTableFilter.jsx";

const page = {
  pageSize: 10,
  pageNumber: 0,
};

export default function Product() {
  const [products, setProducts] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [pageInfo, setPageInfo] = useState(page);
  const [totalPages, setTotalPages] = useState(0);
  const [totalElements, setTotalElements] = useState(0);

  const fetchProducts = async (page, pageSize) => {
    try {
      const data = await getAllProducts(page, pageSize);
      setProducts(() => data.content);
      setTotalPages(() => data.page.totalPages);
      setTotalElements(() => data.page.totalElements);
    } catch (error) {
      console.error("Error fetching products:", error);
    } finally {
      setIsLoading(false);
    }
  };

  useEffect(() => {
    fetchProducts(pageInfo.pageNumber, pageInfo.pageSize);
  }, [pageInfo.pageNumber, pageInfo.pageSize]);

  return (
    <div className="grid min-h-screen w-full md:grid-cols-[220px_1fr] lg:grid-cols-[280px_1fr]">
      <div className="hidden border-r bg-muted/40 md:block">
        <div className="flex h-full max-h-screen flex-col gap-2">
          <Logo />
          <Navbar />
        </div>
      </div>
      <div className="flex flex-col">
        <Header />
        <main className="flex flex-1 flex-col gap-4 p-4 lg:gap-6 lg:p-6">
          <Card
            x-chunk="dashboard-06-chunk-0"
            className=" rounded-lg border border-dashed shadow-sm"
          >
            <CardHeader>
              <ProductTableFilter />
            </CardHeader>
            <CardContent>
              <ProductTable isLoading={isLoading} products={products} />
            </CardContent>
            <CardFooter className="flex justify-between">
              <TablePagination
                pageInfo={pageInfo}
                changePage={setPageInfo}
                totalPages={totalPages}
                totalElements={totalElements}
              />
            </CardFooter>
          </Card>
        </main>
      </div>
    </div>
  );
}