import {
  Table,
  TableBody,
  TableCell,
  TableHeader,
  TableHead,
  TableRow,
} from "../ui/table";

import { Badge } from "../ui/badge";

const WorkspaceTable = ({ isLoading, workspaces }) => {
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
              </TableRow>
            ))}
          </TableBody>
        </Table>
      )}
    </>
  );
};

export default WorkspaceTable;
