import React from 'react';
import classes from './MyInput.module.css';

//React.forwardRef нужен для работы неуправляемого имрорта
const MyInput = React.forwardRef((props, ref) => {
    return (
        <input ref={ref} className={classes.myInput} {...props}/>
    );
});


export default MyInput;