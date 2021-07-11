import React, { useEffect, useState } from "react";
import "./styles.css";
import { useUpdate } from "./UseHooks/useUpdate";
import SingleComponent from "./Components/SingleComponent";
import SingleComponentPlus from "./Components/SingleComponentPlus";
import MultipleComponent from "./Components/MultipleComponent";
import UseCallbackDemo from "./Test/UseCallbackDemo";

const App = () => {
    const [singleComponentChecked, setSingleComponentChecked] = useState(false);
    const [
        singleComponentPlusChecked,
        setSingleComponentPlusChecked
    ] = useState(false);
    const [multipleComponents, setMultipleComponents] = useState<
        {
            id: number;
            checked: boolean;
            children?: React.ReactNode;
        }[]
    >([]);

    const [useCallbackDemoName, setUseCallbackDemoName] = useState("");
    const [useCallbackDemoValue, setUseCallbackDemoValue] = useState("");

    useEffect(() => {
        setInterval(() => {
            setUseCallbackDemoName(useCallbackDemoName + "1");
            // setUseCallbackDemoValue(useCallbackDemoValue + "1");
        }, 1000);
    }, []);

    useEffect(() => {
        setMultipleComponents([
            // { id: 1, checked: true, children: <span>多选1</span> },
            // { id: 2, checked: false, children: <span>多选2</span> },
            // { id: 3, checked: false, children: <span>多选3</span> }
        ]);
    }, []);

    useUpdate(() => {
        // 这里会打印是因为这个组件初始化之后又渲染了一次，也就是渲染了两次
        // 所以 useUpdate 是没问题的
        console.log("App.useUpdate: " + singleComponentPlusChecked);
    }, [singleComponentPlusChecked]);

    return (
        <div className="App">
            <div>
                <SingleComponent
                    checked={singleComponentChecked}
                    onChange={(checked) => setSingleComponentChecked(!checked)}
                >
                    一个单选
                </SingleComponent>
            </div>
            <div>
                <SingleComponentPlus
                    checked={{
                        state: singleComponentPlusChecked,
                        setState: (state: boolean) => {
                            console.log(
                                `App.SingleComponentPlus.setState.checked: ${state}`
                            );
                            setSingleComponentPlusChecked(!state);
                        }
                    }}
                >
                    一个单选 Plus
                </SingleComponentPlus>
            </div>
            <div>
                {multipleComponents.map((value, index) => {
                    return (
                        <MultipleComponent
                            key={value.id}
                            checked={value.checked}
                            onChange={(checked) => {
                                multipleComponents[index].checked = !checked;
                                setMultipleComponents([...multipleComponents]);
                            }}
                        >
                            {value.children}
                        </MultipleComponent>
                    );
                })}
                {/* 以前的误区是把全选按钮的状态独立了，然后可以自己改变状态，也可以是根据多选组的状态来改变状态 */}
                {/* 但是这样就会形成闭环，如果多选组的状态改变了，那么全选按钮的状态也会改变，全选改变了，也触发了改变多选组的事件，然后又会因为多选组状态改变，改变全选状态...... */}
                {/* 所以不能是由两个互调条件来改变状态，导致无限循环，而且会因为一异步更新状态导致无限循环时多选组的值不一致 */}
                {/* 所以最终解题方案就是，只用一个条件来改变和约束这两个组件的状态 */}
                {/* 多选组的每个对象对应每个多选按钮的状态，然后全选按钮根据多选数组的每个状态来决定状态 */}
                {/* 只要多选组的状态改变了，全选按钮的状态也会变，然后全选按钮本身是没有状态的，触发了改变事件，就把多选组中所有的状态调整为当前全选按钮的状态取反 */}
                {/* 总结：两个组件的状态都由一个状态来规定，但是这两个组件都有可以改变这个状态的方法 */}
                <MultipleComponent
                    checked={
                        // !!multipleComponents.length 是为了确保多选数组为空的情况，为空则设置全选按钮为 false
                        !!multipleComponents.length &&
                        multipleComponents.every((value) => value.checked)
                    }
                    onChange={(checked) => {
                        const array = multipleComponents.map((value) => {
                            value.checked = !checked;
                            return value;
                        });
                        setMultipleComponents([...array]);
                    }}
                >
                    全选
                </MultipleComponent>

                {/* 动态添加、减少多选数组测试 */}
                <button
                    onClick={() => {
                        const lastElement = multipleComponents[
                            multipleComponents.length - 1
                        ] || { id: 1, checked: true, children: "多选" };
                        setMultipleComponents([
                            ...multipleComponents,
                            {
                                id: lastElement.id + 1,
                                checked: !lastElement.checked,
                                children: lastElement.children
                            }
                        ]);
                    }}
                >
                    添加
                </button>
                <button
                    onClick={() => {
                        multipleComponents.pop();
                        setMultipleComponents([...multipleComponents]);
                    }}
                >
                    减少
                </button>
            </div>
            --------------------------------------
            <UseCallbackDemo
                name={useCallbackDemoName}
                value={useCallbackDemoValue}
            />
        </div>
    );
};

export default App;
