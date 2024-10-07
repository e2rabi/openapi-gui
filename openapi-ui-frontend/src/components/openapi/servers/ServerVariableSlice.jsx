import { Description } from '@mui/icons-material'
import { createSlice } from '@reduxjs/toolkit'

const initialState = {
    value: [
        { id: '1', name: "value1", Description: "Description1", default: "default1" },
        { id: '2', name: "value2", Description: "Description2", default: "default2" },
        { id: '3', name: "value3", Description: "Description3", default: "default3" },
        { id: '4', name: "value4", Description: "Description4", default: "default4" },
    ],
}
export const ServerVariableSlice = createSlice({
    name: 'serverVariable',
    initialState,
    reducers: {
        addServerVariable: (state, action) => {
            console.log(action.payload)
            state.value.push(action.payload)
        },
        removeServerVariable: (state, action) => {
            state.value = state.value.filter(server => server.id !== action.payload)
        },
    },
})
export const { addServerVariable, removeServerVariable } = ServerVariableSlice.actions

export default ServerVariableSlice.reducer