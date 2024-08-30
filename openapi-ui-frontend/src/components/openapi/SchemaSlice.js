import { createSlice } from '@reduxjs/toolkit'

const initialState = {
    value: [{
        name: "schema1",
    }, {
        name: "schema2",
    }
        , {
        name: "schema32",
    }
        , {
        name: "schema4",
    }
    ],
}

export const schemaSlice = createSlice({
    name: 'schema',
    initialState,
    reducers: {
        addSchema: (state, schemaName) => {
            state.value.push(schemaName)
        },
    },
})

export const { addSchema } = schemaSlice.actions

export default schemaSlice.reducer