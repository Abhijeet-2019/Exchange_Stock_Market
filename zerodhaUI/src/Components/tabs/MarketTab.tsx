import React, { useMemo, useState } from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";

import { Nav, Navbar } from 'react-bootstrap'
import { NavLink } from "react-router-dom";
import MarketWatch from "./MarketWatch";
import MyWatchList from "./MyWatchList";
const MarketTab = () => {

    type TabType = 'marketWatch' | 'myWatchList';
    const [activeTab, setActiveTab] = useState<TabType>('marketWatch');
    // Simple helper for dynamic styling
    const tabButtonStyle = (isActive: boolean): React.CSSProperties => ({
        padding: '10px 20px',
        cursor: 'pointer',
        border: 'none',
        background: 'none',
        borderBottom: isActive ? '5px solid #050f1a' : '3px solid transparent',
        color: isActive ? '#c1cad6' : '#666',
        fontWeight: isActive ? 'bold' : 'normal',
        outline: 'none',
        transition: '0.3s',
    });

    return (
        <div style={{ padding: '20px', fontFamily: 'Arial, sans-serif' }}>
            {/* Tab Headers */}
            <div style={{ display: 'flex', borderBottom: '2px solid #072B47', marginBottom: '15px' }}>
                <button
                    onClick={() => setActiveTab('marketWatch')}
                    style={tabButtonStyle(activeTab === 'marketWatch')}
                >
                    Market Watch
                </button>
                <button
                    onClick={() => setActiveTab('myWatchList')}
                    style={tabButtonStyle(activeTab === 'myWatchList')}
                >
                    My Watchlist
                </button>
            </div>

            {/* Tab Content */}
            <div style={{ padding: '10px', background: '#072B47', borderRadius: '4px' }}>
                {activeTab === 'marketWatch' ? (
                    <MarketWatch />
                ) : (
                    <div>
                        <MyWatchList />
                    </div>
                )}
            </div>
        </div>
    );
}
export default MarketTab;
