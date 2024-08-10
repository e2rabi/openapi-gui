import { useState, useEffect } from "react";
import Header from "../layout/Header";
import Logo from "../layout/Logo";
import Navbar from "../layout/Navbar";
import { getAllSolutions } from "../../services/solutionsService.js";
import {
  Card,
  CardContent,
  CardFooter,
  CardHeader,
} from "@/components/ui/card";

import TablePagination from "../shared/TablePagination";
import SolutionTableFiltred from "./SolutionTableFiltred.jsx";
const page = {
  pageSize: 7,
  pageNumber: 0,
};
export default function Solution() {
  const [solutions, setSolutions] = useState([]);
  const [pageInfo, setPageInfo] = useState(page);
  const [totalPages, setTotalPages] = useState(0);
  const [totalElements, setTotalElements] = useState(0);
  const [isLoading, setIsLoading] = useState(true);
  useEffect(() => {
    fetchSolutions(pageInfo.pageNumber, pageInfo.pageSize);
  }, [pageInfo.pageNumber, pageInfo.pageSize]);

  const fetchSolutions = async (page, pageSize) => {
    try {
      const data = await getAllSolutions(page, pageSize);
      setSolutions(() => data.content);
      setTotalPages(() => data.page.totalPages);
      setTotalElements(() => data.page.totalElements);
    } catch (error) {
      console.error("Error fetching solutions:", error);
    } finally {
      setIsLoading(false);
    }
  };

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
              <div>
                <h1 className="text-lg font-semibold md:text-2xl">Solution</h1>
                <p className="text-sm text-muted-foreground">
                  Recent solution from your sandbox.
                </p>
              </div>
            </CardHeader>
            <CardContent>
              <SolutionTableFiltred
                isLoading={isLoading}
                solutions={solutions}
              />
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
