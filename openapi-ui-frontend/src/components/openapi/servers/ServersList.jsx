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
export default function ServersList() {
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
                    <div className="flex flex-col space-y-1.5 w-1/5">
                        <Button>
                            <SquarePlus className="h-4 w-4 mr-1" />
                            Add variable</Button>
                    </div>
                </div>
            </CardContent>
            <CardFooter className="flex justify-between">
                <ServerVariable />
            </CardFooter>
        </Card>
    )
}
