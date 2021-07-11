/**
 * 单个组件实现子组件改变父组件 state
 */
import React from "react";
import "./style.scss";

interface SingleComponentPlusProps {
    checked?: {
        state: boolean;
        setState: (
            state: boolean,
            event?: React.ChangeEvent<HTMLInputElement>
        ) => void;
    };
}

const SingleComponentPlus: React.FC<SingleComponentPlusProps> = ({
    checked,
    children
}) => {
    return (
        <div className="App">
            <label>
                <input
                    type="checkbox"
                    checked={checked?.state}
                    onChange={(event) => {
                        console.log(
                            `SingleComponentPlus.onChange.checked: ${checked?.state}`
                        );
                        checked?.setState(checked.state, event);
                    }}
                />
                {children}
            </label>
        </div>
    );
};
export { SingleComponentPlusProps };
export default SingleComponentPlus;
