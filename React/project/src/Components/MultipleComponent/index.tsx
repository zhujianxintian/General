/**
 * 多个组件独立 state，可以自我改变 state，也可以子组件改变父组件传递下来的 state
 */
import React from "react";
import "./style.scss";

interface MultipleComponentProps {
    checked?: boolean;
    onChange?: (
        checked?: boolean,
        event?: React.ChangeEvent<HTMLInputElement>
    ) => void;
}

const MultipleComponent: React.FC<MultipleComponentProps> = ({
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

export { MultipleComponentProps };
export default MultipleComponent;
