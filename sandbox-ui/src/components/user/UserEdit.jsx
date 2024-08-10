import {
    Dialog,
    DialogContent,
    DialogDescription,
    DialogHeader,
    DialogTitle,
    DialogFooter,
    DialogClose
} from "@/components/ui/dialog"
import React, { useState } from "react";
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
const UserEditDialog = ({ isOpen, setIsOpen }) => {
    const [date, setDate] = useState(new Date())

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
                                    <div className="flex flex-col space-y-1.5">
                                        <Label htmlFor="name">Username</Label>
                                        <Input disabled id="name" placeholder="admin" />
                                    </div>
                                    <div className="flex flex-col space-y-1.5">
                                        <Label htmlFor="framework">Email</Label>
                                        <Input disabled id="framework" placeholder="example@test.com" />
                                    </div>
                                    <div className="flex flex-col space-y-1.5">
                                        <Label htmlFor="framework">FirstName</Label>
                                        <Input id="framework" placeholder="firstname" />
                                    </div>
                                    <div className="flex flex-col space-y-1.5">
                                        <Label htmlFor="framework">LastName</Label>
                                        <Input id="framework" placeholder="lastname" />
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
                                        <Switch checked />
                                    </div>
                                    <div className=" flex items-center space-x-4 rounded-md border p-4 top-3 relative">
                                        <CalendarOff />
                                        <div className="flex-1 space-y-1">
                                            <p className="text-sm font-medium leading-none">
                                                Account expired
                                            </p>
                                            <p className="text-sm text-muted-foreground">
                                                expiry date :  2024-12-31
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
                                                    <SelectItem value="light">Light</SelectItem>
                                                    <SelectItem value="dark">Dark</SelectItem>
                                                    <SelectItem value="system">System</SelectItem>
                                                </SelectContent>
                                            </Select>
                                        </div>
                                        <div>
                                            <Select>
                                                <SelectTrigger className="w-[180px]">
                                                    <SelectValue placeholder="Role" />
                                                </SelectTrigger>
                                                <SelectContent>
                                                    <SelectItem value="light">Light</SelectItem>
                                                    <SelectItem value="dark">Dark</SelectItem>
                                                    <SelectItem value="system">System</SelectItem>
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
