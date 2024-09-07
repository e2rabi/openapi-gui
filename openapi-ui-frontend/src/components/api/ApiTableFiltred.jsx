import { useState } from "react";
import { Button } from "../ui/button";
import { PlusCircle, File } from "lucide-react";
import ApiTable from "./ApiTable.jsx";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import { useNavigate } from "react-router-dom";

const ApiTableFiltred = ({ isLoading, apis }) => {
  const navigate = useNavigate();

  const [filter, setFilter] = useState("all");
  return (
    <>
      {isLoading ? (
        <p>Loading APIs...</p>
      ) : (
        <Tabs defaultValue="all">
          <div className="flex items-center">
            <TabsList>
              <TabsTrigger value="all" onClick={() => setFilter("all")}>
                All
              </TabsTrigger>
              <TabsTrigger value="active" onClick={() => setFilter("active")}>
                Active
              </TabsTrigger>
              <TabsTrigger
                value="disabled"
                onClick={() => setFilter("disabled")}
              >
                Disabled
              </TabsTrigger>
              <TabsTrigger
                value="expired"
                onClick={() => setFilter("expired")}
                className="hidden sm:flex"
              >
                Expired
              </TabsTrigger>
            </TabsList>
            <div className="ml-auto flex items-center gap-2">
              <Button size="sm" variant="outline" className="h-8 gap-1">
                <File className="h-3.5 w-3.5" />
                <span className="sr-only sm:not-sr-only sm:whitespace-nowrap">
                  Export
                </span>
              </Button>
              <Button size="sm" className="h-8 gap-1" onClick={() => navigate("/api-builder")}>
                <PlusCircle className="h-3.5 w-3.5" />
                <span className="sr-only sm:not-sr-only sm:whitespace-nowrap">
                  Add API
                </span>
              </Button>
            </div>
          </div>
          <TabsContent value={filter}>
            <ApiTable isLoading={isLoading} apis={apis} filtreBy={filter} />
          </TabsContent>
        </Tabs>
      )}
    </>
  );
};

export default ApiTableFiltred;
