import { createSlice } from '@reduxjs/toolkit'

const initialState = {
    value: [
        { id: '1', value: "value1" },
        { id: '2', value: "value2" },
        { id: '3', value: "value3" },
        { id: '4', value: "value4" },
    ],
}
export const serverSlice = createSlice({
    name: 'server',
    initialState,
    reducers: {
        addServer: (state, action) => {
            console.log(action.payload)
            state.value.push(action.payload)
        },
        removeServer: (state, action) => {
            state.value = state.value.filter(server => server.id !== action.payload)
        },
    },
})
export const { addServer, removeServer } = serverSlice.actions

export default serverSlice.reducer