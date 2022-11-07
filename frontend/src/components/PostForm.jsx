import React, {useState} from 'react';
import MyInput from "./UI/input/MyInput";
import MyButton from "./UI/button/MyButton";

const PostForm = ({create}) => {

    const [post, setPost] = useState({title: '', body: ''});

    const addNewPost = (e) => {
        //предотвращение поведения по-умолчанию
        e.preventDefault()

        const newPost = {
            ...post, id: Date.now()
        }

        create(newPost)
        setPost({title: '', body: ''})
    };

    return (
        <form>
            {/*Управляемый компонент*/}
            <MyInput
                value={post.title}
                onChange={e => setPost({...post, title: e.target.value})}
                type ="text"
                placeholder="Post name"/>
            {/*Не управляемый/не контролируемый компонент*/}
            <MyInput
                value={post.body}
                onChange={e => setPost({...post, body: e.target.value})}
                type ="text"
                placeholder="Post description"/>
            <MyButton onClick={addNewPost}>Add post</MyButton>
        </form>
    );
};

export default PostForm;