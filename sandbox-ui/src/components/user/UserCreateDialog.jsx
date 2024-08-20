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
import React, { useState, useEffect, useRef, useCallback } from "react";
import { useToast } from "@/components/ui/use-toast"
import { Separator } from "@/components/ui/separator"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"
import { Button } from "@/components/ui/button"
import { useNavigate } from 'react-router-dom';

import {
    Card,
    CardContent,
    CardHeader,
    CardTitle,
    CardFooter
} from "@/components/ui/card"
import { Switch } from "@/components/ui/switch"
import { LockKeyhole, UserRound } from "lucide-react"
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
import { useForm } from "react-hook-form"
import useWorkspace from "../../hooks/UseWorkspace.js";
import { saveUser } from "../../services/userService.js";
import { UserCreatedSuccess, ValidtionError, internalError } from "../../services/MessageConstant.js";
import TemporaryPasswordDialog from "./TemporaryPasswordDialog.jsx";

const MemoizedUserCreateDialog = ({ isCreateUserDialogOpen, setIsCreateUserDialogOpen, onRefreshCallback }) => {
    const [date, setDate] = useState(new Date());
    const [selectedWorkspace, setSelectedWorkspace] = useState("");
    const [isTemporaryPasswordDialogOpen, setIsTemporaryPasswordDialogOpen] = useState(false);
    const [userStatus, setUserStatus] = useState(false);
    const [firstLoginChangePassword, setFirstLoginChangePassword] = useState(true);
    const [temporaryPassword, setTemporaryPassword] = useState("");
    const hiddenSubmitButtonRef = useRef(null);
    const { toast } = useToast()
    const { workspaces } = useWorkspace();
    const navigate = useNavigate();

    const {
        register,
        handleSubmit,
        reset,
        formState: { errors }
    } = useForm();

    useEffect(() => {
        reset();
        setDate(() => format(new Date(), 'yyyy-MM-dd'));
        setUserStatus(() => false);
        setFirstLoginChangePassword(() => true);
        setSelectedWorkspace(() => "");
    }, [isCreateUserDialogOpen, reset]);

    const onSubmit = (newUser) => {
        const createUser = async (newUser) => {
            const controller = new AbortController();
            try {
                if (selectedWorkspace) {
                    const el = workspaces.filter((workspace) => workspace.name == selectedWorkspace);
                    if (el) {
                        const [workspace] = el;
                        newUser.workspaceId = workspace.id;
                    }
                }
                newUser.enabled = userStatus;
                newUser.firstLoginChangePassword = firstLoginChangePassword;
                newUser.expiryDate = date ? format(date, 'yyyy-MM-dd') : new Date()
                const response = await saveUser(newUser);
                handleSuccess(response)
            } catch (error) {
                handleError(error);
            }
            return () => controller.abort();
        };
        createUser(newUser);
    };
    const handleSuccess = (response) => {
        setTemporaryPassword(() => response.temporaryPassword);
        toast(UserCreatedSuccess);
        setIsTemporaryPasswordDialogOpen(() => true)
    }
    const temporaryPasswordCopyCallback = useCallback(() => {
        onRefreshCallback();
        setIsTemporaryPasswordDialogOpen(() => false);
        setIsCreateUserDialogOpen(() => false);
    }, [onRefreshCallback, setIsCreateUserDialogOpen]);

    const handleError = (error) => {
        console.error("Error creating user :", error);
        if (error && error.responseInfo) {
            ValidtionError.description = error.responseInfo.errorDescription;
            toast(ValidtionError);
        } else {
            toast(internalError);
        }
    }
    const onCreateUser = () => {
        hiddenSubmitButtonRef.current.click();
    };
    const onChangeWorkspace = (workspaceName) => {
        if (workspaceName) {
            const el = workspaces.filter((workspace) => workspace.name == workspaceName);
            if (el) {
                const [workspace] = el;
                setSelectedWorkspace(() => workspace ? workspace.name : "");
            }
        }
    };
    const onChangeUserStatus = async () => {
        setUserStatus(() => !userStatus)
    };
    const onChnageDate = (date) => {
        if (date) {
            setDate(() => date);
        }
    }
    return (
        <Dialog open={isCreateUserDialogOpen}
            onOpenChange={() => setIsCreateUserDialogOpen(false)}
            closeOnEsc={true}
            aria-label="Create user"
            closeOnOverlayClick={true}
            isDismissable={true}
        >
            <DialogContent className="max-w-[45rem]">
                <DialogHeader>
                    <DialogTitle>Create new user</DialogTitle>
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
                                                <Input className="relative top-1" id="username" placeholder="" {...register("username", { required: true })} />
                                                {errors.username && <div className="text-red-500 text-sm  mt-2 relative">Username is required </div>}
                                            </div>
                                            <div className="flex flex-col space-y-1.5 mr-5">
                                                <Label htmlFor="email">Email</Label>
                                                <Input className="relative top-1" id="email" placeholder="" {...register("email", { required: true })} />
                                                {errors.email && <div className="text-red-500 text-sm  mt-2 relative">Email is required </div>}
                                            </div>
                                            <div className="flex flex-col space-y-1.5 mr-5">
                                                <Label htmlFor="phone">Phone</Label>
                                                <Input className="relative top-1" id="phone" placeholder="" {...register("phone", { required: true })} />
                                                {errors.phone && <div className="text-red-500 text-sm  mt-2 relative">Phone is required </div>}
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
                                            <Switch checked={userStatus} onCheckedChange={() => onChangeUserStatus()} />
                                        </div>
                                        <div className=" flex items-center space-x-4 rounded-md border p-4 top-3 relative">
                                            <UserRound />
                                            <div className="flex-1 space-y-1">
                                                <p className="text-sm font-medium leading-none">
                                                    Change password
                                                </p>
                                                <p className="text-sm text-muted-foreground">
                                                    User must change password on next login.
                                                </p>
                                            </div>
                                            <Switch checked={firstLoginChangePassword} onCheckedChange={() => setFirstLoginChangePassword(!firstLoginChangePassword)} />
                                        </div>
                                        <div className=" flex items-center space-x-4 rounded-md border p-4 top-5 relative">
                                            <div className="flex-1 space-y-1">
                                                <p className="text-sm font-medium leading-none">
                                                    Account expired
                                                </p>
                                                <p className="text-sm text-muted-foreground">
                                                    expiry date :  {date ? format(date, 'yyyy-MM-dd') : new Date()}
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
                                                                onSelect={onChnageDate}
                                                                initialFocus
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
                                                <Select value={selectedWorkspace} onValueChange={onChangeWorkspace}>
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
                                        <Button onClick={() => navigate('/workspaces')} variant="link">Workspace management</Button>
                                    </HoverCardTrigger>
                                    <HoverCardContent className="w-80">
                                        <div className="flex justify-between space-x-4">
                                            <div className="space-y-1">
                                                <h4 className="text-sm font-semibold">Create new workspace</h4>
                                                <p className="text-sm">
                                                    To create a new workspace,edit a workspace and more.
                                                </p>
                                            </div>
                                        </div>
                                    </HoverCardContent>
                                </HoverCard>
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
                    <Button onClick={() => onCreateUser()}>Create</Button>
                </DialogFooter>
            </DialogContent>
            <TemporaryPasswordDialog isTemporaryPasswordDialogOpen={isTemporaryPasswordDialogOpen} setIsTemporaryPasswordDialogOpen={setIsTemporaryPasswordDialogOpen} temporaryPassword={temporaryPassword} temporaryPasswordCopyCallback={temporaryPasswordCopyCallback} />
        </Dialog>
    );

}
const UserCreateDialog = React.memo(MemoizedUserCreateDialog);
export default UserCreateDialog;
