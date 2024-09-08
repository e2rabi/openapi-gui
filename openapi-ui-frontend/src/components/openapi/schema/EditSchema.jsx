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
import React, { useEffect, useState } from "react";
import { RichTreeView } from '@mui/x-tree-view/RichTreeView';
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"
const SCHEMAS = [
    {
        id: 'Schema1',
        label: 'Schema 1',
        children: [
            { id: 'type', label: 'type: object' },
            {
                id: 'Schema1_properties',
                label: 'properties',
                children: [
                    {
                        id: 'id_field_properties',
                        label: 'id',
                        children: [
                            { id: 'id_type', label: 'type: integer' },
                            { id: 'id_format', label: 'format: int64' },

                        ],
                    },
                    {
                        id: 'username_field_properties',
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
const getAllItemsWithChildrenItemIds = () => {
    const itemIds = [];
    const registerItemId = (item) => {
        if (item.children?.length) {
            itemIds.push(item.id);
            item.children.forEach(registerItemId);
        }
    };

    SCHEMAS.forEach(registerItemId);

    return itemIds;
};
const EditSchema = ({ open, onOpenChange, schemaName = "test" }) => {
    const [expandedItems, setExpandedItems] = useState([]);

    const handleItemExpansionToggle = (event, itemId, isExpanded) => {
        if (itemId) {
            if (itemId.includes('field_properties')) {
                console.log('handleItemExpansionToggle', itemId);
                console.log(JSON.stringify(SCHEMAS[0].children[1]))
                const property = SCHEMAS[0].children.forEach((item) => {
                    if (item.id === itemId) {
                        return { ...item };
                    }
                });
            }
            console.log("property : ", property);
        }
    };

    const handleExpandedItemsChange = (event, itemIds) => {
        setExpandedItems(itemIds);

    };

    useEffect(() => {
        setExpandedItems(getAllItemsWithChildrenItemIds());
    }
        , []);
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
                    <ResizablePanel defaultSize={35}>
                        <div className="flex h-full justify-start p-6">
                            <span className="font-semibold">
                                <RichTreeView
                                    items={SCHEMAS}
                                    onItemExpansionToggle={handleItemExpansionToggle}
                                    defaultExpandedItems={['grid', 'pickers']}
                                    expandedItems={expandedItems}
                                    onExpandedItemsChange={handleExpandedItemsChange}
                                />
                            </span>
                        </div>
                    </ResizablePanel>
                    <ResizableHandle withHandle />
                    <ResizablePanel defaultSize={65}>
                        <div className="flex h-full items-center justify-start p-6">
                            <span className="font-semibold">
                                <div className="grid w-full max-w-sm items-center gap-1.5">
                                    <Label htmlFor="name">Property</Label>
                                    <Input type="text" id="name" placeholder="Name" />
                                </div>
                                <div className="grid w-full max-w-sm items-center gap-1.5">
                                    <Label htmlFor="type">Type</Label>
                                    <Input type="text" id="type" placeholder="Type" />
                                </div>
                                <div className="grid w-full max-w-sm items-center gap-1.5">
                                    <Label htmlFor="format">Fomat</Label>
                                    <Input type="text" id="format" placeholder="Format" />
                                </div>
                                <div className="grid w-full max-w-sm items-center gap-1.5">
                                    <Label htmlFor="example">Example</Label>
                                    <Input type="text" id="example" placeholder="Example" />
                                </div>
                            </span>
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
