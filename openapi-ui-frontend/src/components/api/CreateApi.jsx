import Header from "../layout/Header";
import Logo from "../layout/Logo";
import Navbar from "../layout/Navbar";
import React from "react";

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
import { FileCheck, ScanEye, PlusCircle, ChevronsLeftRight, Server } from "lucide-react"
import Schema from "../openapi/schema/Schema";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs"
import { useSelector, useDispatch } from 'react-redux'
import SchemaYamlView from "../openapi/schema/SchemaYamlView";
import {
  Alert,
  AlertDescription,
  AlertTitle,
} from "@/components/ui/alert"
import { Plus } from "lucide-react"
import { addSchema } from "../openapi/schema/SchemaSlice";
import ServerList from "../openapi/servers/ServersList";
const MemoizedCreateApi = () => {
  const schemas = useSelector((state) => state.schema.value);
  const dispatch = useDispatch()
  const createSchema = () => {
    dispatch(addSchema("newSchema"))

  }
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
            <CardContent className="h-4/5 w-full">
              <Tabs defaultValue="account" className="w-full">
                <TabsList className="w-full justify-start">
                  <TabsTrigger value="password">Info</TabsTrigger>
                  <TabsTrigger value="servers">
                    <Server className="h-4 w-4" />
                    <span className="ml-1">Servers</span>
                  </TabsTrigger>
                  <TabsTrigger value="password">Tags</TabsTrigger>
                  <TabsTrigger value="password">Paths</TabsTrigger>
                  <TabsTrigger value="account">
                    <ChevronsLeftRight className="h-4 w-4" />
                    <span className="ml-1">Schema</span>
                  </TabsTrigger>
                  <TabsTrigger value="password">Security</TabsTrigger>
                </TabsList>
                <TabsContent value="account">
                  <ResizablePanelGroup
                    direction="horizontal"
                    className="max-w-full rounded-lg border"
                  >
                    <ResizablePanel defaultSize={25}>
                      <SchemaYamlView />
                    </ResizablePanel>
                    <ResizableHandle withHandle />
                    <ResizablePanel defaultSize={75}>
                      {
                        schemas.length == 0 &&
                        <Alert className="w-full flex flex-col items-center relative mt-2 border-none">
                          <AlertTitle className="text-green-700">OpenAPI Specification Schemas Object</AlertTitle>
                          <AlertDescription>
                            There are no shared schemas defined yet in this document.
                          </AlertDescription>
                          <Button className="relative mt-2" onClick={createSchema}>
                            <Plus className="mr-2 h-4 w-4" /> Create schema
                          </Button>
                        </Alert>

                      }
                      {
                        schemas.map((schema, index) => (
                          <Schema key={index} name={schema.name} />
                        ))
                      }
                    </ResizablePanel>
                  </ResizablePanelGroup>
                </TabsContent>
                <TabsContent value="servers">
                  <ServerList />
                </TabsContent>
              </Tabs>

            </CardContent>
            <CardFooter className="flex justify-end">
              {/* <div className="flex justify-start">
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
              </div> */}
            </CardFooter>
          </Card>
        </main>
      </div>
    </div>
  );
}
const CreateApi = React.memo(MemoizedCreateApi)
export default CreateApi