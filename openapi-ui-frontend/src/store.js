import { configureStore } from '@reduxjs/toolkit'
import schemaReducer from './components/openapi/schema/SchemaSlice'
import serverReducer from './components/openapi/servers/ServerSlice'
export const store = configureStore({
    reducer: {
        schema: schemaReducer,
        server: serverReducer
    },
})