import {
    Dialog,
    DialogContent,
    DialogDescription,
    DialogHeader,
    DialogTitle,
    DialogFooter,
    DialogClose
} from "@/components/ui/dialog"
import { format } from 'date-fns';
import React, { useState, useEffect, useCallback, useRef, useMemo } from "react";
import { useToast } from "@/components/ui/use-toast"
import { Separator } from "@/components/ui/separator"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"
import { Button } from "@/components/ui/button"
import { internalError, UserStatusUpdatedSuccess, UserUpdatedSuccess } from "../../services/MessageConstant.js";
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
import { getUserById, changeUserStatus, updateUser } from "../../services/userService.js";
import { useForm } from "react-hook-form"
const UserEditDialog = ({ isOpen, setIsOpen, userId, onRefreshCallback }) => {
    const [date, setDate] = useState(null)
    const [workspaces, setWorkspaces] = useState([]);
    const [selectedWorkspace, setSelectedWorkspace] = useState("");
    const [user, setUser] = useState({});
    const hiddenSubmitButtonRef = useRef(null);
    const { toast } = useToast()
    const {
        register,
        handleSubmit,
        setValue,
        reset,
        formState: { errors }
    } = useForm()

    const onSubmit = (data) => {
        const updateUserData = async (data) => {
            const controller = new AbortController();
            try {
                if (selectedWorkspace) {
                    const el = workspaces.filter((workspace) => workspace.name == selectedWorkspace);
                    if (el) {
                        const [workspace] = el;
                        data.workspace = { id: workspace.id };
                    }
                }
                await updateUser(data);
                toast(UserUpdatedSuccess);
                onRefreshCallback();
            } catch (error) {
                toast(internalError);
                console.error("Error updating user details:", error);
            }
            return () => controller.abort();
        };
        data.id = user.id;
        data.enabled = user.enabled;
        data.expiryDate = date ? format(date, 'yyyy-MM-dd') : user.expiryDate;
        updateUserData(data);
    };


    const fetchWorkspaces = useCallback(async (page, pageSize) => {
        const controller = new AbortController();
        try {
            const data = await getAllWorkspaces(page, pageSize);
            setWorkspaces(() => data.content);
        } catch (error) {
            toast(internalError);
            console.error("Error fetching workspaces:", error);
        }
        return () => controller.abort();
    }, [toast]);

    const getUserDetailsById = useCallback(async (userId) => {
        const controller = new AbortController();
        reset();
        try {
            const data = await getUserById(userId);
            setUser(() => data);
            setValue('firstName', data.firstName);
            setValue('lastName', data.lastName);
            setValue('email', data.email);
            setValue('phone', data.phone);
            setValue('username', data.username);
            setDate(() => data.expiryDate);
            setSelectedWorkspace(() => data.workspace ? data.workspace.name : "");
        } catch (error) {
            toast(internalError);
            console.error("Error fetching user details:", error);
        }
        return () => controller.abort();
    }, [setValue, reset, toast]);

    const changeUserStatusById = async () => {
        const controller = new AbortController();
        try {
            setUser((prev) => ({
                ...prev, enabled: !prev.enabled
            }));
            await changeUserStatus(user.id, !user.enabled);
            toast(UserStatusUpdatedSuccess);
            onRefreshCallback();
        } catch (error) {
            toast(internalError);
            console.error("Error fetching user details:", error);
        }
        return () => controller.abort();

    };

    useEffect(() => {
        if (userId) {
            getUserDetailsById(userId)
        }
    }, [userId, getUserDetailsById]);

    useEffect(() => {
        if (userId && workspaces.length === 0) {
            fetchWorkspaces(0, 1000);
        }
    }, [userId, fetchWorkspaces, workspaces.length]);

    const handleSaveClick = () => {
        hiddenSubmitButtonRef.current.click();
    };
    const handleWorkspaceChange = (workspaceName) => {
        if (workspaceName) {
            const el = workspaces.filter((workspace) => workspace.name == workspaceName);
            if (el) {
                const [workspace] = el;
                setSelectedWorkspace(() => workspace ? workspace.name : "");
                console.log("workspace  : ", workspace ? workspace.id : 0);
            }
        }

    };
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
                                <form onSubmit={handleSubmit(onSubmit)}>
                                    <div className="grid gap-6">
                                        <div className="flex justify-start flex-wrap">
                                            <div className="flex flex-col space-y-1.5 mr-5">
                                                <Label htmlFor="username">Username</Label>
                                                <Input className="relative top-1" disabled id="username" placeholder="" {...register("username")} />
                                            </div>
                                            <div className="flex flex-col space-y-1.5 mr-5">
                                                <Label htmlFor="email">Email</Label>
                                                <Input className="relative top-1" disabled id="email" placeholder="" {...register("email")} />
                                            </div>
                                            <div className="flex flex-col space-y-1.5 mr-5">
                                                <Label htmlFor="phone">Phone</Label>
                                                <Input className="relative top-1" id="phone" placeholder="" {...register("phone", { required: true })} />
                                                {errors.phone && <div className="text-red-500 text-sm  mt-1 relative">Phone is required </div>}
                                            </div>
                                            <div className="flex flex-col space-y-1.5 mr-5 mt-4">
                                                <Label htmlFor="firstName">FirstName</Label>
                                                <Input className="relative top-1" name="firstName" id="firstName" placeholder="" {...register("firstName", { required: true })} />
                                                {errors.firstName && <div className="text-red-500 text-sm  mt-1 relative">FirstName is required </div>}
                                            </div>
                                            <div className="flex flex-col space-y-1.5 mr-5 mt-4">
                                                <Label htmlFor="lastName">LastName</Label>
                                                <Input className="relative top-1" id="lastName" placeholder="" {...register("lastName", { required: true })} />
                                                {errors.lastName && <div className="text-red-500 text-sm mt-1 relative">LastName is required </div>}
                                            </div>
                                            <input type="submit" hidden ref={hiddenSubmitButtonRef} />
                                        </div>

                                        <div className=" flex items-center space-x-4 rounded-md border p-4 top-3 relative">
                                            <UserRound />
                                            <div className="flex-1 space-y-1">
                                                <p className="text-sm font-medium leading-none">
                                                    Activate account
                                                </p>
                                                <p className="text-sm text-muted-foreground">
                                                    Activate or deactivate this account
                                                </p>
                                            </div>
                                            <Switch checked={user ? user.enabled : false} onCheckedChange={() => changeUserStatusById()} />
                                        </div>
                                        <div className=" flex items-center space-x-4 rounded-md border p-4 top-5 relative">
                                            <CalendarOff onSelect={setDate} />
                                            <div className="flex-1 space-y-1">
                                                <p className="text-sm font-medium leading-none">
                                                    Account expired
                                                </p>
                                                <p className="text-sm text-muted-foreground">
                                                    expiry date :  {date ? format(date, 'yyyy-MM-dd') : user.expiryDate}
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
                                        <div className=" flex items-center space-x-4 rounded-md border p-4 relative top-7">
                                            <LockKeyhole />
                                            <div className="flex-1 space-y-1">
                                                <p className="text-sm font-medium leading-none">
                                                    Account authorizations
                                                </p>

                                            </div>
                                            <div>
                                                <Select value={selectedWorkspace} onValueChange={handleWorkspaceChange}>
                                                    <SelectTrigger className="w-[180px]">
                                                        <SelectValue>{selectedWorkspace}</SelectValue>
                                                    </SelectTrigger>
                                                    <SelectContent>
                                                        {
                                                            workspaces.map((workspace) => (
                                                                <SelectItem key={workspace.id} value={workspace.name}>
                                                                    {workspace.name}
                                                                </SelectItem>
                                                            ))
                                                        }
                                                    </SelectContent>
                                                </Select>                                            </div>
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
                                </form>
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
                    <Button disabled={!user} onClick={() => handleSaveClick()}>Save</Button>
                </DialogFooter>
            </DialogContent>
        </Dialog>


    );
}
const UserEdit = React.memo(UserEditDialog);
export default UserEdit;
