import { Link } from 'react-router-dom';

import { Button } from "@/components/ui/button"
import {
    Card,
    CardContent,
    CardDescription,
    CardFooter,
    CardHeader,
    CardTitle,
} from "@/components/ui/card"
import { Checkbox } from "@/components/ui/checkbox"
import { Input } from "@/components/ui/input"
import Header from '../layout/Header';
import Logo from '../layout/Logo';
import Navbar from '../layout/Navbar';

export default function Configuration() {
    return (
        <div className="grid min-h-screen w-full md:grid-cols-[220px_1fr] lg:grid-cols-[280px_1fr]">
            <div className="hidden border-r bg-muted/40 md:block">
                <div className="flex h-full max-h-screen flex-col gap-2">
                    <Logo />
                    <Navbar />
                </div>
            </div>
            <div className="flex flex-col">
                <Header />
                <div className="flex min-h-screen w-full flex-col">

                    <main className="flex min-h-[calc(100vh_-_theme(spacing.16))] flex-1 flex-col gap-4 bg-muted/40 p-4 md:gap-8 md:p-10">
                        <div className="mx-auto grid w-full max-w-6xl gap-2">
                            <h1 className="text-3xl font-semibold">Settings</h1>
                        </div>
                        <div className="mx-auto grid w-full max-w-6xl items-start gap-6 md:grid-cols-[180px_1fr] lg:grid-cols-[250px_1fr]">

                            <nav
                                className="grid gap-4 text-sm text-muted-foreground" x-chunk="dashboard-04-chunk-0"
                            >
                                <Link href="#" className="font-semibold text-primary">
                                    General
                                </Link>
                                <Link href="#">Security</Link>
                                <Link href="#">Integrations</Link>
                                <Link href="#">Support</Link>
                                <Link href="#">Organizations</Link>
                                <Link href="#">Advanced</Link>
                            </nav>
                            <div className="grid gap-6">
                                <Card x-chunk="dashboard-04-chunk-2">
                                    <CardHeader>
                                        <CardTitle>General</CardTitle>
                                        <CardDescription>
                                            The directory within your project, in which your plugins are
                                            located.
                                        </CardDescription>
                                    </CardHeader>
                                    <CardContent>
                                        <form className="flex flex-col gap-4">
                                            <Input
                                                placeholder="Project Name"
                                                defaultValue="/content/plugins"
                                            />
                                            <div className="flex items-center space-x-2">
                                                <Checkbox id="include" defaultChecked />
                                                <label
                                                    htmlFor="include"
                                                    className="text-sm font-medium leading-none peer-disabled:cursor-not-allowed peer-disabled:opacity-70"
                                                >
                                                    Allow administrators to change the directory.
                                                </label>
                                            </div>
                                        </form>
                                    </CardContent>
                                    <CardFooter className="border-t px-6 py-4">
                                        <Button>Save</Button>
                                    </CardFooter>
                                </Card>
                            </div>
                        </div>
                    </main>
                </div>
            </div>
        </div>


    )
}
