import Header from '../layout/Header';
import Logo from '../layout/Logo';
import Navbar from '../layout/Navbar';
import { Button } from "../ui/button"
export default function User() {
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
                <main className="flex flex-1 flex-col gap-4 p-4 lg:gap-6 lg:p-6">
                    <div className="flex items-center">
                        <h1 className="text-lg font-semibold md:text-2xl">Users</h1>
                    </div>
                    <div
                        className="flex flex-1 items-center justify-center rounded-lg border border-dashed shadow-sm" x-chunk="dashboard-02-chunk-1"
                    >
                        <div className="flex flex-col items-center gap-1 text-center">
                            <h3 className="text-2xl font-bold tracking-tight">
                                You have no user
                            </h3>
                            <p className="text-sm text-muted-foreground">
                                You can start as soon as you add a user.
                            </p>
                            <Button className="mt-4">Add User</Button>
                        </div>
                    </div>
                </main>
            </div>
        </div>
    )
}
