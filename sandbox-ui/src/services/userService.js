const rootUrl = import.meta.env.VITE_API_URL;
async function getAllUser(page, pageSize) {
    try {
        const response = await fetch(`${rootUrl}users?page=${page}&pageSize=${pageSize}`,
            {
                headers: {
                    Authorization: "Basic " + btoa("admin:admin"),
                },
            });

        if (!response.ok) {
            throw new Error(
                `Failed to fetch users : ${response.statusText}`
            );
        }
        const data = await response.json();
        return data;
    } catch (error) {
        console.error("Error fetching data from API:", error);
        throw error;
    }
}
async function getUsersByQuery(status, email, userName, page, pageSize) {
    try {
        const response = await fetch(`${rootUrl}users/query?status=${status}&email=${email}&userName=${userName}&page=${page}&pageSize=${pageSize}`,
            {
                headers: {
                    Authorization: "Basic " + btoa("admin:admin"),
                },
            });

        if (!response.ok) {
            throw new Error(
                `Failed to fetch users : ${response.statusText}`
            );
        }
        const data = await response.json();
        return data;
    } catch (error) {
        console.error("Error fetching data from API:", error);
        throw error;
    }
}
async function getUserById(userId) {
    try {
        const response = await fetch(`${rootUrl}users/${userId}`,
            {
                headers: {
                    Authorization: "Basic " + btoa("admin:admin"),
                },
            });

        if (!response.ok) {
            throw new Error(
                `Failed to fetch users : ${response.statusText}`
            );
        }
        const data = await response.json();
        return data;
    } catch (error) {
        console.error("Error fetching data from API:", error);
        throw error;
    }
}
async function changeUserStatus(userId, status) {
    try {
        const response = await fetch(`${rootUrl}users/${userId}?status=${status}`,
            {
                method: 'PATCH',
                headers: {
                    Authorization: "Basic " + btoa("admin:admin"),
                },
            });

        if (!response.ok) {
            throw new Error(
                `Failed to fetch users : ${response.statusText}`
            );
        }
        const data = await response;
        return data;
    } catch (error) {
        console.error("Error change user status data from API:", error);
        throw error;
    }
}
async function updateUser(user) {
    try {
        const response = await fetch(`${rootUrl}users`,
            {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: "Basic " + btoa("admin:admin"),
                },
                body: JSON.stringify({
                    ...user
                })
            });

        if (!response.ok) {
            throw new Error(
                `Failed to update user: ${response.statusText}`
            );
        }
        const data = await response.json();
        return data;
    } catch (error) {
        console.error("Error updating user from API:", error);
        throw error;
    }
}
async function deleteUserById(userId) {
    try {
        const response = await fetch(`${rootUrl}users/${userId}`,
            {
                method: 'DELETE',
                headers: {
                    Authorization: "Basic " + btoa("admin:admin"),
                },
            });

        if (!response.ok) {
            throw new Error(
                `Failed to fetch users : ${response.statusText}`
            );
        }
        const data = await response;
        return data;
    } catch (error) {
        console.error("Error delete user from API:", error);
        throw error;
    }
}
async function saveUser(user) {
    try {
        const response = await fetch(`${rootUrl}users`,
            {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: "Basic " + btoa("admin:admin"),
                },
                body: JSON.stringify({
                    username: user.username.trim(),
                    email: user.email.trim(),
                    phone: user.phone.trim(),
                    firstName: user.firstName.trim(),
                    lastName: user.lastName.trim(),
                    enabled: user.enabled,
                    expiryDate: user.expiryDate,
                    firstLoginChangePassword: user.firstLoginChangePassword,
                })
            });

        if (!response.ok) {
            const error = await response.json();
            throw error;
        }
        const data = await response.json();
        return data;
    } catch (error) {
        console.error("Error saving user from API:", error);
        throw error;
    }
}
export { getAllUser, getUserById, getUsersByQuery, changeUserStatus, updateUser, deleteUserById, saveUser };