    import { UserDetails } from "./UserDetails";

export interface User  {
    email :string,
    password :string,
    userName :string,
    lastLoginTime : string,
    create_time :string,
    valid : boolean,
    userDetails: UserDetails
}