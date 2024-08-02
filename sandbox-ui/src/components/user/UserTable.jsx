import React from 'react'
import {
    Table,
    TableBody,
    TableCell,
    TableHeader,
    TableHead,
    TableRow,
} from "../ui/table";
import { Badge } from "../ui/badge"
const UserTable = ({ isLoading, users }) => {
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
                            <TableHead>Email</TableHead>
                            <TableHead>Status</TableHead>
                            <TableHead>Account Non-Expired</TableHead>
                            <TableHead>Account Non-Locked</TableHead>
                            <TableHead>Credentials Non-Expired</TableHead>
                        </TableRow>
                    </TableHeader>
                    <TableBody>
                        {users.map((user) => (
                            <TableRow key={user.id}>
                                <TableCell className="font-medium">{user.id}</TableCell>
                                <TableCell>{user.username}</TableCell>
                                <TableCell>{user.email}</TableCell>
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
                                    {user.credentialsNonExpired ? "Yes" : "No"}
                                </TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            )}
        </>
    )
}

export default UserTable
