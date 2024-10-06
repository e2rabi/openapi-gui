import React, { useEffect, useState } from 'react'
import { Button } from "@/components/ui/button"
import {
    Card,
    CardContent,
    CardFooter,
    CardHeader,
} from "@/components/ui/card"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"
import ServerEnum from "./ServerEnum"
import { SquarePlus } from 'lucide-react'
import { v4 as uuidv4 } from 'uuid';
import { useDispatch, useSelector } from 'react-redux'
import { addServer } from './ServerSlice'
const ServerVariable = () => {
    const dispatch = useDispatch()
    const servers = useSelector((state) => state.server)
    return (

        <Card className="w-full">
            <CardHeader />
            <CardContent>
                <div className="grid w-full items-center gap-4">
                    <div className="flex flex-col space-y-1.5">
                        <Label htmlFor="name" className="mb-1">Name</Label>
                        <Input id="name" placeholder="Name of your project" />
                    </div>
                </div>
                <div className="grid w-full items-center gap-4 mt-2">
                    <div className="flex flex-col space-y-1.5">
                        <Label htmlFor="name" className="mb-1">Description</Label>
                        <Input id="name" placeholder="Description" />
                    </div>
                </div>
                <div className="grid w-full items-center gap-4 mt-2">
                    <div className="flex flex-col space-y-1.5">
                        <Label htmlFor="name" className="mb-1">Default</Label>
                        <Input id="name" placeholder="Change me" />
                    </div>
                </div>
            </CardContent>
            <CardFooter className="flex justify-between">
                <div className="flex flex-col w-full">
                    <Button className="w-48 mb-4" onClick={() => dispatch(addServer({ id: uuidv4(), value: "newValue" }))}>
                        <SquarePlus className="h-4 w-4 mr-1" />
                        Add enum Value</Button>
                    {
                        servers.value.map(server => <ServerEnum id={server.id} key={server.id} />)

                    }
                </div>

            </CardFooter>
        </Card>
    )
}
export default ServerVariable;