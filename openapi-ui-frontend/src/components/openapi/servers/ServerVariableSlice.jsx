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
            console.log("delete by id", action.payload.id)
            state.value = state.value.filter(e => {
                let obj = JSON.parse(JSON.stringify(e));
                console.log(obj)
                return obj.id !== action.payload.id;
            })
        },
        addServerVariable: (state, action) => {
            state.value.push(action.payload)
        },
        removeServerVariable: (state, action) => {
            console.log("delete by id", action.payload.id)
            state.value = state.value.filter(e => {
                let obj = JSON.parse(JSON.stringify(e));
                console.log(obj)
                return obj.id !== action.payload.id;
            })
        },
    },
})
export const { addServerVariable, removeServerVariable, addServerEnum } = ServerVariableSlice.actions

export default ServerVariableSlice.reducer