import React, { useMemo } from "react";
import { AgGridReact } from 'ag-grid-react';
import type { ColDef, CellClassParams } from 'ag-grid-community';
import { StockDetails } from "../../domains/StockDetails";
import AddStockToWatchList from "./AddStockToWatchList";
import RemoveStockFromList from "./RemoveStockFromWatchList";

interface MyWatchListTableProps {
    stockDetails: StockDetails[];
}
const MyWatchListTable: React.FC<MyWatchListTableProps> = ({ stockDetails }) => {


    const [showModal, setShowModal] = React.useState(false);
    // const [data ,setData] = React.useState(stockDetails);
    const columnDefs = useMemo<ColDef[]>(() => [
        {
            headerName: "Add/Remove",
            width: 50,
            cellRendererSelector: (params) => {
                if (params.data.addedToWatchList) {                                        
                    return {
                        component: RemoveStockFromList,
                        params: {
                            onAddStock: (row :any) => addStock(row, 'Add')
                        }
                    };
                } else{
                    return {
                        component: AddStockToWatchList,
                        params: {
                            onAddStock: (row :any) => addStock(row, 'Add')
                        }
                    };
                }
            }
        },
        {
            headerName: 'Symbol',
            field: 'tckrSymb',
        },
        {
            headerName: 'ISIN',
            field: 'isin',
        },
        {
            headerName: 'Name',
            field: 'name',
            cellClass: 'text-blue',
        },
        {
            headerName: 'Open Price',
            field: 'open',
            filter: 'agNumberColumnFilter',
        },
        {
            headerName: 'High Price',
            field: 'high',
            filter: 'agNumberColumnFilter',
        },
        {
            headerName: 'Low Price',
            field: 'low',
            filter: 'agNumberColumnFilter',
        },
        {
            headerName: 'Previous Close Price',
            field: 'prevClosePrice',
            filter: 'agNumberColumnFilter',
        },
        {
            headerName: 'Traded Quaintity',
            field: 'totalTradedQty',
            filter: 'agNumberColumnFilter',
        }, {
            headerName: '52 Week High',
            field: 'yearWeekHigh',
            filter: 'agNumberColumnFilter',
        }, {
            headerName: '52 Week Low',
            field: 'yearWeekLow',
            filter: 'agNumberColumnFilter',
        },
        {
            headerName: 'Industry',
            field: 'industry',
            filter: 'agNumberColumnFilter',
        }
    ], []);
    const defaultColDef = useMemo<ColDef>(() => ({
        sortable: true,
        filter: true,
        resizable: true,
        flex: 1,
        minWidth: 120,
        cellClass: 'cell-bold',

    }), []);

    const addStock = (rowData: any, action: any) => {
        alert("Hi I need to add this stock to my Wish List ==>" + rowData.name);
    };

    return (
        <div>
            <div
                className="ag-theme-quartz"
                style={{ height: '600px', width: '100%' }}>
                <AgGridReact
                    theme="legacy"
                    rowData={stockDetails}
                    columnDefs={columnDefs}
                    defaultColDef={defaultColDef}
                    animateRows
                    pagination
                    paginationPageSize={20}
                />
            </div>
        </div>
    )
}
export default MyWatchListTable