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
import React, { useState, useEffect, useCallback, useRef } from "react";
import { useToast } from "@/components/ui/use-toast"
import { Separator } from "@/components/ui/separator"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"
import { Button } from "@/components/ui/button"
import { internalError, UserStatusUpdatedSuccess, UserUpdatedSuccess, ValidtionError } from "../../services/MessageConstant.js";
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
import { getUserById, changeUserStatus, updateUser } from "../../services/userService.js";
import { useForm } from "react-hook-form"
import useWorkspace from "../../hooks/UseWorkspace.js";

const UserEditDialog = ({ isEditUserDialogOpen, setIsEditUserDialogOpen, userId, onRefreshCallback }) => {
    const [date, setDate] = useState(null)
    const [selectedWorkspace, setSelectedWorkspace] = useState("");
    const [user, setUser] = useState({});
    const hiddenSubmitButtonRef = useRef(null);
    const { toast } = useToast()
    const { workspaces } = useWorkspace();
    const {
        register,
        handleSubmit,
        setValue,
        reset,
        resetField,
        formState: { errors, isDirty }
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
                data.id = user.id;
                data.enabled = user.enabled;
                data.expiryDate = date ? format(date, 'yyyy-MM-dd') : user.expiryDate;
                await updateUser(data);
                toast(UserUpdatedSuccess);
                onRefreshCallback();
            } catch (error) {
                handleError(error)
            }
            return () => controller.abort();
        };
        updateUserData(data);
    };

    const getUserDetailsById = useCallback(async (userId) => {
        const controller = new AbortController();
        reset();
        try {
            const data = await getUserById(userId);
            setUser(() => data);
            resetField("firstName", { defaultValue: data.firstName });
            resetField("lastName", { defaultValue: data.lastName });
            resetField("email", { defaultValue: data.email });
            resetField("phone", { defaultValue: data.phone });
            resetField("username", { defaultValue: data.username });
            setDate(() => data.expiryDate);
            setSelectedWorkspace(() => data.workspace ? data.workspace.name : "");
        } catch (error) {
            toast(internalError);
            console.error("Error fetching user details:", error);
        }
        return () => controller.abort();
    }, [setValue, reset, toast]);

    useEffect(() => {
        if (userId) {
            getUserDetailsById(userId)
        }
    }, [userId, getUserDetailsById]);


    const handleError = (error) => {
        console.error("Error updating user :", error);
        if (error && error.responseInfo) {
            ValidtionError.description = error.responseInfo.errorDescription;
            toast(ValidtionError);
        } else {
            toast(internalError);
        }
    }
    const onChangeUserStatus = async () => {
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
    const onSaveUpdate = () => {
        hiddenSubmitButtonRef.current.click();
    };
    const onWorkspaceChangeHandler = (workspaceName) => {
        if (workspaceName) {
            const el = workspaces.filter((workspace) => workspace.name == workspaceName);
            if (el) {
                const [workspace] = el;
                setSelectedWorkspace(() => workspace ? workspace.name : "");
            }
        }

    };
    return (
        <Dialog open={isEditUserDialogOpen}
            onOpenChange={() => setIsEditUserDialogOpen(false)}
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
                                            <Switch checked={user ? user.enabled : false} onCheckedChange={() => onChangeUserStatus()} />
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
                                                    Authorizations
                                                </p>

                                            </div>
                                            <div>
                                                <Select value={selectedWorkspace} onValueChange={onWorkspaceChangeHandler}>
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
                                </form>
                            </CardContent>
                            <CardFooter className="flex justify-end">
                                <HoverCard>
                                    <HoverCardTrigger asChild>
                                        <Button variant="link">Role management</Button>
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
                    <Button disabled={!isDirty} onClick={() => onSaveUpdate()}>Save</Button>
                </DialogFooter>
            </DialogContent>
        </Dialog>
    );
}
const UserEdit = React.memo(UserEditDialog);
export default UserEdit;
