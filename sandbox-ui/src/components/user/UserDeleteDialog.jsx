import React from "react";
import { Button } from "@/components/ui/button"
import {
    Dialog,
    DialogClose,
    DialogContent,
    DialogDescription,
    DialogFooter,
    DialogHeader,
    DialogTitle,
} from "@/components/ui/dialog"
import { useToast } from "@/components/ui/use-toast"
import { deleteUserById } from "../../services/userService";
import { internalError, UserDeleteSuccess } from "../../services/MessageConstant.js";

function MemoizedUserDeleteDialog({ isDeleteUserDialogOpen, setIsDeleteUserDialogOpen, userId, onRefreshCallback }) {
    const { toast } = useToast()

    const deleteUserHandler = async () => {
        const controller = new AbortController();
        try {
            await deleteUserById(userId);
            toast(UserDeleteSuccess);
            onRefreshCallback();
        } catch (error) {
            toast(internalError);
            console.error("Error deletig user details:", error);
        }
        return () => controller.abort();
    };
    return (
        <Dialog open={isDeleteUserDialogOpen}
            onOpenChange={() => setIsDeleteUserDialogOpen(false)}
            closeOnEsc={true}
            aria-label="delete user"
            closeOnOverlayClick={true}
            isDismissable={true}
        >
            <DialogContent className="sm:max-w-md">
                <DialogHeader>
                    <DialogTitle>Confirme delete</DialogTitle>
                    <DialogDescription />
                </DialogHeader>
                <p>Do you really want to delete this user ? This process cannot be undone.</p>
                <DialogFooter className="sm:justify-end">
                    <DialogClose asChild>
                        <Button type="button" variant="secondary">
                            Cancel
                        </Button>
                    </DialogClose>
                    <DialogClose asChild>
                        <Button type="button" variant="destructive" onClick={deleteUserHandler}>
                            Delete
                        </Button>
                    </DialogClose>
                </DialogFooter>
            </DialogContent>
        </Dialog>
    )
}
const UserDeleteDialog = React.memo(MemoizedUserDeleteDialog);
export default UserDeleteDialog;