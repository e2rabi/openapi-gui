import {
    Dialog,
    DialogContent,
    DialogDescription,
    DialogHeader,
    DialogTitle,
    DialogFooter,
    DialogClose
} from "@/components/ui/dialog"
import React, { useState, useEffect, useCallback } from "react";
import { Separator } from "@/components/ui/separator"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"
import { Button } from "@/components/ui/button"
import {
    Card,
    CardContent,
    CardHeader,
    CardTitle,
    CardFooter
} from "@/components/ui/card"
import { Switch } from "@/components/ui/switch"
import { LockKeyhole, CalendarOff, UserRound } from "lucide-react"
import {
    Popover,
    PopoverContent,
    PopoverTrigger,
} from "@/components/ui/popover"
import { Calendar } from "@/components/ui/calendar"
import {
    Select,
    SelectContent,
    SelectItem,
    SelectTrigger,
    SelectValue,
} from "@/components/ui/select"

import {
    HoverCard,
    HoverCardContent,
    HoverCardTrigger,
} from "@/components/ui/hover-card"
import { getAllWorkspaces } from "../../services/workspaceService.js";
import { getUserById } from "../../services/userService.js";

const UserEditDialog = ({ isOpen, setIsOpen, userId }) => {
    const [date, setDate] = useState(new Date())
    const [workspaces, setWorkspaces] = useState([]);
    const [user, setUser] = useState({});


    const fetchWorkspaces = useCallback(async (page, pageSize) => {
        const controller = new AbortController();
        try {
            const data = await getAllWorkspaces(page, pageSize);
            setWorkspaces(() => data.content);
        } catch (error) {
            console.error("Error fetching workspaces:", error);
        }
        return () => controller.abort();
    }, []);

    const getUserDetailsById = useCallback(async (userId) => {
        const controller = new AbortController();
        try {
            const data = await getUserById(userId);
            setUser(() => data);
        } catch (error) {
            console.error("Error fetching user details:", error);
        }
        return () => controller.abort();
    }, []);

    useEffect(() => {
        if (userId) {
            fetchWorkspaces(0, 1000);
            getUserDetailsById(userId)
        }
    }, [userId, fetchWorkspaces, getUserDetailsById]);

    return (
        <Dialog open={isOpen}
            onOpenChange={() => setIsOpen(false)}
            closeOnEsc={true}
            aria-label="edit user"
            closeOnOverlayClick={true}
            isDismissable={true}
        >
            <DialogContent className="max-w-[45rem]">
                <DialogHeader>
                    <DialogTitle>Edit Account</DialogTitle>
                    <Separator />
                    <DialogDescription />
                </DialogHeader>
                <div className="top-1 relative">
                    <div className="grid w-full items-center gap-4">
                        <Card x-chunk="dashboard-07-chunk-3">
                            <CardHeader>
                                <CardTitle>Account details</CardTitle>
                            </CardHeader>
                            <CardContent>
                                <div className="grid gap-6">
                                    <div className="flex justify-start flex-wrap">
                                        <div className="flex flex-col space-y-1.5 mr-5">
                                            <Label htmlFor="username">Username</Label>
                                            <Input disabled id="username" placeholder="admin" defaultValue={user.username} />
                                        </div>
                                        <div className="flex flex-col space-y-1.5 mr-5">
                                            <Label htmlFor="email">Email</Label>
                                            <Input disabled id="email" placeholder="example@test.com" defaultValue={user.email} />
                                        </div>
                                        <div className="flex flex-col space-y-1.5 mr-5">
                                            <Label htmlFor="phone">Phone</Label>
                                            <Input id="phone" placeholder="+212 0607825454" defaultValue={user.phone} />
                                        </div>
                                        <div className="flex flex-col space-y-1.5 mr-5 mt-4">
                                            <Label htmlFor="firstname">FirstName</Label>
                                            <Input id="firstname" placeholder="firstname" defaultValue={user.firstName} />
                                        </div>
                                        <div className="flex flex-col space-y-1.5 mr-5 mt-4">
                                            <Label htmlFor="lastname">LastName</Label>
                                            <Input id="lastname" placeholder="lastname" defaultValue={user.lastName} />
                                        </div>
                                    </div>

                                    <div className=" flex items-center space-x-4 rounded-md border p-4">
                                        <UserRound />
                                        <div className="flex-1 space-y-1">
                                            <p className="text-sm font-medium leading-none">
                                                Activate account
                                            </p>
                                            <p className="text-sm text-muted-foreground">
                                                Activate or deactivate this account
                                            </p>
                                        </div>
                                        <Switch checked={user.enabled} />
                                    </div>
                                    <div className=" flex items-center space-x-4 rounded-md border p-4 top-3 relative">
                                        <CalendarOff />
                                        <div className="flex-1 space-y-1">
                                            <p className="text-sm font-medium leading-none">
                                                Account expired
                                            </p>
                                            <p className="text-sm text-muted-foreground">
                                                expiry date :  {user.expiryDate}
                                            </p>
                                        </div>
                                        <Popover>
                                            <PopoverTrigger asChild>
                                                <Button variant="outline">Change date</Button>
                                            </PopoverTrigger>
                                            <PopoverContent className="w-80">
                                                <div className="grid gap-4">
                                                    <div className="space-y-2">
                                                        <Calendar
                                                            mode="single"
                                                            selected={date}
                                                            onSelect={setDate}
                                                            className="rounded-md border"
                                                        />
                                                    </div>
                                                </div>
                                            </PopoverContent>
                                        </Popover>
                                    </div>
                                    <div className=" flex items-center space-x-4 rounded-md border p-4">
                                        <LockKeyhole />
                                        <div className="flex-1 space-y-1">
                                            <p className="text-sm font-medium leading-none">
                                                Account authorizations
                                            </p>

                                        </div>
                                        <div>
                                            <Select>
                                                <SelectTrigger className="w-[180px]">
                                                    <SelectValue placeholder="Workspace" />
                                                </SelectTrigger>
                                                <SelectContent>
                                                    {
                                                        workspaces.map((workspace) => (
                                                            <SelectItem key={workspace.id} value={workspace.id}>{workspace.name}</SelectItem>
                                                        ))
                                                    }
                                                </SelectContent>
                                            </Select>
                                        </div>
                                        <div>
                                            <Select>
                                                <SelectTrigger className="w-[180px]">
                                                    <SelectValue placeholder="Role" />
                                                </SelectTrigger>
                                                <SelectContent>
                                                    <SelectItem value="light">Role1</SelectItem>
                                                    <SelectItem value="dark">Role2</SelectItem>
                                                    <SelectItem value="system">default</SelectItem>
                                                </SelectContent>
                                            </Select>
                                        </div>
                                    </div>
                                </div>
                            </CardContent>
                            <CardFooter className="flex justify-end">
                                <HoverCard>
                                    <HoverCardTrigger asChild>
                                        <Button variant="link">Create new role</Button>
                                    </HoverCardTrigger>
                                    <HoverCardContent className="w-80">
                                        <div className="flex justify-between space-x-4">
                                            <div className="space-y-1">
                                                <h4 className="text-sm font-semibold">Create new role</h4>
                                                <p className="text-sm">
                                                    To create a new role with a custom access authorizations on product,apis and more.
                                                </p>
                                            </div>
                                        </div>
                                    </HoverCardContent>
                                </HoverCard>
                            </CardFooter>
                        </Card>
                    </div>
                </div>
                <DialogFooter className="sm:justify-end">
                    <DialogClose asChild >
                        <Button variant="outline">Cancel</Button>
                    </DialogClose>
                    <Button>Save</Button>
                </DialogFooter>
            </DialogContent>
        </Dialog>
    );
}
const UserEdit = React.memo(UserEditDialog);
export default UserEdit;
