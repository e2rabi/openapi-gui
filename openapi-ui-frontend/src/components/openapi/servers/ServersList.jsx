import * as React from "react"

import { Button } from "@/components/ui/button"
import {
    Card,
    CardContent,
    CardDescription,
    CardFooter,
    CardHeader,
    CardTitle,
} from "@/components/ui/card"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"
import ServerVariable from "./ServerVariable"
import { SquarePlus } from 'lucide-react'
import { useDispatch, useSelector } from 'react-redux'

export default function ServersList() {
    const serverVariable = useSelector((state) => state.serverVariable)

    return (
        <Card className="w-full">
            <CardHeader>
                <CardTitle>OpenAPI Specification Servers Object</CardTitle>
                <CardDescription>There are no servers defined. This means the API paths will be relative to the host and path the OpenAPI definition was loaded from.</CardDescription>
            </CardHeader>
            <CardContent>
                <div className=" w-full flex flex-row gap-4 justify-around">
                    <div className="flex flex-col space-y-1.5 w-2/5">
                        <Label htmlFor="name">URL</Label>
                        <Input id="name" placeholder="Name of your project" />
                    </div>
                    <div className="flex flex-col space-y-1.5 w-2/5">
                        <Label htmlFor="name">Description</Label>
                        <Input id="name" placeholder="Name of your project" />
                    </div>
                    <div className="flex flex-col space-y-1.5 w-1/5 relative top-5">
                        <Button>
                            <SquarePlus className="h-4 w-4 mr-1" />
                            Add variable</Button>
                    </div>
                </div>
            </CardContent>
            <CardFooter className="flex flex-col">
                {
                    serverVariable.value.map(serverVariable => <ServerVariable name={serverVariable.name}
                        id={serverVariable.id}
                        key={serverVariable.id}
                        enums={serverVariable.enums}
                    />)
                }
            </CardFooter>
        </Card>
    )
}
