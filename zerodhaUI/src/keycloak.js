import Keycloak from 'keycloak-js';

const keycloak = new Keycloak({
  url: 'http://localhost:8180', // Your Keycloak URL
  realm: 'Stock_Exchange_API',
  clientId: 'stock_exchange_security'
});
export default keycloak;
