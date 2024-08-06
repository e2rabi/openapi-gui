import { useState } from 'react'
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

import { Button } from "../ui/button"
import { Badge } from "../ui/badge"
import { MoreHorizontal } from 'lucide-react';
import UserEdit from './UserEdit';
const UserTable = ({ isLoading, users }) => {

    const [isOpen, setIsOpen] = useState(false);
    const openDialog = () => setIsOpen(true);

    return (
        <>
            {isLoading ? (
                <p>Loading users...</p>
            ) : (
                <Table>
                    <TableHeader>
                        <TableRow>
                            <TableHead className="w-[100px]">ID</TableHead>
                            <TableHead>Username</TableHead>
                            <TableHead>FirstName</TableHead>
                            <TableHead>LastName</TableHead>
                            <TableHead>Email</TableHead>
                            <TableHead>Phone</TableHead>
                            <TableHead>Status</TableHead>
                            <TableHead>Account Expired</TableHead>
                            <TableHead>Account Locked</TableHead>
                            <TableHead>
                                <span className="sr-only">Actions</span>
                            </TableHead>
                        </TableRow>
                    </TableHeader>
                    <TableBody>
                        {users.map((user) => (
                            <TableRow key={user.id} className="cursor-pointer">
                                <TableCell className="font-medium">{user.id}</TableCell>
                                <TableCell>{user.username}</TableCell>
                                <TableCell>{user.firstName}</TableCell>
                                <TableCell>{user.lastName}</TableCell>
                                <TableCell>{user.email}</TableCell>
                                <TableCell>{user.phone}</TableCell>
                                <TableCell>
                                    <Badge className="text-xs" variant={user.enabled ? "enabled" : "disabled"}>
                                        {user.enabled ? "Active" : "Disabled"}
                                    </Badge>
                                </TableCell>
                                <TableCell>
                                    {user.accountNonExpired ? "Yes" : "No"}
                                </TableCell>
                                <TableCell>
                                    {user.accountNonLocked ? "Yes" : "No"}
                                </TableCell>
                                <TableCell>
                                    <DropdownMenu>
                                        <DropdownMenuTrigger asChild>
                                            <Button
                                                aria-haspopup="true"
                                                size="icon"
                                                variant="ghost"
                                            >
                                                <MoreHorizontal className="h-4 w-4" />
                                                <span className="sr-only">Toggle menu</span>
                                            </Button>
                                        </DropdownMenuTrigger>
                                        <DropdownMenuContent align="end">
                                            <DropdownMenuLabel>Actions</DropdownMenuLabel>
                                            <DropdownMenuItem onClick={() => openDialog()}>
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
            <UserEdit isOpen={isOpen} setIsOpen={setIsOpen} />
        </>
    )
}

export default UserTable
