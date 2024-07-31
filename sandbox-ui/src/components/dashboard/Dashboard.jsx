import Header from '../layout/Header';
import Logo from '../layout/Logo';
import Navbar from '../layout/Navbar';
import Main from "../layout/Main";

export default function Dashboard() {
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
                <Main />
            </div>
        </div>
    )
}
