import { useEffect, useState } from "react";
import keycloak from "./keycloak";
import { AuthContext } from "./AuthContext";
export const AuthProvider = ({ children }: { children: React.ReactNode }) => {


    const [initialized, setInitialized] = useState(false);
    const [isAuthenticated, setAuthenticated] = useState(false);
    const [token, setToken] = useState<string | null>(null);
    const [user, setUser] = useState<any>(null);

    useEffect(() => {
        keycloak
            .init({
                onLoad: "login-required",
                pkceMethod: "S256",
                checkLoginIframe: true,
            })
            .then(authenticated => {
                setAuthenticated(authenticated);
                setToken(keycloak.token || null);

                if (authenticated && keycloak.tokenParsed) {
                    setUser({
                        id: keycloak.tokenParsed.sub,
                        name: keycloak.tokenParsed.name,
                        email: keycloak.tokenParsed.email,
                        roles: keycloak.realmAccess?.roles || [],
                    });
                }

                setInitialized(true);
            });

        keycloak.onTokenExpired = () => {
            keycloak.updateToken(30).then(refreshed => {
                if (refreshed) {
                    setToken(keycloak.token!);
                }
            });
        };
    }, []);

    if (!initialized) {
        return <div>Loading authentication...</div>;
    }

    return (
        <AuthContext.Provider
            value={{
                isAuthenticated,
                token,
                user,              
            }}
        >
            {children}
        </AuthContext.Provider>
    );
};
