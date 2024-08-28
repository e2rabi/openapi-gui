import React, { useState } from 'react'
import {
    Command,
    CommandEmpty,
    CommandGroup,
    CommandInput,
    CommandItem,
    CommandList,
} from "@/components/ui/command"
import {
    Popover,
    PopoverContent,
    PopoverTrigger,
} from "@/components/ui/popover"
import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"
const infoObjects = [
    {
        value: "next.js",
        label: "API 1",
    },
    {
        value: "sveltekit",
        label: "API 2",
    },
    {
        value: "nuxt.js",
        label: "API 3",
    },
    {
        value: "remix",
        label: "API 4",
    },
    {
        value: "astro",
        label: "API 5",
    },
]
import { cn } from "@/lib/utils"
import { Check, ChevronsUpDown } from "lucide-react"
const InfoObject = () => {
    const [open, setOpen] = useState(false)
    const [value, setValue] = useState("")
    return (
        <>
            <div className="p-4">
                <h3 className="text-lg font-semibold md:text-2xl">Info Object</h3>
                <p className="text-sm text-muted-foreground pt-3">The object provides metadata about the API. The metadata MAY be used by the clients if needed, and MAY be presented in editing or documentation generation tools for convenience.</p>
            </div>
            <div className="flex flex-col space-y-1.5 mt-1 w-full ml-4">
                <Label htmlFor="lastName">From an existing object : </Label>
                <Popover open={open} onOpenChange={setOpen}>
                    <PopoverTrigger asChild>
                        <Button
                            variant="outline"
                            role="combobox"
                            aria-expanded={open}
                            className="w-[200px] justify-between"
                        >
                            {value
                                ? infoObjects.find((infoObject) => infoObject.value === value)?.label
                                : "Clone existing info ..."}
                            <ChevronsUpDown className="ml-2 h-4 w-4 shrink-0 opacity-50" />
                        </Button>
                    </PopoverTrigger>
                    <PopoverContent className="w-[200px] p-0">
                        <Command>
                            <CommandInput placeholder="Search info object..." />
                            <CommandList>
                                <CommandEmpty>No framework found.</CommandEmpty>
                                <CommandGroup>
                                    {infoObjects.map((framework) => (
                                        <CommandItem
                                            key={framework.value}
                                            value={framework.value}
                                            onSelect={(currentValue) => {
                                                setValue(currentValue === value ? "" : currentValue)
                                                setOpen(false)
                                            }}
                                        >
                                            <Check
                                                className={cn(
                                                    "mr-2 h-4 w-4",
                                                    value === framework.value ? "opacity-100" : "opacity-0"
                                                )}
                                            />
                                            {framework.label}
                                        </CommandItem>
                                    ))}
                                </CommandGroup>
                            </CommandList>
                        </Command>
                    </PopoverContent>
                </Popover>
            </div>
            <div className="flex h-full justify-start p-6 ">
                <form>
                    <div className="grid gap-6">
                        <div className="flex justify-start flex-wrap">

                            <div className="flex flex-col space-y-1.5 mt-1 w-full">
                                <Label htmlFor="username">Title</Label>
                                <Input className="relative top-1" id="username" placeholder="" />
                            </div>
                            <div className="flex flex-col space-y-1.5 mt-5 w-full">
                                <Label htmlFor="email">Summary</Label>
                                <Input className="relative top-1" id="email" placeholder="" />
                            </div>
                            <div className="flex flex-col space-y-1.5 mt-5 w-full">
                                <Label htmlFor="phone">Description</Label>
                                <Input className="relative top-1" id="phone" placeholder="" />
                            </div>
                            <div className="flex flex-col space-y-1.5 mt-5 w-full">
                                <Label htmlFor="firstName">Contact</Label>
                                <Input className="relative top-1" name="firstName" id="firstName" placeholder="" />
                            </div>
                            <div className="flex justify-center w-full">
                                <div className="flex flex-col space-y-1.5 mt-5 w-1/2 p-2">
                                    <Label htmlFor="email">Licence</Label>
                                    <Input className="relative top-1" id="email" placeholder="" />
                                </div>
                                <div className="flex flex-col space-y-1.5 mt-5 w-1/2 p-2">
                                    <Label htmlFor="phone">Version</Label>
                                    <Input className="relative top-1" id="phone" placeholder="" />
                                </div>
                            </div>

                            <input type="submit" hidden />
                        </div>
                    </div>
                </form>
            </div>
        </>
    )
}

export default InfoObject
