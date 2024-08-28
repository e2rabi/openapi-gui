import { useReducer } from "react";
import Header from "../layout/Header";
import Logo from "../layout/Logo";
import Navbar from "../layout/Navbar";


import {
  Card,
  CardContent,
  CardFooter,
  CardHeader,
} from "@/components/ui/card";
import {
  ResizableHandle,
  ResizablePanel,
  ResizablePanelGroup,
} from "@/components/ui/resizable"
import { Button } from "@/components/ui/button"
import { FileCheck, ScanEye, PlusCircle, ChevronsLeftRight } from "lucide-react"
import OpenApiSelector from "./OpenApiSelector";
import { openApiReducer, initialState } from "../../services/reducers/OpenApiReducer";
import InfoObject from "./InfoObject";
import ServersObject from "./ServersObject";
import Schema from "../openapi/Schema";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs"

export default function CreateApi() {

  const [state, dispatch] = useReducer(openApiReducer, initialState);
  const renderPanelContent = () => {
    switch (state.currentStep) {
      case 'info':
        return <InfoObject />;
      case 'servers':
        return <ServersObject />;
      default:
        return <InfoObject />;
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
            className=" rounded-lg border border-dashed shadow-sm h-full"
          >
            <CardHeader>
              <div>
                <h1 className="text-lg font-semibold md:text-2xl">Create new API</h1>
                <p className="text-sm text-muted-foreground">
                  Recent APIs from your sandbox.
                </p>
              </div>
            </CardHeader>
            <CardContent className="h-4/5">
              <Tabs defaultValue="account" className="w-full">
                <TabsList>
                  <TabsTrigger value="account">
                    <ChevronsLeftRight className="h-4 w-4" />
                    <span className="ml-1">Schema</span>
                  </TabsTrigger>
                  <TabsTrigger value="password">Password</TabsTrigger>
                </TabsList>
                <TabsContent value="account">
                  <ResizablePanelGroup
                    direction="horizontal"
                    className="max-w-full rounded-lg border"
                  >
                    <ResizablePanel defaultSize={25}>
                      {/* <OpenApiSelector state={state} dispatch={dispatch} /> */}
                    </ResizablePanel>
                    <ResizableHandle withHandle />
                    <ResizablePanel defaultSize={75}>
                      <Schema />
                      <Schema />
                      <Schema />
                      <Schema />
                      <Schema />
                    </ResizablePanel>
                  </ResizablePanelGroup>
                </TabsContent>
                <TabsContent value="password">Change your password here.</TabsContent>
              </Tabs>

            </CardContent>
            <CardFooter className="flex justify-end">
              <div className="flex justify-start">
                <Button variant="secondary" className="mr-2">
                  <FileCheck className="h-3.5 w-3.5 mr-1" />
                  <span className="sr-only sm:not-sr-only sm:whitespace-nowrap">
                    Yaml
                  </span>
                </Button>
                <Button className="mr-2">
                  <ScanEye className="h-3.5 w-3.5 mr-1" />
                  <span className="sr-only sm:not-sr-only sm:whitespace-nowrap">
                    Preview
                  </span>
                </Button>
                <Button>
                  <PlusCircle className="h-3.5 w-3.5 mr-1" />
                  <span className="sr-only sm:not-sr-only sm:whitespace-nowrap">
                    Create
                  </span>
                </Button>
              </div>
            </CardFooter>
          </Card>
        </main>
      </div>
    </div>
  );
}
