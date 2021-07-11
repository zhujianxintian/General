import { DependencyList, EffectCallback, useEffect, useState } from "react";

/**
 * 作用和 useEffect 一样，不过可以选择是否跳过初始化执行, true 为跳过, false 为 初始化时执行
 * 如果没有依赖项，则需要传入 underfined 填充位置
 * @param {EffectCallback} effect useEffect callback
 * @param {DependencyList} deps dependency List
 * @param {boolean} skipInit skip init
 */
const useUpdate = (
    effect: EffectCallback,
    deps?: DependencyList,
    skipInit: boolean = true
) => {
    const [init, setInit] = useState(skipInit);

    useEffect(() => {
        if (init) {
            setInit(false);
            return;
        }
        return effect();
    }, [effect, init, deps]);
};

export { useUpdate };
