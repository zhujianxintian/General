import React, { useCallback } from "react";

let func1: Function;
let func2: Function;

interface UseCallbackDemoProps {
    name: string;
    value: string;
}

const UseCallbackDemo: React.FC<UseCallbackDemoProps> = ({ name, value }) => {
    const callback1 = useCallback(() => {
        return value + "1";
    }, [value]);

    const callback2 = () => {
        return value + "1";
    };

    console.log(`func1 === callback1: ${Object.is(func1, callback1)}`);

    console.log(`func2 === callback2: ${Object.is(func2, callback2)}`);

    func1 = callback1;

    func2 = callback2;

    return (
        <div>
            <div>{callback1()}</div>
            <div>{callback2()}</div>
        </div>
    );
};

export default UseCallbackDemo;
