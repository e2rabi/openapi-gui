import { useState, useEffect, useRef, useDebugValue } from 'react';
import { getAllWorkspaces } from "../services/workspaceService.js";
import { useToast } from "@/components/ui/use-toast"
import { internalError } from "../services/MessageConstant.js";

function useWorkspace() {
    const { toast } = useToast()
    const [workspaces, setWorkspaces] = useState([]);
    const hasFetched = useRef(false);
    const [isFetching, setIsFetching] = useState(true);

    useEffect(() => {
        const fetchWorkspaces = async (page = 0, pageSize = 1000) => {
            if (hasFetched.current) {
                setIsFetching(false);
                return;
            }
            hasFetched.current = true;
            console.info("Fetching workspaces");
            const controller = new AbortController();
            try {
                const data = await getAllWorkspaces(page, pageSize);
                setWorkspaces(data.content);
            } catch (error) {
                toast(internalError);
                console.error("Error fetching workspaces:", error);
            } finally {
                setIsFetching(false);
            }
            return () => controller.abort();
        };
        fetchWorkspaces();
    }, [toast]);

    useDebugValue(isFetching ? "Fetching workspaces..." : "Workspaces fetched");
    return { workspaces, isFetching };
}
export default useWorkspace;