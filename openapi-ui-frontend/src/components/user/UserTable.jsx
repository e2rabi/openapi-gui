import React, { useState } from 'react'
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
import UserEditDialog from './UserEditDialog';
import UserDeleteDialog from './UserDeleteDialog';
import { format } from 'date-fns';
import {
    Tooltip,
    TooltipContent,
    TooltipTrigger,
} from "@/components/ui/tooltip"

const MemoizedUserTable = ({ isLoading, users, onRefreshCallback }) => {

    const [isEditUserDialogOpen, setIsEditUserDialogOpen] = useState(false);
    const [isDeleteUserDialogOpen, setIsDeleteUserDialogOpen] = useState(false);
    const [userId, setUserId] = useState("");

    const editUserHandler = (userId) => {
        setIsEditUserDialogOpen(true);
        setUserId(userId);
    }
    const deleteUserHandler = (userId) => {
        setIsDeleteUserDialogOpen(true);
        setUserId(userId);
    }
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
                            <TableHead>Account expired</TableHead>
                            <TableHead>Expiry date</TableHead>
                            <TableHead>Workspace</TableHead>
                            <TableHead>Creation date</TableHead>
                            <TableHead>
                                <span className="sr-only">Actions</span>
                            </TableHead>
                        </TableRow>
                    </TableHeader>
                    <TableBody>
                        {users.map((user) => (
                            <TableRow key={user.id} className="cursor-pointer">
                                <TableCell className="font-medium">{user.id}</TableCell>
                                <TableCell >{user.username}</TableCell>
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
                                    {!user.accountNonExpired ? "Yes" : "No"}
                                </TableCell>
                                <TableCell>
                                    {user.accountNonExpired ? user.expiryDate :

                                        <Tooltip>
                                            <TooltipTrigger asChild>
                                                <div>
                                                    <Badge className="text-xs" variant={"disabled"}>
                                                        Expired
                                                    </Badge>
                                                </div>
                                            </TooltipTrigger>
                                            <TooltipContent>
                                                <p>Account expired on : {user.expiryDate}</p>
                                            </TooltipContent>
                                        </Tooltip>
                                    }
                                </TableCell>
                                <TableCell>
                                    {user.workspace ? user.workspace.name : "-"}
                                </TableCell>
                                <TableCell>
                                    {user.created ? format(user.created, 'yyyy-MM-dd') : "-"}
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
                                            <DropdownMenuItem onClick={() => editUserHandler(user.id)} className="cursor-pointer">
                                                Edit
                                            </DropdownMenuItem>
                                            <DropdownMenuItem onClick={() => deleteUserHandler(user.id)}>Delete</DropdownMenuItem>
                                        </DropdownMenuContent>
                                    </DropdownMenu>
                                </TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            )}
            <UserEditDialog isEditUserDialogOpen={isEditUserDialogOpen} setIsEditUserDialogOpen={setIsEditUserDialogOpen} userId={userId} onRefreshCallback={onRefreshCallback} />
            <UserDeleteDialog isDeleteUserDialogOpen={isDeleteUserDialogOpen} setIsDeleteUserDialogOpen={setIsDeleteUserDialogOpen} userId={userId} onRefreshCallback={onRefreshCallback} />
        </>
    )
}

const UserTable = React.memo(MemoizedUserTable);
export default UserTable;
