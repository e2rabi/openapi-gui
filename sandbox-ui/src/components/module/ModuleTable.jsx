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
import ModuleEdit from "./ModuleEdit.jsx";

const MemoizedModuleTable = ({ isLoading, modules }) => {
  const [isOpen, setIsOpen] = useState(false);
  const editModulesHandler = () => setIsOpen(true);

  return (
    <>
      {isLoading ? (
        <p>Loading modules...</p>
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
              <TableHead>solution</TableHead>
              <TableHead>
                <span className="sr-only">Actions</span>
              </TableHead>
            </TableRow>
          </TableHeader>
          <TableBody>
            {modules.map((module) => (
              <TableRow key={module.id} className="cursor-pointer">
                <TableCell className="hidden sm:table-cell">
                  <img
                    className="aspect-square rounded-md object-cover"
                    height="32"
                    src={`data:image/png;base64,${module.image}`}
                    width="32"
                  />
                </TableCell>
                <TableCell className="font-medium">{module.id}</TableCell>
                <TableCell>{module.name}</TableCell>
                <TableCell>{module.description}</TableCell>
                <TableCell>
                  <Badge
                    className="text-xs"
                    variant={module.enabled ? "enabled" : "disabled"}
                  >
                    {module.enabled ? "Active" : "Disabled"}
                  </Badge>
                </TableCell>
                <TableCell>{module.visibility ? "Yes" : "No"}</TableCell>
                <TableCell>{module.solutionName}</TableCell>
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
                        onClick={() => editModulesHandler()}
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
      <ModuleEdit isOpen={isOpen} setIsOpen={setIsOpen} />
    </>
  );
};

const ModuleTable = React.memo(MemoizedModuleTable);
export default ModuleTable;
