const internalError = {
    variant: "destructive",
    title: "Uh oh! Something went wrong.",
    description: "There was a problem with your request.",
};
const UserStatusUpdatedSuccess = {
    variant: "success",
    title: "Success.",
    description: "The user status has been updated.",
};
const UserUpdatedSuccess = {
    variant: "success",
    title: "Success.",
    description: "The user has been updated.",
};
const WorkspaceDeleteSuccess = {
    variant: "success",
    title: "Workspace deleted.",
    description: "The workspace has been deleted.",
};

const UserDeleteSuccess = {
    variant: "success",
    title: "User deleted",
    description: "Successfully deleted user.",
};
const UserCreatedSuccess = {
    variant: "success",
    title: "User created",
    description: "Successfully created user.",
};
const ValidtionError = {
    variant: "destructive",
    title: "Invalid data",
    description: "",
};
export { internalError, UserStatusUpdatedSuccess, UserUpdatedSuccess, WorkspaceDeleteSuccess, UserDeleteSuccess, UserCreatedSuccess, ValidtionError };