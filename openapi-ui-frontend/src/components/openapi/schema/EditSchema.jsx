import {
    AlertDialog,
    AlertDialogAction,
    AlertDialogCancel,
    AlertDialogContent,
    AlertDialogDescription,
    AlertDialogFooter,
    AlertDialogHeader,
    AlertDialogTitle,
} from "@/components/ui/alert-dialog"
import {
    ResizableHandle,
    ResizablePanel,
    ResizablePanelGroup,
} from "@/components/ui/resizable"
import React, { useState } from "react";
import { RichTreeView } from '@mui/x-tree-view/RichTreeView';

const MUI_X_PRODUCTS = [
    {
        id: 'Schema1',
        label: 'Schema 1',
        children: [
            { id: 'type', label: 'type: object' },
            {
                id: 'Schema14_properties',
                label: 'properties',
                children: [
                    {
                        id: 'id_properties',
                        label: 'id',
                        children: [
                            { id: 'id_type', label: 'type: integer' },
                            { id: 'id_format', label: 'format: int64' },

                        ],
                    },
                    {
                        id: 'username_properties',
                        label: 'username',
                        children: [
                            { id: 'username_type', label: 'type: string' },
                            { id: 'username_format', label: 'format: string' },

                        ],
                    },
                ],
            },
        ],
    },

];
const EditSchema = ({ open, onOpenChange, schemaName = "test" }) => {

    return (
        <AlertDialog open={open} onOpenChange={onOpenChange} className="max-w-6xl">
            <AlertDialogContent className="max-w-6xl">
                <AlertDialogHeader>
                    <AlertDialogTitle>Schema Editor -  {schemaName}</AlertDialogTitle>
                    <AlertDialogDescription>

                    </AlertDialogDescription>
                </AlertDialogHeader>
                <ResizablePanelGroup
                    direction="horizontal"
                    className="w-full rounded-lg border md:min-w-[450px]"
                >
                    <ResizablePanel defaultSize={25}>
                        <div className="flex h-full justify-start p-6">
                            <span className="font-semibold">
                                <RichTreeView
                                    items={MUI_X_PRODUCTS}
                                    isItemEditable
                                    experimentalFeatures={{ labelEditing: true }}
                                    defaultExpandedItems={['grid', 'pickers']}
                                />
                            </span>
                        </div>
                    </ResizablePanel>
                    <ResizableHandle withHandle />
                    <ResizablePanel defaultSize={75}>
                        <div className="flex h-full items-center justify-center p-6">
                            <span className="font-semibold">Content</span>
                        </div>
                    </ResizablePanel>
                </ResizablePanelGroup>
                <div>
                </div>
                <AlertDialogFooter>
                    <AlertDialogCancel>Close</AlertDialogCancel>
                    <AlertDialogAction>Save change</AlertDialogAction>
                </AlertDialogFooter>
            </AlertDialogContent>
        </AlertDialog>
    )
}

export default EditSchema
