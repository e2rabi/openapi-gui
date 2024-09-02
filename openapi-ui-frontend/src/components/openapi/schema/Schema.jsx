import React, { useDeferredValue, useEffect, useState } from 'react'
import {
    Card,
    CardContent,
} from "@/components/ui/card"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"
import { Button } from "@/components/ui/button"
import { PlusCircle, CopyCheck, FilePenLine, Trash2 } from "lucide-react"
import { useDispatch } from 'react-redux'
import { addSchema, removeSchema, updateSchema } from './SchemaSlice'
import {
    Tooltip,
    TooltipContent,
    TooltipTrigger,
} from "@/components/ui/tooltip"
import EditSchema from './EditSchema'

const MemoizedSchema = ({ name }) => {
    const [schemaName, setSchemaName] = useState("newSchema")
    const schemaNameDeffered = useDeferredValue(schemaName);
    const dispatch = useDispatch()
    const [isDialogOpen, setIsDialogOpen] = useState(false);

    const handleOpenEditSchema = (open) => {
        setIsDialogOpen(open);
    };

    const createSchema = () => {
        dispatch(addSchema("newSchema"))

    }
    const deleteSchema = (schemaName) => {
        dispatch(removeSchema(schemaName))
    }
    const updateSchemaName = (name, value) => {
        setSchemaName(() => value)
        dispatch(updateSchema({ name, value }))
    }

    useEffect(() => {
        setSchemaName(name)
    }, [name])
    return (
        <>
            <EditSchema open={isDialogOpen} onOpenChange={handleOpenEditSchema} schemaName={schemaName} />
            <Card className="w-full m-2">
                <CardContent>
                    <div className="w-full">
                        <div className="flex items-center flex-row space-x-2 justify-start relative mt-6">

                            <Label htmlFor="name">Schema</Label>
                            <Tooltip>
                                <TooltipTrigger asChild>
                                    <Button disabled={schemaNameDeffered == ""} variant="outline" size="icon" onClick={() => createSchema()}>
                                        <PlusCircle className="h-4 w-4" />
                                    </Button>
                                </TooltipTrigger>
                                <TooltipContent>
                                    <p>Add new schema </p>
                                </TooltipContent>
                            </Tooltip>
                            <Tooltip>
                                <TooltipTrigger asChild>
                                    <Button disabled={schemaNameDeffered == ""} variant="outline" size="icon">
                                        <CopyCheck className="h-4 w-4" />
                                    </Button>
                                </TooltipTrigger>
                                <TooltipContent>
                                    <p>Duplicate schema </p>
                                </TooltipContent>
                            </Tooltip>
                            <Tooltip>
                                <TooltipTrigger asChild>
                                    <Button disabled={schemaNameDeffered == ""} variant="outline" size="icon" onClick={() => setIsDialogOpen(true)}>
                                        <FilePenLine className="h-4 w-4" />
                                    </Button>
                                </TooltipTrigger>
                                <TooltipContent>
                                    <p>Edit schema </p>
                                </TooltipContent>
                            </Tooltip>
                            <Tooltip>
                                <TooltipTrigger asChild>
                                    <Button disabled={schemaNameDeffered == ""} variant="outline" size="icon" onClick={() => deleteSchema(name)}>
                                        <Trash2 className="h-4 w-4" />
                                    </Button>
                                </TooltipTrigger>
                                <TooltipContent>
                                    <p>Delete schema </p>
                                </TooltipContent>
                            </Tooltip>
                            <Input name="schemaName" value={schemaNameDeffered} id="schemaName" placeholder="New Schema" onChange={(e) => updateSchemaName(name, e.target.value)} />

                        </div>
                        {schemaNameDeffered == "" && <span className="flex justify-end text-red-500 text-sm  mt-2 relative">The schema name is required</span>}
                    </div>
                </CardContent>
            </Card>
        </>

    )
}
const Schema = React.memo(MemoizedSchema)
export default Schema
