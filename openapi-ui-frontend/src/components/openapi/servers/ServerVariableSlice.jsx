import { Description } from '@mui/icons-material'
import { createSlice } from '@reduxjs/toolkit'

const initialState = {
    value: [
        { id: '1', name: "value1", Description: "Description1", default: "default1", enums: [{ id: '1', value: "enum 1" }] },
        { id: '2', name: "value2", Description: "Description2", default: "default2", enums: [{ id: '1', value: "enum 1" }] },
    ],
}
export const ServerVariableSlice = createSlice({
    name: 'serverVariable',
    initialState,
    reducers: {
        addServerEnum: (state, action) => {
            state.value.push(action.payload)
        },
        addServerVariable: (state, action) => {
            console.log(action.payload)
            state.value.push(action.payload)
        },
        removeServerVariable: (state, action) => {
            state.value = state.value.filter(server => server.id !== action.payload)
        },
    },
})
export const { addServerVariable, removeServerVariable, addServerEnum } = ServerVariableSlice.actions

export default ServerVariableSlice.reducer