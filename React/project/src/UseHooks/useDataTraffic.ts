import React, { useState } from "react";
import { useUpdate } from "./useUpdate";

/**
 * 实现组件内部 state 控制，如果父组件传递过来的数据发生改变则立刻改变数据，并将数据以回调函数返回
 * @param initState
 * @param parentData
 */
const useDataTraffic = <T>(
    initState: T,
    parentData: T,
    onDataChange: (data: T) => void
): [T, React.Dispatch<React.SetStateAction<T>>] => {
    const [data, setData] = useState(initState);

    // 当父组件的数据发生改变时，改变当前数据
    // data 改变中
    useUpdate(() => {
        setData(parentData);
    }, [parentData]);

    // data 数据发生改变时，触发 onDataChange 回调函数传递数据
    // data 开始改变
    useUpdate(() => {
        onDataChange(data);
    }, [onDataChange, data]);

    return [data, setData];
};

export { useDataTraffic };
