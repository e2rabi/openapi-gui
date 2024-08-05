export async function fetchApi(endpoint, options = {}) {
  const rootUrl = import.meta.env.VITE_API_URL;
  try {
    const response = await fetch(`${rootUrl}${endpoint}`, {
      headers: {
        Authorization: "Basic " + btoa("admin:admin"),
        "Content-Type": "application/json",
        ...options.headers,
      },
      ...options,
    });

    if (!response.ok) {
      throw new Error(
        `Failed to fetch from ${endpoint}: ${response.statusText}`
      );
    }

    const data = await response.json();
    return data;
  } catch (error) {
    console.error("Error fetching data from API:", error);
    throw error;
  }
}
