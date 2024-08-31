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

const MemoizedSchema = ({ name }) => {
    const [schemaName, setSchemaName] = useState("newSchema")
    const schemaNameDeffered = useDeferredValue(schemaName);
    const dispatch = useDispatch()

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
        <Card className="w-full m-2">
            <CardContent>
                <div className="w-full">
                    <div className="flex items-center flex-row space-x-2 justify-start relative mt-6">
                        <Label htmlFor="name">Schema</Label>
                        <Button variant="outline" size="icon" onClick={() => createSchema()}>
                            <PlusCircle className="h-4 w-4" />
                        </Button>
                        <Button variant="outline" size="icon">
                            <CopyCheck className="h-4 w-4" />
                        </Button>
                        <Button variant="outline" size="icon">
                            <FilePenLine className="h-4 w-4" />
                        </Button>
                        <Button variant="outline" size="icon" onClick={() => deleteSchema(name)}>
                            <Trash2 className="h-4 w-4" />
                        </Button>
                        <Input name="schemaName" value={schemaNameDeffered} id="schemaName" placeholder="New Schema" onChange={(e) => updateSchemaName(name, e.target.value)} />
                        {/* {errors.schemaName && <span className="text-red-500 text-sm  mt-2 relative">SchemaName is required</span>} */}
                    </div>
                </div>
            </CardContent>
        </Card>
    )
}
const Schema = React.memo(MemoizedSchema)
export default Schema
