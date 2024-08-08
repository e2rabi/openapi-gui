import { File, ListFilter } from "lucide-react";

import { Button } from "../ui/button";
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuCheckboxItem,
  DropdownMenuLabel,
  DropdownMenuSeparator,
  DropdownMenuTrigger,
} from "../ui/dropdown-menu";

const ReleaseTableFilter = () => {
  return (
    <div className="block items-center">
      <div className="ml-auto flex items-center gap-2 float-right">
        <DropdownMenu>
          <DropdownMenuTrigger asChild>
            <Button variant="outline" size="sm" className="h-7 gap-1 text-sm">
              <ListFilter className="h-3.5 w-3.5" />
              <span className="sr-only sm:not-sr-only">Filter</span>
            </Button>
          </DropdownMenuTrigger>
          <DropdownMenuContent align="end">
            <DropdownMenuLabel>Filter by</DropdownMenuLabel>
            <DropdownMenuSeparator />
            <DropdownMenuCheckboxItem checked>
              Fulfilled
            </DropdownMenuCheckboxItem>
            <DropdownMenuCheckboxItem>Declined</DropdownMenuCheckboxItem>
            <DropdownMenuCheckboxItem>Refunded</DropdownMenuCheckboxItem>
          </DropdownMenuContent>
        </DropdownMenu>
        <Button size="sm" variant="outline" className="h-7 gap-1 text-sm">
          <File className="h-3.5 w-3.5" />
          <span className="sr-only sm:not-sr-only">Export</span>
        </Button>
      </div>
      <h1 className="text-lg font-semibold md:text-2xl">Releases</h1>
      <p className="text-sm text-muted-foreground">
        Recent releases from your sandbox.
      </p>
    </div>
  );
};

export default ReleaseTableFilter;