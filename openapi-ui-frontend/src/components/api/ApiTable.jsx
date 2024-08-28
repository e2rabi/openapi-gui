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
import ApiEdit from "./ApiEdit.jsx";

const MemoizedApiTable = ({ isLoading, apis }) => {
  const [isOpen, setIsOpen] = useState(false);
  const editApiHandler = () => setIsOpen(true);

  return (
    <>
      {isLoading ? (
        <p>Loading APIs...</p>
      ) : (
        <Table>
          <TableHeader>
            <TableRow>
              <TableHead className="w-[100px]">ID</TableHead>
              <TableHead>Name</TableHead>
              <TableHead>Description</TableHead>
              <TableHead>Status</TableHead>
              <TableHead>Visibility</TableHead>
              <TableHead>Module</TableHead>
              <TableHead>
                <span className="sr-only">Actions</span>
              </TableHead>
            </TableRow>
          </TableHeader>
          <TableBody>
            {apis.map((api) => (
              <TableRow key={api.id} className="cursor-pointer">
                <TableCell className="font-medium">{api.id}</TableCell>
                <TableCell>{api.name}</TableCell>
                <TableCell>{api.description}</TableCell>
                <TableCell>
                  <Badge
                    className="text-xs"
                    variant={api.enabled ? "enabled" : "disabled"}
                  >
                    {api.enabled ? "Active" : "Disabled"}
                  </Badge>
                </TableCell>
                <TableCell>{api.visibility ? "Yes" : "No"}</TableCell>
                <TableCell>{api.moduleName}</TableCell>
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
                        onClick={() => editApiHandler()}
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
      <ApiEdit isOpen={isOpen} setIsOpen={setIsOpen} />
    </>
  );
};

const ApiTable = React.memo(MemoizedApiTable);
export default ApiTable;
