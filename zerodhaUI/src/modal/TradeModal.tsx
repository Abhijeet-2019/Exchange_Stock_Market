import React from 'react';
import './trade-modal.css';
import { useState } from 'react';
export type TradeAction = 'BUY' | 'SELL';
type OrderType = 'MARKET' | 'LIMIT';

interface TradeModalProps {
    show: boolean;
    onClose: () => void;
    actionType: TradeAction;
    stock: any;
}


const TradeModal: React.FC<TradeModalProps> = ({
    show,
    onClose,
    actionType,
    stock,
}) => {
    if (!show || !stock) return null;
    const [quantity, setQuantity] = useState(1);
    const [orderType, setOrderType] = useState<OrderType>('MARKET');
    const [limitPrice, setLimitPrice] = useState<number | ''>('');
    const submitOrder = async () => {
        const payload = {
            symbol: stock.tckrSymb,
            action: actionType,
            orderType,
            quantity,
            price: orderType === 'LIMIT' ? limitPrice : null,
        };

        console.log('Order Payload:', payload);

        // TODO: replace with actual API
        // await placeOrder(payload);

        onClose();
    };

    return (
        <div className="modal-overlay">
            <div className="modal-box">
                <div className="modal-header">
                    <h3>{actionType} Stock</h3>
                    <span className="close-btn" onClick={onClose}>×</span>
                </div>

                <div className="modal-body">
                    <p><strong>Name:</strong> {stock.name}</p>
                    <p><strong>Symbol:</strong> {stock.tckrSymb}</p>
                    <hr />
                    <p><strong>Current Price:</strong> ₹{stock.open}</p>
                    <p><strong>Open Price:</strong> ₹{stock.open}</p>
                    <p><strong>Previous Close:</strong> ₹{stock.prevClosePrice}</p>
                                        <hr/>
                    
                    <div className="trade-row-inline">
                        {/* Quantity */}
                        <div className="inline-field">
                            <strong>Quantity</strong>
                            <input
                                type="number"
                                min={1}
                                value={quantity}
                                onChange={(e) => setQuantity(Number(e.target.value))}
                            />
                        </div>

                        {/* Order Type Dropdown */}
                        <div className="inline-field">
                            <strong>Order Type</strong>
                            <select
                                value={orderType}
                                onChange={(e) => setOrderType(e.target.value as OrderType)}
                            >
                                <option value="MARKET">Market</option>
                                <option value="LIMIT">Limit</option>
                            </select>
                        </div>

                        {/* Limit Price (only if LIMIT) */}
                        {orderType === 'LIMIT' && (
                            <div className="inline-field">
                                <label>Limit Price</label>
                                <input
                                    type="number"
                                    value={limitPrice}
                                    onChange={(e) => setLimitPrice(Number(e.target.value))}
                                />
                            </div>
                        )}
                    </div>
                </div>
                <div className="modal-footer">
                    <button onClick={onClose}>Cancel</button>
                    <button className={actionType === 'BUY' ? 'buy' : 'sell'}>
                        Confirm {actionType}
                    </button>
                </div>
            </div>
        </div>
    );
};
export default TradeModal;