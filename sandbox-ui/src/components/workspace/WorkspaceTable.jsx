import React, { useState } from "react";
import {
  Table,
  TableBody,
  TableCell,
  TableHeader,
  TableHead,
  TableRow,
} from "../ui/table";

import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuLabel,
  DropdownMenuTrigger,
} from "../ui/dropdown-menu";

import { Button } from "../ui/button";
import { Badge } from "../ui/badge";
import { MoreHorizontal } from "lucide-react";
import WorkspaceEdit from "./WorkspaceEdit.jsx";

const MemoizedWorkspaceTable = ({ isLoading, workspaces }) => {
  const [isOpen, setIsOpen] = useState(false);
  const editWorkspacesHandler = () => setIsOpen(true);

  return (
    <>
      {isLoading ? (
        <p>Loading workspaces...</p>
      ) : (
        <Table>
          <TableHeader>
            <TableRow>
              <TableHead className="hidden w-[100px] sm:table-cell">
                <span className="sr-only">Image</span>
              </TableHead>
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
                <TableCell className="hidden sm:table-cell">
                  <img
                    className="aspect-square rounded-md object-cover"
                    height="32"
                    src={`data:image/png;base64,${workspace.image}`}
                    width="32"
                  />
                </TableCell>
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
                <TableCell>{workspace.nbOfUsers}</TableCell>
                <TableCell>
                  <DropdownMenu>
                    <DropdownMenuTrigger asChild>
                      <Button aria-haspopup="true" size="icon" variant="ghost">
                        <MoreHorizontal className="h-4 w-4" />
                        <span className="sr-only">Toggle menu</span>
                      </Button>
                    </DropdownMenuTrigger>
                    <DropdownMenuContent align="end">
                      <DropdownMenuLabel>Actions</DropdownMenuLabel>
                      <DropdownMenuItem
                        onClick={() => editWorkspacesHandler()}
                        className="cursor-pointer"
                      >
                        Edit
                      </DropdownMenuItem>
                      <DropdownMenuItem>Delete</DropdownMenuItem>
                    </DropdownMenuContent>
                  </DropdownMenu>
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      )}
      <WorkspaceEdit isOpen={isOpen} setIsOpen={setIsOpen} />
    </>
  );
};

const WorkspaceTable = React.memo(MemoizedWorkspaceTable);
export default WorkspaceTable;
