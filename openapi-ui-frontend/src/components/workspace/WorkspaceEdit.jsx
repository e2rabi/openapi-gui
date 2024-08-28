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
import { Button } from "../ui/button";
import { deleteWorkspaces } from "../../services/workspaceService.js";
import { useToast } from "@/components/ui/use-toast";
import { internalError } from "../../services/MessageConstant";
import { WorkspaceDeleteSuccess } from "../../services/MessageConstant";

const Portal = ({ children, containerId }) => {
  const container = document.getElementById(containerId);
  return ReactDOM.createPortal(children, container);
};

const WorkspaceEditDialog = ({ isOpen, setIsOpen, workspaceId }) => {
  const { toast } = useToast();
  const handleDelete = async () => {
    try {
      await deleteWorkspaces(workspaceId);
      setIsOpen(false);
      toast(WorkspaceDeleteSuccess);
    } catch (error) {
      console.error("Error deleting workspace:", error);
      toast(internalError);
    }
  };

  return (
    <Portal containerId="dialog-portal">
      <Dialog
        open={isOpen}
        onOpenChange={() => setIsOpen(false)}
        closeOnEsc={true}
        aria-label="edit workspace"
        closeOnOverlayClick={true}
        isDismissable={true}
      >
        <DialogContent>
          <DialogHeader>
            <DialogTitle>Delete workspace</DialogTitle>
            <DialogDescription>
              This action cannot be undone. This will permanently delete your
              workspace and remove your data from our servers.
            </DialogDescription>
          </DialogHeader>
          <DialogFooter className="sm:justify-start">
            <Button variant="destructive" onClick={handleDelete}>
              Confirm
            </Button>
            <Button variant="outline" onClick={() => setIsOpen(false)}>
              Cancel
            </Button>
            <DialogClose asChild />
          </DialogFooter>
        </DialogContent>
      </Dialog>
    </Portal>
  );
};
const WorkspaceEdit = React.memo(WorkspaceEditDialog);
export default WorkspaceEdit;
