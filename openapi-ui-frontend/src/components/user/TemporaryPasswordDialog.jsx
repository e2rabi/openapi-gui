import React, { useState } from 'react'
import { Copy } from "lucide-react"
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
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"

export function MemoizedTemporaryPasswordDialog({ temporaryPassword, isTemporaryPasswordDialogOpen, setIsTemporaryPasswordDialogOpen, temporaryPasswordCopyCallback }) {
    const [copyToClipboardText, setCopyToClipboardText] = useState("A temporary password has been generated for the created user.");

    const copyToClipboard = () => {
        navigator.clipboard.writeText(temporaryPassword)
            .then(() => {
                setCopyToClipboardText(() => "Temporary password copied to clipboard.");
                temporaryPasswordCopyCallback();
            })
            .catch(err => {
                setCopyToClipboardText(() => "Failed to copy text");
                console.error("Failed to copy text: ", err);
            });
    };
    const onCloseDialog = () => {
        setIsTemporaryPasswordDialogOpen(false);
        temporaryPasswordCopyCallback();
    }
    return (
        <Dialog open={isTemporaryPasswordDialogOpen}
            onOpenChange={() => setIsTemporaryPasswordDialogOpen(false)}
            closeOnEsc={true}
            closeOnOverlayClick={true}
            isDismissable={true}>
            <DialogContent className="sm:max-w-md">
                <DialogHeader>
                    <DialogTitle>Copy password</DialogTitle>
                    <DialogDescription>
                        {copyToClipboardText}
                    </DialogDescription>
                </DialogHeader>
                <div className="flex items-center space-x-2">
                    <div className="grid flex-1 gap-2">
                        <Label htmlFor="link" className="sr-only">
                            Link
                        </Label>
                        <Input
                            id="link"
                            value={temporaryPassword}
                            diabled="true"
                            readOnly
                        />
                    </div>
                    <Button type="submit" size="sm" className="px-3" onClick={copyToClipboard}>
                        <span className="sr-only">Copy</span>
                        <Copy className="h-4 w-4" />
                    </Button>
                </div>
                <DialogFooter className="sm:justify-start">
                    <DialogClose asChild>
                        <Button type="button" variant="secondary" onClick={onCloseDialog}>
                            Close
                        </Button>
                    </DialogClose>
                </DialogFooter>
            </DialogContent>
        </Dialog>
    )
}
const TemporaryPasswordDialog = React.memo(MemoizedTemporaryPasswordDialog);
export default TemporaryPasswordDialog
