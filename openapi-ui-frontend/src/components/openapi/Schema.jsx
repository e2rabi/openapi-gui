import React, { useRef, useState } from 'react'
import { useForm } from "react-hook-form"

import {
    Card,
    CardContent,
} from "@/components/ui/card"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"
import { Button } from "@/components/ui/button"
import { PlusCircle, CopyCheck, FilePenLine, Trash2 } from "lucide-react"
import { useDispatch } from 'react-redux'
import { addSchema } from './SchemaSlice'
const Schema = ({ name }) => {
    const dispatch = useDispatch()
    const hiddenSubmitButtonRef = useRef(null);
    const {
        register,
        handleSubmit,
        formState: { errors },
    } = useForm()

    const onSubmit = (data) => {
        if (data) {
            dispatch(addSchema(data.schemaName))
        }
    }
    const createSchema = () => {
        hiddenSubmitButtonRef.current.click();
    }
    return (
        <Card className="w-full m-2">
            <CardContent>
                <form onSubmit={handleSubmit(onSubmit)}>
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
                            <Button variant="outline" size="icon">
                                <Trash2 className="h-4 w-4" />
                            </Button>
                            <Input value={name} id="schemaName" placeholder="New Schema" {...register("schemaName", { required: true })} />
                            {errors.schemaName && <span className="text-red-500 text-sm  mt-2 relative">SchemaName is required</span>}
                            <input ref={hiddenSubmitButtonRef} hidden type="submit" />

                        </div>
                    </div>
                </form>
            </CardContent>
        </Card>
    )
}

export default Schema
