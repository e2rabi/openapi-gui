async function getAllReleases(page, pageSize) {
  const rootUrl = import.meta.env.VITE_API_URL;
  try {
    const response = await fetch(
      `${rootUrl}releases?page=${page}&pageSize=${pageSize}`,
      {
        headers: {
          Authorization: "Basic " + btoa("admin:admin"),
        },
      }
    );

    if (!response.ok) {
      throw new Error(`Failed to fetch releases : ${response.statusText}`);
    }
    return await response.json();
  } catch (error) {
    console.error("Error fetching data from API:", error);
    throw error;
  }
}
export { getAllReleases };