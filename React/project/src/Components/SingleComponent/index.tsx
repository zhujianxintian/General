/**
 * 单个组件实现子组件改变父组件 state
 */
import React from "react";
import "./style.scss";

interface SingleComponentProps {
    checked?: boolean;
    onChange?: (
        checked?: boolean,
        event?: React.ChangeEvent<HTMLInputElement>
    ) => void;
}

const SingleComponent: React.FC<SingleComponentProps> = ({
    checked,
    onChange,
    children
}) => {
    return (
        <div className="App">
            <label>
                <input
                    type="checkbox"
                    checked={checked}
                    onChange={(event) => {
                        onChange?.(checked, event);
                    }}
                />
                {children}
            </label>
        </div>
    );
};
export { SingleComponentProps };
export default SingleComponent;
