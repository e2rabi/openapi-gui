import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogHeader,
  DialogTitle,
  DialogFooter,
  DialogClose,
} from "@/components/ui/dialog";
import ReactDOM from "react-dom";
import React from "react";

const Portal = ({ children, containerId }) => {
  const container = document.getElementById(containerId);
  return ReactDOM.createPortal(children, container);
};

const ApiEditDialog = ({ isOpen, setIsOpen }) => {
  return (
    <Portal containerId="dialog-portal">
      <Dialog
        open={isOpen}
        onOpenChange={() => setIsOpen(false)}
        closeOnEsc={true}
        aria-label="edit api"
        closeOnOverlayClick={true}
        isDismissable={true}
      >
        <DialogContent>
          <DialogHeader>
            <DialogTitle>Edit API</DialogTitle>
            <DialogDescription>
              This action cannot be undone. This will permanently delete your
              account and remove your data from our servers.
            </DialogDescription>
          </DialogHeader>
          <DialogFooter className="sm:justify-start">
            <DialogClose asChild />
          </DialogFooter>
        </DialogContent>
      </Dialog>
    </Portal>
  );
};
const ApiEdit = React.memo(ApiEditDialog);
export default ApiEdit;
