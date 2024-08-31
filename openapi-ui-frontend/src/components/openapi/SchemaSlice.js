import { createSlice } from '@reduxjs/toolkit'

const initialState = {
    value: [
        { name: "schema1" },
        { name: "schema2" },
        { name: "schema32" },
        { name: "schema4" },
    ],
}

export const schemaSlice = createSlice({
    name: 'schema',
    initialState,
    reducers: {
        addSchema: (state, action) => {
            if (state.value.find(schema => schema.name === action.payload)) {
                return
            }
            state.value.push({ name: action.payload })
        },
        removeSchema: (state, action) => {
            state.value = state.value.filter(schema => schema.name !== action.payload)
        },
        updateSchema: (state, action) => {
            const schema = state.value.find(schema => schema.name === action.payload.name);
            if (schema) {
                schema.name = action.payload.value;
            }
        },
    },
})

export const { addSchema, removeSchema, updateSchema } = schemaSlice.actions

export default schemaSlice.reducer