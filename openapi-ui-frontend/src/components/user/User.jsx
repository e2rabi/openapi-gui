import { useState, useEffect, useDeferredValue, useCallback } from "react";
import Header from "../layout/Header";
import Logo from "../layout/Logo";
import Navbar from "../layout/Navbar";
import { getUsersByQuery } from "../../services/userService";
import {
  Card,
  CardContent,
  CardFooter,
  CardHeader,
} from "@/components/ui/card"

import TablePagination from "../shared/TablePagination";
import UserTableFiltred from "./UserTableFiltred";
import { useToast } from "@/components/ui/use-toast"
import { internalError } from "../../services/MessageConstant";
const page = {
  "pageSize": 7,
  "pageNumber": 0,
  "totalPages": 0,
  "totalElements": 0,
  "searchQuery": "all"
}
export default function User() {
  const [users, setUsers] = useState([]);
  const deferredUsers = useDeferredValue(users);
  const [pageInfo, setPageInfo] = useState(page);
  const deferredPageInfo = useDeferredValue(pageInfo);
  const [isLoading, setIsLoading] = useState(true);
  const [isRefreshing, setIsRefreshing] = useState(false);
  const { toast } = useToast()

  useEffect(() => {
    const fetchUsers = async (searchQuery, page, pageSize) => {
      try {
        setIsLoading(() => true);
        const data = await getUsersByQuery(searchQuery, "", "", page, pageSize)
        setUsers(() => data.content);
        setPageInfo({
          "searchQuery": pageInfo.searchQuery,
          "pageSize": pageInfo.pageSize,
          "pageNumber": pageInfo.pageNumber,
          "totalPages": data.page.totalPages,
          "totalElements": data.page.totalElements
        });
      } catch (error) {
        toast(internalError);
        console.error("Error fetching users :", error);
      } finally {
        setIsLoading(false);
      }
    }
    fetchUsers(pageInfo.searchQuery, pageInfo.pageNumber, pageInfo.pageSize)
  }, [pageInfo.searchQuery, pageInfo.pageNumber, pageInfo.pageSize, toast, isRefreshing]);

  const refreshUserTable = useCallback(() => {
    setIsRefreshing(() => !isRefreshing);
  }, [isRefreshing]);

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
          <Card x-chunk="dashboard-06-chunk-0" className=" rounded-lg border border-dashed shadow-sm">
            <CardHeader>
              <div>
                <h1 className="text-lg font-semibold md:text-2xl">Accounts</h1>
                <p className="text-sm text-muted-foreground">Recent users from your sandbox.</p>
              </div>
            </CardHeader>
            <CardContent>
              <UserTableFiltred isLoading={isLoading} users={deferredUsers} pageInfo={deferredPageInfo} setPageInfo={setPageInfo} refresh={refreshUserTable} />
            </CardContent>
            <CardFooter className="flex justify-between">
              <TablePagination pageInfo={deferredPageInfo} changePage={setPageInfo} />
            </CardFooter>
          </Card>

        </main>
      </div>
    </div>
  );
}
