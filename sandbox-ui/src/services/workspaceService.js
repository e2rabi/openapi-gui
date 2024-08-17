const rootUrl = import.meta.env.VITE_API_URL;
async function getAllWorkspaces(page, pageSize) {
  try {
    const response = await fetch(
      `${rootUrl}workspaces?page=${page}&pageSize=${pageSize}`,
      {
        headers: {
          Authorization: "Basic " + btoa("admin:admin"),
        },
      }
    );

    if (!response.ok) {
      throw new Error(`Failed to fetch workspaces : ${response.statusText}`);
    }
    const data = await response.json();
    return data;
  } catch (error) {
    console.error("Error fetching data from API:", error);
    throw error;
  }
}

async function getWorkspacesByQuery(status, visibility, page, pageSize) {
  try {
    const response = await fetch(
      `${rootUrl}workspaces/query?status=${status}&visibility=${visibility}&page=${page}&pageSize=${pageSize}`,
      {
        headers: {
          Authorization: "Basic " + btoa("admin:admin"),
        },
      }
    );

    if (!response.ok) {
      throw new Error(`Failed to fetch workspaces : ${response.statusText}`);
    }
    const data = await response.json();
    return data;
  } catch (error) {
    console.error("Error fetching data from API:", error);
    throw error;
  }
}

async function deleteWorkspaces(workspaceId) {
  try {
    const response = await fetch(`${rootUrl}workspace/${workspaceId}`, {
      method: "DELETE",
      headers: {
        Authorization: "Basic " + btoa("admin:admin"),
      },
    });

    if (!response.ok) {
      throw new Error(`Failed to delete workspaces: ${response.statusText}`);
    }

    if (response.status !== 204) {
      const data = await response.json();
      return data;
    }

    return null; // Return null if the response is 204 No Content
  } catch (error) {
    console.error("Error deleting data from Database:", error);
    throw error;
  }
}

export { getAllWorkspaces, getWorkspacesByQuery, deleteWorkspaces };
