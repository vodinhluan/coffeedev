import { useState, useEffect } from "react";

const useFetchData = <T,>(url: string, options: RequestInit = {}) => {
  const [data, setData] = useState<T | null>(null);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string>("");

  useEffect(() => {
    const fetchData = async () => {
      try {
        const token = localStorage.getItem("token"); 
        // console.log('token:  ', token);
        if (!token) throw new Error("Unauthorized: No token found");

        const res = await fetch(url, {
          ...options,
          headers: {
            ...options.headers,
            Authorization: `Bearer ${token}`, 
            "Content-Type": "application/json",
          },
        });

        if (!res.ok) throw new Error(`Error: ${res.status} - ${res.statusText}`);
        const json: T = await res.json();
        setData(json);
      } catch (err: unknown) {
        setError((err as Error).message);
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, [url]);

  return { data, loading, error, setData };
};

export default useFetchData;
