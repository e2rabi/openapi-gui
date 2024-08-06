import {
  Table,
  TableBody,
  TableCell,
  TableHeader,
  TableHead,
  TableRow,
} from "../ui/table";

import { Badge } from "../ui/badge";

const ProductTable = ({ isLoading, products }) => {
  return (
    <>
      {isLoading ? (
        <p>Loading products...</p>
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
            {products.map((product) => (
              <TableRow key={product.id}>
                <TableCell className="font-medium">{product.id}</TableCell>
                <TableCell>{product.name}</TableCell>
                <TableCell>{product.description}</TableCell>
                <TableCell>
                  <Badge
                    className="text-xs"
                    variant={product.enabled ? "enabled" : "disabled"}
                  >
                    {product.enabled ? "Active" : "Disabled"}
                  </Badge>
                </TableCell>
                <TableCell>{product.visibility ? "Yes" : "No"}</TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      )}
    </>
  );
};

export default ProductTable;