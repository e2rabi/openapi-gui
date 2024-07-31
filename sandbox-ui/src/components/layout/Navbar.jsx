import { Link } from 'react-router-dom';

import {
    Home,
    LineChart,
    Package,
    GitMerge,
    Users,
    MonitorCog,
    CloudCog,
    Package2
} from "lucide-react"
import { Badge } from "../ui/badge"
const Navbar = () => {
    return (
        <div className="flex-1">
            <nav className="grid items-start px-2 text-sm font-medium lg:px-4">
                <Link
                    href="#"
                    className="flex items-center gap-3 rounded-lg px-3 py-2 text-muted-foreground transition-all hover:text-primary"
                >
                    <Home className="h-4 w-4" />
                    Dashboard
                </Link>
                <Link
                    href="#"
                    className="flex items-center gap-3 rounded-lg bg-muted px-3 py-2 text-primary transition-all hover:text-primary"
                >
                    <Package className="h-4 w-4" />
                    Products{" "}
                </Link>
                <Link
                    href="#"
                    className="flex items-center gap-3 rounded-lg px-3 py-2 text-muted-foreground transition-all hover:text-primary"
                >
                    <GitMerge className="h-4 w-4" />
                    Releases

                </Link>
                <Link
                    href="#"
                    className="flex items-center gap-3 rounded-lg px-3 py-2 text-muted-foreground transition-all hover:text-primary"
                >
                    <Package2 className="h-4 w-4" />
                    Solutions
                </Link>
                <Link
                    href="#"
                    className="flex items-center gap-3 rounded-lg px-3 py-2 text-muted-foreground transition-all hover:text-primary"
                >
                    <Package className="h-4 w-4" />
                    Modules
                </Link>
                <Link
                    href="#"
                    className="flex items-center gap-3 rounded-lg px-3 py-2 text-muted-foreground transition-all hover:text-primary"
                >
                    <CloudCog className="h-4 w-4" />
                    APIs
                </Link>
                <Link
                    href="#"
                    className="flex items-center gap-3 rounded-lg px-3 py-2 text-muted-foreground transition-all hover:text-primary"
                >
                    <Users className="h-4 w-4" />
                    Users
                    <Badge className="ml-auto flex h-6 w-6 shrink-0 items-center justify-center rounded-full">
                        6
                    </Badge>
                </Link>
                <Link
                    href="#"
                    className="flex items-center gap-3 rounded-lg px-3 py-2 text-muted-foreground transition-all hover:text-primary"
                >
                    <LineChart className="h-4 w-4" />
                    Analytics
                </Link>
                <Link
                    href="#"
                    className="flex items-center gap-3 rounded-lg px-3 py-2 text-muted-foreground transition-all hover:text-primary"
                >
                    <MonitorCog className="h-4 w-4" />
                    Configuration
                </Link>
            </nav>
        </div>
    )
}

export default Navbar
