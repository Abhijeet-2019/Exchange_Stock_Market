import axios from "axios";
import keycloak from "../keycloak";   // adjust path if needed

const baseUserUrl = "http://localhost:8080"; // API Gateway 

/**
 * Request to fetch Market data from Elastic..
 */
export const fetchAllMarketData = (numberOfItem: number): any => {
  const marketUrl = baseUserUrl + "/stock-repo-service/stockDetails/fetchMarketWatch?size=" + numberOfItem;
  console.log("The main URL to fetch the market data==>" + marketUrl);
  console.log("The auth token 000" + keycloak.token)
  const response = axios.get(marketUrl, {
    headers: {
      Authorization: `Bearer ${keycloak.token}`,
      "Content-Type": "application/json"
    }
  });
  return response;
}


export const fetchUserStockWatchList =(email : string) :any =>{
  const fetchUserURL = baseUserUrl + "/user-service/userWatchList/fetch?email="+email;
  const response = axios.get(fetchUserURL, {
    headers: {
      Authorization: `Bearer ${keycloak.token}`,
      "Content-Type": "application/json"
    }
  });  
  return response;
}


export const fetchLatestData =(stockName :String) :any =>{
  const stockNameURL = baseUserUrl + "/stock-repo-service/stockDetails/fetchLatestData?stockName=" + stockName;
   console.log("The main URL to fetch the market data==>" + stockNameURL);
  console.log("The auth token 000" + keycloak.token)
  const response = axios.get(stockNameURL, {
    headers: {
      Authorization: `Bearer ${keycloak.token}`,
      "Content-Type": "application/json"
    }
  });
  return response;
}

