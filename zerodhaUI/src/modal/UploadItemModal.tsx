import React, { useState } from "react";
import './LoginModal.css';
import Papa from "papaparse";

import { Button } from "react-bootstrap";
import styled from "styled-components";

import { ItemDetails } from "../models/itemDetails";
import { InventoryTransaction } from "../models/InventoryTransaction";

import { persistDataInElastic } from "../service/HttpService";

const ButtonPanel = styled.label`
   padding-top: 20px; 
   padding-left: 15px;     
   flex-flow: column;   
   align: left
  `;

interface UploadItemModalProps {
    show: boolean;
    onClose: () => void;
}

const UploadItemModal = (props: UploadItemModalProps) => {

    const [hide, setHide] = useState(true);
    const [excelFile, setExcelFile] = useState(null);
    const [typeError, setTypeError] = useState("");


    const handleFile = (event: any) => {
        let fileTypes = ['application/vnd.ms-excel', 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet', 'text/csv'];
        let selectedFile = event.target.files[0];
        if (selectedFile) {
            if (selectedFile && fileTypes.includes(selectedFile.type)) {
                setTypeError("");
                let reader = new FileReader();
                reader.readAsArrayBuffer(selectedFile);
                reader.onload = (e) => {
                    console.log(event.target.files[0].name);
                    setExcelFile(event.target.files[0]);
                }
            }
            else {
                setTypeError("Please select only csv file types");
                setExcelFile(null);
            }
        }
        else {
            console.log('Please select your file');
        }
    }

    const close = (event: any) => {
        props.onClose();
    }

    const handleFileSubmit = (event: any) => {
        event.preventDefault();
        console.log(excelFile)
        if (excelFile !== null) {
            Papa.parse(excelFile, {
                header: true, // Ensures the first row is treated as keys
                skipEmptyLines: true, // Skips empty rows
                delimitersToGuess: [",", "\t", ";", "|"], // Let Papa try these delimiters
                complete: (result) => {
                    if (result.errors.length > 0) {
                        setTypeError(result.errors[0].message);
                    } else {
                        const dataTpPersist: Array<any> = mapDataToObject(result.data);
                        if (dataTpPersist.length !== 0) {
                            bulkUploadData(dataTpPersist);
                        }
                    }
                },
                error: (err) => {
                    setTypeError(err.message);
                },
            });
        };
    }

    const bulkUploadData = async (bulkDataToPersist: Array<any>): Promise<any> => {
        let result;
        try {
            result = await persistDataInElastic(bulkDataToPersist);
            console.log(" The Result Map we receive" + result.data)
            if (result.data == '') {

            } else {
            }
        } catch (e) {
            console.log(e);
        }
    }

    const mapDataToObject = (rows: any[]): Array<any> => {

        let uploadedData = new Array<any>()
        rows.map((item, index) => {
            {
                let itemId!: string;
                let invalidColumn !: boolean;
                let itemCategory !: string;
                let itemCode !: string;
                let itemQuantity!: string;
                let itemPrice !: string;
                let itemSoldBy !: string;
                Object.entries(item).map(([key, value]) => {
                    console.log("Keys---" + key)
                    let columnNames = ['Item ID', 'Item category', 'Item Name', 'Item Quaintity', 'Item Price', 'Item Seller'];
                    if (!columnNames.includes(key)) {
                        invalidColumn = true;
                    } else {
                        invalidColumn = false;
                    }
                    if (key === "Item category" && !invalidColumn) {
                        itemCategory = value + "";
                    }
                    if (key === "Item Name" && !invalidColumn) {
                        itemCode = value + "";
                    }
                    if (key === "Item Quaintity" && !invalidColumn) {
                        itemQuantity = value + "";
                    }
                    if (key === "Item Price" && !invalidColumn) {
                        itemPrice = value + "";
                    }
                    if (key === "Item Seller" && !invalidColumn) {
                        itemSoldBy = value + "";
                    }
                })
                console.log("___" + invalidColumn);
                if (!invalidColumn) {
                    const itemTransaction: InventoryTransaction
                        = {
                        customerEmail: "abhijeet.mohanty",
                        transactionType: "Items Added By Admin",
                        transactionQuantity: itemQuantity,
                        transactionDate: getCurrentDate()
                    }
                    const row: ItemDetails = {itemId,
                        itemCategory, itemCode,
                        itemQuantity, itemPrice, itemSoldBy,itemAddedDate: getCurrentDate() ,itemTransaction
                    };
                    return uploadedData.push(row);
                
                } else {
                    alert("Invalid Column");
                }
            }
        });
        console.log("The entire data" + uploadedData.length + "total data" + uploadedData);
        return uploadedData;
    }




    function getCurrentDate() {
        const currentDate = new Date();
        const formattedDate = currentDate.toISOString();
        console.log(formattedDate);
        return formattedDate
    }



    return (
        <div className="Model-Backdrop"  >
            <div className="Upload-Modal-content" onClick={(e) => e.stopPropagation()}
                style={{ display: hide ? "block" : "none" }}  >
                <h2>Please upload Item Added Sheet</h2>
                <div className="Form-group">
                    <label htmlFor="username">Browse file :</label>
                    <input className="form-control"
                        type="file"
                        onChange={handleFile}
                        id="fileLocation"
                        required
                    />
                </div>
                {typeError && (
                    <div className="alert alert-danger" role="alert">{typeError}</div>
                )}
                <ButtonPanel>
                    <Button onClick={handleFileSubmit}>Upload File</Button>
                </ButtonPanel>
                <ButtonPanel>
                    <Button onClick={close}>CLose</Button>
                </ButtonPanel>
            </div>
        </div>
    )
}
export default UploadItemModal