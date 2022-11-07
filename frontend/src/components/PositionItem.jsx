import React from 'react';
import MyButton from "./UI/button/MyButton";

const PositionItem = (props) => {
    return (
        <div className="post">
            <div className="post__content">
                <strong>{props.position.id}. {props.position.name}</strong>
                <div>
                    {props.position.worktype}, {props.position.salary}
                </div>
            </div>
            <div className="post__btns">
                <MyButton onClick={() => props.remove(props.post)}>
                    Delete
                </MyButton>
            </div>
        </div>
    );
};

export default PositionItem;