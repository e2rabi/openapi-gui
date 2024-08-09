
import { useState } from "react";
import { Button } from "../ui/button"
import { PlusCircle, File, } from 'lucide-react';
import UserTable from './UserTable';
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs"

const UserTableFiltred = ({ isLoading, users }) => {
    const [filter, setFilter] = useState("all");
    return (
        <>
            {isLoading ? (
                <p>Loading users...</p>
            ) : (
                <Tabs defaultValue="all">
                    <div className="flex items-center">
                        <TabsList>
                            <TabsTrigger value="all" onClick={() => setFilter("all")}>All</TabsTrigger>
                            <TabsTrigger value="active" onClick={() => setFilter("active")}>Active</TabsTrigger>
                            <TabsTrigger value="disabled" onClick={() => setFilter("disabled")}>Disabled</TabsTrigger>
                            <TabsTrigger value="expired" onClick={() => setFilter("expired")} className="hidden sm:flex">
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
                            <Button size="sm" className="h-8 gap-1">
                                <PlusCircle className="h-3.5 w-3.5" />
                                <span className="sr-only sm:not-sr-only sm:whitespace-nowrap">
                                    Add User
                                </span>
                            </Button>
                        </div>
                    </div>
                    <TabsContent value={filter}>
                        <UserTable isLoading={isLoading} users={users} filtreBy={filter} />
                    </TabsContent>
                </Tabs>
            )}
        </>
    )
}

export default UserTableFiltred
