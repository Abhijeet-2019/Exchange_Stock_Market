import React, { useMemo } from "react";
import { useAuth } from "../../AuthContext";
import { useQuery } from '@tanstack/react-query';
import { fetchAllMarketData } from "../../service/HttpService";

import { AgGridReact } from 'ag-grid-react';
import type { ColDef, CellClassParams } from 'ag-grid-community';
import type { ICellRendererParams } from 'ag-grid-community';
import BuySellCell from './BuySellCell';

import 'ag-grid-community/styles/ag-grid.css';
import 'ag-grid-community/styles/ag-theme-quartz.css';
import './ag-grid-custom.css';
//Add modal
import TradeModal, { TradeAction } from '../../modal/TradeModal';

const MarketWatch = () => {
  const { user } = useAuth();

  const { data, isLoading, error } = useQuery({
    queryKey: ['marketDetails', user?.email],
    queryFn: async () => {
      const result = await fetchAllMarketData(50); 
      return result.data.stockDetails;
    },
    refetchOnWindowFocus: false,
    refetchOnReconnect: false,
    refetchOnMount: false,
    gcTime: 1000 * 60 * 10,
  
  });

  // add code for modal 
  const [showModal, setShowModal] = React.useState(false);
  const [selectedStock, setSelectedStock] = React.useState<any>(null);
  const [actionType, setActionType] = React.useState<TradeAction>('BUY');

  const openTradeModal = (rowData: any, action: TradeAction) => {
    setSelectedStock(rowData);
    setActionType(action);
    setShowModal(true);
  };

  const closeModal = () => {
    setShowModal(false);
    setSelectedStock(null);
  };


  const columnDefs = useMemo<ColDef[]>(() => [
    {
      headerName: 'Symbol',
      field: 'tckrSymb',
      pinned: 'left',
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
    },
    {
      headerName: 'Change',
      valueGetter: (params) => {
        const open = Number(params.data?.open ?? 0);
        const prevClose = Number(params.data?.prevClosePrice ?? 0);
        return +(open - prevClose).toFixed(2);
      },
      cellClassRules: {
        'text-green': (p: CellClassParams) => Number(p.value) > 0,
        'text-red': (p: CellClassParams) => Number(p.value) < 0,
      },
    },
    {
      headerName: 'Action',
      pinned: 'right',
      width: 80,
      cellRenderer: BuySellCell,
      cellRendererParams: {
        onBuy: (row: any) => openTradeModal(row, 'BUY'),
        onSell: (row: any) => openTradeModal(row, 'SELL'),
      },
    },

  ], []);

  const defaultColDef = useMemo<ColDef>(() => ({
    sortable: true,
    filter: true,
    resizable: true,
    flex: 1,
    minWidth: 120,
    cellClass: 'cell-bold',

  }), []);

  if (isLoading) return <div>Loading market data...</div>;
  if (error) return <div>Error loading data</div>;

  return (
    <div>
      <p>Market Watch</p>
      <div
        className="ag-theme-quartz"
        style={{ height: '600px', width: '100%' }}
      >
        <AgGridReact
          theme="legacy"
          rowData={data}
          columnDefs={columnDefs}
          defaultColDef={defaultColDef}
          rowSelection="single"
          animateRows
          // pagination
          // paginationPageSize={20}
        />
      </div>
      <TradeModal
        show={showModal}
        onClose={closeModal}
        actionType={actionType}
        stock={selectedStock}
      />
    </div>
  );

};

export default MarketWatch;
