import { useEffect, useState } from "react";
import Header from "../layout/Header";
import Logo from "../layout/Logo";
import Navbar from "../layout/Navbar";

import {
  Card,
  CardContent,
  CardHeader,
  CardFooter,
} from "@/components/ui/card";

import WorkspaceTable from "./WorkspaceTable";
import { fetchApi } from "@/services/apiService";
import WorkspaceTableFilter from "./WorkspaceTableFilter";
import WorkspaceTablePagination from "./WorkspaceTablePagination";

export default function Workspace() {
  const [workspaces, setWorkspaces] = useState([]);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    const loadWorkspaces = async () => {
      try {
        const workspacesData = await fetchApi("workspaces");
        setWorkspaces(workspacesData);
      } catch (error) {
        console.error("Failed to load workspaces:", error);
      } finally {
        setIsLoading(false);
      }
    };

    loadWorkspaces();
  }, []);

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
              <WorkspaceTableFilter />
            </CardHeader>
            <CardContent>
              <WorkspaceTable isLoading={isLoading} workspaces={workspaces} />
            </CardContent>
            <CardFooter className="flex justify-between">
              <WorkspaceTablePagination />
            </CardFooter>
          </Card>
        </main>
      </div>
    </div>
  );
}
