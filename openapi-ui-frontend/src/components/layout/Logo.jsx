
import React from 'react';
import { Link } from 'react-router-dom';
import {
    Bell,
    Braces,
} from "lucide-react"

import { Button } from "../ui/button"

const Logo = () => {
    return (
        <div className="flex h-14 items-center border-b px-4 lg:h-[60px] lg:px-6">
            <Link to="/" className="flex items-center gap-2 font-semibold">
                <Braces className="h-6 w-6" />
                <span className="">Openapi-gui</span>
            </Link>
            <Button variant="outline" size="icon" className="ml-auto h-8 w-8">
                <Bell className="h-4 w-4" />
                <span className="sr-only">Toggle notifications</span>
            </Button>
        </div>
    )
}

export default React.memo(Logo)
