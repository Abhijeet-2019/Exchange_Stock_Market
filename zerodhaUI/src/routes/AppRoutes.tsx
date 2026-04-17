import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import React, { Suspense, lazy } from 'react';
import { MainTabBar } from "../Components/MainTabBar";

import Portfolio from "../Components/tabs/Portfolio";
import Orders from "../Components/tabs/Orders";
import Home from "../Components/tabs/Home";
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import MarketTab from "../Components/tabs/MarketTab";
export const AppRoutes = () => {
    const queryClient = new QueryClient();
        
    return (
        <QueryClientProvider client={queryClient}>
            <Router>
                <div className="App-header">
                    <Suspense fallback={<div>Loading page...</div>}>
                        <MainTabBar />
                        <Routes>
                            <Route path="/" element={<MarketTab />} />
                            <Route path="/market" element={<MarketTab />} />
                            <Route path="/orders" element={<Orders />} />
                            <Route path="/portfolio" element={<Portfolio />} />
                            <Route path="/home" element={<Home />} />
                        </Routes>
                    </Suspense>
                </div>
            </Router>
        </QueryClientProvider>
    );
}
export default AppRoutes;
