import {
    Dialog,
    DialogContent,
    DialogDescription,
    DialogHeader,
    DialogTitle,
    DialogFooter,
    DialogClose
} from "@/components/ui/dialog"
import ReactDOM from 'react-dom';
import React from "react";


const UserEdit = ({ isOpen, setIsOpen }) => {
    const dialogElement = (
        <Dialog open={isOpen}
            onOpenChange={() => setIsOpen(false)}
            closeOnEsc={true}
            aria-label="Sample Dialog"
            closeOnOverlayClick={true}
            isDismissable={true}
        >
            <DialogContent>
                <DialogHeader>
                    <DialogTitle>Are you absolutely sure?</DialogTitle>
                    <DialogDescription>
                        This action cannot be undone. This will permanently delete your account
                        and remove your data from our servers.
                    </DialogDescription>
                </DialogHeader>
                <DialogFooter className="sm:justify-start">
                    <DialogClose asChild />
                </DialogFooter>
            </DialogContent>
        </Dialog>
    );
    return (
        <>
            {/* Your existing code */}
            {ReactDOM.createPortal(dialogElement, document.getElementById('dialog-root'))}
        </>
    )
}
export default React.memo(UserEdit)
