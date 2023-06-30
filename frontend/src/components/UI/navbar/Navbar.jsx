import React from 'react';
import {Link} from "react-router-dom";

const Navbar = () => {
    return (
        <div className="navbar">
            <div className="navbar__links">
                <Link style={{marginRight: 5}} to="/">Home</Link>
                <Link style={{marginRight: 5}} to="/login">Log in</Link>
                <Link style={{marginRight: 5}} to="/about">About</Link>
                <Link style={{marginRight: 5}} to="/positions">Positions</Link>
                <Link style={{marginRight: 5}} to="/salary">Salaries</Link>
            </div>
        </div>
    );
};

export default Navbar;