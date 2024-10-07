import { configureStore } from '@reduxjs/toolkit'
import schemaReducer from './components/openapi/schema/SchemaSlice'
import serverEnumReducer from './components/openapi/servers/ServerEnumSlice'
import serverVariableReducer from './components/openapi/servers/ServerVariableSlice'
export const store = configureStore({
    reducer: {
        schema: schemaReducer,
        serverEnum: serverEnumReducer,
        serverVariable: serverVariableReducer,
    },
})