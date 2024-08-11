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
export { getAllUser, getUserById };