import { useEffect, useState } from "react";
import Header from "../layout/Header";
import Logo from "../layout/Logo";
import Navbar from "../layout/Navbar";
import { getWorkspacesByQuery } from "../../services/workspaceService.js";

import {
  Card,
  CardContent,
  CardHeader,
  CardFooter,
} from "@/components/ui/card";

import TablePagination from "../shared/TablePagination";
import WorkspaceTableFilter from "./WorkspaceTableFilter";
import { useToast } from "@/components/ui/use-toast";
import { internalError } from "../../services/MessageConstant";

const page = {
  pageSize: 7,
  pageNumber: 0,
  totalPages: 0,
  totalElements: 0,
  searchQuery: "all",
  visibleQuery: "",
};

export default function Workspace() {
  const [workspaces, setWorkspaces] = useState([]);
  const [pageInfo, setPageInfo] = useState(page);
  const [isLoading, setIsLoading] = useState(true);
  const { toast } = useToast();

  useEffect(() => {
    const fetchWorkspaces = async (
      searchQuery,
      visibleQuery,
      page,
      pageSize
    ) => {
      try {
        setIsLoading(true);
        const data = await getWorkspacesByQuery(
          searchQuery,
          visibleQuery,
          page,
          pageSize
        );

        setWorkspaces(() => data.content);
        setPageInfo({
          searchQuery: pageInfo.searchQuery,
          visibleQuery: pageInfo.visibleQuery,
          pageSize: pageInfo.pageSize,
          pageNumber: pageInfo.pageNumber,
          totalPages: data.page.totalPages,
          totalElements: data.page.totalElements,
        });
      } catch (error) {
        toast(internalError);
        console.error("Error fetching workspaces:", error);
      } finally {
        setIsLoading(false);
      }
    };
    fetchWorkspaces(
      pageInfo.searchQuery,
      pageInfo.visibleQuery,
      pageInfo.pageNumber,
      pageInfo.pageSize
    );
  }, [
    pageInfo.searchQuery,
    pageInfo.visibleQuery,
    pageInfo.pageNumber,
    pageInfo.pageSize,
    toast,
  ]);

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
                <h1 className="text-lg font-semibold md:text-2xl">Workspace</h1>
                <p className="text-sm text-muted-foreground">
                  Recent workspaces from your sandbox.
                </p>
              </div>
            </CardHeader>
            <CardContent>
              <WorkspaceTableFilter
                isLoading={isLoading}
                workspaces={workspaces}
                pageInfo={pageInfo}
                setPageInfo={setPageInfo}
              />
            </CardContent>
            <CardFooter className="flex justify-between">
              <TablePagination pageInfo={pageInfo} changePage={setPageInfo} />
            </CardFooter>
          </Card>
        </main>
      </div>
    </div>
  );
}
