import React from 'react';
import type { ICellRendererParams } from 'ag-grid-community';
import { IoAddCircle } from "react-icons/io5"; // Ionicons version

interface StocName extends ICellRendererParams {
    onAddStock: (row: any) => void;
}``

const AddStockToWatchList: React.FC<StocName> = (props) => {
    const { data, onAddStock } = props;
    return (
        <div>
            <IoAddCircle
                size={30}
                style={{ cursor: 'pointer', color:'green' }}
                title='Add to Watch List'
            // onClick={onAddStock(data)}
            />
        </div>
    );
}
export default AddStockToWatchList