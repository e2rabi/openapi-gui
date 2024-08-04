import {
  Table,
  TableBody,
  TableCell,
  TableHeader,
  TableHead,
  TableRow,
} from "../ui/table";

import { Badge } from "../ui/badge";
import { useState, useEffect } from "react";
import { fetchApi } from "@/services/apiService";

const WorkspaceTable = ({ isLoading, workspaces }) => {
  const [userCounts, setUserCounts] = useState({});
  useEffect(() => {
    const fetchUserCounts = async () => {
      try {
        const counts = await Promise.all(
          workspaces.map(async (workspace) => {
            const count = await fetchApi(
              `users/workspace/${workspace.id}/count`
            );
            return { workspaceId: workspace.id, count };
          })
        );
        const countsMap = counts.reduce((acc, { workspaceId, count }) => {
          acc[workspaceId] = count;
          return acc;
        }, {});
        setUserCounts(countsMap);
      } catch (error) {
        console.error("Error fetching user counts:", error);
      }
    };

    if (!isLoading) {
      fetchUserCounts();
    }
  }, [isLoading, workspaces]);

  return (
    <>
      {isLoading ? (
        <p>Loading workspaces...</p>
      ) : (
        <Table>
          <TableHeader>
            <TableRow>
              <TableHead className="w-[100px]">ID</TableHead>
              <TableHead>Name</TableHead>
              <TableHead>Description</TableHead>
              <TableHead>Status</TableHead>
              <TableHead>visibility</TableHead>
              <TableHead>Users</TableHead>
            </TableRow>
          </TableHeader>
          <TableBody>
            {workspaces.map((workspace) => (
              <TableRow key={workspace.id}>
                <TableCell className="font-medium">{workspace.id}</TableCell>
                <TableCell>{workspace.name}</TableCell>
                <TableCell>{workspace.description}</TableCell>
                <TableCell>
                  <Badge
                    className="text-xs"
                    variant={workspace.enabled ? "enabled" : "disabled"}
                  >
                    {workspace.enabled ? "Active" : "Disabled"}
                  </Badge>
                </TableCell>
                <TableCell>{workspace.visibility ? "Yes" : "No"}</TableCell>
                <TableCell>
                  {userCounts[workspace.id] !== undefined
                    ? userCounts[workspace.id]
                    : "Loading..."}
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      )}
    </>
  );
};

export default WorkspaceTable;
