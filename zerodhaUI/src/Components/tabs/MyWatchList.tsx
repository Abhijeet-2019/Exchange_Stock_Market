import React from "react";
import { useAuth } from "../../AuthContext";
import { useQuery } from '@tanstack/react-query';

import { useState, useEffect } from "react";
import styled from "styled-components";
import { IoSearchSharp } from "react-icons/io5"; // Ionicons version
import { IoSearchCircle } from "react-icons/io5"; // Ionicons version
import { fetchLatestData } from "../../service/HttpService";
import MyWatchListTable from "./MyWatchListTable";
import { StockDetails } from "../../domains/StockDetails";
import { fetchUserStockWatchList } from "../../service/HttpService";

const MyWatchList = () => {
    const stockNamePlaceHolder = "Please Enter Stock Name";
    const [stockName, setSetStockName] = useState("");
    const [stockDetails, setStockDetails] = useState<StockDetails[]>([]);

    const { user } = useAuth();

    const { data, isLoading, error } = useQuery({
        queryKey: ['userWatchList', user?.email],
        queryFn: async () => {
            const email = user?.email;
            const result = await fetchUserStockWatchList(user!.email);
            setStockDetails(result.data.userWatchList);
            return result.data.userWatchList;
        }, refetchOnWindowFocus: false,
        refetchOnReconnect: false,
        refetchOnMount: false,
        gcTime: 1000 * 60 * 10,
    });

    // this is called when we need to update the List...
    useEffect(() => {
        console.log("The result" + data)
        if (stockDetails.length > 0) {
            sessionStorage.setItem("watchlist", JSON.stringify(stockDetails));
        }
    }, [stockDetails]);



    // This use Effect is used when we need to fetch data 
    useEffect(() => {
        const saved = sessionStorage.getItem("watchlist");
        if (saved) {
            setStockDetails(JSON.parse(saved));
        }
    }, [])

    const retrieveStockDetails = (e: any) => {
        e.preventDefault();
        // Fetch Stock Details from Server
        fetchStockDetailRequest(stockName);
        setSetStockName("");
    }

    const fetchStockDetailRequest = async (stockName: string): Promise<any> => {

        try {
            const result = await fetchLatestData(stockName);
            setStockDetails(prev => {
                const newList = prev.concat(result.data);
                return newList;
            });
            console.log("The complete stock details" + stockDetails);
        } catch (error) {
            console.log(error)
        }
    }
    if (isLoading) return <div>Loading market data...</div>;
    if (error) return <div>Error loading data</div>;

    return (
        <div>
            {/* Top Search Bar Row */}
            <div style={{ display: 'flex', alignItems: 'center', gap: '8px', marginBottom: '10px' }}>
                <input
                    type="text"
                    className="form-control"
                    placeholder={stockNamePlaceHolder}
                    value={stockName}
                    style={{ width: 350 }}
                    onChange={(e) => setSetStockName(e.target.value)}
                />
                <IoSearchSharp
                    size={30}
                    className="search-icon"
                    style={{ cursor: 'pointer' }}
                    onClick={retrieveStockDetails}
                />
            </div>

            {/* The Horizontal Line */}
            <hr style={{ border: '0', borderTop: '1px solid #ccc', margin: '20px 0' }} />

            {/* Table Section Below */}
            <MyWatchListTable stockDetails={stockDetails} />
        </div>
    );

};
export default MyWatchList