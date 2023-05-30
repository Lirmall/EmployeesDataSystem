import About from "./pages/About";
import {ABOUT_ROUTE, ERROR_ROUTE, HOME_ROUTE} from "./utils/constants";
import Error from "./pages/Error";
import Home from "./pages/Home";

export const authRoutes = [
    {
        path: ABOUT_ROUTE,
        Component: About
    },
    {
        path: ERROR_ROUTE,
        Component: Error
    },
    {
        path: HOME_ROUTE,
        Component: Home
    }
]

export const publicRoutes = [

]

export const employeeRoutes = [

]

export const buchhalterRoutes = [

]

export const headBuchhalterRoutes = [

]

export const persDeptEmployeeRoutes = [

]

export const headPersDeptRoutes = [

]

export const engineerRoutes = [

]

export const headEngineerRoutes = [

]

export const chiefEngineerRoutes = [

]

export const techDirRoutes = [

]

export const adminRoutes = [

]