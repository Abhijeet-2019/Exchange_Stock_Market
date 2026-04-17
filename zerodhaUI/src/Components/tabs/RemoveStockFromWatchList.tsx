import React from 'react';
import type { ICellRendererParams } from 'ag-grid-community';
import { IoRemoveCircle } from "react-icons/io5"; // Ionicons version
interface StocName extends ICellRendererParams {
    onAddStock: (row: any) => void;
}


const RemoveStockFromList: React.FC<StocName> = (props) => {
    const { data, onAddStock } = props;
    return (
        <div className='add-stockIcon'>

            <IoRemoveCircle
                size={30}
                style={{ cursor: 'pointer', color: 'red' }}
                title='Remove from Watch List'
                onClick={() => onAddStock(data)}
            />
        </div>
    );
}
export default RemoveStockFromList;