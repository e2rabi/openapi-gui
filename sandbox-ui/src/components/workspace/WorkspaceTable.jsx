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
              <TableHead className="w-[100px]">ID</TableHead>
              <TableHead>Name</TableHead>
              <TableHead>Description</TableHead>
              <TableHead>Status</TableHead>
              <TableHead>visibility</TableHead>
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
              </TableRow>
            ))}
          </TableBody>
        </Table>
      )}
    </>
  );
};

export default WorkspaceTable;
