
import { Button } from "../ui/button"
import React, { useCallback, useDeferredValue, useState, useEffect } from 'react';
import { PlusCircle, File, Search, } from 'lucide-react';
import UserTable from './UserTable';
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs"
import { Input } from "@/components/ui/input"
const MemoizedUserTableFiltred = ({ isLoading, users, pageInfo, setPageInfo }) => {
    const [userList, setUserList] = useState([]);
    const deferredUsers = useDeferredValue(userList);

    useEffect(() => {
        setUserList(users)
    }, [users]);

    const selectQuery = useCallback((query) => {
        setPageInfo({
            ...pageInfo,
            searchQuery: query,
            pageSize: pageInfo.pageSize,
            pageNumber: 0
        });
    }, [setPageInfo, pageInfo]);

    const onSearch = (e) => {
        setUserList(() => users.filter(user => (user.username.includes(e.target.value) || user.email.includes(e.target.value))))
    };

    return (
        <>
            {isLoading ? (
                <p>Loading users...</p>
            ) : (
                <Tabs defaultValue={pageInfo.searchQuery}>
                    <div className="flex items-center">
                        <TabsList>
                            <TabsTrigger value="all" onClick={() => selectQuery("all")}>All</TabsTrigger>
                            <TabsTrigger value="active" onClick={() => selectQuery("active")}>Active</TabsTrigger>
                            <TabsTrigger value="inactive" onClick={() => selectQuery("inactive")}>Inactive</TabsTrigger>
                            <TabsTrigger value="expired" onClick={() => selectQuery("expired")}>Expired</TabsTrigger>
                        </TabsList>
                        <div className="relative ml-3">
                            <Search className="absolute left-2.5 top-2.5 h-4 w-4 text-muted-foreground" />
                            <Input
                                type="search"
                                placeholder="Search users..."
                                className="w-full appearance-none bg-background pl-8 shadow-none w-full"
                                onChange={onSearch}
                            />
                        </div>
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
                    <TabsContent value={pageInfo.searchQuery}>
                        <UserTable isLoading={isLoading} users={deferredUsers} />
                    </TabsContent>
                </Tabs>
            )}
        </>
    )
}

const UserTableFiltred = React.memo(MemoizedUserTableFiltred);
export default UserTableFiltred;
