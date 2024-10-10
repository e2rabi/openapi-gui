import React from 'react'
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
import { SquarePlus, Trash2 } from 'lucide-react'
import { Button } from "@/components/ui/button"
import { useDispatch, useSelector } from 'react-redux'
import { addServer, removeServer } from './ServerEnumSlice'
import { v4 as uuidv4 } from 'uuid';

const ServerEnum = ({ id, value }) => {
    const dispatch = useDispatch()
    return (
        <Card className="w-full mt-2">
            <CardHeader />
            <CardContent>

                <div className="flex w-full items-center">
                    <div className="flex flex-row items-start mr-4">
                        <Button variant="outline" className="mr-2" onClick={() => dispatch(addServer({ id: uuidv4(), value: "newValue" }))}>
                            <SquarePlus className="h-4 w-4" />
                        </Button>
                        <Button variant="outline" className="mr-2" onClick={() => dispatch(removeServer(id))}>
                            <Trash2 className="h-4 w-4" />
                        </Button>
                    </div>
                    <Label htmlFor="name" className="mr-2">Value</Label>
                    <Input className="flex-grow" id="name" placeholder="new value" defaultValue={value} />
                </div>

            </CardContent>
            <CardFooter className="flex justify-between" />
        </Card>
    )
}

export default ServerEnum;