export interface StockDetails {
  tckrSymb: string;
  market: string;
  isin: string;
  name: string;
  open: number;         // double in Java is number in JS
  high: number;
  low: number;
  prevClosePrice: number;
  close: number;
  lastTradedDate: string;
  totalTradedQty: number;
}