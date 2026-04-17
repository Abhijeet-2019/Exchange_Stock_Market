import React from 'react';
import type { ICellRendererParams } from 'ag-grid-community';
import { IoAddCircle } from "react-icons/io5"; // Ionicons version
import { IoRemoveCircle } from "react-icons/io5"; // Ionicons version
interface BuySellCellProps extends ICellRendererParams {
  onBuy: (row: any) => void;
  onSell: (row: any) => void;
}


const BuySellCell: React.FC<BuySellCellProps> = (props) => {
  const { data, onBuy, onSell } = props;
  return (
    <div>
      <IoAddCircle
        style={{ cursor: 'pointer', color: 'green', paddingRight: '5px' }}
        size={30}
        title="Buy Stock"
        onClick={() => onBuy(data)}
      />
      <IoRemoveCircle
        style={{ cursor: 'pointer', color: 'red', paddingRight: '5px' }}
        size={30}
        title='Sell Stock'
        onClick={() => onBuy(data)}
      />
    </div>
  );
};

export default BuySellCell;
