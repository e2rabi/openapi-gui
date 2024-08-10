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
import SolutionEdit from "./SolutionEdit.jsx";

const MemoizedSolutionTable = ({ isLoading, solutions }) => {
  const [isOpen, setIsOpen] = useState(false);
  const editSolutionHandler = () => setIsOpen(true);

  return (
    <>
      {isLoading ? (
        <p>Loading solutions...</p>
      ) : (
        <Table>
          <TableHeader>
            <TableRow>
              <TableHead className="w-[100px]">ID</TableHead>
              <TableHead>Name</TableHead>
              <TableHead>Description</TableHead>
              <TableHead>Status</TableHead>
              <TableHead>visibility</TableHead>
              <TableHead>Releases</TableHead>
              <TableHead>
                <span className="sr-only">Actions</span>
              </TableHead>
            </TableRow>
          </TableHeader>
          <TableBody>
            {solutions.map((solution) => (
              <TableRow key={solution.id} className="cursor-pointer">
                <TableCell className="font-medium">{solution.id}</TableCell>
                <TableCell>{solution.name}</TableCell>
                <TableCell>{solution.description}</TableCell>
                <TableCell>
                  <Badge
                    className="text-xs"
                    variant={solution.enabled ? "enabled" : "disabled"}
                  >
                    {solution.enabled ? "Active" : "Disabled"}
                  </Badge>
                </TableCell>
                <TableCell>{solution.visibility ? "Yes" : "No"}</TableCell>
                <TableCell>{solution.releaseName}</TableCell>
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
                        onClick={() => editSolutionHandler()}
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
      <SolutionEdit isOpen={isOpen} setIsOpen={setIsOpen} />
    </>
  );
};

const SolutionTable = React.memo(MemoizedSolutionTable);
export default SolutionTable;
