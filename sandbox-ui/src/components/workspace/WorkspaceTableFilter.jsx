import { Button } from "../ui/button";
import React, { useCallback } from "react";
import { PlusCircle, File } from "lucide-react";
import WorkspaceTable from "./WorkspaceTable.jsx";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";

const MemoizedWorkspaceTableFiltred = ({
  isLoading,
  workspaces,
  pageInfo,
  setPageInfo,
}) => {
  const selectQuery = useCallback(
    (query) => {
      setPageInfo({
        ...pageInfo,
        searchQuery: query,
        pageSize: pageInfo.pageSize,
        pageNumber: 0,
      });
    },
    [setPageInfo, pageInfo]
  );
  return (
    <>
      {isLoading ? (
        <p>Loading workspaces...</p>
      ) : (
        <Tabs defaultValue={pageInfo.searchQuery}>
          <div className="flex items-center">
            <TabsList>
              <TabsTrigger value="all" onClick={() => selectQuery("all")}>
                All
              </TabsTrigger>
              <TabsTrigger value="active" onClick={() => selectQuery("active")}>
                Active
              </TabsTrigger>
              <TabsTrigger
                value="inactive"
                onClick={() => selectQuery("inactive")}
              >
                Inactive
              </TabsTrigger>
            </TabsList>
            <div className="ml-auto flex items-center gap-2">
              <Button size="sm" variant="outline" className="h-8 gap-1">
                <File className="h-3.5 w-3.5" />
                <span className="sr-only sm:not-sr-only sm:whitespace-nowrap">
                  Export
                </span>
              </Button>
              <Button size="sm" className="h-8 gap-1">
                <PlusCircle className="h-3.5 w-3.5" />
                <span className="sr-only sm:not-sr-only sm:whitespace-nowrap">
                  Add Workspace
                </span>
              </Button>
            </div>
          </div>
          <TabsContent value={pageInfo.searchQuery}>
            <WorkspaceTable isLoading={isLoading} workspaces={workspaces} />
          </TabsContent>
        </Tabs>
      )}
    </>
  );
};

const WorkspaceTableFiltred = React.memo(MemoizedWorkspaceTableFiltred);
export default WorkspaceTableFiltred;
