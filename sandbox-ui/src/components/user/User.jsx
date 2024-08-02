import { useEffect, useState } from "react";
import Header from "../layout/Header";
import Logo from "../layout/Logo";
import Navbar from "../layout/Navbar";

import {
  Card,
  CardContent,
  CardFooter,
  CardHeader,
} from "@/components/ui/card"

import UserTableFilter from "./UserTableFilter";

import UserTablePagination from "./UserTablePagination";
import UserTable from "./UserTable";
export default function User() {
  const [users, setUsers] = useState([]);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    const fetchUsers = async () => {
      try {
        const response = await fetch(
          "http://localhost:8080/sandbox-api/v1/users",
          {
            headers: {
              Authorization: "Basic " + btoa("admin:admin"),
            },
          }
        );
        if (response.ok) {
          const data = await response.json();
          setUsers(data);
        } else {
          console.error("Failed to fetch users");
        }
      } catch (error) {
        console.error("Error fetching users:", error);
      } finally {
        setIsLoading(false);
      }
    };

    fetchUsers();
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
          <Card x-chunk="dashboard-06-chunk-0" className=" rounded-lg border border-dashed shadow-sm">
            <CardHeader>
              <UserTableFilter />
            </CardHeader>
            <CardContent>
              <UserTable isLoading={isLoading} users={users} />
            </CardContent>
            <CardFooter className="flex justify-between">
              <UserTablePagination />
            </CardFooter>
          </Card>

        </main>
      </div>
    </div>
  );
}
