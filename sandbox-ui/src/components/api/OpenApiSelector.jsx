import { Alert, AlertDescription } from "@/components/ui/alert"
import { actions } from "../../services/reducers/OpenApiReducer"
import React from "react"
const MemoizedOpenApiSelector = ({ state, dispatch }) => {
    return (
        <>
            <div className="p-4">
                <h3 className="text-lg font-semibold md:text-2xl">Open API 3.1</h3>
                <p className="text-sm text-muted-foreground pt-3">This is the root object of the OpenAPI document.</p>
            </div>
            <div className="flex flex-col h-full justify-start p-6 flex-wrap">
                <Alert className={`h-12 mt-2 cursor-pointer hover:bg-opacity-50 bg-[${state.info.color}]`} onClick={() => dispatch({ type: actions.INFO_SELECTED })}>
                    <AlertDescription className="text-center font-normal">
                        Info
                    </AlertDescription>
                </Alert>
                <div className="flex justify-start mt-2">
                    <Alert className={`w-2/4 h-12 cursor-pointer hover:bg-opacity-50 bg-[${state.info.status == 'complete' ? state.servers.color : state.default.color}]`} onClick={() => dispatch({ type: actions.SERVERS_SELECTED })}>
                        <AlertDescription className="text-center ">
                            Servers
                        </AlertDescription>
                    </Alert>
                    <Alert className={`w-2/4 h-12 cursor-pointer hover:bg-opacity-50 bg-[${state.servers.status == 'complete' ? state.security.color : state.default.color}]`} onClick={() => dispatch({ type: actions.SECURITY_SELECTED })}>
                        <AlertDescription className="text-center">
                            Security
                        </AlertDescription>
                    </Alert>
                </div>
                <Alert className={`h-32 mt-2 cursor-pointer  hover:bg-opacity-50 flex justify-center flex-col bg-[${state.security.status == 'complete' ? state.paths.color : state.default.color}]`} onClick={() => dispatch({ type: actions.PATHS_SELECTED })}>
                    <AlertDescription className="text-center ">
                        Paths
                    </AlertDescription>
                </Alert>
                <div className="flex justify-start mt-2">
                    <Alert className={`w-2/4 h-12 cursor-pointer hover:bg-opacity-50 bg-[${state.paths.status == 'complete' ? state.tags.color : state.default.color}]`} onClick={() => dispatch({ type: actions.TAGS_SELECTED })}>
                        <AlertDescription className="text-center ">
                            Tags
                        </AlertDescription>
                    </Alert>
                    <Alert className={`w-2/4 h-12 cursor-pointer hover:bg-opacity-50 bg-[${state.tags.status == 'complete' ? state.externalDocs.color : state.default.color}]`} onClick={() => dispatch({ type: actions.EXTERNALDOCS_SELECTED })}>
                        <AlertDescription className="text-center ">
                            externalDocs
                        </AlertDescription>
                    </Alert>
                </div>
                <Alert className={`h-12 mt-2 cursor-pointer hover:bg-opacity-50 bg-[${state.externalDocs.status == 'complete' ? state.components.color : state.default.color}]`} onClick={() => dispatch({ type: actions.COMPONENTS_SELECTED })}>
                    <AlertDescription className="text-center ">
                        Components (reusable objects)
                    </AlertDescription>
                </Alert>
            </div>
        </>
    )
}
const OpenApiSelector = React.memo(MemoizedOpenApiSelector)
export default OpenApiSelector
