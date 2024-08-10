import { useState, useEffect } from "react";
import Header from "../layout/Header";
import Logo from "../layout/Logo";
import Navbar from "../layout/Navbar";
import { getAllModules } from "../../services/moduleService.js";
import {
  Card,
  CardContent,
  CardFooter,
  CardHeader,
} from "@/components/ui/card";

import TablePagination from "../shared/TablePagination";
import ModuleTableFiltred from "./ModuleTableFiltred.jsx";
const page = {
  pageSize: 7,
  pageNumber: 0,
};
export default function Module() {
  const [modules, setModules] = useState([]);
  const [pageInfo, setPageInfo] = useState(page);
  const [totalPages, setTotalPages] = useState(0);
  const [totalElements, setTotalElements] = useState(0);
  const [isLoading, setIsLoading] = useState(true);
  useEffect(() => {
    fetchModules(pageInfo.pageNumber, pageInfo.pageSize);
  }, [pageInfo.pageNumber, pageInfo.pageSize]);

  const fetchModules = async (page, pageSize) => {
    try {
      const data = await getAllModules(page, pageSize);
      setModules(() => data.content);
      setTotalPages(() => data.page.totalPages);
      setTotalElements(() => data.page.totalElements);
    } catch (error) {
      console.error("Error fetching modules:", error);
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
                <h1 className="text-lg font-semibold md:text-2xl">Modules</h1>
                <p className="text-sm text-muted-foreground">
                  Recent modules from your sandbox.
                </p>
              </div>
            </CardHeader>
            <CardContent>
              <ModuleTableFiltred isLoading={isLoading} modules={modules} />
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
