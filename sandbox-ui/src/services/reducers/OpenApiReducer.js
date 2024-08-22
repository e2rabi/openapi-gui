
const initialState = {
    currentStep: '',
    default: {
        status: 'todo',
        color: '#737373'
    },
    info: {
        status: 'todo',
        color: '#737373'
    },
    servers: {
        status: 'todo',
        color: '#737373',
    },
    security: {
        status: 'todo',
        color: '#737373'
    },
    paths: {
        status: 'todo',
        color: '#737373'
    },
    tags: {
        status: 'todo',
        color: '#737373'
    },
    externalDocs: {
        status: 'todo',
        color: '#737373'
    },
    components: {
        status: 'todo',
        color: '#737373'
    }
};
const actions = {
    INFO_SELECTED: 'infoSelected',
    SERVERS_SELECTED: 'serversSelected',
    SECURITY_SELECTED: 'securitySelected',
    PATHS_SELECTED: 'pathsSelected',
    TAGS_SELECTED: 'tagsSelected',
    EXTERNALDOCS_SELECTED: 'externalDocsSelected',
    COMPONENTS_SELECTED: 'componentsSelected'
}
function openApiReducer(state, action) {
    switch (action.type) {
        case actions.INFO_SELECTED:
            return { ...state, info: { color: "#16A34A", status: "inProgress" }, currentStep: 'info' };
        case actions.SERVERS_SELECTED:
            return { ...state, servers: { color: "#16A34A", status: "inProgress" }, currentStep: 'servers' };
        case actions.SECURITY_SELECTED:
            return { ...state, security: { color: "#16A34A", status: "inProgress" }, currentStep: 'security' };
        case actions.PATHS_SELECTED:
            return { ...state, paths: { color: "#16A34A", status: "inProgress" }, currentStep: 'paths' };
        case actions.TAGS_SELECTED:
            return { ...state, tags: { color: "#16A34A", status: "inProgress" }, currentStep: 'tags' };
        case actions.EXTERNALDOCS_SELECTED:
            return { ...state, externalDocs: { color: "#16A34A", status: "inProgress" }, currentStep: 'externalDocs' };
        case actions.COMPONENTS_SELECTED:
            return { ...state, components: { color: "#16A34A", status: "inProgress" }, currentStep: 'components' };
        default:
            throw new Error();
    }
}
export { initialState, openApiReducer, actions };