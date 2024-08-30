import { configureStore } from '@reduxjs/toolkit'
import schemaReducer from './components/openapi/SchemaSlice'
export const store = configureStore({
    reducer: {
        schema: schemaReducer,
    },
})