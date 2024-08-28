import {
  Table,
  TableBody,
  TableCell,
  TableHeader,
  TableHead,
  TableRow,
} from "../ui/table";

import { Badge } from "../ui/badge";

const ReleasesTable = ({ isLoading, releases }) => {
  return (
    <>
      {isLoading ? (
        <p>Loading releases...</p>
      ) : (
        <Table>
          <TableHeader>
            <TableRow>
              <TableHead className="w-[100px]">ID</TableHead>
              <TableHead>Name</TableHead>
              <TableHead>Description</TableHead>
              <TableHead>Status</TableHead>
              <TableHead>visibility</TableHead>
              <TableHead>Product</TableHead>
            </TableRow>
          </TableHeader>
          <TableBody>
            {releases.map((release) => (
              <TableRow key={release.id}>
                <TableCell className="font-medium">{release.id}</TableCell>
                <TableCell>{release.name}</TableCell>
                <TableCell>{release.description}</TableCell>
                <TableCell>
                  <Badge
                    className="text-xs"
                    variant={release.enabled ? "enabled" : "disabled"}
                  >
                    {release.enabled ? "Active" : "Disabled"}
                  </Badge>
                </TableCell>
                <TableCell>{release.visibility ? "Yes" : "No"}</TableCell>
                <TableCell>{release.productName}</TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      )}
    </>
  );
};

export default ReleasesTable;