import React from 'react'
import {
    Card,
    CardContent,
} from "@/components/ui/card"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"
import { Button } from "@/components/ui/button"
import { PlusCircle, CopyCheck, FilePenLine, Trash2 } from "lucide-react"
const Schema = () => {
    return (
        <Card className="w-full m-2">
            <CardContent>
                <form>
                    <div className="w-full">
                        <div className="flex items-center flex-row space-x-2 justify-start relative mt-6">
                            <Label htmlFor="name">Schema</Label>
                            <Button variant="outline" size="icon">
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
                            <Input id="name" placeholder="New Schema" />
                        </div>
                    </div>
                </form>
            </CardContent>
        </Card>
    )
}

export default Schema
